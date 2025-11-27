package com.zyd.springbootserviceseedproject.entity.promotion;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券规则
 * @author zyd
 * @date 2024-05-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("coupon_rule")
public class CouponRule extends PromotionRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 优惠券类型：1-满减券，2-折扣券，3-立减券
     */
    private Integer couponType;

    /**
     * 优惠券面值
     */
    private BigDecimal couponValue;

    /**
     * 每人限领数量
     */
    private Integer limitPerUser;

    /**
     * 总发放数量
     */
    private Integer totalQuantity;

    /**
     * 已发放数量
     */
    private Integer issuedQuantity;

    /**
     * 领取开始时间
     */
    private LocalDateTime receiveStartTime;

    /**
     * 领取结束时间
     */
    private LocalDateTime receiveEndTime;

    /**
     * 有效期天数
     */
    private Integer validDays;
}