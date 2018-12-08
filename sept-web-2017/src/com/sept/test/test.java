package com.sept.test;

import com.sept.exception.AppException;
import com.sept.exception.SqlException;
import com.sept.framework.database.DatabaseSessionUtil;
import com.sept.global.GlobalNames;
import com.sept.global.GlobalVars;
import com.sept.support.database.Sql;
import com.sept.support.database.Transaction;
import com.sept.support.database.TransactionManager;
import com.sept.support.model.data.DataStore;

public class test {
	public static void main(String[] args) throws AppException, SqlException {
		GlobalNames.init(GlobalVars.APP_TYPE_DESK);
//		Transaction tm = TransactionManager.getTransaction();
//		tm.begin();
//		Sql sql = new Sql();
//		StringBuffer sb = new StringBuffer();
//		sb.append("select * from BXSYS.CODE_CONFIG");
//		sql.setSql(sb.toString());
//		// sql.setString(1, temp);
//		DataStore vds = sql.executeQuery();
//		System.out.println(vds);
		DatabaseSessionUtil.initTransactionManager();

		Transaction tm = TransactionManager.getTransaction();
		tm.begin();
		StringBuffer sb = new StringBuffer();
		Sql sql = new Sql();
		sb.setLength(0);
		sb.append("delete from zx.codes a");
		sb.append(" where a.type = ?");
		sb.append("   and a.code = ?");
		sql.setSql(sb.toString());
		sql.setString(1, "XB");
		sql.setString(2, "1");
		System.out.println(sql.getSqlString());
		System.out.println(sql.executeUpdate());
		tm.commitWithoutStart();

	}
}
