package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.CouponUseEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券使用记录表 Mapper 接口
 *
 * @author zyd
 * @since 2024-05-20
 */
@Mapper
public interface CouponUseMapper extends BaseMapper<CouponUseEntity> {
}
