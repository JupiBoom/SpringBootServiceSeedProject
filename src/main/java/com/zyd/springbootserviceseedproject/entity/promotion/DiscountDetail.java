package com.zyd.springbootserviceseedproject.entity.promotion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠明细实体类
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DiscountDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 优惠ID
     */
    private Long discountId;

    /**
     * 优惠类型：1-满减，2-折扣，3-买赠，4-优惠券
     */
    private Integer discountType;

    /**
     * 优惠名称
     */
    private String discountName;

    /**
     * 适用商品ID列表（逗号分隔）
     */
    private String applicableProductIds;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 平台承担成本
     */
    private BigDecimal platformCost;

    /**
     * 优惠规则ID（如果是促销规则）
     */
    private Long promotionRuleId;

    /**
     * 优惠券ID（如果是优惠券）
     */
    private Long couponId;

    /**
     * 用户优惠券ID（如果是用户使用的优惠券）
     */
    private Long userCouponId;

    /**
     * 描述信息
     */
    private String description;
}