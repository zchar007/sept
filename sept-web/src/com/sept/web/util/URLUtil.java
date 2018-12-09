package com.sept.web.util;

import javax.servlet.http.HttpServletRequest;

public class URLUtil {
	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static final String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.indexOf("0:") != -1) {
			ip = "localhost";
		}
		return ip;
	}

	public static final String getRequestUrl(HttpServletRequest request) {
		String url = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort()// 端口号
				+ request.getRequestURI();
		return url;
	}

	public static final String getRequestData(HttpServletRequest request) {
		return request.getQueryString();
	}
}
