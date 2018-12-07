package com.sept.drop.pbf.excel;

import java.io.ByteArrayOutputStream;

import com.sept.datastructure.DataStore;
import com.sept.datastructure.util.JSONUtil;
import com.sept.datastructure.util.XMLUtil;
import com.sept.exception.AppException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {
	/**
	 * 从json中获取EXCEL
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public static byte[] getExcelFromJSON(ExcelColumns excelColumns, String excelJsonStr) throws AppException {
		DataStore vds = JSONUtil.JsonToDataStore(excelJsonStr);
		return getExcelFromDataStore(excelColumns, vds);
	}

	/**
	 * 从xml中获取EXCEL
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public static byte[] getExcelFromXML(ExcelColumns excelColumns, String xmlStr) throws AppException {
		DataStore vds = XMLUtil.XmlToDataStore(xmlStr);
		return getExcelFromDataStore(excelColumns, vds);

	}

	/**
	 * 从DataStore中获取EXCEL
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public static byte[] getExcelFromDataStore(ExcelColumns columns, DataStore datas) throws AppException {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			WritableWorkbook book = Workbook.createWorkbook(os);
			WritableSheet sheet = book.createSheet("第一页", 0);
			String[] clumnsStr = columns.getColumns();
			String columnName, showName, defaultValue;
			Object value;
			// 写入头
			for (int i = 0; i < clumnsStr.length; i++) {
				columnName = clumnsStr[i];
				showName = columns.getShowName(columnName);
				defaultValue = columns.getDefault(columnName);
				// 列，行
				Label label = new Label(i, 0, showName);
				sheet.addCell(label);
				for (int j = 0; j < datas.rowCount(); j++) {
					try {
						value = datas.getObject(j, columnName, defaultValue);
					} catch (Exception e) {
						value = "";
					}
					if (null == value) {
						value = "";
					}
					Label labelV = new Label(i, j + 1, value.toString());
					sheet.addCell(labelV);
				}
			}

			book.write();
			book.close();

			return os.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			AppException ape = new AppException(e.getMessage());
			throw ape;

		}
	}

	/**
	 * OutputStream os = response.getOutputStream();// 取得输出流 response.reset();//
	 * 清空输出流 response.setHeader("Content-disposition", "attachment; filename="+new
	 * String("Book1".getBytes("GB2312"),"8859_1")+".xls");// 设定输出文件头
	 * response.setContentType("application/msexcel");// 定义输出类型 WritableWorkbook wwb
	 * = Workbook.createWorkbook(os); // 建立excel文件
	 * 
	 * @param args
	 * @throws AppException
	 */
	public static void main(String[] args) throws AppException {
		DataStore vds = new DataStore();
		for (int i = 0; i < 10; i++) {
			vds.addRow();
			vds.put(vds.rowCount() - 1, "xm", "李阳" + i);
			vds.put(vds.rowCount() - 1, "nl", 20 + i);
			vds.put(vds.rowCount() - 1, "tz", "10" + i);
			vds.put(vds.rowCount() - 1, "sfpl", "是");
		}

		ExcelColumns columns = new ExcelColumns();
		columns.addColumn(0, "xm", "姓名", "张超");
		columns.addColumn(1, "nl", "年龄", "18");
		columns.addColumn(2, "tz", "体重", "120");
		columns.addColumn(3, "sfpl", "是否漂亮", "是");
		for (int i = 0; i < 4; i++) {
			vds.addRow();
		}

		byte[] bytes = ExcelUtil.getExcelFromDataStore(columns, vds);
		System.out.println(bytes.length);

		String jsonStr = vds.toJSON();
		bytes = ExcelUtil.getExcelFromJSON(columns, jsonStr);
		System.out.println(bytes.length);

		String xmlStr = vds.toXML();
		bytes = ExcelUtil.getExcelFromXML(columns, xmlStr);
		System.out.println(bytes.length);

	}

}
