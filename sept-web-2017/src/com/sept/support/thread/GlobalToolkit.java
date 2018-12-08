package com.sept.support.thread;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sept.exception.AppException;
import com.sept.global.GlobalNames;
import com.sept.support.interfaces.thread.ThreadInterface;
import com.sept.support.interfaces.user.UserInterface;

public class GlobalToolkit {

	private static ThreadLocal<HashMap<String, Object>> threadStore = new ThreadLocal<HashMap<String, Object>>();
	private static final String THREAD_NAME = "__sept__currentthraad__";
	private static final String USER_NAME = "__sept__currentuser__";
	private static final ExecutorService logThreadPool = Executors
			.newFixedThreadPool(1000);

	/**
	 * 获取当前线程
	 * 
	 * @author zchar
	 * @date 创建时间：2017-8-1 下午9:30:07
	 * @return
	 * @throws AppException
	 */
	public static ThreadInterface getCurrentThread() throws AppException {
		HashMap<String, Object> hm = threadStore.get();
		if (null == hm) {
			hm = new HashMap<String, Object>();
			threadStore.set(hm);
		}
		if (hm.containsKey(THREAD_NAME) && null != hm.get(THREAD_NAME)) {
			return (ThreadInterface) hm.get(THREAD_NAME);
		} else {
			ThreadInterface threadInterface = getThreadBean();
			hm.put(THREAD_NAME, threadInterface);
			threadStore.set(hm);
			return threadInterface;
		}
	}

	/**
	 * 获取当前用户
	 * 
	 * @author zchar
	 * @date 创建时间：2017-8-1 下午9:31:51
	 * @return
	 * @throws AppException
	 */
	public static UserInterface getCurrentUser() throws AppException {
		HashMap<String, Object> hm = threadStore.get();
		if (null == hm) {
			hm = new HashMap<String, Object>();
			threadStore.set(hm);
		}
		if (hm.containsKey(USER_NAME) && null != hm.get(USER_NAME)) {
			return (UserInterface) hm.get(USER_NAME);
		} else {
			UserInterface userInterface = getUserBean();
			hm.put(USER_NAME, userInterface);
			threadStore.set(hm);
			return userInterface;
		}
	}

	/**
	 * 增加一个线程数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void addPara(String key, Object value) {
		HashMap<String, Object> hm = threadStore.get();
		if (null == hm) {
			hm = new HashMap<String, Object>();
		}
		hm.put(key, value);
		threadStore.set(hm);
	}

	/**
	 * 以名称获取一个线程数据
	 * 
	 * @param key
	 * @return
	 */
	public static Object getPara(String key) {
		HashMap<String, Object> hm = threadStore.get();
		if (null == hm) {
			hm = new HashMap<String, Object>();
			threadStore.set(hm);
		}

		if (hm.containsKey(key)) {
			return hm.get(key);
		}
		return null;
	}

	/**
	 * 获取当前线程的所有数据
	 * 
	 * @return
	 */
	public static HashMap<String, Object> get() {
		return threadStore.get();

	}

	/**
	 * 移除当前线程所存的所有数据
	 */
	public static void clear() {
		threadStore.remove();
	}

	/**
	 * 实例化一个用户bean
	 * 
	 * @author zchar
	 * @date 创建时间：2017-8-1 下午10:01:50
	 * @return
	 * @throws AppException
	 */
	public static UserInterface getUserBean() throws AppException {
		try {
			return (UserInterface) GlobalNames.getApplicationContext()
					.getBean("userBean");
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	/**
	 * 实例化一个线程bean
	 * 
	 * @author zchar
	 * @date 创建时间：2017-8-1 下午10:02:05
	 * @return
	 * @throws AppException
	 */
	public static ThreadInterface getThreadBean() throws AppException {
		try {
			return (ThreadInterface) GlobalNames.getApplicationContext()
					.getBean("threadBean");
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	/**
	 * 实例化一个线程bean
	 * 
	 * @author zchar
	 * @date 创建时间：2017-8-1 下午10:02:05
	 * @return
	 * @throws AppException
	 */
	public static LogThread getLogBean() throws AppException {
		try {
			return (LogThread) GlobalNames.getApplicationContext().getBean(
					"logBean");
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	/**
	 * 实例化一个线程bean
	 * 
	 * @author zchar
	 * @date 创建时间：2017-8-1 下午10:02:05
	 * @return
	 * @throws AppException
	 */
	public static Object getBean(String beanId) throws AppException {
		try {
			return GlobalNames.getApplicationContext().getBean(beanId);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 记录日志
	 * 
	 * @throws AppException
	 */
	public static void doLog() {
		try {
			// TODO 记录各种信息的日志
			LogThread lt = getLogBean();
			logThreadPool.execute(lt);
			GlobalToolkit.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
