package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 拼团参与记录实体类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("group_buy_participant")
public class GroupBuyParticipantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 参与记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 拼团记录ID
     */
    private Long groupBuyRecordId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 是否为团长：0-否，1-是
     */
    private Integer isLeader;

    /**
     * 参与时间
     */
    private LocalDateTime joinTime;

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