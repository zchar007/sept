package com.sept.drop.newgrid.columns;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;

import com.sept.drop.newgrid.columns.rander.GridColumn;

public class BooleanColumn implements GridColumn {
	/**
	 * 
	 */
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
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JCheckBox checkBox = new JCheckBox();
		checkBox.setSelected(Boolean.parseBoolean(value.toString()));
		if (isSelected) {
			checkBox.setBackground(table.getSelectionBackground());
		} else {
			checkBox.setBackground(table.getBackground());
		}
		return checkBox;
	}

	@Override
	public JComponent getComponent() {
		JCheckBox checkBox = new JCheckBox();
		checkBox.setSelected((Boolean)this.getDefault());
		return checkBox;
	}

	@Override
	public Object dealValue(Object value) {
		value = (null == value || !value.toString().trim().equals("true") || !value.toString().trim().equals("false"))
				? false
				: value;
		return Boolean.parseBoolean(String.valueOf(value));
	}

}
