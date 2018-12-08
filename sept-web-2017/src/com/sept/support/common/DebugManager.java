package com.sept.support.common;

import java.util.Date;
import java.util.HashMap;

import com.sept.exception.AppException;
import com.sept.global.GlobalNames;
import com.sept.support.model.data.DataObject;
import com.sept.support.util.DateUtil;
import com.sept.support.util.UnitConversionUtil;

public final class DebugManager {
	// debug相关配置
	private static DataObject pdoDebug = null;
	private static boolean debug = true;
	private static String debugHead = "DEBUG信息--";

	private static HashMap<Integer, Date> hmDates = new HashMap<Integer, Date>();
	private static int index = 0;
	private static Object indexKey = new Object();
	static {
		try {
			pdoDebug = GlobalNames.getDeploy("debug");
			debug = Boolean.parseBoolean(pdoDebug.getString("DEBUGMODEL"));
			if (pdoDebug.containsKey("DEBUGHEAD")) {
				String mess = pdoDebug.getString("DEBUGHEAD");
				debugHead = mess.trim().isEmpty() ? debugHead : mess;
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * debug输出，不换行
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-1
	 * @since V1.0
	 */
	public final static void print(String message) {
		if (debug) {
			System.err.print(debugHead + "【" + message + "】--"
					+ DateUtil.getCurrentDate("yyyy-MM-dd mm:hh:ss"));
		}
	}

	/**
	 * debug输出，换行
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-1
	 * @since V1.0
	 */
	public final static void println(String message) {
		if (debug) {
			System.err.println(debugHead + "【" + message + "】--"
					+ DateUtil.getCurrentDate("yyyy-MM-dd mm:hh:ss"));
		}
	}

	/**
	 * debug输出，换行
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-1
	 * @since V1.0
	 */
	public final static void println(String head, String message) {
		if (debug) {
			System.err.println(head + "【" + message + "】--"
					+ DateUtil.getCurrentDate("yyyy-MM-dd mm:hh:ss"));
		}
	}

	/**
	 * 设置一段代码的开始时间
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-1
	 * @since V1.0
	 */
	public final static int setStart() {
		if (debug) {
			int nowIndex = getIndex();
			hmDates.put(nowIndex, new Date());

			return nowIndex;
		}
		return -1;
	}

	/**
	 * 设置运行结束时间
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-1
	 * @since V1.0
	 */
	public final static String setEnd(int index) {
		if (debug) {
			if (!hmDates.containsKey(index)) {
				println("未配置相应开始时间!");
			}
			String message = UnitConversionUtil.formatMSToASUnit(new Date()
					.getTime() - hmDates.get(index).getTime());
			println("debug代码段运行时间为：" + message);
			return message;
		}
		return "null";
	}

	private static int getIndex() {
		synchronized (indexKey) {
			return index++;
		}

	}

	public final static boolean isDebugModel() {
		return debug;
	}
}
