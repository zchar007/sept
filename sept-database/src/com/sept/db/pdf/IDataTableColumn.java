package com.sept.db.pdf;

public interface IDataTableColumn {
	public String getName();

	public void setName(String name);

	public String getShowName();

	public void setShowName(String showName);

	public int getDataType();

	public void setDataType(int dataType);

	public String getMask();

	public void setMask(String mask);

	public String getDefaultValue();

	public void setDefaultValue(String defaultValue);
}
