-- 优惠券表
CREATE TABLE `coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `coupon_name` varchar(100) NOT NULL COMMENT '优惠券名称',
  `coupon_type` tinyint(4) NOT NULL COMMENT '优惠券类型：1-满减券，2-折扣券，3-无门槛券',
  `total_quantity` int(11) NOT NULL COMMENT '总数量',
  `issued_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '已发放数量',
  `used_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '已使用数量',
  `discount_value` decimal(10,2) NOT NULL COMMENT '优惠值：满减券为减免金额，折扣券为折扣比例（如8.5表示85折），无门槛券为减免金额',
  `min_order_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最低使用金额',
  `valid_start_time` datetime NOT NULL COMMENT '有效期开始时间',
  `valid_end_time` datetime NOT NULL COMMENT '有效期结束时间',
  `is_stackable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可叠加：1-是，0-否',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_coupon_type` (`coupon_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- 优惠券发放记录表
CREATE TABLE `coupon_issue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `coupon_id` bigint(20) NOT NULL COMMENT '优惠券ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `coupon_code` varchar(50) NOT NULL COMMENT '优惠券码',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1-未使用，2-已使用，3-已过期',
  `issue_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发放时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_coupon_code` (`coupon_code`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券发放记录表';

-- 优惠券使用记录表
CREATE TABLE `coupon_use` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `coupon_issue_id` bigint(20) NOT NULL COMMENT '优惠券发放记录ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `use_amount` decimal(10,2) NOT NULL COMMENT '实际使用金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_coupon_issue_id` (`coupon_issue_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券使用记录表';
