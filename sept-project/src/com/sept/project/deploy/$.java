package com.sept.project.deploy;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;

import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.project.context.ContextUtil;

public class $ {
	private static String CONF_URL = "applicationContext-deploy.xml";
	private static final HashMap<Class<?>, AbstractDeployContext> hmConfigs = new HashMap<>();
	private static ApplicationContext ac;

	/**
	 * 初始化配置
	 */
	public static final void init() {
		ac = ContextUtil.getApplicationContext(CONF_URL);
	}

	/**
	 * 获取配置
	 * 
	 * @param classz
	 * @return
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T get(Class<T> classz) throws AppException {
		Class<?> classFather = classz.getSuperclass();
		if (!classFather.equals(AbstractDeployContext.class)) {
			throw new AppException("[严重错误]配置类必须继承(" + AbstractDeployContext.class + ")!");
		}
		if (!hmConfigs.containsKey(classz)) {
			AbstractDeployContext bean = null;
			try {
				bean = (AbstractDeployContext) ac.getBean(classz);
			} catch (Exception e) {
				LogHandler.info("未找到配置[" + classz + "],将采用配置的默认init初始化配置类！");
				try {
					bean = (AbstractDeployContext) classz.newInstance();
					bean.init();
				} catch (InstantiationException | IllegalAccessException e1) {
					LogHandler.error(e1);
					throw new AppException(e);
				}

			}
			hmConfigs.put(classz, bean);
		}
		return (T) hmConfigs.get(classz);
	}

	public static String getConfUrl() {
		return CONF_URL;
	}

	public static void setConfUrl(String confUrl) {
		CONF_URL = confUrl;
	}

}
