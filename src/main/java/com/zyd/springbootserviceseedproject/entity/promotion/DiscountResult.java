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
import java.util.List;

/**
 * 优惠计算结果实体类
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("discount_result")
public class DiscountResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 购物车ID或订单ID
     */
    private Long businessId;

    /**
     * 业务类型：1-购物车计算，2-订单计算
     */
    private Integer businessType;

    /**
     * 原始总价
     */
    private BigDecimal originalTotalAmount;

    /**
     * 优惠后总价
     */
    private BigDecimal discountedTotalAmount;

    /**
     * 总优惠金额
     */
    private BigDecimal totalDiscountAmount;

    /**
     * 平台承担成本
     */
    private BigDecimal platformCost;

    /**
     * 是否为最优优惠组合：1-是，0-否
     */
    private Integer isOptimal;

    /**
     * 优惠明细（JSON格式存储）
     */
    private String discountDetails;

    /**
     * 使用的优惠券ID列表（逗号分隔）
     */
    private String usedCouponIds;

    /**
     * 使用的促销规则ID列表（逗号分隔）
     */
    private String usedPromotionRuleIds;

    /**
     * 计算时间
     */
    private LocalDateTime calculationTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // 非数据库字段，用于内存计算
    private transient List<DiscountDetail> discountDetailList;
}