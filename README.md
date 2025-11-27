# 电商优惠计算引擎系统

一个基于Spring Boot的电商优惠计算引擎系统，支持多种优惠类型和复杂的优惠叠加策略。

## 功能特性

### 1. 规则配置
- 支持满减、折扣、优惠券、买赠等多类型规则
- 规则作用范围：商品、品类、品牌、店铺
- 规则优先级和互斥关系管理

### 2. 优惠计算
- 购物车级别优惠计算
- 支持优惠叠加策略
- 支持用户优惠券选择计算

### 3. 最优优惠推荐
- 自动计算最优优惠组合
- 考虑平台成本约束（优惠上限）
- 实时展示优惠明细

### 4. 限制管理
- 优惠券使用限制（最低消费金额）
- 用户领取和使用限制
- 促销商品库存限制

### 5. 审计日志
- 记录优惠计算详细日志
- 支持订单级别优惠追溯
- 优惠效果统计分析

## 技术栈

- **Spring Boot 3.2.0** - 应用框架
- **MyBatis Plus 3.5.3.1** - ORM框架
- **Drools 8.44.0.Final** - 规则引擎
- **MySQL 8.0** - 数据库
- **Redis 6.2** - 缓存
- **Maven 3.6** - 构建工具

## 项目结构

```
SpringBootServiceSeedProject/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/zyd/springbootserviceseedproject/
│   │   │       ├── config/          # 配置类
│   │   │       ├── controller/      # 控制器
│   │   │       │   └── promotion/   # 促销相关控制器
│   │   │       ├── entity/          # 实体类
│   │   │       │   └── promotion/   # 促销相关实体
│   │   │       ├── mapper/          # Mapper接口
│   │   │       │   └── promotion/   # 促销相关Mapper
│   │   │       ├── service/         # 服务接口
│   │   │       │   └── promotion/   # 促销相关服务接口
│   │   │       └── service/impl/    # 服务实现
│   │   │           └── promotion/   # 促销相关服务实现
│   │   └── resources/
│   │       ├── mapper/              # MyBatis映射文件
│   │       ├── rules/               # Drools规则文件
│   │       ├── application.yml      # 应用配置
│   │       └── logback-spring.xml   # 日志配置
│   └── test/                        # 测试代码
├── pom.xml                          # Maven配置
└── README.md                        # 项目说明
```

## 核心模块

### 1. 实体类
- **PromotionRule** - 促销规则
- **Coupon** - 优惠券模板
- **UserCoupon** - 用户优惠券
- **CartItem** - 购物车商品
- **DiscountResult** - 优惠计算结果
- **DiscountDetail** - 优惠明细
- **DiscountAuditLog** - 优惠审计日志

### 2. 规则引擎
使用Drools规则引擎实现复杂的优惠计算逻辑：
- `promotion-rules.drl` - 促销规则
- `coupon-rules.drl` - 优惠券规则
- `discount-combination-rules.drl` - 优惠组合规则

### 3. 服务层
- **DiscountCalculationService** - 优惠计算核心服务
- **PromotionRuleService** - 促销规则管理服务
- **CouponService** - 优惠券管理服务

### 4. 控制器
- **DiscountCalculationController** - 优惠计算API
- **PromotionRuleController** - 促销规则管理API
- **CouponController** - 优惠券管理API

## 数据库设计

