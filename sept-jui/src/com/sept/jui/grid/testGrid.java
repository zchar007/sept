package com.sept.jui.grid;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public class testGrid extends JFrame {
	private JPanel contentPane;
	private Grid grid;
	private JButton btnSss;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testGrid frame = new testGrid();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public testGrid() throws AppException {
		setDefaultCloseOperation(3);
		setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));

		this.grid = new Grid();

		grid.addColumn(new GridColumn("姓名", "xm", GridColumn.COLUMNTYPE_STRING, "张三", null, false));
		grid.addColumn(new GridColumn("年龄", "nl", GridColumn.COLUMNTYPE_STRING, "张三", null, false));
		grid.addColumn(new GridColumn("家庭住址", "jtzz", GridColumn.COLUMNTYPE_STRING, "张三", null, false));
		grid.addColumn(new GridColumn("电话", "dh", GridColumn.COLUMNTYPE_STRING, "张三", null, false));
		grid.addColumn(new GridColumn("是不是学生", "sbsxs", GridColumn.COLUMNTYPE_CHECKBOX, "true", null, true));
		grid.addColumn(new GridColumn("年级", "nj", GridColumn.COLUMNTYPE_DROPDOWN, "1", "1:一年级,2:二年级,3:三年级", false));
		grid.addColumn(new GridColumn("姓名", "xm2", GridColumn.COLUMNTYPE_STRING, "张三", null, false));
		grid.addColumn(new GridColumn("年龄", "nl2", GridColumn.COLUMNTYPE_STRING, "张三", null, false));
		grid.addColumn(new GridColumn("家庭住址", "jtzz2", GridColumn.COLUMNTYPE_STRING, "张三", null, false));
		grid.addColumn(new GridColumn("电话", "dh2", GridColumn.COLUMNTYPE_STRING, "张三", null, false));
		grid.addColumn(new GridColumn("是不是学生", "sbsxs2", GridColumn.COLUMNTYPE_CHECKBOX, "true", null, true));
		grid.addColumn(new GridColumn("年级", "nj2", GridColumn.COLUMNTYPE_DROPDOWN, "1", "1:一年级,2:二年级,3:三年级", false));
		this.grid.setShowLineNumber(true);

		DataStore vds = new DataStore();
		for (int i = 0; i < 3; i++) {
			vds.addRow();
			vds.put(vds.rowCount() - 1, "xm", "张三" + i);
			vds.put(vds.rowCount() - 1, "nl", 10 + i);
			vds.put(vds.rowCount() - 1, "dh", "137937" + 10 + i);
			vds.put(vds.rowCount() - 1, "jtzz", "没有住址");
		}
		this.grid.addRows(vds);
		this.grid.setShowPop_add(true);

		this.grid.setShowLineNumber(true);
		this.grid.doGrid();

		this.contentPane.add(this.grid, "Center");

		JButton button = new JButton("\u589E\u52A0\u4E00\u884C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					testGrid.this.grid.addRow(new DataObject());
				} catch (AppException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.grid.add(button, "North");

		btnSss = new JButton("获取数据");
		this.btnSss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(testGrid.this.grid.getDataStore());
				} catch (AppException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.grid.add(this.btnSss, "South");
	}
}
