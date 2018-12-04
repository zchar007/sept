package com.sept.db.dba.temp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.project.context.GlobalContext;

/**
 * 数据库相关
 * 
 * @author zhangchao_lc
 *
 */
public class DBSessionUtil {
	private static String DEFAULT_DATASOURCE_NAME = "dataSource";
	private static ApplicationContext aContext = null;

	static {
		try {
			DEFAULT_DATASOURCE_NAME = GlobalContext.DATABASE_ABLE;
		} catch (Exception e) {
			LogHandler.info("未配置默认数据库，程序默认为“dataSource”。");
		}
	}

	public static ApplicationContext getContext() throws AppException {
		String dbConfigName = GlobalContext.DATABASE_CONFIG_NAME;
		if (aContext == null) {
			if ("true".equalsIgnoreCase(GlobalContext.DATABASE_IS_CONFIG_INWAR)) {
				aContext = new ClassPathXmlApplicationContext("classpath:" + dbConfigName);
			} else {
				aContext = new FileSystemXmlApplicationContext(
						GlobalContext.DATABASE_CONFIG_OUTWARFILE + GlobalContext.WEB_APP_NAME + "/" + dbConfigName);
			}
		}
		return aContext;
	}

	public static String getDefaultDBName() {
		return DEFAULT_DATASOURCE_NAME;
	}
}
