package com.restaurantordering.mapper;

import com.restaurantordering.dtos.DishesTypeChartsDto;
import com.restaurantordering.entity.DishesType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2025-03-06
 */
@Mapper
public interface DishesTypeMapper extends BaseMapper<DishesType> {

    @Select("" +
            "SELECT\n" +
            "\tdt.dishesTypeName,\n" +
            "\tCOUNT( d.dishesId ) AS count \n" +
            "FROM\n" +
            "\tdishes_type dt\n" +
            "\tLEFT JOIN dishes d ON dt.dishesTypeId = d.dishesType \n" +
            "GROUP BY\n" +
            "\tdt.dishesTypeId,\n" +
            "\tdt.dishesTypeName \n" +
            "ORDER BY\n" +
            "\tdt.dishesTypeId;" +
            "")
    List<DishesTypeChartsDto> selectDishesTypeCount();
}
