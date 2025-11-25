package com.zyd.springbootserviceseedproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.GroupBuyEntity;
import com.zyd.springbootserviceseedproject.entity.GroupBuyMemberEntity;
import com.zyd.springbootserviceseedproject.entity.GroupBuyOrderEntity;

import java.util.List;

/**
 * 拼团表 Service 接口
 *
 * @author zyd
 * @since 2024-05-20
 */
public interface GroupBuyService extends IService<GroupBuyEntity> {

    /**
     * 根据活动ID查询拼团列表
     * @param activityId 活动ID
     * @return 拼团列表
     */
    List<GroupBuyEntity> listByActivityId(Long activityId);

    /**
     * 创建拼团
     * @param groupBuyId 拼团ID
     * @param userId 用户ID
     * @param quantity 购买数量
     * @return 拼团订单
     */
    GroupBuyOrderEntity createGroupBuy(Long groupBuyId, Long userId, Integer quantity);

    /**
     * 加入拼团
     * @param groupNo 拼团编号
     * @param userId 用户ID
     * @param quantity 购买数量
     * @return 拼团订单
     */
    GroupBuyOrderEntity joinGroupBuy(String groupNo, Long userId, Integer quantity);

    /**
     * 查询拼团详情
     * @param groupNo 拼团编号
     * @return 拼团详情
     */
    GroupBuyEntity getGroupBuyByGroupNo(String groupNo);

    /**
     * 查询拼团成员列表
     * @param groupNo 拼团编号
     * @return 拼团成员列表
     */
    List<GroupBuyMemberEntity> getGroupBuyMembers(String groupNo);

    /**
     * 查询拼团订单列表
     * @param groupNo 拼团编号
     * @return 拼团订单列表
     */
    List<GroupBuyOrderEntity> getGroupBuyOrders(String groupNo);

    /**
     * 检查拼团是否成功
     * @param groupNo 拼团编号
     * @return true-成功，false-失败
     */
    boolean checkGroupBuySuccess(String groupNo);

    /**
     * 处理拼团结果
     * @param groupNo 拼团编号
     * @return true-处理成功，false-处理失败
     */
    boolean handleGroupBuyResult(String groupNo);

    /**
     * 处理超时未成团的拼团
     * @param groupNo 拼团编号
     * @return true-处理成功，false-处理失败
     */
    boolean handleTimeoutGroupBuy(String groupNo);

    /**
     * 拼团失败自动退款
     * @param groupNo 拼团编号
     * @return true-退款成功，false-退款失败
     */
    boolean refundForGroupBuyFailure(String groupNo);
}
