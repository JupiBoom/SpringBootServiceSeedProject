package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.promotion.PromotionAuditLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠审计日志Mapper
 * @author zyd
 * @date 2024-05-20
 */
@Mapper
public interface PromotionAuditLogMapper extends BaseMapper<PromotionAuditLog> {
}