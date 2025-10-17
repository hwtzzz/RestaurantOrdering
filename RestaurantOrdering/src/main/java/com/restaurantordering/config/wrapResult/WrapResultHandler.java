package com.restaurantordering.config.wrapResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantordering.config.JsonResult;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@ControllerAdvice
public class WrapResultHandler implements ResponseBodyAdvice<Object> {
    @Resource
    ObjectMapper objectMapper;

    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        boolean dontWrapResult = returnType.getMethod().isAnnotationPresent(DontWrapResult.class);
        return !dontWrapResult;

        /*Object s = request.getAttribute(RESPONSE_RESULT_ANN);
        //判断请求是否有包装标志
        WrapResult wrapResult = (WrapResult) request.getAttribute(RESPONSE_RESULT_ANN);
        return wrapResult == null ? false : true;*/
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof JsonResult) {
            return body;
        } else if (body instanceof String) {
            return objectMapper.writeValueAsString(JsonResult.success(body));
        } else {
            return JsonResult.success(body);
        }
    }
}
