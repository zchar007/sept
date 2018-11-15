package com.sept.jui.grid;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public class Grid extends JComponent implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private boolean showHeade = false;
	private boolean showFoot = false;
	private boolean showPop_add = true;
	private boolean showPop_remove = true;
	private boolean showPop_clear = true;
	private boolean autoResize = false;
	private JPanel heade_panel;
	private JPanel foot_panel;
	private JScrollPane scrollPane;
	private ArrayList<GridColumn> gridColumns;
	private DataStore dsData;
	private GridModel gridModel;
	private boolean showLineNumber;
	private JPopupMenu popupMenu;
	private JMenuItem menuItem_add;
	private JMenuItem menuItem_delete;
	private JMenuItem menuItem_clear;

	public Grid() {
		this.gridColumns = new ArrayList<GridColumn>();
		this.dsData = new DataStore();
		setLayout(new BorderLayout(0, 0));
		this.scrollPane = new JScrollPane();
		add(this.scrollPane, BorderLayout.CENTER);
	}

	/**
	 * 增加一列
	 * 
	 * @param gridClumn
	 */
	public void addColumn(GridColumn gridClumn) {
		if (gridClumn == null) {
			return;
		}
		this.gridColumns.add(gridClumn);
	}

	/**
	 * 增加一列
	 * 
	 * @param gridClumn
	 */
	public void addColumn(String showName, String realName, String columnType, String defautValue, String dsCode,
			boolean readonly) throws AppException {
		GridColumn gridClumn = new GridColumn(showName, realName, columnType, defautValue, dsCode, readonly);
		this.gridColumns.add(gridClumn);
	}

	/**
	 * 增加一行
	 * 
	 * @param para
	 * @throws AppException
	 * @throws DataException
	 */
	public void addRow(DataObject para) throws AppException {
		if (para == null) {
			return;
		}
		this.dsData.addRow(para);
		if (this.gridModel == null) {
			return;
		}
		this.gridModel.addRow(getRowDataFromeDataObject(para));
		updateDatas();
	}

	/**
	 * 增加多行
	 * 
	 * @param para
	 * @throws AppException
	 * @throws DataException
	 */
	public void addRows(DataStore para) throws AppException {
		if (para == null) {
			return;
		}
		for (DataObject dataObject : para) {
			if (dataObject != null) {
				this.dsData.addRow(dataObject);
				if (this.gridModel != null) {
					this.gridModel.addRow(getRowDataFromeDataObject(dataObject));
				}
			}
		}
		if (this.gridModel != null) {
			updateDatas();
		}
	}

	/**
	 * 删除一行
	 * 
	 * @param index
	 * @throws AppException
	 * @throws DataException
	 */
	public void removeRow(int index) throws AppException {
		this.gridModel.removeRow(index);
		updateDatasWithUpdateTable();
	}

	/**
	 * 获取选中行
	 * 
	 * @return
	 */
	public int getSelectRowIndex() {
		return this.table.getSelectedRow();
	}

	/**
	 * 清除数据
	 * 
	 * @throws AppException
	 * @throws DataException
	 */
	public void clearData() throws AppException {
		this.gridModel.setRowCount(0);
		updateDatasWithUpdateTable();
	}

	/**
	 * 初始化
	 * 
	 * @throws AppException
	 * @throws DataException
	 */
	private void init() throws AppException {
		if (isShowHeade()) {
			this.heade_panel = new JPanel();
			add(this.heade_panel, "North");
		}
		if (isShowFoot()) {
			this.foot_panel = new JPanel();
			add(this.foot_panel, "South");
		}
		this.gridModel = new GridModel(getDatas(), getColumnsName());
		this.table = new JTable(this.gridModel);
		this.table.setAutoResizeMode(this.autoResize ? 4 : 0);
		for (int i = 0; i < this.gridColumns.size(); i++) {
			if (((GridColumn) this.gridColumns.get(i)).getColumnType().equals(GridColumn.COLUMNTYPE_DROPDOWN)) {
				TableColumn tc = this.table.getColumnModel().getColumn(i);
				tc.setCellEditor(new DefaultCellEditor(((GridColumn) this.gridColumns.get(i)).getJComboBox()));
			}
		}
		this.popupMenu = new JPopupMenu();
		addPopup(this.table, this.popupMenu);
		addPopup(this.scrollPane, this.popupMenu);

		this.menuItem_add = new JMenuItem("增加");
		this.menuItem_delete = new JMenuItem("删除");
		this.menuItem_clear = new JMenuItem("清空");

		if (this.showPop_add) {
			this.menuItem_add.addActionListener(this);
			this.popupMenu.add(this.menuItem_add);
		}
		if (this.showPop_remove) {
			this.menuItem_delete.addActionListener(this);
			this.popupMenu.add(menuItem_delete);
		}
		if (this.showPop_clear) {
			this.menuItem_clear.addActionListener(this);
			this.popupMenu.add(this.menuItem_clear);
		}
	}

	/**
	 * 设置完毕调用此方法
	 * 
	 * @return
	 * @throws AppException
	 * @throws DataException
	 */
	public boolean doGrid() throws AppException {
		init();
		this.scrollPane.setViewportView(this.table);
		return true;
	}

	public boolean isShowHeade() {
		return this.showHeade;
	}

	public void setShowHeade(boolean showHeade) {
		this.showHeade = showHeade;
	}

	public boolean isShowFoot() {
		return this.showFoot;
	}

	public void setShowFoot(boolean showFoot) {
		this.showFoot = showFoot;
	}

	private Object[] getColumnsName() {
		return this.gridColumns.toArray();
	}

	/**
	 * 获取数据（内部调用）
	 * 
	 * @return
	 * @throws AppException
	 * @throws DataException
	 */
	private Object[][] getDatas() throws AppException {
		Object[][] objs = new Object[this.dsData.rowCount()][this.gridColumns.size()];
		for (int i = 0; i < this.dsData.rowCount(); i++) {
			for (int j = 0; j < this.gridColumns.size(); j++) {
				GridColumn gridClumn = (GridColumn) this.gridColumns.get(j);
				String realName = gridClumn.getRealName();
				String type = gridClumn.getColumnType();
				Object value = null;
				if (realName.equals("no")) {
					objs[i][j] = Integer.valueOf(i + 1);
				} else {
					if (this.dsData.containsItem(i, realName)) {
						value = this.dsData.getObject(i, realName);
					}
					if (value == null) {
						value = gridClumn.getDefautValue();
					}
					if (type.equals(GridColumn.COLUMNTYPE_STRING)) {
						objs[i][j] = value;
					} else if (type.equals(GridColumn.COLUMNTYPE_DROPDOWN)) {
						objs[i][j] = gridClumn.getDPValue(value.toString());
					} else if (type.equals(GridColumn.COLUMNTYPE_CHECKBOX)) {
						objs[i][j] = Boolean.valueOf(Boolean.parseBoolean((String) value));
					}
				}
			}
		}
		return objs;
	}

	/**
	 * 获取数据（内部调用）
	 * 
	 * @return
	 * @throws AppException
	 * @throws DataException
	 */
	private Object[] getRowDataFromeDataObject(DataObject para) throws AppException {
		Object[] obj = new Object[this.gridColumns.size()];
		for (int i = 0; i < this.gridColumns.size(); i++) {
			GridColumn gridClumn = (GridColumn) this.gridColumns.get(i);
			String realName = gridClumn.getRealName();
			String type = gridClumn.getColumnType();
			Object value = null;
			if (realName.equals("no")) {
				obj[i] = Integer.valueOf(this.gridModel.getRowCount() + 1);
			} else {
				if (para.containsKey(realName)) {
					value = para.getObject(realName);
				}
				if (value == null) {
					value = gridClumn.getDefautValue();
				}
				if (type.equals(GridColumn.COLUMNTYPE_STRING)) {
					obj[i] = value;
				} else if (type.equals(GridColumn.COLUMNTYPE_DROPDOWN)) {
					obj[i] = gridClumn.getDPValue(value.toString());
				} else if (type.equals(GridColumn.COLUMNTYPE_CHECKBOX)) {
					obj[i] = Boolean.parseBoolean(value.toString());
				}
			}
		}
		return obj;
	}

	/**
	 * 获取是否显示行号
	 * 
	 * @return
	 */
	public boolean isShowLineNumber() {
		return this.showLineNumber;
	}

	/**
	 * 设置是否显示行号
	 * 
	 * @return
	 */
	public void setShowLineNumber(boolean showLineNumber) {
		this.showLineNumber = showLineNumber;
		if (this.showLineNumber) {
			if (!((GridColumn) this.gridColumns.get(0)).getRealName().equals("no")) {
				try {
					GridColumn gc = new GridColumn("NO.", "no");
					this.gridColumns.add(new GridColumn());
					for (int i = this.gridColumns.size() - 1; i > 0; i--) {
						this.gridColumns.set(i, (GridColumn) this.gridColumns.get(i - 1));
					}
					this.gridColumns.set(0, gc);
				} catch (AppException e) {
					e.printStackTrace();
				}
			}
		} else if (((GridColumn) this.gridColumns.get(0)).getRealName().equals("no")) {
			this.gridColumns.remove(0);
		}
	}

	/**
	 * 仅更新数据
	 * 
	 * @throws AppException
	 * @throws DataException
	 */
	private void updateDatas() throws AppException {
		DataStore values = new DataStore();
		int colCount = this.gridColumns.size();
		int rowCount = this.gridModel.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			values.addRow();
			for (int j = 0; j < colCount; j++) {
				if (!((GridColumn) this.gridColumns.get(j)).getRealName().equals("no")) {
					String type = ((GridColumn) this.gridColumns.get(j)).getColumnType();
					if (type.equals(GridColumn.COLUMNTYPE_STRING)) {
						values.put(values.rowCount() - 1, ((GridColumn) this.gridColumns.get(j)).getRealName(),
								this.gridModel.getValueAt(i, j).toString());
					} else if (type.equals(GridColumn.COLUMNTYPE_DROPDOWN)) {
						values.put(values.rowCount() - 1, ((GridColumn) this.gridColumns.get(j)).getRealName(),
								((GridColumn) this.gridColumns.get(j))
										.getDPCode(this.gridModel.getValueAt(i, j).toString()));
					} else if (type.equals(GridColumn.COLUMNTYPE_CHECKBOX)) {
						values.put(values.rowCount() - 1, ((GridColumn) this.gridColumns.get(j)).getRealName(),
								Boolean.valueOf(Boolean.parseBoolean(this.gridModel.getValueAt(i, j).toString())));
					}
				}
			}
		}
		this.dsData = values;
	}

	/**
	 * 更新数据并更新显示
	 * 
	 * @throws AppException
	 * @throws DataException
	 */
	private void updateDatasWithUpdateTable() throws AppException {
		updateDatas();

		this.gridModel.setRowCount(0);

		DataStore vds = this.dsData.clone();

		addRows(vds);
	}

	public boolean isShowPop_add() {
		return this.showPop_add;
	}

	public void setShowPop_add(boolean showPop_add) {
		this.showPop_add = showPop_add;
	}

	public boolean isShowPop_remove() {
		return this.showPop_remove;
	}

	public void setShowPop_remove(boolean showPop_remove) {
		this.showPop_remove = showPop_remove;
	}

	public boolean isShowPop_clear() {
		return this.showPop_clear;
	}

	public void setShowPop_clear(boolean showPop_clear) {
		this.showPop_clear = showPop_clear;
	}

	/**
	 * 增加item
	 * 
	 * @param item
	 */
	public void addMenuItem(JMenuItem item) {
		this.popupMenu.add(item);
	}

	/**
	 * 增加menu
	 * 
	 * @param menu
	 */
	public void addMenu(JMenu menu) {
		this.popupMenu.add(menu);
	}

	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Grid.this.selectTable(e);
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				Grid.this.selectTable(e);
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				Point mousepoint = e.getPoint();

				int index = table.rowAtPoint(mousepoint);
				if (index < 0) {
					menuItem_delete.setEnabled(false);
				} else {
					menuItem_delete.setEnabled(true);
				}

				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	private void selectTable(MouseEvent e) {
		int row = this.table.rowAtPoint(e.getPoint());
		if (row >= 0) {
			this.table.setRowSelectionInterval(row, row);
		}
	}

	public DataStore getDataStore() throws AppException {
		if (this.table.isEditing()) {
			this.table.getCellEditor().stopCellEditing();
		}
		this.table.clearSelection();
		updateDatas();
		return this.dsData;
	}

	class GridModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;

		public GridModel(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
		}

		public Class<?> getColumnClass(int columnIndex) {
			String columnType = ((GridColumn) Grid.this.gridColumns.get(columnIndex)).getColumnType();
			if (columnType.equals(GridColumn.COLUMNTYPE_CHECKBOX)) {
				return Boolean.class;
			}
			if (columnType.equals(GridColumn.COLUMNTYPE_DROPDOWN)) {
				return JComboBox.class;
			}
			if (columnType.equals(GridColumn.COLUMNTYPE_STRING)) {
				return String.class;
			}
			return super.getColumnClass(columnIndex);
		}

		public boolean isCellEditable(int row, int column) {
			return !((GridColumn) Grid.this.gridColumns.get(column)).isReadonly();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.menuItem_add)) {
			try {
				addRow(new DataObject());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(this.menuItem_delete)) {
			try {
				removeRow(getSelectRowIndex());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(this.menuItem_clear)) {
			try {
				clearData();
			} catch (AppException e1) {
				e1.printStackTrace();
			}
		}
	}

	public boolean isAutoResize() {
		return this.autoResize;
	}

	public void setAutoResize(boolean autoResize) {
		this.autoResize = autoResize;
	}
}
