package com.sept.db.pdf.access;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public interface IAccessTable {
	DataStore select(String filterStr) throws AppException;

	int delete(String filterStr) throws AppException;

	int updateRow(String filterStr, DataObject row) throws AppException;

	boolean isInDataBase() throws AppException;
}
