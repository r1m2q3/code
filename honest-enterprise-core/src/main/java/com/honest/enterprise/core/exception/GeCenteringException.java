package com.honest.enterprise.core.exception;


import com.honest.enterprise.core.model.interfaces.BaseStatus;

/**
 * 自定义异常
 * @author fanjie
 * @date 2022-07-17 14:16
 */
public class GeCenteringException extends BaseException {
	private static final long serialVersionUID = 1L;
	private BaseStatus status;

	public GeCenteringException(String msg) {
		super(msg);
	}

	public GeCenteringException(String msg, int code) {
		super(msg, code);
	}

	public GeCenteringException(String msg, int code, Throwable cause) {
		super(msg, code, cause);
	}

	public GeCenteringException(String msg, Throwable e) {
		super(msg, e);
	}

	public GeCenteringException(Throwable source) {
		super(source);
	}

	public GeCenteringException(BaseStatus status) {
		this(status, new Object[]{});
	}

	public GeCenteringException(BaseStatus status, Throwable cause) {
		super(status.getMsg(), status.getCode(), cause);
		this.status = status;
	}

	public GeCenteringException(String msg, int code, Object... args) {
		super(msg, code, args);
	}

	public GeCenteringException(BaseStatus status, Object... args) {
		this(status, null, args);
	}


	public GeCenteringException(BaseStatus status, Throwable cause, Object... args) {
		super(status.getMsg(), status.getCode(), cause, args);
		this.status = status;
	}

	public BaseStatus getStatus() {
		return this.status;
	}

	public GeCenteringException(String msg, int code, Throwable source, Object... args) {
		super(msg, code, source, args);
	}

}
