package com.sept.io.encrypt;

import com.sept.exception.ApplicationException;

public interface MessageStore {

	/**
	 * 添加一个消息信息
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public void putMessage(String key, Object value) throws Exception;

	/**
	 * 防止先取出后添加回造成的并发问题<br>
	 * （取过之后释放了锁有可能被其他线程获取到锁而对数据进行了操作）
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public void putAndAddMessage(String key, Object value) throws Exception;

	/**
	 * 获取数据
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public Object getMessage(String key) throws Exception;

	/**
	 * 提供静态的异步方法，用于对原有数据进行加操作<br>
	 * 防止因等待锁而导致主线程缓慢
	 * 
	 * @author 张超
	 * @throws ApplicationException
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public void asynchPutAndAddMessage(String key, Object value) throws Exception;

	/**
	 * 提供静态的异步方法，用于对数据进行添加操作<br>
	 * 防止因等待锁而导致主线程缓慢
	 * 
	 * @author 张超
	 * @throws ApplicationException
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public void asynchPutMessage(String key, Object value) throws Exception;

	public void killMine();

}
