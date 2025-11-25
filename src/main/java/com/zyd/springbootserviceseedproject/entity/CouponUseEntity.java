package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券使用记录表
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@TableName("coupon_use")
public class CouponUseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠券发放记录ID
     */
    @TableField("coupon_issue_id")
    private Long couponIssueId;

    /**
     * 订单ID
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 实际使用金额
     */
    @TableField("use_amount")
    private BigDecimal useAmount;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
