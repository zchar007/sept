package com.sept.drop.pdf;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.sept.exception.AppException;

public class DataTableColumns implements IDataTableColumns {
	private LinkedHashMap<String, IDataTableColumn> lhmClumns;

	public DataTableColumns() {
		this.lhmClumns = new LinkedHashMap<String, IDataTableColumn>();

	}

	@Override
	public void addColumn(IDataTableColumn idtColumn) throws AppException {
		if (!this.checkColumn(idtColumn)) {
			throw new AppException("即将增加的第[" + this.lhmClumns.size() + "]列存不符合列数据要求，请检查！");
		}
		this.lhmClumns.put(idtColumn.getName(), idtColumn);

	}

	@Override
	public void insertColumn(int index, IDataTableColumn idtColumn) throws AppException {
		if (index < 0) {
			throw new AppException("下标[" + index + "]不能小于0,请检查！");
		}
		if (index > this.lhmClumns.size()) {
			throw new AppException("下标[" + index + "]大于当前所有列的最大下标[" + this.lhmClumns.size() + "],请检查！");
		}
		if (!this.checkColumn(idtColumn)) {
			throw new AppException("即将设置的第[" + (index + 1) + "]列存不符合列数据要求，请检查！");
		}
		if (index == this.lhmClumns.size()) {
			this.lhmClumns.put(idtColumn.getName(), idtColumn);
		} else {
			LinkedHashMap<String, IDataTableColumn> lhmClumns_temp = new LinkedHashMap<>();
			int i = 0;
			for (String key : this.lhmClumns.keySet()) {
				if (i == index) {
					lhmClumns_temp.put(idtColumn.getName(), idtColumn);
				} else {
					lhmClumns_temp.put(key, this.lhmClumns.get(key));
				}
			}
			this.lhmClumns = lhmClumns_temp;
		}

	}

	@Override
	public void setColumn(int index, IDataTableColumn idtColumn) throws AppException {
		if (index < 0) {
			throw new AppException("下标[" + index + "]不能小于0,请检查！");
		}
		if (index > this.lhmClumns.size()) {
			throw new AppException("下标[" + index + "]大于当前所有列的最大下标[" + this.lhmClumns.size() + "],请检查！");
		}
		if (!this.checkColumn(idtColumn)) {
			throw new AppException("即将插入的第[" + (index + 1) + "]列存不符合列数据要求，请检查！");
		}
		if (index == this.lhmClumns.size()) {
			this.lhmClumns.put(idtColumn.getName(), idtColumn);
		} else {

			int i = 0;
			for (String key : this.lhmClumns.keySet()) {
				if (i == index) {
					this.lhmClumns.replace(key, idtColumn);
					break;
				}
			}
		}
	}

	@Override
	public void deleteColumn(int index) throws AppException {
		if (index < 0) {
			throw new AppException("下标[" + index + "]不能小于0,请检查！");
		}
		if (index >= this.lhmClumns.size()) {
			throw new AppException("下标[" + index + "]大于当前列的最大值[" + (this.lhmClumns.size() - 1) + "],请检查！");
		}
		int i = 0;
		for (String key : this.lhmClumns.keySet()) {
			if (i == index) {
				this.lhmClumns.remove(key);
				break;
			}
			i++;
		}
	}

	@Override
	public void deleteColumn(IDataTableColumn idtColumn) throws AppException {
		if (null == idtColumn) {
			throw new AppException("传入列为null!");
		}
		if (!this.lhmClumns.containsValue(idtColumn)) {
			throw new AppException("当前列不含有列[" + idtColumn.getName() + "],请检查！");
		}
		for (String key : this.lhmClumns.keySet()) {
			if (this.lhmClumns.get(key).equals(idtColumn)) {
				this.lhmClumns.remove(key);
				break;
			}
		}
	}

	@Override
	public IDataTableColumn getColumn(int index) throws AppException {
		if (index < 0) {
			throw new AppException("下标[" + index + "]不能小于0,请检查！");
		}
		if (index >= this.lhmClumns.size()) {
			throw new AppException("下标[" + index + "]大于当前列的最大值[" + (this.lhmClumns.size() - 1) + "],请检查！");
		}
		int i = 0;
		for (String key : this.lhmClumns.keySet()) {
			if (i == index) {
				return this.lhmClumns.get(key);
			}
			i++;
		}
		return null;
	}

	@Override
	public IDataTableColumn getColumn(String name) {
		return this.lhmClumns.get(name);
	}

	@Override
	public boolean checkColumn(IDataTableColumn idtColumn) throws AppException {
		if (null == idtColumn) {
			throw new AppException("即将增加的第[" + this.lhmClumns.size() + "]列为null，请检查！");
		}
		if (null == idtColumn.getName()) {
			return false;
		}
		if (null == idtColumn.getShowName()) {
			return false;
		}
		return true;
	}

	@Override
	public ArrayList<String> getHead() {
		@SuppressWarnings("unchecked")
		ArrayList<String> al = (ArrayList<String>) this.lhmClumns.keySet();
		return al;
	}

	@Override
	public int columnCount() {
		return this.lhmClumns.size();
	}

	@Override
	public String getName(String showName) {
		for (IDataTableColumn idTableColumn : this.lhmClumns.values()) {
			if (idTableColumn.getShowName().equals(showName)) {
				return idTableColumn.getName();
			}
		}
		return showName;
	}

	@Override
	public String getShowName(String name) {
		return this.lhmClumns.containsKey(name) ? this.lhmClumns.get(name).getShowName() : name;
	}

	@Override
	public IDataTableColumn getColumnByShowName(String name) throws AppException {
		return this.lhmClumns.get(this.getName(name));
	}

}
