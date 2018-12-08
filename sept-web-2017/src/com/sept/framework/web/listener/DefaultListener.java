package com.sept.framework.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sept.exception.AppException;
import com.sept.global.GlobalNames;
import com.sept.global.GlobalVars;
import com.sept.support.common.DebugManager;
import com.sept.support.database.DatabaseSessionUtil;

public class DefaultListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		DebugManager.println("SEPT--:", "已关闭:"
				+ sce.getServletContext().getContextPath());
	}

	public void contextInitialized(ServletContextEvent sce) {
		try {
			GlobalNames.init(GlobalVars.APP_TYPE_WEB);
			String dbAble = GlobalNames.getDeploy("sql", "SPRING-DBABLE");
			if ("true".equals(dbAble)) {
				DatabaseSessionUtil.initTransactionManager();
				DebugManager.println("SEPT--:", "数据库已开启！！");
			} else {
				DebugManager.println("SEPT--:", "数据库未开启！！");
			}
			DebugManager.println("SEPT--:", "正在开启:"
					+ sce.getServletContext().getContextPath());
			DebugManager.println("SEPT--:", "已设置APP类型为-web");
			DebugManager.println("SEPT--:", "已设置APP配置路径为-"
					+ GlobalNames.DEFAULT_DEPLOY_NAME);
			// 读取各种信息到AntGlobNames
			DebugManager.println("SEPT--:", "开启完毕:"
					+ sce.getServletContext().getContextPath());
		} catch (AppException e) {
			e.printStackTrace();
		}

	}

}
