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
 * 优惠审计日志实体类
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("discount_audit_log")
public class DiscountAuditLog implements Serializable {

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
     * 订单ID
     */
    private Long orderId;

    /**
     * 优惠计算结果ID
     */
    private Long discountResultId;

    /**
     * 操作类型：1-优惠计算，2-优惠应用，3-优惠撤销
     */
    private Integer operationType;

    /**
     * 原始数据（JSON格式存储）
     */
    private String originalData;

    /**
     * 计算过程（JSON格式存储）
     */
    private String calculationProcess;

    /**
     * 计算结果（JSON格式存储）
     */
    private String calculationResult;

    /**
     * 错误信息（如果计算失败）
     */
    private String errorMessage;

    /**
     * 操作人员ID
     */
    private Long operatorId;

    /**
     * 操作人员名称
     */
    private String operatorName;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}