package com.sept.support.interfaces.thread;

import java.util.ArrayList;

import com.sept.support.interfaces.log.Logable;
import com.sept.support.interfaces.user.UserInterface;

/**
 * 
 * @author zchar
 * 
 */
public interface ThreadInterface {

	public void setStartTime(long startTime);// 设置开始时间

	public long getStartTime();// 获取时间

	public void setEndTime(long endTime);// 设置结束时间

	public long getEndTime();// 获取结束时间

	public void setUser(UserInterface user);// 设置请求用户

	public UserInterface getUser();// 获取用户

	public void setRequestUrl(String url);// 设置请求url

	public String getRequestUrl();// 获取请求url

	public void setEntrance(String entrance);// 设置controller.method

	public String getEntrance();// 获取controller.method

	public void addLogClass(Logable log);// 设置可记录日志的Log信息

	public ArrayList<Logable> getLogClass();// 获取所有记日志的信息

	public void addSql(String sql);// 设置sql

	public ArrayList<String> getSqls();// 获取所有sql

}
