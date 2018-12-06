package com.sept.db.pdf;

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

	/** 获取行首展示名称（value） **/
	ArrayList<String> getHead() throws AppException;

	DataStore getDataStore() throws AppException;

	void ready() throws AppException;

}
