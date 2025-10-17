package com.restaurantordering.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建于  2025-03-11
 *
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexAppraiseDto {

    private String appraise;

    private String name;

    private String avatar;

    private Integer star;

}
