package com.zyd.springbootserviceseedproject.service.promotion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionAuditLog;

import java.util.List;

/**
 * 优惠审计日志Service
 * @author zyd
 * @date 2024-05-20
 */
public interface PromotionAuditLogService extends IService<PromotionAuditLog> {

    /**
     * 记录审计日志
     * @param log 审计日志
     * @return 是否记录成功
     */
    boolean recordLog(PromotionAuditLog log);

    /**
     * 根据订单ID获取审计日志
     * @param orderId 订单ID
     * @return 审计日志列表
     */
    List<PromotionAuditLog> getLogsByOrderId(Long orderId);

    /**
     * 根据用户ID获取审计日志
     * @param userId 用户ID
     * @return 审计日志列表
     */
    List<PromotionAuditLog> getLogsByUserId(Long userId);

    /**
     * 根据规则ID获取审计日志
     * @param ruleId 规则ID
     * @return 审计日志列表
     */
    List<PromotionAuditLog> getLogsByRuleId(Long ruleId);
}