package com.restaurantordering.controller;


import com.restaurantordering.dtos.IndexAppraiseDto;
import com.restaurantordering.dtos.OrdersListDto;
import com.restaurantordering.dtos.PageDto;
import com.restaurantordering.entity.Dishes;
import com.restaurantordering.entity.Orders;
import com.restaurantordering.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2025-03-06
 */
@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    /**
     * 获取所有订单分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/getOrdersList/{currentPage}/{pageSize}")
    public PageDto getOrdersList(@PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        return ordersService.getOrdersList(currentPage, pageSize);
    }

    @GetMapping("/updateOrderFlag/{ordersId}/{flag}")
    public PageDto updateOrderFlag(@PathVariable Integer ordersId, @PathVariable Integer flag) {
        return ordersService.updateOrderFlag(ordersId, flag);
    }

    @GetMapping("/searchOrder/{flag}")
    public List<OrdersListDto> searchOrder(@PathVariable Integer flag) {
        return ordersService.searchOrder(flag);
    }

    @GetMapping("/addOrder/{userId}")
    public List<Dishes> addOrder(@PathVariable Integer userId) {
        return ordersService.addOrder(userId);
    }

    @GetMapping("/getMyOrders/{userId}")
    public List<OrdersListDto> getMyOrders(@PathVariable Integer userId){
        return ordersService.getMyOrders(userId);
    }

    @GetMapping("/addAppraise/{ordersId}/{star}/{appraise}")
    public List<OrdersListDto> addAppraise(@PathVariable Integer ordersId,
                                           @PathVariable Integer star,
                                           @PathVariable String appraise){
        return ordersService.addAppraise(ordersId , star , appraise);
    }

    @GetMapping("/getIndexAppraise")
    public List<IndexAppraiseDto> getIndexAppraise(){
        return ordersService.getIndexAppraise();
    }

}

