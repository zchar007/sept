package com.sept.drop.pdf.newpbf;

import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

/**
 * 表数据源，反应到excel上就是一个excel,access上就是一个access
 * 
 * @author zhangchao_lc
 *
 * @param <T> 指定表类型
 */
public interface IDTSource<T> {

	/**
	 * .新建表
	 * 
	 * @param tableName  not null
	 * @param ditColumns not null
	 * @return
	 */
	T newTable(String tableName, IDTColumns dtColumns) throws AppException;

	/**
	 * .获取表，默认name和head是一样的
	 * 
	 * @param tableName
	 * @return
	 */
	T getTable(String tableName) throws AppException;

	/**
	 * . 获取表，传入head和name的对应关系
	 * 
	 * @param tableName
	 * @param ditColumns
	 * @return
	 */
	T getTable(String tableName, String keyValue) throws AppException;

	/**
	 * . 获取表，传入列定义，以指定列定义格式化数据
	 * 
	 * @param tableName
	 * @param ditColumns
	 * @return
	 */
	T getTable(String tableName, IDTColumns dtColumns) throws AppException;

	/**
	 * . 获取DataStore数据
	 * 
	 * @param tableName
	 * @return
	 */
	DataStore getDataStore(String tableName) throws AppException;

	/**
	 * .获取DataStore数据
	 * 
	 * @param tableName
	 * @param ditColumns
	 * @return
	 */
	DataStore getDataStore(String tableName, IDTColumns dtColumns) throws AppException;

	/**
	 * .获取byte数据
	 * 
	 * @return
	 */
	byte[] getBytes() throws AppException;

	/**
	 * .持久化数据
	 * 
	 * @return
	 */
	boolean save() throws AppException;
	
	/**
	 * .持久化数据
	 * 
	 * @return
	 */
	boolean save(String tableName) throws AppException;
}
