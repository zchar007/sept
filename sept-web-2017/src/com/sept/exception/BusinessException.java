package com.sept.exception;

/**
 * 这个是正常展示给用户的
 * @author zchar
 *
 */
public class BusinessException extends SeptException {
	private static final long serialVersionUID = 1L;

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(String msg, Throwable e) {
		super(msg, e);
	}

	public BusinessException(Throwable e) {
		this(e == null ? "程序出现异常!" : e.toString(), e);
	}
}
