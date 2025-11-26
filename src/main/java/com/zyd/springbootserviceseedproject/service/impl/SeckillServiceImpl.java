package com.zyd.springbootserviceseedproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyd.springbootserviceseedproject.entity.SeckillEntity;
import com.zyd.springbootserviceseedproject.mapper.SeckillMapper;
import com.zyd.springbootserviceseedproject.service.SeckillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 秒杀活动Service实现类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Service
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, SeckillEntity> implements SeckillService {

    /**
     * 秒杀活动下单
     *
     * @param seckillId 秒杀活动ID
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 下单结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean seckill(Long seckillId, Long userId, Long productId) {
        // 1. 检查秒杀活动是否存在
        SeckillEntity seckill = this.getById(seckillId);
        if (seckill == null) {
            throw new RuntimeException("秒杀活动不存在");
        }

        // 2. 检查秒杀活动是否已经开始
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(seckill.getSeckillStartTime())) {
            throw new RuntimeException("秒杀活动尚未开始");
        }

        // 3. 检查秒杀活动是否已经结束
        if (now.isAfter(seckill.getSeckillEndTime())) {
            throw new RuntimeException("秒杀活动已经结束");
        }

        // 4. 检查库存是否充足
        if (seckill.getSeckillStock() <= 0) {
            throw new RuntimeException("商品已经售罄");
        }

        // 5. 检查用户是否已经参与过该秒杀活动
        // TODO: 实现用户参与记录检查

        // 6. 扣减库存（使用乐观锁）
        seckill.setSeckillStock(seckill.getSeckillStock() - 1);
        seckill.setSoldCount(seckill.getSoldCount() + 1);
        boolean success = this.updateById(seckill);
        if (!success) {
            throw new RuntimeException("库存扣减失败，请重试");
        }

        // 7. 生成秒杀订单
        // TODO: 实现秒杀订单生成

        return true;
    }

}