package com.zyd.springbootserviceseedproject.controller.promotion;

import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionCalculationResult;
import com.zyd.springbootserviceseedproject.service.promotion.PromotionCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠计算Controller
 * @author zyd
 * @date 2024-05-20
 */
@RestController
@RequestMapping("/promotion/calculation")
public class PromotionCalculationController {

    @Autowired
    private PromotionCalculationService promotionCalculationService;

    /**
     * 计算购物车优惠
     * @param userId 用户ID
     * @param cartId 购物车ID
     * @param selectedCouponIds 用户选择的优惠券ID列表
     * @return 优惠计算结果
     */
    @PostMapping("/cart")
    public Result<PromotionCalculationResult> calculateCartDiscount(@RequestParam Long userId, @RequestParam Long cartId, @RequestBody List<Long> selectedCouponIds) {
        PromotionCalculationResult result = promotionCalculationService.calculateCartDiscount(userId, cartId, selectedCouponIds);
        return Result.success(result);
    }

    /**
     * 推荐最优优惠组合
     * @param userId 用户ID
     * @param cartId 购物车ID
     * @return 最优优惠计算结果
     */
    @GetMapping("/best")
    public Result<PromotionCalculationResult> recommendBestDiscount(@RequestParam Long userId, @RequestParam Long cartId) {
        PromotionCalculationResult result = promotionCalculationService.recommendBestDiscount(userId, cartId);
        return Result.success(result);
    }

    /**
     * 计算订单优惠
     * @param userId 用户ID
     * @param orderId 订单ID
     * @return 优惠计算结果
     */
    @GetMapping("/order")
    public Result<PromotionCalculationResult> calculateOrderDiscount(@RequestParam Long userId, @RequestParam Long orderId) {
        PromotionCalculationResult result = promotionCalculationService.calculateOrderDiscount(userId, orderId);
        return Result.success(result);
    }

    /**
     * 获取优惠计算历史
     * @param userId 用户ID
     * @param cartId 购物车ID
     * @return 优惠计算历史列表
     */
    @GetMapping("/history")
    public Result<List<PromotionCalculationResult>> getCalculationHistory(@RequestParam Long userId, @RequestParam Long cartId) {
        List<PromotionCalculationResult> list = promotionCalculationService.getCalculationHistory(userId, cartId);
        return Result.success(list);
    }
}