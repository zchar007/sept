package com.sept.exception;

public class AppException extends SeptException {
	private static final long serialVersionUID = 8605483565352355562L;
	public static final String exceptionType = "系统";

	public AppException() {
		this(ExceptionNames.defaultCode, "程序出现系统异常!");
	}

	public AppException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "程序出现系统异常!" : cause.toString(), cause);
	}

	public AppException(String errtext) {
		this(ExceptionNames.defaultCode, errtext);
	}

	public AppException(String errtext, Throwable cause) {
		this(ExceptionNames.defaultCode, errtext, cause);
	}

	public AppException(int errcode, String errtext) {
		super(errcode + "_" + errtext);
	}

	public AppException(int errcode, String errtext, Throwable cause) {
		super(errcode + "_" + errtext, cause);
	}
}