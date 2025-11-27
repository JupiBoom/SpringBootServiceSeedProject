package com.zyd.springbootserviceseedproject.controller.promotion;

import com.zyd.springbootserviceseedproject.entity.promotion.CartItem;
import com.zyd.springbootserviceseedproject.entity.promotion.DiscountResult;
import com.zyd.springbootserviceseedproject.entity.promotion.UserCoupon;
import com.zyd.springbootserviceseedproject.service.promotion.DiscountCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠计算控制器
 * 
 * @author zyd
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/promotion/discount")
public class DiscountCalculationController {

    @Autowired
    private DiscountCalculationService discountCalculationService;

    /**
     * 购物车优惠计算
     * 
     * @param userId    用户ID
     * @param cartItems 购物车商品列表
     * @return 优惠计算结果
     */
    @PostMapping("/calculate")
    public DiscountResult calculateDiscount(@RequestParam Long userId, @RequestBody List<CartItem> cartItems) {
        return discountCalculationService.calculateDiscountForCart(userId, cartItems);
    }

    /**
     * 指定优惠券组合计算
     * 
     * @param userId        用户ID
     * @param cartItems     购物车商品列表
     * @param userCouponIds 选择的优惠券ID列表
     * @return 优惠计算结果
     */
    @PostMapping("/calculate-with-coupons")
    public DiscountResult calculateDiscountWithCoupons(
            @RequestParam Long userId,
            @RequestBody List<CartItem> cartItems,
            @RequestParam List<Long> userCouponIds) {
        return discountCalculationService.calculateDiscountWithCoupons(userId, cartItems, userCouponIds);
    }

    /**
     * 最优优惠推荐
     * 
     * @param userId    用户ID
     * @param cartItems 购物车商品列表
     * @return 最优优惠计算结果
     */
    @PostMapping("/recommend-optimal")
    public DiscountResult recommendOptimalDiscount(@RequestParam Long userId, @RequestBody List<CartItem> cartItems) {
        return discountCalculationService.recommendOptimalDiscount(userId, cartItems);
    }

    /**
     * 订单优惠计算
     * 
     * @param orderId 订单ID
     * @return 优惠计算结果
     */
    @GetMapping("/order/{orderId}")
    public DiscountResult calculateDiscountForOrder(@PathVariable Long orderId) {
        return discountCalculationService.calculateDiscountForOrder(orderId);
    }

    /**
     * 获取用户可用优惠券
     * 
     * @param userId    用户ID
     * @param cartItems 购物车商品列表
     * @return 可用优惠券列表
     */
    @PostMapping("/available-coupons")
    public List<UserCoupon> getAvailableCoupons(@RequestParam Long userId, @RequestBody List<CartItem> cartItems) {
        return discountCalculationService.getAvailableCouponsForUser(userId, cartItems);
    }

    /**
     * 获取适用促销规则
     * 
     * @param cartItems 购物车商品列表
     * @return 适用促销规则列表
     */
    @PostMapping("/applicable-promotions")
    public List<Object> getApplicablePromotions(@RequestBody List<CartItem> cartItems) {
        return discountCalculationService.getApplicablePromotions(cartItems);
    }
}