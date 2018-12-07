package com.sept.drop.pdf;

import java.util.ArrayList;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public interface IDataTable {
	/** 增加列 **/
	void addColumn(IDataTableColumn idtColumn) throws AppException;

	/** 设置某列属性，替换相应列 **/
	void setColumn(int index, IDataTableColumn dtColumn) throws AppException;

	/** 设置列属性，替换 **/
	void setColumns(IDataTableColumns dtColumns) throws AppException;

	/** 获取行首展示名称（value） **/
	ArrayList<String> getHead() throws AppException;

	DataStore getDataStore() throws AppException;

	void ready() throws AppException;

}
