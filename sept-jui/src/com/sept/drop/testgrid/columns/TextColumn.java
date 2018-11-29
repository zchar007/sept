package com.sept.drop.testgrid.columns;

import com.sept.drop.testgrid.GridColumn;

public class TextColumn implements GridColumn {
	private String name;
	private String head;
	private String defaultValue;
	private boolean readonly;

	public TextColumn(String name, String head, String defaultValue, boolean readonly) {
		super();
		this.name = name;
		this.head = head;
		this.defaultValue = defaultValue;
		this.readonly = readonly;
	}

	@Override
	public String getHead() {
		return this.head;
	}

	@Override
	public Object getDefault() {
		return this.defaultValue;
	}

	@Override
	public boolean readonly() {
		return this.readonly;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Class<?> getValueType() {
		return String.class;
	}

	@Override
	public Object dealValue(Object value) {
		value = null == value ? "" : value;
		return value.toString();
	}

}
