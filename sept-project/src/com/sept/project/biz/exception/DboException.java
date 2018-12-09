package com.sept.project.biz.exception;

import com.sept.exception.ExceptionNames;
import com.sept.exception.SeptException;

public class DboException extends SeptException {
	private static final long serialVersionUID = 8605483565352355562L;
	public static final String exceptionType = "DBO";

	public DboException() {
		this(ExceptionNames.defaultCode, "DBO异常!");
	}

	public DboException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "DBO异常!" : cause.toString(), cause);
	}

	public DboException(String errtext) {
		this(ExceptionNames.defaultCode, errtext);
	}

	public DboException(String errtext, Throwable cause) {
		this(ExceptionNames.defaultCode, errtext, cause);
	}

	public DboException(int errcode, String errtext) {
		super(errcode + "_" + errtext);
	}

	public DboException(int errcode, String errtext, Throwable cause) {
		super(errcode + "_" + errtext, cause);
	}
}