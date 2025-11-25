package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.SeckillOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 秒杀订单表 Mapper 接口
 *
 * @author zyd
 * @since 2024-05-20
 */
@Mapper
public interface SeckillOrderMapper extends BaseMapper<SeckillOrderEntity> {

    /**
     * 根据用户ID和活动ID查询秒杀订单数量
     * @param userId 用户ID
     * @param activityId 活动ID
     * @return 订单数量
     */
    int countByUserIdAndActivityId(@Param("userId") Long userId, @Param("activityId") Long activityId);

    /**
     * 根据订单号查询秒杀订单
     * @param orderNo 订单号
     * @return 秒杀订单
     */
    SeckillOrderEntity selectByOrderNo(@Param("orderNo") String orderNo);
}
