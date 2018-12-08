package com.sept.support.model.data;

import java.math.BigDecimal;
import java.util.Date;

import com.sept.exception.AppException;
import com.sept.global.GlobalNames;
import com.sept.global.GlobalVars;
import com.sept.support.database.Sql;
import com.sept.support.database.Transaction;
import com.sept.support.database.TransactionManager;
import com.sept.support.util.TypeUtil;

public class TestDataStroeObject {

	public static void main(String[] args) throws Exception {
		GlobalNames.init(GlobalVars.APP_TYPE_DESK);
		// testJSON();
		testFilter();

	}

	private static void testFilter() throws Exception {
		Transaction tm = TransactionManager.getTransaction();
		tm.begin();
		Sql sql = new Sql();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from bxsys.code_config where 1 = ?");
		sql.setSql(sb.toString());
		sql.setInt(1, 1);
		DataStore vds = sql.executeQuery();
		System.out.println(vds.toJSON());
		DataStore ds2 = vds.filterKeep(" code == matong");
		System.out.println(ds2);
		System.out.println(vds);

	}

	private static void testJSON() throws AppException {
		DataObject pdo = new DataObject();
		pdo.put("string", "string");
		pdo.put("int", 100);
		pdo.put("double", "string");
		pdo.put("BigDecimal", new BigDecimal("12345678901234567890"));
		pdo.put("date", new Date());
		pdo.put("string", "string");

		DataObject doTemp = new DataObject();
		doTemp.put("string", "string");
		doTemp.put("int", 100);
		doTemp.put("double", "string");

		pdo.put("do", doTemp);

		DataStore ds = new DataStore();
		ds.setRowCount(2);
		ds.put(0, "xm", "张三\n");
		ds.put(0, "nl", 18);
		ds.put(1, "xm", "李四}],:\"");
		ds.put(1, "nl", 17);
		pdo.put("ds", ds);
		System.out.println(pdo.getTypeList());
		System.out.println(pdo.toJSON());

		DataObject rdo = (DataObject) TypeUtil.getValueByType(
				TypeUtil.DATAOBJECT, pdo.toJSON());
		System.out.println(rdo.getString("date"));
		System.out.println(rdo.getDate("date"));
		System.out.println(rdo.getBigDecimal("BigDecimal").toString());
		System.out.println(rdo.getDataObject("do").getString("string"));
		System.out.println(rdo.getDataStore("ds").getRow(0).getString("xm"));
		System.out.println(rdo.getDataStore("ds").getRow(1).getInt("nl"));

		String jsonStr = pdo.toJSON();

		rdo = DataObject.parseFromJSON(jsonStr);
		System.out.println(rdo.getString("date"));
		System.out.println(rdo.getDate("date"));
		System.out.println(rdo.getBigDecimal("BigDecimal").toString());
		System.out.println(rdo.getDataObject("do").getString("string"));
		System.out.println(rdo.getDataStore("ds").getRow(0).getString("xm"));
		System.out.println(rdo.getDataStore("ds").getRow(1).getString("xm"));

	}

}
