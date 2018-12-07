package com.sept.datastructure.excel.newexcel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.io.local.FileIOTool;

/**
 * HSSF － 提供读写Microsoft Excel XLS格式档案的功能。
 * 
 * XSSF － 提供读写Microsoft Excel OOXML XLSX格式档案的功能。
 * 
 * 
 * @author zhangchao_lc
 *
 */
public class ExcelUtil {
	public static void main(String[] args) throws AppException {

		DataStore ds = new DataStore();
		for (int i = 0; i < 100; i++) {
			ds.addRow();
			ds.put(ds.rowCount() - 1, "xm", "姓名" + i);
			ds.put(ds.rowCount() - 1, "nl", "年龄" + i);
			ds.put(ds.rowCount() - 1, "yx", "邮箱" + i);
			ds.put(ds.rowCount() - 1, "zz", "家庭住址" + i);
		}

		Workbook book = ExcelUtil.createExcel2010("第一页", "姓名:xm,年龄:nl,邮箱:yx,家庭住址:zz", ds);
		ExcelUtil.addSheetToExcel(book, "第二页", "姓名:xm,年龄:nl,邮箱:yx,家庭住址:zz", ds);

		byte[] bt = ExcelUtil.getBytes(book);

		book = ExcelUtil.openExcel(bt);
		bt = ExcelUtil.getBytes(book);
		FileIOTool.writeBytesToFile(bt, new File("D://poi2.xlsx"));

//		Workbook book = openExcel(new File("D://poi.xlsx"));
//		Sheet sheet = book.getSheetAt(0);
//		DataStore ds = ExcelUtil.getDataFromSheet(sheet, "姓名:xm,邮箱:yx,家庭住址:zz");
//		System.out.println(ds.rowCount());
//		System.out.println(ds.get(3));
	}

	/**
	 * 获取workbook
	 * 
	 * @param file
	 * @return
	 * @throws AppException
	 */
	public static final Workbook openExcel(File file) throws AppException {
		if (!isExcel(file)) {
			throw new AppException("传入文件[" + file.getAbsolutePath() + "]不是Excel文件！");
		}
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			if (file.getName().endsWith(".xls")) {
				return new HSSFWorkbook(is);
			} else {
				return new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			throw new AppException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 根据字节流构建workbook
	 * 
	 * @param bt
	 * @return
	 * @throws AppException
	 */
	public static final Workbook openExcel(byte[] bt) throws AppException {
		InputStream is = null;
		try {

			try {
				is = new ByteArrayInputStream(bt);
				return new HSSFWorkbook(is);
			} catch (Exception e) {
				is = new ByteArrayInputStream(bt);
				return new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			throw new AppException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 从sheet中获取DataStore
	 * 
	 * @param sheet
	 * @param columns
	 * @return
	 * @throws AppException
	 */
	public static final DataStore getDataFromSheet(Sheet sheet, String columns) throws AppException {
		LinkedHashMap<String, String> kv_t = getHeadMap(columns);
		LinkedHashMap<String, String> kv = new LinkedHashMap<>();
		// 调整数据
		Row row0 = sheet.getRow(0);

		int columnCount = row0.getPhysicalNumberOfCells();
		int rowCount = sheet.getPhysicalNumberOfRows();

		for (int i = 0; i < columnCount; i++) {
			String value = row0.getCell(i).getStringCellValue();
			if (kv_t.containsKey(value)) {
				kv.put(value, kv_t.get(value));
			} else {
				kv.put(value, value);
			}
		}
		DataStore ds = new DataStore();
		for (int i = 1; i < rowCount; i++) {
			ds.addRow();
			Row row_i = sheet.getRow(i);
			int k = 0;
			for (String value : kv.values()) {
				ds.put(ds.rowCount() - 1, value, row_i.getCell(k++).getStringCellValue());
			}
		}

		return ds;
	}

	/**
	 * 获取2010年前xls后缀的文件
	 * 
	 * @param sheetName
	 * @param columns
	 * @param datas
	 * @return
	 * @throws AppException
	 */
	public static final HSSFWorkbook createExcel(String sheetName, String columns, DataStore datas)
			throws AppException {
		return (HSSFWorkbook) createExcel(sheetName, columns, datas, false);
	}

	/**
	 * 床架2010以后的xlsx后缀的excel文件
	 * 
	 * @param sheetName
	 * @param columns
	 * @param datas
	 * @return
	 * @throws AppException
	 */
	public static final XSSFWorkbook createExcel2010(String sheetName, String columns, DataStore datas)
			throws AppException {
		return (XSSFWorkbook) createExcel(sheetName, columns, datas, true);
	}

	/**
	 * 创建一个workbook并创建一个sheet页
	 * 
	 * @param sheetName
	 * @param columns
	 * @param datas
	 * @return
	 * @throws AppException
	 */
	public static final Workbook createExcel(String sheetName, String columns, DataStore datas, boolean xlsxAble)
			throws AppException {
		Workbook book = null;
		Sheet sheet = null;
		if (xlsxAble) {
			book = new XSSFWorkbook();
		} else {
			book = new HSSFWorkbook();
		}
		sheet = book.createSheet(sheetName);

		writeDataToSheet(sheet, columns, datas);
		return book;
	}

	/**
	 * 为book增加sheet并写入数据
	 * 
	 * @param book
	 * @param sheetName
	 * @param columns
	 * @param datas
	 * @return
	 * @throws AppException
	 */
	public static final Sheet addSheetToExcel(Workbook book, String sheetName, String columns, DataStore datas)
			throws AppException {
		Sheet sheet = book.createSheet(sheetName);
		writeDataToSheet(sheet, columns, datas);
		return sheet;
	}

	/**
	 * 数据写入sheet
	 * 
	 * @param sheet
	 * @param columns
	 * @param datas
	 * @throws AppException
	 */
	public static final void writeDataToSheet(Sheet sheet, String columns, DataStore datas) throws AppException {
		LinkedHashMap<String, String> kv = getHeadMap(columns);
		Row row = sheet.createRow(0);
		int i = 0;
		// 写头
		for (String key : kv.keySet()) {
			Cell cell = row.createCell(i++);
			cell.setCellValue(key);
		}
		// 写数据
		for (int j = 0; j < datas.rowCount(); j++) {
			i = 0;
			DataObject pdo = datas.get(j);
			Row dataRow = sheet.createRow(j + 1);

			for (String key : kv.keySet()) {
				Cell cell = dataRow.createCell(i++);
				cell.setCellValue(pdo.getString(kv.get(key), ""));
			}
		}
	}

	/**
	 * 获取excel的字节流
	 * 
	 * @param book
	 * @return
	 * @throws AppException
	 */
	public static final byte[] getBytes(Workbook book) throws AppException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] bt = null;
		try {
			book.write(os);
			bt = os.toByteArray();
		} catch (IOException e) {
			throw new AppException(e);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
			}
		}
		return bt;
	}

	/**
	 * 获取key_value map
	 * 
	 * @param columns
	 * @return
	 */
	public static final LinkedHashMap<String, String> getHeadMap(String columns) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
		String[] keys_values = columns.split(",");
		for (int i = 0; i < keys_values.length; i++) {
			String[] key_value = keys_values[i].split(":");
			hashMap.put(key_value[0], key_value[1]);
		}
		return hashMap;
	}

	/**
	 * 检查是不是excel文件
	 * 
	 * @param file
	 * @return
	 */
	public static final boolean isExcel(File file) {
		return file.isFile() && (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx"));
	}
}
