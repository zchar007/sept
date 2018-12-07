package com.sept.drop.pdf.access;

import java.sql.Types;

import com.sept.datastructure.DataStore;
import com.sept.drop.pdf.DataTableColumn;
import com.sept.drop.pdf.DataTableColumns;
import com.sept.drop.pdf.IDataTableColumns;
import com.sept.exception.AppException;

public class Demo {
	public static void main(String[] args) throws AppException {
		created(100);
		AccessTable at = get("user8");
		System.out.println(at.select(" xm like 2"));
	}
	private static AccessTable get(String tableName) throws AppException {
		Access acc = Access.open("D://text3.mdb");

		return acc.getTable(tableName);
	}
	private static void created(int index) throws AppException {

		Access acc = Access.open("D://text3.mdb");
		IDataTableColumns dit = new DataTableColumns();

		DataTableColumn cname = new DataTableColumn();
		cname.setDataType(Types.VARCHAR);
		cname.setName("xm");
		cname.setShowName("姓名");
		dit.addColumn(cname);

		DataTableColumn cage = new DataTableColumn();
		cage.setDataType(Types.VARCHAR);
		cage.setName("age");
		cage.setShowName("年龄");
		dit.addColumn(cage);

		DataTableColumn curl = new DataTableColumn();
		curl.setDataType(Types.VARCHAR);
		curl.setName("url");
		curl.setShowName("邮箱");
		dit.addColumn(curl);

		DataTableColumn czz = new DataTableColumn();
		czz.setDataType(Types.VARCHAR);
		czz.setName("zz");
		czz.setShowName("家庭住址");
		dit.addColumn(czz);

		AccessTable at = (AccessTable) acc.newTable("user8", dit);
		DataStore ds = new DataStore();
		for (int i = 0; i < index; i++) {
			ds.addRow();
			ds.put(ds.rowCount()-1, "xm", "姓名"+i);
			ds.put(ds.rowCount()-1, "age", "年龄"+i);
			ds.put(ds.rowCount()-1, "url", "邮箱"+i);
			ds.put(ds.rowCount()-1, "zz", "家庭住址"+i);

		}
		at.addRows(ds);
		acc.save();
	
	}
}
