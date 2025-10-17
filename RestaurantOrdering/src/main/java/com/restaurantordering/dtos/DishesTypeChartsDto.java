package com.restaurantordering.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 创建于  2025-02-24
 *
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class DishesTypeChartsDto {

    private String dishesTypeName;

    private Integer count;

}
