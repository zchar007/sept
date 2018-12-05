package com.sept.db.dba;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.sept.db.DBDeploy;
import com.sept.exception.AppException;
import com.sept.project.deploy.DeployFactory;

/**
 * 数据库相关
 * 
 * @author zhangchao_lc
 *
 */
public class DBSessionUtil {
	/** 数据库上下文 **/
	private static ApplicationContext dbaContext = null;
	/** 数据源map **/
	private static HashMap<String, DataSource> dsMap = new HashMap<String, DataSource>();
	/** 事务处理器 **/
	private static HashMap<String, DataSourceTransactionManager> tmMap = new HashMap<String, DataSourceTransactionManager>();

	/**
	 * 初始化默认数据源
	 * 
	 * @throws AppException
	 */
	public static final void initTransactionManager() throws AppException {
		getCurrentTM();
	}

	/**
	 * 初始化指定数据源
	 * 
	 * @param dbName
	 * @throws AppException
	 */
	public static final void initTransactionManager(String dbName) throws AppException {
		getCurrentTM(dbName);
	}

	/**
	 * 获取默认数据源的事务处理器
	 * 
	 * @return
	 * @throws AppException
	 */
	public static final DataSourceTransactionManager getCurrentTM() throws AppException {
		return getCurrentTM(DeployFactory.get(DBDeploy.class).getDefaultDataSource());
	}

	/**
	 * 获取指定数据源的事务处理器
	 *
	 * @param dbName
	 * @return
	 * @throws AppException
	 */
	public static final DataSourceTransactionManager getCurrentTM(String dbName) throws AppException {
		ApplicationContext ctx = getContext();
		DataSource dataSource = null;
		DataSourceTransactionManager txManager = null;

		if (dbName == null || dbName.equals("")) {
			dbName = DeployFactory.get(DBDeploy.class).getDefaultDataSource();
		}

		// 初始化数据源
		if (!dsMap.containsKey(dbName)) {
			dataSource = ctx.getBean(dbName, DataSource.class);
			dsMap.put(dbName, dataSource);
		} else {
			dataSource = dsMap.get(dbName);
		}
		// 初始化事务管理器
		if (!tmMap.containsKey(dbName)) {
			txManager = new DataSourceTransactionManager(dataSource);
			tmMap.put(dbName, txManager);
		} else {
			txManager = tmMap.get(dbName);
		}

		return txManager;
	}

	/**
	 * 取数据库类型
	 * 
	 * @return
	 * @throws AppException
	 */
	public static DBType getDBType() throws AppException {
		return getDBType(DeployFactory.get(DBDeploy.class).getDefaultDataSource());
	}

	/**
	 * 取数据库类型
	 * 
	 * @param dbName
	 * @return
	 * @throws AppException
	 */
	public static DBType getDBType(String dbName) throws AppException {
		if (dbName == null || dbName.equals("")) {
			dbName = DeployFactory.get(DBDeploy.class).getDefaultDataSource();
		}
		DataSource ds = dsMap.get(dbName);
		if (ds instanceof DruidDataSource) {
			String jdbcUrl = ((DruidDataSource) ds).getRawJdbcUrl();

			Pattern regx = Pattern.compile("(?<=jdbc:).*?(?=:)");
			Matcher match = regx.matcher(jdbcUrl);
			if (!match.find()) {
				throw new AppException("未从数据库连接串【" + jdbcUrl + "】中识别出数据库类型!");
			}
			String dbType = match.group();
			if ("POSTGRESQL".equalsIgnoreCase(dbType)) {
				return DBType.POSTGRESQL;
			} else if ("ORACLE".equalsIgnoreCase(dbType)) {
				return DBType.ORACLE;
			} else if ("INSPUR".equalsIgnoreCase(dbType)) {
				return DBType.ORACLE;
			} else if ("MYSQL".equalsIgnoreCase(dbType)) {
				return DBType.MYSQL;
			} else if ("SQLSERVER".equalsIgnoreCase(dbType) || "MICROSOFT".equalsIgnoreCase(dbType)
					|| "MICROSOFT:SQLSERVER".equalsIgnoreCase(dbType)) {
				return DBType.SQLSERVER;
			} else {
				return DBType.UNKNOW;
			}
		}
		return DBType.UNKNOW;
	}

