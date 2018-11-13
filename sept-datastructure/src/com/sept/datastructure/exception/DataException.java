package com.sept.datastructure.exception;

import com.sept.exception.ExceptionNames;
import com.sept.exception.SeptException;

public class DataException extends SeptException {
	private static final long serialVersionUID = 8605483565352355562L;
	public static final String exceptionType = "数据";

	public DataException() {
		this(ExceptionNames.defaultCode, "程序出现["+exceptionType+"]异常!");
	}

	public DataException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "程序出现["+exceptionType+"]异常!" : cause.toString(), cause);
	}

	public DataException(String errtext) {
		this(ExceptionNames.defaultCode, errtext);
	}

	public DataException(String errtext, Throwable cause) {
		this(ExceptionNames.defaultCode, errtext, cause);
	}

	public DataException(int errcode, String errtext) {
		super(errcode + "_" + errtext);
	}

	public DataException(int errcode, String errtext, Throwable cause) {
		super(errcode + "_" + errtext, cause);
	}
}