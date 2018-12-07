package com.sept.drop.pdf.newpbf;

import java.util.ArrayList;

import com.sept.exception.AppException;

public interface IDTColumns {
	/**
	 * .获取指定列定义
	 * 
	 * @param index
	 * @return
	 * @throws AppException
	 */
	DTColumn getColumn(int index) throws AppException;

	/**
	 * .获取指定名的列定义
	 * 
	 * @param name
	 * @return
	 * @throws AppException
	 */
	DTColumn getColumnByKey(String key) throws AppException;

	/**
	 * .获取指定列头的列定义
	 * 
	 * @param head
	 * @return
	 * @throws AppException
	 */
	DTColumn getColumnByHead(String head) throws AppException;

	/**
	 * .增加列定义
	 * 
	 * @param dtColumn
	 * @throws AppException
	 */
	void addColumn(DTColumn dtColumn) throws AppException;

	/**
	 * .插入列定义
	 * 
	 * @param index
	 * @param dtColumn
	 * @throws AppException
	 */
	void insertColumn(int index, DTColumn dtColumn) throws AppException;

	/**
	 * .替换列定义
	 * 
	 * @param index
	 * @param dtColumn
	 * @throws AppException
	 */
	void setColumn(int index, DTColumn dtColumn) throws AppException;

	/**
	 * .删除列定义
	 * 
	 * @param index
	 * @throws AppException
	 */
	void deleteColumn(int index) throws AppException;

	/**
	 * .删除列定义
	 * 
	 * @param dtColumn
	 * @throws AppException
	 */
	void deleteColumn(DTColumn dtColumn) throws AppException;

	/**
	 * .获取列头array
	 * 
	 * @return
	 * @throws AppException
	 */
	ArrayList<String> getHead() throws AppException;

	/**
	 * .获取列总数
	 * 
	 * @return
	 * @throws AppException
	 */
	int columnCount() throws AppException;

	/**
	 * .检查列
	 * 
	 * @param dtColumn
	 * @return
	 * @throws AppException
	 */
	boolean checkColumn(DTColumn dtColumn) throws AppException;

}
