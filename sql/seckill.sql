-- 秒杀商品表
CREATE TABLE `seckill_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `seckill_price` decimal(10,2) NOT NULL COMMENT '秒杀价格',
  `total_stock` int(11) NOT NULL COMMENT '总库存',
  `available_stock` int(11) NOT NULL COMMENT '可用库存',
  `sold_stock` int(11) NOT NULL DEFAULT '0' COMMENT '已售库存',
  `limit_per_user` int(11) NOT NULL DEFAULT '1' COMMENT '每人限购数量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_activity_product` (`activity_id`,`product_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀商品表';

-- 秒杀订单表
CREATE TABLE `seckill_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `seckill_product_id` bigint(20) NOT NULL COMMENT '秒杀商品ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '购买数量',
  `seckill_price` decimal(10,2) NOT NULL COMMENT '秒杀价格',
  `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态：1-待支付，2-已支付，3-已取消，4-已完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀订单表';
