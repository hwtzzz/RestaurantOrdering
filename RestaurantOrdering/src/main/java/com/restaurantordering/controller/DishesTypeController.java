package com.restaurantordering.controller;


import com.restaurantordering.entity.DishesType;
import com.restaurantordering.service.DishesTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2025-03-06
 */
@RestController
@RequestMapping("/api/dishesType")
public class DishesTypeController {

    @Autowired
    DishesTypeService dishesTypeService;


    /**
     * 获取所有分类
     *
     * @return
     */
    @GetMapping("/getDishesTypeList")
    public List<DishesType> getDishesTypeList() {
        return dishesTypeService.getDishesTypeList();
    }

    /**
     * 修改分类信息
     *
     * @param dishesType
     */
    @PostMapping("/updateDishesType")
    public List<DishesType> updateDishesType(@RequestBody DishesType dishesType) {
        return dishesTypeService.updateDishesType(dishesType);
    }

    /**
     * 添加分类
     *
     * @param dishesType
     * @return
     */
    @PostMapping("/addDishesType")
    public List<DishesType> addTeacher(@RequestBody DishesType dishesType) {
        return dishesTypeService.addDishesType(dishesType);
    }

    /**
     * 删除分类信息
     *
     * @param dishesTypeId
     * @return
     */
    @GetMapping("/deleteDishesType/{dishesTypeId}")
    public List<DishesType> deleteDishesType(@PathVariable Integer dishesTypeId) {
        return dishesTypeService.deleteDishesType(dishesTypeId);
    }

    /**
     * 查找分类
     *
     * @param dishesTypeName
     * @return
     */
    @GetMapping("/searchDishesType/{dishesTypeName}")
    public List<DishesType> searchDishesType(@PathVariable String dishesTypeName) {
        return dishesTypeService.searchDishesType(dishesTypeName);
    }

}

