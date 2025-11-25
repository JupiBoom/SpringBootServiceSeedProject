package com.zyd.springbootserviceseedproject.controller;

import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.entity.GroupBuyEntity;
import com.zyd.springbootserviceseedproject.entity.GroupBuyMemberEntity;
import com.zyd.springbootserviceseedproject.entity.GroupBuyOrderEntity;
import com.zyd.springbootserviceseedproject.service.GroupBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 拼团表 Controller
 *
 * @author zyd
 * @since 2024-05-20
 */
@RestController
@RequestMapping("/groupBuy")
public class GroupBuyController {

    @Autowired
    private GroupBuyService groupBuyService;

    /**
     * 根据活动ID查询拼团列表
     * @param activityId 活动ID
     * @return 拼团列表
     */
    @GetMapping("/listByActivityId/{activityId}")
    public Result<List<GroupBuyEntity>> listByActivityId(@PathVariable Long activityId) {
        List<GroupBuyEntity> groupBuyList = groupBuyService.listByActivityId(activityId);
        return Result.success(groupBuyList);
    }

    /**
     * 创建拼团
     * @param groupBuyId 拼团ID
     * @param userId 用户ID
     * @param quantity 购买数量
     * @return 拼团订单
     */
    @PostMapping("/create")
    public Result<GroupBuyOrderEntity> createGroupBuy(@RequestParam Long groupBuyId, @RequestParam Long userId, @RequestParam Integer quantity) {
        GroupBuyOrderEntity groupBuyOrder = groupBuyService.createGroupBuy(groupBuyId, userId, quantity);
        return groupBuyOrder != null ? Result.success(groupBuyOrder) : Result.fail("创建拼团失败");
    }

    /**
     * 加入拼团
     * @param groupNo 拼团编号
     * @param userId 用户ID
     * @param quantity 购买数量
     * @return 拼团订单
     */
    @PostMapping("/join")
    public Result<GroupBuyOrderEntity> joinGroupBuy(@RequestParam String groupNo, @RequestParam Long userId, @RequestParam Integer quantity) {
        GroupBuyOrderEntity groupBuyOrder = groupBuyService.joinGroupBuy(groupNo, userId, quantity);
        return groupBuyOrder != null ? Result.success(groupBuyOrder) : Result.fail("加入拼团失败");
    }

    /**
     * 查询拼团详情
     * @param groupNo 拼团编号
     * @return 拼团详情
     */
    @GetMapping("/getByGroupNo/{groupNo}")
    public Result<GroupBuyEntity> getGroupBuyByGroupNo(@PathVariable String groupNo) {
        GroupBuyEntity groupBuy = groupBuyService.getGroupBuyByGroupNo(groupNo);
        return groupBuy != null ? Result.success(groupBuy) : Result.fail("拼团不存在");
    }

    /**
     * 查询拼团成员列表
     * @param groupNo 拼团编号
     * @return 拼团成员列表
     */
    @GetMapping("/getMembers/{groupNo}")
    public Result<List<GroupBuyMemberEntity>> getGroupBuyMembers(@PathVariable String groupNo) {
        List<GroupBuyMemberEntity> memberList = groupBuyService.getGroupBuyMembers(groupNo);
        return Result.success(memberList);
    }

    /**
     * 查询拼团订单列表
     * @param groupNo 拼团编号
     * @return 拼团订单列表
     */
    @GetMapping("/getOrders/{groupNo}")
    public Result<List<GroupBuyOrderEntity>> getGroupBuyOrders(@PathVariable String groupNo) {
        List<GroupBuyOrderEntity> orderList = groupBuyService.getGroupBuyOrders(groupNo);
        return Result.success(orderList);
    }

    /**
     * 检查拼团是否成功
     * @param groupNo 拼团编号
     * @return 检查结果
     */
    @GetMapping("/checkSuccess/{groupNo}")
    public Result<Boolean> checkGroupBuySuccess(@PathVariable String groupNo) {
        boolean success = groupBuyService.checkGroupBuySuccess(groupNo);
        return Result.success(success);
    }

    /**
     * 处理拼团结果
     * @param groupNo 拼团编号
     * @return 处理结果
     */
    @PostMapping("/handleResult/{groupNo}")
    public Result<Boolean> handleGroupBuyResult(@PathVariable String groupNo) {
        boolean success = groupBuyService.handleGroupBuyResult(groupNo);
        return success ? Result.success(true) : Result.fail("处理失败");
    }

    /**
     * 处理超时未成团的拼团
     * @param groupNo 拼团编号
     * @return 处理结果
     */
    @PostMapping("/handleTimeout/{groupNo}")
    public Result<Boolean> handleTimeoutGroupBuy(@PathVariable String groupNo) {
        boolean success = groupBuyService.handleTimeoutGroupBuy(groupNo);
        return success ? Result.success(true) : Result.fail("处理失败");
    }

    /**
     * 拼团失败自动退款
     * @param groupNo 拼团编号
     * @return 退款结果
     */
    @PostMapping("/refund/{groupNo}")
    public Result<Boolean> refundForGroupBuyFailure(@PathVariable String groupNo) {
        boolean success = groupBuyService.refundForGroupBuyFailure(groupNo);
        return success ? Result.success(true) : Result.fail("退款失败");
    }

    /**
     * 创建拼团活动
     * @param groupBuy 拼团活动信息
     * @return 创建结果
     */
    @PostMapping("/createActivity")
    public Result<Boolean> createGroupBuyActivity(@RequestBody GroupBuyEntity groupBuy) {
        boolean success = groupBuyService.save(groupBuy);
        return success ? Result.success(true) : Result.fail("创建失败");
    }

    /**
     * 更新拼团活动
     * @param groupBuy 拼团活动信息
     * @return 更新结果
     */
    @PutMapping("/updateActivity")
    public Result<Boolean> updateGroupBuyActivity(@RequestBody GroupBuyEntity groupBuy) {
        boolean success = groupBuyService.updateById(groupBuy);
        return success ? Result.success(true) : Result.fail("更新失败");
    }

    /**
     * 删除拼团活动
     * @param groupBuyId 拼团活动ID
     * @return 删除结果
     */
    @DeleteMapping("/deleteActivity/{groupBuyId}")
    public Result<Boolean> deleteGroupBuyActivity(@PathVariable Long groupBuyId) {
        boolean success = groupBuyService.removeById(groupBuyId);
        return success ? Result.success(true) : Result.fail("删除失败");
    }

    /**
     * 根据ID查询拼团活动
     * @param groupBuyId 拼团活动ID
     * @return 拼团活动信息
     */
    @GetMapping("/getActivityById/{groupBuyId}")
    public Result<GroupBuyEntity> getGroupBuyActivityById(@PathVariable Long groupBuyId) {
        GroupBuyEntity groupBuy = groupBuyService.getById(groupBuyId);
        return groupBuy != null ? Result.success(groupBuy) : Result.fail("拼团活动不存在");
    }

    /**
     * 查询所有拼团活动
     * @return 拼团活动列表
     */
    @GetMapping("/listAllActivities")
    public Result<List<GroupBuyEntity>> listAllGroupBuyActivities() {
        List<GroupBuyEntity> groupBuyList = groupBuyService.list();
        return Result.success(groupBuyList);
    }
}