### 促销规则表 (promotion_rule)
```sql
CREATE TABLE promotion_rule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    rule_name VARCHAR(255) NOT NULL,
    rule_type INT NOT NULL COMMENT '1:满减,2:折扣,3:买赠',
    rule_value DECIMAL(10,2),
    discount_value DECIMAL(10,2),
    applicable_range INT NOT NULL COMMENT '1:商品,2:品类,3:品牌,4:店铺',
    product_ids VARCHAR(1000),
    category_ids VARCHAR(1000),
    brand_ids VARCHAR(1000),
    shop_id BIGINT,
    priority INT DEFAULT 1,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    status INT DEFAULT 1 COMMENT '1:启用,0:禁用',
    max_discount_per_order DECIMAL(10,2),
    max_discount_per_user_per_day DECIMAL(10,2),
    is_active INT DEFAULT 1,
    is_stackable INT DEFAULT 0 COMMENT '是否可叠加',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### 优惠券表 (coupon)
```sql
CREATE TABLE coupon (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    coupon_name VARCHAR(255) NOT NULL,
    coupon_type INT NOT NULL COMMENT '1:满减券,2:折扣券,3:无门槛券',
    coupon_value DECIMAL(10,2),
    discount_value DECIMAL(10,2),
    min_order_amount DECIMAL(10,2),
    applicable_range INT NOT NULL COMMENT '1:商品,2:品类,3:品牌,4:店铺',
    product_ids VARCHAR(1000),
    category_ids VARCHAR(1000),
    brand_ids VARCHAR(1000),
    shop_id BIGINT,
    total_quantity INT NOT NULL,
    received_quantity INT DEFAULT 0,
    limit_per_user INT DEFAULT 1,
    validity_type INT NOT NULL COMMENT '1:固定时间,2:领取后N天',
    start_time DATETIME,
    end_time DATETIME,
    valid_days INT,
    status INT DEFAULT 1 COMMENT '1:启用,0:禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### 用户优惠券表 (user_coupon)
```sql
CREATE TABLE user_coupon (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    coupon_id BIGINT NOT NULL,
    coupon_code VARCHAR(50) NOT NULL,
    receive_time DATETIME NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    use_status INT DEFAULT 1 COMMENT '1:未使用,2:已使用,3:已过期',
    use_time DATETIME,
    order_id BIGINT,
    actual_discount_amount DECIMAL(10,2),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (coupon_id) REFERENCES coupon(id)
);
```

## 快速开始

### 1. 环境准备
- JDK 17+
- MySQL 8.0+
- Redis 6.2+
- Maven 3.6+

### 2. 配置修改
修改 `src/main/resources/application.yml` 中的数据库和Redis配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/promotion_engine?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
```

### 3. 数据库初始化
执行数据库脚本创建所需表结构。

### 4. 启动项目
```bash
mvn spring-boot:run
```

## API接口

### 优惠计算
- `POST /api/promotion/discount/calculate` - 购物车优惠计算
- `POST /api/promotion/discount/calculate-with-coupons` - 指定优惠券组合计算
- `POST /api/promotion/discount/recommend-optimal` - 最优优惠推荐
- `GET /api/promotion/discount/order/{orderId}` - 订单优惠计算

### 促销规则管理
- `GET /api/promotion/rule/list` - 获取促销规则列表
- `POST /api/promotion/rule/create` - 创建促销规则
- `PUT /api/promotion/rule/{id}` - 更新促销规则
- `DELETE /api/promotion/rule/{id}` - 删除促销规则

### 优惠券管理
- `GET /api/promotion/coupon/list` - 获取优惠券列表
- `POST /api/promotion/coupon/create` - 创建优惠券
- `POST /api/promotion/coupon/receive/{couponId}` - 用户领取优惠券
- `POST /api/promotion/coupon/use/{userCouponId}` - 用户使用优惠券

## 使用示例

### 购物车优惠计算
```bash
curl -X POST "http://localhost:8080/api/promotion/discount/calculate?userId=1" \
  -H "Content-Type: application/json" \
  -d '[{
    "productId": 1,
    "productName": "测试商品1",
    "productCode": "TEST001",
    "categoryId": 1,
    "brandId": 1,
    "shopId": 1,
    "quantity": 2,
    "unitPrice": 100.00,
    "totalPrice": 200.00,
    "isPromotion": 1,
    "stockQuantity": 100,
    "status": 1
  }]'
```

### 最优优惠推荐
```bash
curl -X POST "http://localhost:8080/api/promotion/discount/recommend-optimal?userId=1" \
  -H "Content-Type: application/json" \
  -d '[{
    "productId": 1,
    "productName": "测试商品1",
    "productCode": "TEST001",
    "categoryId": 1,
    "brandId": 1,
    "shopId": 1,
    "quantity": 1,
    "unitPrice": 300.00,
    "totalPrice": 300.00,
    "isPromotion": 1,
    "stockQuantity": 100,
    "status": 1
  }]'
```

## 规则引擎使用

### 添加新的促销规则
在 `src/main/resources/rules/promotion-rules.drl` 中添加新的规则：

```drools
rule "满500减100"
    salience 10
    when
        $cart: CartContext(totalAmount >= 500)
        $rule: PromotionRule(ruleType == 1, ruleValue == 500, discountValue == 100, isActive == 1)
    then
        BigDecimal discount = $rule.getDiscountValue();
        $cart.addDiscountDetail(new DiscountDetail("满500减100", discount, $rule.getId()));
        $cart.setTotalDiscount($cart.getTotalDiscount().add(discount));
end
```

## 扩展说明

### 自定义优惠类型
1. 在 `PromotionRule` 实体中添加新的规则类型
2. 在Drools规则文件中添加对应的规则逻辑
3. 在 `DiscountCalculationService` 中添加相应的处理方法

### 优惠叠加策略
在 `discount-combination-rules.drl` 中修改优惠叠加规则，支持不同的叠加策略。

## 许可证

MIT License

## 联系方式

如有问题或建议，请联系：
- 作者：zyd
- 邮箱：zyd@example.com