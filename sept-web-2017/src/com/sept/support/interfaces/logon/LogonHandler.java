package com.sept.support.interfaces.logon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sept.support.model.data.DataObject;

/**
 * 例子<br>
 * 单独的spring配置文件<br>
 * <bean id="logonInterface" class="com.sept.framework.handler.logon.TestLogon"
 * /> <br>
 * <bean id="logonHandler"
 * class="com.sept.framework.handler.logon.LogonHandler"> <br>
 * <property name="logonInterface" ref="logonInterface" /> <br>
 * </bean>
 * 
 * 
 * 
 * web.xml <br>
 * <context-param> <param-name>contextConfigLocation</param-name>
 * <param-value>/WEB-INF/applicationContext.xml</param-value> </context-param>
 * <listener>
 * <listener-class>org.springframework.web.context.ContextLoaderListener
 * </listener-class><br>
 * </listener>
 * 
 * @author zchar
 * @date 创建时间：2017-7-28 上午10:23:00
 * 
 */
public class LogonHandler {

	private static LogonInterface logonInterface = null;

	// 登录检测
	public static boolean checkLogon(DataObject para,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (logonInterface == null) {
			return false;
		} else {
			return logonInterface.checkLogon(para, request, response);
		}
	}
	// 登录检测
	public static boolean checkLogonWithLogon(DataObject para,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (logonInterface == null) {
			return false;
		} else {
			return logonInterface.checkLogonWithLogon(para, request, response);
		}
	}
	// 登录检测
	public static boolean LogonOut(DataObject para,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (logonInterface == null) {
			return false;
		} else {
			return logonInterface.LogonOut(para, request, response);
		}
	}

	public LogonInterface getLogonInterface() {
		return logonInterface;
	}

	public void setLogonInterface(LogonInterface logonInterface) {
		LogonHandler.logonInterface = logonInterface;
	}

}
