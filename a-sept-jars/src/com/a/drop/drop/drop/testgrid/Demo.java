package com.sept.drop.testgrid;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import com.sept.datastructure.DataStore;
import com.sept.drop.testgrid.columns.BooleanColumn;
import com.sept.drop.testgrid.columns.DropdownColumn;
import com.sept.drop.testgrid.columns.TextColumn;
import com.sept.exception.AppException;

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
					String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
					UIManager.setLookAndFeel(windows);
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
	 * @throws Exception 
	 */
	public Demo() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new Grid();

		table.addColumns(getHead());
		table.addRows(getData());
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		scrollPane.setViewportView(table);

	}

	private ArrayList<GridColumn> getHead() throws AppException {
		ArrayList<GridColumn> gridColumns = new ArrayList<>();
		gridColumns.add(new TextColumn("xm", "姓名", "", false));
		gridColumns.add(new TextColumn("nl", "年龄", "", false));
		gridColumns.add(new TextColumn("dh", "电话", "", false));
		gridColumns.add(new TextColumn("jtzz", "家庭住址", "", false));
		gridColumns.add(new BooleanColumn("sfzx", "是否住校", true, false));
		gridColumns.add(new DropdownColumn<String>("nj", "年级", "1", "1:一年级,2:二年级,3:三年级", false));

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
			vds.put(vds.rowCount() - 1, "nj", "2");
			vds.put(vds.rowCount() - 1, "sfzx", true);
		}
		return vds;
	}
}