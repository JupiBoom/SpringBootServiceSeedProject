package com.zyd.springbootserviceseedproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyd.springbootserviceseedproject.entity.ActivityEntity;
import com.zyd.springbootserviceseedproject.mapper.ActivityMapper;
import com.zyd.springbootserviceseedproject.service.ActivityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动表 Service 实现类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityEntity> implements ActivityService {

    @Override
    public List<ActivityEntity> listByActivityType(Integer activityType) {
        LambdaQueryWrapper<ActivityEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityEntity::getActivityType, activityType);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<ActivityEntity> listByStatus(Integer status) {
        LambdaQueryWrapper<ActivityEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityEntity::getStatus, status);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<ActivityEntity> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<ActivityEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(ActivityEntity::getStartTime, startTime, endTime)
                .or().between(ActivityEntity::getEndTime, startTime, endTime);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean checkActivityAvailable(Long activityId) {
        ActivityEntity activity = baseMapper.selectById(activityId);
        if (activity == null) {
            return false;
        }

        // 检查活动状态
        if (activity.getStatus() != 2) { // 2-进行中
            return false;
        }

        // 检查活动时间
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(activity.getStartTime()) && now.isBefore(activity.getEndTime());
    }

    @Override
    public boolean updateActivityStatus(Long activityId, Integer status) {
        ActivityEntity activity = new ActivityEntity();
        activity.setId(activityId);
        activity.setStatus(status);
        return baseMapper.updateById(activity) > 0;
    }

    @Override
    public boolean checkProductInOtherActivity(Long productId, Long excludeActivityId) {
        // TODO: 实现商品是否已参与其他活动的检查逻辑
        // 需要查询秒杀和拼团活动中是否有该商品参与
        return false;
    }
}
