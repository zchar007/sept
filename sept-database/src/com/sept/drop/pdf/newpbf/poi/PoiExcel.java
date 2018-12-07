package com.sept.drop.pdf.newpbf.poi;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;

import com.sept.datastructure.DataStore;
import com.sept.drop.pdf.newpbf.IDTColumns;
import com.sept.drop.pdf.newpbf.IDTSource;
import com.sept.exception.AppException;

public class PoiExcel implements IDTSource<PoiSheet> {
	private HSSFWorkbook book;

	public PoiExcel() {
		book = HSSFWorkbookFactory.createWorkbook();
	}

	@Override
	public PoiSheet newTable(String sheetName, IDTColumns dtColumns) throws AppException {
		HSSFSheet sheet = book.createSheet(sheetName);
		
		
		
		
		return null;
	}

	@Override
	public PoiSheet getTable(String sheetName) throws AppException {
		return null;
	}

	@Override
	public PoiSheet getTable(String sheetName, String keyValue) throws AppException {
		return null;
	}

	@Override
	public PoiSheet getTable(String sheetName, IDTColumns dtColumns) throws AppException {
		return null;
	}

	@Override
	public DataStore getDataStore(String sheetName) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataStore getDataStore(String sheetName, IDTColumns dtColumns) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBytes() throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save() throws AppException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save(String sheetName) throws AppException {
		// TODO Auto-generated method stub
		return false;
	}

}
