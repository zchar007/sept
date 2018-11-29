package com.sept.drop.newgrid;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.drop.newgrid.columns.rander.GridCellEditor;
import com.sept.drop.newgrid.columns.rander.GridColumn;
import com.sept.exception.AppException;

public class Grid extends JTable {
	private static final long serialVersionUID = 1L;
	private ArrayList<GridColumn> gridColumns;
	private DefaultGridModel model;

	public Grid() {
		this.setRowHeight(30);
		gridColumns = new ArrayList<>();
	}

	public void addColumn(GridColumn column) throws AppException {
		this.gridColumns.add(column);
		this.setModel();
	}

	/**
	 * 增加多列
	 * 
	 * @param gridClumns
	 * @throws AppException
	 */
	public void addColumns(ArrayList<GridColumn> gridClumns) throws AppException {
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

	/**
	 * 设置模型
	 * 
	 * @throws AppException
	 */
	public void setModel() throws AppException {
		model = new DefaultGridModel(this.gridColumns);
		this.setModel(model);
		TableColumnModel columnModel = this.getColumnModel();
		for (int i = 0; i < gridColumns.size(); i++) {
			columnModel.getColumn(i).setCellRenderer((TableCellRenderer) gridColumns.get(i));

			columnModel.getColumn(i).setCellEditor(new GridCellEditor(gridColumns.get(i)));
		}
	}
}
