package com.honest.enterprise.core.exception;

import java.text.MessageFormat;

/**
 * @Description: 通用异常类
 * @Author: liuguosheng
 * @Date: 2022-07-17 09:51:06
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int code = 500;
    private String msg;
    private Object[] args;

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(Throwable source) {
        super(source);
    }

    public BaseException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BaseException(String msg, int code) {
        super(msg);
        this.setErrorCode(msg, code);
    }

    public BaseException(String msg, int code, Throwable e) {
        super(msg, e);
        this.setErrorCode(msg, code);
    }

    public BaseException( String msg, int code, Object... args) {
        super(format(msg, args));
        this.setErrorCode(msg, code, args);
    }

    public BaseException(String msg, int code, Throwable source, Object... args) {
        super(format(msg, args), source);
        this.setErrorCode(msg, code, args);
    }


    // ********************** private functions **********************

    private static String format(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }

    public String getMsg() {
        if (args == null) {
            return msg;
        }
        return format(msg, args);
    }

    public Object[] getArgs() {
        return args;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    private void setErrorCode(String msg, int code) {
        this.code = code;
        this.msg = msg;
    }

    private void setErrorCode(String msg, int code, Object... args) {
        this.code = code;
        this.msg = msg;
        this.args = args;
    }
}
