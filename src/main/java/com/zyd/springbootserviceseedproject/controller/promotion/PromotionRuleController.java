package com.zyd.springbootserviceseedproject.controller.promotion;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionRule;
import com.zyd.springbootserviceseedproject.service.promotion.PromotionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 促销规则管理控制器
 * 
 * @author zyd
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/promotion/rule")
public class PromotionRuleController {

    @Autowired
    private PromotionRuleService promotionRuleService;

    /**
     * 获取所有促销规则（分页）
     * 
     * @param page 页码
     * @param size 每页大小
     * @return 促销规则分页列表
     */
    @GetMapping("/list")
    public Page<PromotionRule> getPromotionRuleList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return promotionRuleService.page(new Page<>(page, size));
    }

    /**
     * 获取所有有效促销规则
     * 
     * @return 有效促销规则列表
     */
    @GetMapping("/valid")
    public List<PromotionRule> getAllValidRules() {
        return promotionRuleService.getAllValidRules();
    }

    /**
     * 根据ID获取促销规则
     * 
     * @param id 促销规则ID
     * @return 促销规则详情
     */
    @GetMapping("/{id}")
    public PromotionRule getPromotionRuleById(@PathVariable Long id) {
        return promotionRuleService.getById(id);
    }

    /**
     * 创建促销规则
     * 
     * @param promotionRule 促销规则信息
     * @return 创建后的促销规则
     */
    @PostMapping("/create")
    public PromotionRule createPromotionRule(@RequestBody PromotionRule promotionRule) {
        return promotionRuleService.savePromotionRule(promotionRule);
    }

    /**
     * 更新促销规则
     * 
     * @param id            促销规则ID
     * @param promotionRule 促销规则信息
     * @return 更新后的促销规则
     */
    @PutMapping("/{id}")
    public PromotionRule updatePromotionRule(@PathVariable Long id, @RequestBody PromotionRule promotionRule) {
        promotionRule.setId(id);
        return promotionRuleService.updatePromotionRule(promotionRule);
    }

    /**
     * 删除促销规则
     * 
     * @param id 促销规则ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean deletePromotionRule(@PathVariable Long id) {
        return promotionRuleService.deletePromotionRule(id);
    }

    /**
     * 根据商品ID获取适用促销规则
     * 
     * @param productId 商品ID
     * @return 适用促销规则列表
     */
    @GetMapping("/applicable/product/{productId}")
    public List<PromotionRule> getApplicableRulesByProductId(@PathVariable Long productId) {
        return promotionRuleService.getApplicableRulesByProductId(productId);
    }

    /**
     * 根据品类ID获取适用促销规则
     * 
     * @param categoryId 品类ID
     * @return 适用促销规则列表
     */
    @GetMapping("/applicable/category/{categoryId}")
    public List<PromotionRule> getApplicableRulesByCategoryId(@PathVariable Long categoryId) {
        return promotionRuleService.getApplicableRulesByCategoryId(categoryId);
    }

    /**
     * 根据品牌ID获取适用促销规则
     * 
     * @param brandId 品牌ID
     * @return 适用促销规则列表
     */
    @GetMapping("/applicable/brand/{brandId}")
    public List<PromotionRule> getApplicableRulesByBrandId(@PathVariable Long brandId) {
        return promotionRuleService.getApplicableRulesByBrandId(brandId);
    }

    /**
     * 根据店铺ID获取适用促销规则
     * 
     * @param shopId 店铺ID
     * @return 适用促销规则列表
     */
    @GetMapping("/applicable/shop/{shopId}")
    public List<PromotionRule> getApplicableRulesByShopId(@PathVariable Long shopId) {
        return promotionRuleService.getApplicableRulesByShopId(shopId);
    }
}