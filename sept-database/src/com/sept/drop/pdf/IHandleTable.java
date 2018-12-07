package com.sept.drop.pdf;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public interface IHandleTable {
	/** 追加一行 **/
	void addRow(DataObject row) throws AppException;

	/** 追加多行 **/
	void addRows(DataStore rows) throws AppException;

	/** 插入一行，插在index上，原index之后的数据依次后移 **/
	void insertRow(int index, DataObject row) throws AppException;

	/** 插入多行，从index开始插入，原index之后的数据依次后移 **/
	void insertRows(int index, DataStore rows) throws AppException;

	/** 以列类型检查行，并去除列类型中不含有的数据 **/
	DataObject checkRow(DataObject row) throws AppException;

	DataStore select(String filterStr) throws AppException;

	int delete(String filterStr) throws AppException;

	int updateRow(String filterStr, DataObject row) throws AppException;

	boolean isInDataBase() throws AppException;
}
