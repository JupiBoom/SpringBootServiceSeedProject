package com.zyd.springbootserviceseedproject.service.promotion;

import com.zyd.springbootserviceseedproject.entity.promotion.CartItem;
import com.zyd.springbootserviceseedproject.entity.promotion.DiscountResult;
import com.zyd.springbootserviceseedproject.entity.promotion.UserCoupon;

import java.util.List;

/**
 * 优惠计算核心服务接口
 * 
 * @author zyd
 * @since 2024-01-01
 */
public interface DiscountCalculationService {

    /**
     * 购物车优惠计算
     * 
     * @param userId    用户ID
     * @param cartItems 购物车商品列表
     * @return 优惠计算结果
     */
    DiscountResult calculateDiscountForCart(Long userId, List<CartItem> cartItems);

    /**
     * 指定优惠券组合计算
     * 
     * @param userId        用户ID
     * @param cartItems     购物车商品列表
     * @param userCouponIds 选择的优惠券ID列表
     * @return 优惠计算结果
     */
    DiscountResult calculateDiscountWithCoupons(Long userId, List<CartItem> cartItems, List<Long> userCouponIds);

    /**
     * 最优优惠推荐
     * 
     * @param userId    用户ID
     * @param cartItems 购物车商品列表
     * @return 最优优惠计算结果
     */
    DiscountResult recommendOptimalDiscount(Long userId, List<CartItem> cartItems);

    /**
     * 订单优惠计算
     * 
     * @param orderId 订单ID
     * @return 优惠计算结果
     */
    DiscountResult calculateDiscountForOrder(Long orderId);

    /**
     * 获取用户可用优惠券
     * 
     * @param userId    用户ID
     * @param cartItems 购物车商品列表
     * @return 可用优惠券列表
     */
    List<UserCoupon> getAvailableCouponsForUser(Long userId, List<CartItem> cartItems);

    /**
     * 获取适用促销规则
     * 
     * @param cartItems 购物车商品列表
     * @return 适用促销规则列表
     */
    List<Object> getApplicablePromotions(List<CartItem> cartItems);
}