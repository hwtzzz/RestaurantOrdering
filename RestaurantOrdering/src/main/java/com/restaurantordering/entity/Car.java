package com.restaurantordering.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "carId", type = IdType.AUTO)
    private Integer carId;

    /**
     * 食品信息
     */
    private Integer dishesId;

    /**
     * 食品数量
     */
    private Integer count;

    /**
     * 用户信息
     */
    private Integer userId;


}
