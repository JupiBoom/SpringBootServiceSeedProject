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
 * 优惠券实体类
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 优惠券类型：1-满减券，2-折扣券，3-无门槛券
     */
    private Integer couponType;

    /**
     * 优惠券金额（满减券和无门槛券使用）
     */
    private BigDecimal couponAmount;

    /**
     * 折扣率（折扣券使用，如85表示8.5折）
     */
    private Integer discountRate;

    /**
     * 最低消费金额（满减券使用）
     */
    private BigDecimal minConsumeAmount;

    /**
     * 发放总量
     */
    private Integer totalQuantity;

    /**
     * 已领取数量
     */
    private Integer receivedQuantity;

    /**
     * 每人限领数量
     */
    private Integer limitPerUser;

    /**
     * 有效期类型：1-固定时间，2-领取后N天有效
     */
    private Integer validityType;

    /**
     * 开始时间（固定时间类型使用）
     */
    private LocalDateTime startTime;

    /**
     * 结束时间（固定时间类型使用）
     */
    private LocalDateTime endTime;

    /**
     * 有效天数（领取后N天有效类型使用）
     */
    private Integer validDays;

    /**
     * 适用范围：1-全场通用，2-指定商品，3-指定品类，4-指定品牌，5-指定店铺
     */
    private Integer applyScope;

    /**
     * 适用范围值（商品ID、品类ID等，多个用逗号分隔）
     */
    private String applyScopeValue;

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