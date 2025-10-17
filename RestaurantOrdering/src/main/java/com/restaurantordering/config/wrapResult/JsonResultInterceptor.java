package com.restaurantordering.config.wrapResult;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

//定义请求拦截器通过反射获取到有此注解的HandlerMethod设置包装拦截标志
public class JsonResultInterceptor implements HandlerInterceptor {

    //标记名称
    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.err.println("进入JsonResultInterceptor");

        //请求方法
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            //判断是否在对象上加了注解
            if (clazz.isAnnotationPresent(WrapResult.class)) {
                //设置此请求返回体需要包装，往下传递，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANN, clazz.getAnnotation(WrapResult.class));
                //方法体上是否有注解
            } else if (method.isAnnotationPresent(WrapResult.class)) {
                //设置此请求返回体需要包装，往下传递，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANN, clazz.getAnnotation(WrapResult.class));
            }
        }
        return true;
    }
}
