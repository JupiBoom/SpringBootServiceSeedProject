package com.zyd.springbootserviceseedproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyd.springbootserviceseedproject.entity.ActivityEntity;
import com.zyd.springbootserviceseedproject.mapper.ActivityMapper;
import com.zyd.springbootserviceseedproject.service.ActivityService;
import org.springframework.stereotype.Service;

/**
 * 活动Service实现类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityEntity> implements ActivityService {

}