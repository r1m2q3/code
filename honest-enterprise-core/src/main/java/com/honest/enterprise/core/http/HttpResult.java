package com.honest.enterprise.core.http;

import lombok.Data;

/**
 * 统一返回结果集实体类
 * @param <T> 返回数据对象t
 */
@Data
public class HttpResult<T> {
    //错误码
    private Integer retCode;

    //错误信息，一般为前端提示信息
    private String retMsg;

    //返回值，一般为成功后返回的数据
    private T data;

    //错误详情，一般为失败后的详细原因，如空指针之类的
    private String errorDetail;

    public HttpResult() {}

    public HttpResult(Integer retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public HttpResult(Integer retCode, String retMsg, T data) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.data = data;
    }

    /**
     * 配合静态对象直接设置 data 参数
     * @param data
     * @return
     */
    public HttpResult setNewData(T data) {
        HttpResult error = new HttpResult();
        error.setRetCode(this.retCode);
        error.setRetMsg(this.retMsg);
        error.setErrorDetail(this.errorDetail);
        error.setData(data);
        return error;
    }

    /**
     * 配合静态对象直接设置 errorMsg 参数
     * @param errorMsg
     * @return
     */
    public HttpResult setNewErrorMsg(String errorMsg) {
        HttpResult error = new HttpResult();
        error.setRetCode(this.retCode);
        error.setRetMsg(errorMsg);
        error.setErrorDetail(this.errorDetail);
        error.setData(this.data);
        return error;
    }

    public static final HttpResult SUCCESS = new HttpResult(200, "成功");

    public static final HttpResult INSERT_SUCCESS = new HttpResult(200, "新增成功");

    public static final HttpResult UPDATE_SUCCESS = new HttpResult(200, "更新成功");

    public static final HttpResult DELETE_SUCCESS = new HttpResult(200, "删除成功");

    public static final HttpResult UPLOAD_SUCCESS = new HttpResult(200, "上传成功");

    public static final HttpResult DOWNLOAD_SUCCESS = new HttpResult(200, "下载成功");

    public static final HttpResult LOGIN_SUCCESS = new HttpResult(200, "登陆成功");

    public static final HttpResult LOGOUT_SUCCESS = new HttpResult(200, "登出成功");

    public static final HttpResult LOGIN_ERROR = new HttpResult(201, "登陆错误");

    public static final HttpResult LOGIN_EXPIRE = new HttpResult(202, "登陆过期");

    public static final HttpResult ACCESS_LIMITED = new HttpResult(301, "访问受限");

    public static final HttpResult VALIDATOR_INVALID = new HttpResult(400, "表单验证无效");

    public static final HttpResult UNKOWN_ERROR = new HttpResult(500, "内部服务错误");

    public static final HttpResult ARGS_ERROR = new HttpResult(501, "参数错误");

    public static final HttpResult BAD_GATEWAY = new HttpResult(502, "路由错误");

    public static final HttpResult INSERT_ERROR = new HttpResult(503, "新增错误");

    public static final HttpResult UPDATE_ERROR = new HttpResult(504, "更新错误");

    public static final HttpResult DELETE_ERROR = new HttpResult(506, "删除错误");

    public static final HttpResult UPLOAD_ERROR = new HttpResult(507, "上传错误");

    public static final HttpResult DOWNLOAD_ERROR = new HttpResult(508, "下载错误");

    public static final HttpResult OTHER_SYSTEM_ERROR = new HttpResult(509, "调用系统异常");

    /**
     * @Description: 幂等性接口重复操作
     * @params:
     * @Author: yuanjunshuai
     * @Date: 2021/4/16 9:18
    */
    public static final HttpResult IDEMPOTENT_ERROR = new HttpResult(510, "重复操作");

    public static final HttpResult CODE_REPEAT = new HttpResult(512, "编号重复");
    public static final HttpResult NAME_REPEAT = new HttpResult(513, "名称重复");

    public static final HttpResult INVALID_TOKEN = new HttpResult(601, "无效TOKEN");
    public static final HttpResult INVALID_AUTH_CENTER = new HttpResult(602, "无效认证中心");
    public static final HttpResult SERVICE_FUSING = new HttpResult(603, "服务熔断");
    public static final HttpResult NEED_RETRY = new HttpResult(604, "需要重试");
}
