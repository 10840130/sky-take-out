package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;


    /**
     * 訂單搜尋
     *
     * @param ordersPageQueryDTO
     * @return
     */
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        //開始分頁查詢
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());

        Page<Orders> page = orderMapper.pageQuery(ordersPageQueryDTO);

        List<OrderVO> orderVOList = getOrderVOList(page);


        return new PageResult(page.getTotal(), orderVOList);
    }

    private List<OrderVO> getOrderVOList(Page<Orders> page) {
        List<OrderVO> orderVOList = new ArrayList<>();

        List<Orders> ordersList = page.getResult();
        if (!CollectionUtils.isEmpty(ordersList)) {
            for (Orders orders : ordersList) {

                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                String orderDishes = getOrderDishesStr(orders);

                orderVO.setOrderDishes(orderDishes);
                orderVOList.add(orderVO);
            }
        }
        return orderVOList;
    }

    /**
     * 根據訂單ID獲取菜品訊息
     * @param orders
     * @return
     */
    private String getOrderDishesStr(Orders orders) {
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());

        List<String> orderDishList = orderDetailList.stream().map(x->{
            String orderDish = x.getName()+"*"+x.getNumber()+";";
            return orderDish;
        }).collect(Collectors.toList());
        return String.join("",orderDishList);
    }


    /**
     * 各狀態的訂單統計
     * @return
     */
    public OrderStatisticsVO statistics() {
        //根據狀態，分別查詢訂單數量
        Integer toBeConfirmed =orderMapper.countStatus(Orders.TO_BE_CONFIRMED);
        Integer Confirmed =orderMapper.countStatus(Orders.CONFIRMED);
        Integer deliveryInProgress =orderMapper.countStatus(Orders.DELIVERY_IN_PROGRESS);

        //將資料封裝給orderStatisticsVO
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
        orderStatisticsVO.setConfirmed(Confirmed);
        orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);
        return orderStatisticsVO;

    }

    /**
     * 查詢訂單詳情
     * @param id
     * @return
     */
    public OrderVO details(Long id) {
        //根據id查詢訂單
        Orders orders = orderMapper.getById(id);
        //查詢該訂單的菜品/明細
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());
        //將該訂單封裝進OrderVO
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders,orderVO);
        orderVO.setOrderDetailList(orderDetailList);

        return orderVO;
    }

    /**
     * 接單
     * @param ordersConfirmDTO
     */
    public void confrim(OrdersConfirmDTO ordersConfirmDTO) {
        Orders orders = Orders.builder()
                .id(ordersConfirmDTO.getId())
                .status(Orders.CONFIRMED)
                .build();

        orderMapper.update(orders);
    }
}
