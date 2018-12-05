package com.sept.drop.pbf.temp;

public class DataTableColumn implements IDataTableColumn {
	private String name;
	private String showName;
	private String dataType;
	private String mask;
	private String defaultValue;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
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
	public String getDataType() {
		return dataType;
	}

	@Override
	public void setDataType(String dataType) {
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
