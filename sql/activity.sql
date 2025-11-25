-- 活动表
CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_name` varchar(100) NOT NULL COMMENT '活动名称',
  `activity_type` tinyint(4) NOT NULL COMMENT '活动类型：1-优惠券，2-秒杀，3-拼团',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '活动状态：1-待开始，2-进行中，3-已结束，4-已关闭',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_activity_type` (`activity_type`),
  KEY `idx_status` (`status`),
  KEY `idx_start_end_time` (`start_time`,`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';
