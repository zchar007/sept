package com.sept.jui.grid.model;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.sept.jui.grid.action.GridCellAction;

public interface GridColumn extends TableCellRenderer, Serializable {

	String getName();

	String getHead();

	Object getDefault();

	boolean readonly();

	JComponent getComponent();

	/**
	 * 有code的话转换成code值，没有则返回原值即可
	 * 
	 * @param value
	 * @return
	 */
	Object dealValue(Object value);

	/**
	 * 有value的话转换成value值，没有则返回原值即可
	 * 
	 * @param value
	 * @return
	 */
	Object dealValue4Get(Object value);

	/**
	 * 获取value的序号（主要针对combox）,其他组建返回0；
	 * 
	 * @param value
	 * @return
	 */
	int getValueIndex(Object value);

	GridCellAction getAction();

	Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int column);
}
