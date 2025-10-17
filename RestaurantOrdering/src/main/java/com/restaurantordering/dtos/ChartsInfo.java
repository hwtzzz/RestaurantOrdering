package com.restaurantordering.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 创建于  2025-02-24
 *
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartsInfo {

    private Integer totalPeople;

    private Integer ordersTotal;

    private Integer dishesTotal;

    private String priceTotal;

    private List<DishesTypeChartsDto> serveList;

    private List<LoginChartsDto> loginChartsList;

    private List<OrderPriceCharts> list;

}
