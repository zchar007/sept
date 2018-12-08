package com.sept.global;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;

/**
 * 交互字典
 * 
 * @author zchar
 * 
 */
public final class InteractiveDict {
	private static DataObject dsDict = null;
	// key
	public static String INTERACTIVE_SYSTEM_JSON;// 系统数据，包含数据类型，用户信息等
	public static String INTERACTIVE_DATA;// 请求所需数据（xml或json）
	public static String INTERACTIVE_DATA_TYPE;// 请求所需数据（xml或json）
	public static String INTERACTIVE_ERROR_FLAG;//错误标志的名字
	public static String INTERACTIVE_ERROR_TEXT;//错误标志的名字

	// value
	public static String INTERACTIVE_DATA_TYPE_JSON;// 请求所需数据（json）
	public static String INTERACTIVE_DATA_TYPE_XML;// 请求所需数据（xml）
	
	public static String INTERACTIVE_ERROR_BUSI;// 请求所需数据（xml）
	public static String INTERACTIVE_ERROR_APP;// 请求所需数据（xml）
	
	public static String INTERACTIVE_ERROR__FWD__MAIN;// 请求所需数据（xml）

	static {
		try {
			dsDict = GlobalNames.getDeploy("interactivedict");

			if (null == dsDict) {
				dsDict = new DataObject();
			}

		} catch (AppException e) {
			// e.printStackTrace();
			dsDict = new DataObject();
		}
		// 关于前后台交互的一些关键参数key
		// 前台——>后台
		try {
			INTERACTIVE_SYSTEM_JSON = dsDict.getString(
					"INTERACTIVE_SYSTEM_JSON", "__SYSTEM_INFO__");
			
			INTERACTIVE_DATA = dsDict.getString("INTERACTIVE_DATA", "__DATA__");
			
			INTERACTIVE_DATA_TYPE = dsDict.getString("INTERACTIVE_DATA_TYPE",
					"__DATA__TYPE__");
			
			INTERACTIVE_DATA_TYPE_JSON = dsDict.getString(
					"INTERACTIVE_DATA_TYPE_JSON", "__DATA__TYPE__JSON__");
			
			INTERACTIVE_DATA_TYPE_XML = dsDict.getString(
					"INTERACTIVE_DATA_TYPE_XML", "__DATA__TYPE__XML__");
			
			INTERACTIVE_ERROR_FLAG = dsDict.getString(
					"INTERACTIVE_ERROR_FLAG", "__ERROR__FLAG__");
			INTERACTIVE_ERROR_TEXT = dsDict.getString(
					"INTERACTIVE_ERROR_TEXT", "__ERROR__TEXT__");
			INTERACTIVE_ERROR_BUSI = dsDict.getString(
					"INTERACTIVE_ERROR_BUSI", "__ERROR__BUSI__");
			INTERACTIVE_ERROR_APP = dsDict.getString(
					"INTERACTIVE_ERROR_APP", "__ERROR__APP__");
			INTERACTIVE_ERROR__FWD__MAIN = dsDict.getString(
							"INTERACTIVE_ERROR__FWD__MAIN", "__ERROR__FWD__MAIN__");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 交互数据类型

	}

}
