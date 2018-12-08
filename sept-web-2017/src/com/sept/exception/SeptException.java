package com.sept.exception;

public class SeptException extends Exception {
	private final static String head = "";

	private static final long serialVersionUID = 1L;

	public SeptException(String msg) {
		super(head + "" + msg);
	}
	public SeptException(String msg, Throwable e) {
		super(head + "" + msg, e);
	}

	public SeptException(Throwable e) {
		this(e == null ? "" : e.toString(), e);
	}
}
