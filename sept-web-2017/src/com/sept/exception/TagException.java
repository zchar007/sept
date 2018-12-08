package com.sept.exception;

public class TagException extends SeptException {
	private final static String head = "标签出现异常!";

	private static final long serialVersionUID = 1L;

	public TagException(String msg) {
		super(head + ">>" + msg);
	}

	public TagException(String msg, Throwable e) {
		super(head + ">>" + msg, e);
	}

	public TagException(Throwable e) {
		this(e == null ? "" : e.toString(), e);
	}
}
