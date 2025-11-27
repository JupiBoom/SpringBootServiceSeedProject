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
 * 促销规则基类
 * @author zyd
 * @date 2024-05-20
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
    private String name;

    /**
     * 规则类型：1-满减，2-折扣，3-优惠券，4-买赠
     */
    private Integer type;

    /**
     * 规则状态：0-停用，1-启用
     */
    private Integer status;

    /**
     * 优先级：数字越小优先级越高
     */
    private Integer priority;

    /**
     * 是否互斥：0-否，1-是
     */
    private Integer exclusive;

    /**
     * 作用范围：1-商品，2-品类，3-品牌，4-店铺
     */
    private Integer scope;

    /**
     * 作用范围值：商品ID、品类ID、品牌ID或店铺ID
     */
    private String scopeValue;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 最低消费金额
     */
    private BigDecimal minAmount;

    /**
     * 优惠上限
     */
    private BigDecimal maxDiscount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;
}