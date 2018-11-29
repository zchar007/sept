package com.sept.drop.grid;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.drop.grid.columns.GridLineNumberColumn;
import com.sept.drop.grid.columns.GridSelectionModelColumn;
import com.sept.exception.AppException;
import com.sept.jui.util.TextUtil;

public class Grid extends JTable implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	/**
	 * 选择模式
	 */
	public static final int SINGLE_SELECTION = ListSelectionModel.SINGLE_SELECTION;
	public static final int SINGLE_INTERVAL_SELECTION = ListSelectionModel.SINGLE_INTERVAL_SELECTION;
	public static final int MULTIPLE_INTERVAL_SELECTION = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
	private int selection = SINGLE_SELECTION;
	private ArrayList<GridColumn> gridColumns;
	private GridModel model;
	private boolean showLineNumber = false;
	private JPopupMenu popupMenu;
	private JMenuItem menuItem_add;
	private JMenuItem menuItem_delete;
	private JMenuItem menuItem_clear;
	private JMenuItem menuItem_copy;
	private JMenuItem menuItem_paste;
	private JMenuItem menuItem_paste4Insert;

	private int selectCubColumnIndex = -1;

	public Grid() {
		super();
		this.gridColumns = new ArrayList<GridColumn>();
		this.addPopMenu();
		this.addMouseListener(this);
	}

	private void addPopMenu() {
		this.popupMenu = new JPopupMenu();
		addPopup(this, this.popupMenu);

		this.menuItem_copy = new JMenuItem("Copy");
		this.menuItem_paste = new JMenuItem("Paste");
		this.menuItem_paste4Insert = new JMenuItem("Insert");

		this.menuItem_add = new JMenuItem("Add");
		this.menuItem_delete = new JMenuItem("Delete");
		this.menuItem_clear = new JMenuItem("Clear");

		this.menuItem_copy.addActionListener(this);
		this.popupMenu.add(this.menuItem_copy);

		this.menuItem_paste.addActionListener(this);
		this.popupMenu.add(this.menuItem_paste);

		this.menuItem_paste4Insert.addActionListener(this);
		this.popupMenu.add(this.menuItem_paste4Insert);

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
					if (Grid.this.selection == SINGLE_SELECTION) {
						Grid.this.setSelectTable(e);
					} else {
						Grid.this.addSelectTable(e);
					}
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					if (Grid.this.selection == SINGLE_SELECTION) {
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
				for (int i = 0; i < indexs.length; i++) {
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
		dealModel();
	}

	@SuppressWarnings("unchecked")
	private void dealModel() throws AppException {
		model = new GridModel(this.gridColumns);
		this.setModel(model);

		DefaultTableCellRenderer cellRenderer = this.getHeadCellRenderer();
		for (int i = 0; i < this.gridColumns.size(); i++) {
			if (this.gridColumns.get(i).getComponentType().equals(JComboBox.class)) {
				TableColumn tc = this.getColumnModel().getColumn(i);
				tc.setCellEditor(new DefaultCellEditor((JComboBox<String>) this.gridColumns.get(i).getComponent()));
			}
			// i是表头的列
			TableColumn column = this.getTableHeader().getColumnModel().getColumn(i);
			column.setHeaderRenderer(cellRenderer);
		}
	}

	/**
	 * 增加一列
	 *
	 * @param gridClumn
	 * @throws AppException
	 */
	public void addColumn(GridColumn gridClumn) throws AppException {
		if (gridClumn == null) {
			return;
		}
		this.gridColumns.add(gridClumn);
		dealModel();
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
		this.model.setData(dataStore);
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
	 * 是否显示行号
	 * 
	 * @param showLineNumber
	 * @throws AppException
	 */
	public void showLineNumber(boolean showLineNumber) throws AppException {
		this.showLineNumber = showLineNumber;
		if (this.showLineNumber) {
			if (this.gridColumns.size() <= 0) {
				GridLineNumberColumn gc = new GridLineNumberColumn();
				this.gridColumns.add(gc);
				DataStore dataStore = this.getDataStore();
				this.dealModel();
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
					this.dealModel();
					this.updateData(dataStore);
				}
			}
		} else {
			if (null != this.gridColumns && this.gridColumns.size() > 0
					&& this.gridColumns.get(0).getName().equals(GridLineNumberColumn.DEFAULT_NAME)) {
				this.gridColumns.remove(0);
				DataStore dataStore = this.getDataStore();
				this.dealModel();
				this.updateData(dataStore);
			}
		}
	}

	/**
	 * 清空
	 * 
	 * @throws AppException
	 */
	public void clear() throws AppException {
		this.updateData(new DataStore());
	}

	protected DefaultTableCellRenderer getHeadCellRenderer() {

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setBackground(new Color(51, 102, 255));
		return cellRenderer;
	}

	@Override
	public void setSelectionMode(int selectionMode) {
		super.setSelectionMode(selectionMode);
		this.selection = selectionMode;
		try {
			if (Grid.SINGLE_SELECTION == selectionMode) {
				// 移除选择列
				/**
				 * 有行号无选择
				 */
				if (this.gridColumns.get(0).getName().equals(GridSelectionModelColumn.DEFAULT_NAME)) {
					this.gridColumns.remove(0);
					DataStore dataStore = this.getDataStore();
					this.dealModel();
					this.updateData(dataStore);
				} else if (this.gridColumns.get(1).getName().equals(GridSelectionModelColumn.DEFAULT_NAME)) {// 无行号无选择
					this.gridColumns.remove(1);
					DataStore dataStore = this.getDataStore();
					this.dealModel();
					this.updateData(dataStore);
				}

			} else if (Grid.SINGLE_INTERVAL_SELECTION == selectionMode) {
				// 增加选择列
				if (this.gridColumns.size() <= 0) {
					GridSelectionModelColumn gc = new GridSelectionModelColumn();
					this.gridColumns.add(gc);
					DataStore dataStore = this.getDataStore();
					this.dealModel();
					this.updateData(dataStore);
					selectCubColumnIndex = 0;
				} else {
					/**
					 * 有行号无选择
					 */
					if (this.gridColumns.get(0).getName().equals(GridLineNumberColumn.DEFAULT_NAME)
							&& !this.gridColumns.get(1).getName().equals(GridSelectionModelColumn.DEFAULT_NAME)) {
						GridSelectionModelColumn gc = new GridSelectionModelColumn();
						this.gridColumns.add(null);
						for (int i = this.gridColumns.size() - 1; i > 1; i--) {
							this.gridColumns.set(i, this.gridColumns.get(i - 1));
						}
						this.gridColumns.set(1, gc);
						DataStore dataStore = this.getDataStore();
						this.dealModel();
						this.updateData(dataStore);
						selectCubColumnIndex = 1;
					} else if (!this.gridColumns.get(0).getName().equals(GridLineNumberColumn.DEFAULT_NAME)
							&& !this.gridColumns.get(0).getName().equals(GridSelectionModelColumn.DEFAULT_NAME)) {// 无行号无选择

						GridSelectionModelColumn gc = new GridSelectionModelColumn();
						this.gridColumns.add(null);
						for (int i = this.gridColumns.size() - 1; i > 0; i--) {
							this.gridColumns.set(i, this.gridColumns.get(i - 1));
						}
						this.gridColumns.set(0, gc);
						DataStore dataStore = this.getDataStore();
						this.dealModel();
						this.updateData(dataStore);
						selectCubColumnIndex = 0;
					}
				}

			} else if (Grid.MULTIPLE_INTERVAL_SELECTION == selectionMode) {
				// 增加选择列

				// 增加选择列
				if (this.gridColumns.size() <= 0) {
					GridSelectionModelColumn gc = new GridSelectionModelColumn();
					this.gridColumns.add(gc);
					DataStore dataStore = this.getDataStore();
					this.dealModel();
					this.updateData(dataStore);
					selectCubColumnIndex = 0;
				} else {
					/**
					 * 有行号无选择
					 */
					if (this.gridColumns.get(0).getName().equals(GridLineNumberColumn.DEFAULT_NAME)
							&& !this.gridColumns.get(1).getName().equals(GridSelectionModelColumn.DEFAULT_NAME)) {
						GridSelectionModelColumn gc = new GridSelectionModelColumn();
						this.gridColumns.add(null);
						for (int i = this.gridColumns.size() - 1; i > 1; i--) {
							this.gridColumns.set(i, this.gridColumns.get(i - 1));
						}
						this.gridColumns.set(1, gc);
						DataStore dataStore = this.getDataStore();
						this.dealModel();
						this.updateData(dataStore);
						selectCubColumnIndex = 1;
					} else if (!this.gridColumns.get(0).getName().equals(GridLineNumberColumn.DEFAULT_NAME)
							&& !this.gridColumns.get(0).getName().equals(GridSelectionModelColumn.DEFAULT_NAME)) {// 无行号无选择

						GridSelectionModelColumn gc = new GridSelectionModelColumn();
						this.gridColumns.add(null);
						for (int i = this.gridColumns.size() - 1; i > 0; i--) {
							this.gridColumns.set(i, this.gridColumns.get(i - 1));
						}
						this.gridColumns.set(0, gc);
						DataStore dataStore = this.getDataStore();
						this.dealModel();
						this.updateData(dataStore);
						selectCubColumnIndex = 0;
					}
				}

			}
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setSelectionModel(ListSelectionModel newModel) {
		super.setSelectionModel(newModel);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (selection != SINGLE_SELECTION) {
			int count = this.getRowCount();
			int[] indexs = this.getSelectedRows();
			HashSet<String> hs = new HashSet<>();
			for (int i = 0; i < indexs.length; i++) {
				hs.add(indexs[i] + "");
			}
			for (int i = 0; i < count; i++) {
				if (hs.contains(i + "")) {
					this.setValueAt(true, i, this.selectCubColumnIndex);
				} else {
					this.setValueAt(false, i, this.selectCubColumnIndex);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (selection != SINGLE_SELECTION) {
			int count = this.getRowCount();
			int[] indexs = this.getSelectedRows();
			HashSet<String> hs = new HashSet<>();
			for (int i = 0; i < indexs.length; i++) {
				hs.add(indexs[i] + "");
			}
			for (int i = 0; i < count; i++) {
				if (hs.contains(i + "")) {
					this.setValueAt(true, i, this.selectCubColumnIndex);
				} else {
					this.setValueAt(false, i, this.selectCubColumnIndex);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
