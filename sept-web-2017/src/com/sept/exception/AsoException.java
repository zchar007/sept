package com.sept.exception;

public class AsoException extends SeptException {
	private final static String head = "出现异常!";

	private static final long serialVersionUID = 1L;

	public AsoException(String msg) {
		super(head + ">>" + msg);
	}
	public AsoException(String msg, Throwable e) {
		super(head + ">>" + msg, e);
	}

	public AsoException(Throwable e) {
		this(e == null ? "" : e.toString(), e);
	}
}
