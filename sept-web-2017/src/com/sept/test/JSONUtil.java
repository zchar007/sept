package com.sept.test;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;
import com.sept.support.util.DateUtil;

public final class JSONUtil {
	@SuppressWarnings("unchecked")
	public final static String toJson(Object o) {
		if (o == null)
			return "null";
		if ((o instanceof String))
			return string2Json((String) o);
		if ((o instanceof Boolean))
			return boolean2Json((Boolean) o);
		if ((o instanceof Number))
			return number2Json((Number) o);
		if ((o instanceof Map)) {
			return map2Json((Map<String, Object>) o);
		}
		if ((o instanceof DataStore))
			return DataStoreToJson((DataStore) o);
		if ((o instanceof Object[]))
			return array2Json((Object[]) (Object[]) o);
		if ((o instanceof int[]))
			return intArray2Json((int[]) (int[]) o);
		if ((o instanceof boolean[]))
			return booleanArray2Json((boolean[]) (boolean[]) o);
		if ((o instanceof long[]))
			return longArray2Json((long[]) (long[]) o);
		if ((o instanceof float[]))
			return floatArray2Json((float[]) (float[]) o);
		if ((o instanceof double[]))
			return doubleArray2Json((double[]) (double[]) o);
		if ((o instanceof short[]))
			return shortArray2Json((short[]) (short[]) o);
		if ((o instanceof byte[]))
			return byteArray2Json((byte[]) (byte[]) o);
		if ((o instanceof Date))
			return date2Json((Date) o);
		throw new RuntimeException("Unsupported type: "
				+ o.getClass().getName());
	}

	static String array2Json(Object[] array) {
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

	static String intArray2Json(int[] array) {
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

	static String longArray2Json(long[] array) {
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

	static String booleanArray2Json(boolean[] array) {
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

	static String floatArray2Json(float[] array) {
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

	static String doubleArray2Json(double[] array) {
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

	static String shortArray2Json(short[] array) {
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

	static String byteArray2Json(byte[] array) {
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

	static String map2Json(Map<String, Object> map) {
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

	public final static String DataStoreToJson(DataStore vds) {
		if (vds.size() == 0) {
			return "{}";
		}
		Object[] DataObjectArray = vds.toArray();
		return toJson(DataObjectArray);
	}

	static String boolean2Json(Boolean bool) {
		return bool.toString();
	}

	static String date2Json(Date date) {
		return "\"" + DateUtil.formatDate(date, "yyyyMMddhhmmss") + "\"";
	}

	static String number2Json(Number number) {
		return number.toString();
	}

	static String string2Json(String s) {
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

	public final static String encodeJson(String jsonString)
			throws AppException {
		jsonString = jsonString.replaceAll("\r", "&#xD;");
		jsonString = jsonString.replaceAll("\n", "&#xA;");
		jsonString = jsonString.replaceAll("\t", "&#x9;");
		jsonString = jsonString.replaceAll("'", "&apos;");
		jsonString = jsonString.replaceAll("\b", "&back;");
		jsonString = jsonString.replaceAll("\f", "&page;");
		jsonString = jsonString.replaceAll("\\\\", "&slash;");
		jsonString = jsonString.replace(">", "&gt;");
		jsonString = jsonString.replace("<", "&lt;");
		jsonString = jsonString.replace(" ", "&nbsp;");
		jsonString = jsonString.replace("\"", "&quot;");
		jsonString = jsonString.replace("\\", "&slash;");// 对斜线的转义
		return jsonString;
	}

	public final static String decodeJson(String jsonString)
			throws AppException {
		jsonString = jsonString.replaceAll("&#xD;", "\r");
		jsonString = jsonString.replaceAll("&#xA;", "\n");
		jsonString = jsonString.replaceAll("&#x9;", "\t");
		jsonString = jsonString.replaceAll("&apos;", "'");
		jsonString = jsonString.replaceAll("&back;", "\b");
		jsonString = jsonString.replaceAll("&page;", "\f");
		jsonString = jsonString.replaceAll("&slash;", "\\\\");
		jsonString = jsonString.replace("&gt;", ">");
		jsonString = jsonString.replace("&lt;", "<");
		jsonString = jsonString.replace("&nbsp;", " ");
		jsonString = jsonString.replace("&quot;", "\"");
		jsonString = jsonString.replace("&slash;", "\\");// 对斜线的转义
		return jsonString;
	}

	public final static String DataObjectToJson(DataObject para)
			throws AppException {
		return toJson(para);
	}

	public final static DataObject JSONToDataObject(String json)
			throws AppException {
		DataObject dto = new DataObject();
		try {
			JSONObject jObject = new JSONObject(json);
			Iterator<?> iterator = jObject.keys();
			while (iterator.hasNext()) {
				String varName = (String) iterator.next();
				Object varValue = jObject.get(varName);
				if (varValue == null)
					dto.put(varName, "");
				else if ((varValue instanceof JSONArray))
					dto.put(varName, JSONToDataStore(varValue.toString()));
				else if ((varValue instanceof JSONObject))
					dto.put(varName, JSONToDataObject(varValue.toString()));
				else if ((varValue instanceof Double))
					dto.put(varName, ((Double) varValue).doubleValue());
				else if ((varValue instanceof java.util.Date))
					dto.put(varName, (java.util.Date) varValue);
				// dto.put(varName, DateUtil.formatDate(
				// (java.util.Date) varValue, "yyyyMMddHHMMss"));
				else if ((varValue instanceof Number))
					dto.put(varName, (Number) varValue);
				else if ((varValue instanceof Boolean))
					dto.put(varName, (Boolean) varValue);
				else
					dto.put(varName, varValue);
			}
		} catch (JSONException e) {
			throw new AppException(e.toString());
		}
		return dto;
	}

	public final static DataStore JSONToDataStore(String json)
			throws AppException {
		DataStore dso = new DataStore();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				DataObject dto = JSONToDataObject(array.get(i).toString());
				dso.addRow(dto);
			}
		} catch (JSONException e) {
			throw new AppException(e.toString());
		}
		return dso;
	}

	public final static void main(String[] args) throws AppException,
			JSONException {
		DataObject para = new DataObject();
		para.put("date", new Date());
		para.put("string", "string");
		System.out.println(para.toJSON());
		System.out.println(JSONToDataObject(para.toJSON()).getDate("date"));

	}
}