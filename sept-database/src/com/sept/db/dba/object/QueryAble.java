package com.sept.db.dba.object;

import com.sept.datastructure.DataStore;
import com.sept.db.DBDeploy;
import com.sept.db.DBType;
import com.sept.db.dba.DBSessionUtil;
import com.sept.db.dba.Sql;
import com.sept.db.util.SqlUtil;
import com.sept.exception.AppException;
import com.sept.project.deploy.DeployFactory;

public class QueryAble {
	protected Sql sql;

	public QueryAble() {
		sql = new Sql();// 默认只允许查询的sql
	}

	/**
	 * 查询一个表的数据
	 * 
	 * @param tableName
	 * @return
	 * @throws AppException
	 */
	protected DataStore query(String tableName) throws AppException {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append(" select * from " + tableName + " where rowcount <= ?");
		this.sql.setSql(sqlSb);
		this.sql.setInt(1, DeployFactory.get(DBDeploy.class).getMaxSelectLine());
		DataStore ds = this.sql.executeQuery();
		return ds;
	}

	/**
	 * 依据主键进行查询，无主键不能查询，多主键用or连接
	 * 
	 * @param tableName
	 * @param id
	 * @return
	 * @throws AppException
	 */
	protected DataStore query(String tableName, String id) throws AppException {
		DataStore dsReturn = new DataStore();
		if (DBSessionUtil.getDBType() == DBType.ORACLE) {
			StringBuilder sqlSb = new StringBuilder();
			String[] pks = SqlUtil.getPK(this.sql.getDbSource(), tableName);
			if (pks.length == 0) {
				throw new AppException("表[" + tableName + "]无主键，无法时用此query!");
			}

			sqlSb.setLength(0);
			sqlSb.append("select * from " + tableName + " where ");
			for (int i = 0; i < pks.length; i++) {
				sqlSb.append(" " + pks[i] + " = ? ");
				if (i < pks.length - 1) {
					sqlSb.append(" or ");
				}
			}
			this.sql.setSql(sqlSb);
			for (int i = 0; i < pks.length; i++) {
				this.sql.setString((i + 1), id);
			}
			dsReturn = this.sql.executeQuery();

		}
		return dsReturn;
	}

	/**
	 * 模糊查询
	 * 
	 * @param tableName
	 * @param queryColumnNames
	 * @param conditionColumnNames
	 * @param value
	 * @return
	 * @throws AppException
	 */
	protected DataStore queryFuzzy(String tableName, String queryColumnNames, String conditionColumnNames, String value)
			throws AppException {
		DataStore dsReturn = new DataStore();
		String[] conditionColumnNameArray = conditionColumnNames.split(",");

		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("select " + queryColumnNames + " from " + tableName + " where ");
		for (int i = 0; i < conditionColumnNameArray.length; i++) {
			sqlSb.append(" " + conditionColumnNameArray[i] + " like %" + value + "% ");
			if (i < conditionColumnNameArray.length - 1) {
				sqlSb.append(" or ");
			}
		}
		this.sql.setSql(sqlSb);
		dsReturn = this.sql.executeQuery();

		return dsReturn;
	}

	/**
	 * 字符串形式的condition
	 * 
	 * @param tableName
	 * @param queryColumnNames
	 * @param condition
	 * @return
	 * @throws AppException
	 */
	protected DataStore query(String tableName, String queryColumnNames, String condition) throws AppException {
		DataStore dsReturn = new DataStore();
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("select " + queryColumnNames + " from " + tableName + " where " + condition);

		this.sql.setSql(sqlSb);
		dsReturn = this.sql.executeQuery();

		return dsReturn;
	}

	/**
	 * 设置数据源
	 * 
	 * @param dbSurce
	 */
	protected void setDataBase(String dbSurce) {
		this.sql.setDbSource(dbSurce);
	}
}
