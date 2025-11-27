package com.zyd.springbootserviceseedproject.mapper.promotion;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 促销规则Mapper接口
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Mapper
public interface PromotionRuleMapper extends BaseMapper<PromotionRule> {

    /**
     * 根据商品ID查询适用的促销规则
     * 
     * @param productId 商品ID
     * @return 适用的促销规则列表
     */
    List<PromotionRule> findApplicableRulesByProductId(@Param("productId") Long productId);

    /**
     * 根据品类ID查询适用的促销规则
     * 
     * @param categoryId 品类ID
     * @return 适用的促销规则列表
     */
    List<PromotionRule> findApplicableRulesByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 根据品牌ID查询适用的促销规则
     * 
     * @param brandId 品牌ID
     * @return 适用的促销规则列表
     */
    List<PromotionRule> findApplicableRulesByBrandId(@Param("brandId") Long brandId);

    /**
     * 根据店铺ID查询适用的促销规则
     * 
     * @param shopId 店铺ID
     * @return 适用的促销规则列表
     */
    List<PromotionRule> findApplicableRulesByShopId(@Param("shopId") Long shopId);

    /**
     * 查询所有有效的促销规则
     * 
     * @return 有效的促销规则列表
     */
    List<PromotionRule> findAllValidRules();
}