package com.zyd.springbootserviceseedproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.entity.GroupBuyEntity;
import com.zyd.springbootserviceseedproject.service.GroupBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 拼团活动Controller
 *
 * @author zyd
 * @since 2024-05-20
 */
@RestController
@RequestMapping("/group-buy")
public class GroupBuyController {

    @Autowired
    private GroupBuyService groupBuyService;

    /**
     * 分页查询拼团活动
     *
     * @param page 页码
     * @param size 每页数量
     * @return 分页结果
     */
    @GetMapping("/page")
    public Page<GroupBuyEntity> page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<GroupBuyEntity> pageParam = new Page<>(page, size);
        return groupBuyService.page(pageParam);
    }

    /**
     * 根据ID查询拼团活动
     *
     * @param id 拼团活动ID
     * @return 拼团活动信息
     */
    @GetMapping("/{id}")
    public GroupBuyEntity getById(@PathVariable Long id) {
        return groupBuyService.getById(id);
    }

    /**
     * 创建拼团活动
     *
     * @param groupBuy 拼团活动信息
     * @return 创建结果
     */
    @PostMapping
    public boolean save(@RequestBody GroupBuyEntity groupBuy) {
        return groupBuyService.save(groupBuy);
    }

    /**
     * 更新拼团活动
     *
     * @param groupBuy 拼团活动信息
     * @return 更新结果
     */
    @PutMapping
    public boolean update(@RequestBody GroupBuyEntity groupBuy) {
        return groupBuyService.updateById(groupBuy);
    }

    /**
     * 删除拼团活动
     *
     * @param id 拼团活动ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return groupBuyService.removeById(id);
    }

    /**
     * 批量删除拼团活动
     *
     * @param ids 拼团活动ID列表
     * @return 删除结果
     */
    @DeleteMapping("/batch")
    public boolean deleteBatch(@RequestBody List<Long> ids) {
        return groupBuyService.removeByIds(ids);
    }

    /**
     * 创建拼团
     *
     * @param groupBuyId 拼团活动ID
     * @param userId     用户ID
     * @param productId  商品ID
     * @return 拼团记录ID
     */
    @PostMapping("/create-group")
    public Result createGroup(@RequestParam Long groupBuyId, @RequestParam Long userId, @RequestParam Long productId) {
        Long groupRecordId = groupBuyService.createGroup(groupBuyId, userId, productId);
        return Result.success(groupRecordId);
    }

    /**
     * 加入拼团
     *
     * @param groupRecordId 拼团记录ID
     * @param userId        用户ID
     * @return 加入结果
     */
    @PostMapping("/join-group")
    public Result joinGroup(@RequestParam Long groupRecordId, @RequestParam Long userId) {
        boolean result = groupBuyService.joinGroup(groupRecordId, userId);
        return result ? Result.success() : Result.fail(500, "加入拼团失败");
    }

    /**
     * 取消拼团
     *
     * @param groupRecordId 拼团记录ID
     * @param userId        用户ID
     * @return 取消结果
     */
    @PostMapping("/cancel-group")
    public Result cancelGroup(@RequestParam Long groupRecordId, @RequestParam Long userId) {
        boolean result = groupBuyService.cancelGroup(groupRecordId, userId);
        return result ? Result.success() : Result.fail(500, "取消拼团失败");
    }

    /**
     * 完成拼团
     *
     * @param groupRecordId 拼团记录ID
     * @return 完成结果
     */
    @PostMapping("/complete-group")
    public Result completeGroup(@RequestParam Long groupRecordId) {
        boolean result = groupBuyService.completeGroup(groupRecordId);
        return result ? Result.success() : Result.fail(500, "完成拼团失败");
    }

}
