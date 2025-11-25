package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 秒杀商品表
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@TableName("seckill_product")
public class SeckillProductEntity implements Serializable {
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
     * 秒杀价格
     */
    @TableField("seckill_price")
    private BigDecimal seckillPrice;

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
     * 每人限购数量
     */
    @TableField("limit_per_user")
    private Integer limitPerUser;

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
