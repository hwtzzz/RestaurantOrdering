package com.restaurantordering.mapper;

import com.restaurantordering.dtos.IndexAppraiseDto;
import com.restaurantordering.dtos.OrderPriceCharts;
import com.restaurantordering.dtos.OrdersListDto;
import com.restaurantordering.entity.Orders;
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
public interface OrdersMapper extends BaseMapper<Orders> {

    @Select("" +
            "SELECT\n" +
            "\tdate_sequence.DATE AS orderTime,\n" +
            "\tIFNULL( SUM( CAST( o.totalPrice AS DECIMAL ( 10, 2 ))), 0 ) AS totalPrice \n" +
            "FROM\n" +
            "\t(-- 生成近15天日期序列（含当前日期）\n" +
            "\tSELECT\n" +
            "\t\tCURDATE() - INTERVAL (\n" +
            "\t\ta.a + ( 10 * b.a ) + ( 100 * c.a )) DAY AS DATE \n" +
            "\tFROM\n" +
            "\t\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\t0 AS a UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t1 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t2 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t3 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t4 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t5 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t6 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t7 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t8 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t9 \n" +
            "\t\t) AS a\n" +
            "\t\tCROSS JOIN (\n" +
            "\t\tSELECT\n" +
            "\t\t\t0 AS a UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t1 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t2 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t3 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t4 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t5 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t6 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t7 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t8 UNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\t9 \n" +
            "\t\t) AS b\n" +
            "\t\tCROSS JOIN ( SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 ) AS c \n" +
            "\tWHERE\n" +
            "\t\t(\n" +
            "\t\ta.a + ( 10 * b.a ) + ( 100 * c.a )) <= 14 \n" +
            "\tORDER BY\n" +
            "\tDATE \n" +
            "\t) date_sequence\n" +
            "\tLEFT JOIN orders o ON DATE ( o.orderTime ) = date_sequence.DATE \n" +
            "GROUP BY\n" +
            "\tdate_sequence.DATE \n" +
            "ORDER BY\n" +
            "\tdate_sequence.DATE;" +
            "")
    List<OrderPriceCharts> selectOrderPrice();

    @Select("" +
            "SELECT\n" +
            "\to.appraise,\n" +
            "\to.star,\n" +
            "\tu.avatar,\n" +
            "\tu.`name` \n" +
            "FROM\n" +
            "\t`orders` o\n" +
            "\tLEFT JOIN USER u ON o.userId = u.userId \n" +
            "WHERE\n" +
            "\to.flag = 4" +
            "")
    List<IndexAppraiseDto> getIndexAppraise();
}
