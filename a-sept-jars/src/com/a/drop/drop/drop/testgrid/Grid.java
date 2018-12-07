package com.sept.drop.testgrid;

import java.util.ArrayList;

import javax.swing.JTable;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public class Grid extends JTable {
	private static final long serialVersionUID = 1L;
	private ArrayList<GridColumn> gridColumns;
	private DefaultGridModel model;

	public Grid() {
		this.setRowHeight(20);
		gridColumns = new ArrayList<>();
	}

	public void addColumn(GridColumn column) throws Exception {
		this.gridColumns.add(column);
		this.setModel();
	}

	/**
	 * 增加多列
	 * 
	 * @param gridClumns
	 * @throws Exception 
	 */
	public void addColumns(ArrayList<GridColumn> gridClumns) throws Exception {
		for (int i = 0; i < gridClumns.size(); i++) {
			if (gridClumns.get(i) == null) {
				continue;
			}
			this.gridColumns.add(gridClumns.get(i));
		}
		this.setModel();
	}

	/**
	 * 增加一行
	 * 
	 * @param para
	 * @throws AppException
	 * @throws DataException
	 */
	public void addRow(DataObject para) throws AppException {
		if (para == null) {
			return;
		}
		if (this.model == null) {
			return;
		}
		this.model.addRow(para);
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
		if (this.model == null) {
			return;
		}
		this.model.addRows(para);
	}

	public void setModel() throws Exception {
		model = new DefaultGridModel(this.gridColumns);
		this.setModel(model);
	}
}
