package com.sept.db.pdf.excel;

import java.util.ArrayList;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.db.pdf.DataTableColumn;
import com.sept.db.pdf.DataTableColumns;
import com.sept.db.pdf.IDataTable;
import com.sept.db.pdf.IDataTableColumn;
import com.sept.db.pdf.IDataTableColumns;
import com.sept.exception.AppException;

import jxl.Cell;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

public class ExcelSheet implements IDataTable {
	private WritableSheet sheet;
	private boolean writeAble = true;
	private IDataTableColumns dtColumns;
	private DataStore datas;

	protected ExcelSheet(WritableSheet sheet, boolean writeAble) throws AppException {
		this.sheet = sheet;
		this.writeAble = writeAble;
		this.dtColumns = new DataTableColumns();
		this.datas = new DataStore();
		this.setDefault();
	}

	@Override
	public void addColumn(IDataTableColumn idtColumn) throws AppException {
		if (!this.writeAble) {
			throw new AppException("当前excel不可编辑！");
		}
		this.dtColumns.addColumn(idtColumn);
	}

	@Override
	public void setColumn(int index, IDataTableColumn dtColumn) throws AppException {
		if (!this.writeAble) {
			throw new AppException("当前excel不可编辑！");
		}
		this.dtColumns.setColumn(index, dtColumn);
	}

	@Override
	public void setColumns(IDataTableColumns dtColumns) throws AppException {
		if (!this.writeAble) {
			throw new AppException("当前excel不可编辑！");
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
	public DataStore getDataStore() throws AppException {
		// 修正
		DataStore dsTemp = new DataStore();
		for (int i = 0; i < this.datas.rowCount(); i++) {
			dsTemp.addRow();
		}
		for (int i = 0; i < this.dtColumns.columnCount(); i++) {
			IDataTableColumn idtc = this.dtColumns.getColumn(i);
			for (int j = 0; j < this.datas.rowCount(); j++) {
				String value = idtc.getDefaultValue();
				if (this.datas.get(j).containsKey(idtc.getShowName())) {
					value = this.datas.getString(j, idtc.getShowName());
				} else if (this.datas.get(j).containsKey(idtc.getName())) {
					value = this.datas.getString(j, idtc.getName());
				}
				dsTemp.put(j, idtc.getName(), value);
			}
		}

		return dsTemp;
	}

	@Override
	public void ready() throws AppException {
		for (int i = 0; i < this.datas.rowCount(); i++) {
			try {
				for (int j = 0; j < this.dtColumns.columnCount(); j++) {
					IDataTableColumn idtc = this.dtColumns.getColumn(j);
					String key = idtc.getShowName();
					String defaultValue = idtc.getDefaultValue();
					//System.out.print(key+",");
					Label label = new Label(j, i + 1, datas.get(i).getString(key, defaultValue));
					sheet.addCell(label);
				}
				//System.out.println();
			} catch (WriteException e) {
				throw new AppException(e);
			}
		}
	}

	private void setDefault() throws AppException {
		this.dtColumns = new DataTableColumns();
		int columnCount = sheet.getColumns();
		this.datas = new DataStore();
		for (int i = 0; i < columnCount; i++) {
			Cell[] cells = sheet.getColumn(i);
			String name = null;
			for (int j = 0; j < cells.length; j++) {
				if (j == 0) {
					name = cells[j].getContents();
					IDataTableColumn idtc = new DataTableColumn(name, name);
					this.dtColumns.addColumn(idtc);
				} else {
					if (j - 1 >= this.datas.rowCount()) {
						this.datas.addRow();
					}
					try {
						this.datas.put(j - 1, name, cells[j].getContents());
					} catch (Exception e) {
						throw new AppException(e);
					}
				}
			}
		}

	}

}
