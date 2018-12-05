package com.sept.db.dba;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sept.db.ITransaction;
import com.sept.exception.AppException;

public class Transaction implements ITransaction {
	private TransactionStatus status = null;
	private PlatformTransactionManager ptm;
	private boolean transactionStarted = false;

	public Transaction(DataSourceTransactionManager ptm) {
		this.ptm = ptm;
	}

	@Override
	public boolean isUnderTransaction() {
		return (this.status != null) && (!this.status.isCompleted());
	}

	@Override
	public boolean isRollbackOnly() {
		if (this.status == null) {
			return false;
		}
		return this.status.isRollbackOnly();
	}

	@Override
	public int begin() {
		return begin(0);
	}

	@Override
	public int begin(int propagation) {
		if (this.transactionStarted) {
			return 0;
		}
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setIsolationLevel(2);
		def.setPropagationBehavior(propagation);
		this.status = this.ptm.getTransaction(def);
		this.transactionStarted = true;
		return 1;
	}

	@Override
	public void commit() throws AppException {
		commitWithoutStart();
		begin();
	}

	@Override
	public void commitWithoutStart() throws AppException {
		if (null == this.status) {
			throw new AppException("当前commit操作，未正常开启事物，请联系开发人员处理！");
		}
		if (this.status.isCompleted()) {
			return;
		}
		if (this.status.isRollbackOnly()) {
			this.ptm.rollback(this.status);
			this.transactionStarted = false;
			throw new AppException("当前事物已被锁定为rollback，请联系开发人员处理！");
		}
		this.ptm.commit(this.status);
		this.transactionStarted = false;
	}

	@Override
	public void rollback() throws AppException {
		rollbackWithoutStart();
		begin();
	}

	@Override
	public void rollbackWithoutStart() throws AppException {
		if (null == this.status) {
			throw new AppException("当前rollback操作，未正常开启事物，请联系开发人员处理！");
		}
		if (this.status.isCompleted()) {
			return;
		}
		this.ptm.rollback(this.status);
		this.transactionStarted = false;
	}

	public PlatformTransactionManager getPtm() {
		return ptm;
	}
}
