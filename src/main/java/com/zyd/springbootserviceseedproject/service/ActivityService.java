package com.zyd.springbootserviceseedproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.ActivityEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动表 Service 接口
 *
 * @author zyd
 * @since 2024-05-20
 */
public interface ActivityService extends IService<ActivityEntity> {

    /**
     * 根据活动类型查询活动列表
     * @param activityType 活动类型：1-优惠券，2-秒杀，3-拼团
     * @return 活动列表
     */
    List<ActivityEntity> listByActivityType(Integer activityType);

    /**
     * 根据活动状态查询活动列表
     * @param status 活动状态：1-待开始，2-进行中，3-已结束，4-已关闭
     * @return 活动列表
     */
    List<ActivityEntity> listByStatus(Integer status);

    /**
     * 查询指定时间范围内的活动列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 活动列表
     */
    List<ActivityEntity> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 检查活动是否可参与
     * @param activityId 活动ID
     * @return true-可参与，false-不可参与
     */
    boolean checkActivityAvailable(Long activityId);

    /**
     * 更新活动状态
     * @param activityId 活动ID
     * @param status 活动状态：1-待开始，2-进行中，3-已结束，4-已关闭
     * @return true-更新成功，false-更新失败
     */
    boolean updateActivityStatus(Long activityId, Integer status);

    /**
     * 检查商品是否已参与其他活动
     * @param productId 商品ID
     * @param excludeActivityId 排除的活动ID（用于更新活动时）
     * @return true-已参与，false-未参与
     */
    boolean checkProductInOtherActivity(Long productId, Long excludeActivityId);
}
