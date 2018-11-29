package com.sept.jui.grid;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.jui.grid.columns.ButtonColumn;
import com.sept.jui.grid.columns.ButtonsColumn;
import com.sept.jui.grid.columns.GridLineNumberColumn;
import com.sept.jui.grid.columns.GridSelectionColumn;
import com.sept.jui.grid.model.GridColumn;

public class GridModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private ArrayList<GridColumn> gridColumns;
	private boolean editAble = true;

	@SuppressWarnings("unchecked")
	public GridModel(ArrayList<GridColumn> gridColumns, DataStore ds) throws AppException {
		super(getDatas(ds, gridColumns), dealColumns(gridColumns));
		try {
			// this.gridColumns = CloneUtil.deepClone(gridColumns);
			this.gridColumns = (ArrayList<GridColumn>) gridColumns.clone();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		}
	}

	public GridModel(ArrayList<GridColumn> gridColumns) throws AppException {
		this(gridColumns, new DataStore());
	}

	public GridModel(ArrayList<GridColumn> gridColumns, DataStore ds, boolean editAble) throws AppException {
		this(gridColumns, ds);
		this.setEditAble(editAble);
	}

	public GridModel(ArrayList<GridColumn> gridColumns, boolean editAble) throws AppException {
		this(gridColumns, new DataStore());
		this.setEditAble(editAble);
	}

	/**
	 * 获取数据（内部调用）
	 * 
	 * @return
	 * @throws AppException
	 * @throws DataException
	 */
	private static final Object[][] getDatas(DataStore dsData_temp, ArrayList<GridColumn> gridColumns_temp)
			throws AppException {
		Object[][] objs = new Object[dsData_temp.rowCount()][gridColumns_temp.size()];
		for (int i = 0; i < dsData_temp.rowCount(); i++) {
			for (int j = 0; j < gridColumns_temp.size(); j++) {
				GridColumn gridClumn = (GridColumn) gridColumns_temp.get(j);
				String realName = gridClumn.getName();
				Object value = null;
				if (realName.equals(GridLineNumberColumn.DEFAULT_NAME)) {
					//System.out.println(i);
					objs[i][j] = gridClumn.dealValue(i + 1);
					continue;
				}

				if (dsData_temp.containsItem(i, realName)) {
					value = dsData_temp.getObject(i, realName);
				}
				if (value == null || "".equals(value)) {
					value = gridClumn.getDefault();
				}
				objs[i][j] = gridClumn.dealValue(value);
			}
		}
		return objs;
	}

	/**
	 * 获取头部
	 * 
	 * @param gridColumns_temp
	 * @return
	 */
	private static final Object[] dealColumns(ArrayList<GridColumn> gridColumns_temp) {
		Object[] objs = new Object[gridColumns_temp.size()];
		for (int i = 0; i < gridColumns_temp.size(); i++) {
			objs[i] = gridColumns_temp.get(i).getHead();
		}
		return objs;
	}

	/**
	 * 获取数据（内部调用）
	 * 
	 * @return
	 * @throws AppException
	 * @throws DataException
	 */
	private Object[] getRowDataFromeDataObject(DataObject para) throws AppException {
		Object[] obj = new Object[this.gridColumns.size()];
		for (int i = 0; i < this.gridColumns.size(); i++) {
			GridColumn gridClumn = (GridColumn) this.gridColumns.get(i);
			String realName = gridClumn.getName();
			Object value = null;
			if (realName.equals(GridLineNumberColumn.DEFAULT_NAME)) {
				obj[i] = gridClumn.dealValue(this.getRowCount() + 1);
				continue;
			}
			if (para.containsKey(realName)) {
				value = para.getObject(realName);
			}
			if (value == null || "".equals(value)) {
				value = gridClumn.getDefault();
			}
			obj[i] = gridClumn.dealValue(value);
		}
		return obj;
	}

	public boolean isEditAble() {
		return editAble;
	}

	public void setEditAble(boolean editAble) {
		this.editAble = editAble;
	}

	public void addRow(DataObject para) throws AppException {
		super.addRow(getRowDataFromeDataObject(para));
	}

	/**
	 * 增加多行
	 * 
	 * @param para
	 * @throws AppException
	 * @throws DataException
	 */
	public void addRows(DataStore para) throws AppException {
		if (para == null) {
			return;
		}
		for (DataObject dataObject : para) {
			if (dataObject != null) {
				super.addRow(getRowDataFromeDataObject(dataObject));
			}
		}
	}

	public void insertRow(int row, DataObject para) throws AppException {
		super.insertRow(row, getRowDataFromeDataObject(para));

	}

	/**
	 * 增加多行
	 * 
	 * @param para
	 * @throws AppException
	 * @throws DataException
	 */
	public void insertRows(int row, DataStore para) throws AppException {
		if (para == null) {
			return;
		}
		int k = 0;
		for (DataObject dataObject : para) {
			if (dataObject != null) {
				super.insertRow(row + (k++), getRowDataFromeDataObject(dataObject));
			}
		}
	}

	/**
	 * 仅更新数据
	 * 
	 * @throws AppException
	 * @throws DataException
	 */
	public DataStore getDataStore() throws AppException {
		DataStore values = new DataStore();
		int colCount = this.gridColumns.size();
		int rowCount = this.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			values.addRow();
			for (int j = 0; j < colCount; j++) {
				if (this.gridColumns.get(j) instanceof GridLineNumberColumn
						|| this.gridColumns.get(j) instanceof GridSelectionColumn
						|| this.gridColumns.get(j) instanceof ButtonsColumn
						|| this.gridColumns.get(j) instanceof ButtonColumn) {
					continue;
				}
				//System.out.println(((GridColumn) this.gridColumns.get(j)).getName() + ":" + this.getValueAt(i, j));
				values.put(values.rowCount() - 1, ((GridColumn) this.gridColumns.get(j)).getName(),
						this.gridColumns.get(j).dealValue4Get(this.getValueAt(i, j)));
			}
		}
		return values;
	}

	public DataObject getRowData(int rowIndex) throws AppException {
		DataObject values = new DataObject();
		int colCount = this.gridColumns.size();
		int rowCount = this.getRowCount();
		if (rowCount <= rowIndex) {
			throw new AppException("[" + rowIndex + "]超出grid总行数[" + rowCount + "]!");
		}
		for (int j = 0; j < colCount; j++) {
			if (this.gridColumns.get(j) instanceof GridLineNumberColumn
					|| this.gridColumns.get(j) instanceof GridSelectionColumn
					|| this.gridColumns.get(j) instanceof ButtonsColumn
					|| this.gridColumns.get(j) instanceof ButtonColumn) {
				continue;
			}
			//System.out.println(((GridColumn) this.gridColumns.get(j)).getName() + ":" + this.getValueAt(rowIndex, j));

			values.put(((GridColumn) this.gridColumns.get(j)).getName(),
					this.gridColumns.get(j).dealValue4Get(this.getValueAt(rowIndex, j)));
		}
		return values;
	}

	public void updateData(DataStore dataStore) throws AppException {
		this.setRowCount(0);
		this.addRows(dataStore);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (editAble) {
			boolean readonly = this.gridColumns.get(column).readonly();
			return !readonly;
		}
		return false;
	}
}
