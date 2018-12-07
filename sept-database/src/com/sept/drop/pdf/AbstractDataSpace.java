package com.sept.drop.pdf;

import java.util.LinkedHashMap;

import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public abstract class AbstractDataSpace {
	protected LinkedHashMap<String, IDataTable> hmTables;
	protected String url;

	public abstract IDataTable newTable(String tableName, IDataTableColumns ditColumns) throws AppException;

	public abstract void save() throws AppException;

	public IDataTable getTable(String tableName, IDataTableColumns ditColumns) throws AppException {
		return this.hmTables.get(tableName);
	}

	public DataStore getDataStore(String tableName) throws AppException {
		return this.hmTables.get(tableName).getDataStore();
	}

	public String getUrl() {
		return url;
	}

	protected void setHmTables(LinkedHashMap<String, IDataTable> hmTables) {
		this.hmTables = hmTables;
	}

	protected void setUrl(String url) {
		this.url = url;
	}

	public abstract byte[] getBytes() throws AppException;

}
