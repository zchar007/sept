package com.sept.db;

import com.sept.exception.AppException;

public interface ITransaction {
	boolean isUnderTransaction();

	boolean isRollbackOnly();

	int begin();

	int begin(int propagation);

	void commit() throws AppException;

	void commitWithoutStart() throws AppException;

	void rollback() throws AppException;

	void rollbackWithoutStart() throws AppException;

}
