package com.sept.drop.pbf.access;

import com.healthmarketscience.jackcess.Table;

class AccessTable {
	private Table table;

	protected AccessTable(Table table) {
		this.setTable(table);
	}

	protected Table getTable() {
		return table;
	}

	protected void setTable(Table table) {
		this.table = table;
	}
}
