package com.sept.jui.grid.columns;

import javax.swing.JComponent;

import com.sept.jui.grid.GridColumn;

public class TextColumn implements GridColumn {
	private String name;
	private String showName;
	private String defaultValue;
	private boolean readonly;

	public TextColumn(String name, String showName, String defaultValue, boolean readonly) {
		super();
		this.name = name;
		this.showName = showName;
		this.defaultValue = defaultValue;
		this.readonly = readonly;
	}

	@Override
	public Class<?> getComponentType() {
		return String.class;
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
		return this.dealValue(this.defaultValue);
	}

	@Override
	public Object dealValue(Object value) {
		return null == value ? "" : value.toString();
	}

	@Override
	public boolean readonly() {
		return this.readonly;
	}

	@Override
	public JComponent getComponent() {
		return null;
	}

	@Override
	public Object dealValue4Get(Object value) {
		return null == value ? "" : value.toString();
	}

}
