package com.zyd.springbootserviceseedproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyd.springbootserviceseedproject.entity.GroupBuyEntity;
import com.zyd.springbootserviceseedproject.entity.GroupBuyRecordEntity;
import com.zyd.springbootserviceseedproject.entity.GroupBuyParticipantEntity;
import com.zyd.springbootserviceseedproject.mapper.GroupBuyMapper;
import com.zyd.springbootserviceseedproject.service.GroupBuyService;
import com.zyd.springbootserviceseedproject.service.GroupBuyRecordService;
import com.zyd.springbootserviceseedproject.service.GroupBuyParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 拼团活动Service实现类
 *
 * @author zyd
 * @since 2024-05-20
 */
@Service
public class GroupBuyServiceImpl extends ServiceImpl<GroupBuyMapper, GroupBuyEntity> implements GroupBuyService {

    @Autowired
    private GroupBuyRecordService groupBuyRecordService;

    @Autowired
    private GroupBuyParticipantService groupBuyParticipantService;

    /**
     * 创建拼团
     *
     * @param groupBuyId 拼团活动ID
     * @param userId     用户ID
     * @param productId  商品ID
     * @return 拼团记录ID
     */
    @Transactional(rollbackFor = Exception.class)
    public Long createGroup(Long groupBuyId, Long userId, Long productId) {
        // 1. 检查拼团活动是否存在
        GroupBuyEntity groupBuy = this.getById(groupBuyId);
        if (groupBuy == null) {
            throw new RuntimeException("拼团活动不存在");
        }

        // 2. 检查拼团活动是否已经开始
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(groupBuy.getCreateTime())) {
            throw new RuntimeException("拼团活动尚未开始");
        }

        // 3. 检查拼团活动是否已经完成
        if (groupBuy.getSoldCount() >= groupBuy.getTotalStock()) {
            throw new RuntimeException("拼团活动已经完成");
        }

        // 4. 检查拼团活动是否已经结束
        if (now.isAfter(groupBuy.getCreateTime().plusMinutes(groupBuy.getValidMinutes()))) {
            throw new RuntimeException("拼团活动已经结束");
        }

        // 4. 检查库存是否充足
        if (groupBuy.getTotalStock() <= 0) {
            throw new RuntimeException("商品已经售罄");
        }

        // 5. 创建拼团记录
        GroupBuyRecordEntity groupBuyRecord = new GroupBuyRecordEntity();
        groupBuyRecord.setGroupBuyId(groupBuyId);
        groupBuyRecord.setLeaderUserId(userId);
        groupBuyRecord.setCurrentNum(1);
        groupBuyRecord.setRequiredNum(groupBuy.getRequiredNum());
        groupBuyRecord.setStatus(0); // 待成团
        groupBuyRecord.setStartTime(LocalDateTime.now());
        groupBuyRecord.setEndTime(LocalDateTime.now().plusMinutes(groupBuy.getValidMinutes()));
        groupBuyRecordService.save(groupBuyRecord);

        // 6. 添加创建者为参与者
        GroupBuyParticipantEntity participant = new GroupBuyParticipantEntity();
        participant.setGroupBuyRecordId(groupBuyRecord.getId());
        participant.setUserId(userId);
        participant.setIsLeader(1); // 是团长
        participant.setJoinTime(LocalDateTime.now());
        groupBuyParticipantService.save(participant);

        // 7. 更新拼团活动已售数量
        groupBuy.setSoldCount(groupBuy.getSoldCount() + 1);
        this.updateById(groupBuy);

