package com.zyd.springbootserviceseedproject.entity.promotion;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 买赠规则
 * @author zyd
 * @date 2024-05-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gift_rule")
public class GiftRule extends PromotionRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购买数量
     */
    private Integer buyQuantity;

    /**
     * 赠送商品ID
     */
    private Long giftProductId;

    /**
     * 赠送商品名称
     */
    private String giftProductName;

    /**
     * 赠送数量
     */
    private Integer giftQuantity;
}