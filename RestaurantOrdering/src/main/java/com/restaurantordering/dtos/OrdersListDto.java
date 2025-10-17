package com.restaurantordering.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurantordering.config.AppConsts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 创建于  2025-03-07
 *
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersListDto {

    private Integer ordersId;

    private Integer userId;

    private String totalPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConsts.dateTimeFormatPattern, timezone = "GMT+8")
    @DateTimeFormat(pattern = AppConsts.dateTimeFormatPattern)
    private Date orderTime;

    private String dishesNames;

    /**
     * 订单状态 1已下单 2制作中 3已完成 4已评价
     */
    private Integer flag;

    private String name;

    private String phone;

    private Integer star;

    private String appraise;

}
