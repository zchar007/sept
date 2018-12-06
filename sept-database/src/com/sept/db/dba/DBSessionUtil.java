package com.sept.db.dba;

import java.io.File;
import java.io.IOException;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.sept.db.DBDeploy;
import com.sept.db.DBType;
import com.sept.db.pdf.IPBFSource;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.project.classz.dynamic.JavaStringCompiler;
import com.sept.project.deploy.DeployFactory;
import com.sept.util.StringUtil;

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
	 * 初始化默认数据源
	 * 
	 * @throws AppException
	 */
	public static final void initTransactionManager(String dbName, Class<?> dataSourceClass, Class<?> driverClass,
			String url, String username, String password) throws AppException {
		addTransactionManager(dbName, dataSourceClass, driverClass, url, username, password);
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
	 * 手动添加一个数据源
	 * 
	 * @param dbName
	 * @param dataSourceClass
	 * @param driverClass
	 * @param url
	 * @param username
	 * @param password
	 * @throws AppException
	 */
	@SuppressWarnings("resource")
	public static final DataSourceTransactionManager addTransactionManager(String dbName, Class<?> dataSourceClass,
			Class<?> driverClass, String url, String username, String password) throws AppException {
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
		Object dsObj = null;
		Driver driverObj = null;
		DataSource ds = null;

		try {
			dsObj = dataSourceClass.newInstance();
			driverObj = (Driver) driverClass.newInstance();
		} catch (Exception e) {
			throw new AppException(e);
		}
		 

		if (dsObj instanceof com.alibaba.druid.pool.DruidDataSource) {
			com.alibaba.druid.pool.DruidDataSource dsT = (DruidDataSource) dsObj;
			dsT.setDriver(driverObj);
			dsT.setUsername(username);
			dsT.setPassword(password);
			dsT.setUrl(url);
			ds = dsT;
		} else if (dsObj instanceof IPBFSource) {
			IPBFSource dsT = (IPBFSource) dsObj;
			dsT.setDriver(driverObj);
			dsT.setUsername(username);
			dsT.setPassword(password);
			dsT.setUrl(url);
			ds = dsT;
		}
		AnnotationDBConfig.setDataSource(ds);
		acac.register(AnnotationDBConfig.class);
		acac.refresh();

		DataSource dataSource = null;
		DataSourceTransactionManager txManager = null;

		if (dbName == null || dbName.equals("")) {
			dbName = DeployFactory.get(DBDeploy.class).getDefaultDataSource();
		}

		// 初始化数据源
		if (!dsMap.containsKey(dbName)) {
			dataSource = acac.getBean("dsBean", DataSource.class);
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
		acac.close();

		return txManager;

	}

	@Deprecated
	private static final Class<?> createAnnotationContext(String dbName, Class<?> dataSourceClass, Class<?> driverClass,
			String url, String username, String password) throws AppException {
		String className = "AnnotationConfig_" + StringUtil.getUUID();
		Class<?> clazz = null;
		StringBuilder classz = new StringBuilder();
		classz.setLength(0);
		classz.append("package com.sept.db.dba; ").append("\n");
		classz.append("import javax.sql.DataSource; ").append("\n");
		classz.append("import org.springframework.context.annotation.Bean; ").append("\n");
		classz.append("import org.springframework.context.annotation.Configuration; ").append("\n");

		classz.append("@Configuration").append("\n");
		classz.append("public class " + className + " { ").append("\n");
		classz.append(" @Bean(name=\"" + dbName + "\")").append("\n");
		classz.append("  public DataSource " + dbName + "() { ").append("\n");
		classz.append("    " + dataSourceClass.getName() + " ds = new " + dataSourceClass.getName() + "(); ")
				.append("\n");
		classz.append("    ds.setUsername(\"" + username + "\"); ").append("\n");
		classz.append("    ds.setPassword(\"" + password + "\"); ").append("\n");
		classz.append("    ds.setUrl(\"" + url + "\"); ").append("\n");
		classz.append("    ds.setDriver(new " + driverClass.getName() + "()); ").append("\n");
		classz.append("    return ds; ").append("\n");
		classz.append("  } ").append("\n");
		classz.append("} ").append("\n");
		System.out.println(classz.toString());
		JavaStringCompiler compiler = new JavaStringCompiler();
		Map<String, byte[]> results;
		try {
			results = compiler.compile(className + ".java", classz.toString());
			clazz = compiler.loadClass("com.sept.db.dba." + className, results);
		} catch (IOException | ClassNotFoundException e) {
			LogHandler.fatal(e);
			throw new AppException(e);
		}
		return clazz;

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
