package com.sept.exception;

public class BizException extends SeptException {
	private static final long serialVersionUID = 1L;
	public static final String exceptionType = "业务";

	public BizException() {
		this(ExceptionNames.defaultCode, "程序出现业务异常!");
	}

	public BizException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "程序出现业务异常!" : cause.toString(), cause);
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