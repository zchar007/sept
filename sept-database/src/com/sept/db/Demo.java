package com.sept.db;

import com.sept.datastructure.DataStore;
import com.sept.db.dba.DBSessionUtil;
import com.sept.db.dba.Sql;
import com.sept.db.dba.Transaction;
import com.sept.db.dba.TransactionManager;
import com.sept.exception.AppException;
import com.sept.project.deploy.DeployFactory;

public class Demo {
	public static void main(String[] args) throws AppException {
		DeployFactory.init();
		DBSessionUtil.initTransactionManager("dataSource2", com.alibaba.druid.pool.DruidDataSource.class,
				oracle.jdbc.driver.OracleDriver.class, "jdbc:oracle:thin:@10.24.19.152:1521:oradb", "aecc", "password");
		Transaction t = TransactionManager.getTransaction("dataSource2");
		t.begin();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT trandate FROM pub_sysctrl_info where subsys='11'");
		Sql sql = new Sql();
		sql.setSql(sb);
		DataStore ds = sql.executeQuery();
		System.out.println(ds);
	}
}
