package com.sky.service;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;


public interface OrderService {

    /**
     * 條件搜尋訂單
     *
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各狀態的訂單統計
     *
     * @return
     */
    OrderStatisticsVO statistics();

    /**
     * 查詢訂單詳情
     *
     * @param id
     * @return
     */
    OrderVO details(Long id);

    /**
     * 接單
     *
     * @param ordersConfirmDTO
     */
    void confrim(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 拒單
     *
     * @return
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * 取消訂單
     * @return
     */
    void cancel(OrdersCancelDTO ordersCancelDTO)throws Exception;
}
