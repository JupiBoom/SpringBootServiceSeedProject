package com.zyd.springbootserviceseedproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.CouponEntity;
import com.zyd.springbootserviceseedproject.entity.CouponIssueEntity;

import java.util.List;

/**
 * 优惠券表 Service 接口
 *
 * @author zyd
 * @since 2024-05-20
 */
public interface CouponService extends IService<CouponEntity> {

    /**
     * 根据活动ID查询优惠券列表
     * @param activityId 活动ID
     * @return 优惠券列表
     */
    List<CouponEntity> listByActivityId(Long activityId);

    /**
     * 批量生成优惠券码
     * @param couponId 优惠券ID
     * @param quantity 生成数量
     * @return 优惠券码列表
     */
    List<String> generateCouponCodes(Long couponId, Integer quantity);

    /**
     * 发放优惠券给用户
     * @param couponId 优惠券ID
     * @param userId 用户ID
     * @return 优惠券发放记录
     */
    CouponIssueEntity issueCouponToUser(Long couponId, Long userId);

    /**
     * 批量发放优惠券给用户
     * @param couponId 优惠券ID
     * @param userIds 用户ID列表
     * @return 发放成功数量
     */
    int batchIssueCouponToUsers(Long couponId, List<Long> userIds);

    /**
     * 用户领取优惠券
     * @param couponId 优惠券ID
     * @param userId 用户ID
     * @return 优惠券发放记录
     */
    CouponIssueEntity userClaimCoupon(Long couponId, Long userId);

    /**
     * 核销优惠券
     * @param couponIssueId 优惠券发放记录ID
     * @param orderId 订单ID
     * @return true-核销成功，false-核销失败
     */
    boolean useCoupon(Long couponIssueId, Long orderId);

    /**
     * 查询用户可用优惠券列表
     * @param userId 用户ID
     * @return 可用优惠券列表
     */
    List<CouponIssueEntity> listUserAvailableCoupons(Long userId);

    /**
     * 查询用户已使用优惠券列表
     * @param userId 用户ID
     * @return 已使用优惠券列表
     */
    List<CouponIssueEntity> listUserUsedCoupons(Long userId);

    /**
     * 查询用户已过期优惠券列表
     * @param userId 用户ID
     * @return 已过期优惠券列表
     */
    List<CouponIssueEntity> listUserExpiredCoupons(Long userId);
}
