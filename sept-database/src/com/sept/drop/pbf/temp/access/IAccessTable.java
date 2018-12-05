package com.sept.drop.pbf.temp.access;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public interface IAccessTable {
	DataStore getData() throws AppException;

	DataStore select(String filterStr) throws AppException;

	int delete(String filterStr) throws AppException;

	int updateRow(String filterStr, DataObject row) throws AppException;

	boolean save() throws AppException;

	boolean isInDataBase() throws AppException;
}
