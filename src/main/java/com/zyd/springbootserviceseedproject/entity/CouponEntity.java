package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券表
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@TableName("coupon")
public class CouponEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动ID
     */
    @TableField("activity_id")
    private Long activityId;

    /**
     * 优惠券名称
     */
    @TableField("coupon_name")
    private String couponName;

    /**
     * 优惠券类型：1-满减券，2-折扣券，3-无门槛券
     */
    @TableField("coupon_type")
    private Integer couponType;

    /**
     * 总数量
     */
    @TableField("total_quantity")
    private Integer totalQuantity;

    /**
     * 已发放数量
     */
    @TableField("issued_quantity")
    private Integer issuedQuantity;

    /**
     * 已使用数量
     */
    @TableField("used_quantity")
    private Integer usedQuantity;

    /**
     * 优惠值：满减券为减免金额，折扣券为折扣比例（如8.5表示85折），无门槛券为减免金额
     */
    @TableField("discount_value")
    private BigDecimal discountValue;

    /**
     * 最低使用金额
     */
    @TableField("min_order_amount")
    private BigDecimal minOrderAmount;

    /**
     * 有效期开始时间
     */
    @TableField("valid_start_time")
    private LocalDateTime validStartTime;

    /**
     * 有效期结束时间
     */
    @TableField("valid_end_time")
    private LocalDateTime validEndTime;

    /**
     * 是否可叠加：1-是，0-否
     */
    @TableField("is_stackable")
    private Integer isStackable;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 手动添加getter和setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getIssuedQuantity() {
        return issuedQuantity;
    }

    public void setIssuedQuantity(Integer issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }

    public Integer getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(Integer usedQuantity) {
        this.usedQuantity = usedQuantity;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public LocalDateTime getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(LocalDateTime validStartTime) {
        this.validStartTime = validStartTime;
    }

    public LocalDateTime getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(LocalDateTime validEndTime) {
        this.validEndTime = validEndTime;
    }

    public Integer getIsStackable() {
        return isStackable;
    }

    public void setIsStackable(Integer isStackable) {
        this.isStackable = isStackable;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
