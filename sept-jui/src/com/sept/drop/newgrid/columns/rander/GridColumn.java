package com.sept.drop.newgrid.columns.rander;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public interface GridColumn extends TableCellRenderer, Serializable {

	String getName();

	String getHead();

	Object getDefault();

	boolean readonly();
	
	JComponent getComponent();
	
	Object dealValue(Object value);

	Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int column);
}
