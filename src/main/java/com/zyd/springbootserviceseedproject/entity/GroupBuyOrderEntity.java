package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 拼团订单表
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@TableName("group_buy_order")
public class GroupBuyOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 拼团ID
     */
    @TableField("group_buy_id")
    private Long groupBuyId;

    /**
     * 拼团编号
     */
    @TableField("group_no")
    private String groupNo;

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
     * 拼团价格
     */
    @TableField("group_buy_price")
    private BigDecimal groupBuyPrice;

    /**
     * 订单总金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 订单状态：1-待成团，2-已成团，3-已取消，4-已完成，5-拼团失败
     */
    @TableField("status")
    private Integer status;

    /**
     * 退款状态：0-未退款，1-退款中，2-已退款
     */
    @TableField("refund_status")
    private Integer refundStatus;

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
