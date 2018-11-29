package com.sept.jui.grid.columns;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sept.jui.grid.action.GridCellAction;
import com.sept.jui.grid.model.GridColumn;

public class GridLineNumberColumn implements GridColumn {
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_HEAD = "No.";
	public static final String DEFAULT_NAME = "_no_";
	private String head = DEFAULT_HEAD;

	public GridLineNumberColumn() {
		super();
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
		return null;
	}

	@Override
	public boolean readonly() {
		return true;
	}

	@Override
	public String getName() {
		return DEFAULT_NAME;
	}

	@Override
	public JComponent getComponent() {
		JTextField jtf = new JTextField();
		return jtf;
	}

	@Override
	public Object dealValue(Object value) {
		return String.valueOf(value);
	}

	@Override
	public GridCellAction getAction() {
		return null;
	}

	@Override
	public Object dealValue4Get(Object value) {
		return String.valueOf(value);
	}

	@Override
	public int getValueIndex(Object value) {
		return 0;
	}

}
