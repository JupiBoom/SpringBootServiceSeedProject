package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionCalculationResult;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠计算结果Mapper
 * @author zyd
 * @date 2024-05-20
 */
@Mapper
public interface PromotionCalculationResultMapper extends BaseMapper<PromotionCalculationResult> {
}