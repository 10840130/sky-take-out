package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    
    /**
     * 新增菜品與對應口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分頁查詢
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品批量刪除
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根據id查詢菜品與對應口味資料
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 根據id修改菜品與對應的口味資料
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 根據分類id查詢菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);
}
