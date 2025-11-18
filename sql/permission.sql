/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : master

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 24/09/2025 17:16:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `parent_id` int DEFAULT '0' COMMENT '父权限ID',
  `permission_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称',
  `permission_key` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限关键字',
  `permission_type` char(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限类型（1菜单 2按钮）',
  `url` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由地址',
  `icon` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单图标',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='权限信息表';

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` (`id`, `parent_id`, `permission_name`, `permission_key`, `permission_type`, `url`, `icon`, `order_num`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, 0, '系统管理', 'system:manage', '1', '/system', 'system', 1, '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
INSERT INTO `permission` (`id`, `parent_id`, `permission_name`, `permission_key`, `permission_type`, `url`, `icon`, `order_num`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (2, 1, '用户管理', 'system:user:manage', '1', '/system/user', 'user', 2, '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
INSERT INTO `permission` (`id`, `parent_id`, `permission_name`, `permission_key`, `permission_type`, `url`, `icon`, `order_num`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (3, 2, '用户列表', 'system:user:list', '2', NULL, NULL, 3, '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
INSERT INTO `permission` (`id`, `parent_id`, `permission_name`, `permission_key`, `permission_type`, `url`, `icon`, `order_num`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (4, 2, '用户添加', 'system:user:add', '2', NULL, NULL, 4, '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
INSERT INTO `permission` (`id`, `parent_id`, `permission_name`, `permission_key`, `permission_type`, `url`, `icon`, `order_num`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (5, 2, '用户修改', 'system:user:edit', '2', NULL, NULL, 5, '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
INSERT INTO `permission` (`id`, `parent_id`, `permission_name`, `permission_key`, `permission_type`, `url`, `icon`, `order_num`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (6, 2, '用户删除', 'system:user:delete', '2', NULL, NULL, 6, '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
INSERT INTO `permission` (`id`, `parent_id`, `permission_name`, `permission_key`, `permission_type`, `url`, `icon`, `order_num`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (7, 1, '角色管理', 'system:role:manage', '1', '/system/role', 'role', 7, '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
INSERT INTO `permission` (`id`, `parent_id`, `permission_name`, `permission_key`, `permission_type`, `url`, `icon`, `order_num`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (8, 1, '部门管理', 'system:dept:manage', '1', '/system/dept', 'dept', 8, '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
INSERT INTO `permission` (`id`, `parent_id`, `permission_name`, `permission_key`, `permission_type`, `url`, `icon`, `order_num`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (9, 1, '日志管理', 'system:log:manage', '1', '/system/log', 'log', 9, '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` int NOT NULL COMMENT '角色ID',
  `permission_id` int NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色和权限关联表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 1);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 2);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 3);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 4);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 5);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 6);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 7);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 8);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (1, 9);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (2, 1);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (2, 2);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (2, 3);
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (3, 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;