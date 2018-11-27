package com.sept.jui.input.lov;

import javax.swing.JTextField;

import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.jui.grid.GridColumn;
import com.sept.util.DateUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

public class LovField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public LovField() {
		this.setEditable(false);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {
					try {
						LovPanel.showLovPanel(LovField.this, getHead(), getData());
					} catch (AppException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public Date getDate() throws AppException {
		return DateUtil.formatStrToDate(this.getText());
	}

	private ArrayList<GridColumn> getHead() throws AppException {
		ArrayList<GridColumn> gridColumns = new ArrayList<>();
		gridColumns.add(new GridColumn("姓名", "xm", GridColumn.COLUMNTYPE_STRING, "张三", null, true));
		gridColumns.add(new GridColumn("年龄", "nl", GridColumn.COLUMNTYPE_STRING, "张三", null, true));
		gridColumns.add(new GridColumn("家庭住址", "jtzz", GridColumn.COLUMNTYPE_STRING, "张三", null, true));
		gridColumns.add(new GridColumn("电话", "dh", GridColumn.COLUMNTYPE_STRING, "张三", null, true));
		gridColumns.add(new GridColumn("是不是学生", "sbsxs", GridColumn.COLUMNTYPE_CHECKBOX, "false", null, true));
		gridColumns.add(new GridColumn("年级", "nj", GridColumn.COLUMNTYPE_DROPDOWN, "1", "1:一年级,2:二年级,3:三年级", true));
		gridColumns.add(new GridColumn("姓名", "xm2", GridColumn.COLUMNTYPE_STRING, "张三", null, true));
		gridColumns.add(new GridColumn("年龄", "nl2", GridColumn.COLUMNTYPE_STRING, "张三", null, true));
		gridColumns.add(new GridColumn("家庭住址", "jtzz2", GridColumn.COLUMNTYPE_STRING, "张三", null, true));
		gridColumns.add(new GridColumn("电话", "dh2", GridColumn.COLUMNTYPE_STRING, "张三", null, true));
		gridColumns.add(new GridColumn("是不是学生", "sbsxs2", GridColumn.COLUMNTYPE_CHECKBOX, "false", null, true));
		gridColumns.add(new GridColumn("年级", "nj2", GridColumn.COLUMNTYPE_DROPDOWN, "1", "1:一年级,2:二年级,3:三年级", true));
		return gridColumns;
	}

	private DataStore getData() throws AppException {
		DataStore vds = new DataStore();
		for (int i = 0; i < 3; i++) {
			vds.addRow();
			vds.put(vds.rowCount() - 1, "xm", "张三" + i);
			vds.put(vds.rowCount() - 1, "nl", 10 + i);
			vds.put(vds.rowCount() - 1, "dh", "137937" + 10 + i);
			vds.put(vds.rowCount() - 1, "jtzz", "没有住址");
		}
		return vds;
	}
}
