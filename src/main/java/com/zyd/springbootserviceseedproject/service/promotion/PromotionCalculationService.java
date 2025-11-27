package com.zyd.springbootserviceseedproject.service.promotion;

import com.zyd.springbootserviceseedproject.entity.promotion.PromotionCalculationResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠计算Service
 * @author zyd
 * @date 2024-05-20
 */
public interface PromotionCalculationService {

    /**
     * 计算购物车优惠
     * @param userId 用户ID
     * @param cartId 购物车ID
     * @param selectedCouponIds 用户选择的优惠券ID列表
     * @return 优惠计算结果
     */
    PromotionCalculationResult calculateCartDiscount(Long userId, Long cartId, List<Long> selectedCouponIds);

    /**
     * 推荐最优优惠组合
     * @param userId 用户ID
     * @param cartId 购物车ID
     * @return 最优优惠计算结果
     */
    PromotionCalculationResult recommendBestDiscount(Long userId, Long cartId);

    /**
     * 计算订单优惠
     * @param userId 用户ID
     * @param orderId 订单ID
     * @return 优惠计算结果
     */
    PromotionCalculationResult calculateOrderDiscount(Long userId, Long orderId);

    /**
     * 获取优惠计算历史
     * @param userId 用户ID
     * @param cartId 购物车ID
     * @return 优惠计算历史列表
     */
    List<PromotionCalculationResult> getCalculationHistory(Long userId, Long cartId);
}