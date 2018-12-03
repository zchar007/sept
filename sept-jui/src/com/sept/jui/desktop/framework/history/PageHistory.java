package com.sept.jui.desktop.framework.history;

import java.io.Serializable;

import com.sept.datastructure.DataObject;

public class PageHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	private String toolName;
	private String id;
	private DataObject datas;
	/** yyyyMMddHHmmss的日期数据 **/
	private String date;
	
}
