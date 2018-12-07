package com.sept.drop.testgrid;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.util.CloneUtil;

public class DefaultGridModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private ArrayList<GridColumn> gridColumns;
	private boolean editAble = true;

	public DefaultGridModel(ArrayList<GridColumn> gridColumns, DataStore ds) throws Exception {
		super(getData(ds, gridColumns), getHead(gridColumns));
		this.gridColumns = CloneUtil.deepClone(gridColumns);

	}

	public DefaultGridModel(ArrayList<GridColumn> gridColumns) throws Exception {
		this(gridColumns, new DataStore());
	}

	/**
	 * 获取数据（内部调用）
	 * 
	 * @return
	 * @throws AppException
	 * @throws DataException
	 */
	private static final Object[][] getData(DataStore dsData_temp, ArrayList<GridColumn> gridColumns_temp)
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
	private static final Object[] getHead(ArrayList<GridColumn> gridColumns_temp) {
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
			obj[i] = gridClumn.dealValue(value);
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

}
