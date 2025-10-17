package com.restaurantordering.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 将图片上传到本地服务器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //此处路径和上面的图片上传位置保持一致  就可以在浏览器看到本地文件了
        registry.addResourceHandler("/image/**").addResourceLocations("file:" + AppConsts.IMAGE_PATH);
    }
}