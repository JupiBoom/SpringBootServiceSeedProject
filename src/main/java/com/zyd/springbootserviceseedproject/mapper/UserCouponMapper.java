package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.promotion.UserCoupon;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户优惠券Mapper
 * @author zyd
 * @date 2024-05-20
 */
@Mapper
public interface UserCouponMapper extends BaseMapper<UserCoupon> {
}