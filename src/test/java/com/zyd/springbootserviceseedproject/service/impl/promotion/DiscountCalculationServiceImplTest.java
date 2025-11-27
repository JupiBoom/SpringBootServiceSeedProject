package com.zyd.springbootserviceseedproject.service.impl.promotion;

import com.zyd.springbootserviceseedproject.entity.promotion.CartItem;
import com.zyd.springbootserviceseedproject.entity.promotion.DiscountResult;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionRule;
import com.zyd.springbootserviceseedproject.entity.promotion.UserCoupon;
import com.zyd.springbootserviceseedproject.service.promotion.DiscountCalculationService;
import com.zyd.springbootserviceseedproject.service.promotion.PromotionRuleService;
import com.zyd.springbootserviceseedproject.service.promotion.CouponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 优惠计算服务测试类
 * 
 * @author zyd
 * @since 2024-01-01
 */
@SpringBootTest
class DiscountCalculationServiceImplTest {

    @Autowired
    private DiscountCalculationService discountCalculationService;

    @Autowired
    private PromotionRuleService promotionRuleService;

    @Autowired
    private CouponService couponService;

    /**
     * 测试购物车优惠计算
     */
    @Test
    void testCalculateDiscountForCart() {
        // 创建测试购物车商品
        List<CartItem> cartItems = new ArrayList<>();
        CartItem item1 = new CartItem();
        item1.setProductId(1L);
        item1.setProductName("测试商品1");
        item1.setProductCode("TEST001");
        item1.setCategoryId(1L);
        item1.setBrandId(1L);
        item1.setShopId(1L);
        item1.setQuantity(2);
        item1.setUnitPrice(new BigDecimal(100));
        item1.setTotalPrice(new BigDecimal(200));
        item1.setIsPromotion(1);
        item1.setStockQuantity(100);
        item1.setStatus(1);
        cartItems.add(item1);

        CartItem item2 = new CartItem();
        item2.setProductId(2L);
        item2.setProductName("测试商品2");
        item2.setProductCode("TEST002");
        item2.setCategoryId(1L);
        item2.setBrandId(1L);
        item2.setShopId(1L);
        item2.setQuantity(1);
        item2.setUnitPrice(new BigDecimal(50));
        item2.setTotalPrice(new BigDecimal(50));
        item2.setIsPromotion(0);
        item2.setStockQuantity(100);
        item2.setStatus(1);
        cartItems.add(item2);

        // 创建测试促销规则
        PromotionRule promotionRule = new PromotionRule();
        promotionRule.setRuleName("满200减50");
        promotionRule.setRuleType(1); // 满减
        promotionRule.setRuleValue(new BigDecimal(200));
        promotionRule.setDiscountValue(new BigDecimal(50));
        promotionRule.setApplicableRange(2); // 全店
        promotionRule.setShopId(1L);
        promotionRule.setPriority(1);
        promotionRule.setStartTime(LocalDateTime.now().minusDays(1));
        promotionRule.setEndTime(LocalDateTime.now().plusDays(1));
        promotionRule.setStatus(1);
        promotionRule.setMaxDiscountPerOrder(new BigDecimal(100));
        promotionRule.setMaxDiscountPerUserPerDay(new BigDecimal(200));
        promotionRule.setIsActive(1);
        // promotionRule.setIsStackable(1); // 移除该方法调用，因为原代码中没有这个方法
        promotionRuleService.savePromotionRule(promotionRule);

        // 执行优惠计算
        DiscountResult result = discountCalculationService.calculateDiscountForCart(1L, cartItems);

        // 验证结果
        assertNotNull(result);
        assertEquals(new BigDecimal(250), result.getOriginalTotalAmount()); // 修正方法名
        assertEquals(new BigDecimal(200), result.getDiscountedTotalAmount()); // 修正方法名
        assertEquals(new BigDecimal(50), result.getTotalDiscountAmount());
        assertFalse(result.getDiscountDetailList().isEmpty());
    }

    /**
     * 测试最优优惠推荐
     */
    @Test
    void testRecommendOptimalDiscount() {
        // 创建测试购物车商品
        List<CartItem> cartItems = new ArrayList<>();
        CartItem item1 = new CartItem();
        item1.setProductId(1L);
        item1.setProductName("测试商品1");
        item1.setProductCode("TEST001");
        item1.setCategoryId(1L);
        item1.setBrandId(1L);
        item1.setShopId(1L);
        item1.setQuantity(1);
        item1.setUnitPrice(new BigDecimal(300));
        item1.setTotalPrice(new BigDecimal(300));
        item1.setIsPromotion(1);
        item1.setStockQuantity(100);
        item1.setStatus(1);
        cartItems.add(item1);

        // 创建多个促销规则
        PromotionRule rule1 = new PromotionRule();
        rule1.setRuleName("满300减50");
        rule1.setRuleType(1);
        rule1.setRuleValue(new BigDecimal(300));
        rule1.setDiscountValue(new BigDecimal(50));
        rule1.setApplicableRange(2);
        rule1.setShopId(1L);
        rule1.setPriority(1);
        rule1.setStartTime(LocalDateTime.now().minusDays(1));
        rule1.setEndTime(LocalDateTime.now().plusDays(1));
        rule1.setStatus(1);
        rule1.setMaxDiscountPerOrder(new BigDecimal(100));
        rule1.setMaxDiscountPerUserPerDay(new BigDecimal(200));
        rule1.setIsActive(1);
        // rule1.setIsStackable(1); // 移除该方法调用，因为原代码中没有这个方法
        promotionRuleService.savePromotionRule(rule1);

        PromotionRule rule2 = new PromotionRule();
        rule2.setRuleName("8折优惠");
        rule2.setRuleType(2);
        rule2.setRuleValue(new BigDecimal(0.8));
        rule2.setDiscountValue(null);
        rule2.setApplicableRange(2);
        rule2.setShopId(1L);
        rule2.setPriority(2);
        rule2.setStartTime(LocalDateTime.now().minusDays(1));
        rule2.setEndTime(LocalDateTime.now().plusDays(1));
        rule2.setStatus(1);
        rule2.setMaxDiscountPerOrder(new BigDecimal(100));
        rule2.setMaxDiscountPerUserPerDay(new BigDecimal(200));
        rule2.setIsActive(1);
        // rule2.setIsStackable(1); // 移除该方法调用，因为原代码中没有这个方法
        promotionRuleService.savePromotionRule(rule2);

        // 执行最优优惠推荐
        DiscountResult result = discountCalculationService.recommendOptimalDiscount(1L, cartItems);

        // 验证结果（应该选择8折，优惠60元，比满减50元更优惠）
        assertNotNull(result);
        assertEquals(new BigDecimal(300), result.getOriginalTotalAmount()); // 修正方法名
        assertEquals(new BigDecimal(240), result.getDiscountedTotalAmount()); // 修正方法名
        assertEquals(new BigDecimal(60), result.getTotalDiscountAmount());
        assertTrue(result.getIsOptimal() == 1); // 修正参数类型
    }
}