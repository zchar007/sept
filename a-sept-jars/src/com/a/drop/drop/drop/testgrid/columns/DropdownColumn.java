package com.sept.drop.testgrid.columns;

import java.util.LinkedHashMap;

import com.sept.drop.testgrid.GridColumn;
import com.sept.exception.AppException;

public class DropdownColumn<T> implements GridColumn {
	private static final long serialVersionUID = 1L;
	private String name;
	private String head;
	private T defaultValue;
	private boolean readonly;
	private String arrayCode;
	private LinkedHashMap<String, String> keyValue;
	private LinkedHashMap<String, String> valueKey;

	public DropdownColumn(String name, String head, T defaultValue, String arrayCode, boolean readonly)
			throws AppException {
		super();
		if (null == defaultValue) {
			throw new AppException("带有泛型的默认值不能为null！");
		}
		this.name = name;
		this.head = head;
		this.defaultValue = defaultValue;
		this.readonly = readonly;
		this.arrayCode = arrayCode;
		this.keyValue = new LinkedHashMap<>();
		this.valueKey = new LinkedHashMap<>();
		this.dealCode();

	}

	protected void dealCode() {
		String[] str = this.arrayCode.split(",");
		for (int i = 0; i < str.length; i++) {
			String[] st2 = str[i].split(":");
			this.keyValue.put(st2[0], st2[1]);
			this.valueKey.put(st2[1], st2[0]);
		}
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
		return defaultValue.getClass();
	}

	@Override
	public Object dealValue(Object value) {
		return deal(value);
	}

	@SuppressWarnings("unchecked")
	protected T deal(Object value) {
		return (T) value;
	}

}
