package com.zyd.springbootserviceseedproject.service.impl.promotion;

import com.zyd.springbootserviceseedproject.entity.promotion.CartItem;
import com.zyd.springbootserviceseedproject.entity.promotion.UserCoupon;
import com.zyd.springbootserviceseedproject.entity.promotion.DiscountResult;
import com.zyd.springbootserviceseedproject.entity.promotion.DiscountDetail;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionRule;
import com.zyd.springbootserviceseedproject.service.promotion.DiscountCalculationService;
import com.zyd.springbootserviceseedproject.service.promotion.PromotionRuleService;
import com.zyd.springbootserviceseedproject.service.promotion.CouponService;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠计算服务实现类
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Service
public class DiscountCalculationServiceImpl implements DiscountCalculationService {

    @Autowired
    private KieSession kieSession;

    @Autowired
    private PromotionRuleService promotionRuleService;

    @Autowired
    private CouponService couponService;

    @Override
    public DiscountResult calculateDiscountForCart(Long userId, List<CartItem> cartItems) {
        // 初始化优惠计算结果
        DiscountResult discountResult = initDiscountResult(cartItems, userId, 1);
        
        // 获取适用的促销规则
        List<PromotionRule> applicableRules = getApplicablePromotionRules(cartItems)
                .stream()
                .map(info -> promotionRuleService.getById(info.getId()))
                .collect(Collectors.toList());
        
        // 获取用户可用的优惠券
        List<UserCoupon> availableCoupons = couponService.getAvailableCouponsByUserId(userId);
        
        // 执行规则引擎计算
        executeRulesEngine(discountResult, cartItems, applicableRules, availableCoupons);
        
        return discountResult;
    }

    @Override
    public DiscountResult calculateDiscountWithCoupons(Long userId, List<CartItem> cartItems, List<Long> userCouponIds) {
        // 初始化优惠计算结果
        DiscountResult discountResult = initDiscountResult(cartItems, userId, 1);
        
        // 获取适用的促销规则
        List<PromotionRule> applicableRules = getApplicablePromotionRules(cartItems)
                .stream()
                .map(info -> promotionRuleService.getById(info.getId()))
                .collect(Collectors.toList());
        
        // 获取用户选择的优惠券
        List<UserCoupon> selectedCoupons = userCouponIds.stream()
                .map(couponService::getUserCouponById)
                .collect(Collectors.toList());
        
        // 执行规则引擎计算
        executeRulesEngine(discountResult, cartItems, applicableRules, selectedCoupons);
        
        return discountResult;
    }

    /**
     * 使用指定优惠券计算优惠（内部方法）
     */
    private DiscountResult calculateDiscountWithSelectedCoupons(List<CartItem> cartItems, List<UserCoupon> selectedCoupons) {
        // 初始化优惠计算结果
        DiscountResult discountResult = initDiscountResult(cartItems, null, 1);
        
        // 获取适用的促销规则
        List<PromotionRule> applicableRules = getApplicablePromotionRules(cartItems)
                .stream()
                .map(info -> promotionRuleService.getById(info.getId()))
                .collect(Collectors.toList());
        
        // 执行规则引擎计算
        executeRulesEngine(discountResult, cartItems, applicableRules, selectedCoupons);
        
        return discountResult;
    }

    @Override
    public DiscountResult recommendOptimalDiscount(Long userId, List<CartItem> cartItems) {
        // 获取用户可用的优惠券
        List<UserCoupon> availableCoupons = couponService.getAvailableCouponsByUserId(userId);
        
        // 生成所有可能的优惠券组合
        List<List<UserCoupon>> couponCombinations = generateCouponCombinations(availableCoupons);
        
        // 计算每种组合的优惠结果
        List<DiscountResult> allResults = new ArrayList<>();
        for (List<UserCoupon> combination : couponCombinations) {
            DiscountResult result = calculateDiscountWithSelectedCoupons(cartItems, combination);
            allResults.add(result);
        }
        
        // 选择最优结果
        DiscountResult optimalResult = null;
        BigDecimal maxDiscount = BigDecimal.ZERO;
        
        for (DiscountResult result : allResults) {
            if (result.getTotalDiscountAmount().compareTo(maxDiscount) > 0) {
                maxDiscount = result.getTotalDiscountAmount();
                optimalResult = result;
            }
        }
        
        if (optimalResult != null) {
            optimalResult.setIsOptimal(1);
        }
        
        return optimalResult;
    }

    @Override
    public DiscountResult calculateDiscountForOrder(Long orderId) {
        // TODO: 根据订单ID获取订单商品信息，然后计算优惠
        return null;
    }

