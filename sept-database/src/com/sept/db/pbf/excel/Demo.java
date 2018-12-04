package com.sept.db.pbf.excel;

import java.io.File;
import java.io.IOException;

import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.io.local.FileIOUtil;

import jxl.write.WritableSheet;

public class Demo {
	public static void main(String[] args) throws AppException, IOException {
		ExcelColumns columns = new ExcelColumns();
		columns.addColumn(0, "name", "姓名", "");
		columns.addColumn(1, "age", "年龄", "");
		columns.addColumn(2, "bm", "部门", "");
		Excel excel = new Excel();
		WritableSheet sheet1 = excel.addSheet("第一页", columns);
		WritableSheet sheet2 = excel.addSheet("第二页", columns);
		WritableSheet sheet3 = excel.addSheet("第三页", columns);
		System.out.println(sheet1.equals(excel.getSheet(0)));

		DataStore datas = new DataStore();
		for (int i = 0; i < 10; i++) {
			datas.addRow();
			datas.put(datas.rowCount() - 1, "name", "张三" + i);
			datas.put(datas.rowCount() - 1, "age", "2" + i);
			datas.put(datas.rowCount() - 1, "bm", "社" + i);
		}
		
		excel.addColumnsToSheet(0, datas);
		excel.addColumnsToSheet(1, datas);
		excel.addColumnsToSheet(2, datas);
		File f = new File("D://text.xls");
		if(f.exists()) {
			f.delete();
		}
		f.createNewFile();
		FileIOUtil.writeBytesToFile(excel.getBytes(), f);

	}

}
