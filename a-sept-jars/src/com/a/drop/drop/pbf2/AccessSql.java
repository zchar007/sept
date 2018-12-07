package com.sept.drop.pbf2;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

import com.sept.datastructure.DataStore;
import com.sept.drop.ISql;
import com.sept.exception.AppException;

public class AccessSql implements ISql {

	public AccessSql(String dbUrl) {
		
	}

	@Override
	public void setSql(String sql) throws AppException {

	}

	@Override
	public void setSql(StringBuilder sql) throws AppException {

	}

	@Override
	public void setSql(StringBuffer sql) throws AppException {

	}

	@Override
	public void setPara(int index, Object para) throws AppException {

	}

	@Override
	public void setInt(int index, int para) throws AppException {

	}

	@Override
	public void setDouble(int index, double para) throws AppException {

	}

	@Override
	public void setFloat(int index, float para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBoolean(int index, boolean para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setString(int index, String para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDate(int index, Date para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDateTime(int index, Date para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDate(int index, java.sql.Date para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDateTime(int index, java.sql.Date para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTimestamp(int index, Timestamp para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBlob(int index, Blob para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBlob(int index, byte[] para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBlob(int index, String para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClob(int index, Clob para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClob(int index, byte[] para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClob(int index, String para) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addBatch() throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetBatch() throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public int executeBatch() throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate() throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DataStore executeQuery() throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSqlString() throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSql() throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

}
