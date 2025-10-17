package com.restaurantordering.config.wrapResult;

import java.lang.annotation.*;

/**
 * 添加该注解的方法将对返回结果添加JsonResult包装
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WrapResult {
}
