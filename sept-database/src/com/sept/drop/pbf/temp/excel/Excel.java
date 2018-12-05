package com.sept.drop.pbf.temp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sept.drop.pbf.DataTableColumn;
import com.sept.drop.pbf.DataTableColumns;
import com.sept.drop.pbf.IDataTable;
import com.sept.exception.AppException;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Excel implements IDataTable {
	private ByteArrayOutputStream os;
	private WritableWorkbook book;
	private HashMap<WritableSheet, DataTableColumns> hmSheetColumns = new HashMap<>();
	private HashMap<WritableSheet, Integer> hmSheetIndex = new HashMap<>();

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

	@Override
	public void setColumn(int index, DataTableColumn dtColumn) {

	}

	@Override
	public void setColumns(DataTableColumns dtColumns) {

	}

	@Override
	public void addRow(Map row) {

	}

	@Override
	public void addRows(ArrayList rows) {

	}

	@Override
	public void insertRow(int index, Map row) {

	}

	@Override
	public void insertRows(int index, ArrayList rows) {

	}

	@Override
	public String[] getHead() {
		return null;
	}

	@Override
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
}
