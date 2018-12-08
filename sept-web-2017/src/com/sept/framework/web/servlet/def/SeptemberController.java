package com.sept.framework.web.servlet.def;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.sept.exception.AppException;
import com.sept.exception.BusinessException;
import com.sept.framework.web.ao.AbstractSupport;
import com.sept.framework.web.ao.BPO;
import com.sept.framework.web.servlet.MultiActionController;
import com.sept.framework.web.util.ActionUtil;
import com.sept.support.interfaces.def.CurrentUser;
import com.sept.support.interfaces.logon.LogonHandler;
import com.sept.support.interfaces.thread.ThreadInterface;
import com.sept.support.interfaces.user.UserInterface;
import com.sept.support.model.data.DataObject;
import com.sept.support.thread.GlobalToolkit;
import com.sept.support.util.DateUtil;

public class SeptemberController extends MultiActionController {

	@Override
	protected boolean doBeforResponse(HttpServletRequest request,
			HttpServletResponse response, DataObject para, Method method)
			throws Exception {

		// Enumeration<String> headersEnumeration = request.getHeaderNames();
		// while (headersEnumeration.hasMoreElements()) {
		// String hearderName = headersEnumeration.nextElement();
		// String headerValue = request.getHeader(hearderName);
		// System.out.println(hearderName + ":" + headerValue + "\n");
		// }
		// 先检查用户信息,每次请求都检查
		// if (!LogonHandler.checkLogonWithLogon(para, request, response)) {
		// return false;
		// }

		ThreadInterface ct = GlobalToolkit.getCurrentThread();
		ct.setStartTime(DateUtil.getCurrentDate().getTime());
		String requestUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort()// 端口号
				+ request.getRequestURI() + "?" + request.getQueryString();
		ct.setRequestUrl(requestUrl);
		ct.setEntrance(this.getClass().getName() + "." + method.getName());
		if (para.containsKey("_user_name_") && para.containsKey("_pass_word_")) {
			CurrentUser user = new CurrentUser();
			DataObject pdo = new DataObject();
			pdo.put("username", para.getString("_user_name_"));
			user.initUser(pdo);
			ct.setUser(user);
		}

		return true;
	}

	@Override
	protected void doAfterResponse(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		try {
			ThreadInterface ct = GlobalToolkit.getCurrentThread();
			ct.setEndTime(DateUtil.getCurrentDate().getTime());
			// 记录本次的日志
			GlobalToolkit.doLog();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected ModelAndView handleException(HttpServletRequest request,
			HttpServletResponse response, Throwable ex) throws Exception {
		// 对异常进行处理
		return ActionUtil.writeExceptionToResponse(response, ex);
	}

	/**
	 * 创建BPO
	 * 
	 * @param classz
	 * @return
	 * @throws Exception
	 */
	protected BPO newBPO(Class<? extends BPO> classz) throws Exception {
		AbstractSupport obj = null;
		// DiyTransaction dt = JDBCManager.getTransaction();
		try {
			obj = classz.newInstance();
		} catch (Exception e) {
			if (e.getCause() instanceof BusinessException) {
				throw (BusinessException) (e.getCause());
			}
			if (e.getCause() instanceof AppException) {
				throw (AppException) (e.getCause());
			}
			throw new AppException(e.getCause());
		}
		return (BPO) obj;
	}

}
