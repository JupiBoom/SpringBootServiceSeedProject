package com.zyd.springbootserviceseedproject.service.promotion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionRule;

import java.util.List;

/**
 * 促销规则服务接口
 * 
 * @author zyd
 * @since 2024-01-01
 */
public interface PromotionRuleService extends IService<PromotionRule> {

    /**
     * 获取所有有效的促销规则
     * 
     * @return 有效的促销规则列表
     */
    List<PromotionRule> getAllValidRules();

    /**
     * 根据商品ID获取适用的促销规则
     * 
     * @param productId 商品ID
     * @return 适用的促销规则列表
     */
    List<PromotionRule> getApplicableRulesByProductId(Long productId);

    /**
     * 根据品类ID获取适用的促销规则
     * 
     * @param categoryId 品类ID
     * @return 适用的促销规则列表
     */
    List<PromotionRule> getApplicableRulesByCategoryId(Long categoryId);

    /**
     * 根据品牌ID获取适用的促销规则
     * 
     * @param brandId 品牌ID
     * @return 适用的促销规则列表
     */
    List<PromotionRule> getApplicableRulesByBrandId(Long brandId);

    /**
     * 根据店铺ID获取适用的促销规则
     * 
     * @param shopId 店铺ID
     * @return 适用的促销规则列表
     */
    List<PromotionRule> getApplicableRulesByShopId(Long shopId);

    /**
     * 保存促销规则
     * 
     * @param promotionRule 促销规则
     * @return 保存后的促销规则
     */
    PromotionRule savePromotionRule(PromotionRule promotionRule);

    /**
     * 更新促销规则
     * 
     * @param promotionRule 促销规则
     * @return 更新后的促销规则
     */
    PromotionRule updatePromotionRule(PromotionRule promotionRule);

    /**
     * 删除促销规则
     * 
     * @param id 促销规则ID
     * @return 删除结果
     */
    boolean deletePromotionRule(Long id);

    /**
     * 促销规则信息DTO
     */
    class PromotionRuleInfo {
        private Long id;
        private String ruleName;
        private Integer ruleType;
        private Integer scopeType;
        private String scopeValue;
        private Integer priority;
        private Integer isExclusive;
        
        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getRuleName() { return ruleName; }
        public void setRuleName(String ruleName) { this.ruleName = ruleName; }
        public Integer getRuleType() { return ruleType; }
        public void setRuleType(Integer ruleType) { this.ruleType = ruleType; }
        public Integer getScopeType() { return scopeType; }
        public void setScopeType(Integer scopeType) { this.scopeType = scopeType; }
        public String getScopeValue() { return scopeValue; }
        public void setScopeValue(String scopeValue) { this.scopeValue = scopeValue; }
        public Integer getPriority() { return priority; }
        public void setPriority(Integer priority) { this.priority = priority; }
        public Integer getIsExclusive() { return isExclusive; }
        public void setIsExclusive(Integer isExclusive) { this.isExclusive = isExclusive; }
    }
}