package com.restaurantordering.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建于  2025-03-08
 *
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {

    @TableId(value = "bannerId", type = IdType.AUTO)
    private Integer bannerId;

    private String title;

    private String description;

    private String image;

}
