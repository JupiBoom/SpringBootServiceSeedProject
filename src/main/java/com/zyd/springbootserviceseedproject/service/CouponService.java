package com.zyd.springbootserviceseedproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.CouponEntity;

/**
 * 优惠券Service接口
 *
 * @author zyd
 * @since 2024-05-20
 */
public interface CouponService extends IService<CouponEntity> {

    /**
     * 批量生成优惠券
     *
     * @param couponId 优惠券ID
     * @param count    生成数量
     * @return 生成结果
     */
    boolean batchGenerateCoupons(Long couponId, Integer count);

    /**
     * 用户领取优惠券
     *
     * @param couponId 优惠券ID
     * @param userId   用户ID
     * @return 领取结果
     */
    boolean receiveCoupon(Long couponId, Long userId);

    /**
     * 核销优惠券
     *
     * @param couponCode 券码
     * @param userId     用户ID
     * @param orderId    订单ID
     * @return 核销结果
     */
    boolean useCoupon(String couponCode, Long userId, Long orderId);

}