package com.sept.support.model.data.filter;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import com.sept.support.model.data.DataObject;
import com.sept.support.util.DateUtil;
import com.sept.support.util.HanDict;

public class DataObjectComparator implements Comparator<DataObject> {

	private String[] key = null;

	public DataObjectComparator(String compareKeys) {
		this.key = getColNames(compareKeys);
	}

	public int compare(DataObject o1, DataObject o2) {
		int result = 0;
		for (int i = 0; i < this.key.length; i++) {
			Object o1Value;
			o1Value = o1.getObject(this.key[i], null);
			Object o2Value;
			o2Value = o2.getObject(this.key[i], null);
			result = compareCell(o1Value, o2Value);
			if (result != 0) {
				return result;
			}
		}
		return result;
	}

	protected int compareCell(Object o1, Object o2) {
		if ((o1 != null) && (o2 != null)) {
			if ((o1.getClass().getName().equals("java.lang.Integer"))
					|| (o1.getClass().getName().equals("java.lang.Double"))
					|| (o1.getClass().getName().equals("java.lang.Long"))
					|| (o1.getClass().getName().equals("java.math.BigDecimal"))) {
				return new BigDecimal(o1.toString()).compareTo(new BigDecimal(
						o2.toString()));
			}
			if (o1.getClass().getName().equals("java.util.Date")) {
				return DateUtil.formatDate((java.util.Date) o1,
						"yyyyMMddhhmmss").compareTo(
						DateUtil.formatDate((java.util.Date) o2,
								"yyyyMMddhhmmss"));
			}
			if (o1.getClass().getName().equals("java.sql.Date")) {
				return DateUtil
						.formatDate((java.sql.Date) o1, "yyyyMMddhhmmss")
						.compareTo(
								DateUtil.formatDate((java.sql.Date) o2,
										"yyyyMMddhhmmss"));
			}
			return compareChinese(o1.toString(), o2.toString());
		}
		if ((o1 == null) && (o2 != null)) {
			return 1;
		}
		if ((o1 != null) && (o2 == null)) {
			return -1;
		}
		return 0;
	}

	protected int compareChinese(String o1, String o2) {
		for (int i = 0; (i < o1.length()) && (i < o2.length()); i++) {
			int codePoint1 = o1.charAt(i);
			int codePoint2 = o2.charAt(i);
			if ((Character.isSupplementaryCodePoint(codePoint1))
					|| (Character.isSupplementaryCodePoint(codePoint2))) {
				i++;
			}
			if (codePoint1 != codePoint2) {
				if ((Character.isSupplementaryCodePoint(codePoint1))
						|| (Character.isSupplementaryCodePoint(codePoint2))) {
					return codePoint1 - codePoint2;
				}
				String pinyin1 = pinyin((char) codePoint1);
				String pinyin2 = pinyin((char) codePoint2);
				if ((pinyin1 != null) && (pinyin2 != null)) {
					if (!pinyin1.equals(pinyin2)) {
						return pinyin1.compareTo(pinyin2);
					}
				} else {
					return codePoint1 - codePoint2;
				}
			}
		}
		return o1.length() - o2.length();
	}

	protected String pinyin(char c) {
		List<String> pinyinArray = HanDict.getPY(c, true);
		if (pinyinArray == null || pinyinArray.size() == 0) {
			return null;
		}
		String pinyin = HanDict.getPY(c + "", true).trim();
		pinyin = pinyin.substring(0, pinyin.length() - 1);
		return pinyin;
	}

	protected String[] getColNames(String cols) {
		if (null == cols) {
			return null;
		}
		String[] result = cols.split(",");
		for (int i = 0; i < result.length; i++) {
			result[i] = result[i].trim();
		}
		return result;
	}
}
