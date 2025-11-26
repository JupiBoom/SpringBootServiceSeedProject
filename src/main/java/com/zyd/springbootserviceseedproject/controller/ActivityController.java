package com.zyd.springbootserviceseedproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.entity.ActivityEntity;
import com.zyd.springbootserviceseedproject.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 活动Controller
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
     * 分页查询活动列表
     *
     * @param page 页码
     * @param size 每页大小
     * @return 活动列表
     */
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<ActivityEntity> pageParam = new Page<>(page, size);
        Page<ActivityEntity> pageResult = activityService.page(pageParam);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询活动
     *
     * @param id 活动ID
     * @return 活动信息
     */
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id) {
        ActivityEntity activity = activityService.getById(id);
        return Result.success(activity);
    }

    /**
     * 创建活动
     *
     * @param activity 活动信息
     * @return 创建结果
     */
    @PostMapping("/create")
    public Result create(@RequestBody ActivityEntity activity) {
        activityService.save(activity);
        return Result.success();
    }

    /**
     * 更新活动
     *
     * @param activity 活动信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result update(@RequestBody ActivityEntity activity) {
        activityService.updateById(activity);
        return Result.success();
    }

    /**
     * 删除活动
     *
     * @param id 活动ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        activityService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除活动
     *
     * @param ids 活动ID列表
     * @return 删除结果
     */
    @DeleteMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<Long> ids) {
        activityService.removeByIds(ids);
        return Result.success();
    }

}