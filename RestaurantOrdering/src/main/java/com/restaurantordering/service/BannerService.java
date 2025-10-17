package com.restaurantordering.service;

import com.restaurantordering.entity.Banner;
import com.restaurantordering.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建于  2025-03-08
 *
 * @Version: 1.0
 */
@Service
public class BannerService {

    @Autowired
    BannerMapper bannerMapper;

    public List<Banner> getIndexBanner() {
        return bannerMapper.selectList(null);
    }

    public List<Banner> addBanner(Banner banner) {
        bannerMapper.insert(banner);
        return getIndexBanner();
    }

    public void updateBanner(Banner banner) {
        bannerMapper.updateById(banner);
    }

    public List<Banner> deleteBanner(Integer bannerId) {
        bannerMapper.deleteById(bannerId);
        return getIndexBanner();
    }
}
