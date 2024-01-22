package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 新增套餐與對應菜品
     *
     * @param setmealDTO
     */
    @Transactional
    public void saveWithDishes(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        // 向套餐表插入1條資料 (一次只能新增一個套餐)
        setmealMapper.insert(setmeal);
        Long setmealId = setmeal.getId();

        //向套餐菜品表插入N條資料(至少1)
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        //向套餐菜品表插入資料
        setmealDishMapper.insertBatch(setmealDishes);

    }

    /**
     * 菜品分頁查詢
     *
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 套餐批量刪除
     *
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {

        //起售中不可刪除
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                //當前套餐銷售中
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

        //刪除套餐表中的資料
        setmealMapper.deleteById(ids);
        //刪除套餐菜品關聯資料
        setmealDishMapper.deleteSetmealById(ids);

    }

    /**
     * 根據id查詢套餐與關聯菜品資料
     *
     * @param id
     * @return
     */
    public SetmealVO getByIdWithDish(Long id) {
        //根據id查詢套餐資料
        Setmeal setmeal = setmealMapper.getById(id);

        //根據套餐id查詢關聯菜品資料
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);

        //將查詢到的資料封裝給VO
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }


    /**
     * 根據id修改套餐與關聯菜品資料
     *
     * @param setmealDTO
     */
    public void updateWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //修改套餐表資料
        setmealMapper.update(setmeal);
        Long setmealId = setmealDTO.getId();

        //刪除原有的菜品關聯資料
        setmealDishMapper.deleteByDishIds(setmealDTO.getId());

        //重新插入菜品關聯資料
        List<SetmealDish> dishes = setmealDTO.getSetmealDishes();
        dishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId );
        });

        //向套餐菜品表插入資料
        setmealDishMapper.insertBatch(dishes);
    }

    /**
     * 啟用禁用套餐
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        // sql: update setmeal set status = ? where id = ?

        //起售時，判定是否有停售菜品
        if(status==StatusConstant.ENABLE){
            List<Dish> dishList = dishMapper.getBySetmealId(id);
            if(dishList!=null&&dishList.size()>0){
                for (Dish dish : dishList) {
                    if (StatusConstant.DISABLE==dish.getStatus()){
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                }
            }
        }


        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);

        setmealMapper.update(setmeal);

    }
}
