package com.sept.drop.pdf.newpbf.poi;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;

public class PoiTest {
	public static void main(String[] args) {
		HSSFWorkbook book = HSSFWorkbookFactory.createWorkbook();
		HSSFSheet sheet = book.createSheet("第一页");
		
	}
	
}
