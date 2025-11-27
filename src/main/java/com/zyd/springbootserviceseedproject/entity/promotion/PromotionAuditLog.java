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
 * 优惠审计日志
 * @author zyd
 * @date 2024-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("promotion_audit_log")
public class PromotionAuditLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作类型：1-优惠计算，2-优惠券领取，3-优惠券使用，4-规则配置变更
     */
    private Integer operationType;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 规则ID
     */
    private Long ruleId;

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 操作内容
     */
    private String operationContent;

    /**
     * 操作结果：0-失败，1-成功
     */
    private Integer operationResult;

    /**
     * 失败原因
     */
    private String failureReason;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * IP地址
     */
    private String ipAddress;
}