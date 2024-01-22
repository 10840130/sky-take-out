package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根據菜品id查詢對應的套餐id
     * @param dishIds
     * @return
     */

    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 批量插入套餐菜品資料
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);


    /**
     * 根據套餐id集合，刪除套餐資料
     * @param setmealIds
     */
    void deleteSetmealById(List<Long> setmealIds);

    /**
     * 根據菜品id刪除口味資料
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteByDishIds(Long setmealId);

    /**
     * 根據套餐id查詢對應菜品資料
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);
}
