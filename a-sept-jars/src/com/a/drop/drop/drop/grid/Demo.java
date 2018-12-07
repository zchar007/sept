package com.sept.drop.grid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import com.sept.datastructure.DataStore;
import com.sept.drop.grid.columns.CheckboxColumn;
import com.sept.drop.grid.columns.DropdownColumn;
import com.sept.drop.grid.columns.TextColumn;
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

		table = new Grid() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected DefaultTableCellRenderer getHeadCellRenderer() {
				DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
				cellRenderer.setBackground(Color.LIGHT_GRAY);
				cellRenderer.setFont(new Font("宋体", Font.BOLD, 40));
				cellRenderer.setBorder(BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
				cellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

				return cellRenderer;
			}
		};

		table.showLineNumber(true);
		table.addColumns(getHead());
		table.addRows(getData());
		table.setEditAble(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		scrollPane.setViewportView(table);
		table.addDefaultPopMenuToComponent(scrollPane);

		btnE = new JButton("获取数据");
		btnE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(table.getDataStore().toJSON());
				} catch (AppException e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnE, BorderLayout.NORTH);

		btnNewButton = new JButton("获取选中行");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indexs = table.getSelectedRows();
				for (int i = 0; i < indexs.length; i++) {
					System.out.println(indexs[i] + ",");
				}
				System.out.println();
			}
		});
		frame.getContentPane().add(btnNewButton, BorderLayout.SOUTH);
	}

	private ArrayList<GridColumn> getHead() throws AppException {
		ArrayList<GridColumn> gridColumns = new ArrayList<>();
		gridColumns.add(new TextColumn("xm", "姓名", "", false));
		gridColumns.add(new TextColumn("nl", "年龄", "", false));
		gridColumns.add(new TextColumn("dh", "电话", "", false));
		gridColumns.add(new TextColumn("jtzz", "家庭住址", "", false));
		gridColumns.add(new CheckboxColumn("sfzx", "住校", true, false));
		gridColumns.add(new DropdownColumn("nj", "年级", "1", "1:一年级,2:二年级,3:三年级", false));
		
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
			vds.put(vds.rowCount() - 1, "sfzx", false);

		}
		return vds;
	}
}