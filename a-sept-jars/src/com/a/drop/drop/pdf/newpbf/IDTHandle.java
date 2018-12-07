package com.sept.drop.pdf.newpbf;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

/**
 * table数据操作相关
 * 
 * @author zhangchao_lc
 *
 */
public interface IDTHandle {
	/**
	 * 追加一行
	 * 
	 * @param row
	 * @throws AppException
	 */
	void add(DataObject row) throws AppException;

	/**
	 * .追加多行
	 * 
	 * @param rows
	 * @throws AppException
	 */
	void add(DataStore rows) throws AppException;

	/**
	 * .插入一行，插在index上，原index之后的数据依次后移
	 * 
	 * @param index >= 0
	 * @param row
	 * @throws AppException
	 */
	void insert(int index, DataObject row) throws AppException;

	/**
	 * .插入多行,从index 开始插入,原 index 之后的数据依次后移
	 * 
	 * @param index >= 0
	 * @param rows
	 * @throws AppException
	 */
	void insert(int index, DataStore rows) throws AppException;

	/**
	 * .删除指定行
	 * 
	 * @param index >= 0
	 * @return
	 * @throws AppException
	 */
	int delete(int index) throws AppException;

	/**
	 * .删除符合条件的行
	 * 
	 * @param condition DataStore过滤的语句 not null
	 * @return
	 * @throws AppException
	 */
	int delete(String condition) throws AppException;

	/**
	 * .更新指定行数据
	 * 
	 * @param index >= 0
	 * @param row
	 * @return
	 * @throws AppException
	 */
	int update(int index, DataObject row) throws AppException;

	/**
	 * .更新符合条件的行数据
	 * 
	 * @param conditionDataStore过滤的语句 not null
	 * @param row
	 * @return
	 * @throws AppException
	 */
	int update(String condition, DataObject row) throws AppException;

	/**
	 * .获取所有
	 * 
	 * @return
	 * @throws AppException
	 */
	DataStore select() throws AppException;

	/**
	 * .查找
	 * 
	 * @param index >=0
	 * @return
	 * @throws AppException
	 */
	DataStore select(int index) throws AppException;

	/**
	 * .查找
	 * 
	 * @param condition DataStore过滤的语句 not null
	 * @return
	 * @throws AppException
	 */
	DataStore select(String condition) throws AppException;

	/**
	 * .以列类型检查行
	 * 
	 * @param row
	 * @return
	 * @throws AppException
	 */
	DataObject check(DataObject row) throws AppException;
}
