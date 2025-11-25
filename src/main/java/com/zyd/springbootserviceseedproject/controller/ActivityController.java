package com.zyd.springbootserviceseedproject.controller;

import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.entity.ActivityEntity;
import com.zyd.springbootserviceseedproject.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动表 Controller
 *
 * @author zyd
 * @since 2024-05-20
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 根据活动类型查询活动列表
     * @param activityType 活动类型：1-秒杀，2-拼团，3-优惠券
     * @return 活动列表
     */
    @GetMapping("/listByType/{activityType}")
    public Result<List<ActivityEntity>> listByActivityType(@PathVariable Integer activityType) {
        List<ActivityEntity> activityList = activityService.listByActivityType(activityType);
        return Result.success(activityList);
    }

    /**
     * 根据活动状态查询活动列表
     * @param status 活动状态：1-未开始，2-进行中，3-已结束
     * @return 活动列表
     */
    @GetMapping("/listByStatus/{status}")
    public Result<List<ActivityEntity>> listByStatus(@PathVariable Integer status) {
        List<ActivityEntity> activityList = activityService.listByStatus(status);
        return Result.success(activityList);
    }

    /**
     * 根据时间范围查询活动列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 活动列表
     */
    @GetMapping("/listByTimeRange")
    public Result<List<ActivityEntity>> listByTimeRange(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        List<ActivityEntity> activityList = activityService.listByTimeRange(startTime, endTime);
        return Result.success(activityList);
    }

    /**
     * 检查活动是否可用
     * @param activityId 活动ID
     * @return 检查结果
     */
    @GetMapping("/checkAvailable/{activityId}")
    public Result<Boolean> checkActivityAvailable(@PathVariable Long activityId) {
        boolean available = activityService.checkActivityAvailable(activityId);
        return Result.success(available);
    }

    /**
     * 更新活动状态
     * @param activityId 活动ID
     * @param status 活动状态：1-未开始，2-进行中，3-已结束
     * @return 更新结果
     */
    @PutMapping("/updateStatus/{activityId}/{status}")
    public Result<Boolean> updateActivityStatus(@PathVariable Long activityId, @PathVariable Integer status) {
        boolean success = activityService.updateActivityStatus(activityId, status);
        return success ? Result.success(true) : Result.fail("更新失败");
    }

    /**
     * 检查商品是否已参与其他活动
     * @param productId 商品ID
     * @param excludeActivityId 排除的活动ID
     * @return 检查结果
     */
    @GetMapping("/checkProductInOtherActivity")
    public Result<Boolean> checkProductInOtherActivity(@RequestParam Long productId, @RequestParam(required = false) Long excludeActivityId) {
        boolean inOtherActivity = activityService.checkProductInOtherActivity(productId, excludeActivityId);
        return Result.success(inOtherActivity);
    }

    /**
     * 创建活动
     * @param activity 活动信息
     * @return 创建结果
     */
    @PostMapping("/create")
    public Result<Boolean> createActivity(@RequestBody ActivityEntity activity) {
        boolean success = activityService.save(activity);
        return success ? Result.success(true) : Result.fail("创建失败");
    }

    /**
     * 更新活动
     * @param activity 活动信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result<Boolean> updateActivity(@RequestBody ActivityEntity activity) {
        boolean success = activityService.updateById(activity);
        return success ? Result.success(true) : Result.fail("更新失败");
    }

    /**
     * 删除活动
     * @param activityId 活动ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{activityId}")
    public Result<Boolean> deleteActivity(@PathVariable Long activityId) {
        boolean success = activityService.removeById(activityId);
        return success ? Result.success(true) : Result.fail("删除失败");
    }

    /**
     * 根据ID查询活动
     * @param activityId 活动ID
     * @return 活动信息
     */
    @GetMapping("/getById/{activityId}")
    public Result<ActivityEntity> getActivityById(@PathVariable Long activityId) {
        ActivityEntity activity = activityService.getById(activityId);
        return activity != null ? Result.success(activity) : Result.fail("活动不存在");
    }

    /**
     * 查询所有活动
     * @return 活动列表
     */
    @GetMapping("/listAll")
    public Result<List<ActivityEntity>> listAllActivities() {
        List<ActivityEntity> activityList = activityService.list();
        return Result.success(activityList);
    }
}