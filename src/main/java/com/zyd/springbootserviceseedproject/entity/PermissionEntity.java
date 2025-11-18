package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaoyudong
 * @version 1.0
 * @description 权限表
 * @date 2025/9/24 16:27
 */
@Data
@TableName("permission")
public class PermissionEntity implements Serializable {

    /**
     * 权限ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 父权限ID
     */
    private Integer parentId;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限关键字
     */
    private String permissionKey;

    /**
     * 权限类型（1菜单 2按钮）
     */
    private String permissionType;

    /**
     * 路由地址
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private String updateTime;
}
