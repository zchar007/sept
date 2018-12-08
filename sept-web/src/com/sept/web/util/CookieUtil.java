package com.sept.web.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sept.exception.AppException;
import com.sept.project.context.GlobalContext;

public class CookieUtil {
	protected static Object cooks = new Object();

	/**
	 * 添加cookie
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-31
	 * @since V1.0
	 */
	public static void addCookie(HttpServletResponse response, String key, String value) throws AppException {
		synchronized (cooks) {
			// System.out.println("");
			try {
				key = java.net.URLEncoder.encode(key, GlobalContext.DEFAULT_CHARACTER_ENCODING);
				value = java.net.URLEncoder.encode(value, GlobalContext.DEFAULT_CHARACTER_ENCODING);
				Cookie cookie = new Cookie(key, value);
				response.addCookie(cookie);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new AppException(e);
			}

		}
	}

	/**
	 * 通过cookie名获取值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		synchronized (cooks) {
			String value = null;
			if (null == key || null == request) {
				return value;
			}
			Cookie[] cook = request.getCookies();
			if (null == cook) {
				return value;
			}
			for (int i = 0; i < cook.length; i++) {
				if (key.equalsIgnoreCase(cook[i].getName())) {
					value = cook[i].getValue();
					break;
				}
			}
			return value;
		}
	}

	/**
	 * 通过cookie名获取值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Cookie removeCookie(HttpServletResponse response, HttpServletRequest request, String key) {
		synchronized (cooks) {
			Cookie value = null;
			if (null == key || null == request) {
				return value;
			}
			Cookie[] cook = request.getCookies();
			if (null == cook) {
				return value;
			}
			for (int i = 0; i < cook.length; i++) {
				if (key.equalsIgnoreCase(cook[i].getName())) {
					value = cook[i];
					break;
				}
			}
			value.setMaxAge(0);
			response.addCookie(value);
			return value;
		}
	}
}
