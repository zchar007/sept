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

		grid.addColumn(new GridColumn("����", "xm", GridColumn.COLUMNTYPE_STRING, "����", null, false));
		grid.addColumn(new GridColumn("����", "nl", GridColumn.COLUMNTYPE_STRING, "����", null, false));
		grid.addColumn(new GridColumn("��ͥסַ", "jtzz", GridColumn.COLUMNTYPE_STRING, "����", null, false));
		grid.addColumn(new GridColumn("�绰", "dh", GridColumn.COLUMNTYPE_STRING, "����", null, false));
		grid.addColumn(new GridColumn("�ǲ���ѧ��", "sbsxs", GridColumn.COLUMNTYPE_CHECKBOX, "true", null, true));
		grid.addColumn(new GridColumn("�꼶", "nj", GridColumn.COLUMNTYPE_DROPDOWN, "1", "1:һ�꼶,2:���꼶,3:���꼶", false));
		grid.addColumn(new GridColumn("����", "xm2", GridColumn.COLUMNTYPE_STRING, "����", null, false));
		grid.addColumn(new GridColumn("����", "nl2", GridColumn.COLUMNTYPE_STRING, "����", null, false));
		grid.addColumn(new GridColumn("��ͥסַ", "jtzz2", GridColumn.COLUMNTYPE_STRING, "����", null, false));
		grid.addColumn(new GridColumn("�绰", "dh2", GridColumn.COLUMNTYPE_STRING, "����", null, false));
		grid.addColumn(new GridColumn("�ǲ���ѧ��", "sbsxs2", GridColumn.COLUMNTYPE_CHECKBOX, "true", null, true));
		grid.addColumn(new GridColumn("�꼶", "nj2", GridColumn.COLUMNTYPE_DROPDOWN, "1", "1:һ�꼶,2:���꼶,3:���꼶", false));
		this.grid.setShowLineNumber(true);

		DataStore vds = new DataStore();
		for (int i = 0; i < 3; i++) {
			vds.addRow();
			vds.put(vds.rowCount() - 1, "xm", "����" + i);
			vds.put(vds.rowCount() - 1, "nl", 10 + i);
			vds.put(vds.rowCount() - 1, "dh", "137937" + 10 + i);
			vds.put(vds.rowCount() - 1, "jtzz", "û��סַ");
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

		btnSss = new JButton("��ȡ����");
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
