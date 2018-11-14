package com.sept.datastructure.tree;

import java.util.LinkedList;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public interface TreeInterface<T> {
	/**
	 * ����Ӵ�
	 * 
	 * @param node
	 * @throws AppException
	 */
	public void addChild(T node) throws AppException;
	/**
	 * ɾ���Ӵ�
	 * 
	 * @param node
	 * @throws AppException
	 */
	public void removeChild(T node) throws AppException;
	/**
	 * ��ȡ�Ӵ�
	 * 
	 * @return
	 * @throws AppException
	 */
	LinkedList<T> getChildren() throws AppException;

	/**
	 * ��ȡ���к��
	 * 
	 * @return
	 * @throws AppException
	 */
	LinkedList<T> getPosterity() throws AppException;

	/**
	 * ��ȡ�ڵ��DataObject�����ʽ
	 * 
	 * @return
	 * @throws AppException
	 */
	public DataObject getDataObject() throws AppException;

	/**
	 * ��ȡ�ڵ��DataStore�����ʽ
	 * 
	 * @return
	 * @throws AppException
	 */
	public DataStore getDataStore() throws AppException;
}
