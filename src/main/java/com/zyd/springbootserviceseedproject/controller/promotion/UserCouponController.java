package com.zyd.springbootserviceseedproject.controller.promotion;

import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.entity.promotion.UserCoupon;
import com.zyd.springbootserviceseedproject.service.promotion.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户优惠券Controller
 * @author zyd
 * @date 2024-05-20
 */
@RestController
@RequestMapping("/promotion/coupon")
public class UserCouponController {

    @Autowired
    private UserCouponService userCouponService;

    /**
     * 获取用户可用的优惠券
     * @param userId 用户ID
     * @return 可用的优惠券列表
     */
    @GetMapping("/available")
    public Result<List<UserCoupon>> getUserAvailableCoupons(@RequestParam Long userId) {
        List<UserCoupon> list = userCouponService.getUserAvailableCoupons(userId);
        return Result.success(list);
    }

    /**
     * 领取优惠券
     * @param userId 用户ID
     * @param couponRuleId 优惠券规则ID
     * @return 领取结果
     */
    @PostMapping("/receive")
    public Result<Boolean> receiveCoupon(@RequestParam Long userId, @RequestParam Long couponRuleId) {
        boolean received = userCouponService.receiveCoupon(userId, couponRuleId);
        return Result.success(received);
    }

    /**
     * 使用优惠券
     * @param userId 用户ID
     * @param couponId 优惠券ID
     * @param orderId 订单ID
     * @return 使用结果
     */
    @PostMapping("/use")
    public Result<Boolean> useCoupon(@RequestParam Long userId, @RequestParam Long couponId, @RequestParam Long orderId) {
        boolean used = userCouponService.useCoupon(userId, couponId, orderId);
        return Result.success(used);
    }

    /**
     * 获取用户优惠券数量
     * @param userId 用户ID
     * @param status 优惠券状态
     * @return 优惠券数量
     */
    @GetMapping("/count")
    public Result<Integer> getUserCouponCount(@RequestParam Long userId, @RequestParam Integer status) {
        int count = userCouponService.getUserCouponCount(userId, status);
        return Result.success(count);
    }
}