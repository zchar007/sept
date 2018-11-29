package com.sept.drop.newgrid.columns;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sept.drop.newgrid.columns.rander.GridColumn;

public class TextColumn implements GridColumn {
	private static final long serialVersionUID = 1L;
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
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JTextField jtf = new JTextField();

		jtf.setText(String.valueOf(value));
		if (isSelected) {
			jtf.setBackground(table.getSelectionBackground());
		} else {
			jtf.setBackground(table.getBackground());
		}
		return jtf;
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
	public JComponent getComponent() {
		JTextField jtf = new JTextField((String) this.getDefault());

		return jtf;
	}

	@Override
	public Object dealValue(Object value) {
		return String.valueOf(value);
	}

}
