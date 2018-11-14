package com.sept.util.bools.comparator;

public class CompareCell {
	/**
	 * ������
	 */
	private String cmpoperator;
	/**
	 * �����ֶε�key
	 */
	private String cmpkey;
	/**
	 * ��Ҫ��Ϊ�Ƚϵ�ֵ
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
