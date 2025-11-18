package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhaoyudong
 * @version 1.0
 * @description 权限Mapper
 * @date 2025/9/24 16:35
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    /**
     * 根据角色ID查询权限列表
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Select("SELECT p.* FROM permission p INNER JOIN role_permission rp ON p.id = rp.permission_id WHERE rp.role_id = #{roleId} AND p.status = '0' AND p.del_flag = '0'")
    List<PermissionEntity> selectPermissionsByRoleId(Integer roleId);

    /**
     * 根据用户ID查询权限列表
     * @param userId 用户ID
     * @return 权限列表
     */
    @Select("SELECT p.* FROM permission p INNER JOIN role_permission rp ON p.id = rp.permission_id INNER JOIN user_role ur ON rp.role_id = ur.role_id WHERE ur.user_id = #{userId} AND p.status = '0' AND p.del_flag = '0'")
    List<PermissionEntity> selectPermissionsByUserId(Integer userId);
}
