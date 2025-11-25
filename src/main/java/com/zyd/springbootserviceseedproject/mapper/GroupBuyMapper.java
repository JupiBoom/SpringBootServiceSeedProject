package com.zyd.springbootserviceseedproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyd.springbootserviceseedproject.entity.GroupBuyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 拼团表 Mapper 接口
 *
 * @author zyd
 * @since 2024-05-20
 */
@Mapper
public interface GroupBuyMapper extends BaseMapper<GroupBuyEntity> {

    /**
     * 扣减拼团商品库存
     * @param id 拼团ID
     * @param quantity 扣减数量
     * @return 影响行数
     */
    @Update("UPDATE group_buy SET available_stock = available_stock - #{quantity}, sold_stock = sold_stock + #{quantity} WHERE id = #{id} AND available_stock >= #{quantity}")
    int decreaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    /**
     * 恢复拼团商品库存
     * @param id 拼团ID
     * @param quantity 恢复数量
     * @return 影响行数
     */
    @Update("UPDATE group_buy SET available_stock = available_stock + #{quantity}, sold_stock = sold_stock - #{quantity} WHERE id = #{id}")
    int increaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);
}
