package com.sept.biz;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.sept.database.db.Sql;
import com.sept.datastructure.DataObject;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.exception.BizException;
import com.sept.exception.SeptException;
import com.sept.web.trace.RequestTracer;

public class BizEntry implements BusinessAble {
	protected Sql sql = null;
	private Map<String, Method> handlerMethodMap = new HashMap<String, Method>();

	public BizEntry() {
		this.registerBizMethods();
	}

	@Override
	public DataObject doMethod(Class<? extends BusinessAble> classz, String methodName, DataObject para)
			throws SeptException {
		BizEntry entry = null;
		Method method;
		DataObject returnPara = null;
		try {
			entry = (BizEntry) classz.newInstance();
			// 再执行指定方法
			method = classz.getMethod(methodName, DataObject.class);
			if (!this.handlerMethodMap.containsValue(method)) {
				throw new AppException("不是合法的BizEntry方法--[" + this.getClass().getName() + "." + method.getName() + "]");
			}
			method.setAccessible(true);
			returnPara = (DataObject) method.invoke(entry, para);
		} catch (Exception e) {
			if (!(e.getCause() instanceof BizException)) {
				LogHandler.fatal("执行Biz方法异常", e);
			}
			// 不是的话直接抛出
			if (e.getCause() instanceof BizException) {
				throw (BizException) (e.getCause());
			}
			if (e.getCause() instanceof AppException) {
				throw (AppException) (e.getCause());
			}
			throw new AppException(e);
		}
		return returnPara;
	}

	@Override
	public DataObject doMethod(String methodName, DataObject para) throws SeptException {
		BizEntry entry = null;
		Method methodz;
		DataObject returnPara = null;
		try {
			entry = this.getClass().newInstance();
			RequestTracer.startNode(this.getClass().getName(), methodName);
			methodz = this.getClass().getDeclaredMethod(methodName, DataObject.class);
			if (!this.handlerMethodMap.containsValue(methodz)) {
				throw new AppException("不是合法的BizEntry方法--[" + this.getClass().getName() + "." + methodName + "]");
			}
			methodz.setAccessible(true);
			returnPara = (DataObject) methodz.invoke(entry, para);
		} catch (Exception e) {
			if (!(e.getCause() instanceof BizException)) {
				LogHandler.fatal("执行Biz方法异常", e);
			}
			// 不是的话直接抛出
			if (e.getCause() instanceof BizException) {
				throw (BizException) (e.getCause());
			}
			if (e.getCause() instanceof AppException) {
				throw (AppException) (e.getCause());
			}
			throw new AppException(e);
		}
		return returnPara;
	}

	/**
	 * 注册所有的方法
	 */
	private void registerBizMethods() {
		this.handlerMethodMap.clear();
		Method[] methods = this.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (this.isBizMethod(method)) {
				registerBizMethod(method);
			}
		}
	}

	/**
	 * 根据method注册
	 * 
	 * @param method
	 */
	private void registerBizMethod(Method method) {
		this.handlerMethodMap.put(method.getName(), method);
	}

	/**
	 * 是不是对外提供功能的method(入参出参都是唯一的DataObject)
	 * 
	 * @param method
	 * @return
	 */
	private boolean isBizMethod(Method method) {
		// 返回值
		if (DataObject.class.getName().equals(method.getReturnType().getName())) {
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
