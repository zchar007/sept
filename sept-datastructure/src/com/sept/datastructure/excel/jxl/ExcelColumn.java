package com.sept.datastructure.excel.jxl;

public class ExcelColumn {
	private String name;
	private String showName;
	private String defaultValue;

	public ExcelColumn(String name, String showName, String defaultValue) {
		super();
		this.name = name;
		this.showName = showName;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
