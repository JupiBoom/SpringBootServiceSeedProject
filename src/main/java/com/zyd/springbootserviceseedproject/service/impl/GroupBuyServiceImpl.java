package com.zyd.springbootserviceseedproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyd.springbootserviceseedproject.entity.GroupBuyEntity;
import com.zyd.springbootserviceseedproject.entity.GroupBuyMemberEntity;
import com.zyd.springbootserviceseedproject.entity.GroupBuyOrderEntity;
import com.zyd.springbootserviceseedproject.mapper.GroupBuyMapper;
import com.zyd.springbootserviceseedproject.mapper.GroupBuyMemberMapper;
import com.zyd.springbootserviceseedproject.mapper.GroupBuyOrderMapper;
import com.zyd.springbootserviceseedproject.service.GroupBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 拼团表 Service 实现类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Service
public class GroupBuyServiceImpl extends ServiceImpl<GroupBuyMapper, GroupBuyEntity> implements GroupBuyService {

    @Autowired
    private GroupBuyMapper groupBuyMapper;

    @Autowired
    private GroupBuyMemberMapper groupBuyMemberMapper;

    @Autowired
    private GroupBuyOrderMapper groupBuyOrderMapper;

    @Override
    public List<GroupBuyEntity> listByActivityId(Long activityId) {
        LambdaQueryWrapper<GroupBuyEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupBuyEntity::getActivityId, activityId);
        return groupBuyMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GroupBuyOrderEntity createGroupBuy(Long groupBuyId, Long userId, Integer quantity) {
        GroupBuyEntity groupBuy = groupBuyMapper.selectById(groupBuyId);
        if (groupBuy == null) {
            return null;
        }

        // 检查拼团库存是否充足
        if (groupBuy.getAvailableStock() < quantity) {
            return null;
        }

        // 生成拼团编号
        String groupNo = UUID.randomUUID().toString().replace("-", "");

        // 创建拼团订单
        GroupBuyOrderEntity groupBuyOrder = new GroupBuyOrderEntity();
        groupBuyOrder.setGroupBuyId(groupBuyId);
        groupBuyOrder.setGroupNo(groupNo);
        groupBuyOrder.setUserId(userId);
        groupBuyOrder.setProductId(groupBuy.getProductId());
        groupBuyOrder.setProductName(groupBuy.getProductName());
        groupBuyOrder.setProductImage(groupBuy.getProductImage());
        groupBuyOrder.setGroupBuyPrice(groupBuy.getGroupBuyPrice());
        groupBuyOrder.setQuantity(quantity);
        groupBuyOrder.setTotalAmount(groupBuy.getGroupBuyPrice().multiply(quantity.longValue()));
        groupBuyOrder.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        groupBuyOrder.setStatus(1); // 1-待支付
        groupBuyOrder.setCreateTime(LocalDateTime.now());
        groupBuyOrderMapper.insert(groupBuyOrder);

        // 创建拼团成员记录
        GroupBuyMemberEntity groupBuyMember = new GroupBuyMemberEntity();
        groupBuyMember.setGroupBuyId(groupBuyId);
        groupBuyMember.setGroupNo(groupNo);
        groupBuyMember.setUserId(userId);
        groupBuyMember.setOrderNo(groupBuyOrder.getOrderNo());
        groupBuyMember.setIsLeader(1); // 1-团长
        groupBuyMember.setJoinTime(LocalDateTime.now());
        groupBuyMemberMapper.insert(groupBuyMember);

        // 扣减拼团库存
        groupBuyMapper.decreaseStock(groupBuyId, quantity);

        return groupBuyOrder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GroupBuyOrderEntity joinGroupBuy(String groupNo, Long userId, Integer quantity) {
        // 查询拼团详情
        LambdaQueryWrapper<GroupBuyEntity> groupBuyQueryWrapper = new LambdaQueryWrapper<>();
        groupBuyQueryWrapper.eq(GroupBuyEntity::getGroupNo, groupNo);
        GroupBuyEntity groupBuy = groupBuyMapper.selectOne(groupBuyQueryWrapper);
        if (groupBuy == null) {
            return null;
        }

        // 检查拼团是否已经开始
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(groupBuy.getStartTime())) {
            return null;
        }

        // 检查拼团是否已经结束
        if (now.isAfter(groupBuy.getEndTime())) {
            return null;
        }

        // 检查拼团是否已经成功
        if (groupBuy.getStatus() == 2) { // 2-拼团成功
            return null;
        }

        // 检查拼团是否已经失败
        if (groupBuy.getStatus() == 3) { // 3-拼团失败
            return null;
        }

        // 检查拼团库存是否充足
        if (groupBuy.getStock() < quantity) {
            return null;
        }

        // 检查用户是否已经加入该拼团
        LambdaQueryWrapper<GroupBuyMemberEntity> memberQueryWrapper = new LambdaQueryWrapper<>();
        memberQueryWrapper.eq(GroupBuyMemberEntity::getGroupNo, groupNo)
                .eq(GroupBuyMemberEntity::getUserId, userId);
        if (groupBuyMemberMapper.selectCount(memberQueryWrapper) > 0) {
            return null;
        }

        // 创建拼团订单
        GroupBuyOrderEntity groupBuyOrder = new GroupBuyOrderEntity();
        groupBuyOrder.setGroupBuyId(groupBuy.getId());
        groupBuyOrder.setGroupNo(groupNo);
        groupBuyOrder.setUserId(userId);
        groupBuyOrder.setProductId(groupBuy.getProductId());
        groupBuyOrder.setProductName(groupBuy.getProductName());
        groupBuyOrder.setProductImage(groupBuy.getProductImage());
        groupBuyOrder.setGroupBuyPrice(groupBuy.getGroupBuyPrice());
        groupBuyOrder.setQuantity(quantity);
        groupBuyOrder.setTotalAmount(groupBuy.getGroupBuyPrice().multiply(quantity.longValue()));
        groupBuyOrder.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        groupBuyOrder.setStatus(1); // 1-待支付
        groupBuyOrder.setCreateTime(LocalDateTime.now());
        groupBuyOrderMapper.insert(groupBuyOrder);

        // 创建拼团成员记录
        GroupBuyMemberEntity groupBuyMember = new GroupBuyMemberEntity();
        groupBuyMember.setGroupBuyId(groupBuy.getId());
        groupBuyMember.setGroupNo(groupNo);
        groupBuyMember.setUserId(userId);
        groupBuyMember.setOrderNo(groupBuyOrder.getOrderNo());
        groupBuyMember.setIsLeader(0); // 0-团员
        groupBuyMember.setJoinTime(LocalDateTime.now());
        groupBuyMemberMapper.insert(groupBuyMember);

        // 扣减拼团库存
        groupBuyMapper.decreaseStock(groupBuy.getId(), quantity);

        // 检查拼团是否已经达到人数要求
        int memberCount = groupBuyMemberMapper.countByGroupNo(groupNo);
        if (memberCount >= groupBuy.getRequiredNum()) {
            // 更新拼团状态为成功
            groupBuy.setStatus(2); // 2-拼团成功
            groupBuy.setSuccessTime(LocalDateTime.now());
            groupBuyMapper.updateById(groupBuy);

            // 处理拼团结果
            handleGroupBuyResult(groupNo);
        }

        return groupBuyOrder;
    }

