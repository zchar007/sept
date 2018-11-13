package com.sept.jui.exception;

import com.sept.exception.ExceptionNames;
import com.sept.exception.SeptException;

public class JUIException extends SeptException {
	private static final long serialVersionUID = 8605483565352355562L;
	public static final String exceptionType = "JUI";

	public JUIException() {
		this(ExceptionNames.defaultCode, "�������JUI�쳣!");
	}

	public JUIException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "�������JUI�쳣!" : cause.toString(), cause);
	}

	public JUIException(String errtext) {
		this(ExceptionNames.defaultCode, errtext);
	}

	public JUIException(String errtext, Throwable cause) {
		this(ExceptionNames.defaultCode, errtext, cause);
	}

	public JUIException(int errcode, String errtext) {
		super(errcode + "_" + errtext);
	}

	public JUIException(int errcode, String errtext, Throwable cause) {
		super(errcode + "_" + errtext, cause);
	}
}
