package com.sept.drop.grid.columns;

import javax.swing.JComponent;

import com.sept.drop.grid.GridColumn;

public class CheckboxColumn implements GridColumn {
	private String name;
	private String showName;
	private boolean defaultValue;
	private boolean readonly;

	public CheckboxColumn(String name, String showName, boolean defaultValue, boolean readonly) {
		super();
		this.name = name;
		this.showName = showName;
		this.defaultValue = defaultValue;
		this.readonly = readonly;
	}

	@Override
	public Class<?> getComponentType() {
		return Boolean.class;
	}

	@Override
	public String getShowName() {
		return this.showName;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getDefaultValue() {
		return this.defaultValue;
	}

	@Override
	public Object dealValue(Object value) {
		value = (null == value || !value.toString().trim().equals("true") || !value.toString().trim().equals("false"))
				? false
				: value;
		return value;
	}

	@Override
	public Object dealValue4Get(Object value) {
		value = (null == value || !value.toString().trim().equals("true") || !value.toString().trim().equals("false"))
				? false
				: value;
		return value;
	}

	@Override
	public boolean readonly() {
		return this.readonly;
	}

	@Override
	public JComponent getComponent() {
		return null;
	}

}
