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
@TableName("dishes")
public class Dishes implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dishesId", type = IdType.AUTO)
    private Integer dishesId;

    /**
     * 菜品名称
     */
    private String dishesName;

    /**
     * 菜品图片
     */
    private String dishesImage;

    /**
     * 菜品库存
     */
    private Integer dishesCount;

    /**
     * 菜品介绍
     */
    private String dishesDesc;

    /**
     * 菜品分类
     */
    private Integer dishesType;

    /**
     * 菜品价格
     */
    private Double price;

    private Integer count;

}
