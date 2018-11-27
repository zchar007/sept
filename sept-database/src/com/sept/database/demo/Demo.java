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
		sb.append("update * from zx.codes ");
		Sql sql = new Sql();
		sql.setSql(sb);
		 sql.executeUpdate();
		//System.out.println(rdo);
		transaction.rollbackWithoutStart();
	}
}
