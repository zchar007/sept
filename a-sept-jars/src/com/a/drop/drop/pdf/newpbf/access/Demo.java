package com.sept.drop.pdf.newpbf.access;

import java.sql.Types;

import com.sept.datastructure.DataStore;
import com.sept.drop.pdf.newpbf.DTColumn;
import com.sept.drop.pdf.newpbf.DTColumns;
import com.sept.drop.pdf.newpbf.IDTColumns;
import com.sept.exception.AppException;

public class Demo {
	public static void main(String[] args) throws AppException {
//		testCreated(100);
		AccessTable at = testOpen("user");
		at.setKeyValue("姓名:xm,年龄:nl,邮箱:yx,家庭住址:zz");
		System.out.println(at.select());
		System.out.println(at.select(" xm like 2"));
	}

	private static void testCreated(int index) throws AppException {
		Access acc = new Access("D://access.mdb");
		IDTColumns dit = new DTColumns();
		dit.addColumn(new DTColumn("姓名", "xm"));
		dit.addColumn(new DTColumn("年龄", "nl"));
		dit.addColumn(new DTColumn("邮箱", "yx"));
		dit.addColumn(new DTColumn("家庭住址", "zz"));

		AccessTable at = acc.newTable("user", dit);
		DataStore ds = new DataStore();
		for (int i = 0; i < index; i++) {
			ds.addRow();
			ds.put(ds.rowCount() - 1, "xm", "姓名" + i);
			ds.put(ds.rowCount() - 1, "nl", "年龄" + i);
			ds.put(ds.rowCount() - 1, "yx", "邮箱" + i);
			ds.put(ds.rowCount() - 1, "zz", "家庭住址" + i);

		}
		at.add(ds);
		acc.save();

	}

	private static AccessTable testOpen(String tableName) throws AppException {
		Access acc = Access.open("D://access.mdb");
		
		return acc.getTable(tableName);
	}

	private static void testModify() throws AppException {
	}
}
