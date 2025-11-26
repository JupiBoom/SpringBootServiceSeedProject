package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.ActivityEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 活动Mapper接口
 *
 * @author zyd
 * @since 2024-05-20
 */
@Mapper
public interface ActivityMapper extends BaseMapper<ActivityEntity> {

}