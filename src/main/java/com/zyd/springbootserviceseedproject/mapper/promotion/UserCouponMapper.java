package com.zyd.springbootserviceseedproject.mapper.promotion;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.promotion.UserCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户优惠券Mapper接口
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Mapper
public interface UserCouponMapper extends BaseMapper<UserCoupon> {

    /**
     * 根据用户ID和状态查询用户优惠券
     * 
     * @param userId 用户ID
     * @param useStatus 使用状态
     * @return 用户优惠券列表
     */
    List<UserCoupon> findByUserIdAndStatus(@Param("userId") Long userId, @Param("useStatus") Integer useStatus);

    /**
     * 根据优惠券ID和用户ID查询用户优惠券
     * 
     * @param couponId 优惠券ID
     * @param userId 用户ID
     * @return 用户优惠券列表
     */
    List<UserCoupon> findByCouponIdAndUserId(@Param("couponId") Long couponId, @Param("userId") Long userId);

    /**
     * 查询用户已领取的优惠券数量
     * 
     * @param couponId 优惠券ID
     * @param userId 用户ID
     * @return 已领取数量
     */
    Integer countByCouponIdAndUserId(@Param("couponId") Long couponId, @Param("userId") Long userId);

    /**
     * 根据订单ID查询使用的优惠券
     * 
     * @param orderId 订单ID
     * @return 用户优惠券
     */
    UserCoupon findByOrderId(@Param("orderId") Long orderId);
}