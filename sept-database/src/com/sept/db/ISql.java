package com.sept.db;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public interface ISql {
	void setSql(String sql) throws AppException;

	void setSql(StringBuilder sql) throws AppException;

	void setSql(StringBuffer sql) throws AppException;

	void setPara(int index, Object para) throws AppException;

	void setInt(int index, int para) throws AppException;

	void setDouble(int index, double para) throws AppException;

	void setFloat(int index, float para) throws AppException;

	void setBoolean(int index, boolean para) throws AppException;

	void setString(int index, String para) throws AppException;

	void setDate(int index, Date para) throws AppException;

	void setDateTime(int index, Date para) throws AppException;

	void setDate(int index, java.sql.Date para) throws AppException;

	void setDateTime(int index, java.sql.Date para) throws AppException;

	void setTimestamp(int index, Timestamp para) throws AppException;

	void setBlob(int index, Blob para) throws AppException;

	void setBlob(int index, byte[] para) throws AppException;

	void setBlob(int index, String para) throws AppException;

	void setClob(int index, Clob para) throws AppException;

	void setClob(int index, byte[] para) throws AppException;

	void setClob(int index, String para) throws AppException;

	void addBatch() throws AppException;

	void resetBatch() throws AppException;

	int executeBatch() throws AppException;

	int executeUpdate() throws AppException;

	DataStore executeQuery() throws AppException;

	String getSqlString() throws AppException;

	String getSql() throws AppException;

}
