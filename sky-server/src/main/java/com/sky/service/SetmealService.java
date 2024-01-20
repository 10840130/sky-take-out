package com.sky.service;

import com.sky.dto.SetmealDTO;

public interface SetmealService {

    /**
     * 新增套餐與對應菜品
     * @param setmealDTO
     */
    public void saveWithDishes(SetmealDTO setmealDTO);
}
