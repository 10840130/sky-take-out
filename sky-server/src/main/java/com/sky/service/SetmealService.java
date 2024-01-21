package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface SetmealService {
    /**
     * 新增套餐與對應菜品
     *
     * @param setmealDTO
     */
    public void saveWithDishes(SetmealDTO setmealDTO);


    /**
     * 菜品分頁查詢
     *
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 套餐批量刪除
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
