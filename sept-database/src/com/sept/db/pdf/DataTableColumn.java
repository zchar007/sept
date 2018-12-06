package com.sept.db.pdf;

import java.sql.Types;

public class DataTableColumn implements IDataTableColumn {
	private String name;
	private String showName;
	private int dataType;
	private String mask;
	private String defaultValue;

	public DataTableColumn() {
	}

	public DataTableColumn(String name, String showName, int dataType) {
		super();
		this.name = name;
		this.showName = showName;
		this.dataType = dataType;
	}

	public DataTableColumn(String name, String showName) {
		super();
		this.name = name;
		this.showName = showName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		this.setShowName(name);
	}

	@Override
	public String getShowName() {
		return showName;
	}

	@Override
	public void setShowName(String showName) {
		this.showName = showName;
	}

	@Override
	public int getDataType() {
		return dataType;
	}

	@Override
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	@Override
	public String getMask() {
		return mask;
	}

	@Override
	public void setMask(String mask) {
		this.mask = mask;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
