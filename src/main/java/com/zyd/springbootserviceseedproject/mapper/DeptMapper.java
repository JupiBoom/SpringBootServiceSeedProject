package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.DeptEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhaoyudong
 * @version 1.0
 * @description 部门Mapper
 * @date 2025/9/24 16:35
 */
@Mapper
public interface DeptMapper extends BaseMapper<DeptEntity> {
}
