package com.sept.io.exception;

import com.sept.exception.AppException;
import com.sept.exception.ExceptionNames;
import com.sept.exception.SeptException;

public class IOException extends SeptException {

	private static final long serialVersionUID = 8605483565352355562L;

	public static final String exceptionType = "IO";
	static{
		try {
			ExceptionNames.addExceptionName(-11, "IO��ȡ�쳣");
			ExceptionNames.addExceptionName(-12, "IOд���쳣");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public IOException() {
		this(ExceptionNames.defaultCode, "�������" + exceptionType + "�쳣!");
	}

	public IOException(Throwable cause) {
		this(ExceptionNames.defaultCode, cause == null ? "�������" + exceptionType + "�쳣!" : cause.toString(), cause);
	}

	public IOException(String errtext) {
		this(ExceptionNames.defaultCode, errtext);
	}

	public IOException(String errtext, Throwable cause) {
		this(ExceptionNames.defaultCode, errtext, cause);
	}

	public IOException(int errcode, String errtext) {
		super(errcode + "_" + errtext);
	}

	public IOException(int errcode, String errtext, Throwable cause) {
		super(errcode + "_" + errtext, cause);
	}
}
