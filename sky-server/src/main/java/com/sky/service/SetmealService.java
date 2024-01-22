package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

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
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根據id查詢套餐與關聯菜品資料
     *
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * 根據id修改套餐與關聯菜品資料
     *
     * @param setmealDTO
     */
    void updateWithDish(SetmealDTO setmealDTO);
}
