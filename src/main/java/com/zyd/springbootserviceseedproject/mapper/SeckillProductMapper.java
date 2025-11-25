package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.SeckillProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 秒杀商品表 Mapper 接口
 *
 * @author zyd
 * @since 2024-05-20
 */
@Mapper
public interface SeckillProductMapper extends BaseMapper<SeckillProductEntity> {

    /**
     * 扣减秒杀商品库存
     * @param id 秒杀商品ID
     * @param quantity 扣减数量
     * @return 影响行数
     */
    @Update("UPDATE seckill_product SET available_stock = available_stock - #{quantity}, sold_stock = sold_stock + #{quantity} WHERE id = #{id} AND available_stock >= #{quantity}")
    int decreaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    /**
     * 恢复秒杀商品库存
     * @param id 秒杀商品ID
     * @param quantity 恢复数量
     * @return 影响行数
     */
    @Update("UPDATE seckill_product SET available_stock = available_stock + #{quantity}, sold_stock = sold_stock - #{quantity} WHERE id = #{id}")
    int increaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);
}
