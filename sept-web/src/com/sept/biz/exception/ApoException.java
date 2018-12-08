package com.sept.biz.exception;

import com.sept.exception.ExceptionNames;
import com.sept.exception.SeptException;

public class ApoException extends SeptException {
	private static final long serialVersionUID = 8605483565352355562L;
	public static final String exceptionType = "APO";

	public ApoException() {
		this(ExceptionNames.defaultCode, "APO异常!");
	}

	public ApoException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "APO异常!" : cause.toString(), cause);
	}

	public ApoException(String errtext) {
		this(ExceptionNames.defaultCode, errtext);
	}

	public ApoException(String errtext, Throwable cause) {
		this(ExceptionNames.defaultCode, errtext, cause);
	}

	public ApoException(int errcode, String errtext) {
		super(errcode + "_" + errtext);
	}

	public ApoException(int errcode, String errtext, Throwable cause) {
		super(errcode + "_" + errtext, cause);
	}
}