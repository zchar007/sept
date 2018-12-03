package com.sept.database.demo;

import com.sept.database.db.DatabaseSessionUtil;
import com.sept.database.db.Sql;
import com.sept.database.db.Transaction;
import com.sept.database.db.TransactionManager;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public class Demo {
	public static void main(String[] args) throws AppException {
		DatabaseSessionUtil.initTransactionManager();
		Transaction transaction = TransactionManager.getTransaction();
		transaction.begin();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT trandate FROM pub_sysctrl_info where subsys='11'");
		Sql sql = new Sql();
		sql.setSql(sb);
		DataStore ds = sql.executeQuery();
		System.out.println(ds);
		transaction.rollbackWithoutStart();
	}
}
