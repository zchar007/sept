package com.sept.util;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.sept.exception.AppException;

public final class JSONUtil {

	@SuppressWarnings("unchecked")
	 public static final String toJson(Object o) throws AppException {
		String cname = o.getClass().getName();
		if (o instanceof String || cname.equals("java.lang.String") || o instanceof StringBuffer
				|| cname.equals("java.lang.StringBuffer") || o instanceof StringBuilder
				|| cname.equals("java.lang.StringBuilder")) {
			return string2Json(String.valueOf(o));

		}
		if (o instanceof Double || o instanceof Integer || o instanceof Float || o instanceof Long
				|| o instanceof BigDecimal || (cname.equals("java.lang.Double")) || cname.equals("java.lang.Integer")
				|| cname.equals("java.lang.Long") || cname.equals("java.lang.Float")
				|| cname.equals("java.math.BigDecimal")) {
			return string2Json(String.valueOf(o));
		}
		if (o instanceof Blob || cname.equals("java.sql.Blob")) {
			return "";
		}
		if (o instanceof Clob || cname.equals("java.sql.Clob")) {
			return "";
		}
		if (o instanceof Boolean || cname.equals("java.lang.Boolean")) {
			return string2Json((String) TypeUtil.getValueByType(TypeUtil.STRING, o));
		}
		if (o instanceof Date || o instanceof java.sql.Date || o instanceof java.sql.Timestamp
				|| (cname.equals("java.util.Date")) || (cname.equals("java.sql.Date"))
				|| (cname.equals("java.sql.Timestamp"))) {
			return string2Json((String) TypeUtil.getValueByType(TypeUtil.STRING, o));
		}

		if (o instanceof Map) {
			return map2Json((Map<String, Object>) o);
		}
		if (o instanceof List) {
			return list2Json((List<Object>) o);
		}
		if ((o instanceof int[])) {
			return intArray2Json((int[]) (int[]) o);
		}
		if ((o instanceof boolean[])) {
			return booleanArray2Json((boolean[]) (boolean[]) o);
		}
		if ((o instanceof long[])) {
			return longArray2Json((long[]) (long[]) o);
		}
		if ((o instanceof float[])) {
			return floatArray2Json((float[]) (float[]) o);
		}
		if ((o instanceof double[])) {
			return doubleArray2Json((double[]) (double[]) o);
		}
		if ((o instanceof short[])) {
			return shortArray2Json((short[]) (short[]) o);
		}
		if ((o instanceof byte[])) {
			return byteArray2Json((byte[]) (byte[]) o);
		}
		if ((o instanceof Object[])) {
			return array2Json((Object[]) (Object[]) o);
		}

		throw new RuntimeException("Unsupported type: " + o.getClass().getName() + "--" + o.toString());
	}

	 public static final String string2Json(String s) {
		StringBuilder sb = new StringBuilder(s.length() + 20);
		sb.append('"');
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		sb.append('"');
		return sb.toString();
	}

	public static final String map2Json(Map<String, Object> map) throws AppException {
		if (map.isEmpty())
			return "{}";
		StringBuilder sb = new StringBuilder(map.size() << 4);
		sb.append('{');
		for (String key : map.keySet()) {
			Object value = map.get(key);
			sb.append('"');
			sb.append(key);
			sb.append('"');
			sb.append(':');
			sb.append(toJson(value));
			sb.append(',');
		}

		sb.setCharAt(sb.length() - 1, '}');
		return sb.toString();
	}

	public static final String list2Json(List<Object> list) throws AppException {
		if (list.size() == 0) {
			return "{}";
		}
		Object[] DataObjectArray = list.toArray();
		return toJson(DataObjectArray);
	}

	public static final String array2Json(Object[] array) throws AppException {
		if (array.length == 0)
			return "[]";
		StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (Object o : array) {
			sb.append(toJson(o));
			sb.append(',');
		}

		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static final String intArray2Json(int[] array) {
		if (array.length == 0)
			return "[]";
		StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (int o : array) {
			sb.append(Integer.toString(o));
			sb.append(',');
		}

		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static final String longArray2Json(long[] array) {
		if (array.length == 0)
			return "[]";
		StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (long o : array) {
			sb.append(Long.toString(o));
			sb.append(',');
		}

		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static final String booleanArray2Json(boolean[] array) {
		if (array.length == 0)
			return "[]";
		StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (boolean o : array) {
			sb.append(Boolean.toString(o));
			sb.append(',');
		}

		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static final String floatArray2Json(float[] array) {
		if (array.length == 0)
			return "[]";
		StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (float o : array) {
			sb.append(Float.toString(o));
			sb.append(',');
		}

		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static final String doubleArray2Json(double[] array) {
		if (array.length == 0)
			return "[]";
		StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (double o : array) {
			sb.append(Double.toString(o));
			sb.append(',');
		}

		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static final String shortArray2Json(short[] array) {
		if (array.length == 0)
			return "[]";
		StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (short o : array) {
			sb.append(Short.toString(o));
			sb.append(',');
		}

		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static final String byteArray2Json(byte[] array) {
		if (array.length == 0)
			return "[]";
		StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (byte o : array) {
			sb.append(Byte.toString(o));
			sb.append(',');
		}

		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}
}