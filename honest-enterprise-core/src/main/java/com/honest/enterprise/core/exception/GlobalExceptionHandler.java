package com.honest.enterprise.core.exception;

import com.alibaba.fastjson.JSON;
import com.honest.enterprise.core.http.HttpResult;
import com.honest.enterprise.core.model.interfaces.BaseStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 全局异常拦截
 * @Author: liuguosheng
 * @Date: 2022-07-17 09:34:41
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * json格式
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public HttpResult errorHandler(MethodArgumentNotValidException ex) {
        BindingResult re = ex.getBindingResult();
        return getErrorResult(re);
    }

    /**
     * 表单格式
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public HttpResult errorHandler(BindException ex) {
        BindingResult result = ex.getBindingResult();
        return getErrorResult(result);
    }

    /**
     * 获取错误相应
     *
     * @param re
     * @return
     */
    private HttpResult getErrorResult(BindingResult re) {
        StringBuilder errorMsg = new StringBuilder();
        for (ObjectError error : re.getAllErrors()) {
            errorMsg.append(error.getDefaultMessage()).append(",");
        }
        errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
        HttpResult ret = HttpResult.VALIDATOR_INVALID;
        //ret.setData(errorMsg.toString());
        ret.setRetMsg(errorMsg.toString());
        return ret;
    }


    @ExceptionHandler(GeCenteringException.class)
    public HttpResult handleException(HttpServletRequest request, HttpServletResponse response,
                                      GeCenteringException exception) throws Throwable {
        recordRequestInfo(request);
        return GdExceptionHttpResult(exception);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResult handleException(HttpServletRequest request, Exception exception) throws Throwable {
        recordRequestInfo(request);
        return unknownErrorHttpResult(exception);
    }

    @ExceptionHandler(value = GoodsCenterBusinessException.class)
    public HttpResult errorHandler(HttpServletRequest request, GoodsCenterBusinessException goodsCenterBusinessException) {
        recordRequestInfo(request);
        return goodsCenterBusinessExceptionHttpResult(goodsCenterBusinessException);
    }

    private HttpResult goodsCenterBusinessExceptionHttpResult(GoodsCenterBusinessException goodsCenterBusinessException) {
        StringBuilder msg = new StringBuilder();
        msg.append(goodsCenterBusinessException.getMsg());
        log.error(msg.toString(), goodsCenterBusinessException);
        BaseStatus status = goodsCenterBusinessException.getStatus();
        if (status == null) {
            HttpResult ret = HttpResult.UNKOWN_ERROR;
            ret.setRetMsg(msg.toString());
            return ret;
        }
        HttpResult httpResult = new HttpResult(status.getCode(), format(status.getMsg(), goodsCenterBusinessException.getArgs()));
        return httpResult;
    }


    private HttpResult GdExceptionHttpResult(GeCenteringException exception) throws Throwable {
        StringBuilder msg = new StringBuilder();
        buildExceptionMessage(msg, exception);
        log.error(msg.toString(), exception);
        BaseStatus status = exception.getStatus();
        if (status == null) {
            HttpResult ret = HttpResult.UNKOWN_ERROR;
           // ret.setData(msg.toString());
            ret.setRetMsg(msg.toString());
            return ret;
        }

        HttpResult httpResult = new HttpResult(status.getCode(), format(status.getMsg(), exception.getArgs()));
        return httpResult;
    }



    // ***************************** private function  *****************************

    private HttpResult unknownErrorHttpResult(Exception exception) throws Throwable {

        StringBuilder msg = new StringBuilder();
        buildExceptionMessage(msg, exception);
        log.error(msg.toString(), exception);
        if (exception instanceof NestedServletException) {
            NestedServletException servletException = (NestedServletException) exception;
            if (Objects.nonNull(servletException.getRootCause()) && (servletException.getRootCause() instanceof OutOfMemoryError)) {
                //oom err 不拦截，让其触发Jvm HeapDumpOnOutOfMemoryError
                throw servletException.getRootCause();
            }
        }
        HttpResult ret = HttpResult.UNKOWN_ERROR;
        ret.setData(msg.toString());
        return ret;
    }

    private void recordRequestInfo(HttpServletRequest request) {
        HttpRequestInfo requestInfo = new HttpRequestInfo();
        try {
            requestInfo.setPath(request.getServletPath());
            requestInfo.setParameters(request.getParameterMap());
            log.info(String.format("RequestInfo:%s", JSON.toJSONString(requestInfo)));
        } catch (Exception e) {
            log.warn(String.format("RequestInfo:%s,%s:", JSON.toJSONString(requestInfo), e.getMessage()));
        }
    }

    private void buildExceptionMessage(StringBuilder msg, Throwable throwable) {
        if (Objects.isNull(throwable)) {
            return;
        }
        if (msg.length() > 0) {
            msg.append(System.lineSeparator());
        }
        msg.append(String.format("Cause:%s --> Msg:%s", throwable.getClass(), throwable.getMessage()));

        Throwable[] suppresseds = throwable.getSuppressed();
        if (Objects.nonNull(suppresseds) && (suppresseds.length > 0)) {
            for (Throwable suppressed : suppresseds) {
                msg.append(System.lineSeparator() + suppressed.getMessage());
            }
        }
        buildExceptionMessage(msg, throwable.getCause());
    }

    private static String format(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }

    @Data
    public static class HttpRequestInfo {
        private String path;
        private Map<String, String[]> parameters;
        private String body;
    }
}