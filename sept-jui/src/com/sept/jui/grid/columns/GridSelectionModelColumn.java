package com.sept.jui.grid.columns;

import javax.swing.JComponent;

import com.sept.jui.grid.GridColumn;

public class GridSelectionModelColumn implements GridColumn {
	public static final String DEFAULT_SHOW_NAME = "";
	public static final String DEFAULT_NAME = "_select_";

	public GridSelectionModelColumn() {
		super();
	}

	@Override
	public Class<?> getComponentType() {
		return Boolean.class;
	}

	@Override
	public String getShowName() {
		return DEFAULT_SHOW_NAME;
	}

	@Override
	public String getName() {
		return DEFAULT_NAME;
	}

	@Override
	public Object getDefaultValue() {
		return false;
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
		return false;
	}

	@Override
	public JComponent getComponent() {
		return null;
	}

}
