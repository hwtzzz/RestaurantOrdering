package com.restaurantordering.config.wrapResult;

import java.lang.annotation.*;

/**
 * 添加该注解的方法直接返回结果，不适用JsonResult进行包装
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DontWrapResult {
}
