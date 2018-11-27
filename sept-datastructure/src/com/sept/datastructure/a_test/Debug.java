package com.sept.datastructure.a_test;

import java.util.Date;
import java.util.HashMap;

import com.sept.util.UnitConversionUtil;

public class Debug {
	private static final HashMap<String, Date> hmDatas = new HashMap<>();

	public static final void start(String id) {
		hmDatas.put(id, new Date());
	}

	public static final void end(String id) {
		Date dStart = hmDatas.get(id);
		System.out.println(id + ":" + UnitConversionUtil.formatMSToASUnit(new Date().getTime() - dStart.getTime()));
	}

}
