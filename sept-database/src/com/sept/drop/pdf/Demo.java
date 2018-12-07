package com.sept.drop.pdf;

import com.sept.datastructure.DataObject;
import com.sept.drop.pdf.excel.Excel;
import com.sept.exception.AppException;

public class Demo {
	public static void main(String[] args) throws AppException {
		
		IDataTableColumns idtc = new DataTableColumns();
		idtc.addColumn(new DataTableColumn("xm", "姓名"));
		idtc.addColumn(new DataTableColumn("nl", "年龄"));
		idtc.addColumn(new DataTableColumn("bm", "部门"));
		
		
		Excel excel = Excel.open("D://text.xls", true);
		IDataTable es = excel.getSheet(0, idtc);
		System.out.println(es.getDataStore());


		System.out.println(es.getDataStore());

		DataObject pdo = new DataObject();
		pdo.put("xm", "王五");
		pdo.put("bm", "社8");
		pdo.put("nl", "180");
		es.addRow(pdo);
		excel.save();
		System.out.println(es.getDataStore());

	}
}
