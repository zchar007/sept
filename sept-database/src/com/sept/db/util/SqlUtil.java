package com.sept.db.util;

import com.sept.datastructure.DataStore;
import com.sept.db.DBDeploy;
import com.sept.db.DBType;
import com.sept.db.dba.DBSessionUtil;
import com.sept.db.dba.Sql;
import com.sept.exception.AppException;
import com.sept.project.deploy.DeployFactory;

public class SqlUtil {
	/**
	 * 获取某个表的主键
	 * 
	 * @param table
	 * @return
	 * @throws AppException
	 */
	public static final String[] getPK(String dataSource, String table) throws AppException {
		DataStore vds = null;
		if (DBType.ORACLE.equals(DBSessionUtil.getDBType())) {
			String[] ut = table.split(",");
			Sql sql = new Sql(dataSource);
			StringBuilder sqlSb = new StringBuilder();
			sqlSb.append("select column_name ");
			sqlSb.append("  from all_cons_columns");
			sqlSb.append(" where  constraint_name = (select constraint_name");
			sqlSb.append("                            from all_constraints");
			sqlSb.append("                           where owner = ?");
			sqlSb.append("                             and table_name = ?");
			sqlSb.append("                             and constraint_type = 'P')");
			sql.setSql(sqlSb.toString());
			sql.setString(1, ut[0].toUpperCase());
			sql.setString(2, ut[1].toUpperCase());
			vds = sql.executeQuery();
		} else {
			throw new AppException("暂时无法识别数据库！");
		}
		String[] pks = new String[vds.rowCount()];
		for (int i = 0; i < vds.rowCount(); i++) {
			pks[i] = vds.getString(i, "column_name");
		}
		return pks;
	}

	/**
	 * 获取某个表的主键
	 * 
	 * @param table
	 * @return
	 * @throws AppException
	 */
	public static final String[] getPK(String table) throws AppException {
		return getPK(DeployFactory.get(DBDeploy.class).getDefaultDataSource(), table);
	}

}
