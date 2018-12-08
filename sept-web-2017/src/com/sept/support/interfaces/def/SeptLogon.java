package com.sept.support.interfaces.def;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sept.exception.AppException;
import com.sept.exception.BusinessException;
import com.sept.exception.SqlException;
import com.sept.framework.web.util.CookieUtil;
import com.sept.framework.web.util.SessionUtil;
import com.sept.support.interfaces.logon.LogonInterface;
import com.sept.support.interfaces.user.UserInterface;
import com.sept.support.model.data.DataObject;
import com.sept.support.thread.GlobalToolkit;
import com.sept.support.util.DateUtil;
import com.sept.support.util.Md5Util;

public class SeptLogon implements LogonInterface {

	@Override
	public boolean checkLogon(DataObject para, HttpServletRequest request,
			HttpServletResponse response) throws AppException,
			BusinessException, SqlException {
		System.out.println("开测：" + "checkLogon(DataObject para)" + para);
		String username = para.getString("username");
		String password = para.getString("password");

		if (!"admin".equals(username)) {
			throw new BusinessException("无法找到此用户！");
		}
		if (!"sa".equals(password)) {
			throw new BusinessException("密码错误！");
		}
//		if (null != SessionUtil.getSession(username)
//				&& !"".equals(SessionUtil.getSession(username))) {
//			throw new BusinessException("当前用户已在线！");
//		}
		HttpSession session = request.getSession();

		session.setAttribute("username", username);
		session.setAttribute("password", Md5Util.hex_md5(password));
		session.setAttribute("signtime", DateUtil.getDBTime());
		SessionUtil.putSession(username, session);

		// 这个不是记住密码，而是为前台获取用户名密码来后台做业务验证的
		CookieUtil.addCookie(response, "_user_name_", username);
		CookieUtil.addCookie(response, "_pass_word_",  Md5Util.hex_md5(password));

		return true;
	}

	@Override
	public boolean checkLogonWithLogon(DataObject para,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(!para.containsKey("_user_name_") || !para.containsKey("_pass_word_")){
			return false;
		}
		String username = para.getString("_user_name_");
		String password = para.getString("_pass_word_");

		// 用户不是在线状态，重新登录
		if (null == SessionUtil.getSession(username)
				|| "".equals(SessionUtil.getSession(username))) {
			return false;
		}
		String usernameS = (String) SessionUtil.getSession(username)
				.getAttribute("username");
		String passwordS = (String) SessionUtil.getSession(username)
				.getAttribute("password");

		if (!usernameS.equals(username)) {
			return false;
		}
		if (!passwordS.equals(password)) {
			return false;
		}

		UserInterface userInterface;
		userInterface = GlobalToolkit.getCurrentUser();
		DataObject pdo = new DataObject();
		pdo.put("username", username);
		userInterface.initUser(pdo);
		// 这个不是记住密码，而是为前台获取用户名密码来后台做业务验证的
		CookieUtil.addCookie(response, "_user_name_", username);
		CookieUtil.addCookie(response, "_pass_word_", password);

		return true;
	}

	@Override
	public boolean LogonOut(DataObject para, HttpServletRequest request,
			HttpServletResponse response) {
		CookieUtil.removeCookie(response, request, "_user_name_");
		CookieUtil.removeCookie(response, request, "_pass_word_");
		return false;
	}
}