        return groupBuyRecord.getId();
    }

    /**
     * 加入拼团
     *
     * @param groupRecordId 拼团记录ID
     * @param userId        用户ID
     * @return 加入结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean joinGroup(Long groupRecordId, Long userId) {
        // 1. 检查拼团记录是否存在
        GroupBuyRecordEntity groupBuyRecord = groupBuyRecordService.getById(groupRecordId);
        if (groupBuyRecord == null) {
            throw new RuntimeException("拼团记录不存在");
        }

        // 2. 检查拼团是否已经结束
        if (groupBuyRecord.getStatus() != 0) {
            throw new RuntimeException("拼团已经结束");
        }

        // 3. 检查用户是否已经加入该拼团
        List<GroupBuyParticipantEntity> existingParticipants = groupBuyParticipantService.list(
                new QueryWrapper<GroupBuyParticipantEntity>()
                        .eq("group_record_id", groupRecordId)
                        .eq("user_id", userId)
        );
        if (!existingParticipants.isEmpty()) {
            throw new RuntimeException("您已经加入该拼团");
        }

        // 4. 检查拼团是否还有库存
        GroupBuyEntity groupBuy = this.getById(groupBuyRecord.getGroupBuyId());
        if (groupBuy.getTotalStock() <= 0) {
            throw new RuntimeException("拼团活动库存不足");
        }

        // 5. 检查拼团是否已满员
        long participantCount = groupBuyParticipantService.count(
                new QueryWrapper<GroupBuyParticipantEntity>()
                        .eq("group_record_id", groupRecordId)
        );
        if (participantCount >= groupBuy.getRequiredNum().longValue()) {
            throw new RuntimeException("拼团已经满员");
        }

        // 6. 添加参与者
        GroupBuyParticipantEntity participant = new GroupBuyParticipantEntity();
        participant.setGroupBuyRecordId(groupRecordId);
        participant.setUserId(userId);
        participant.setIsLeader(0); // 不是团长
        participant.setJoinTime(LocalDateTime.now());
        boolean success = groupBuyParticipantService.save(participant);
        if (success) {
            // 6. 检查拼团是否已满员，如果满员则完成拼团
            participantCount++; // 刚刚加入了一个参与者
            if (participantCount >= groupBuy.getRequiredNum()) {
                completeGroup(groupRecordId);
            }
        }

        return success;
    }

    /**
     * 取消拼团
     *
     * @param groupRecordId 拼团记录ID
     * @param userId        用户ID
     * @return 取消结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelGroup(Long groupRecordId, Long userId) {
        // 1. 检查拼团记录是否存在
        GroupBuyRecordEntity groupBuyRecord = groupBuyRecordService.getById(groupRecordId);
        if (groupBuyRecord == null) {
            throw new RuntimeException("拼团记录不存在");
        }

        // 2. 检查拼团是否已经结束
        if (groupBuyRecord.getStatus() != 0) {
            throw new RuntimeException("拼团已经结束");
        }

        // 3. 检查用户是否是该拼团的参与者
        GroupBuyParticipantEntity participant = groupBuyParticipantService.getOne(
                new QueryWrapper<GroupBuyParticipantEntity>()
                        .eq("group_record_id", groupRecordId)
                        .eq("user_id", userId)
        );
        if (participant == null) {
            throw new RuntimeException("您不是该拼团的参与者");
        }

        // 4. 检查用户是否是团长
        if (participant.getIsLeader() == 1) {
            // 团长取消拼团，需要解散整个拼团
            groupBuyRecord.setStatus(2); // 拼团失败
            groupBuyRecord.setEndTime(LocalDateTime.now());
            groupBuyRecordService.updateById(groupBuyRecord);

            // 删除所有参与者
            groupBuyParticipantService.remove(
                    new QueryWrapper<GroupBuyParticipantEntity>()
                            .eq("group_record_id", groupRecordId)
            );
        } else {
            // 普通成员取消拼团，只删除自己的参与记录
            groupBuyParticipantService.removeById(participant.getId());
        }

        return true;
    }

    /**
     * 完成拼团
     *
     * @param groupRecordId 拼团记录ID
     * @return 完成结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean completeGroup(Long groupRecordId) {
        // 1. 检查拼团记录是否存在
        GroupBuyRecordEntity groupBuyRecord = groupBuyRecordService.getById(groupRecordId);
        if (groupBuyRecord == null) {
            throw new RuntimeException("拼团记录不存在");
        }

        // 2. 检查拼团是否已经结束
        if (groupBuyRecord.getStatus() != 0) {
            throw new RuntimeException("拼团已经结束");
        }

        // 3. 更新拼团记录状态为成功
        groupBuyRecord.setStatus(1); // 拼团成功
        groupBuyRecord.setEndTime(LocalDateTime.now());
        boolean success = groupBuyRecordService.updateById(groupBuyRecord);
        if (success) {
            // 4. 更新拼团活动已完成数量
            GroupBuyEntity groupBuy = this.getById(groupBuyRecord.getGroupBuyId());
            groupBuy.setSoldCount(groupBuy.getSoldCount() + 1);
            this.updateById(groupBuy);

            // 5. 扣减库存
            groupBuy.setTotalStock(groupBuy.getTotalStock() - 1);
            groupBuy.setSoldCount(groupBuy.getSoldCount() + 1);
            this.updateById(groupBuy);
        }

        return success;
    }

}