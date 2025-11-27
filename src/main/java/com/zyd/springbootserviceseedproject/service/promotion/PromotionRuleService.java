package com.zyd.springbootserviceseedproject.service.promotion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionRule;

import java.util.List;

/**
 * 促销规则Service
 * @author zyd
 * @date 2024-05-20
 */
public interface PromotionRuleService extends IService<PromotionRule> {

    /**
     * 获取可用的促销规则列表
     * @param userId 用户ID
     * @param cartId 购物车ID
     * @return 可用的促销规则列表
     */
    List<PromotionRule> getAvailableRules(Long userId, Long cartId);

    /**
     * 根据规则类型获取规则列表
     * @param type 规则类型
     * @return 规则列表
     */
    List<PromotionRule> getRulesByType(Integer type);

    /**
     * 保存促销规则
     * @param rule 促销规则
     * @return 保存后的促销规则
     */
    PromotionRule saveRule(PromotionRule rule);

    /**
     * 删除促销规则
     * @param id 规则ID
     * @return 是否删除成功
     */
    boolean deleteRule(Long id);
}