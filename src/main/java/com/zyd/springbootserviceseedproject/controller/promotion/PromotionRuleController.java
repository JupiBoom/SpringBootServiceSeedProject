package com.zyd.springbootserviceseedproject.controller.promotion;

import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionRule;
import com.zyd.springbootserviceseedproject.service.promotion.PromotionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 促销规则Controller
 * @author zyd
 * @date 2024-05-20
 */
@RestController
@RequestMapping("/promotion/rule")
public class PromotionRuleController {

    @Autowired
    private PromotionRuleService promotionRuleService;

    /**
     * 获取所有促销规则
     * @return 促销规则列表
     */
    @GetMapping("/list")
    public Result<List<PromotionRule>> getPromotionRuleList() {
        List<PromotionRule> list = promotionRuleService.list();
        return Result.success(list);
    }

    /**
     * 根据ID获取促销规则
     * @param id 规则ID
     * @return 促销规则
     */
    @GetMapping("/{id}")
    public Result<PromotionRule> getPromotionRuleById(@PathVariable Long id) {
        PromotionRule rule = promotionRuleService.getById(id);
        return Result.success(rule);
    }

    /**
     * 保存促销规则
     * @param rule 促销规则
     * @return 保存后的促销规则
     */
    @PostMapping("/save")
    public Result<PromotionRule> savePromotionRule(@RequestBody PromotionRule rule) {
        PromotionRule savedRule = promotionRuleService.saveRule(rule);
        return Result.success(savedRule);
    }

    /**
     * 更新促销规则
     * @param rule 促销规则
     * @return 更新后的促销规则
     */
    @PutMapping("/update")
    public Result<PromotionRule> updatePromotionRule(@RequestBody PromotionRule rule) {
        promotionRuleService.updateById(rule);
        return Result.success(rule);
    }

    /**
     * 删除促销规则
     * @param id 规则ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deletePromotionRule(@PathVariable Long id) {
        boolean deleted = promotionRuleService.deleteRule(id);
        return Result.success(deleted);
    }

    /**
     * 获取可用的促销规则
     * @param userId 用户ID
     * @param cartId 购物车ID
     * @return 可用的促销规则列表
     */
    @GetMapping("/available")
    public Result<List<PromotionRule>> getAvailableRules(@RequestParam Long userId, @RequestParam Long cartId) {
        List<PromotionRule> list = promotionRuleService.getAvailableRules(userId, cartId);
        return Result.success(list);
    }
}