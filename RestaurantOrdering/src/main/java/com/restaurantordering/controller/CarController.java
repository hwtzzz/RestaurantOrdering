package com.restaurantordering.controller;


import com.restaurantordering.entity.Car;
import com.restaurantordering.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    CarService carService;

    /**
     * 添加购物车
     *
     * @param userId
     * @param dishesId
     */
    @GetMapping("addCar/{userId}/{dishesId}")
    public int addCar(@PathVariable Integer userId, @PathVariable Integer dishesId) {
        return carService.addCar(userId, dishesId);
    }

    @GetMapping("getMyCarCount/{userId}")
    public int getMyCarCount(@PathVariable Integer userId) {
        return carService.getMyCarCount(userId);
    }

    @GetMapping("getMyCarList/{userId}")
    public List<Car> getMyCarList(@PathVariable Integer userId) {
        return carService.getMyCarList(userId);
    }

    @GetMapping("deleteCar/{carId}")
    public List<Car> deleteCar(@PathVariable Integer carId) {
        return carService.deleteCar(carId);
    }

}

