-- 拼团表
CREATE TABLE `group_buy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `group_buy_price` decimal(10,2) NOT NULL COMMENT '拼团价格',
  `min_group_size` int(11) NOT NULL COMMENT '最小成团人数',
  `max_group_size` int(11) NOT NULL COMMENT '最大成团人数',
  `group_validity_minutes` int(11) NOT NULL COMMENT '拼团有效期（分钟）',
  `total_stock` int(11) NOT NULL COMMENT '总库存',
  `available_stock` int(11) NOT NULL COMMENT '可用库存',
  `sold_stock` int(11) NOT NULL DEFAULT '0' COMMENT '已售库存',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_activity_product` (`activity_id`,`product_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团表';

-- 拼团成员表
CREATE TABLE `group_buy_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_buy_id` bigint(20) NOT NULL COMMENT '拼团ID',
  `group_no` varchar(50) NOT NULL COMMENT '拼团编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `is_leader` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为团长：1-是，0-否',
  `join_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_group_buy_id` (`group_buy_id`),
  KEY `idx_group_no` (`group_no`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团成员表';

-- 拼团订单表
CREATE TABLE `group_buy_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_buy_id` bigint(20) NOT NULL COMMENT '拼团ID',
  `group_no` varchar(50) NOT NULL COMMENT '拼团编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '购买数量',
  `group_buy_price` decimal(10,2) NOT NULL COMMENT '拼团价格',
  `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态：1-待成团，2-已成团，3-已取消，4-已完成，5-拼团失败',
  `refund_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '退款状态：0-未退款，1-退款中，2-已退款',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_group_buy_id` (`group_buy_id`),
  KEY `idx_group_no` (`group_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团订单表';
