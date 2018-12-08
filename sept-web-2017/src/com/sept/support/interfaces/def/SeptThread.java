package com.sept.support.interfaces.def;

import java.util.ArrayList;

import com.sept.support.interfaces.log.Logable;
import com.sept.support.interfaces.thread.ThreadInterface;
import com.sept.support.interfaces.user.UserInterface;

public class SeptThread implements ThreadInterface {
	public static final int NOT_SETTED = -1;
	private CurrentUser user = null;
	private String requestUrl = null;// 请求的路径
	private String entrance = null;// 请求的入口Controller.方法名
	private long startTime = NOT_SETTED;// 开始时间
	private long endTime = NOT_SETTED;// 结束时间
	private ArrayList<Logable> logBizType = null;// 当前线程所操作的记账ASO类名
	private ArrayList<String> sqls = null;// 所执行的sql集合

	@Override
	public String getRequestUrl() {
		return requestUrl;
	}

	@Override
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Override
	public String getEntrance() {
		return entrance;
	}

	@Override
	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

	@Override
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public long getEndTime() {
		return endTime;
	}

	@Override
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	@Override
	public void addLogClass(Logable log) {
		if (null == this.logBizType) {
			this.logBizType = new ArrayList<>();
		}
		this.logBizType.add(log);
	}

	@Override
	public ArrayList<Logable> getLogClass() {
		return null == logBizType?new ArrayList<Logable>():logBizType;
	}

	@Override
	public void addSql(String sql) {
		if (null == this.sqls) {
			this.sqls = new ArrayList<>();
		}
		this.sqls.add(sql);
	}

	public ArrayList<String> getSqls() {
		return null == sqls?new ArrayList<String>():sqls;
	}

	@Override
	public void setUser(UserInterface user) {
		this.user = (CurrentUser) user;
	}

	@Override
	public UserInterface getUser() {
		return user;
	}

}
