package com.restaurantordering.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurantordering.entity.Dishes;
import com.restaurantordering.service.DishesService;
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
@RequestMapping("/api/dishes")
public class DishesController {

    @Autowired
    DishesService dishesService;

    /**
     * 获取所有菜品分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/getDishesList/{currentPage}/{pageSize}")
    public Page<Dishes> getDishesList(@PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        return dishesService.getDishesList(currentPage, pageSize);
    }

    /**
     * 修改菜品信息
     *
     * @param dishes
     */
    @PostMapping("/updateDishes")
    public void updateDishes(@RequestBody Dishes dishes) {
        dishesService.updateDishes(dishes);
    }

    /**
     * 添加菜品
     *
     * @param dishes
     * @return
     */
    @PostMapping("/addDishes")
    public Page<Dishes> addTeacher(@RequestBody Dishes dishes) {
        return dishesService.addDishes(dishes);
    }

    /**
     * 删除菜品信息
     *
     * @param dishesId
     * @return
     */
    @GetMapping("/deleteDishes/{dishesId}")
    public Page<Dishes> deleteDishes(@PathVariable Integer dishesId) {
        return dishesService.deleteDishes(dishesId);
    }

    /**
     * 查找菜品
     *
     * @param dishes
     * @return
     */
    @PostMapping("/searchDishes")
    public List<Dishes> searchDishes(@RequestBody Dishes dishes) {
        return dishesService.searchDishes(dishes.getDishesName(), dishes.getDishesType());
    }

    //获取主页三条热门菜品
    @GetMapping("getIndexDishes")
    public List<Dishes> getIndexDishes(){
        return dishesService.getIndexDishes();
    }

    @GetMapping("getAllDishes")
    public List<Dishes> getAllDishes(){
        return dishesService.getAllDishes();
    }

}

