package com.sept.demo.logon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.sept.framework.web.ao.BPO;
import com.sept.framework.web.servlet.def.SeptemberController;
import com.sept.framework.web.util.ActionUtil;
import com.sept.framework.web.util.CookieUtil;
import com.sept.support.interfaces.logon.LogonHandler;
import com.sept.support.model.data.DataObject;

public class PubController extends SeptemberController {
	/**
	 * 登录页面
	 * 
	 */
	public ModelAndView logon(HttpServletRequest request,
			HttpServletResponse response, DataObject para) throws Exception {
		return ActionUtil.forward("/sept/demo/logon/Logon.jsp", para);
	}
	/**
	 * 登录页面
	 * 
	 */
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response, DataObject para) throws Exception {
		LogonHandler.LogonOut(para, request, response);
		return ActionUtil.forward("/sept/demo/logon/Logon.jsp", para);
	}

	/**
	 * 登录
	 * 
	 */
	public ModelAndView checkLogin(HttpServletRequest request,
			HttpServletResponse response, DataObject para) throws Exception {
		LogonHandler.checkLogon(para, request, response);
		DataObject pdo = new DataObject();
		pdo.put("url", "pub.do?method=mainPage");
		return ActionUtil.writeDataObjectToResponse(response, pdo);
	}
	/**
	 * 登录
	 * 
	 */
	public ModelAndView mainPage(HttpServletRequest request,
			HttpServletResponse response, DataObject para) throws Exception {
		return ActionUtil.forward("/sept/demo/main/mainPage.jsp");
	}
}
