package com.sept.jui.grid.columns;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JTable;

import com.sept.exception.AppException;
import com.sept.jui.grid.action.GridCellAction;
import com.sept.jui.grid.model.GridColumn;
import com.sept.jui.input.combox.color.SColorPickerCombobox;
import com.sept.jui.input.date.SDateField;
import com.sept.util.ColorUtil;
import com.sept.util.DateUtil;
import com.sept.util.ImageUtil;

public class ColorColumn implements GridColumn {
	private static final long serialVersionUID = 1L;
	private String name;
	private String head;
	private Color defaultValue;
	private boolean readonly;

	public ColorColumn(String name, String head, Color defaultValue, boolean readonly) {
		super();
		this.name = name;
		this.head = head;
		this.defaultValue = defaultValue;
		this.readonly = readonly;
	}

	public ColorColumn(String name, String head, String defaultValue, boolean readonly) {
		super();
		this.name = name;
		this.head = head;
		this.defaultValue = ColorUtil.toColorFromString(defaultValue);
		this.readonly = readonly;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		SColorPickerCombobox spc = new SColorPickerCombobox();
		spc.setOpaque(false);  
		spc.setBorder(null);  
		spc.setSelectedColor((Color) value);
		if (isSelected) {
			spc.setBackground(table.getSelectionBackground());
		} else {
			spc.setBackground(table.getBackground());
		}
		return spc;
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
		SColorPickerCombobox spc = new SColorPickerCombobox();
		spc.setOpaque(false);  
		spc.setBorder(null);  
		spc.setSelectedColor(this.defaultValue);
		return spc;
	}

	@Override
	public Object dealValue(Object value) {
		if (value instanceof Color) {
			return value;
		}
		return ColorUtil.toColorFromString(String.valueOf(value));
	}

	@Override
	public GridCellAction getAction() {
		return null;
	}

	@Override
	public Object dealValue4Get(Object value) {
		return ColorUtil.toHexFromColor(((Color) value));
	}

	@Override
	public int getValueIndex(Object value) {
		return 0;
	}

}
