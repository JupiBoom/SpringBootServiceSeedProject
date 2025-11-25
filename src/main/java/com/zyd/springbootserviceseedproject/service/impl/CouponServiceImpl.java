package com.zyd.springbootserviceseedproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyd.springbootserviceseedproject.entity.CouponEntity;
import com.zyd.springbootserviceseedproject.entity.CouponIssueEntity;
import com.zyd.springbootserviceseedproject.mapper.CouponMapper;
import com.zyd.springbootserviceseedproject.mapper.CouponIssueMapper;
import com.zyd.springbootserviceseedproject.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 优惠券表 Service 实现类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponEntity> implements CouponService {

    @Autowired
    private CouponIssueMapper couponIssueMapper;

    @Override
    public List<CouponEntity> listByActivityId(Long activityId) {
        LambdaQueryWrapper<CouponEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CouponEntity::getActivityId, activityId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<String> generateCouponCodes(Long couponId, Integer quantity) {
        List<String> couponCodes = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            String couponCode = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
            couponCodes.add(couponCode);
        }
        return couponCodes;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CouponIssueEntity issueCouponToUser(Long couponId, Long userId) {
        // 检查优惠券是否存在且还有剩余数量
        CouponEntity coupon = baseMapper.selectById(couponId);
        if (coupon == null || coupon.getIssuedQuantity() >= coupon.getTotalQuantity()) {
            return null;
        }

        // 检查用户是否已经领取过该优惠券
        LambdaQueryWrapper<CouponIssueEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CouponIssueEntity::getCouponId, couponId)
                .eq(CouponIssueEntity::getUserId, userId);
        if (couponIssueMapper.selectCount(queryWrapper) > 0) {
            return null;
        }

        // 生成优惠券码
        String couponCode = UUID.randomUUID().toString().replace("-", "").substring(0, 20);

        // 创建优惠券发放记录
        CouponIssueEntity couponIssue = new CouponIssueEntity();
        couponIssue.setCouponId(couponId);
        couponIssue.setUserId(userId);
        couponIssue.setCouponCode(couponCode);
        couponIssue.setStatus(1); // 1-未使用
        couponIssue.setIssueTime(LocalDateTime.now());
        couponIssueMapper.insert(couponIssue);

        // 更新优惠券已发放数量
        coupon.setIssuedQuantity(coupon.getIssuedQuantity() + 1);
        baseMapper.updateById(coupon);

        return couponIssue;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchIssueCouponToUsers(Long couponId, List<Long> userIds) {
        int successCount = 0;
        for (Long userId : userIds) {
            try {
                CouponIssueEntity couponIssue = issueCouponToUser(couponId, userId);
                if (couponIssue != null) {
                    successCount++;
                }
            } catch (Exception e) {
                // 记录日志，继续处理下一个用户
                log.error("发放优惠券给用户{}失败: {}", userId, e.getMessage());
            }
        }
        return successCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CouponIssueEntity userClaimCoupon(Long couponId, Long userId) {
        return issueCouponToUser(couponId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean useCoupon(Long couponIssueId, Long orderId) {
        // 检查优惠券发放记录是否存在且未使用
        CouponIssueEntity couponIssue = couponIssueMapper.selectById(couponIssueId);
        if (couponIssue == null || couponIssue.getStatus() != 1) {
            return false;
        }

        // 检查优惠券是否已过期
        CouponEntity coupon = baseMapper.selectById(couponIssue.getCouponId());
        if (coupon == null || LocalDateTime.now().isAfter(coupon.getValidEndTime())) {
            return false;
        }

        // 更新优惠券发放记录状态为已使用
        couponIssue.setStatus(2); // 2-已使用
        couponIssue.setUseTime(LocalDateTime.now());
        couponIssueMapper.updateById(couponIssue);

        // 更新优惠券已使用数量
        coupon.setUsedQuantity(coupon.getUsedQuantity() + 1);
        baseMapper.updateById(coupon);

        // TODO: 创建优惠券使用记录

        return true;
    }

    @Override
    public List<CouponIssueEntity> listUserAvailableCoupons(Long userId) {
        LambdaQueryWrapper<CouponIssueEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CouponIssueEntity::getUserId, userId)
                .eq(CouponIssueEntity::getStatus, 1) // 1-未使用
                .lt(CouponIssueEntity::getIssueTime, LocalDateTime.now());
        // TODO: 还需要关联优惠券表检查有效期
        return couponIssueMapper.selectList(queryWrapper);
    }

    @Override
    public List<CouponIssueEntity> listUserUsedCoupons(Long userId) {
        LambdaQueryWrapper<CouponIssueEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CouponIssueEntity::getUserId, userId)
                .eq(CouponIssueEntity::getStatus, 2); // 2-已使用
        return couponIssueMapper.selectList(queryWrapper);
    }

    @Override
    public List<CouponIssueEntity> listUserExpiredCoupons(Long userId) {
        LambdaQueryWrapper<CouponIssueEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CouponIssueEntity::getUserId, userId)
                .eq(CouponIssueEntity::getStatus, 3); // 3-已过期
        return couponIssueMapper.selectList(queryWrapper);
    }
}
