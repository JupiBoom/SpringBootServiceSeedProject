package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 拼团记录实体类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("group_buy_record")
public class GroupBuyRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 拼团记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 拼团活动ID
     */
    private Long groupBuyId;

    /**
     * 拼团编号
     */
    private String groupBuyNo;

    /**
     * 团长用户ID
     */
    private Long leaderUserId;

    /**
     * 当前人数
     */
    private Integer currentNum;

    /**
     * 成团人数
     */
    private Integer requiredNum;

    /**
     * 拼团状态：0-待成团，1-成功，2-失败
     */
    private Integer status;

    /**
     * 拼团开始时间
     */
    private LocalDateTime startTime;

    /**
     * 拼团结束时间
     */
    private LocalDateTime endTime;

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