package com.zyd.springbootserviceseedproject.mapper.promotion;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.promotion.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠券Mapper接口
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {

    /**
     * 查询所有有效的优惠券
     * 
     * @return 有效的优惠券列表
     */
    List<Coupon> findAllValidCoupons();

    /**
     * 根据用户ID查询可使用的优惠券
     * 
     * @param userId 用户ID
     * @return 用户可使用的优惠券列表
     */
    List<Coupon> findAvailableCouponsByUserId(@Param("userId") Long userId);

    /**
     * 根据商品ID查询适用的优惠券
     * 
     * @param productId 商品ID
     * @return 适用的优惠券列表
     */
    List<Coupon> findApplicableCouponsByProductId(@Param("productId") Long productId);

    /**
     * 查询指定类型的优惠券
     * 
     * @param couponType 优惠券类型
     * @return 优惠券列表
     */
    List<Coupon> findCouponsByType(@Param("couponType") Integer couponType);
}