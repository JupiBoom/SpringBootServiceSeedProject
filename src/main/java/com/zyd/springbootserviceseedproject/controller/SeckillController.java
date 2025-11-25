package com.zyd.springbootserviceseedproject.controller;

import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.entity.SeckillProductEntity;
import com.zyd.springbootserviceseedproject.entity.SeckillOrderEntity;
import com.zyd.springbootserviceseedproject.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 秒杀商品表 Controller
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
     * 根据活动ID查询秒杀商品列表
     * @param activityId 活动ID
     * @return 秒杀商品列表
     */
    @GetMapping("/listByActivityId/{activityId}")
    public Result<List<SeckillProductEntity>> listByActivityId(@PathVariable Long activityId) {
        List<SeckillProductEntity> seckillProductList = seckillService.listByActivityId(activityId);
        return Result.success(seckillProductList);
    }

    /**
     * 预热秒杀商品库存到Redis
     * @param seckillProductId 秒杀商品ID
     * @return 预热结果
     */
    @PostMapping("/preheatStock/{seckillProductId}")
    public Result<Boolean> preheatStockToRedis(@PathVariable Long seckillProductId) {
        boolean success = seckillService.preheatStockToRedis(seckillProductId);
        return success ? Result.success(true) : Result.fail("预热失败");
    }

    /**
     * 检查用户是否有秒杀资格
     * @param seckillProductId 秒杀商品ID
     * @param userId 用户ID
     * @param ip 用户IP
     * @return 检查结果
     */
    @GetMapping("/checkQualification")
    public Result<Boolean> checkSeckillQualification(@RequestParam Long seckillProductId, @RequestParam Long userId, @RequestParam String ip) {
        boolean hasQualification = seckillService.checkSeckillQualification(seckillProductId, userId, ip);
        return Result.success(hasQualification);
    }

    /**
     * 创建秒杀订单
     * @param seckillProductId 秒杀商品ID
     * @param userId 用户ID
     * @param quantity 购买数量
     * @return 秒杀订单
     */
    @PostMapping("/createOrder")
    public Result<SeckillOrderEntity> createSeckillOrder(@RequestParam Long seckillProductId, @RequestParam Long userId, @RequestParam Integer quantity) {
        SeckillOrderEntity seckillOrder = seckillService.createSeckillOrder(seckillProductId, userId, quantity);
        return seckillOrder != null ? Result.success(seckillOrder) : Result.fail("创建订单失败");
    }

    /**
     * 根据订单号查询秒杀订单
     * @param orderNo 订单号
     * @return 秒杀订单
     */
    @GetMapping("/getOrderByNo/{orderNo}")
    public Result<SeckillOrderEntity> getSeckillOrderByOrderNo(@PathVariable String orderNo) {
        SeckillOrderEntity seckillOrder = seckillService.getSeckillOrderByOrderNo(orderNo);
        return seckillOrder != null ? Result.success(seckillOrder) : Result.fail("订单不存在");
    }

    /**
     * 更新秒杀订单状态
     * @param orderNo 订单号
     * @param status 订单状态：1-待支付，2-已支付，3-已取消，4-已完成
     * @return 更新结果
     */
    @PutMapping("/updateOrderStatus/{orderNo}/{status}")
    public Result<Boolean> updateSeckillOrderStatus(@PathVariable String orderNo, @PathVariable Integer status) {
        boolean success = seckillService.updateSeckillOrderStatus(orderNo, status);
        return success ? Result.success(true) : Result.fail("更新失败");
    }

    /**
     * 处理超时未支付的秒杀订单
     * @param orderNo 订单号
     * @return 处理结果
     */
    @PostMapping("/handleTimeoutOrder/{orderNo}")
    public Result<Boolean> handleTimeoutSeckillOrder(@PathVariable String orderNo) {
        boolean success = seckillService.handleTimeoutSeckillOrder(orderNo);
        return success ? Result.success(true) : Result.fail("处理失败");
    }

    /**
     * 创建秒杀商品
     * @param seckillProduct 秒杀商品信息
     * @return 创建结果
     */
    @PostMapping("/createProduct")
    public Result<Boolean> createSeckillProduct(@RequestBody SeckillProductEntity seckillProduct) {
        boolean success = seckillService.save(seckillProduct);
        return success ? Result.success(true) : Result.fail("创建失败");
    }

    /**
     * 更新秒杀商品
     * @param seckillProduct 秒杀商品信息
     * @return 更新结果
     */
    @PutMapping("/updateProduct")
    public Result<Boolean> updateSeckillProduct(@RequestBody SeckillProductEntity seckillProduct) {
        boolean success = seckillService.updateById(seckillProduct);
        return success ? Result.success(true) : Result.fail("更新失败");
    }

    /**
     * 删除秒杀商品
     * @param seckillProductId 秒杀商品ID
     * @return 删除结果
     */
    @DeleteMapping("/deleteProduct/{seckillProductId}")
    public Result<Boolean> deleteSeckillProduct(@PathVariable Long seckillProductId) {
        boolean success = seckillService.removeById(seckillProductId);
        return success ? Result.success(true) : Result.fail("删除失败");
    }

    /**
     * 根据ID查询秒杀商品
     * @param seckillProductId 秒杀商品ID
     * @return 秒杀商品信息
     */
    @GetMapping("/getProductById/{seckillProductId}")
    public Result<SeckillProductEntity> getSeckillProductById(@PathVariable Long seckillProductId) {
        SeckillProductEntity seckillProduct = seckillService.getById(seckillProductId);
        return seckillProduct != null ? Result.success(seckillProduct) : Result.fail("秒杀商品不存在");
    }

    /**
     * 查询所有秒杀商品
     * @return 秒杀商品列表
     */
    @GetMapping("/listAllProducts")
    public Result<List<SeckillProductEntity>> listAllSeckillProducts() {
        List<SeckillProductEntity> seckillProductList = seckillService.list();
        return Result.success(seckillProductList);
    }
}