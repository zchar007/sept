package com.sept.jui.desktop.framework.history;

import java.util.HashMap;

public class Historys {
	/** 通常为文件名 **/
	private String name;
	/** 完整路径 **/
	private String path;
	/** 数据 app, app历史数据->id, 历史数据 **/
	private HashMap<String, HashMap<String, PageHistory>> hmDatas;
}
