package com.sept.support.model.excel.simple;

import java.util.ArrayList;
import java.util.HashMap;

public class Excel {
	private HashMap<String, ExcelColumn> hmClumns;
	private ArrayList<String> alKeys;

	public Excel() {
		hmClumns = new HashMap<String, ExcelColumn>();
		alKeys = new ArrayList<String>();
	}

	public boolean addColumn(int columnIndex, ExcelColumn column) {
		if (columnIndex < 0) {
			columnIndex = 0;
		}
		// 如果存在，则覆盖
		if (!checkColumn(column)) {
			return false;
		}
		hmClumns.put(column.getName(), column);
		alKeys.add(columnIndex, column.getName());
		return false;
	}

	public boolean addColumn(int columnIndex, String name, String showName,
			String defaultValue) {
		if (columnIndex < 0) {
			columnIndex = 0;
		}
		ExcelColumn column = new ExcelColumn(name, showName, defaultValue);

		return this.addColumn(columnIndex, column);
	}

	public boolean removeColumn(String key) {
		if (this.hmClumns.containsKey(key)) {
			this.hmClumns.remove(key);
			return true;
		}
		return false;
	}

	public String[] getColumns() {
		String[] str = new String[this.alKeys.size()];
		return this.alKeys.toArray(str);
	}

	public String getShowName(String name) {
		return this.hmClumns.get(name).getShowName();
	}

	public String getDefault(String name) {
		String dv = this.hmClumns.get(name).getDefaultValue();
		return null == dv ? "" : dv;
	}

	public ExcelColumn getColumn(int columnIndex) {
		if (columnIndex >= this.alKeys.size()) {
			columnIndex = this.alKeys.size() - 1;
		}
		return this.hmClumns.get(this.alKeys.get(columnIndex));

	}

	private boolean checkColumn(ExcelColumn column) {
		if (null == column.getName()) {
			return false;
		}
		if (null == column.getShowName()) {
			return false;
		}
		return true;
	}

}
