package com.restaurantordering.config.wrapResult;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.restaurantordering.config.JsonResult;
import com.restaurantordering.utils.HttpServletUtil;
import com.restaurantordering.utils.UserFriendlyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@ControllerAdvice
@Slf4j
public class CustomException {
    //private Logger logger = LoggerFactory.getLogger(this.getClass());
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult<Object> errorHandler(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            String msg = "";
            for (ObjectError err : e.getBindingResult().getAllErrors()) {
                msg = err.getDefaultMessage() + "；" + msg;
            }

            log.warn(
                    "异常警告：参数验证失败（" + msg + "） <br/>\n"
                            + "异常类型：" + ex.toString() + " <br/>\n"
                            + "异常位置：" + ex.getStackTrace()[0].toString() + " <br/>\n"
                            + getRequestInfo()
            );
            return JsonResult.fail(msg);
        } else if (ex instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException e = (HttpMessageNotReadableException) ex;
            log.warn(
                    "异常警告：资源不存在（" + ex.getMessage() + "） <br/>\n"
                            + "异常类型：" + ex.toString() + " <br/>\n"
                            + "异常位置：" + ex.getStackTrace()[0].toString() + " <br/>\n"
                            + getRequestInfo()
            );
            return JsonResult.fail(e.getCause().getMessage().split("\\n")[0]);
        } else if (ex instanceof NoHandlerFoundException) {
            log.warn(
                    "异常警告：资源不存在（" + ex.getMessage() + "） <br/>\n"
                            + "异常类型：" + ex.toString() + " <br/>\n"
                            + "异常位置：" + ex.getStackTrace()[0].toString() + " <br/>\n"
                            + getRequestInfo()
            );
            return JsonResult.fail("资源不存在：" + ex.getMessage(), 404);
        } else if (ex instanceof UserFriendlyException) {
            if (!ex.getMessage().equals("请先登录")) {
                log.warn(
                        "异常警告：" + ex.getMessage() + " <br/>\n"
                                + "异常类型：" + ex.toString() + " <br/>\n"
                                + "异常位置：" + ex.getStackTrace()[0].toString() + " <br/>\n"
                                + getRequestInfo()
                );
            }
            Integer c = ((UserFriendlyException) ex).getErrorCode();
            return JsonResult.fail(ex.getMessage(), c);
        } else {
            //内部异常记录详细异常信息
            log.error(
                    "异常错误：" + ex.getMessage() + "<br/>\n"
                            + "异常类型：" + ex.toString() + " <br/>\n"
                            + "异常位置：" + ex.getStackTrace()[0].toString() + " <br/>\n"
                            + getRequestInfo()
            );
            //return JsonResult.fail(ex.getMessage());
            return JsonResult.fail("该请求出错了！");
        }
    }

    String getRequestInfo() {
        HttpServletRequest request = HttpServletUtil.getRequest();
        if (request == null) {
            return "";
        }
        //方法
        String method = request.getMethod();
        //URL
        String url = request.getRequestURL().toString();
        //URL参数
        String urlParams = request.getQueryString();
        if (StringUtils.isNotBlank(urlParams)) {
            url += "?" + urlParams;
        }
        //body参数
        // String s=IOUtils.toString(request.getReader());
        String bodyParams = "";
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            bodyParams = responseStrBuilder.toString();
        } catch (Exception ex) {

        }

        //headers 仅获取必要的参数
//        String token = request.getHeader(AppConsts.apiHeaderTokenKey);

        return "请求地址: " + url + " <br/>\n"
                + "请求方法: " + method + " <br/>\n"
                + "Body参数: " + bodyParams + "\n\n";
    }

}