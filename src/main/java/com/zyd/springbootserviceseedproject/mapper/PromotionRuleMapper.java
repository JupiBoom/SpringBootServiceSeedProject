package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * 促销规则Mapper
 * @author zyd
 * @date 2024-05-20
 */
@Mapper
public interface PromotionRuleMapper extends BaseMapper<PromotionRule> {
}