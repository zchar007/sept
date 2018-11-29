package com.sept.jui.grid.columns;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sept.jui.grid.action.GridCellAction;
import com.sept.jui.grid.model.GridColumn;

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
		//jtf.setOpaque(false);  
		jtf.setBorder(null);  
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
		//jtf.setOpaque(false);  
		jtf.setBorder(null);  
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
