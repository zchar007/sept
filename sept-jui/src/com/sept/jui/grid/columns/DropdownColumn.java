package com.sept.jui.grid.columns;

import java.awt.Component;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;

import com.sept.jui.grid.action.GridCellAction;
import com.sept.jui.grid.model.GridColumn;

public class DropdownColumn implements GridColumn {
	private static final long serialVersionUID = 1L;
	private String name;
	private String head;
	private String defaultValue;
	private boolean readonly;
	private String arrayCode;
	private LinkedHashMap<String, String> keyValue;
	private LinkedHashMap<String, String> valueKey;

	public DropdownColumn(String name, String head, String defaultValue, String arrayCode, boolean readonly) {
		super();
		this.name = name;
		this.head = head;
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
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JComboBox<Object> jcb = new JComboBox<>();
		for (Object item : this.keyValue.values()) {
			jcb.addItem(item);
		}
		jcb.setSelectedItem(value);
		if (isSelected) {
			jcb.setBorder(BorderFactory.createLineBorder(table.getSelectionBackground()));
			// jcb.setBackground(table.getSelectionBackground());
		} else {
			jcb.setBorder(BorderFactory.createLineBorder(table.getBackground()));
			// jcb.setBackground(table.getBackground());
		}
		return jcb;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public JComponent getComponent() {
		JComboBox<Object> jcb = new JComboBox<>();
		for (Object item : this.keyValue.values()) {
			jcb.addItem(item);
		}
		jcb.setSelectedItem(this.keyValue.get(this.getDefault()));
		return jcb;
	}

	@Override
	public Object dealValue(Object value) {
		return this.keyValue.get(value);
	}

	@Override
	public GridCellAction getAction() {
		return null;
	}

	@Override
	public Object dealValue4Get(Object value) {
		return this.valueKey.get(value);
	}

	@Override
	public int getValueIndex(Object value) {
		int index = -1;
		for (Object key : this.valueKey.keySet()) {
			index++;
			if (value.equals(key)) {
				return index;
			}
		}
		return -1;
	}

}
