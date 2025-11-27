package com.zyd.springbootserviceseedproject.service.promotion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.promotion.UserCoupon;

import java.util.List;

/**
 * 用户优惠券Service
 * @author zyd
 * @date 2024-05-20
 */
public interface UserCouponService extends IService<UserCoupon> {

    /**
     * 获取用户可用的优惠券列表
     * @param userId 用户ID
     * @return 可用的优惠券列表
     */
    List<UserCoupon> getUserAvailableCoupons(Long userId);

    /**
     * 领取优惠券
     * @param userId 用户ID
     * @param couponRuleId 优惠券规则ID
     * @return 是否领取成功
     */
    boolean receiveCoupon(Long userId, Long couponRuleId);

    /**
     * 使用优惠券
     * @param userId 用户ID
     * @param couponId 优惠券ID
     * @param orderId 订单ID
     * @return 是否使用成功
     */
    boolean useCoupon(Long userId, Long couponId, Long orderId);

    /**
     * 获取用户优惠券数量
     * @param userId 用户ID
     * @param status 优惠券状态
     * @return 优惠券数量
     */
    int getUserCouponCount(Long userId, Integer status);
}