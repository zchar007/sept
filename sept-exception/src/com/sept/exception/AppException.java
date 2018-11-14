package com.sept.exception;

public class AppException extends SeptException {
	private static final long serialVersionUID = 8605483565352355562L;
	public static final String exceptionType = "ϵͳ";

	public AppException() {
		this(ExceptionNames.defaultCode, "�������ϵͳ�쳣!");
	}

	public AppException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "�������ϵͳ�쳣!" : cause.toString(), cause);
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