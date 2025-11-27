package com.zyd.springbootserviceseedproject.entity.promotion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户优惠券实体类
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_coupon")
public class UserCoupon implements Serializable {

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
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 优惠券编码
     */
    private String couponCode;

    /**
     * 领取时间
     */
    private LocalDateTime receiveTime;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 使用状态：1-未使用，2-已使用，3-已过期
     */
    private Integer useStatus;

    /**
     * 使用时间
     */
    private LocalDateTime useTime;

    /**
     * 订单ID（使用后关联）
     */
    private Long orderId;

    /**
     * 实际优惠金额
     */
    private java.math.BigDecimal actualDiscountAmount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}