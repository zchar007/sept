package com.sept.support.interfaces.log;

/**
 * 特定类型的类需要记日志，如aso，<br>
 * 用于对一种类的记日志类进行规范
 * 
 * @author zchar
 * 
 */
public interface Logable{

	public String getClassName();

	public void setClassName(String className);

	public long getStartTime();

	public void setStartTime(long startTime);

	public long getEndTime();

	public void setEndTime(long endTime);

	public String getAttachMessage();

	public void setAttachMessage(String attachMessage);
	
	public String getLogType();

}
