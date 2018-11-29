package com.sept.jui.grid.columns;

import java.awt.Component;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sept.exception.AppException;
import com.sept.jui.grid.action.GridCellAction;
import com.sept.jui.grid.model.GridColumn;
import com.sept.jui.input.date.SDateField;
import com.sept.util.DateUtil;

public class DateColumn implements GridColumn {
	private static final long serialVersionUID = 1L;
	private String name;
	private String head;
	private String defaultValue;
	private String sourceMask;
	private String mask;
	private boolean readonly;

	public DateColumn(String name, String head, String defaultValue, String sourceMask, String mask, boolean readonly) {
		super();
		this.name = name;
		this.head = head;
		this.defaultValue = defaultValue;
		this.sourceMask = sourceMask;
		this.mask = mask;
		this.readonly = readonly;
	}

	public DateColumn(String name, String head, Date defaultValue, String sourceMask, String mask, boolean readonly) {
		super();
		this.name = name;
		this.head = head;
		this.defaultValue = DateUtil.formatDate(defaultValue, sourceMask);
		this.readonly = readonly;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		SDateField sdf = new SDateField(this.mask);
		//sdf.setOpaque(false);  
		sdf.setBorder(null);  
		try {
			String text = DateUtil.formatDate(DateUtil.formatStrToDate(String.valueOf(value)), mask);
			sdf.setText(text);
		} catch (AppException e) {
			e.printStackTrace();
		}
		if (isSelected) {
			sdf.setBackground(table.getSelectionBackground());
		} else {
			sdf.setBackground(table.getBackground());
		}
		return sdf;
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
		SDateField sdf = new SDateField(this.mask);
		//sdf.setOpaque(false);  
		sdf.setBorder(null);  
		try {
			sdf.setText(DateUtil.formatDate(DateUtil.formatStrToDate(this.defaultValue), mask));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return sdf;
	}

	@Override
	public Object dealValue(Object value) {
		try {
			return String.valueOf(DateUtil.formatDate(DateUtil.formatStrToDate(String.valueOf(value)), mask));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public GridCellAction getAction() {
		return null;
	}

	@Override
	public Object dealValue4Get(Object value) {
		try {
			return String.valueOf(DateUtil.formatDate(DateUtil.formatStrToDate(String.valueOf(value)), sourceMask));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public int getValueIndex(Object value) {
		return 0;
	}

}
