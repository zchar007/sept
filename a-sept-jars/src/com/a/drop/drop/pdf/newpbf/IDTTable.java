package com.sept.drop.pdf.newpbf;

import java.util.ArrayList;

import com.sept.exception.AppException;

/**
 * 关于列操作的，不涉及数据操作
 * 
 * @author zhangchao_lc
 *
 */
public interface IDTTable {
	/**
	 * .设置列属性，替换
	 * 
	 * @param dtColumns
	 * @throws AppException
	 */
	void setColumns(IDTColumns dtColumns) throws AppException;

	/**
	 * .设置数据的head和name关系
	 * 
	 * @param keyValue
	 * @throws AppException
	 */
	void setKeyValue(String keyValue) throws AppException;

	/**
	 * .获取行首展示名称(showName)
	 * 
	 * @return
	 * @throws AppException
	 */
	ArrayList<String> getHead() throws AppException;
}
