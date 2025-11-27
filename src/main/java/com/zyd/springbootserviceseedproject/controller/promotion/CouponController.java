package com.zyd.springbootserviceseedproject.controller.promotion;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyd.springbootserviceseedproject.entity.promotion.CartItem;
import com.zyd.springbootserviceseedproject.entity.promotion.Coupon;
import com.zyd.springbootserviceseedproject.entity.promotion.UserCoupon;
import com.zyd.springbootserviceseedproject.service.promotion.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券管理控制器
 * 
 * @author zyd
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/promotion/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * 获取所有优惠券（分页）
     * 
     * @param page 页码
     * @param size 每页大小
     * @return 优惠券分页列表
     */
    @GetMapping("/list")
    public Page<Coupon> getCouponList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return couponService.page(new Page<>(page, size));
    }

    /**
     * 获取所有有效优惠券
     * 
     * @return 有效优惠券列表
     */
    @GetMapping("/valid")
    public List<Coupon> getAllValidCoupons() {
        return couponService.getAllValidCoupons();
    }

    /**
     * 根据ID获取优惠券
     * 
     * @param id 优惠券ID
     * @return 优惠券详情
     */
    @GetMapping("/{id}")
    public Coupon getCouponById(@PathVariable Long id) {
        return couponService.getById(id);
    }

    /**
     * 创建优惠券
     * 
     * @param coupon 优惠券信息
     * @return 创建后的优惠券
     */
    @PostMapping("/create")
    public Coupon createCoupon(@RequestBody Coupon coupon) {
        return couponService.saveCoupon(coupon);
    }

    /**
     * 更新优惠券
     * 
     * @param id     优惠券ID
     * @param coupon 优惠券信息
     * @return 更新后的优惠券
     */
    @PutMapping("/{id}")
    public Coupon updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        coupon.setId(id);
        return couponService.updateCoupon(coupon);
    }

    /**
     * 删除优惠券
     * 
     * @param id 优惠券ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean deleteCoupon(@PathVariable Long id) {
        return couponService.deleteCoupon(id);
    }

    /**
     * 用户领取优惠券
     * 
     * @param couponId 优惠券ID
     * @param userId   用户ID
     * @return 领取结果
     */
    @PostMapping("/receive/{couponId}")
    public boolean receiveCoupon(@PathVariable Long couponId, @RequestParam Long userId) {
        return couponService.receiveCoupon(couponId, userId);
    }

    /**
     * 用户使用优惠券
     * 
     * @param userCouponId 用户优惠券ID
     * @param orderId      订单ID
     * @return 使用结果
     */
    @PostMapping("/use/{userCouponId}")
    public boolean useCoupon(@PathVariable Long userCouponId, @RequestParam Long orderId) {
        return couponService.useCoupon(userCouponId, orderId);
    }

    /**
     * 用户退还优惠券
     * 
     * @param userCouponId 用户优惠券ID
     * @return 退还结果
     */
    @PostMapping("/refund/{userCouponId}")
    public boolean refundCoupon(@PathVariable Long userCouponId) {
        return couponService.refundCoupon(userCouponId);
    }

    /**
     * 获取用户可用优惠券
     * 
     * @param userId 用户ID
     * @return 用户可用优惠券列表
     */
    @GetMapping("/user/available/{userId}")
    public List<UserCoupon> getAvailableCouponsByUserId(@PathVariable Long userId) {
        return couponService.getAvailableCouponsByUserId(userId);
    }

    /**
     * 根据商品ID获取适用优惠券
     * 
     * @param productId 商品ID
     * @return 适用优惠券列表
     */
    @GetMapping("/applicable/product/{productId}")
    public List<Coupon> getApplicableCouponsByProductId(@PathVariable Long productId) {
        return couponService.getApplicableCouponsByProductId(productId);
    }

    /**
     * 获取购物车适用优惠券
     * 
     * @param userId    用户ID
     * @param cartItems 购物车商品列表
     * @return 适用优惠券列表
     */
    @PostMapping("/applicable/cart")
    public List<Coupon> getApplicableCouponsByCart(@RequestParam Long userId, @RequestBody List<CartItem> cartItems) {
        return couponService.getApplicableCouponsByCart(userId, cartItems);
    }
}