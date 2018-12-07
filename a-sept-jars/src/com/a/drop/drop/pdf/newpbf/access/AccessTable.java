package com.sept.drop.pdf.newpbf.access;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.datastructure.comparator.Datas;
import com.sept.drop.pdf.newpbf.DTColumn;
import com.sept.drop.pdf.newpbf.DTColumns;
import com.sept.drop.pdf.newpbf.IDTColumns;
import com.sept.drop.pdf.newpbf.IDTHandle;
import com.sept.drop.pdf.newpbf.IDTTable;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.util.CloneUtil;

class AccessTable implements IDTTable, IDTHandle {
	private Table table;
	private IDTColumns dtColumns;
	private DataStore dsTable;

	public AccessTable(Table table) {
		this.dsTable = new DataStore();
		this.table = table;
	}

	/**
	 * 还需验证
	 */
	@Override
	public void setColumns(IDTColumns dtColumns) throws AppException {
		if (null == this.dtColumns) {
			this.defaultDTColumn();
		}
		// 比较前后两个，以插入原则放入
		HashMap<String, String> record = new HashMap<>();
		int length = dtColumns.columnCount();
		for (int i = 0; i < length; i++) {
			DTColumn dt_before = this.dtColumns.columnCount() >= i ? this.dtColumns.getColumn(i) : null;
			DTColumn dt_after = dtColumns.getColumn(i);
			if (null == dt_before) {// 超过了原来列数的部分直接往后拼
				this.dtColumns.addColumn(dt_after);
				record.put(dt_after.getKey(), dt_after.getKey());
			} else {
				if (dt_before.getHead().equals(dt_after.getHead())) {// 相同列
					record.put(dt_before.getKey(), dt_after.getKey());
					dt_before.setKey(dt_after.getKey());
					dt_before.setMask(dt_after.getMask());
					dt_before.setType(dt_after.getType());
					dt_before.setNone(dt_after.getNone());
					dt_before.setArray(dt_after.getArray());
				} else {// 不同列，替换
					record.put(dt_before.getKey(), dt_after.getKey());
					this.dtColumns.setColumn(i, dt_after);
				}
			}
		}
		// 多余部分，截掉
		int length_now = this.dtColumns.columnCount();
		for (int i = length_now; i > length; i--) {
			this.dtColumns.deleteColumn(i);
		}

		for (int i = 0; i < this.dsTable.rowCount(); i++) {
			for (String key : record.keySet()) {
				this.dsTable.put(i, record.get(key),
						this.dsTable.get(i).get(key, this.dtColumns.getColumnByKey(record.get(key)).getNone()));
				try {
					this.dsTable.get(i).remove(key);
				} catch (Exception e) {
				}
			}
		}

	}

	@Override
	public void setKeyValue(String keyValue) throws AppException {
		if (null == this.dtColumns) {
			this.defaultDTColumn();
		}
		HashMap<String, String> record = new HashMap<>();
		String[] keys_values = keyValue.split(",");
		for (int i = 0; i < keys_values.length; i++) {
			String[] key_value = keys_values[i].split(":");
			DTColumn dtColumn = this.dtColumns.getColumnByHead(key_value[0]);
			record.put(dtColumn.getKey(), key_value[1]);
			dtColumn.setKey(key_value[1]);
		}
		for (int i = 0; i < this.dsTable.rowCount(); i++) {
			for (String key : record.keySet()) {
				this.dsTable.put(i, record.get(key), this.dsTable.get(i).get(key));
				this.dsTable.get(i).remove(key);
			}
		}
	}

	private void defaultDTColumn() throws AppException {
		this.dtColumns = new DTColumns();
		List<? extends Column> lColumns = table.getColumns();
		for (int i = 0; i < lColumns.size(); i++) {
			Column column = lColumns.get(i);
			String name = column.getName();
			int type;
			try {
				type = column.getType().getSQLType();
			} catch (SQLException e) {
				LogHandler.fatal(e);
				throw new AppException(e);
			}
			DTColumn dtColumn = new DTColumn(name, name);
			dtColumn.setType(type);
			this.dtColumns.addColumn(dtColumn);
		}

	}

	@Override
	public ArrayList<String> getHead() throws AppException {
		return this.dtColumns.getHead();
	}

	@Override
	public void add(DataObject row) throws AppException {
		this.dsTable.addRow();
		for (int j = 0; j < dtColumns.columnCount(); j++) {
			DTColumn idtc = dtColumns.getColumn(j);
			if (row.containsKey(idtc.getKey())) {
				this.dsTable.put(this.dsTable.rowCount() - 1, idtc.getKey(), row.get(idtc.getKey()));
			} else {
				this.dsTable.put(this.dsTable.rowCount() - 1, idtc.getKey(), idtc.getNone());
			}
		}
	}

