package com.sept.datastructure.tree;

import java.util.LinkedList;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public interface TreeInterface<T> {
	/**
	 * 添加子代
	 * 
	 * @param node
	 * @throws AppException
	 */
	public void addChild(T node) throws AppException;
	/**
	 * 删除子代
	 * 
	 * @param node
	 * @throws AppException
	 */
	public void removeChild(T node) throws AppException;
	/**
	 * 获取子代
	 * 
	 * @return
	 * @throws AppException
	 */
	LinkedList<T> getChildren() throws AppException;

	/**
	 * 获取所有后代
	 * 
	 * @return
	 * @throws AppException
	 */
	LinkedList<T> getPosterity() throws AppException;

	/**
	 * 获取节点的DataObject表达形式
	 * 
	 * @return
	 * @throws AppException
	 */
	public DataObject getDataObject() throws AppException;

	/**
	 * 获取节点的DataStore表达形式
	 * 
	 * @return
	 * @throws AppException
	 */
	public DataStore getDataStore() throws AppException;
}
