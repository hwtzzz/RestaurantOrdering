package com.restaurantordering.config;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class JsonResult<T> implements Serializable {
    private boolean success; //是否成功
    private T result; //返回结果
    private String msg; //提示信息
    private int resultCode; //通常作为错误代码，通常用不到

    public JsonResult(boolean success, T result, String message, int resultCode) {
        this.success = success;
        this.result = result;
        this.msg = message;
        this.resultCode = resultCode;
    }


    public static <T> JsonResult<T> success() {
        return new JsonResult<T>(true, null, null, 0);
    }

    public static <T> JsonResult<T> success(T result) {
        return new JsonResult<T>(true, result, null, 0);
    }

    public static <T> JsonResult<T> success(T result, String message) {
        return new JsonResult<T>(true, result, message, 0);
    }

    public static <T> JsonResult<T> fail(String message) {
        return new JsonResult<>(false, null, message, 200);
    }

    public static <T> JsonResult<T> fail(String message, int resultCode) {
        return new JsonResult<T>(false, null, message, resultCode);
    }
}