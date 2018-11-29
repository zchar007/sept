package com.sept.jui.grid;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.jui.grid.columns.GridLineNumberColumn;
import com.sept.jui.grid.columns.GridSelectionColumn;
import com.sept.jui.grid.model.GridCellEditor;
import com.sept.jui.grid.model.GridColumn;
import com.sept.jui.util.TextUtil;

public class Grid extends JTable implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final int SINGLE_SELECTION = ListSelectionModel.SINGLE_SELECTION;
	public static final int SINGLE_INTERVAL_SELECTION = ListSelectionModel.SINGLE_INTERVAL_SELECTION;
	public static final int MULTIPLE_INTERVAL_SELECTION = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
	private int selectionMode = SINGLE_SELECTION;
	private boolean showLineNumbers = false;

	private ArrayList<GridColumn> gridColumns;
	private GridModel model;
	private int selectCubColumnIndex = -1;

	private JPopupMenu popupMenu;
	private JMenuItem menuItem_add;
	private JMenuItem menuItem_delete;
	private JMenuItem menuItem_clear;
	private JMenuItem menuItem_copy;
	private JMenuItem menuItem_paste;
	private JMenuItem menuItem_paste4Insert;
	private JMenuItem menuItem_insertEmpty;

	public Grid() {
		this.setRowHeight(25);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		gridColumns = new ArrayList<>();
		this.addPopMenu();
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Grid.this.selectionMode != SINGLE_SELECTION) {
					int count = Grid.this.getRowCount();
					int[] indexs = Grid.this.getSelectedRows();
					HashSet<String> hs = new HashSet<>();
					for (int i = 0; i < indexs.length; i++) {
						hs.add(indexs[i] + "");
					}
					for (int i = 0; i < count; i++) {
						if (hs.contains(i + "")) {
							Grid.this.setValueAt(true, i, Grid.this.selectCubColumnIndex);
						} else {
							Grid.this.setValueAt(false, i, Grid.this.selectCubColumnIndex);
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (Grid.this.selectionMode != SINGLE_SELECTION) {
					int count = Grid.this.getRowCount();
					int[] indexs = Grid.this.getSelectedRows();
					HashSet<String> hs = new HashSet<>();
					for (int i = 0; i < indexs.length; i++) {
						hs.add(indexs[i] + "");
					}
					for (int i = 0; i < count; i++) {
						if (hs.contains(i + "")) {
							Grid.this.setValueAt(true, i, Grid.this.selectCubColumnIndex);
						} else {
							Grid.this.setValueAt(false, i, Grid.this.selectCubColumnIndex);
						}
					}
				}
			}
		});
	}

	public void addColumn(GridColumn column) throws AppException {
		this.gridColumns.add(column);
		this.setModel();
	}

	/**
	 * 增加多列
	 * 
	 * @param gridClumns
	 * @throws AppException
	 */
	public void addColumns(ArrayList<GridColumn> gridClumns) throws AppException {
		for (int i = 0; i < gridClumns.size(); i++) {
			if (gridClumns.get(i) == null) {
				continue;
			}
			this.gridColumns.add(gridClumns.get(i));
		}
		this.setModel();
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
		if (this.model == null) {
			return;
		}
		this.model.addRow(para);
	}

	/**
	 * 插入
	 * 
	 * @param para
	 * @throws AppException
	 * @throws DataException
	 */
	public void insertRow(int row, DataObject para) throws AppException {
		if (para == null) {
			return;
		}
		if (this.model == null) {
			return;
		}
		this.model.insertRow(row, para);
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
		if (this.model == null) {
			return;
		}
		this.model.addRows(para);
	}

	/**
	 * 插入
	 * 
	 * @param para
	 * @throws AppException
	 * @throws DataException
	 */
	public void insertRows(int row, DataStore para) throws AppException {
		if (para == null) {
			return;
		}
		if (this.model == null) {
			return;
		}
		this.model.insertRows(row, para);
	}

	/**
	 * 删除一行
	 * 
	 * @param index
	 * @throws AppException
	 * @throws DataException
	 */
	public void removeRow(int index) throws AppException {
		this.model.removeRow(index);
	}

	/**
	 * 更新数据，覆盖原数据
	 * 
	 * @param dataStore
	 * @throws AppException
	 */
	public void updateData(DataStore dataStore) throws AppException {
		this.model.updateData(dataStore);
	}

	/**
	 * 设置是否可编辑（全局）
	 * 
	 * @param editAble
	 */
	public void setEditAble(boolean editAble) {
		this.model.setEditAble(editAble);
	}

	/**
	 * 是否可编辑（全局）
	 * 
	 * @return
	 */
	public boolean isEditAble() {
		return this.model.isEditAble();
	}

	/**
	 * 获取数据
	 * 
	 * @return
	 * @throws AppException
	 */
	public DataStore getDataStore() throws AppException {
		if (null == this.model) {
			return new DataStore();
		}
		return this.model.getDataStore();
	}

	public DataObject getRowData(int rowIndex) throws AppException {
		return this.model.getRowData(rowIndex);
	}

	public DataStore getSelectData() throws AppException {
		DataStore ds = new DataStore();
		int[] indexs = this.getSelectedRows();
		for (int i = 0; i < indexs.length; i++) {
			ds.addRow(this.getRowData(indexs[i]));
		}
		return ds;
	}

	/**
	 * 设置模型
	 * 
	 * @throws AppException
	 */
	private void setModel() throws AppException {
		model = new GridModel(this.gridColumns);
		this.setModel(model);
		TableColumnModel columnModel = this.getColumnModel();
		for (int i = 0; i < gridColumns.size(); i++) {
			columnModel.getColumn(i).setCellRenderer((TableCellRenderer) gridColumns.get(i));
			columnModel.getColumn(i).setCellEditor(new GridCellEditor(gridColumns.get(i)));
		}
	}

	/**
	 * 显示行号
	 * 
	 * @param showLineNumbers
	 * @throws AppException
	 */
	public void showLineNumbers(boolean showLineNumbers) throws AppException {
		this.showLineNumbers = showLineNumbers;
		if (this.showLineNumbers) {
			if (this.gridColumns.size() <= 0) {
				GridLineNumberColumn gc = new GridLineNumberColumn();
				this.gridColumns.add(gc);
				DataStore dataStore = this.getDataStore();
				this.setModel();
				this.updateData(dataStore);

			} else {
				if (!this.gridColumns.get(0).getName().equals(GridLineNumberColumn.DEFAULT_NAME)) {
					GridLineNumberColumn gc = new GridLineNumberColumn();
					this.gridColumns.add(null);
					for (int i = this.gridColumns.size() - 1; i > 0; i--) {
						this.gridColumns.set(i, this.gridColumns.get(i - 1));
					}
					this.gridColumns.set(0, gc);
					DataStore dataStore = this.getDataStore();
					this.setModel();
					this.updateData(dataStore);
				}
			}
		} else {
			if (null != this.gridColumns && this.gridColumns.size() > 0
					&& this.gridColumns.get(0).getName().equals(GridLineNumberColumn.DEFAULT_NAME)) {
				this.gridColumns.remove(0);
				DataStore dataStore = this.getDataStore();
				this.setModel();
				this.updateData(dataStore);
			}
		}
	}

	@Override
	public void setSelectionMode(int selectionMode) {
		super.setSelectionMode(selectionMode);
		this.selectionMode = selectionMode;
		try {
			if (Grid.SINGLE_SELECTION == selectionMode) {
				// 移除选择列
				/**
				 * 有行号无选择
				 */
				if (this.gridColumns.get(0).getName().equals(GridSelectionColumn.DEFAULT_NAME)) {
					this.gridColumns.remove(0);
					DataStore dataStore = this.getDataStore();
					this.setModel();
					this.updateData(dataStore);
				} else if (this.gridColumns.get(1).getName().equals(GridSelectionColumn.DEFAULT_NAME)) {// 无行号无选择
					this.gridColumns.remove(1);
					DataStore dataStore = this.getDataStore();
					this.setModel();
					this.updateData(dataStore);
				}

			} else if (Grid.SINGLE_INTERVAL_SELECTION == selectionMode) {
				// 增加选择列
				if (this.gridColumns.size() <= 0) {
					GridSelectionColumn gc = new GridSelectionColumn();
					this.gridColumns.add(gc);
					DataStore dataStore = this.getDataStore();
					this.setModel();
					this.updateData(dataStore);
					setSelectCubColumnIndex(0);
				} else {
					/**
					 * 有行号无选择
					 */
					if (this.gridColumns.get(0).getName().equals(GridLineNumberColumn.DEFAULT_NAME)
							&& !this.gridColumns.get(1).getName().equals(GridSelectionColumn.DEFAULT_NAME)) {
						GridSelectionColumn gc = new GridSelectionColumn();
						this.gridColumns.add(null);
						for (int i = this.gridColumns.size() - 1; i > 1; i--) {
							this.gridColumns.set(i, this.gridColumns.get(i - 1));
						}
						this.gridColumns.set(1, gc);
						DataStore dataStore = this.getDataStore();
						this.setModel();
						this.updateData(dataStore);
						setSelectCubColumnIndex(1);
					} else if (!this.gridColumns.get(0).getName().equals(GridLineNumberColumn.DEFAULT_NAME)
							&& !this.gridColumns.get(0).getName().equals(GridSelectionColumn.DEFAULT_NAME)) {// 无行号无选择

						GridSelectionColumn gc = new GridSelectionColumn();
						this.gridColumns.add(null);
						for (int i = this.gridColumns.size() - 1; i > 0; i--) {
							this.gridColumns.set(i, this.gridColumns.get(i - 1));
						}
						this.gridColumns.set(0, gc);
						DataStore dataStore = this.getDataStore();
						this.setModel();
						this.updateData(dataStore);
						setSelectCubColumnIndex(0);
					}
				}

			} else if (Grid.MULTIPLE_INTERVAL_SELECTION == selectionMode) {
				// 增加选择列

				// 增加选择列
				if (this.gridColumns.size() <= 0) {
					GridSelectionColumn gc = new GridSelectionColumn();
					this.gridColumns.add(gc);
					DataStore dataStore = this.getDataStore();
					this.setModel();
					this.updateData(dataStore);
					setSelectCubColumnIndex(0);
				} else {
					/**
					 * 有行号无选择
					 */
					if (this.gridColumns.get(0).getName().equals(GridLineNumberColumn.DEFAULT_NAME)
							&& !this.gridColumns.get(1).getName().equals(GridSelectionColumn.DEFAULT_NAME)) {
						GridSelectionColumn gc = new GridSelectionColumn();
						this.gridColumns.add(null);
						for (int i = this.gridColumns.size() - 1; i > 1; i--) {
							this.gridColumns.set(i, this.gridColumns.get(i - 1));
						}
						this.gridColumns.set(1, gc);
						DataStore dataStore = this.getDataStore();
						this.setModel();
						this.updateData(dataStore);
						setSelectCubColumnIndex(1);
					} else if (!this.gridColumns.get(0).getName().equals(GridLineNumberColumn.DEFAULT_NAME)
							&& !this.gridColumns.get(0).getName().equals(GridSelectionColumn.DEFAULT_NAME)) {// 无行号无选择

						GridSelectionColumn gc = new GridSelectionColumn();
						this.gridColumns.add(null);
						for (int i = this.gridColumns.size() - 1; i > 0; i--) {
							this.gridColumns.set(i, this.gridColumns.get(i - 1));
						}
						this.gridColumns.set(0, gc);
						DataStore dataStore = this.getDataStore();
						this.setModel();
						this.updateData(dataStore);
						setSelectCubColumnIndex(0);
					}
				}

			}
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	private void setSelectCubColumnIndex(int selectCubColumnIndex) {
		this.selectCubColumnIndex = selectCubColumnIndex;
	}

	public int getSelection() {
		return selectionMode;
	}

	/**
	 * 清空
	 * 
	 * @throws AppException
	 */
	public void clear() throws AppException {
		this.updateData(new DataStore());
	}

	// 右键菜单
	private void addPopMenu() {
		this.popupMenu = new JPopupMenu();
		addPopup(this, this.popupMenu);

		this.menuItem_copy = new JMenuItem("Copy");
		this.menuItem_paste = new JMenuItem("Paste");
		this.menuItem_paste4Insert = new JMenuItem("Insert");
		this.menuItem_insertEmpty = new JMenuItem("Insert Empty");

		this.menuItem_add = new JMenuItem("Add");
		this.menuItem_delete = new JMenuItem("Delete");
		this.menuItem_clear = new JMenuItem("Clear");

		this.menuItem_copy.addActionListener(this);
		this.popupMenu.add(this.menuItem_copy);

		this.menuItem_paste.addActionListener(this);
		this.popupMenu.add(this.menuItem_paste);

		this.menuItem_paste4Insert.addActionListener(this);
		this.popupMenu.add(this.menuItem_paste4Insert);

		this.menuItem_insertEmpty.addActionListener(this);
		this.popupMenu.add(this.menuItem_insertEmpty);

		this.menuItem_add.addActionListener(this);
		this.popupMenu.add(this.menuItem_add);

		this.menuItem_delete.addActionListener(this);
		this.popupMenu.add(menuItem_delete);

		this.menuItem_clear.addActionListener(this);
		this.popupMenu.add(this.menuItem_clear);

	}

	public void addDefaultPopMenuToComponent(Component component) {
		this.addPopup(component, this.popupMenu);
	}

	public void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					if (Grid.this.selectionMode == SINGLE_SELECTION) {
						Grid.this.setSelectTable(e);
					} else {
						Grid.this.addSelectTable(e);
					}
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					if (Grid.this.selectionMode == SINGLE_SELECTION) {
						Grid.this.setSelectTable(e);
					} else {
						Grid.this.addSelectTable(e);
					}
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				if (!isEditAble()) {
					return;
				}
				Point mousepoint = e.getPoint();

				int index = Grid.this.rowAtPoint(mousepoint);
				if (index < 0) {
					menuItem_delete.setEnabled(false);
				} else {
					menuItem_delete.setEnabled(true);
				}

				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	@Override
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
				int[] indexs = this.getSelectedRows();
				for (int i = indexs.length - 1; i >= 0; i--) {
					this.removeRow(indexs[i]);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(this.menuItem_clear)) {
			try {
				this.clear();
			} catch (AppException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(this.menuItem_copy)) {
			try {
				DataStore ds = this.getSelectData();
				TextUtil.sendStringToClipboard(ds.toJSON());
			} catch (AppException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(this.menuItem_paste4Insert)) {
			String text = TextUtil.getStringFromClipboard();
			try {
				DataStore ds = DataStore.parseFromJSON(text);
				this.insertRows(this.getSelectedRow(), ds);
			} catch (AppException e1) {
				DataStore ds = new DataStore();
				ds.addRow();
				try {
					this.insertRows(this.getSelectedRow(), ds);
				} catch (AppException e2) {
					e2.printStackTrace();
				}
			}
		}
		if (e.getSource().equals(this.menuItem_insertEmpty)) {
			DataStore ds = new DataStore();
			ds.addRow();
			try {
				this.insertRows(this.getSelectedRow(), ds);
			} catch (AppException e2) {
				e2.printStackTrace();
			}
		}
		if (e.getSource().equals(this.menuItem_paste)) {
			String text = TextUtil.getStringFromClipboard();
			try {
				DataStore ds = DataStore.parseFromJSON(text);
				this.addRows(ds);
			} catch (AppException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void setSelectTable(MouseEvent e) {
		int row = this.rowAtPoint(e.getPoint());
		if (row >= 0) {
			this.setRowSelectionInterval(row, row);
		}
	}

	private void addSelectTable(MouseEvent e) {
		int row = this.rowAtPoint(e.getPoint());
		if (row >= 0) {
			this.addRowSelectionInterval(row, row);
		}
	}

}
