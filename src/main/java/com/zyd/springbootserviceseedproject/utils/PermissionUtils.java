package com.zyd.springbootserviceseedproject.utils;

import com.zyd.springbootserviceseedproject.entity.PermissionEntity;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限工具类
 * 处理权限继承和冲突解决
 */
public class PermissionUtils {

    /**
     * 获取所有父权限ID列表
     * @param permission 权限
     * @param allPermissions 所有权限列表
     * @return 父权限ID列表
     */
    public static List<Integer> getAllParentPermissionIds(PermissionEntity permission, List<PermissionEntity> allPermissions) {
        List<Integer> parentIds = new ArrayList<>();
        if (permission.getParentId() != null && permission.getParentId() != 0) {
            parentIds.add(permission.getParentId());
            // 递归获取父权限的父权限
            PermissionEntity parentPermission = findPermissionById(permission.getParentId(), allPermissions);
            if (parentPermission != null) {
                parentIds.addAll(getAllParentPermissionIds(parentPermission, allPermissions));
            }
        }
        return parentIds;
    }

    /**
     * 根据权限ID查找权限
     * @param permissionId 权限ID
     * @param permissions 权限列表
     * @return 权限对象
     */
    public static PermissionEntity findPermissionById(Integer permissionId, List<PermissionEntity> permissions) {
        for (PermissionEntity permission : permissions) {
            if (permission.getId().equals(permissionId)) {
                return permission;
            }
        }
        return null;
    }

    /**
     * 处理权限继承，获取所有继承的权限
     * @param permissions 原始权限列表
     * @param allPermissions 系统所有权限
     * @return 包含继承权限的列表
     */
    public static Set<PermissionEntity> processPermissionInheritance(List<PermissionEntity> permissions, List<PermissionEntity> allPermissions) {
        Set<PermissionEntity> result = new HashSet<>(permissions);

        for (PermissionEntity permission : permissions) {
            // 获取当前权限的所有父权限
            List<Integer> parentIds = getAllParentPermissionIds(permission, allPermissions);
            // 添加所有父权限到结果集中
            for (Integer parentId : parentIds) {
                PermissionEntity parentPermission = findPermissionById(parentId, allPermissions);
                if (parentPermission != null) {
                    result.add(parentPermission);
                }
            }
        }

        return result;
    }

    /**
     * 解决权限冲突
     * 规则：子权限覆盖父权限
     * @param permissions 权限列表
     * @return 解决冲突后的权限列表
     */
    public static List<PermissionEntity> resolvePermissionConflicts(List<PermissionEntity> permissions) {
        if (CollectionUtils.isEmpty(permissions)) {
            return new ArrayList<>();
        }

        // 按照权限层级降序排序（子权限在前面）
        permissions.sort((p1, p2) -> {
            int level1 = getPermissionLevel(p1, permissions);
            int level2 = getPermissionLevel(p2, permissions);
            return level2 - level1;
        });

        Set<PermissionEntity> result = new HashSet<>();
        for (PermissionEntity permission : permissions) {
            // 检查是否已经存在父权限
            boolean hasParent = false;
            for (PermissionEntity existingPermission : result) {
                if (isParentPermission(existingPermission, permission, permissions)) {
                    hasParent = true;
                    break;
                }
            }
            if (!hasParent) {
                result.add(permission);
            }
        }

        return new ArrayList<>(result);
    }

    /**
     * 获取权限的层级
     * @param permission 权限
     * @param allPermissions 所有权限
     * @return 权限层级（根权限为1，子权限逐级加1）
     */
    public static int getPermissionLevel(PermissionEntity permission, List<PermissionEntity> allPermissions) {
        if (permission.getParentId() == null || permission.getParentId() == 0) {
            return 1;
        }
        PermissionEntity parent = findPermissionById(permission.getParentId(), allPermissions);
        if (parent == null) {
            return 1;
        }
        return getPermissionLevel(parent, allPermissions) + 1;
    }

    /**
     * 判断是否是父权限
     * @param parent 父权限候选
     * @param child 子权限候选
     * @param allPermissions 所有权限
     * @return 是否是父权限
     */
    public static boolean isParentPermission(PermissionEntity parent, PermissionEntity child, List<PermissionEntity> allPermissions) {
        if (child.getParentId() == null || child.getParentId() == 0) {
            return false;
        }
        if (parent.getId().equals(child.getParentId())) {
            return true;
        }
        PermissionEntity childParent = findPermissionById(child.getParentId(), allPermissions);
        if (childParent == null) {
            return false;
        }
        return isParentPermission(parent, childParent, allPermissions);
    }

    /**
     * 获取权限的所有子权限
     * @param permission 权限
     * @param allPermissions 所有权限
     * @return 子权限列表
     */
    public static List<PermissionEntity> getAllChildPermissions(PermissionEntity permission, List<PermissionEntity> allPermissions) {
        List<PermissionEntity> children = new ArrayList<>();
        for (PermissionEntity p : allPermissions) {
            if (permission.getId().equals(p.getParentId())) {
                children.add(p);
                // 递归获取子权限的子权限
                children.addAll(getAllChildPermissions(p, allPermissions));
            }
        }
        return children;
    }
}
