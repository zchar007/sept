package com.sept.drop.newgrid;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.drop.newgrid.columns.rander.GridColumn;
import com.sept.exception.AppException;

public class DefaultGridModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private ArrayList<GridColumn> gridColumns;
	private boolean editAble = true;

	@SuppressWarnings("unchecked")
	public DefaultGridModel(ArrayList<GridColumn> gridColumns, DataStore ds) throws AppException {
		super(getDatas(ds, gridColumns), dealColumns(gridColumns));
		this.gridColumns = (ArrayList<GridColumn>) gridColumns.clone();
	}

	public DefaultGridModel(ArrayList<GridColumn> gridColumns) throws AppException {
		this(gridColumns, new DataStore());
	}

	public DefaultGridModel(ArrayList<GridColumn> gridColumns, DataStore ds, boolean editAble) throws AppException {
		this(gridColumns, ds);
		this.setEditAble(editAble);
	}

	public DefaultGridModel(ArrayList<GridColumn> gridColumns, boolean editAble) throws AppException {
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
				if (dsData_temp.containsItem(i, realName)) {
					value = dsData_temp.getObject(i, realName);
				}
				if (value == null || "".equals(value)) {
					value = gridClumn.getDefault();
				}
				//objs[i][j] = gridClumn.dealValue(value);
				objs[i][j] = value;
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

			if (para.containsKey(realName)) {
				value = para.getObject(realName);
			}
			if (value == null || "".equals(value)) {
				value = gridClumn.getDefault();
			}
			//obj[i] = gridClumn.dealValue(value);
			obj[i] = value;
		}
		return obj;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (editAble) {
			boolean readonly = this.gridColumns.get(column).readonly();
			return !readonly;
		}
		return false;
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

//	@Override
//	public Class<?> getColumnClass(int columnIndex) {
//		return this.gridColumns.get(columnIndex).getValueType();
//	}
}
