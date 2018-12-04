package com.sept.db.pbf.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Excel {
	private ByteArrayOutputStream os;
	private WritableWorkbook book;
	private HashMap<WritableSheet, ExcelColumns> hmSheetColumns = new HashMap<>();
	private HashMap<WritableSheet, Integer> hmSheetIndex = new HashMap<>();

	/**
	 * excelFile 读取excel文件,修改并不会导致原文件的修改，若要修改，请删除源文件然后保存同名文件
	 * 
	 * @param excelFile
	 * @throws AppException
	 */
	public Excel(File excelFile) throws AppException {
		FileInputStream fis;
		try {
			fis = new FileInputStream(excelFile);
			this.os = new ByteArrayOutputStream();
			Workbook book4read = Workbook.getWorkbook(fis);
			book = Workbook.createWorkbook(os, book4read);
		} catch (BiffException | IOException e) {
			throw new AppException(e);
		}
	}

	/**
	 * 读取excel数据流
	 * 
	 * @param excelFile
	 * @throws AppException
	 */
	public Excel(byte[] excelByte) throws AppException {
		this.os = new ByteArrayOutputStream();
		try {
			os.write(excelByte);
			os.flush();
			this.book = Workbook.createWorkbook(os);
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

	/**
	 * 创建空的excel
	 * 
	 * @throws AppException
	 */
	public Excel() throws AppException {
		this.os = new ByteArrayOutputStream();
		try {
			this.book = Workbook.createWorkbook(os);
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

	/**
	 * 增加一个sheet
	 * 
	 * @param name
	 * @return
	 */
	public WritableSheet addSheet(String name) {
		WritableSheet sheet = book.createSheet(name, book.getSheetNames().length);
		return sheet;
	}

	/**
	 * 增加一个sheet,并默首列
	 * 
	 * @param name
	 * @param columns
	 * @return
	 * @throws AppException
	 */
	public WritableSheet addSheet(String name, ExcelColumns columns) throws AppException {
		WritableSheet sheet = book.createSheet(name, book.getSheetNames().length);
		for (int i = 0; i < book.getSheetNames().length; i++) {
			System.out.print(book.getSheetNames()[i] + ",");
		}
		this.setHead(sheet, columns);
		return sheet;
	}

	/**
	 * 设置某个sheet的首列
	 * 
	 * @param index
	 * @param columns
	 * @throws AppException
	 */
	public void setHead(int index, ExcelColumns columns) throws AppException {
		WritableSheet sheet = getSheet(index);
		this.setHead(sheet, columns);
	}

	/**
	 * 设置某个sheet的首列
	 * 
	 * @param index
	 * @param columns
	 * @throws AppException
	 */
	public void setHead(WritableSheet sheet, ExcelColumns columns) throws AppException {
		try {
			hmSheetColumns.put(sheet, columns);
			this.addIndex(sheet);
			for (int i = 0; i < columns.columnCount(); i++) {
				ExcelColumn column = columns.getColumn(i);
				Label label = new Label(i, 0, column.getShowName());
				sheet.addCell(label);
			}
		} catch (WriteException e) {
			throw new AppException(e);
		}
	}

	/**
	 * 以序号获取sheet
	 * 
	 * @param index
	 * @return
	 */
	public WritableSheet getSheet(int index) {
		return book.getSheet(index);
	}

	/**
	 * 以名字获取sheet
	 * 
	 * @param name
	 * @return
	 */
	public WritableSheet getSheet(String name) {
		return book.getSheet(name);
	}

	/**
	 * 增加多行数据
	 * 
	 * @param index
	 * @param datas
	 * @throws AppException
	 */
	public void addColumnsToSheet(int index, DataStore datas) throws AppException {
		this.addColumnsToSheet(this.getSheet(index), datas);
	}

	/**
	 * 增加多行数据
	 * 
	 * @param sheet
	 * @param datas
	 * @throws AppException
	 */
	public void addColumnsToSheet(WritableSheet sheet, DataStore datas) throws AppException {
		for (int i = 0; i < datas.rowCount(); i++) {
			this.addColumnToSheet(sheet, datas.get(i));
		}
	}

	/**
	 * 增加单行数据
	 * 
	 * @param index
	 * @param datas
	 * @throws AppException
	 */
	public void addColumnToSheet(int index, DataObject datas) throws AppException {
		this.addColumnToSheet(this.getSheet(index), datas);
	}

	/**
	 * 增加单行数据
	 * 
	 * @param sheet
	 * @param datas
	 * @throws AppException
	 */
	public void addColumnToSheet(WritableSheet sheet, DataObject datas) throws AppException {
		try {
			ExcelColumns columns = this.hmSheetColumns.get(sheet);
			this.addIndex(sheet);
			for (int i = 0; i < columns.columnCount(); i++) {
				ExcelColumn column = columns.getColumn(i);
				String key = column.getName();
				String defaultValue = column.getDefaultValue();
				System.out.println(this.getSheetIndex(sheet));
				Label label = new Label(i, this.getSheetIndex(sheet), datas.getString(key, defaultValue));
				sheet.addCell(label);
			}
		} catch (WriteException e) {
			this.minIndex(sheet);
			throw new AppException(e);
		}
	}

	/**
	 * 给某个sheet增加一行
	 * 
	 * @param sheet
	 */
	private void addIndex(WritableSheet sheet) {
		if (hmSheetIndex.containsKey(sheet)) {
			Integer index = hmSheetIndex.get(sheet);
			index = null == index ? 0 : index;
			this.hmSheetIndex.put(sheet, index.intValue() + 1);
		} else {
			hmSheetIndex.put(sheet, 0);
		}

	}

	/**
	 * 给某个sheet减少一行
	 * 
	 * @param sheet
	 */
	private void minIndex(WritableSheet sheet) {
		if (hmSheetIndex.containsKey(sheet)) {
			Integer index = hmSheetIndex.get(sheet);
			index = null == index ? 0 : (index.intValue() == 0 ? 0 : (index.intValue() - 1));
			hmSheetIndex.put(sheet, index);
		} else {
			hmSheetIndex.put(sheet, 0);
		}
	}

	/**
	 * 获取某个sheet当前的行数
	 * 
	 * @param sheet
	 * @return
	 */
	private int getSheetIndex(WritableSheet sheet) {
		return hmSheetIndex.get(sheet);
	}

	/**
	 * 结束编辑
	 * 
	 * @throws AppException
	 */
	public void finsh() throws AppException {
		try {
			book.write();
			book.close();
		} catch (WriteException | IOException e) {
			throw new AppException(e);
		}
	}

	public byte[] getBytes() throws AppException {
		this.finsh();
		return os.toByteArray();
	}
}
