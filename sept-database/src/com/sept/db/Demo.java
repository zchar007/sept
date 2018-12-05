package com.sept.db;

import com.sept.db.dba.DBSessionUtil;
import com.sept.db.dba.Transaction;
import com.sept.db.dba.TransactionManager;
import com.sept.exception.AppException;
import com.sept.project.deploy.DeployFactory;

public class Demo {
	public static void main(String[] args) throws AppException {
		DeployFactory.init();
		DBSessionUtil.initTransactionManager();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Transaction transaction;
				try {
					transaction = TransactionManager.getTransaction();
					System.out.println("线程1："+transaction.begin()+transaction.getPtm());				
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Transaction transaction;
				try {
					transaction = TransactionManager.getTransaction();
					System.out.println("线程2："+transaction.begin()+transaction.getPtm());				
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		
		
		
		
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT trandate FROM pub_sysctrl_info where subsys='11'");
//		Sql sql = new Sql();
//		sql.setSql(sb);
//		DataStore ds = sql.executeQuery();
//		System.out.println(ds);
//		transaction.rollbackWithoutStart();
	}
}
