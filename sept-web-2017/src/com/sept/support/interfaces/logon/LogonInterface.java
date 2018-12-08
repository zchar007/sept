package com.sept.support.interfaces.logon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sept.support.model.data.DataObject;

/**
 * 对于登录，必须实现此接口
 * 
 * @author zchar
 * @date 创建时间：2017-7-31 上午10:25:13
 * 
 */
public interface LogonInterface {
	// 登录检测
	public boolean checkLogon(DataObject para, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	// 中间加测（通常在登录之后进行校验请求人状态）
	public boolean checkLogonWithLogon(DataObject para,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	public boolean LogonOut(DataObject para, HttpServletRequest request,
			HttpServletResponse response);

}
