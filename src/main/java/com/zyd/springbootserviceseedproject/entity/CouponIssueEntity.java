package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 优惠券发放记录表
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@TableName("coupon_issue")
public class CouponIssueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠券ID
     */
    @TableField("coupon_id")
    private Long couponId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 优惠券码
     */
    @TableField("coupon_code")
    private String couponCode;

    /**
     * 状态：1-未使用，2-已使用，3-已过期
     */
    @TableField("status")
    private Integer status;

    /**
     * 发放时间
     */
    @TableField("issue_time")
    private LocalDateTime issueTime;

    /**
     * 使用时间
     */
    @TableField("use_time")
    private LocalDateTime useTime;

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

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(LocalDateTime issueTime) {
        this.issueTime = issueTime;
    }

    public LocalDateTime getUseTime() {
        return useTime;
    }

    public void setUseTime(LocalDateTime useTime) {
        this.useTime = useTime;
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
