package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 拼团表
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@TableName("group_buy")
public class GroupBuyEntity implements Serializable {
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
     * 商品ID
     */
    @TableField("product_id")
    private Long productId;

    /**
     * 拼团价格
     */
    @TableField("group_buy_price")
    private BigDecimal groupBuyPrice;

    /**
     * 最小成团人数
     */
    @TableField("min_group_size")
    private Integer minGroupSize;

    /**
     * 最大成团人数
     */
    @TableField("max_group_size")
    private Integer maxGroupSize;

    /**
     * 拼团有效期（分钟）
     */
    @TableField("group_validity_minutes")
    private Integer groupValidityMinutes;

    /**
     * 总库存
     */
    @TableField("total_stock")
    private Integer totalStock;

    /**
     * 可用库存
     */
    @TableField("available_stock")
    private Integer availableStock;

    /**
     * 已售库存
     */
    @TableField("sold_stock")
    private Integer soldStock;

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
}
