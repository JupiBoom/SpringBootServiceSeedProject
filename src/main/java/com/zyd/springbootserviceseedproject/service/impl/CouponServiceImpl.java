package com.zyd.springbootserviceseedproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyd.springbootserviceseedproject.entity.CouponEntity;
import com.zyd.springbootserviceseedproject.entity.CouponRecordEntity;
import com.zyd.springbootserviceseedproject.mapper.CouponMapper;
import com.zyd.springbootserviceseedproject.service.CouponRecordService;
import com.zyd.springbootserviceseedproject.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 优惠券Service实现类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponEntity> implements CouponService {

    @Autowired
    private CouponRecordService couponRecordService;

    /**
     * 批量生成优惠券
     *
     * @param couponId 优惠券ID
     * @param count    生成数量
     * @return 生成结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean batchGenerateCoupons(Long couponId, Integer count) {
        CouponEntity coupon = this.getById(couponId);
        if (coupon == null) {
            throw new RuntimeException("优惠券不存在");
        }

        // 生成优惠券记录
        List<CouponRecordEntity> couponRecords = IntStream.range(0, count)
                .mapToObj(i -> {
                    CouponRecordEntity record = new CouponRecordEntity();
                    record.setCouponId(couponId);
                    record.setCouponCode(UUID.randomUUID().toString().replace("-", ""));
                    record.setStatus(0); // 未使用
                    record.setReceiveTime(LocalDateTime.now());
                    return record;
                })
                .collect(Collectors.toList());

        // 保存优惠券记录
        boolean success = couponRecordService.saveBatch(couponRecords);
        if (success) {
            // 更新优惠券已领取数量
            coupon.setReceivedCount(coupon.getReceivedCount() + count);
            this.updateById(coupon);
        }

        return success;
    }

    /**
     * 用户领取优惠券
     *
     * @param couponId 优惠券ID
     * @param userId   用户ID
     * @return 领取结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean receiveCoupon(Long couponId, Long userId) {
        CouponEntity coupon = this.getById(couponId);
        if (coupon == null) {
            throw new RuntimeException("优惠券不存在");
        }

        // 检查优惠券是否还有库存
        if (coupon.getReceivedCount() >= coupon.getTotalCount()) {
            throw new RuntimeException("优惠券已领完");
        }

        // 检查用户是否已经领取过该优惠券
        List<CouponRecordEntity> existingRecords = couponRecordService.list(
                new QueryWrapper<CouponRecordEntity>()
                        .eq("coupon_id", couponId)
                        .eq("user_id", userId)
        );
        if (!existingRecords.isEmpty()) {
            throw new RuntimeException("您已经领取过该优惠券");
        }

        // 生成优惠券记录
        CouponRecordEntity record = new CouponRecordEntity();
        record.setCouponId(couponId);
        record.setUserId(userId);
        record.setCouponCode(UUID.randomUUID().toString().replace("-", ""));
        record.setStatus(0); // 未使用
        record.setReceiveTime(LocalDateTime.now());

        // 保存优惠券记录
        boolean success = couponRecordService.save(record);
        if (success) {
            // 更新优惠券已领取数量
            coupon.setReceivedCount(coupon.getReceivedCount() + 1);
            this.updateById(coupon);
        }

        return success;
    }

    /**
     * 核销优惠券
     *
     * @param couponCode 券码
     * @param userId     用户ID
     * @param orderId    订单ID
     * @return 核销结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean useCoupon(String couponCode, Long userId, Long orderId) {
        CouponRecordEntity record = couponRecordService.getOne(
                new QueryWrapper<CouponRecordEntity>()
                        .eq("coupon_code", couponCode)
                        .eq("user_id", userId)
        );
        if (record == null) {
            throw new RuntimeException("优惠券不存在");
        }

        // 检查优惠券是否已经使用
        if (record.getStatus() == 1) {
            throw new RuntimeException("优惠券已经使用");
        }

        // 检查优惠券是否已经过期
        CouponEntity coupon = this.getById(record.getCouponId());
        if (coupon == null) {
            throw new RuntimeException("优惠券不存在");
        }
        if (LocalDateTime.now().isAfter(coupon.getValidEndTime())) {
            throw new RuntimeException("优惠券已经过期");
        }

        // 更新优惠券记录状态
        record.setStatus(1); // 已使用
        record.setUseTime(LocalDateTime.now());
        record.setOrderId(orderId);
        boolean success = couponRecordService.updateById(record);
        if (success) {
            // 更新优惠券已使用数量
            coupon.setUsedCount(coupon.getUsedCount() + 1);
            this.updateById(coupon);
        }

        return success;
    }

}