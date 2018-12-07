package com.sept.drop.grid.columns;

import java.util.LinkedHashMap;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import com.sept.drop.grid.GridColumn;

public class DropdownColumn implements GridColumn {
	private String name;
	private String showName;
	private String defaultValue;
	private boolean readonly;
	private String arrayCode;
	private LinkedHashMap<String, String> keyValue;
	private LinkedHashMap<String, String> valueKey;

	public DropdownColumn(String name, String showName, String defaultValue, String arrayCode, boolean readonly) {
		super();
		this.name = name;
		this.showName = showName;
		this.defaultValue = defaultValue;
		this.readonly = readonly;
		this.arrayCode = arrayCode;
		this.keyValue = new LinkedHashMap<>();
		this.valueKey = new LinkedHashMap<>();

		String[] str = this.arrayCode.split(",");
		for (int i = 0; i < str.length; i++) {
			String[] st2 = str[i].split(":");
			this.keyValue.put(st2[0], st2[1]);
			this.valueKey.put(st2[1], st2[0]);
		}

	}

	@Override
	public Class<?> getComponentType() {
		return JComboBox.class;
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
		return this.keyValue.get(value);
	}

	@Override
	public boolean readonly() {
		return this.readonly;
	}

	@Override
	public JComponent getComponent() {
		JComboBox<String> cob = new JComboBox<String>();
		for (String value : this.keyValue.values()) {
			cob.addItem(value);
		}
		return cob;
	}

	@Override
	public Object dealValue4Get(Object value) {
		return this.valueKey.get(value);
	}

}
