package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.GroupBuyMemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拼团成员表 Mapper 接口
 *
 * @author zyd
 * @since 2024-05-20
 */
@Mapper
public interface GroupBuyMemberMapper extends BaseMapper<GroupBuyMemberEntity> {

    /**
     * 根据拼团编号查询拼团成员列表
     * @param groupNo 拼团编号
     * @return 拼团成员列表
     */
    List<GroupBuyMemberEntity> selectByGroupNo(@Param("groupNo") String groupNo);

    /**
     * 根据拼团编号查询拼团成员数量
     * @param groupNo 拼团编号
     * @return 拼团成员数量
     */
    int countByGroupNo(@Param("groupNo") String groupNo);

    /**
     * 根据用户ID和拼团编号查询拼团成员
     * @param userId 用户ID
     * @param groupNo 拼团编号
     * @return 拼团成员
     */
    GroupBuyMemberEntity selectByUserIdAndGroupNo(@Param("userId") Long userId, @Param("groupNo") String groupNo);
}
