package com.sept.support.interfaces.user;

import java.util.Date;

import com.sept.support.model.data.DataObject;

public interface UserInterface {

	/**
	 * 初始化用户，必须传入用户ID（唯一标识） 返回false表示无此用户
	 * 
	 * @param para
	 *            userid,username必传,若传入password，则验证后进行返回，<br>
	 *            验证不通过则返回false
	 * @return
	 */
	public boolean initUser(DataObject para);

	/**
	 * 获取用户名
	 * 
	 * @return
	 */
	public String getUserName();

	/**
	 * 获取用户ID
	 * 
	 * @return
	 */
	public String getUserId();

	/**
	 * 检查用户密码是否准确
	 * 
	 * @return
	 */
	public boolean checkUserPassword(String password);

	/**
	 * 检查当前请求的用户是不是创建此user时登录的IP
	 * 
	 * @param nowIp
	 * @return
	 */
	public boolean checkSignIP(String nowIp);

	/**
	 * 获取登录的时间,默认为当前初始化用户的时间
	 * 
	 * @return
	 */
	public Date getSignTime();

}