	/**
	 * 获取默认数据库连接的url
	 * 
	 * @return
	 * @throws AppException
	 */
	public static String getJdbcUrl() throws AppException {
		return getJdbcUrl(DeployFactory.get(DBDeploy.class).getDefaultDataSource());
	}

	/**
	 * 获取默认数据库连接的url
	 * 
	 * @param dbName
	 * @return
	 * @throws AppException
	 */
	public static String getJdbcUrl(String dbName) throws AppException {
		if (dbName == null || dbName.equals("")) {
			dbName = DeployFactory.get(DBDeploy.class).getDefaultDataSource();
		}
		DataSource ds = dsMap.get(dbName);
		if (ds instanceof DruidDataSource) {
			return ((DruidDataSource) ds).getRawJdbcUrl();
		}
		return null;
	}

	/**
	 * 取默认数据源密码
	 * 
	 * @throws AppException
	 */
	public static String getPassword() throws AppException {
		return getPassword(DeployFactory.get(DBDeploy.class).getDefaultDataSource());
	}

	/**
	 * 取指定数据源密码
	 * 
	 * @param dbName
	 * @return
	 * @throws AppException
	 */
	public static String getPassword(String dbName) throws AppException {
		if (dbName == null || dbName.equals("")) {
			dbName = DeployFactory.get(DBDeploy.class).getDefaultDataSource();
		}
		DataSource ds = dsMap.get(dbName);
		if (ds instanceof DruidDataSource) {
			return ((DruidDataSource) ds).getPassword();
		}
		return null;
	}

	/**
	 * 取默认数据源用户名
	 * 
	 * @throws AppException
	 */
	public static String getUsername() throws AppException {
		return getUsername(DeployFactory.get(DBDeploy.class).getDefaultDataSource());
	}

	/**
	 * 取指定数据源用户名
	 * 
	 * @throws AppException
	 */
	public static String getUsername(String dbName) throws AppException {
		if (dbName == null || dbName.equals("")) {
			dbName = DeployFactory.get(DBDeploy.class).getDefaultDataSource();
		}
		DataSource ds = dsMap.get(dbName);
		if (ds instanceof DruidDataSource) {
			return ((DruidDataSource) ds).getUsername();
		}
		return null;
	}

	/**
	 * 获取默认数据源jdbc执行与更新类
	 * 
	 * @return
	 * @throws AppException
	 */
	public static JdbcTemplate getJdbcTemplate() throws AppException {
		return getJdbcTemplate(DeployFactory.get(DBDeploy.class).getDefaultDataSource());
	}

	/**
	 * 获取指定数据源jdbc执行与更新类
	 * 
	 * @param dbName
	 * @return
	 * @throws AppException
	 */
	public static JdbcTemplate getJdbcTemplate(String dbName) throws AppException {
		JdbcTemplate jdbcTemplate;
		if (dbName == null || dbName.equals("")) {
			dbName = DeployFactory.get(DBDeploy.class).getDefaultDataSource();
		}
		DataSource dataSource = null;
		if (!dsMap.containsKey(dbName)) {
			dataSource = getContext().getBean(dbName, DataSource.class);
			dsMap.put(dbName, dataSource);
		} else {
			dataSource = dsMap.get(dbName);
		}
		jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	/**
	 * 获取数据库配置上下文
	 * 
	 * @return
	 * @throws AppException
	 */
	public static ApplicationContext getContext() throws AppException {
		if (dbaContext == null) {
			if (DeployFactory.get(DBDeploy.class).isDbaDeployInWar()) {
				dbaContext = new ClassPathXmlApplicationContext(
						"classpath:" + DeployFactory.get(DBDeploy.class).getDbaFileName());
			} else {
				dbaContext = new FileSystemXmlApplicationContext(DeployFactory.get(DBDeploy.class) + File.separator
						+ DeployFactory.get(DBDeploy.class).getDbaFileName());
			}
		}
		return dbaContext;
	}
}
