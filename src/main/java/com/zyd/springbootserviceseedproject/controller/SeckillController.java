package com.zyd.springbootserviceseedproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.entity.SeckillEntity;
import com.zyd.springbootserviceseedproject.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 秒杀活动Controller
 *
 * @author zyd
 * @since 2024-05-20
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 分页查询秒杀活动
     *
     * @param page 页码
     * @param size 每页数量
     * @return 分页结果
     */
    @GetMapping("/page")
    public Page<SeckillEntity> page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<SeckillEntity> pageParam = new Page<>(page, size);
        return seckillService.page(pageParam);
    }

    /**
     * 根据ID查询秒杀活动
     *
     * @param id 秒杀活动ID
     * @return 秒杀活动信息
     */
    @GetMapping("/{id}")
    public SeckillEntity getById(@PathVariable Long id) {
        return seckillService.getById(id);
    }

    /**
     * 创建秒杀活动
     *
     * @param seckill 秒杀活动信息
     * @return 创建结果
     */
    @PostMapping
    public boolean save(@RequestBody SeckillEntity seckill) {
        return seckillService.save(seckill);
    }

    /**
     * 更新秒杀活动
     *
     * @param seckill 秒杀活动信息
     * @return 更新结果
     */
    @PutMapping
    public boolean update(@RequestBody SeckillEntity seckill) {
        return seckillService.updateById(seckill);
    }

    /**
     * 删除秒杀活动
     *
     * @param id 秒杀活动ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return seckillService.removeById(id);
    }

    /**
     * 批量删除秒杀活动
     *
     * @param ids 秒杀活动ID列表
     * @return 删除结果
     */
    @DeleteMapping("/batch")
    public boolean deleteBatch(@RequestBody List<Long> ids) {
        return seckillService.removeByIds(ids);
    }

    /**
     * 秒杀活动下单
     *
     * @param seckillId 秒杀活动ID
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 下单结果
     */
    @PostMapping("/order")
    public Result seckill(@RequestParam Long seckillId, @RequestParam Long userId, @RequestParam Long productId) {
        boolean result = seckillService.seckill(seckillId, userId, productId);
        return result ? Result.success() : Result.fail(500, "秒杀失败");
    }

}
