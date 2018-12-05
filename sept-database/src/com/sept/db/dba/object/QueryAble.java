package com.sept.db.dba.object;

import com.sept.datastructure.DataStore;
import com.sept.db.DBDeploy;
import com.sept.db.dba.DBSessionUtil;
import com.sept.db.dba.DBType;
import com.sept.db.dba.Sql;
import com.sept.exception.AppException;
import com.sept.project.deploy.DeployFactory;

public class QueryAble {
	protected Sql sql = new Sql();// 默认只允许查询的sql

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

	protected DataStore query(String tableName, String id) throws AppException {
		DataStore dsReturn = new DataStore();
		if (DBSessionUtil.getDBType() == DBType.ORACLE) {
			String[] owner_table = tableName.split(".");
			StringBuilder sqlSb = new StringBuilder();
			sqlSb.append("select column_name ");
			sqlSb.append("  from all_cons_columns");
			sqlSb.append(" where  constraint_name = (select constraint_name");
			sqlSb.append("                            from all_constraints");
			sqlSb.append("                           where owner = ?");
			sqlSb.append("                             and table_name = ?");
			sqlSb.append("                             and constraint_type = 'P')");
			this.sql.setSql(sqlSb.toString());
			this.sql.setString(1, owner_table[0].toUpperCase());
			this.sql.setString(2, owner_table[1].toUpperCase());
			DataStore vds = this.sql.executeQuery();
			if (vds.rowCount() == 0) {
				throw new AppException("表[" + tableName + "]无主键，无法时用此query!");
			}

			sqlSb.setLength(0);
			sqlSb.append("select * from " + tableName + " where ");
			for (int i = 0; i < vds.rowCount(); i++) {
				sqlSb.append(" " + vds.getString(i, "column_name") + " = ? ");
				if (i < vds.rowCount() - 1) {
					sqlSb.append(" or ");
				}
			}
			this.sql.setSql(sqlSb);
			for (int i = 0; i < vds.rowCount(); i++) {
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
}
