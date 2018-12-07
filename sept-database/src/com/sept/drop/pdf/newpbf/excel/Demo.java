package com.sept.drop.pdf.newpbf.excel;

import com.sept.datastructure.DataObject;
import com.sept.drop.pdf.newpbf.DTColumn;
import com.sept.drop.pdf.newpbf.DTColumns;
import com.sept.drop.pdf.newpbf.IDTColumns;
import com.sept.exception.AppException;

public class Demo {
	public static void main(String[] args) throws AppException {

		IDTColumns idtc = new DTColumns();
		idtc.addColumn(new DTColumn("xm", "姓名"));
		idtc.addColumn(new DTColumn("nl", "年龄"));
		idtc.addColumn(new DTColumn("bm", "部门"));
		Excel excel = new Excel("D://excel.xls");
		//Excel excel = Excel.open("D://excel.xls", true);
		ExcelSheet es = excel.getSheet(0, idtc);
		System.out.println(excel.getDataStore(0));

		DataObject pdo = new DataObject();
		pdo.put("xm", "王五");
		pdo.put("bm", "社8");
		pdo.put("nl", "180");
		es.add(pdo);
		excel.save();
		System.out.println(excel.getDataStore(0));

	}
}
