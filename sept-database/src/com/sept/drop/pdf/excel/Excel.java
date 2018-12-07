package com.sept.drop.pdf.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.sept.drop.pdf.AbstractDataSpace;
import com.sept.drop.pdf.IDataTable;
import com.sept.drop.pdf.IDataTableColumn;
import com.sept.drop.pdf.IDataTableColumns;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.io.local.FileIOTool;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Excel extends AbstractDataSpace {
	private ByteArrayOutputStream os;
	private WritableWorkbook book;
	private HashMap<WritableSheet, ExcelSheet> hmSheets;
	private boolean writeAble = true;// 默认可写入的

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
		this.hmTables = new LinkedHashMap<>();
		this.hmSheets = new HashMap<>();
	}

	/**
	 * excelFile 读取excel文件,修改并不会导致原文件的修改，若要修改，请删除源文件然后保存同名文件
	 * 
	 * @param excelFile
	 * @throws AppException
	 */
	public Excel(String excelUrl) throws AppException {
		this(new File(excelUrl));
	}

	/**
	 * excelFile 读取excel文件,修改并不会导致原文件的修改，若要修改，请删除源文件然后保存同名文件
	 * 
	 * @param excelFile
	 * @throws AppException
	 */
	public Excel(File excelFile) throws AppException {
		if (excelFile.exists()) {
			throw new AppException("Excel文件[" + excelFile.getAbsolutePath() + "]已存在！");
		}
		FileInputStream fis;
		try {
			this.setUrl(excelFile.getAbsolutePath());
			fis = new FileInputStream(excelFile);
			this.os = new ByteArrayOutputStream();
			Workbook book4read = Workbook.getWorkbook(fis);
			book = Workbook.createWorkbook(os, book4read);
			this.hmTables = new LinkedHashMap<>();
			this.hmSheets = new HashMap<>();
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
			this.hmTables = new LinkedHashMap<>();
			this.hmSheets = new HashMap<>();
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

	/**
	 * 打开一个表空间
	 * 
	 * @param url
	 * @return
	 * @throws AppException
	 */
	public static final Excel open(String fileUrl, boolean writeAble) throws AppException {
		return open(new File(fileUrl), writeAble);
	}

	/**
	 * 打开一个表空间
	 * 
	 * @param url
	 * @return
	 * @throws AppException
	 */
	public static final Excel open(File file, boolean writeAble) throws AppException {
		try {
			if (!file.isFile()) {
				throw new AppException("url不是有效的文件！");
			}
			if (!file.exists()) {
				throw new AppException("DB文件不存在！");
			}
			Excel excel = new Excel(file, writeAble);
			return excel;
		} catch (Exception e) {
			if (!(e instanceof AppException)) {
				e.printStackTrace();
				LogHandler.fatal(e.getMessage(), e);
			} else {
				LogHandler.error(e.getMessage(), e);
				throw (AppException) e;
			}
			throw new AppException(e);
		}
	}

	private Excel(File excelFile, boolean writeAble) throws AppException {
		this.writeAble = writeAble;
		FileInputStream fis;
		try {
			this.setUrl(excelFile.getAbsolutePath());
			fis = new FileInputStream(excelFile);
			this.os = new ByteArrayOutputStream();
			Workbook book4read = Workbook.getWorkbook(fis);
			book = Workbook.createWorkbook(os, book4read);
			this.hmTables = new LinkedHashMap<>();
			this.hmSheets = new HashMap<>();
		} catch (BiffException | IOException e) {
			throw new AppException(e);
		}
	}

	public ExcelSheet addSheet(String sheetName, IDataTableColumns ditColumns) throws AppException {
		if (!writeAble) {
			throw new AppException("当前excel不可编辑！");
		}
		WritableSheet sheet = book.createSheet(sheetName, book.getSheetNames().length);
		this.dealSheetHead(sheet, ditColumns);
		ExcelSheet es = new ExcelSheet(sheet, ditColumns, writeAble);
		this.hmTables.put(sheetName, es);
		this.hmSheets.put(sheet, es);
		return es;
	}

	public ExcelSheet getSheet(String sheetName, IDataTableColumns ditColumns) throws AppException {
		if (this.hmTables.containsKey(sheetName)) {
			return (ExcelSheet) this.hmTables.get(sheetName);
		}
		if (null == ditColumns) {
			throw new AppException("当前缓存中不存在此sheet,必须传入列定义获取excel中sheet!");
		}
		WritableSheet sheet = book.getSheet(sheetName);
		if (null == sheet) {
			throw new AppException("Excel中不存在Sheet表[" + sheetName + "]");
		}

		ExcelSheet es = new ExcelSheet(sheet, ditColumns, writeAble);
		this.hmTables.put(sheetName, es);

		return es;
	}

	public ExcelSheet getSheet(int index, IDataTableColumns ditColumns) throws AppException {
		WritableSheet wsheet = book.getSheet(index);
		if (this.hmSheets.containsKey(wsheet)) {
			return this.hmSheets.get(wsheet);
		}
		if (null == ditColumns) {
			throw new AppException("当前缓存中不存在此sheet,必须传入列定义获取excel中sheet!");
		}
		ExcelSheet es = new ExcelSheet(wsheet, ditColumns, writeAble);
		this.hmTables.put(wsheet.getName(), es);
		this.hmSheets.put(wsheet, es);
		return es;
	}

	@Override
	public IDataTable newTable(String tableName, IDataTableColumns ditColumns) throws AppException {
		return this.addSheet(tableName, ditColumns);
	}

	@Override
	public IDataTable getTable(String tableName, IDataTableColumns ditColumns) throws AppException {
		return this.getSheet(tableName, ditColumns);
	}

	@Override
	public void save() throws AppException {
		if (!writeAble) {
			throw new AppException("当前excel不可编辑！");
		}
		if (null == this.getUrl() || this.getUrl().trim().isEmpty()) {
			throw new AppException("url为空，不可保存！");
		}

		File file = new File(this.getUrl());
		if (file.exists()) {
			file.delete();
		}
		for (IDataTable at : this.hmTables.values()) {
			at.ready();
		}
		try {
			file.createNewFile();
			FileIOTool.writeBytesToFile(this.getBytes(), file);
		} catch (IOException e) {
			throw new AppException(e);
		}

	}

	public byte[] getBytes() throws AppException {
		this.finsh();
		return os.toByteArray();
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

	private void dealSheetHead(WritableSheet sheet, IDataTableColumns ditColumns) throws AppException {
		try {
			for (int i = 0; i < ditColumns.columnCount(); i++) {
				IDataTableColumn column = ditColumns.getColumn(i);
				Label label = new Label(i, 0, column.getName());
				sheet.addCell(label);
			}
		} catch (WriteException e) {
			throw new AppException(e);
		}
	}
}
