package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.GroupBuyOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拼团订单表 Mapper 接口
 *
 * @author zyd
 * @since 2024-05-20
 */
@Mapper
public interface GroupBuyOrderMapper extends BaseMapper<GroupBuyOrderEntity> {

    /**
     * 根据拼团编号查询拼团订单列表
     * @param groupNo 拼团编号
     * @return 拼团订单列表
     */
    List<GroupBuyOrderEntity> selectByGroupNo(@Param("groupNo") String groupNo);

    /**
     * 根据用户ID和拼团ID查询拼团订单
     * @param userId 用户ID
     * @param groupBuyId 拼团ID
     * @return 拼团订单
     */
    GroupBuyOrderEntity selectByUserIdAndGroupBuyId(@Param("userId") Long userId, @Param("groupBuyId") Long groupBuyId);

    /**
     * 根据订单号查询拼团订单
     * @param orderNo 订单号
     * @return 拼团订单
     */
    GroupBuyOrderEntity selectByOrderNo(@Param("orderNo") String orderNo);
}
