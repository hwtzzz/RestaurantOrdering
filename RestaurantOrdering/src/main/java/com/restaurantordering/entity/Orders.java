package com.restaurantordering.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurantordering.config.AppConsts;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ordersId", type = IdType.AUTO)
    private Integer ordersId;

    /**
     * 用户信息
     */
    private Integer userId;

    /**
     * 总金额
     */
    private String totalPrice;

    /**
     * 下单时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConsts.dateTimeFormatPattern, timezone = "GMT+8")
    @DateTimeFormat(pattern = AppConsts.dateTimeFormatPattern)
    private Date orderTime;

    /**
     * 订单状态 1已下单 2制作中 3已完成 4已评价
     */
    private Integer flag;

    private Integer star;

    private String appraise;

}
