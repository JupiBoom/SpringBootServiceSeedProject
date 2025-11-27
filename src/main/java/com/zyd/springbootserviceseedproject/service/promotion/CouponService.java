package com.zyd.springbootserviceseedproject.service.promotion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.promotion.Coupon;
import com.zyd.springbootserviceseedproject.entity.promotion.UserCoupon;

import java.util.List;

/**
 * 优惠券服务接口
 * 
 * @author zyd
 * @since 2024-01-01
 */
public interface CouponService extends IService<Coupon> {

    /**
     * 获取所有有效的优惠券
     * 
     * @return 有效的优惠券列表
     */
    List<Coupon> getAllValidCoupons();

    /**
     * 根据用户ID获取可使用的优惠券
     * 
     * @param userId 用户ID
     * @return 用户可使用的优惠券列表
     */
    List<UserCoupon> getAvailableCouponsByUserId(Long userId);

    /**
     * 根据商品ID获取适用的优惠券
     * 
     * @param productId 商品ID
     * @return 适用的优惠券列表
     */
    List<Coupon> getApplicableCouponsByProductId(Long productId);

    /**
     * 领取优惠券
     * 
     * @param couponId 优惠券ID
     * @param userId 用户ID
     * @return 领取结果
     */
    boolean receiveCoupon(Long couponId, Long userId);

    /**
     * 使用优惠券
     * 
     * @param userCouponId 用户优惠券ID
     * @param orderId 订单ID
     * @return 使用结果
     */
    boolean useCoupon(Long userCouponId, Long orderId);

    /**
     * 退还优惠券
     * 
     * @param userCouponId 用户优惠券ID
     * @return 退还结果
     */
    boolean refundCoupon(Long userCouponId);

    /**
     * 保存优惠券
     * 
     * @param coupon 优惠券
     * @return 保存后的优惠券
     */
    Coupon saveCoupon(Coupon coupon);

    /**
     * 更新优惠券
     * 
     * @param coupon 优惠券
     * @return 更新后的优惠券
     */
    Coupon updateCoupon(Coupon coupon);

    /**
     * 删除优惠券
     * 
     * @param id 优惠券ID
     * @return 删除结果
     */
    boolean deleteCoupon(Long id);
}