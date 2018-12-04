package com.sept.db.util;

import java.sql.SQLException;
import java.util.Date;

import com.sept.datastructure.DataStore;
import com.sept.db.dba.DatabaseSessionUtil;
import com.sept.db.dba.Sql;
import com.sept.exception.AppException;
import com.sept.project.context.GlobalContext;
import com.sept.util.DateUtil;

public class DBUtil {
	public static long dbTime = 0; // 数据库时间
	public static long serverStartTime = 0; // 获取数据库时间的开始时间
	public static final Object refreshDBTime = new Object();// 并发访问控制信号量，避免并发错误。

	/**
	 * 说明：获取数据库时间
	 * 
	 * @throws SQLException
	 */
	public final static Date getDBDate() throws AppException, SQLException {
		String s = null;
		if (!"true".equals(GlobalContext.DATABASE_ABLE.toLowerCase())) {
			return new Date();
		}
		Sql sql = new Sql();
		if (DatabaseSessionUtil.getDBType() == DatabaseSessionUtil.DBTYPE_ORACLE) {
			sql.setSql("select to_char(sysdate,'yyyy-MM-dd') dbdate from dual");
		} else if (DatabaseSessionUtil.getDBType() == DatabaseSessionUtil.DBTYPE_POSTGRESQL) {
			sql.setSql("select to_char(CURRENT_DATE,'yyyy-MM-dd') dbdate from dual");
		} else {
			throw new AppException("框架不签不能识别数据库类型【" + DatabaseSessionUtil.getDBType() + "】");
		}

		DataStore vds = sql.executeQuery();
		if (vds.rowCount() > 0) {
			s = vds.getString(0, "dbdate");
		}

		return new Date(DateUtil.formatStrToDate(s).getTime());
	}

	/**
	 * 获取数据时间：格式为：yyyy-MM-dd hh:mm:ss
	 * 
	 * @throws SQLException
	 */
	public final static Date getDBTime() throws AppException, SQLException {
		synchronized (refreshDBTime) {
			long currentTime = System.currentTimeMillis();
			if (!"true".equals(GlobalContext.DATABASE_ABLE.toLowerCase())) {
				return new Date();
			}

			if (dbTime == 0 || (dbTime != 0 && currentTime - serverStartTime > 60000)) {
				String s = null;
				Sql sql = new Sql();
				if (DatabaseSessionUtil.getDBType() == DatabaseSessionUtil.DBTYPE_ORACLE) {
					sql.setSql("select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') dbdate from dual");
				} else if (DatabaseSessionUtil.getDBType() == DatabaseSessionUtil.DBTYPE_POSTGRESQL) {
					sql.setSql("select to_char(CURRENT_TIMESTAMP,'yyyy-mm-dd hh24:mi:ss') dbdate from dual");
				} else {
					throw new AppException("框架不签不能识别数据库类型【" + DatabaseSessionUtil.getDBType() + "】");
				}

				DataStore vds = sql.executeQuery();
				if (vds.rowCount() > 0) {
					s = vds.getString(0, "dbdate");
				}

				dbTime = DateUtil.formatStrToDate(s).getTime();
				serverStartTime = System.currentTimeMillis();
			} else {
				dbTime += currentTime - serverStartTime;
				serverStartTime = currentTime;
			}
			return new Date(dbTime);
		}
	}

}
