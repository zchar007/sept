package com.sept.web.exception;

import com.sept.exception.SeptException;

public class NoSuchHanderMethodException extends SeptException {
	private static final long serialVersionUID = 8605483565352355562L;
	public static final String exceptionType = "系统";

	public NoSuchHanderMethodException(String url, String method, Class<?> classz) {
		super(classz.getName() + "." + method + "[没有此方法][" + url + "]");
	}
}
