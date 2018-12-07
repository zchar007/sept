package com.sept.drop.pdf;

import java.util.ArrayList;

import com.sept.exception.AppException;

public interface IDataTableColumns {

	void addColumn(IDataTableColumn idtColumn) throws AppException;

	void insertColumn(int index, IDataTableColumn idtColumn) throws AppException;

	void setColumn(int index, IDataTableColumn idtColumn) throws AppException;

	void deleteColumn(int index) throws AppException;

	void deleteColumn(IDataTableColumn idtColumn) throws AppException;

	IDataTableColumn getColumn(int index) throws AppException;

	IDataTableColumn getColumn(String name) throws AppException;

	IDataTableColumn getColumnByShowName(String name) throws AppException;

	boolean checkColumn(IDataTableColumn idtColumn) throws AppException;

	ArrayList<String> getHead() throws AppException;

	int columnCount() throws AppException;

	String getName(String showName);

	String getShowName(String showName);

}
