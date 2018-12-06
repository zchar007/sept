package com.sept.db;

import java.util.HashMap;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.sept.db.DBDeploy;
import com.sept.exception.AppException;
import com.sept.project.deploy.DeployFactory;

public class TransactionManager {
	private static final ThreadLocal<HashMap<String, Transaction>> transLocalMap = new ThreadLocal<HashMap<String, Transaction>>();

	/**
	 * 获取默认数据源的实务控制器
	 * 
	 * @return
	 * @throws AppException
	 */
	public static final Transaction getTransaction() throws AppException {
		return getTransaction(DeployFactory.get(DBDeploy.class).getDefaultDataSource());
	}

	/**
	 * 获取指定数据源的实务控制器
	 * 
	 * @return
	 * @throws AppException
	 */
	public static final Transaction getTransaction(String dbName) throws AppException {
		if ((dbName == null) || (dbName.equals(""))) {
			dbName = DeployFactory.get(DBDeploy.class).getDefaultDataSource();
		}
		/** 获取当前线程中的数据 **/
		HashMap<String, Transaction> transMap = (HashMap<String, Transaction>) transLocalMap.get();
		if (null == transMap) {
			transMap = new HashMap<String, Transaction>();
		}
		/** 获取Transaction对象 **/
		Transaction transaction = (Transaction) transMap.get(dbName);
		DataSourceTransactionManager ptm = DBSessionUtil.getCurrentTM(dbName);

		if (transaction == null) {
			transaction = new Transaction(ptm);
			transMap.put(dbName, transaction);
		} else if (transaction.isRollbackOnly() == true) {
			transaction.rollback();
			transaction = new Transaction(ptm);
			transMap.put(dbName, transaction);
		}
		transLocalMap.set(transMap);
		return transaction;
	}

}
