package com.honest.enterprise.websocket.dto;

import lombok.Data;

/**
 * 通知DTO
 * @author fanjie
 * @date 2022-05-27
 */
@Data
public class WsNoticeDTO<T>  {
    /**
     * 错误码
     */
    private Integer retCode;
    /**
     * 错误信息，一般为前端提示信息
     */
    private String retMsg;
    /**
     * 返回值，一般为成功后返回的数据
     */
    private T data;

    public WsNoticeDTO(){
    }

    public WsNoticeDTO(Integer retCode, String retMsg){
        this.retCode=retCode;
        this.retMsg=retMsg;
    }
    public WsNoticeDTO setNewData(T data) {
        WsNoticeDTO error = new WsNoticeDTO();
        error.setRetCode(this.retCode);
        error.setRetMsg(this.retMsg);
        error.setData(data);
        return error;
    }

    public static final WsNoticeDTO SUCCESS = new WsNoticeDTO(200, "处理成功");

    public static final WsNoticeDTO FAIL = new WsNoticeDTO(201, "处理失败");

}
