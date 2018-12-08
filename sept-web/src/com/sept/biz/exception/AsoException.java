package com.sept.biz.exception;

import com.sept.exception.ExceptionNames;
import com.sept.exception.SeptException;

public class AsoException extends SeptException {
	private static final long serialVersionUID = 8605483565352355562L;
	public static final String exceptionType = "ASO";

	public AsoException() {
		this(ExceptionNames.defaultCode, "ASO异常!");
	}

	public AsoException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "ASO异常!" : cause.toString(), cause);
	}

	public AsoException(String errtext) {
		this(ExceptionNames.defaultCode, errtext);
	}

	public AsoException(String errtext, Throwable cause) {
		this(ExceptionNames.defaultCode, errtext, cause);
	}

	public AsoException(int errcode, String errtext) {
		super(errcode + "_" + errtext);
	}

	public AsoException(int errcode, String errtext, Throwable cause) {
		super(errcode + "_" + errtext, cause);
	}
}