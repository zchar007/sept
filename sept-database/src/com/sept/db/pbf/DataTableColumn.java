package com.sept.db.pbf;

public class DataTableColumn {
	private String name;
	private String showName;
	private String defaultValue;

	public DataTableColumn(String name, String showName, String defaultValue) {
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
