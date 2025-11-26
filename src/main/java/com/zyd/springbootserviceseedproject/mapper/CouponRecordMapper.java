package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.CouponRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券发放记录Mapper接口
 *
 * @author zyd
 * @since 2024-05-20
 */
@Mapper
public interface CouponRecordMapper extends BaseMapper<CouponRecordEntity> {

}