package com.sept.jui.grid.columns;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;

import com.sept.jui.grid.action.GridCellAction;
import com.sept.jui.grid.model.GridColumn;

public class ButtonColumn implements GridColumn {
	private static final long serialVersionUID = 1L;
	private String name;
	private String head;
	private boolean readonly;
	private GridCellAction gca;

	public ButtonColumn(String name, String head, boolean readonly, GridCellAction gca) {
		super();
		this.name = name;
		this.head = head;
		this.readonly = readonly;
		this.gca = gca;
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
		return this.readonly;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JButton button = new JButton(this.getName());

		if (isSelected) {
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setBackground(table.getBackground());
		}
		return button;
	}

	@Override
	public JComponent getComponent() {
		JButton button = new JButton(this.getName());
		return button;
	}

	@Override
	public Object dealValue(Object value) {
		return this.name;
	}

	@Override
	public Object dealValue4Get(Object value) {
		return this.name;
	}

	@Override
	public int getValueIndex(Object value) {
		return 0;
	}

	@Override
	public GridCellAction getAction() {
		return this.gca;
	}
}
