package com.zyd.springbootserviceseedproject.entity.promotion;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 折扣规则
 * @author zyd
 * @date 2024-05-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("discount_rule")
public class DiscountRule extends PromotionRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 折扣值：如8.5表示85折
     */
    private BigDecimal discountValue;
}