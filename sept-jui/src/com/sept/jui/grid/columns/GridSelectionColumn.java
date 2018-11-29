package com.sept.jui.grid.columns;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;

import com.sept.jui.grid.action.GridCellAction;
import com.sept.jui.grid.model.GridColumn;

public class GridSelectionColumn implements GridColumn {
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_SHOW_NAME = "";
	public static final String DEFAULT_NAME = "_select_";
	private String name = DEFAULT_SHOW_NAME;

	public GridSelectionColumn() {
		super();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getHead() {
		return this.name;
	}

	@Override
	public Object getDefault() {
		return false;
	}

	@Override
	public boolean readonly() {
		return false;
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
		checkBox.setSelected((Boolean) this.getDefault());
		return checkBox;
	}

	@Override
	public GridCellAction getAction() {
		return null;
	}

	@Override
	public Object dealValue(Object value) {
		value = (null == value || (!value.toString().trim().equals("true") && !value.toString().trim().equals("false")))
				? false
				: value;
		return Boolean.parseBoolean(String.valueOf(value));
	}

	@Override
	public Object dealValue4Get(Object value) {
		value = (null == value || (!value.toString().trim().equals("true") && !value.toString().trim().equals("false")))
				? false
				: value;
		return Boolean.parseBoolean(String.valueOf(value));
	}

	@Override
	public int getValueIndex(Object value) {
		return 0;
	}

}
