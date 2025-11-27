-- 促销规则表
CREATE TABLE `promotion_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL COMMENT '规则名称',
  `type` int(11) NOT NULL COMMENT '规则类型：1-满减，2-折扣，3-优惠券，4-买赠',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '规则状态：0-停用，1-启用',
  `priority` int(11) NOT NULL DEFAULT '100' COMMENT '优先级：数字越小优先级越高',
  `exclusive` int(11) NOT NULL DEFAULT '0' COMMENT '是否互斥：0-否，1-是',
  `scope` int(11) NOT NULL COMMENT '作用范围：1-商品，2-品类，3-品牌，4-店铺',
  `scope_value` varchar(255) NOT NULL COMMENT '作用范围值：商品ID、品类ID、品牌ID或店铺ID',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `min_amount` decimal(10,2) DEFAULT NULL COMMENT '最低消费金额',
  `max_discount` decimal(10,2) DEFAULT NULL COMMENT '优惠上限',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_type` (`type`),
  KEY `idx_status` (`status`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='促销规则表';

-- 满减规则表
CREATE TABLE `full_reduction_rule` (
  `id` bigint(20) NOT NULL COMMENT '主键ID，关联promotion_rule.id',
  `full_amount` decimal(10,2) NOT NULL COMMENT '满金额',
  `reduce_amount` decimal(10,2) NOT NULL COMMENT '减金额',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_full_reduction_rule_id` FOREIGN KEY (`id`) REFERENCES `promotion_rule` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='满减规则表';

-- 折扣规则表
CREATE TABLE `discount_rule` (
  `id` bigint(20) NOT NULL COMMENT '主键ID，关联promotion_rule.id',
  `discount_value` decimal(5,2) NOT NULL COMMENT '折扣值：如8.5表示85折',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_discount_rule_id` FOREIGN KEY (`id`) REFERENCES `promotion_rule` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='折扣规则表';

-- 优惠券规则表
CREATE TABLE `coupon_rule` (
  `id` bigint(20) NOT NULL COMMENT '主键ID，关联promotion_rule.id',
  `coupon_type` int(11) NOT NULL COMMENT '优惠券类型：1-满减券，2-折扣券，3-立减券',
  `coupon_value` decimal(10,2) NOT NULL COMMENT '优惠券面值',
  `limit_per_user` int(11) NOT NULL DEFAULT '1' COMMENT '每人限领数量',
  `total_quantity` int(11) NOT NULL COMMENT '总发放数量',
  `issued_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '已发放数量',
  `receive_start_time` datetime NOT NULL COMMENT '领取开始时间',
  `receive_end_time` datetime NOT NULL COMMENT '领取结束时间',
  `valid_days` int(11) NOT NULL COMMENT '有效期天数',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_coupon_rule_id` FOREIGN KEY (`id`) REFERENCES `promotion_rule` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券规则表';

-- 买赠规则表
CREATE TABLE `gift_rule` (
  `id` bigint(20) NOT NULL COMMENT '主键ID，关联promotion_rule.id',
  `buy_quantity` int(11) NOT NULL COMMENT '购买数量',
  `gift_product_id` bigint(20) NOT NULL COMMENT '赠送商品ID',
  `gift_product_name` varchar(255) NOT NULL COMMENT '赠送商品名称',
  `gift_quantity` int(11) NOT NULL COMMENT '赠送数量',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_gift_rule_id` FOREIGN KEY (`id`) REFERENCES `promotion_rule` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='买赠规则表';

-- 用户优惠券表
CREATE TABLE `user_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `coupon_rule_id` bigint(20) NOT NULL COMMENT '优惠券规则ID',
  `coupon_code` varchar(50) NOT NULL COMMENT '优惠券编码',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '优惠券状态：0-未使用，1-已使用，2-已过期',
  `receive_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint(20) DEFAULT NULL COMMENT '使用订单ID',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_coupon_rule_id` (`coupon_rule_id`),
  KEY `idx_status` (`status`),
  KEY `idx_expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户优惠券表';

-- 优惠计算结果表
CREATE TABLE `promotion_calculation_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `cart_id` bigint(20) NOT NULL COMMENT '购物车ID',
  `original_amount` decimal(10,2) NOT NULL COMMENT '原始金额',
  `discount_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
  `final_amount` decimal(10,2) NOT NULL COMMENT '最终金额',
  `used_rule_ids` varchar(255) DEFAULT NULL COMMENT '使用的优惠规则ID列表',
  `used_coupon_ids` varchar(255) DEFAULT NULL COMMENT '使用的优惠券ID列表',
  `discount_details` text COMMENT '优惠明细',
  `calculation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '计算时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_cart_id` (`cart_id`),
  KEY `idx_calculation_time` (`calculation_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠计算结果表';

-- 优惠审计日志表
CREATE TABLE `promotion_audit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `operation_type` int(11) NOT NULL COMMENT '操作类型：1-优惠计算，2-优惠券领取，3-优惠券使用，4-规则配置变更',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `rule_id` bigint(20) DEFAULT NULL COMMENT '规则ID',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '优惠券ID',
  `operation_content` text COMMENT '操作内容',
  `operation_result` int(11) NOT NULL DEFAULT '1' COMMENT '操作结果：0-失败，1-成功',
  `failure_reason` varchar(255) DEFAULT NULL COMMENT '失败原因',
  `operation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `ip_address` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  PRIMARY KEY (`id`),
  KEY `idx_operation_type` (`operation_type`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_operation_time` (`operation_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠审计日志表';