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
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `dept_name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `parent_id` int DEFAULT '0' COMMENT '父部门ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门信息表';

-- ----------------------------
-- Records of dept
-- ----------------------------
BEGIN;
INSERT INTO `dept` (`id`, `dept_name`, `parent_id`, `order_num`, `leader`, `phone`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, '总公司', 0, 1, '张三', '15812345678', '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
INSERT INTO `dept` (`id`, `dept_name`, `parent_id`, `order_num`, `leader`, `phone`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (2, '技术部', 1, 2, '李四', '13300000000', '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
INSERT INTO `dept` (`id`, `dept_name`, `parent_id`, `order_num`, `leader`, `phone`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (3, '销售部', 1, 3, '王五', '14588888888', '0', '0', 'admin', '2025-09-24 16:33:50', '', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;