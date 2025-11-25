package com.zyd.springbootserviceseedproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.SeckillProductEntity;
import com.zyd.springbootserviceseedproject.entity.SeckillOrderEntity;

import java.util.List;

/**
 * 秒杀商品表 Service 接口
 *
 * @author zyd
 * @since 2024-05-20
 */
public interface SeckillService extends IService<SeckillProductEntity> {

    /**
     * 根据活动ID查询秒杀商品列表
     * @param activityId 活动ID
     * @return 秒杀商品列表
     */
    List<SeckillProductEntity> listByActivityId(Long activityId);

    /**
     * 预热秒杀商品库存到Redis
     * @param seckillProductId 秒杀商品ID
     * @return true-预热成功，false-预热失败
     */
    boolean preheatStockToRedis(Long seckillProductId);

    /**
     * 从Redis扣减秒杀商品库存
     * @param seckillProductId 秒杀商品ID
     * @param quantity 扣减数量
     * @return true-扣减成功，false-扣减失败
     */
    boolean decreaseStockFromRedis(Long seckillProductId, Integer quantity);

    /**
     * 从Redis恢复秒杀商品库存
     * @param seckillProductId 秒杀商品ID
     * @param quantity 恢复数量
     * @return true-恢复成功，false-恢复失败
     */
    boolean increaseStockToRedis(Long seckillProductId, Integer quantity);

    /**
     * 检查用户是否有秒杀资格
     * @param seckillProductId 秒杀商品ID
     * @param userId 用户ID
     * @param ip 用户IP
     * @return true-有资格，false-无资格
     */
    boolean checkSeckillQualification(Long seckillProductId, Long userId, String ip);

    /**
     * 创建秒杀订单
     * @param seckillProductId 秒杀商品ID
     * @param userId 用户ID
     * @param quantity 购买数量
     * @return 秒杀订单
     */
    SeckillOrderEntity createSeckillOrder(Long seckillProductId, Long userId, Integer quantity);

    /**
     * 根据订单号查询秒杀订单
     * @param orderNo 订单号
     * @return 秒杀订单
     */
    SeckillOrderEntity getSeckillOrderByOrderNo(String orderNo);

    /**
     * 更新秒杀订单状态
     * @param orderNo 订单号
     * @param status 订单状态：1-待支付，2-已支付，3-已取消，4-已完成
     * @return true-更新成功，false-更新失败
     */
    boolean updateSeckillOrderStatus(String orderNo, Integer status);

    /**
     * 处理超时未支付的秒杀订单
     * @param orderNo 订单号
     * @return true-处理成功，false-处理失败
     */
    boolean handleTimeoutSeckillOrder(String orderNo);
}
