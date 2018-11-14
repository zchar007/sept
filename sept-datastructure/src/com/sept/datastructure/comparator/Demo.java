package com.sept.datastructure.comparator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.util.UnitConversionUtil;

public class Demo {
	public static void main(String[] args) throws AppException {
		test1(20000);
		test2(20000 );
	}

	public static void test1(int count) throws AppException {
		DataStore al = new DataStore();

		for (int i = 0; i < count; i++) {
			DataObject hm = new DataObject();
			hm.put("name", "����" + i);
			hm.put("xl", "����" + i);
			hm.put("age", i);
			al.add(hm);
		}
		System.out.println(al);
		System.out.println("DataStore:��ʼ");
		Date d11 = new Date();
		String filter = " name like ����1 and xl like ����1 and ( age > 2 and age < 2000)";
		al.filterModify(filter);
		Date d22 = new Date();
		System.out.println("DataStore:" + UnitConversionUtil.formatMSToASUnit(d22.getTime() - d11.getTime()));
		System.out.println("DataStore:" + al.size());

//		HashMap<String, Object> hm = new HashMap<>();
//		hm.put("name", "����11");
//		hm.put("xl", "����11");
//		hm.put("age", 11);
//		System.out.println(ComparaUtil.match(hm, filter));
	}

	public static void test2(int count) throws AppException {
		ArrayList<HashMap<String, Object>> al = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("name", "����" + i);
			hm.put("xl", "����" + i);
			hm.put("age", i);
			al.add(hm);
		}
		System.out.println("ArrayList��ʼ");
		Date d11 = new Date();
		String filter = " name like ����1 and xl like ����1 and ( age > 2 and age < 20)";
		ComparaUtil.filter(al, filter);
		Date d22 = new Date();
		System.out.println("ArrayList:" + UnitConversionUtil.formatMSToASUnit(d22.getTime() - d11.getTime()));
		System.out.println("ArrayList:" + al.size());

//		HashMap<String, Object> hm = new HashMap<>();
//		hm.put("name", "����11");
//		hm.put("xl", "����11");
//		hm.put("age", 11);
//		System.out.println(ComparaUtil.match(hm, filter));
	}
}
