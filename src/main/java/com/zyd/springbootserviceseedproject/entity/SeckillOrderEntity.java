package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 秒杀订单表
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@TableName("seckill_order")
public class SeckillOrderEntity implements Serializable {
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
     * 秒杀商品ID
     */
    @TableField("seckill_product_id")
    private Long seckillProductId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 商品ID
     */
    @TableField("product_id")
    private Long productId;

    /**
     * 购买数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 秒杀价格
     */
    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    /**
     * 订单总金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 订单状态：1-待支付，2-已支付，3-已取消，4-已完成
     */
    @TableField("status")
    private Integer status;

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
