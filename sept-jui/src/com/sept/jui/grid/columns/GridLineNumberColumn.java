package com.sept.jui.grid.columns;

import javax.swing.JComponent;

import com.sept.jui.grid.GridColumn;

public class GridLineNumberColumn implements GridColumn {
	public static final String DEFAULT_SHOW_NAME = "No.";
	public static final String DEFAULT_NAME = "no";

	@Override
	public Class<?> getComponentType() {
		return String.class;
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
		return null;
	}

	@Override
	public Object dealValue(Object value) {
		return null == value ? "" : value.toString();
	}

	@Override
	public boolean readonly() {
		return true;
	}

	@Override
	public JComponent getComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object dealValue4Get(Object value) {
		return null == value ? "" : value.toString();

	}
}
