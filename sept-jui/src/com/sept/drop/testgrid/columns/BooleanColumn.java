package com.sept.drop.testgrid.columns;


import com.sept.drop.testgrid.GridColumn;

public class BooleanColumn implements GridColumn {
	private static final long serialVersionUID = 1L;
	private String name;
	private String head;
	private boolean defaultValue;
	private boolean readonly;

	public BooleanColumn(String name, String head, boolean defaultValue, boolean readonly) {
		super();
		this.name = name;
		this.head = head;
		this.defaultValue = defaultValue;
		this.readonly = readonly;
	}

	@Override
	public String getName() {
		return this.name;
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
	public Class<?> getValueType() {
		return Boolean.class;
	}

	@Override
	public Object dealValue(Object value) {
		value = (null == value || !value.toString().trim().equals("true") || !value.toString().trim().equals("false"))
				? false
				: value;
		return Boolean.parseBoolean(value.toString());
	}

}
