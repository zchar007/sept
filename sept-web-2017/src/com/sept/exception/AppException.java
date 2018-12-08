package com.sept.exception;

public class AppException extends SeptException {
	private final static String head = "程序出现异常!";

	private static final long serialVersionUID = 1L;

	public AppException(String msg) {
		super(head + ">>" + msg);
	}

	public AppException(String msg, Throwable e) {
		super(head + ">>" + msg, e);
	}

	public AppException(Throwable e) {
		this(e == null ? "" : e.toString(), e);
	}

}