    @Override
    public GroupBuyEntity getGroupBuyByGroupNo(String groupNo) {
        LambdaQueryWrapper<GroupBuyEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupBuyEntity::getGroupNo, groupNo);
        return groupBuyMapper.selectOne(queryWrapper);
    }

    @Override
    public List<GroupBuyMemberEntity> getGroupBuyMembers(String groupNo) {
        return groupBuyMemberMapper.selectByGroupNo(groupNo);
    }

    @Override
    public List<GroupBuyOrderEntity> getGroupBuyOrders(String groupNo) {
        return groupBuyOrderMapper.selectByGroupNo(groupNo);
    }

    @Override
    public boolean checkGroupBuySuccess(String groupNo) {
        GroupBuyEntity groupBuy = getGroupBuyByGroupNo(groupNo);
        if (groupBuy == null) {
            return false;
        }

        return groupBuy.getStatus() == 2; // 2-拼团成功
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleGroupBuyResult(String groupNo) {
        GroupBuyEntity groupBuy = getGroupBuyByGroupNo(groupNo);
        if (groupBuy == null || groupBuy.getStatus() != 2) {
            return false;
        }

        // 查询该拼团的所有订单
        List<GroupBuyOrderEntity> groupBuyOrders = groupBuyOrderMapper.selectByGroupNo(groupNo);
        for (GroupBuyOrderEntity order : groupBuyOrders) {
            // 更新订单状态为已支付（假设拼团成功后自动支付）
            order.setStatus(2); // 2-已支付
            order.setUpdateTime(LocalDateTime.now());
            groupBuyOrderMapper.updateById(order);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleTimeoutGroupBuy(String groupNo) {
        GroupBuyEntity groupBuy = getGroupBuyByGroupNo(groupNo);
        if (groupBuy == null || groupBuy.getStatus() != 1) { // 1-拼团中
            return false;
        }

        // 更新拼团状态为失败
        groupBuy.setStatus(3); // 3-拼团失败
        groupBuy.setUpdateTime(LocalDateTime.now());
        groupBuyMapper.updateById(groupBuy);

        // 处理拼团失败退款
        refundForGroupBuyFailure(groupNo);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean refundForGroupBuyFailure(String groupNo) {
        // 查询该拼团的所有订单
        List<GroupBuyOrderEntity> groupBuyOrders = groupBuyOrderMapper.selectByGroupNo(groupNo);
        for (GroupBuyOrderEntity order : groupBuyOrders) {
            // 更新订单状态为已退款
            order.setStatus(4); // 4-已退款
            order.setUpdateTime(LocalDateTime.now());
            groupBuyOrderMapper.updateById(order);
        }

        // 恢复拼团库存
        GroupBuyEntity groupBuy = getGroupBuyByGroupNo(groupNo);
        if (groupBuy != null) {
            // 查询该拼团的总购买数量
            int totalQuantity = groupBuyOrders.stream().mapToInt(GroupBuyOrderEntity::getQuantity).sum();
            groupBuyMapper.increaseStock(groupBuy.getId(), totalQuantity);
        }

        return true;
    }
}