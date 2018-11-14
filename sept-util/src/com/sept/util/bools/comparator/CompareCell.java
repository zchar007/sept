package com.sept.util.bools.comparator;

public class CompareCell {
	/**
	 * 操作符
	 */
	private String cmpoperator;
	/**
	 * 操作字段的key
	 */
	private String cmpkey;
	/**
	 * 需要作为比较的值
	 */
	private String cmpvalue;

	public CompareCell(String cmpoperator, String cmpkey, String cmpvalue) {
		super();
		this.cmpoperator = cmpoperator;
		this.cmpkey = cmpkey;
		this.cmpvalue = cmpvalue;
	}

	public String getCmpoperator() {
		return cmpoperator;
	}

	public void setCmpoperator(String cmpoperator) {
		this.cmpoperator = cmpoperator;
	}

	public String getCmpkey() {
		return cmpkey;
	}

	public void setCmpkey(String cmpkey) {
		this.cmpkey = cmpkey;
	}

	public String getCmpvalue() {
		return cmpvalue;
	}

	public void setCmpvalue(String cmpvalue) {
		this.cmpvalue = cmpvalue;
	}

	@Override
	public String toString() {
		return "CompareCell [cmpoperator=" + cmpoperator + ", cmpkey=" + cmpkey + ", cmpvalue=" + cmpvalue + "]";
	}

}
