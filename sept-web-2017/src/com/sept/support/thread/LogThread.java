package com.sept.support.thread;

import java.util.ArrayList;

import com.sept.exception.AppException;
import com.sept.support.interfaces.log.Logable;
import com.sept.support.interfaces.thread.ThreadInterface;
import com.sept.support.interfaces.user.UserInterface;

public abstract class LogThread implements Runnable {
	public static final int NOT_SETTED = -1;
	private UserInterface user;// 用户
	private String requestUrl = null;// 请求的路径
	private String entrance = null;// 请求的入口Controller.方法名
	private long startTime = NOT_SETTED;// 开始时间
	private long endTime = NOT_SETTED;// 结束时间
	private ArrayList<Logable> logBizType = null;// 当前线程所操作的记账ASO类名
	private ArrayList<String> sqls = null;// 所执行的sql集合

	@SuppressWarnings("unchecked")
	public LogThread() throws AppException {
		ThreadInterface threadInterface = GlobalToolkit.getCurrentThread();
		this.requestUrl = threadInterface.getRequestUrl();
		this.entrance = threadInterface.getEntrance();
		this.startTime = threadInterface.getStartTime();
		this.endTime = threadInterface.getEndTime();
		this.user = threadInterface.getUser();
		this.logBizType = (ArrayList<Logable>) threadInterface.getLogClass()
				.clone();
		this.sqls = (ArrayList<String>) threadInterface.getSqls().clone();
	}

	@Override
	public void run() {
		// System.out.println("开始记录日志。。。");
		this.doLog();
	}

	public abstract void doLog();

	public String getRequestUrl() {
		return requestUrl;
	}

	public String getEntrance() {
		return entrance;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public ArrayList<Logable> getLogBizType() {
		return logBizType;
	}

	public ArrayList<String> getSqls() {
		return sqls;
	}

	@Override
	public String toString() {
		return "LogThread [requestUrl=" + requestUrl + ",currentuser="+user+", entrance=" + entrance
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", logBizType=" + logBizType + ", sqls=" + sqls + "]";
	}

}