package com.sept.jui.grid;

import java.util.HashMap;

import javax.swing.JComboBox;

import com.sept.exception.AppException;

public class GridColumn {
	private String showName;
	private String realName;
	private String columnType = COLUMNTYPE_STRING;
	private String defautValue = "";
	private String additional;
	private boolean readonly = false;
	private HashMap<String, String> arrayCode;
	private HashMap<String, String> arrayValue;
	public static String COLUMNTYPE_STRING = "String";
	public static String COLUMNTYPE_DATE = "Date";
	public static String COLUMNTYPE_DROPDOWN = "DropDown";
	public static String COLUMNTYPE_CHECKBOX = "CheckBox";

	public GridColumn() {
		this.arrayCode = new HashMap<String, String>();
		this.arrayValue = new HashMap<String, String>();
	}

	public GridColumn(String showName, String realName, String columnType, String defautValue, String additional,
			boolean readonly) throws AppException {
		this();
		setShowName(showName);
		setRealName(realName);
		setColumnType(columnType);
		setDefautValue(defautValue);
		setAdditional(additional);
		setReadonly(readonly);
	}

	protected GridColumn(String showName, String realName) throws AppException {
		this();
		setShowName(showName);
		setRealNameInClass(realName);
		setColumnType(COLUMNTYPE_STRING);
		setDefautValue("0");
		setReadonly(true);
	}

	public String getShowName() {
		return this.showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) throws AppException {
		if ((realName == null) || (realName.trim().isEmpty())) {
			throw new AppException("名字不能为空");
		}
		if ("no".equals(realName)) {
			throw new AppException("名字不能为no");
		}
		this.realName = realName;
	}

	protected void setRealNameInClass(String realName) throws AppException {
		if ((realName == null) || (realName.trim().isEmpty())) {
			throw new AppException("名字不能为空");
		}
		this.realName = realName;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getDefautValue() {
		return this.defautValue;
	}

	public void setDefautValue(String defautValue) {
		this.defautValue = defautValue;
	}

	public String getAdditional() {
		return this.additional;
	}

	public void setAdditional(String additional) {
		if (additional == null) {
			return;
		}
		this.additional = additional;
		if (COLUMNTYPE_DROPDOWN.equals(this.columnType)) {
			String[] str = this.additional.split(",");
			for (int i = 0; i < str.length; i++) {
				String[] st2 = str[i].split(":");
				this.arrayCode.put(st2[0], st2[1]);
				this.arrayValue.put(st2[1], st2[0]);
			}
		} else {
			COLUMNTYPE_DATE.equals(this.columnType);
		}
	}

	public boolean isReadonly() {
		return this.readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public String getDPValue(String name) {
		if (this.arrayCode.containsKey(name)) {
			return (String) this.arrayCode.get(name);
		}
		return name;
	}

	public String getDPCode(String value) {
		if (this.arrayValue.containsKey(value)) {
			return (String) this.arrayValue.get(value);
		}
		return value;
	}

	public JComboBox<String> getJComboBox() {
		if (!this.columnType.equals(COLUMNTYPE_DROPDOWN)) {
			return new JComboBox<String>();
		}
		JComboBox<String> cob = new JComboBox<String>();
		for (String value : this.arrayCode.values()) {
			cob.addItem(value);
		}
		return cob;
	}

	public String toString() {
		return this.showName;
	}
}
