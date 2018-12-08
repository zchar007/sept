package com.sept.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;
import com.sept.support.util.UnitConversionUtil;

public class Demo {
	public static void main(String[] args) throws Exception {
		test1(2000000);
		//test2(240000 );
	}

	public static void test1(int count) throws Exception {
		DataStore al = new DataStore();

		for (int i = 0; i < count; i++) {
			DataObject hm = new DataObject();
			hm.put("name", "张三" + i);
			hm.put("xl", "本科" + i);
			hm.put("age", i);
			al.add(hm);
		}
		//System.out.println(al);
		System.out.println("DataStore:开始");
		Date d11 = new Date();
		String filter = " name like 张三1 and xl like 本科1 and ( age > 2 and age < 2000)";
		al.filterModify(filter);
		Date d22 = new Date();
		System.out.println("DataStore:" + UnitConversionUtil.formatMSToASUnit(d22.getTime() - d11.getTime()));
		System.out.println("DataStore:" + al.size());

//		HashMap<String, Object> hm = new HashMap<>();
//		hm.put("name", "张三11");
//		hm.put("xl", "本科11");
//		hm.put("age", 11);
//		System.out.println(ComparaUtil.match(hm, filter));
	}

	public static void test2(int count) throws AppException {
		ArrayList<HashMap<String, Object>> al = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("name", "张三" + i);
			hm.put("xl", "本科" + i);
			hm.put("age", i);
			al.add(hm);
		}
		System.out.println("ArrayList开始");
		Date d11 = new Date();
		String filter = " name like 张三1 and xl like 本科1 and ( age > 2 and age < 2000)";
		ComparaUtil.filter(al, filter);
		Date d22 = new Date();
		System.out.println("ArrayList:" + UnitConversionUtil.formatMSToASUnit(d22.getTime() - d11.getTime()));
		System.out.println("ArrayList:" + al.size());

//		HashMap<String, Object> hm = new HashMap<>();
//		hm.put("name", "张三11");
//		hm.put("xl", "本科11");
//		hm.put("age", 11);
//		System.out.println(ComparaUtil.match(hm, filter));
	}
}
