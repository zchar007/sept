package com.sept.jui.grid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.jui.grid.action.GridCellAction;
import com.sept.jui.grid.columns.BooleanColumn;
import com.sept.jui.grid.columns.ButtonColumn;
import com.sept.jui.grid.columns.ButtonsColumn;
import com.sept.jui.grid.columns.ColorColumn;
import com.sept.jui.grid.columns.DateColumn;
import com.sept.jui.grid.columns.DropdownColumn;
import com.sept.jui.grid.columns.TextColumn;
import com.sept.jui.grid.model.GridColumn;
import com.sept.jui.themes.Themes;

public class Demo {

	private JFrame frame;
	private Grid table;
	private JTable table_1;
	private JButton btnE;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Themes.setTheme(Themes.THEME_NIMBUS);
					Demo window = new Demo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 * 
	 * @throws AppException
	 */
	public Demo() throws AppException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws AppException
	 */
	private void initialize() throws AppException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new Grid();

		table.addColumns(getHead());
		table.addRows(getData());
		table.showLineNumbers(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		table.addDefaultPopMenuToComponent(scrollPane);
		scrollPane.setViewportView(table);

	}

	private ArrayList<GridColumn> getHead() throws AppException {
		ArrayList<GridColumn> gridColumns = new ArrayList<>();
		gridColumns.add(new TextColumn("xm", "姓名", "", false));
//		gridColumns.add(new TextColumn("nl", "年龄", "", false));
//		gridColumns.add(new TextColumn("dh", "电话", "", false));
//		gridColumns.add(new TextColumn("jtzz", "家庭住址", "", false));
		gridColumns.add(new DateColumn("csrq", "出生日期", "20160101", "yyyyMMddhhmmss", "yyyy-MM-dd hh:mm:ss", false));
		gridColumns.add(new ColorColumn("color", "喜欢的颜色", Color.RED, false));
		gridColumns.add(new BooleanColumn("sfzx", "是否住校", true, false));
		gridColumns.add(new DropdownColumn("nj", "年级", "1", "2:一年级,1:二年级,5:三年级", false));
		gridColumns.add(new ButtonColumn("操作", "按钮", false, new GridCellAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e, int columnIndex, int rowIndex) {
				System.out.println(e.getActionCommand());
				System.out.println(columnIndex);
				System.out.println(rowIndex);

			}

		}));
		gridColumns.add(new ButtonsColumn("操作1,操作2,操作3", "按钮", false, new GridCellAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e, int columnIndex, int rowIndex) {
				System.out.println(e.getActionCommand());
				System.out.println(columnIndex);
				System.out.println(rowIndex);

			}

		}));

		return gridColumns;
	}

	private DataStore getData() throws AppException {
		DataStore vds = new DataStore();
		for (int i = 0; i < 5; i++) {
			vds.addRow();
			vds.put(vds.rowCount() - 1, "xm", "张三" + i);
			vds.put(vds.rowCount() - 1, "nl", 10 + i);
			vds.put(vds.rowCount() - 1, "dh", "137937" + 10 + i);
			vds.put(vds.rowCount() - 1, "jtzz", "没有住址");
			vds.put(vds.rowCount() - 1, "nj", "5");
			vds.put(vds.rowCount() - 1, "sfzx", true);
			vds.put(vds.rowCount() - 1, "color", "0xFF0000FF");
		}
		return vds;
	}
}