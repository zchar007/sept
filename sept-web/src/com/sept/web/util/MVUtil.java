package com.sept.web.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.exception.BizException;
import com.sept.project.context.GlobalContext;

public class MVUtil {
	/**
	 * @description 以默认字符集往前台写消息，仅仅是字符串的消息，接受端可直接使用
	 * @param response
	 * @param message
	 * @return
	 * @throws AppException
	 * @author zchar.2018年11月19日下午2:45:42
	 */
	public static final ModelAndView writeMessage(HttpServletResponse response, String message) throws AppException {
		return MVUtil.writeMessage(response, message, GlobalContext.DEFAULT_CHARACTER_ENCODING);
	}

	/**
	 * @description 以指定字符集往前台写消息，仅仅是字符串的消息，接受端可直接使用
	 * @param response
	 * @param message
	 * @param charset
	 * @return
	 * @throws AppException
	 * @author zchar.2018年11月19日下午2:46:04
	 */
	public static final ModelAndView writeMessage(HttpServletResponse response, String message, String charset)
			throws AppException {
		response.setContentType("text/html;charset=" + charset);
		message = null == message ? "" : message;
		PrintWriter out = null;
		try {
			response.setContentLength(message.getBytes(charset).length);
			out = response.getWriter();
			out.print(message);
			out.flush();
		} catch (Exception ex) {
			// 日志记录
			LogHandler.fatal("MV输出异常", ex);
			throw new AppException(ex);
		} finally {
			if (null != out) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * @description 以默认格式化，默认字符集向前台发送一个DataObject的数据
	 * @param response
	 * @param vdo
	 * @return
	 * @throws AppException
	 * @author zchar.2018年11月19日下午2:46:25
	 */
	public static final ModelAndView writeDataObject(HttpServletResponse response, DataObject vdo) throws AppException {
		return MVUtil.writeDataObject(response, vdo, GlobalContext.DEFAULT_PARAM_TYPE_JSON,
				GlobalContext.DEFAULT_CHARACTER_ENCODING);
	}

	/**
	 * @description 以指定格式化，指定字符集向前台发送一个DataObject的数据
	 * @param response
	 * @param vdo
	 * @param datatype
	 * @param charset
	 * @return
	 * @throws AppException
	 * @author zchar.2018年11月19日下午2:45:09
	 */
	public static final ModelAndView writeDataObject(HttpServletResponse response, DataObject vdo, String datatype,
			String charset) throws AppException {
		response.setContentType("text/html;charset=" + charset);

		String message = GlobalContext.DEFAULT_PARAM_TYPE_XML.equals(datatype) ? vdo.toXML() : vdo.toJSON();
		message = null == message ? "" : message;

		PrintWriter out = null;
		try {
			response.setContentLength(message.getBytes(charset).length);
			out = response.getWriter();
			out.print(message);
			out.flush();
		} catch (IOException ex) {
			// 日志记录
			LogHandler.fatal("MV输出异常", ex);
			throw new AppException(ex);
		} finally {
			if (null != out) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * @description 以默认格式化，默认字符集向前台发送一个DataStore的数据
	 * @param response
	 * @param vds
	 * @return
	 * @throws AppException
	 * @author zchar.2018年11月19日下午2:46:25
	 */
	public static final ModelAndView writeDataStore(HttpServletResponse response, DataStore vds) throws AppException {
		return MVUtil.writeDataStore(response, vds, GlobalContext.DEFAULT_PARAM_TYPE_JSON,
				GlobalContext.DEFAULT_CHARACTER_ENCODING);
	}

	/**
	 * @description 以指定格式化，指定字符集向前台发送一个DataStore的数据
	 * @param response
	 * @param vds
	 * @param datatype
	 * @param charset
	 * @return
	 * @throws AppException
	 * @author zchar.2018年11月19日下午2:45:09
	 */
	public static final ModelAndView writeDataStore(HttpServletResponse response, DataStore vds, String datatype,
			String charset) throws AppException {
		response.setContentType("text/html;charset=" + charset);

		String message = GlobalContext.DEFAULT_PARAM_TYPE_XML.equals(datatype) ? vds.toXML() : vds.toJSON();
		message = null == message ? "" : message;

		PrintWriter out = null;
		try {
			response.setContentLength(message.getBytes(charset).length);
			out = response.getWriter();
			out.print(message);
			out.flush();
		} catch (IOException ex) {
			// 日志记录
			LogHandler.fatal("MV输出异常", ex);
			throw new AppException(ex);
		} finally {
			if (null != out) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * @description 业务或内部出现错误往前台写错误
	 * @param response
	 * @param ex
	 * @return
	 * @author zchar.2018年11月19日下午2:58:26
	 */
	public static ModelAndView writeException(HttpServletResponse response, Throwable ex) {
		// e.printStackTrace();
		DataObject pdo = new DataObject();
		// PrintWriter out = response.getWriter();
		// 异常已在catch中转换成了两种--biz或app
		Throwable throwable = ex;
		// 抛出提示性消息
		if (throwable instanceof BizException) {

		} else {// 打开错误窗口res，显示错误信息
			ex.printStackTrace();
			StackTraceElement[] st = throwable.getStackTrace();
			String error = "<br/>" + throwable.toString();
			String errortext = "";
			for (int i = 0; i < st.length; i++) {
				errortext += "<br/>" + st[i].toString();
			}
			error += errortext;
			pdo.clear();

		}
		try {
			writeMessage(response, pdo.toJSON());
		} catch (AppException e1) {
			e1.printStackTrace();
		}
		return new ModelAndView();
	}

	/**
	 * @description :往前台写Cookies
	 * @param response
	 * @param pdo
	 * @return
	 * @throws AppException
	 * @author zchar.2018年11月19日下午2:58:56
	 */
	public static ModelAndView writeCookies(HttpServletResponse response, DataObject pdo) throws AppException {
		for (String key : pdo.keySet()) {
			CookieUtil.addCookie(response, key, pdo.getString(key));
		}
		return null;
	}

	/**
	 * @description :往前台写Cookies
	 * @param response
	 * @param pdo
	 * @return
	 * @throws AppException
	 * @author zchar.2018年11月19日下午2:58:56
	 */
	public static ModelAndView writeCookies(HttpServletResponse response, String key, String value)
			throws AppException {
		CookieUtil.addCookie(response, key, value);
		return null;
	}

	/**
	 * 
	 * @description 转发
	 * @param forwardUrl：转发的地址
	 * @return
	 * @author zchar.2018年11月19日下午2:55:10
	 */
	public static ModelAndView forward(String forwardUrl) {
		forwardUrl = checkForwardUrl(forwardUrl);
		return new ModelAndView(forwardUrl);
	}

	/**
	 * 
	 * @description 转发
	 * @param forwardUrl：转发的地址
	 * @param messageName
	 * @param message
	 * @return
	 * @author zchar.2018年11月19日下午2:54:57
	 */
	public static ModelAndView forward(String forwardUrl, String messageName, String message) {
		forwardUrl = checkForwardUrl(forwardUrl);
		return new ModelAndView(forwardUrl, messageName, message);
	}

	/**
	 * 
	 * @description 转发
	 * @param forwardUrl：转发的地址
	 * @param vdsName
	 * @param vds
	 * @return
	 * @author zchar.2018年11月19日下午2:54:46
	 */
	public static ModelAndView forward(String forwardUrl, String vdsName, DataStore vds) {
		forwardUrl = checkForwardUrl(forwardUrl);
		return new ModelAndView(forwardUrl, vdsName, vds);
	}

	/**
	 * 
	 * @description 转发
	 * @param forwardUrl：转发的地址
	 * @param vdo
	 * @return
	 * @author zchar.2018年11月19日下午2:54:16
	 */
	public static ModelAndView forward(String forwardUrl, DataObject vdo) {
		forwardUrl = checkForwardUrl(forwardUrl);
		return new ModelAndView(forwardUrl, vdo);
	}

	/**
	 * 
	 * @description 重定向
	 * @param redirectUrl：重定向的地址
	 * @return
	 * @author zchar.2018年11月19日下午2:54:02
	 */
	public static ModelAndView redirect(String redirectUrl) {
		redirectUrl = checkRedirectUrl(redirectUrl);
		return new ModelAndView(redirectUrl);
	}

	/**
	 * 
	 * @description 重定向
	 * @param redirectUrl：重定向的地址
	 * @param messageName
	 * @param message
	 * @return
	 * @author zchar.2018年11月19日下午2:53:50
	 */
	public static ModelAndView redirect(String redirectUrl, String messageName, String message) {
		redirectUrl = checkRedirectUrl(redirectUrl);
		return new ModelAndView(redirectUrl, messageName, message);
	}

	/**
	 * 
	 * @description 重定向
	 * @param redirectUrl：重定向的地址
	 * @param vdsName
	 * @param vds
	 * @return
	 * @author zchar.2018年11月19日下午2:53:26
	 */
	public static ModelAndView redirect(String redirectUrl, String vdsName, DataStore vds) {
		redirectUrl = checkRedirectUrl(redirectUrl);
		return new ModelAndView(redirectUrl, vdsName, vds);
	}

	/**
	 * 
	 * @description 重定向
	 * @param redirectUrl：重定向的地址
	 * @param vdo
	 * @return
	 * @author zchar.2018年11月19日下午2:53:07
	 */
	public static ModelAndView redirect(String redirectUrl, DataObject vdo) {
		redirectUrl = checkRedirectUrl(redirectUrl);
		return new ModelAndView(redirectUrl, vdo);
	}

	/**
	 * @description 把URL替换为转发
	 * @param forwardUrl：转发的地址
	 * @return
	 * @author zchar.2018年11月19日下午2:52:19
	 */
	private static String checkForwardUrl(String forwardUrl) {
		if ("redirect".startsWith(forwardUrl)) {
			forwardUrl.replaceAll("redirect:", "");
		}
		if (!"forward:".startsWith(forwardUrl)) {
			forwardUrl = "forward:" + forwardUrl;
		}
		return forwardUrl;
	}

	/**
	 * 
	 * @description 把url改为重定向
	 * @param redirectUrl：重定向的地址
	 * @return
	 * @author zchar.2018年11月19日下午2:51:52
	 */
	private static String checkRedirectUrl(String redirectUrl) {
		if ("forward".startsWith(redirectUrl)) {
			redirectUrl.replaceAll("forward:", "");
		}
		if (!"redirect:".startsWith(redirectUrl)) {
			redirectUrl = "redirect:" + redirectUrl;
		}
		return redirectUrl;
	}
}
