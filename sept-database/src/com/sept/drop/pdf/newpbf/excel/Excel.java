package com.sept.drop.pdf.newpbf.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.sept.datastructure.DataStore;
import com.sept.drop.pdf.newpbf.DTColumn;
import com.sept.drop.pdf.newpbf.IDTColumns;
import com.sept.drop.pdf.newpbf.IDTSource;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.io.local.FileIOTool;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Excel implements IDTSource<ExcelSheet> {
	private boolean readonly;
	private String url;
	private ByteArrayOutputStream os;
	private WritableWorkbook book;
	private HashMap<WritableSheet, ExcelSheet> hmWSheets;
	private LinkedHashMap<String, ExcelSheet> hmSheets;

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
		this.hmSheets = new LinkedHashMap<>();
		this.hmWSheets = new HashMap<>();
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
		try {
			// 创建
			String path = excelFile.getAbsolutePath();
			path = path.substring(0, path.length() - excelFile.getName().length());
			File pathFile = new File(path);
			if(!pathFile.exists()) {
				pathFile.mkdirs();
			}
			excelFile.createNewFile();
			excelFile = new File(excelFile.getAbsolutePath());
			
			this.url = excelFile.getAbsolutePath();
			this.os = new ByteArrayOutputStream();
			
			Workbook book4read = Workbook.getWorkbook(new FileInputStream(excelFile));
			book = Workbook.createWorkbook(os, book4read);
			this.hmSheets = new LinkedHashMap<>();
			this.hmWSheets = new HashMap<>();
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
			this.hmSheets = new LinkedHashMap<>();
			this.hmWSheets = new HashMap<>();
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
				throw new AppException("Excel文件不存在！");
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

	private Excel(File excelFile, boolean readonly) throws AppException {
		this.readonly = readonly;
		FileInputStream fis;
		try {
			this.url = excelFile.getAbsolutePath();
			fis = new FileInputStream(excelFile);
			this.os = new ByteArrayOutputStream();
			Workbook book4read = Workbook.getWorkbook(fis);
			book = Workbook.createWorkbook(os, book4read);
			this.hmSheets = new LinkedHashMap<>();
			this.hmWSheets = new HashMap<>();
		} catch (BiffException | IOException e) {
			throw new AppException(e);
		}
	}

	public ExcelSheet addSheet(String sheetName, IDTColumns dtColumns) throws AppException {
		if (readonly) {
			throw new AppException("当前excel不可编辑！");
		}
		if (this.hmSheets.containsKey(sheetName)) {
			throw new AppException("当前Excel已存在名为【" + sheetName + "】的sheet页！");
		}
		WritableSheet sheet = book.getSheet(sheetName);// TODO 看这里是否会报错
		if (null != sheet) {
			throw new AppException("当前Excel已存在名为【" + sheetName + "】的sheet页！");
		}
		sheet = book.createSheet(sheetName, book.getSheetNames().length);
		this.addHeadToSheet(sheet, dtColumns);
		ExcelSheet es = new ExcelSheet(sheet);
		es.setColumns(dtColumns);
		es.init();// 导入数据

		this.hmSheets.put(sheetName, es);
		this.hmWSheets.put(sheet, es);

		return es;
	}

	@Override
	public ExcelSheet newTable(String sheetName, IDTColumns dtColumns) throws AppException {
		return this.addSheet(sheetName, dtColumns);
	}

	public ExcelSheet getSheet(String sheetName) throws AppException {
		if (this.hmSheets.containsKey(sheetName)) {
			return this.hmSheets.get(sheetName);
		}
		WritableSheet sheet = book.getSheet(sheetName);// TODO 看这里是否会报错
		if (null == sheet) {
			throw new AppException("当前Excel不存在名为【" + sheetName + "】的sheet页！");
		}
		ExcelSheet es = new ExcelSheet(sheet);
		es.init();// 导入数据

		this.hmSheets.put(sheetName, es);
		this.hmWSheets.put(sheet, es);

		return es;
	}

	@Override
	public ExcelSheet getTable(String sheetName) throws AppException {
		return this.getSheet(sheetName);
	}

	public ExcelSheet getSheet(String sheetName, String keyValue) throws AppException {
		ExcelSheet es = this.getSheet(sheetName);
		es.setKeyValue(keyValue);
		return es;
	}

	@Override
	public ExcelSheet getTable(String sheetName, String keyValue) throws AppException {
		return this.getSheet(sheetName, keyValue);
	}

	public ExcelSheet getSheet(int index, IDTColumns ditColumns) throws AppException {
		WritableSheet wsheet = book.getSheet(index);
		if (this.hmWSheets.containsKey(wsheet)) {
			return this.hmWSheets.get(wsheet);
		}
		if (null == ditColumns) {
			throw new AppException("当前缓存中不存在此sheet,必须传入列定义获取excel中sheet!");
		}
		ExcelSheet es = new ExcelSheet(wsheet);
		es.init();
		es.setColumns(ditColumns);

		this.hmSheets.put(wsheet.getName(), es);
		this.hmWSheets.put(wsheet, es);
		return es;
	}

	public ExcelSheet getSheet(String sheetName, IDTColumns dtColumns) throws AppException {
		ExcelSheet es = this.getSheet(sheetName);
		es.setColumns(dtColumns);
		return es;
	}

	@Override
	public ExcelSheet getTable(String sheetName, IDTColumns dtColumns) throws AppException {
		return this.getSheet(sheetName, dtColumns);
	}

	public DataStore getDataStore(int index) throws AppException {
		ExcelSheet sheet = getSheet(book.getSheet(index).getName());
		return sheet.select();
	}

	@Override
	public DataStore getDataStore(String sheetName) throws AppException {
		ExcelSheet es = this.getSheet(sheetName);
		return es.select();
	}

	@Override
	public DataStore getDataStore(String sheetName, IDTColumns ditColumns) throws AppException {
		ExcelSheet es = this.getTable(sheetName, ditColumns);
		return es.select();
	}

	@Override
	public byte[] getBytes() throws AppException {
		for (ExcelSheet es : this.hmSheets.values()) {
			es.commit();
		}
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

	@Override
	public boolean save() throws AppException {
		if (readonly) {
			throw new AppException("当前access已设置为只读，无法操作保存！");
		}

		if (readonly) {
			throw new AppException("当前excel不可编辑！");
		}
		if (null == this.url || this.url.trim().isEmpty()) {
			throw new AppException("url为空，不可保存！");
		}

		File file = new File(this.url);
		if (file.exists()) {
			file.delete();
		}
		for (ExcelSheet es : this.hmSheets.values()) {
			es.commit();
		}
		try {
			file.createNewFile();
			FileIOTool.writeBytesToFile(this.getBytes(), file);
		} catch (IOException e) {
			throw new AppException(e);
		}
		return true;
	}

	@Override
	public boolean save(String sheetName) throws AppException {
		if (readonly) {
			throw new AppException("当前access已设置为只读，无法操作保存！");
		}
		if (this.hmSheets.containsKey(sheetName)) {
			this.hmSheets.get(sheetName).commit();
			return true;
		}
		return false;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	private void addHeadToSheet(WritableSheet sheet, IDTColumns ditColumns) throws AppException {
		try {
			for (int i = 0; i < ditColumns.columnCount(); i++) {
				DTColumn column = ditColumns.getColumn(i);
				Label label = new Label(i, 0, column.getHead());
				sheet.addCell(label);
			}
		} catch (WriteException e) {
			throw new AppException(e);
		}
	}

}
