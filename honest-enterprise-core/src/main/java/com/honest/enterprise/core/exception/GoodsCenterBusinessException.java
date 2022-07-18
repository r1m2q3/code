package com.honest.enterprise.core.exception;


import com.honest.enterprise.core.model.interfaces.BaseStatus;

/**
 * @author wangyanan
 * @desc 商品中心业务异常
 * @date 2022-07-17 14:03
 */
public class GoodsCenterBusinessException extends BaseException{

    private static final long serialVersionUID = 6824951060625486532L;

    private BaseStatus status;

    public GoodsCenterBusinessException(String msg) {
        super(msg);
    }


    public BaseStatus getStatus() {
        return status;
    }
}
