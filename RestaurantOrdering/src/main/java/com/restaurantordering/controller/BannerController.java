package com.restaurantordering.controller;

import com.restaurantordering.entity.Banner;
import com.restaurantordering.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 创建于  2025-03-08
 *
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Autowired
    BannerService bannerService;

    @GetMapping("/getIndexBanner")
    public List<Banner> getIndexBanner(){
        return bannerService.getIndexBanner();
    }

    /**
     * 添加轮播图
     * @param banner
     * @return
     */
    @PostMapping("/addBanner")
    public List<Banner> addBanner(@RequestBody Banner banner) {
        return bannerService.addBanner(banner);
    }

    /**
     * 修改轮播图
     * @param banner
     */
    @PostMapping("/updateBanner")
    public void updateBanner(@RequestBody Banner banner) {
        bannerService.updateBanner(banner);
    }

    /**
     * 删除轮播图
     * @param bannerId
     * @return
     */
    @GetMapping("/deleteBanner/{bannerId}")
    public List<Banner> deleteBanner(@PathVariable Integer bannerId) {
        return bannerService.deleteBanner(bannerId);
    }

}
