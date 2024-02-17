package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.OrderDetail;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
@Slf4j
@Api(tags = "訂單管理介面")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 訂單搜尋
     *
     * @param ordersPageQueryDTO
     * @return
     */
    @GetMapping("/conditionSearch")
    @ApiOperation("訂單搜尋")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageResult pageResult = orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 各狀態的訂單統計
     *
     * @return
     */
    @GetMapping("/statistics")
    @ApiOperation("各狀態的訂單統計")
    public Result<OrderStatisticsVO> statistics() {
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }


    /**
     * 查詢訂單詳情
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    @ApiOperation("查詢訂單詳情")
    public Result<OrderVO> details(@PathVariable("id") Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * 接單
     * @return
     */
    @PutMapping("/confirm")
    @ApiOperation("接單")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        orderService.confrim(ordersConfirmDTO);
        return Result.success();
    }

    /**
     * 拒單
     * @return
     */
    @PutMapping("/rejection")
    @ApiOperation("拒單")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception{
        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * 取消訂單
     * @return
     */
    @PutMapping("/cancel")
    @ApiOperation("取消訂單")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) throws Exception{
        orderService.cancel(ordersCancelDTO);
        return Result.success();
    }

    /**
     * 派送訂單
     * @return
     */
    @PutMapping("/delivery/{id}")
    @ApiOperation("派送訂單")
    public Result delivery(@PathVariable Long id) {
        orderService.delivery(id);
        return Result.success();
    }

    /**
     * 完成訂單
     * @return
     */
    @PutMapping("/complete/{id}")
    @ApiOperation("完成訂單")
    public Result complete(@PathVariable Long id) {
        orderService.complete(id);
        return Result.success();
    }

}
