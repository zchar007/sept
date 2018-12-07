package com.sept.drop.pdf.access;

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
import com.sept.drop.pdf.DataTableColumn;
import com.sept.drop.pdf.DataTableColumns;
import com.sept.drop.pdf.IDataTable;
import com.sept.drop.pdf.IDataTableColumn;
import com.sept.drop.pdf.IDataTableColumns;
import com.sept.drop.pdf.IHandleTable;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;

class AccessTable implements IDataTable, IHandleTable {
	private Table table;
	private Database accdb;
	private IDataTableColumns dtColumns;
	private DataStore datas;

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
		// 保证datas和表里存的一样
		if (null == row) {
			return new DataObject();
		}
		DataObject rowT = new DataObject();
		for (int i = 0; i < this.dtColumns.columnCount(); i++) {
			IDataTableColumn idtc = this.dtColumns.getColumn(i);
			rowT.put(idtc.getShowName(),
					row.containsKey(idtc.getName()) ? row.getString(idtc.getName())
							: (row.containsKey(idtc.getShowName()) ? row.getString(idtc.getShowName())
									: idtc.getDefaultValue()));
		}

		return rowT;
	}

	@Override
	public ArrayList<String> getHead() throws AppException {
		return this.dtColumns.getHead();
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
	public boolean isInDataBase() {
		return null != this.accdb;
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

	@Override
	public DataStore getDataStore() throws AppException {
		// 修正
		DataStore dsTemp = new DataStore();
		for (int i = 0; i < this.datas.rowCount(); i++) {
			dsTemp.addRow();
		}
		for (int i = 0; i < this.dtColumns.columnCount(); i++) {
			IDataTableColumn idtc = this.dtColumns.getColumn(i);
			for (int j = 0; j < this.datas.rowCount(); j++) {
				dsTemp.put(j, idtc.getName(),
						this.datas.get(j).containsKey(idtc.getShowName()) ? this.datas.getString(j, idtc.getShowName())
								: (this.datas.get(j).containsKey(idtc.getName())
										? this.datas.getString(j, idtc.getName())
										: ""));
			}
		}

		return dsTemp;
	}

	@Override
	public void ready() throws AppException {
		if (!this.isInDataBase()) {
			return;
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
		} catch (IOException e) {
			LogHandler.error(e.getMessage());
		}
	}

}