	@Override
	public void add(DataStore rows) throws AppException {
		for (DataObject row : rows) {
			this.add(row);
		}
	}

	@Override
	public void insert(int index, DataObject row) throws AppException {
		DataObject row_temp = new DataObject();
		for (int j = 0; j < dtColumns.columnCount(); j++) {
			DTColumn idtc = dtColumns.getColumn(j);
			if (row.containsKey(idtc.getKey())) {
				row_temp.put(idtc.getKey(), row.get(idtc.getKey()));
			} else {
				row_temp.put(idtc.getKey(), idtc.getNone());
			}
		}
		this.dsTable.insertRow(index, row_temp);
	}

	@Override
	public void insert(int index, DataStore rows) throws AppException {
		for (int i = rows.rowCount(); i >= 0; i--) {
			this.insert(index, rows.get(i));
		}
	}

	@Override
	public int delete(int index) throws AppException {
		if (index < 0 || index >= this.dsTable.rowCount()) {
			return 0;
		}
		this.dsTable.remove(index);

		return 1;
	}

	@Override
	public int delete(String condition) throws AppException {
		int[] all = this.dsTable.findAll(condition);
		for (int i = all.length - 1; i >= 0; i--) {
			this.dsTable.remove(all[i]);
		}
		return all.length;
	}

	@Override
	public int update(int index, DataObject row) throws AppException {
		if (index < 0 || index >= this.dsTable.rowCount()) {
			return 0;
		}
		for (int j = 0; j < dtColumns.columnCount(); j++) {
			DTColumn idtc = dtColumns.getColumn(j);
			if (row.containsKey(idtc.getKey())) {
				this.dsTable.get(index).put(idtc.getKey(), row.get(idtc.getKey()));
			} else {
				this.dsTable.get(index).put(idtc.getKey(), this.dsTable.get(index).get(idtc.getKey()));
			}
		}
		return 1;
	}

	@Override
	public int update(String condition, DataObject row) throws AppException {
		int[] all = this.dsTable.findAll(condition);
		for (int i = all.length - 1; i >= 0; i--) {
			for (int j = 0; j < dtColumns.columnCount(); j++) {
				DTColumn idtc = dtColumns.getColumn(j);
				if (row.containsKey(idtc.getKey())) {
					this.dsTable.get(all[i]).put(idtc.getKey(), row.get(idtc.getKey()));
				} else {
					this.dsTable.get(all[i]).put(idtc.getKey(), this.dsTable.get(all[i]).get(idtc.getKey()));
				}
			}
		}
		return all.length;
	}

	@Override
	public DataStore select() throws AppException {
		return this.dsTable;
	}

	@Override
	public DataStore select(int index) throws AppException {
		DataStore ds = new DataStore();
		ds.addRow(this.dsTable.get(index));
		return ds;
	}

	@Override
	public DataStore select(String condition) throws AppException {
		return Datas.filter(this.dsTable, condition, false);
	}

	protected void commit() throws AppException {
		try {
			ArrayList<Row> al = new ArrayList<>();
			for (Row row : this.table) {
				al.add(row);
			}
			for (int i = 0; i < al.size(); i++) {
				this.table.deleteRow(al.get(i));
			}

			DataStore ds_temp = new DataStore();

			for (int i = 0; i < this.dsTable.rowCount(); i++) {
				ds_temp.addRow();
				for (int j = 0; j < dtColumns.columnCount(); j++) {
					DTColumn idtc = dtColumns.getColumn(j);
					ds_temp.put(ds_temp.rowCount() - 1, idtc.getHead(), this.dsTable.get(i).get(idtc.getKey()));
				}
			}
			this.table.addRowsFromMaps(ds_temp);
		} catch (IOException e) {
			LogHandler.error(e.getMessage());
			throw new AppException(e);
		}
	}

	@Override
	public DataObject check(DataObject row) throws AppException {
		return null;
	}

	protected void init() throws AppException {
		// 获取dtColumns
		if (null == this.dtColumns || this.dtColumns.columnCount() == 0) {
			this.defaultDTColumn();
		}
		this.dsTable = new DataStore();
		for (Row row : table) {
			this.dsTable.addRow();
			for (int i = 0; i < this.dtColumns.columnCount(); i++) {
				DTColumn dtColumn = this.dtColumns.getColumn(i);
				this.dsTable.put(this.dsTable.rowCount() - 1, dtColumn.getKey(), row.get(dtColumn.getHead()));
			}
		}

	}

}
