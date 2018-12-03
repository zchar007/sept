package com.sept.database.db;

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
import com.sept.exception.AppException;
import com.sept.project.context.GlobalContext;
@SuppressWarnings( value = { "" })
public class DatabaseSessionUtil {

	/**
	 * 数据库类型
	 */
	public static final int DBTYPE_ORACLE = 0;
	public static final int DBTYPE_POSTGRESQL = 1;

	public static final String DEFAULT_DATASOURCE_NAME = "dataSource";

	private static HashMap<String, DataSource> dsMap = new HashMap<String, DataSource>();

	private static HashMap<String, DataSourceTransactionManager> tmMap = new HashMap<String, DataSourceTransactionManager>();

	private static ApplicationContext ctx = null;
	private final int DEFAULT_DTASOURCE_NAME = 1;
	public static JdbcTemplate getCurrentSession() throws AppException {
		return getCurrentSession(DEFAULT_DATASOURCE_NAME);
	}

	public static String getJdbcUrl() {
		DataSource ds = dsMap.get(DEFAULT_DATASOURCE_NAME);
		if (ds instanceof DruidDataSource) {
			@SuppressWarnings("resource")
			DruidDataSource druidDs = (DruidDataSource) ds;
			return druidDs.getRawJdbcUrl();
		} else
			return null;
	}

	/**
	 * 取密码
	 */
	public static String getPassword() {
		return getPassword(DEFAULT_DATASOURCE_NAME);
	}

	public static String getPassword(String dbName) {
		DataSource ds = dsMap.get(dbName);
		if (ds instanceof DruidDataSource) {
			@SuppressWarnings("resource")
			DruidDataSource druidDs = (DruidDataSource) ds;
			return druidDs.getPassword();
		} else
			return null;
	}

	/**
	 * 取用户名
	 */
	public static String getUsername() {
		return getUsername(DEFAULT_DATASOURCE_NAME);
	}

	public static String getUsername(String dbName) {
		DataSource ds = dsMap.get(dbName);
		if (ds instanceof DruidDataSource) {
			@SuppressWarnings("resource")
			DruidDataSource druidDs = (DruidDataSource) ds;
			return druidDs.getUsername();
		} else
			return null;
	}

	public static JdbcTemplate getCurrentSession(String dbName) throws AppException {
		JdbcTemplate jdbcTemplate;
		if (dbName == null || dbName.equals("")) {
			dbName = DEFAULT_DATASOURCE_NAME;
		}
		DataSource dataSource = null;
		if (!dsMap.containsKey(dbName)) {
			dataSource = getCtx().getBean(dbName, DataSource.class);
			dsMap.put(dbName, dataSource);
		} else {
			dataSource = dsMap.get(dbName);
		}
		jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	/**
	 * 取数据库类型
	 */
	public static int getDBType() throws AppException {
		return getDBType(DEFAULT_DATASOURCE_NAME);
	}

	public static int getDBType(String dbName) throws AppException {
		DataSource ds = dsMap.get(dbName);
		if (ds instanceof DruidDataSource) {
			@SuppressWarnings("resource")
			DruidDataSource druidDs = (DruidDataSource) ds;
			String jdbcUrl = druidDs.getRawJdbcUrl();

			Pattern regx = Pattern.compile("(?<=jdbc:).*?(?=:)");
			Matcher match = regx.matcher(jdbcUrl);
			if (!match.find()) {
				throw new AppException("未从数据库连接串【" + jdbcUrl + "】中识别出数据库类型!");
			}
			String dbType = match.group();
			if ("POSTGRESQL".equalsIgnoreCase(dbType)) {
				return DBTYPE_POSTGRESQL;
			} else if ("ORACLE".equalsIgnoreCase(dbType)) {
				return DBTYPE_ORACLE;
			} else if ("INSPUR".equalsIgnoreCase(dbType)) {
				return DBTYPE_ORACLE;
			} else {
				throw new AppException("数据库连接串【" + jdbcUrl + "】中数据库类型【" + dbType + "】目前框架无法识别!");
			}
		}
		throw new AppException("未获取到连接【" + dbName + "】的数据库类型!");
	}

	public static DataSourceTransactionManager getCurrentTM() throws AppException {
		return getCurrentTM(DEFAULT_DATASOURCE_NAME);
	}

	public static DataSourceTransactionManager getCurrentTM(String dbName) throws AppException {

		DataSourceTransactionManager txManager = null;
		if (!tmMap.containsKey(dbName)) {
			DruidDataSource dataSource = getCtx().getBean(dbName, DruidDataSource.class);
			txManager = new DataSourceTransactionManager(dataSource);
			tmMap.put(dbName, txManager);
		} else {
			txManager = tmMap.get(dbName);
		}
		return txManager;
	}

	public static void initTransactionManager() throws AppException {
		ApplicationContext ctx = getCtx();
		// 初始化数据源
		DataSource dataSource = ctx.getBean(DEFAULT_DATASOURCE_NAME, DataSource.class);
		dsMap.put(DEFAULT_DATASOURCE_NAME, dataSource);
		// 初始化事务管理器
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(dataSource);
		tmMap.put(DEFAULT_DATASOURCE_NAME, txManager);
	}

	public static ApplicationContext getCtx() throws AppException {
		String dbConfigName = GlobalContext.DATABASE_CONFIG_NAME;
		if (ctx == null) {
			if ("true".equalsIgnoreCase(GlobalContext.DATABASE_IS_CONFIG_INWAR)) {
				ctx = new ClassPathXmlApplicationContext("classpath:" + dbConfigName);
			} else {
				ctx = new FileSystemXmlApplicationContext(
						GlobalContext.DATABASE_CONFIG_OUTWARFILE + GlobalContext.WEB_APP_NAME + "/" + dbConfigName);
			}
		}
		return ctx;
	}

	public static void setCtx(ApplicationContext application) {
		ctx = application;
	}
}
