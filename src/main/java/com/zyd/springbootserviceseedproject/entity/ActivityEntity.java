package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动基础实体类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("activity")
public class ActivityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动类型：1-优惠券，2-秒杀，3-拼团
     */
    private Integer type;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 活动状态：0-待开始，1-进行中，2-已结束，3-已关闭
     */
    private Integer status;

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