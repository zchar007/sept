package com.sept.framework.web.ao.log;

import com.sept.support.interfaces.log.Logable;
import com.sept.support.util.DateUtil;

public class ASOLog implements Logable {
	public static final int NOT_SETTED = -1;
	private String className = null;// 请求的路径
	private long startTime = NOT_SETTED;// 开始时间
	private long endTime = NOT_SETTED;// 结束时间
	private String attachMessage = null;// 附加信息

	public ASOLog() {
		this.setStartTime(DateUtil.getCurrentDate().getTime());
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getAttachMessage() {
		return attachMessage;
	}

	public void setAttachMessage(String attachMessage) {
		this.attachMessage = attachMessage;
	}

	@Override
	public String getLogType() {
		return "ASO";
	}

}
