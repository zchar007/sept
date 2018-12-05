package com.sept.drop.a_test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.datastructure.comparator.Datas;
import com.sept.debug.Debug;
import com.sept.exception.AppException;
import com.sept.io.test.UnitConversionUtil;
import com.sept.util.compara.ComparaUtil;

public class TestDataFilter {
	public static void main(String[] args) throws AppException {
	//	testFilter(2000, true);
	//	testFilter(11, false);
		
//		testFilter(20000, true);
	//	testFilter(20000, false);
//		
//		testFilter(200000, true);
	//	testFilter(200000, false);
//		
//		testFilter(400000, true);
	//	testFilter(400000, false);
//		
//		testFilter(600000, true);
//		testFilter(600000, false);
//		
//		testFilter(800000, true);
	//	testFilter(800000, false);		
//		
//		testFilter(1000000, true);
	//	testFilter(1000000, false);
//		
//		testFilter(1200000, true);
		testFilter(1200000, false);
//		
//		testFilter(1400000, true);
		testFilter(1400000, false);
//		
//		testFilter(1600000, true);
//		testFilter(1600000, false);
//		testFindAll(2000);
//		testFindAll(20000);
//		testFindAll(200000);
//		testFindAll(400000);
//		testFindAll(600000);
//		testFindAll(800000);
//		testFindAll(1000000);
//		testFindAll(1200000);

		// testDataStore(1000000);
		// testDataStore2();

		// 验证 DataObject.putAll, DataStore.insertRow,addAll

		// testDataObject();
		// testDataStoreFind(10000000);
	}

	public static void testFilter(int count, boolean modify) throws AppException {

		DataStore pds = new DataStore();

		for (int i = 0; i < count; i++) {
			DataObject pdo = new DataObject();
			pdo.put("name", "张三" + i);
			pdo.put("xl", "本科" + i);
			pdo.put("age", i);
			pds.add(pdo);
		}
		System.out.println("《["+modify+"]["+count+"][testFilter]----------------开始");
		Debug.start("["+modify+"]["+count+"][testFilter]");
		String filterStr = " name like 张三1 and xl like 本科1 and ( age > 2 and age < 2000)";
//		DataStore ds = Datas.filter(pds, filterStr, modify);
//		System.out.println(modify+"查找到" + ds.size());

		pds.filter(filterStr);
		System.out.println(modify+"查找到" + pds.size());

		Debug.end("["+modify+"]["+count+"][testFilter]");
		System.out.println("["+modify+"]["+count+"][testFilter]----------------结束》");
	}

	public static void testFindAll(int count) throws AppException {

		DataStore pds = new DataStore();

		for (int i = 0; i < count; i++) {
			DataObject pdo = new DataObject();
			pdo.put("name", "张三" + i);
			pdo.put("xl", "本科" + i);
			pdo.put("age", i);
			pds.add(pdo);
		}
		// System.out.println(al);
		System.out.println("DataStore:开始");
		Date d11 = new Date();
		String filterStr = " name like 张三1 and xl like 本科1 and ( age > 2 and age < 2000)";
		int[] index = Datas.findAll(pds, filterStr);
		System.out.println("查找到" + index.length);
		Date d22 = new Date();
		System.out.println("DataStore:" + UnitConversionUtil.formatMSToASUnit(d22.getTime() - d11.getTime()));
		System.out.println("DataStore:" + pds.size());

	}

	public static void testDataStore2() throws AppException {
		// 验证 DataObject.putAll, DataStore.insertRow,addAll
		DataObject pdo = new DataObject();
		pdo.put("name", "张三");
		pdo.put("xl", "本科");
		pdo.put("age", 12);
		DataObject pdo2 = new DataObject();
		pdo2.put("name2", "张三2");
		pdo2.put("xl2", "本科2");
		pdo2.put("age2", 122);
		pdo.putAll(pdo2);
		System.out.println(pdo);

		DataStore pds = new DataStore();
		for (int i = 0; i < 5; i++) {
			DataObject pdo3 = new DataObject();
			pdo3.put("name", "张三" + i);
			pdo3.put("xl", "本科" + i);
			pdo3.put("age", i);
			pds.add(pdo3);
		}

		pds.insertRow(6, pdo2);
		System.out.println(pds);

		DataStore pds2 = new DataStore();
		for (int i = 0; i < 5; i++) {
			DataObject pdo3 = new DataObject();
			pdo3.put("name", "张三" + i);
			pdo3.put("xl", "本科" + i);
			pdo3.put("age", i);
			pds2.add(pdo3);
		}
		pds2.addAll(pds);
		System.out.println(pds2);
	}

	public static void testDataStore(int count) throws AppException {
		Debug.start("DataStore");
		DataStore pds = new DataStore();
		for (int i = 0; i < count; i++) {
			DataObject pdo = new DataObject();
			pdo.put("name", "张三" + i);
			pdo.put("xl", "本科" + i);
			pdo.put("age", i);
			pds.add(pdo);
		}
		Debug.end("DataStore");
		Debug.start("ArrayList");
		ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < count; i++) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("name", "张三" + i);
			hm.put("xl", "本科" + i);
			hm.put("age", i);
			al.add(hm);
		}
		Debug.end("ArrayList");
	}

	public static void testDataObjectMatch() throws AppException {
		DataObject pdo = new DataObject();
		pdo.put("name", "张三11");
		pdo.put("xl", "本科11");
		pdo.put("age", 11);
		String filter = " name like 张三1 and xl like 本科1 and ( age > 2 and age < 2000)";

		ComparaUtil.match(pdo, filter);
	}

	public static void testDataStoreFind(int count) throws AppException {
		DataStore pds = new DataStore();

		for (int i = 0; i < count; i++) {
			DataObject pdo = new DataObject();
			pdo.put("name", "张三" + i);
			pdo.put("xl", "本科" + i);
			pdo.put("age", i);
			pds.add(pdo);
		}
		// System.out.println(al);
		System.out.println("DataStore:开始");
		Date d11 = new Date();
		String filterStr = " name like 张三1 and xl like 本科1 and ( age > 2 and age < 2000)";
		int index = Datas.find(pds, filterStr);
		System.out.println(pds.get(index));
		Date d22 = new Date();
		System.out.println("DataStore:" + UnitConversionUtil.formatMSToASUnit(d22.getTime() - d11.getTime()));
		System.out.println("DataStore:" + pds.size());
	}
}
