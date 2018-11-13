package com.sept.datastructure.exception;

import com.sept.exception.ExceptionNames;
import com.sept.exception.SeptException;

public class DataException extends SeptException {
	private static final long serialVersionUID = 8605483565352355562L;
	public static final String exceptionType = "����";

	public DataException() {
		this(ExceptionNames.defaultCode, "�������["+exceptionType+"]�쳣!");
	}

	public DataException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "�������["+exceptionType+"]�쳣!" : cause.toString(), cause);
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