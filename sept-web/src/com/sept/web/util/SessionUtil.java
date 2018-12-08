package com.sept.web.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	private final static HashMap<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();

	/**
	 * 直接插入session
	 * 
	 * @param sessionname
	 * @param session
	 */
	public static void putSession(String sessionname, HttpSession session) {
		synchronized (sessionMap) {
			if (!sessionMap.containsKey(sessionname)) {
				sessionMap.put(sessionname, session);
			}
		}
	}

	/**
	 * 添加session到Util中
	 * 
	 * @param sessionname
	 * @param request
	 */
	public static void putSession(String sessionname, HttpServletRequest request) {
		synchronized (sessionMap) {
			if (!sessionMap.containsKey(sessionname)) {
				sessionMap.put(sessionname, request.getSession());
			}
		}
	}

	/**
	 * 根据sessionname，如果已存入，则不再存入，反之则存入
	 * 
	 * @param sessionname
	 * @param request
	 * @return
	 */
	public static HttpSession getSession(String sessionname, HttpServletRequest request) {
		synchronized (sessionMap) {
			if (!sessionMap.containsKey(sessionname)) {
				sessionMap.put(sessionname, request.getSession());
			}
			return sessionMap.get(sessionname);
		}

	}

	/**
	 * 获取session
	 * 
	 * @param sessionname
	 * @return
	 */
	public static HttpSession getSession(String sessionname) {
		synchronized (sessionMap) {
			if (!sessionMap.containsKey(sessionname)) {
				System.err.println("没有此session");
				return null;
			}
			return sessionMap.get(sessionname);
		}

	}

	/**
	 * 删除session
	 * 
	 * @param sessionname
	 * @return
	 */
	public static HttpSession removeSession(String sessionname) {
		synchronized (sessionMap) {
			if (!sessionMap.containsKey(sessionname)) {
				System.err.println("没有此session");
				return null;
			}
			return sessionMap.remove(sessionname);
		}

	}

	/**
	 * 获取session数据
	 * 
	 * @param sessionname
	 * @return
	 */
	public static Object getVarToSession(String sessionname, String key) {
		synchronized (sessionMap) {
			if (!sessionMap.containsKey(sessionname)) {
				System.err.println("没有此session");
				return null;
			}
			HttpSession session = sessionMap.get(sessionname);
			return session.getAttribute(key);
		}
	}

	/**
	 * 添加数据到指定session
	 * 
	 * @param sessionname
	 * @return
	 */
	public static Object putVarToSession(String sessionname, String key, Object value) {
		synchronized (sessionMap) {
			if (!sessionMap.containsKey(sessionname)) {
				System.err.println("没有此session");
				return null;
			}
			sessionMap.get(sessionname).setAttribute(key, value);
			return value;
		}
	}

	/**
	 * 删除数据到指定session
	 * 
	 * @param sessionname
	 * @return
	 */
	public static Object removeVarToSession(String sessionname, String key) {
		synchronized (sessionMap) {
			if (!sessionMap.containsKey(sessionname)) {
				System.err.println("没有此session");
				return null;
			}
			Object obj = sessionMap.get(sessionname).getAttribute(key);
			sessionMap.get(sessionname).removeAttribute(key);
			return obj;
		}
	}

}
