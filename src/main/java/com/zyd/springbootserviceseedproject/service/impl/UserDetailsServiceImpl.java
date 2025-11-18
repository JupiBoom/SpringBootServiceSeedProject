package com.zyd.springbootserviceseedproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zyd.springbootserviceseedproject.bean.LoginUser;
import com.zyd.springbootserviceseedproject.entity.PermissionEntity;
import com.zyd.springbootserviceseedproject.entity.RoleEntity;
import com.zyd.springbootserviceseedproject.entity.UserEntity;
import com.zyd.springbootserviceseedproject.mapper.PermissionMapper;
import com.zyd.springbootserviceseedproject.mapper.UserMapper;
import com.zyd.springbootserviceseedproject.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhaoyudong
 * @version 1.0
 * @description security登录用户服务实现
 * @date 2025/9/24 11:20
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getNo, username);
        UserEntity user = userMapper.selectOne(queryWrapper);
        //如果没有查询到用户就抛出异常
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或者密码错误");
        }
        //根据用户ID查询角色列表
        List<RoleEntity> roles = userRoleMapper.selectRolesByUserId(user.getId());

        //获取所有角色ID
        List<Integer> roleIds = roles.stream()
                .map(RoleEntity::getId)
                .collect(Collectors.toList());

        //根据角色ID查询权限列表
        List<PermissionEntity> permissionEntities = new ArrayList<>();
        for (Integer roleId : roleIds) {
            List<PermissionEntity> permissions = permissionMapper.selectPermissionsByRoleId(roleId);
            permissionEntities.addAll(permissions);
        }

        //去重权限列表
        Set<PermissionEntity> permissionSet = new HashSet<>(permissionEntities);

        //把权限信息转换为权限字符串列表
        List<String> permissions = permissionSet.stream()
                .map(permission -> {
                    //如果是按钮权限，直接返回权限关键字
                    if ("2".equals(permission.getPermissionType())) {
                        return permission.getPermissionKey();
                    }
                    //如果是菜单权限，返回菜单URL作为权限标识
                    return permission.getUrl();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        //添加角色信息到权限列表
        List<String> rolePermissions = roles.stream()
                .map(role -> "ROLE_" + role.getRoleKey().toUpperCase())
                .collect(Collectors.toList());
        permissions.addAll(rolePermissions);

        //把数据封装成UserDetails返回
        return new LoginUser(user, permissions);
    }
}
