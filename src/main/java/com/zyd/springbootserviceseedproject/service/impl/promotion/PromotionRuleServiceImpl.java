package com.zyd.springbootserviceseedproject.service.impl.promotion;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionRule;
import com.zyd.springbootserviceseedproject.mapper.promotion.PromotionRuleMapper;
import com.zyd.springbootserviceseedproject.service.promotion.PromotionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 促销规则服务实现类
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Service
public class PromotionRuleServiceImpl extends ServiceImpl<PromotionRuleMapper, PromotionRule> implements PromotionRuleService {

    @Autowired
    private PromotionRuleMapper promotionRuleMapper;

    @Override
    public List<PromotionRule> getAllValidRules() {
        return promotionRuleMapper.findAllValidRules();
    }

    @Override
    public List<PromotionRule> getApplicableRulesByProductId(Long productId) {
        return promotionRuleMapper.findApplicableRulesByProductId(productId);
    }

    @Override
    public List<PromotionRule> getApplicableRulesByCategoryId(Long categoryId) {
        return promotionRuleMapper.findApplicableRulesByCategoryId(categoryId);
    }

    @Override
    public List<PromotionRule> getApplicableRulesByBrandId(Long brandId) {
        return promotionRuleMapper.findApplicableRulesByBrandId(brandId);
    }

    @Override
    public List<PromotionRule> getApplicableRulesByShopId(Long shopId) {
        return promotionRuleMapper.findApplicableRulesByShopId(shopId);
    }

    @Override
    public PromotionRule savePromotionRule(PromotionRule promotionRule) {
        promotionRule.setCreateTime(LocalDateTime.now());
        promotionRule.setUpdateTime(LocalDateTime.now());
        save(promotionRule);
        return promotionRule;
    }

    @Override
    public PromotionRule updatePromotionRule(PromotionRule promotionRule) {
        promotionRule.setUpdateTime(LocalDateTime.now());
        updateById(promotionRule);
        return promotionRule;
    }

    @Override
    public boolean deletePromotionRule(Long id) {
        return removeById(id);
    }
}