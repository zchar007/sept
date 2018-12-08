package com.sept.exception;

public class SqlException extends SeptException {
	private final static String head = "Sql语句出现异常!";

	private static final long serialVersionUID = 1L;

	public SqlException(String msg) {
		super(head + ">>" + msg);
	}
	public SqlException(String msg, Throwable e) {
		super(head + ">>" + msg, e);
	}

	public SqlException(Throwable e) {
		this(e == null ? "" : e.toString(), e);
	}
}