    @Override
    public List<UserCoupon> getAvailableCouponsForUser(Long userId, List<CartItem> cartItems) {
        return couponService.getAvailableCouponsByUserId(userId);
    }

    @Override
    public List<Object> getApplicablePromotions(List<CartItem> cartItems) {
        List<PromotionRuleService.PromotionRuleInfo> applicableRules = new ArrayList<>();
        
        for (CartItem cartItem : cartItems) {
            // 获取商品级别的促销规则
            List<PromotionRule> productRules = promotionRuleService.getApplicableRulesByProductId(cartItem.getProductId());
            applicableRules.addAll(convertToRuleInfo(productRules));
            
            // 获取品类级别的促销规则
            List<PromotionRule> categoryRules = promotionRuleService.getApplicableRulesByCategoryId(cartItem.getCategoryId());
            applicableRules.addAll(convertToRuleInfo(categoryRules));
            
            // 获取品牌级别的促销规则
            List<PromotionRule> brandRules = promotionRuleService.getApplicableRulesByBrandId(cartItem.getBrandId());
            applicableRules.addAll(convertToRuleInfo(brandRules));
            
            // 获取店铺级别的促销规则
            List<PromotionRule> shopRules = promotionRuleService.getApplicableRulesByShopId(cartItem.getShopId());
            applicableRules.addAll(convertToRuleInfo(shopRules));
        }
        
        // 去重并按优先级排序
        return new ArrayList<>(applicableRules.stream()
                .distinct()
                .sorted((r1, r2) -> r1.getPriority().compareTo(r2.getPriority()))
                .collect(Collectors.toList()));
    }

    /**
     * 初始化优惠计算结果
     */
    private DiscountResult initDiscountResult(List<CartItem> cartItems, Long userId, Integer businessType) {
        DiscountResult discountResult = new DiscountResult();
        discountResult.setUserId(userId);
        discountResult.setBusinessType(businessType);
        discountResult.setOriginalTotalAmount(calculateOriginalTotal(cartItems));
        discountResult.setDiscountedTotalAmount(discountResult.getOriginalTotalAmount());
        discountResult.setTotalDiscountAmount(BigDecimal.ZERO);
        discountResult.setPlatformCost(BigDecimal.ZERO);
        discountResult.setIsOptimal(0);
        discountResult.setDiscountDetails("");
        discountResult.setUsedCouponIds("");
        discountResult.setUsedPromotionRuleIds("");
        discountResult.setCalculationTime(LocalDateTime.now());
        discountResult.setDiscountDetailList(new ArrayList<>());
        
        return discountResult;
    }

    /**
     * 计算原始总价
     */
    private BigDecimal calculateOriginalTotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 执行规则引擎计算
     */
    private void executeRulesEngine(DiscountResult discountResult, List<CartItem> cartItems, 
                                   List<PromotionRule> promotionRules, List<UserCoupon> userCoupons) {
        try {
            // 插入事实对象
            kieSession.insert(discountResult);
            cartItems.forEach(kieSession::insert);
            promotionRules.forEach(kieSession::insert);
            userCoupons.forEach(kieSession::insert);
            
            // 执行规则
            int firedRules = kieSession.fireAllRules();
            
            // 清理会话
            kieSession.dispose();
        } catch (Exception e) {
            // 处理规则执行异常
            e.printStackTrace();
        }
    }

    /**
     * 生成优惠券组合
     */
    private List<List<UserCoupon>> generateCouponCombinations(List<UserCoupon> coupons) {
        List<List<UserCoupon>> combinations = new ArrayList<>();
        combinations.add(new ArrayList<>()); // 空组合
        
        for (UserCoupon coupon : coupons) {
            int size = combinations.size();
            for (int i = 0; i < size; i++) {
                List<UserCoupon> newCombination = new ArrayList<>(combinations.get(i));
                newCombination.add(coupon);
                combinations.add(newCombination);
            }
        }
        
        return combinations;
    }

    /**
     * 转换为PromotionRuleInfo
     */
    private List<PromotionRuleService.PromotionRuleInfo> convertToRuleInfo(List<PromotionRule> rules) {
        return rules.stream().map(rule -> {
            PromotionRuleService.PromotionRuleInfo info = new PromotionRuleService.PromotionRuleInfo();
            info.setId(rule.getId());
            info.setRuleName(rule.getRuleName());
            info.setRuleType(rule.getRuleType());
            info.setScopeType(rule.getScopeType());
            info.setScopeValue(rule.getScopeValue());
            info.setPriority(rule.getPriority());
            info.setIsExclusive(rule.getIsExclusive());
            return info;
        }).collect(Collectors.toList());
    }
}