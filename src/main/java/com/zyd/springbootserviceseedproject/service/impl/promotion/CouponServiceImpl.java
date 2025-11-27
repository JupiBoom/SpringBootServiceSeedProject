package com.zyd.springbootserviceseedproject.service.impl.promotion;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyd.springbootserviceseedproject.entity.promotion.Coupon;
import com.zyd.springbootserviceseedproject.entity.promotion.UserCoupon;
import com.zyd.springbootserviceseedproject.mapper.promotion.CouponMapper;
import com.zyd.springbootserviceseedproject.mapper.promotion.UserCouponMapper;
import com.zyd.springbootserviceseedproject.service.promotion.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 优惠券服务实现类
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Override
    public List<Coupon> getAllValidCoupons() {
        return couponMapper.findAllValidCoupons();
    }

    @Override
    public List<UserCoupon> getAvailableCouponsByUserId(Long userId) {
        return userCouponMapper.findByUserIdAndStatus(userId, 1);
    }

    @Override
    public List<Coupon> getApplicableCouponsByProductId(Long productId) {
        return couponMapper.findApplicableCouponsByProductId(productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean receiveCoupon(Long couponId, Long userId) {
        // 检查优惠券是否存在且有效
        Coupon coupon = getById(couponId);
        if (coupon == null || coupon.getStatus() != 1) {
            return false;
        }

        // 检查是否已达到发放总量
        if (coupon.getReceivedQuantity() >= coupon.getTotalQuantity()) {
            return false;
        }

        // 检查用户是否已达到限领数量
        Integer receivedCount = userCouponMapper.countByCouponIdAndUserId(couponId, userId);
        if (receivedCount >= coupon.getLimitPerUser()) {
            return false;
        }

        // 生成用户优惠券
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(couponId);
        userCoupon.setCouponCode(generateCouponCode());
        userCoupon.setReceiveTime(LocalDateTime.now());
        userCoupon.setUseStatus(1); // 未使用

        // 设置有效期
        if (coupon.getValidityType() == 1) {
            // 固定时间
            userCoupon.setStartTime(coupon.getStartTime());
            userCoupon.setEndTime(coupon.getEndTime());
        } else {
            // 领取后N天有效
            userCoupon.setStartTime(LocalDateTime.now());
            userCoupon.setEndTime(LocalDateTime.now().plusDays(coupon.getValidDays()));
        }

        userCoupon.setCreateTime(LocalDateTime.now());
        userCoupon.setUpdateTime(LocalDateTime.now());

        // 保存用户优惠券
        boolean saved = userCouponMapper.insert(userCoupon) > 0;
        if (saved) {
            // 更新优惠券已领取数量
            coupon.setReceivedQuantity(coupon.getReceivedQuantity() + 1);
            updateById(coupon);
        }

        return saved;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean useCoupon(Long userCouponId, Long orderId) {
        // 查询用户优惠券
        UserCoupon userCoupon = userCouponMapper.selectById(userCouponId);
        if (userCoupon == null || userCoupon.getUseStatus() != 1) {
            return false;
        }

        // 检查是否过期
        if (userCoupon.getEndTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        // 更新用户优惠券状态
        userCoupon.setUseStatus(2); // 已使用
        userCoupon.setUseTime(LocalDateTime.now());
        userCoupon.setOrderId(orderId);
        userCoupon.setUpdateTime(LocalDateTime.now());

        return userCouponMapper.updateById(userCoupon) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean refundCoupon(Long userCouponId) {
        // 查询用户优惠券
        UserCoupon userCoupon = userCouponMapper.selectById(userCouponId);
        if (userCoupon == null || userCoupon.getUseStatus() != 2) {
            return false;
        }

        // 检查是否已过期
        if (userCoupon.getEndTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        // 更新用户优惠券状态
        userCoupon.setUseStatus(1); // 未使用
        userCoupon.setUseTime(null);
        userCoupon.setOrderId(null);
        userCoupon.setUpdateTime(LocalDateTime.now());

        return userCouponMapper.updateById(userCoupon) > 0;
    }

    @Override
    public Coupon saveCoupon(Coupon coupon) {
        coupon.setCreateTime(LocalDateTime.now());
        coupon.setUpdateTime(LocalDateTime.now());
        coupon.setReceivedQuantity(0);
        save(coupon);
        return coupon;
    }

    @Override
    public Coupon updateCoupon(Coupon coupon) {
        coupon.setUpdateTime(LocalDateTime.now());
        updateById(coupon);
        return coupon;
    }

    @Override
    public boolean deleteCoupon(Long id) {
        return removeById(id);
    }

    /**
     * 生成优惠券编码
     */
    private String generateCouponCode() {
        return "COUPON_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}