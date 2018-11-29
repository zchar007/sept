package com.sept.drop.newgrid.columns;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;

import com.sept.drop.newgrid.columns.rander.GridColumn;

public class ButtonColumn implements GridColumn {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String head;
	private boolean readonly;
	private TableCellButtonAction tcba;

	public ButtonColumn(String name, String head, boolean readonly, TableCellButtonAction tcba) {
		super();
		this.name = name;
		this.head = head;
		this.readonly = readonly;
		this.setTableCellButtonAction(tcba);
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

	public TableCellButtonAction getTableCellButtonAction() {
		return tcba;
	}

	public void setTableCellButtonAction(TableCellButtonAction tcba) {
		this.tcba = tcba;
	}

	public interface TableCellButtonAction extends EventListener {
		void actionPerformed(ActionEvent e, int rowIndex);
	}

	@Override
	public Object dealValue(Object value) {
		return this.name;
	}
}
