package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 拼团活动实体类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("group_buy")
public class GroupBuyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 拼团ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 拼团价格
     */
    private BigDecimal groupPrice;

    /**
     * 成团人数
     */
    private Integer requiredNum;

    /**
     * 拼团有效期（分钟）
     */
    private Integer validMinutes;

    /**
     * 总库存
     */
    private Integer totalStock;

    /**
     * 已售数量
     */
    private Integer soldCount;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除标记：0-正常，1-删除
     */
    @TableLogic
    private Integer deleted;

}