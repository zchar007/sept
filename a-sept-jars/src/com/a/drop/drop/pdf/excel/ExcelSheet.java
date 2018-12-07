package com.sept.drop.pdf.excel;

import java.util.ArrayList;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.datastructure.comparator.Datas;
import com.sept.drop.pdf.DataTableColumns;
import com.sept.drop.pdf.IDataTable;
import com.sept.drop.pdf.IDataTableColumn;
import com.sept.drop.pdf.IDataTableColumns;
import com.sept.drop.pdf.IHandleTable;
import com.sept.exception.AppException;

import jxl.Cell;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

class ExcelSheet implements IDataTable, IHandleTable {
	private WritableSheet sheet;
	private boolean writeAble = true;
	private IDataTableColumns dtColumns;
	private DataStore datas;

	protected ExcelSheet(WritableSheet sheet, IDataTableColumns dtColumns, boolean writeAble) throws AppException {
		this.sheet = sheet;
		this.dtColumns = dtColumns;
		this.writeAble = writeAble;
		this.datas = new DataStore();
		this.setDefault();
	}

	@Override
	public void addColumn(IDataTableColumn idtColumn) throws AppException {
		if (!this.writeAble) {
			throw new AppException("当前excel不可编辑！");
		}
		if (null != this.datas && this.datas.rowCount() > 0) {
			throw new AppException("当前sheet中已存在数据，无法操作其列属性");
		}
		this.dtColumns.addColumn(idtColumn);
	}

	@Override
	public void setColumn(int index, IDataTableColumn dtColumn) throws AppException {
		if (!this.writeAble) {
			throw new AppException("当前excel不可编辑！");
		}
		if (null != this.datas && this.datas.rowCount() > 0) {
			throw new AppException("当前sheet中已存在数据，无法操作其列属性");
		}
		this.dtColumns.setColumn(index, dtColumn);
	}

	@Override
	public void setColumns(IDataTableColumns dtColumns) throws AppException {
		if (!this.writeAble) {
			throw new AppException("当前excel不可编辑！");
		}
		if (null != this.datas && this.datas.rowCount() > 0) {
			throw new AppException("当前sheet中已存在数据，无法操作其列属性");
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

//		DataObject rowT = new DataObject();
//		for (int i = 0; i < this.dtColumns.columnCount(); i++) {
//			IDataTableColumn idtc = this.dtColumns.getColumn(i);
//			rowT.put(idtc.getShowName(),
//					row.containsKey(idtc.getName()) ? row.getString(idtc.getName())
//							: (row.containsKey(idtc.getShowName()) ? row.getString(idtc.getShowName())
//									: idtc.getDefaultValue()));
//		}

		return row;
	}

	@Override
	public ArrayList<String> getHead() throws AppException {
		return this.dtColumns.getHead();
	}

	@Override
	public DataStore getDataStore() throws AppException {
		// 修正
//		DataStore dsTemp = new DataStore();
//		for (int i = 0; i < this.datas.rowCount(); i++) {
//			dsTemp.addRow();
//		}
//		for (int i = 0; i < this.dtColumns.columnCount(); i++) {
//			IDataTableColumn idtc = this.dtColumns.getColumn(i);
//			for (int j = 0; j < this.datas.rowCount(); j++) {
//				String value = idtc.getDefaultValue();
//				if (this.datas.get(j).containsKey(idtc.getShowName())) {
//					value = this.datas.getString(j, idtc.getShowName());
//				} else if (this.datas.get(j).containsKey(idtc.getName())) {
//					value = this.datas.getString(j, idtc.getName());
//				}
//				dsTemp.put(j, idtc.getName(), value);
//			}
//		}

		return this.datas;
	}

	@Override
	public void ready() throws AppException {
		for (int i = 0; i < this.datas.rowCount(); i++) {
			try {
				for (int j = 0; j < this.dtColumns.columnCount(); j++) {
					IDataTableColumn idtc = this.dtColumns.getColumn(j);
					String key = idtc.getName();
					String defaultValue = idtc.getDefaultValue();
					// System.out.print(key+",");
					Label label = new Label(j, i + 1, datas.get(i).getString(key, defaultValue));
					sheet.addCell(label);
				}
				// System.out.println();
			} catch (WriteException e) {
				throw new AppException(e);
			}
		}
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
		return true;
	}

	/**
	 * 用于初始化的时候使用
	 * 
	 * @throws AppException
	 */
	private void setDefault() throws AppException {
		int columnCount = sheet.getColumns();
		this.datas = new DataStore();
		for (int i = 0; i < columnCount; i++) {
			Cell[] cells = sheet.getColumn(i);
			String showName = null;
			for (int j = 0; j < cells.length; j++) {
				if (j == 0) {
					showName = cells[j].getContents();
//					IDataTableColumn idtc = new DataTableColumn(name, name);
//					this.dtColumns.addColumn(idtc);
				} else {
					if (j - 1 >= this.datas.rowCount()) {
						this.datas.addRow();
					}
					try {

						this.datas.put(j - 1, this.dtColumns.getName(showName), cells[j].getContents());
					} catch (Exception e) {
						throw new AppException(e);
					}
				}
			}
		}

	}
}
