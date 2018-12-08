package com.sept.global;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;

/**
 * 获取配置信息，web/desk
 * 
 * @author zchar
 *
 */
public class GlobalNames {
	// 是否使用默认的配置，只在桌面应用下生效,配置了false，则默认读取当前DEFAULT_DEPLOY_NAME
	public static boolean IS_DEFAULT_DEPLOY = true;

	public static String DEFAULT_DEPLOY_NAME = null;
	public static int APP_TYPE = -100;

	private static HashMap<String, DataObject> hmDeploys = null;

	// 以下需要xml配置
	public static final boolean CONFIGINWAR = true;// 默认包内配置
	public static String CONFIGFILE = null;// 包外设置的文档位置
	public static String WEB_APP_NAME = null;// 当前app的名字

	public static String DEFAULT_ENCODING = "UTF-8";
	private static ApplicationContext applicationContext = null;

	public static boolean init(int appType) throws AppException {
		GlobalNames.APP_TYPE = appType;
		// 桌面应用
		if (appType == GlobalVars.APP_TYPE_DESK) {
			// 如果是默认，直接获取
			if (IS_DEFAULT_DEPLOY) {
				GlobalNames.DEFAULT_DEPLOY_NAME = GlobalVars.DEFAULT_DEPLOY_NAME_DESK;
			}
			try {
				String conf = "classpath:applicationContext.xml";
				applicationContext = new ClassPathXmlApplicationContext(conf);// 实例化容器
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// web应用
		else if (appType == GlobalVars.APP_TYPE_WEB) {
			GlobalNames.DEFAULT_DEPLOY_NAME = GlobalVars.DEFAULT_DEPLOY_NAME_WEB;

			// 需要在web.xml中配置
			/**
			 * web.xml <br>
			 * <context-param> <param-name>contextConfigLocation</param-name>
			 * <param-value>/WEB-INF/applicationContext.xml</param-value>
			 * </context-param> <listener>
			 * <listener-class>org.springframework.web
			 * .context.ContextLoaderListener </listener-class><br>
			 */
			try {
				applicationContext = ContextLoaderListener
						.getCurrentWebApplicationContext();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new AppException("严重错误，未知的系统类型！【" + appType + "】");
		}
		hmDeploys = DeployTools.getAllDeploy();
		// 这里还需读取CONFIGINWAR等信息

		// 获取bean信息
		if (appType == GlobalVars.APP_TYPE_DESK) {

		}
		return false;

	}

	public static DataObject getDeploy(String deployName) throws AppException {
		if (null == hmDeploys) {
			throw new AppException("框架GlobalNames还未初始化!");
		}
		return hmDeploys.get(deployName);
	}

	public static String getDeploy(String deployName, String name)
			throws AppException {
		try {
			return hmDeploys.get(deployName).getString(name, null);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取beans工厂
	 * 
	 * @return
	 * @throws AppException
	 */
	public static ApplicationContext getApplicationContext()
			throws AppException {
		if (null == applicationContext) {
			throw new AppException("还未配置applicationContext");
		}
		return applicationContext;
	}

}
