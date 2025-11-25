package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 拼团成员表
 *
 * @author zyd
 * @since 2024-05-20
 */
@Data
@TableName("group_buy_member")
public class GroupBuyMemberEntity implements Serializable {
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
     * 是否为团长：1-是，0-否
     */
    @TableField("is_leader")
    private Integer isLeader;

    /**
     * 加入时间
     */
    @TableField("join_time")
    private LocalDateTime joinTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
