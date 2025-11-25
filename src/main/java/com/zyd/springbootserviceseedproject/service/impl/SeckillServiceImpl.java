package com.zyd.springbootserviceseedproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyd.springbootserviceseedproject.entity.SeckillProductEntity;
import com.zyd.springbootserviceseedproject.entity.SeckillOrderEntity;
import com.zyd.springbootserviceseedproject.mapper.SeckillProductMapper;
import com.zyd.springbootserviceseedproject.mapper.SeckillOrderMapper;
import com.zyd.springbootserviceseedproject.service.SeckillService;
import com.zyd.springbootserviceseedproject.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀商品表 Service 实现类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Service
public class SeckillServiceImpl extends ServiceImpl<SeckillProductMapper, SeckillProductEntity> implements SeckillService {

    @Autowired
    private SeckillProductMapper seckillProductMapper;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<SeckillProductEntity> listByActivityId(Long activityId) {
        LambdaQueryWrapper<SeckillProductEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SeckillProductEntity::getActivityId, activityId);
        return seckillProductMapper.selectList(queryWrapper);
    }

    @Override
    public boolean preheatStockToRedis(Long seckillProductId) {
        SeckillProductEntity seckillProduct = seckillProductMapper.selectById(seckillProductId);
        if (seckillProduct == null) {
            return false;
        }

        String stockKey = "seckill:stock:" + seckillProductId;
        redisTemplate.opsForValue().set(stockKey, seckillProduct.getStock(), 24, TimeUnit.HOURS);
        return true;
    }

    @Override
    public boolean decreaseStockFromRedis(Long seckillProductId, Integer quantity) {
        String stockKey = "seckill:stock:" + seckillProductId;
        Long stock = redisTemplate.opsForValue().decrement(stockKey, quantity);
        if (stock != null && stock >= 0) {
            return true;
        } else {
            // 库存不足，回滚
            redisTemplate.opsForValue().increment(stockKey, quantity);
            return false;
        }
    }

    @Override
    public boolean increaseStockToRedis(Long seckillProductId, Integer quantity) {
        String stockKey = "seckill:stock:" + seckillProductId;
        redisTemplate.opsForValue().increment(stockKey, quantity);
        return true;
    }

    @Override
    public boolean checkSeckillQualification(Long seckillProductId, Long userId, String ip) {
        // 检查用户是否已经参与过该秒杀
        String userSeckillKey = "seckill:user:" + userId + ":" + seckillProductId;
        if (redisTemplate.hasKey(userSeckillKey)) {
            return false;
        }

        // 检查IP是否已经参与过该秒杀
        String ipSeckillKey = "seckill:ip:" + ip + ":" + seckillProductId;
        if (redisTemplate.hasKey(ipSeckillKey)) {
            return false;
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SeckillOrderEntity createSeckillOrder(Long seckillProductId, Long userId, Integer quantity) {
        SeckillProductEntity seckillProduct = seckillProductMapper.selectById(seckillProductId);
        if (seckillProduct == null) {
            return null;
        }

        // 扣减数据库库存
        int result = seckillProductMapper.decreaseStock(seckillProductId, quantity);
        if (result <= 0) {
            return null;
        }

        // 创建秒杀订单
        SeckillOrderEntity seckillOrder = new SeckillOrderEntity();
        seckillOrder.setSeckillProductId(seckillProductId);
        seckillOrder.setUserId(userId);
        seckillOrder.setProductId(seckillProduct.getProductId());
        seckillOrder.setProductName(seckillProduct.getProductName());
        seckillOrder.setProductImage(seckillProduct.getProductImage());
        seckillOrder.setSeckillPrice(seckillProduct.getSeckillPrice());
        seckillOrder.setQuantity(quantity);
        seckillOrder.setTotalAmount(seckillProduct.getSeckillPrice().multiply(quantity.longValue()));
        seckillOrder.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        seckillOrder.setStatus(1); // 1-待支付
        seckillOrder.setCreateTime(LocalDateTime.now());
        seckillOrderMapper.insert(seckillOrder);

        // 记录用户和IP参与秒杀的信息
        String userSeckillKey = "seckill:user:" + userId + ":" + seckillProductId;
        String ipSeckillKey = "seckill:ip:" + userId + ":" + seckillProductId; // 这里应该使用真实IP，暂时用userId代替
        redisTemplate.opsForValue().set(userSeckillKey, "1", 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(ipSeckillKey, "1", 24, TimeUnit.HOURS);

        return seckillOrder;
    }

    @Override
    public SeckillOrderEntity getSeckillOrderByOrderNo(String orderNo) {
        return seckillOrderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public boolean updateSeckillOrderStatus(String orderNo, Integer status) {
        SeckillOrderEntity seckillOrder = new SeckillOrderEntity();
        seckillOrder.setOrderNo(orderNo);
        seckillOrder.setStatus(status);
        seckillOrder.setUpdateTime(LocalDateTime.now());
        return seckillOrderMapper.updateById(seckillOrder) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleTimeoutSeckillOrder(String orderNo) {
        SeckillOrderEntity seckillOrder = seckillOrderMapper.selectByOrderNo(orderNo);
        if (seckillOrder == null || seckillOrder.getStatus() != 1) { // 1-待支付
            return false;
        }

        // 更新订单状态为已取消
        seckillOrder.setStatus(3); // 3-已取消
        seckillOrder.setUpdateTime(LocalDateTime.now());
        seckillOrderMapper.updateById(seckillOrder);

        // 恢复数据库库存
        seckillProductMapper.increaseStock(seckillOrder.getSeckillProductId(), seckillOrder.getQuantity());

        // 恢复Redis库存
        increaseStockToRedis(seckillOrder.getSeckillProductId(), seckillOrder.getQuantity());

        return true;
    }
}