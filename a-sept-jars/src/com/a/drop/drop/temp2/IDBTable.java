package com.sept.drop.temp2;

import java.util.ArrayList;

import com.sept.exception.AppException;

public interface IDBTable {
	/** 增加列 **/
	void addColumn(DBColumn idbColumn) throws AppException;

	/** 设置某列属性，替换相应列 **/
	void setColumn(int index, DBColumn idbColumn) throws AppException;

	/** 设置某列的显示名 **/
	void setShowName(int index, String name) throws AppException;

	/** 获取行首展示名称（value） **/
	ArrayList<String> getHead() throws AppException;

	String getSql() throws AppException;
}
