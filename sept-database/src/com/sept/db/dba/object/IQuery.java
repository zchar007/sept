package com.sept.db.dba.object;

import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

interface IQuery {

	/**
	 * 查询全表
	 * 
	 * @param tableName
	 * @return
	 * @throws AppException
	 */
	DataStore query(String tableName) throws AppException;

	/**
	 * 依据主键进行查询，无主键不能查询，多主键选第一个
	 * 
	 * @param tableName
	 * @param id
	 * @return
	 * @throws AppException
	 */
	DataStore query(String tableName, String id) throws AppException;

	/**
	 * 模糊查询
	 * 
	 * @param tableName
	 * @param queryColumnNames
	 * @param conditionColumnNames
	 * @param value
	 * @return
	 * @throws AppException
	 */
	DataStore queryFuzzy(String tableName, String queryColumnNames, String conditionColumnNames, String value)
			throws AppException;

	/**
	 * 字符串形式的condition
	 * 
	 * @param tableName
	 * @param queryColumnNames
	 * @param condition
	 * @return
	 * @throws AppException
	 */
	DataStore query(String tableName, String queryColumnNames, String condition) throws AppException;
}
