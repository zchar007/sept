package com.sept.jui.grid.columns;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.sept.jui.grid.action.GridCellAction;
import com.sept.jui.grid.model.GridColumn;

@Deprecated
public class ButtonsColumn implements GridColumn {
	private static final long serialVersionUID = 1L;
	private String name;
	private String head;
	private boolean readonly;
	private GridCellAction gca;
	private ArrayList<JButton> alButtons;

	public ButtonsColumn(String name, String head, boolean readonly, GridCellAction gca) {
		super();
		this.alButtons = new ArrayList<>();
		this.name = name;
		String[] names = name.split(",");
		for (int i = 0; i < names.length; i++) {
			if (!names[i].trim().isEmpty()) {
				JButton button = new JButton(names[i]);
				alButtons.add(button);
			}
		}
		this.head = head;
		this.readonly = readonly;
		this.gca = gca;
	}

	public ButtonsColumn(ArrayList<JButton> alButtons, String head, boolean readonly, GridCellAction gca) {
		super();
		this.alButtons = alButtons;
		String nameT = "";
		for (int i = 0; i < alButtons.size(); i++) {
			nameT += alButtons.get(i).getActionCommand() + (i < alButtons.size() - 1 ? "," : "");
		}
		this.name = nameT;
		this.head = head;
		this.readonly = readonly;
		this.gca = gca;
	}

	@Override
	public String getName() {
		return this.name;
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
	public JComponent getComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3, 0, 10));
		for (int i = 0; i < alButtons.size(); i++) {
			panel.add(alButtons.get(i));
		}
		return panel;
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

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3, 0, 10));
		for (int i = 0; i < alButtons.size(); i++) {
			panel.add(alButtons.get(i));
		}
		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
		return panel;
	}

	public ArrayList<JButton> getAlButtons() {
		return alButtons;
	}

}
