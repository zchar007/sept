package com.sept.framework.web.servlet.def;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.sept.exception.AppException;
import com.sept.exception.BusinessException;
import com.sept.exception.SeptException;
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

/**
 * 业务相关的Controller,
 * 
 * @author zchar
 */
public class BusinessController extends MultiActionController {

	/**
	 * 请求检测，用户验证，用户填入现成变量等
	 */

	// /**
	// * 处理异常
	// *
	// * @throws Exception
	// */
	// @Override
	// protected void handleException(HttpServletRequest request,
	// HttpServletResponse response, Exception e) throws Exception {
	// e.printStackTrace();
	// // 对异常的处理
	// super.handleException(request, response, e);
	// }
	@Override
	protected boolean doBeforResponse(HttpServletRequest request,
			HttpServletResponse response, DataObject para, Method method)
			throws Exception {
		ThreadInterface ct = GlobalToolkit.getCurrentThread();
		ct.setStartTime(DateUtil.getCurrentDate().getTime());
		String requestUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort()// 端口号
				+ request.getRequestURI() + "?" + request.getQueryString();
		ct.setRequestUrl(requestUrl);
		ct.setEntrance(this.getClass().getName() + "." + method.getName());
		CurrentUser user = new CurrentUser();
		DataObject pdo = new DataObject();
		pdo.put("username", para.getString("_user_name_"));
		user.initUser(pdo);
		ct.setUser(user);
		// 设置当前用户信息
		
		//用户校验（伴随着当前线程用户的插入）
		if(!LogonHandler.checkLogonWithLogon(para, request, response)){
			//throw new BusinessException("此处应该转向登录页面！");
			return false;
		}
		
//		UserInterface userInterface;
//		userInterface = GlobalToolkit.getCurrentUser();
//		DataObject pdo = new DataObject();
//		pdo.put("username", "张三2");
//		userInterface.initUser(pdo);
		return true;
	}

	@Override
	protected void doAfterResponse(HttpServletRequest request,
			HttpServletResponse response) throws SeptException {
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
	 * 
	 * 擦屁股 记日志
	 */
	protected void clearSituation() {

	}

	public static String getIpAddr(HttpServletRequest request) {
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
