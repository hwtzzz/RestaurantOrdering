package com.restaurantordering.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2025-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("orders_item")
public class OrdersItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ordersItemId", type = IdType.AUTO)
    private Integer ordersItemId;

    /**
     * 订单编号
     */
    private Integer ordersId;

    /**
     * 食品数量
     */
    private Integer count;

    /**
     * 此条食品总金额
     */
    private BigDecimal total;

    /**
     * 食品名称
     */
    private String dishesName;
}
