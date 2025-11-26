package com.zyd.springbootserviceseedproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.entity.CouponEntity;
import com.zyd.springbootserviceseedproject.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券Controller
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
     * 分页查询优惠券列表
     *
     * @param page 页码
     * @param size 每页大小
     * @return 优惠券列表
     */
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<CouponEntity> pageParam = new Page<>(page, size);
        Page<CouponEntity> pageResult = couponService.page(pageParam);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询优惠券
     *
     * @param id 优惠券ID
     * @return 优惠券信息
     */
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id) {
        CouponEntity coupon = couponService.getById(id);
        return Result.success(coupon);
    }

    /**
     * 创建优惠券
     *
     * @param coupon 优惠券信息
     * @return 创建结果
     */
    @PostMapping("/create")
    public Result create(@RequestBody CouponEntity coupon) {
        couponService.save(coupon);
        return Result.success();
    }

    /**
     * 更新优惠券
     *
     * @param coupon 优惠券信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result update(@RequestBody CouponEntity coupon) {
        couponService.updateById(coupon);
        return Result.success();
    }

    /**
     * 删除优惠券
     *
     * @param id 优惠券ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        couponService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除优惠券
     *
     * @param ids 优惠券ID列表
     * @return 删除结果
     */
    @DeleteMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<Long> ids) {
        couponService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 批量生成优惠券
     *
     * @param couponId 优惠券ID
     * @param count    生成数量
     * @return 生成结果
     */
    @PostMapping("/batch-generate")
    public Result batchGenerateCoupons(@RequestParam Long couponId, @RequestParam Integer count) {
        boolean result = couponService.batchGenerateCoupons(couponId, count);
        return result ? Result.success() : Result.fail(500, "操作失败");
    }

    /**
     * 用户领取优惠券
     *
     * @param couponId 优惠券ID
     * @param userId   用户ID
     * @return 领取结果
     */
    @PostMapping("/receive")
    public Result receiveCoupon(@RequestParam Long couponId, @RequestParam Long userId) {
        boolean result = couponService.receiveCoupon(couponId, userId);
        return result ? Result.success() : Result.fail(500, "领取优惠券失败");
    }

    /**
     * 核销优惠券
     *
     * @param couponCode 券码
     * @param userId     用户ID
     * @param orderId    订单ID
     * @return 核销结果
     */
    @PostMapping("/use")
    public Result useCoupon(@RequestParam String couponCode, @RequestParam Long userId, @RequestParam Long orderId) {
        boolean result = couponService.useCoupon(couponCode, userId, orderId);
        return result ? Result.success() : Result.fail(500, "核销优惠券失败");
    }

}