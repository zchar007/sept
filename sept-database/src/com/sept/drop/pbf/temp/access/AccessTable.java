package com.sept.drop.pbf.temp.access;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.datastructure.comparator.Datas;
import com.sept.drop.pbf.temp.DataTableColumn;
import com.sept.drop.pbf.temp.DataTableColumns;
import com.sept.drop.pbf.temp.IDataTable;
import com.sept.drop.pbf.temp.IDataTableColumn;
import com.sept.drop.pbf.temp.IDataTableColumns;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.io.local.FileIOUtil;

class AccessTable implements IDataTable, IAccessTable {
	private Table table;
	private Database accdb;

	private IDataTableColumns dtColumns;

	private DataStore datas;;

	public AccessTable(Table table, Database accdb) throws AppException {
		this.table = table;
		this.accdb = accdb;
		dtColumns = new DataTableColumns();
		datas = new DataStore();
		this.setDefault();
	}

	public AccessTable(String name) {
		dtColumns = new DataTableColumns();
		datas = new DataStore();
	}

	@Override
	public void addColumn(IDataTableColumn idtColumn) throws AppException {
		if (this.isInDataBase()) {
			throw new AppException("当前表已在表空间中，无法修改表结构，请检查！");
		}
		this.dtColumns.addColumn(idtColumn);
	}

	@Override
	public void setColumn(int index, IDataTableColumn dtColumn) throws AppException {
		if (this.isInDataBase()) {
			throw new AppException("当前表已在表空间中，无法修改表结构，请检查！");
		}
		this.dtColumns.setColumn(index, dtColumn);
	}

	@Override
	public void setColumns(IDataTableColumns dtColumns) throws AppException {
		if (this.isInDataBase()) {
			throw new AppException("当前表已在表空间中，无法修改表结构，请检查！");
		}
		this.dtColumns = dtColumns;
	}

	@Override
	public void addRow(DataObject row) throws AppException {
		row = this.checkRow(row);
		this.datas.addRow(row);
	}

	@Override

	public void addRows(DataStore rows) throws AppException {
		for (int i = 0; i < rows.rowCount(); i++) {
			DataObject row = this.checkRow(rows.get(i));
			this.datas.addRow(row);
		}
	}

	@Override

	public void insertRow(int index, DataObject row) throws AppException {
		row = this.checkRow(row);
		this.datas.insertRow(index, row);

	}

	@Override
	public void insertRows(int index, DataStore rows) throws AppException {
		for (int i = 0; i < rows.rowCount(); i++) {
			DataObject row = this.checkRow(rows.get(i));
			this.datas.insertRow(index + i, row);
		}
	}

	@Override

	public DataObject checkRow(DataObject row) throws AppException {
		if (null == row) {
			return new DataObject();
		}

		return row;
	}

	@Override
	public ArrayList<String> getHead() throws AppException {
		return this.dtColumns.getHead();
	}

	@Override
	public byte[] getBytes() throws AppException {
		if (this.isInDataBase()) {
			File file = accdb.getFile();
			byte[] accbytes;
			try {
				accbytes = FileIOUtil.getBytesFromFile(file);
				return accbytes;
			} catch (IOException e) {
				throw new AppException(e);
			}
		}
		throw new AppException("当前表未在表空间中，无法获取byte数组,请检查！");
	}

	@Override
	public DataStore getData() {
		return this.datas;
	}

	@Override
	public DataStore select(String filterStr) throws AppException {
		return Datas.filter(this.datas, filterStr, false);
	}

	@Override
	public int delete(String filterStr) throws AppException {
		int[] all = this.datas.findAll(filterStr);
		for (int i = all.length - 1; i >= 0; i--) {
			this.datas.remove(all[i]);
		}
		return all.length;
	}

	@Override
	public int updateRow(String filterStr, DataObject row) throws AppException {
		int[] all = this.datas.findAll(filterStr);
		for (int i = all.length - 1; i >= 0; i--) {
			for (int j = 0; j < dtColumns.columnCount(); j++) {
				IDataTableColumn idtc = dtColumns.getColumn(j);
				if (row.containsKey(idtc.getName())) {
					this.datas.get(all[i]).put(idtc.getName(), row.get(idtc.getName()));
				}
			}
		}
		return all.length;
	}

	@Override
	public boolean save() throws AppException {
		if (!this.isInDataBase()) {
			throw new AppException("当前表未在表空间中，无法直接保存，请检查！");
		}
		try {
			ArrayList<Row> al = new ArrayList<>();
			for (Row row : this.table) {
				al.add(row);
			}
			for (int i = 0; i < al.size(); i++) {
				this.table.deleteRow(al.get(i));
			}

			this.table.addRowsFromMaps(this.datas);
			return true;
		} catch (IOException e) {
			LogHandler.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean isInDataBase() {
		return null == this.accdb;
	}

	private void setDefault() throws AppException {
		try {
			datas = new DataStore();
			List<? extends Column> columns = table.getColumns();
			this.dtColumns = new DataTableColumns();
			boolean isGetColumn = true;
			for (Row row : table) {
				datas.addRow();
				for (int i = 0; i < columns.size(); i++) {
					String key = columns.get(i).getName();
					datas.put(datas.rowCount() - 1, key, row.get(key));
					if (isGetColumn) {
						IDataTableColumn idtColumn = new DataTableColumn();
						idtColumn.setName(key);
						idtColumn.setShowName(key);
						this.dtColumns.addColumn(idtColumn);
					}
				}
				isGetColumn = false;
			}
		} catch (Exception e) {
			if (!(e instanceof AppException)) {
				LogHandler.fatal(e.getMessage(), e);
			} else {
				LogHandler.error(e.getMessage(), e);
				throw (AppException) e;
			}
			throw new AppException(e);
		}

	}

}
