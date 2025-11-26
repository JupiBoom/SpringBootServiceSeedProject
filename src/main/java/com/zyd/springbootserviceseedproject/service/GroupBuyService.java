package com.zyd.springbootserviceseedproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyd.springbootserviceseedproject.entity.GroupBuyEntity;

/**
 * 拼团活动Service接口
 *
 * @author zyd
 * @since 2024-05-20
 */
public interface GroupBuyService extends IService<GroupBuyEntity> {

    /**
     * 创建拼团
     *
     * @param groupBuyId 拼团活动ID
     * @param userId     用户ID
     * @param productId  商品ID
     * @return 拼团记录ID
     */
    Long createGroup(Long groupBuyId, Long userId, Long productId);

    /**
     * 加入拼团
     *
     * @param groupRecordId 拼团记录ID
     * @param userId        用户ID
     * @return 加入结果
     */
    boolean joinGroup(Long groupRecordId, Long userId);

    /**
     * 取消拼团
     *
     * @param groupRecordId 拼团记录ID
     * @param userId        用户ID
     * @return 取消结果
     */
    boolean cancelGroup(Long groupRecordId, Long userId);

    /**
     * 完成拼团
     *
     * @param groupRecordId 拼团记录ID
     * @return 完成结果
     */
    boolean completeGroup(Long groupRecordId);

}