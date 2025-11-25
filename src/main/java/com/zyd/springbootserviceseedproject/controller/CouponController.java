package com.zyd.springbootserviceseedproject.controller;

import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.entity.CouponEntity;
import com.zyd.springbootserviceseedproject.entity.CouponIssueEntity;
import com.zyd.springbootserviceseedproject.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券表 Controller
 *
 * @author zyd
 * @since 2024-05-20
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * 根据活动ID查询优惠券列表
     * @param activityId 活动ID
     * @return 优惠券列表
     */
    @GetMapping("/listByActivityId/{activityId}")
    public Result<List<CouponEntity>> listByActivityId(@PathVariable Long activityId) {
        List<CouponEntity> couponList = couponService.listByActivityId(activityId);
        return Result.success(couponList);
    }

    /**
     * 生成优惠券码
     * @param couponId 优惠券ID
     * @param quantity 生成数量
     * @return 优惠券码列表
     */
    @GetMapping("/generateCodes/{couponId}/{quantity}")
    public Result<List<String>> generateCouponCodes(@PathVariable Long couponId, @PathVariable Integer quantity) {
        List<String> couponCodes = couponService.generateCouponCodes(couponId, quantity);
        return Result.success(couponCodes);
    }

    /**
     * 发放优惠券给用户
     * @param couponId 优惠券ID
     * @param userId 用户ID
     * @return 发放记录
     */
    @PostMapping("/issueToUser/{couponId}/{userId}")
    public Result<CouponIssueEntity> issueCouponToUser(@PathVariable Long couponId, @PathVariable Long userId) {
        CouponIssueEntity couponIssue = couponService.issueCouponToUser(couponId, userId);
        return couponIssue != null ? Result.success(couponIssue) : Result.fail("发放失败");
    }

    /**
     * 批量发放优惠券给用户
     * @param couponId 优惠券ID
     * @param userIds 用户ID列表
     * @return 成功发放数量
     */
    @PostMapping("/batchIssueToUsers/{couponId}")
    public Result<Integer> batchIssueCouponToUsers(@PathVariable Long couponId, @RequestBody List<Long> userIds) {
        int successCount = couponService.batchIssueCouponToUsers(couponId, userIds);
        return Result.success(successCount);
    }

    /**
     * 用户领取优惠券
     * @param couponId 优惠券ID
     * @param userId 用户ID
     * @return 领取记录
     */
    @PostMapping("/receive/{couponId}/{userId}")
    public Result<CouponIssueEntity> receiveCoupon(@PathVariable Long couponId, @PathVariable Long userId) {
        CouponIssueEntity couponIssue = couponService.receiveCoupon(couponId, userId);
        return couponIssue != null ? Result.success(couponIssue) : Result.fail("领取失败");
    }

    /**
     * 核销优惠券
     * @param couponCode 优惠券码
     * @param userId 用户ID
     * @return 核销结果
     */
    @PostMapping("/use/{couponCode}/{userId}")
    public Result<Boolean> useCoupon(@PathVariable String couponCode, @PathVariable Long userId) {
        boolean success = couponService.useCoupon(couponCode, userId);
        return success ? Result.success(true) : Result.fail("核销失败");
    }

    /**
     * 查询用户未使用的优惠券
     * @param userId 用户ID
     * @return 优惠券列表
     */
    @GetMapping("/listUnusedByUserId/{userId}")
    public Result<List<CouponIssueEntity>> listUnusedCouponsByUserId(@PathVariable Long userId) {
        List<CouponIssueEntity> couponIssueList = couponService.listUnusedCouponsByUserId(userId);
        return Result.success(couponIssueList);
    }

    /**
     * 查询用户已使用的优惠券
     * @param userId 用户ID
     * @return 优惠券列表
     */
    @GetMapping("/listUsedByUserId/{userId}")
    public Result<List<CouponIssueEntity>> listUsedCouponsByUserId(@PathVariable Long userId) {
        List<CouponIssueEntity> couponIssueList = couponService.listUsedCouponsByUserId(userId);
        return Result.success(couponIssueList);
    }

    /**
     * 查询用户已过期的优惠券
     * @param userId 用户ID
     * @return 优惠券列表
     */
    @GetMapping("/listExpiredByUserId/{userId}")
    public Result<List<CouponIssueEntity>> listExpiredCouponsByUserId(@PathVariable Long userId) {
        List<CouponIssueEntity> couponIssueList = couponService.listExpiredCouponsByUserId(userId);
        return Result.success(couponIssueList);
    }

    /**
     * 创建优惠券
     * @param coupon 优惠券信息
     * @return 创建结果
     */
    @PostMapping("/create")
    public Result<Boolean> createCoupon(@RequestBody CouponEntity coupon) {
        boolean success = couponService.save(coupon);
        return success ? Result.success(true) : Result.fail("创建失败");
    }

    /**
     * 更新优惠券
     * @param coupon 优惠券信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result<Boolean> updateCoupon(@RequestBody CouponEntity coupon) {
        boolean success = couponService.updateById(coupon);
        return success ? Result.success(true) : Result.fail("更新失败");
    }

    /**
     * 删除优惠券
     * @param couponId 优惠券ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{couponId}")
    public Result<Boolean> deleteCoupon(@PathVariable Long couponId) {
        boolean success = couponService.removeById(couponId);
        return success ? Result.success(true) : Result.fail("删除失败");
    }

    /**
     * 根据ID查询优惠券
     * @param couponId 优惠券ID
     * @return 优惠券信息
     */
    @GetMapping("/getById/{couponId}")
    public Result<CouponEntity> getCouponById(@PathVariable Long couponId) {
        CouponEntity coupon = couponService.getById(couponId);
        return coupon != null ? Result.success(coupon) : Result.fail("优惠券不存在");
    }

    /**
     * 查询所有优惠券
     * @return 优惠券列表
     */
    @GetMapping("/listAll")
    public Result<List<CouponEntity>> listAllCoupons() {
        List<CouponEntity> couponList = couponService.list();
        return Result.success(couponList);
    }
}