package com.sept.debug;

import java.util.Date;
import java.util.HashMap;

import com.sept.debug.log4j.LogHandler;
import com.sept.util.UnitConverUtil;

public class Debug {
	private static final HashMap<String, Date> hmDatas = new HashMap<>();

	public static final void start(String id) {
		hmDatas.put(id, new Date());
	}

	public static final void end(String id) {
		Date dStart = hmDatas.get(id);
		LogHandler
				.debug("[" + id + "]耗时:{" + UnitConverUtil.msToAnySuit(new Date().getTime() - dStart.getTime()) + "}");
	}

	public static void main(String[] args) throws InterruptedException {
		Debug.start("kkk");
		Thread.sleep(200);
		Debug.end("kkk");

	}
}
