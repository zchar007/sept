package com.sept.db.dba;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.sept.exception.AppException;

public class TransactionManager {
	public static Transaction getTransaction() throws AppException {
		return getTransaction("dataSource");
	}

	public static Transaction getTransaction(String dbName) throws AppException {
		if ((dbName == null) || (dbName.equals(""))) {
			dbName = "dataSource";
		}
		Transaction currentTransaction = null;
		DataSourceTransactionManager ptm = DatabaseSessionUtil
				.getCurrentTM(dbName);

		currentTransaction = Transaction.getTransaction(ptm, dbName);
		return currentTransaction;
	}
}
