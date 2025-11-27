package com.zyd.springbootserviceseedproject.entity.promotion;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 满减规则
 * @author zyd
 * @date 2024-05-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("full_reduction_rule")
public class FullReductionRule extends PromotionRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 满金额
     */
    private BigDecimal fullAmount;

    /**
     * 减金额
     */
    private BigDecimal reduceAmount;
}