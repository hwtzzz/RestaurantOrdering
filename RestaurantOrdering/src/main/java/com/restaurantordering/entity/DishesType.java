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
@TableName("dishes_type")
public class DishesType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dishesTypeId", type = IdType.AUTO)
    private Integer dishesTypeId;

    /**
     * 分类名称
     */
    private String dishesTypeName;

    /**
     * 分类描述
     */
    private String dishesTypeDesc;


}
