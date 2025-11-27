package com.zyd.springbootserviceseedproject.entity.promotion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 促销规则实体类
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("promotion_rule")
public class PromotionRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 规则类型：1-满减，2-折扣，3-买赠，4-优惠券
     */
    private Integer ruleType;

    /**
     * 规则作用范围：1-商品，2-品类，3-品牌，4-店铺
     */
    private Integer scopeType;

    /**
     * 作用范围值（商品ID、品类ID等）
     */
    private String scopeValue;

    /**
     * 满减规则：满多少
     */
    private BigDecimal fullAmount;

    /**
     * 满减规则：减多少
     */
    private BigDecimal reduceAmount;

    /**
     * 折扣规则：折扣率（如85表示8.5折）
     */
    private Integer discountRate;

    /**
     * 买赠规则：买多少
     */
    private Integer buyQuantity;

    /**
     * 买赠规则：赠多少
     */
    private Integer giftQuantity;

    /**
     * 优惠券规则：优惠券金额
     */
    private BigDecimal couponAmount;

    /**
     * 最低消费金额
     */
    private BigDecimal minConsumeAmount;

    /**
     * 优先级（值越小优先级越高）
     */
    private Integer priority;

    /**
     * 是否互斥（1-是，0-否）
     */
    private Integer isExclusive;

    /**
     * 平台成本上限
     */
    private BigDecimal costLimit;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
}