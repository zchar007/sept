package com.sept.db;

import com.sept.exception.AppException;

public interface ITransaction {
	boolean isUnderTransaction();

	/**这种模式有何用？**/
	boolean isRollbackOnly();

	int begin();

	int begin(int propagation);

	void commit() throws AppException;

	void commitWithoutStart() throws AppException;

	void rollback() throws AppException;

	void rollbackWithoutStart() throws AppException;

}
