package com.sept.framework.web.ao;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sept.exception.AppException;
import com.sept.exception.BusinessException;
import com.sept.framework.web.requestTrace.CallStackTracer;
import com.sept.support.database.Sql;
import com.sept.support.model.data.DataObject;
import com.sept.support.thread.GlobalToolkit;

/**
 * 所有AO的父类
 * 
 * @author zchar
 */
public abstract class AbstractSupport {
	protected Sql sql = null;
	private Map<String, Method> handlerMethodMap = new HashMap<String, Method>();
	public static final String PAGE_NOT_FOUND_LOG_CATEGORY = "org.springframework.web.servlet.PageNotFound";
	protected static final Log pageNotFoundLogger = LogFactory
			.getLog(PAGE_NOT_FOUND_LOG_CATEGORY);

	public AbstractSupport() {
		this.sql = new Sql();
		this.registerAbstractSupportMethods();
	}

	// 老办法
	// private DiyTransaction dt;
	// public Sql sql;
	// 这只是一个参考，将来可以直接发送用户类
	// private String userId;
	protected DataObject doMethod(Class<? extends AbstractSupport> classz,
			String methodName, DataObject para) throws Exception {
		DataObject returnPara = null;
		AbstractSupport obj;

		try {
			obj = classz.newInstance();
			// 再执行指定方法
			Method methodz = classz.getMethod(methodName, DataObject.class);
			methodz.setAccessible(true);
			returnPara = (DataObject) methodz.invoke(obj, para);
		} catch (Exception e) {
			// 不是的话直接抛出
			if (e.getCause() instanceof BusinessException) {
				throw (BusinessException) (e.getCause());
			}
			if (e.getCause() instanceof AppException) {
				throw (AppException) (e.getCause());
			}
			throw new AppException(e);
		}

		return returnPara;
	}

	/**
	 * 以方法名执行本地方法
	 * 
	 * @param method
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public DataObject doMethod(String method, DataObject para) throws Exception {
		AbstractSupport obj = null;
		Method methodz;
		DataObject returnPara = null;
		try {
			obj = this.getClass().newInstance();
			CallStackTracer.startNode(this.getClass().getName(), method);
			methodz = this.getClass().getDeclaredMethod(method,
					DataObject.class);
			if (!isAbstractSupport(methodz)) {
				throw new AppException("不是合法的AbstractSupport方法--["
						+ this.getClass().getName() + "." + method + "]");
			}
			methodz.setAccessible(true);
			returnPara = (DataObject) methodz.invoke(obj, para);
		} catch (Exception e) {
			e.printStackTrace();
			// 不是的话直接抛出
			if (e.getCause() instanceof BusinessException) {
				throw (BusinessException) (e.getCause());
			}
			if (e.getCause() instanceof AppException) {
				throw (AppException) (e.getCause());
			}
			throw new AppException(e);
		}
		return returnPara;
	}

	public void bizException(String msg) throws BusinessException {
		throw new BusinessException(msg);
	}

	/**
	 * 获取参数,其他子类如果想获取具体参数，可以从此方法取
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-3
	 * @since V1.0
	 */
	protected Object getPara(String key) {
		return GlobalToolkit.getPara(key);
	}

//	/**
//	 * 方法简介.记日志 具体的类有具体的记日志方法
//	 * 
//	 * @author 张超
//	 * @throws DiyException
//	 * @date 创建时间 2017-6-13
//	 * @since V1.0
//	 */
//	protected void log(String tableName, String columns, DataObject para)
//			throws SeptException {
//		QuickSqlTools.insert(tableName, columns, para);
//	}

	/**
	 * 注册所有的方法
	 */
	private void registerAbstractSupportMethods() {
		this.handlerMethodMap.clear();
		Method[] methods = this.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			// We're looking for methods with given parameters.
			Method method = methods[i];
			if (isAbstractSupport(method)) {
				registerAbstractSupportMethod(method);
				// System.out.println(method);
			}
		}
	}

	/**
	 * 根据method注册
	 * 
	 * @param method
	 */
	private void registerAbstractSupportMethod(Method method) {
		this.handlerMethodMap.put(method.getName(), method);
	}

	/**
	 * 是不是对外提供功能的method
	 * 
	 * @param method
	 * @return
	 */
	private boolean isAbstractSupport(Method method) {
		if (DataObject.class.getName().equals(method
				.getReturnType().getName())) {
			Class<?>[] parameterTypes = method.getParameterTypes();
			// 一个入参
			if (parameterTypes.length == 1) {
				if (DataObject.class.equals(parameterTypes[0])) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

}
