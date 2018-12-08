package com.sept.support.model.data.filter;

import java.math.BigDecimal;
import java.util.List;

import com.sept.exception.AppException;
import com.sept.support.util.DateUtil;
import com.sept.support.util.HanDict;

/**
 * 
 * @author zchar
 * 
 */
public class ObjectComparator {

	public static boolean compare(Object o1, Object o2, String compareStr)
			throws AppException {
		// (?<=\\s)(==|<|>|<=|>=|!=|like)(?=\\s)|(?<=\\s)(isnull|isnotnull)
		String commd = compareStr.trim();
		boolean isTrue = false;
		if ("==".equals(commd)) {
			isTrue = _eq(o1, o2);
		} else if ("<".equals(commd)) {
			isTrue = _lt(o1, o2);
		} else if (">".equals(commd)) {
			isTrue = _gt(o1, o2);
		} else if ("<=".equals(commd)) {
			isTrue = _lte(o1, o2);
		} else if (">=".equals(commd)) {
			isTrue = _gte(o1, o2);
		} else if ("!=".equals(commd)) {
			isTrue = _neq(o1, o2);
		} else if ("<>".equals(commd)) {
			isTrue = _neq(o1, o2);
		} else if ("like".equals(commd)) {
			isTrue = _like(o1, o2);
		} else if ("isnull".equals(commd)) {
			isTrue = _isnull(o1);
		} else if ("isnotnull".equals(commd)) {
			isTrue = _isnotnull(o1);
		} else {
			isTrue = false;
		}
		return isTrue;
	}

	public static boolean _eq(Object o1, Object o2) throws AppException {
		//System.out.println(o1+"--"+o2+"] is eq = "+(compare(o1, o2) == 0));
		return compare(o1, o2) == 0;
	}

	public static boolean _neq(Object o1, Object o2) throws AppException {
		return compare(o1, o2) != 0;
	}

	public static boolean _lt(Object o1, Object o2) throws AppException {
		return compare(o1, o2) < 0;
	}

	public static boolean _lte(Object o1, Object o2) throws AppException {
		return compare(o1, o2) <= 0;
	}

	public static boolean _gt(Object o1, Object o2) throws AppException {
		return compare(o1, o2) > 0;
	}

	public static boolean _gte(Object o1, Object o2) throws AppException {
		return compare(o1, o2) >= 0;
	}

	public static boolean _isnull(Object o1) {
		return ("".equals(o1)) || (o1 == null);
	}

	public static boolean _isnotnull(Object o1) {
		return (!"".equals(o1)) && (o1 != null);
	}

	public static boolean _like(Object o1, Object o2) {
		if (o1 == null) {
			return false;
		}
		if (o2 == null) {
			return true;
		}
		return o1.toString().indexOf(o2.toString()) != -1;
	}

	private static int compare(Object o1, Object o2) throws AppException {
		if ((o1 != null) && (o2 != null)) {
			if ((o1.getClass().getName().equals("java.lang.Integer"))
					|| (o1.getClass().getName().equals("java.lang.Double"))
					|| (o1.getClass().getName().equals("java.lang.Long"))
					|| (o1.getClass().getName().equals("java.lang.Float"))
					|| (o1.getClass().getName().equals("java.math.BigDecimal"))) {
				return new BigDecimal(o1.toString()).compareTo(new BigDecimal(
						o2.toString()));
			}
			if (o1.getClass().getName().equals("java.util.Date")) {
				try {
					if ((o2 instanceof String)) {

						o2 = DateUtil.formatStrToDate((String) o2);
					}
					return DateUtil.formatDate((java.util.Date) o1,
							"yyyyMMddhhmmss").compareTo(
							DateUtil.formatDate((java.util.Date) o2,
									"yyyyMMddhhmmss"));
				} catch (AppException e) {
					e.printStackTrace();
					return 0;
				}
			}
			if (o1.getClass().getName().equals("java.sql.Date")) {
				try {
					if ((o2 instanceof String)) {
						o2 = DateUtil.formatStrToDate((String) o2);
					}
					return DateUtil.formatDate((java.sql.Date) o1,
							"yyyyMMddhhmmss").compareTo(
							DateUtil.formatDate((java.sql.Date) o2,
									"yyyyMMddhhmmss"));
				} catch (AppException e) {
					e.printStackTrace();
					return 0;
				}
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

	private static int compareChinese(String o1, String o2) {
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

	private static String pinyin(char c) {
		List<String> pinyinArray = HanDict.getPY(c, true);

		if (pinyinArray == null || pinyinArray.size() == 0) {
			return null;
		}
		String pinyin = HanDict.getPY(c + "", true).trim();
		pinyin = pinyin.substring(0, 1);
		return pinyin;
	}
}
