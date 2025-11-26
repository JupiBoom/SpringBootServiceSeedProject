package com.zyd.springbootserviceseedproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.SeckillEntity;

/**
 * 秒杀活动Service接口
 *
 * @author zyd
 * @since 2024-05-20
 */
public interface SeckillService extends IService<SeckillEntity> {

    /**
     * 秒杀活动下单
     *
     * @param seckillId 秒杀活动ID
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 下单结果
     */
    boolean seckill(Long seckillId, Long userId, Long productId);

}