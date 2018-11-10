package com.sept.exception;

public class ApplicationException extends SeptException {
	private static final long serialVersionUID = 8605483565352355562L;
	public static final String exceptionType = "ϵͳ";

	public ApplicationException() {
		this(ExceptionNames.defaultCode, "�������ϵͳ�쳣!");
	}

	public ApplicationException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "�������ϵͳ�쳣!" : cause.toString(), cause);
	}

	public ApplicationException(String errtext) {
		this(ExceptionNames.defaultCode, errtext);
	}

	public ApplicationException(String errtext, Throwable cause) {
		this(ExceptionNames.defaultCode, errtext, cause);
	}

	public ApplicationException(int errcode, String errtext) {
		super(errcode + "_" + errtext);
	}

	public ApplicationException(int errcode, String errtext, Throwable cause) {
		super(errcode + "_" + errtext, cause);
	}
}