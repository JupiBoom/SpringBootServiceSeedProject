package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动表
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@TableName("activity")
public class ActivityEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动名称
     */
    @TableField("activity_name")
    private String activityName;

    /**
     * 活动类型：1-优惠券，2-秒杀，3-拼团
     */
    @TableField("activity_type")
    private Integer activityType;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 活动状态：1-待开始，2-进行中，3-已结束，4-已关闭
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
