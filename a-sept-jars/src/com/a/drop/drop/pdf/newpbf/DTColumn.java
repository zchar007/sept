package com.sept.drop.pdf.newpbf;

import java.sql.Types;
import java.util.LinkedHashMap;

import com.sept.exception.AppException;
import com.sept.util.DateUtil;

public class DTColumn {
	/** 表中列首 **/
	private String head;

	/** 反应到数据中的名字 **/
	private String key;

	/** 数据类型 **/
	private int type;

	/** 如果是日期类型，还可以设置其格式化方式 **/
	private String mask;

	/** 默认值 **/
	private String none;

	/** 是下拉框的那种，一般excel中可能会用到 **/
	private LinkedHashMap<String, String> array;

	public DTColumn(String head, String key) {
		super();
		this.head = head;
		this.key = key;
		this.type = CommonTypes.VARCHAR;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getNone() {
		return none;
	}

	public void setNone(String none) {
		this.none = none;
	}

	public LinkedHashMap<String, String> getArray() {
		return array;
	}

	public void setArray(LinkedHashMap<String, String> array) {
		this.array = array;
	}

	public String handValue(String value) throws AppException {
		if (this.type == Types.DATE) {
			return DateUtil.formatDate(DateUtil.formatStrToDate(value), mask);
		}
		if (this.type == CommonTypes.ARRAY) {
			// TODO 这里怎么处理还没未知
		}
		return value;
	}
}
