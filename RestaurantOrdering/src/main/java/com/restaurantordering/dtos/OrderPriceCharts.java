package com.restaurantordering.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurantordering.config.AppConsts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 创建于  2025-03-11
 *
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPriceCharts {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConsts.dateFormatPattern, timezone = "GMT+8")
    @DateTimeFormat(pattern = AppConsts.dateFormatPattern)
    private Date orderTime;

    private String totalPrice;

}
