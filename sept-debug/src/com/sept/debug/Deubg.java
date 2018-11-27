package com.sept.debug;

import java.util.Date;
import java.util.HashMap;

import com.sept.debug.log4j.LogHandler;
import com.sept.util.UnitConversionUtil;

public class Deubg {
	private static final HashMap<String, Date> hmDatas = new HashMap<>();

	public static final void start(String id) {
		hmDatas.put(id, new Date());
	}

	public static final void end(String id) {
		Date dStart = hmDatas.get(id);
		LogHandler.debug("[" + id + "]" + UnitConversionUtil.formatMSToASUnit(new Date().getTime() - dStart.getTime()),
				Deubg.class+".end");
	}
	public static void main(String[] args) {
		Deubg.start("kkk");
		Deubg.end("kkk");

	}
}
