package com.sept.datastructure.common;

public interface SharedInformationPool {

	/**
	 * 添加一个消息信息
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public void put(String key, Object value) throws Exception;

	/**
	 * 添加一个消息信息到原有信息上
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public void put4Add(String key, Object value) throws Exception;

	/**
	 * 异步添加
	 * 
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void asynchPut(String key, Object value) throws Exception;

	/**
	 * 异步添加一个消息信息到原有信息上
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public void asynchPut4Add(String key, Object value) throws Exception;

	/**
	 * 获取数据
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public Object get(String key) throws Exception;

	/**
	 * 摧毁池子
	 */
	public void killMine() throws Exception;

}
