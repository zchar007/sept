package com.sept.exception;

public class BizException extends SeptException {
	private static final long serialVersionUID = 1L;
	public static final String exceptionType = "ҵ��";

	public BizException() {
		this(ExceptionNames.defaultCode, "�������ҵ���쳣!");
	}

	public BizException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "�������ҵ���쳣!" : cause.toString(), cause);
	}

	public BizException(String errtext) {
		this(ExceptionNames.defaultCode, errtext);
	}

	public BizException(String errtext, Throwable cause) {
		this(ExceptionNames.defaultCode, errtext, cause);
	}

	public BizException(int errcode, String errtext) {
		super(errcode + "_" + errtext);
	}

	public BizException(int errcode, String errtext, Throwable cause) {
		super(errcode + "_" + errtext, cause);
	}
}