package com.sept.db.pbf.temp;

public interface IDataTableColumn {
	public String getName();

	public void setName(String name);

	public String getShowName();

	public void setShowName(String showName);

	public String getDataType();

	public void setDataType(String dataType);

	public String getMask();

	public void setMask(String mask);

	public String getDefaultValue();

	public void setDefaultValue(String defaultValue);
}
