package com.sept.datastructure.util;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.util.DateUtil;

public class TypeUtil {
	public static final String STRING = "s";// 字符串
	public static final String NUMBER = "n";// 数字
	public static final String BLOB = "bb";
	public static final String CLOB = "c";
	public static final String DATASTORE = "ds";
	public static final String DATAOBJECT = "do";
	public static final String BOOLEAN = "b";
	public static final String DATE = "d";
	public static final String OBJECT = "o";

	// 以下可能会被转换
	public static final String DATETIME = "dt";
	public static final String DOUBLE = "db";
	public static final String FLOAT = "f";
	public static final String LONG = "l";
	public static final String INTEGER = "i";
	public static final String BIGDECIMAL = "bd";

	private static HashMap<String, Class<?>> types;
	static {
		types = new HashMap<String, Class<?>>();
		types.put("s", String.class);
		types.put("n", Number.class);
		types.put("bb", Blob.class);
		types.put("c", Clob.class);
		types.put("ds", DataStore.class);
		types.put("do", DataObject.class);
		types.put("b", Boolean.class);
		types.put("d", Date.class);
		types.put("o", Object.class);
	}

	public static boolean isType(String typeName) {
		return types.containsKey(typeName.toLowerCase());
	}

	/**
	 * DataStore DataObject 存储数据时获取value类型</br>
	 * 数字型参数转成Number 日期型转成 java.util.Date
	 * 
	 * @param 关键字 说明
	 * @return 关键字 说明
	 * @throws 异常说明 发生条件
	 * @author 张超
	 * @date 创建时间 2017-6-12
	 * @since V1.0
	 */
	public static final String getValueType(Object o) {
		if (null == o) {// null作String 处理
			return STRING;
		}
		String cname = o.getClass().getName();
		if (o instanceof String || cname.equals("java.lang.String") || o instanceof StringBuffer
				|| cname.equals("java.lang.StringBuffer") || o instanceof StringBuilder
				|| cname.equals("java.lang.StringBuilder")) {
			return STRING;

		}
		if (o instanceof Double || o instanceof Integer || o instanceof Float || o instanceof Long
				|| o instanceof BigDecimal || (cname.equals("java.lang.Double")) || cname.equals("java.lang.Integer")
				|| cname.equals("java.lang.Long") || cname.equals("java.lang.Float")
				|| cname.equals("java.math.BigDecimal")) {
			return NUMBER;
		}
		if (o instanceof Blob || cname.equals("java.sql.Blob")) {
			return BLOB;
		}
		if (o instanceof Clob || cname.equals("java.sql.Clob")) {
			return CLOB;
		}
		if (o instanceof DataObject || cname.equals(DataObject.class.getName())) {
			return DATAOBJECT;
		}
		if (o instanceof DataStore || cname.equals(DataStore.class.getName())) {
			return DATASTORE;
		}
		if (o instanceof Boolean || cname.equals("java.lang.Boolean")) {
			return BOOLEAN;
		}
		if (o instanceof Date || o instanceof java.sql.Date || o instanceof java.sql.Timestamp
				|| (cname.equals("java.util.Date")) || (cname.equals("java.sql.Date"))
				|| (cname.equals("java.sql.Timestamp"))) {
			return DATE;
		}
		return OBJECT;

	}

	/**
	 * 把Object 转换为对应的类型
	 * 
	 * @param type
	 * @param value
	 * @return
	 * @throws AppException
	 * @throws AppException
	 * @throws AppException
	 */
	public static Object getValueByType(String type, Object o) throws AppException {
		try {
			if (TypeUtil.BLOB.equals(type)) {
				return objectToBlob(o);
			}
			if (TypeUtil.CLOB.equals(type)) {
				return objectToClob(o);
			}
			if (TypeUtil.DATAOBJECT.equals(type)) {
				return objectToDataObject(o);
			}
			if (TypeUtil.DATASTORE.equals(type)) {
				return objectToDataStore(o);
			}
			if (TypeUtil.STRING.equals(type)) {
				return objectToString(o);
			}
			if (TypeUtil.INTEGER.equals(type)) {
				return objectToInt(o);
			}
			if (TypeUtil.FLOAT.equals(type)) {
				return objectToFloat(o);
			}
			if (TypeUtil.DOUBLE.equals(type)) {
				return objectToDouble(o);
			}
			if (TypeUtil.LONG.equals(type)) {
				return objectToLong(o);
			}
			if (TypeUtil.BOOLEAN.equals(type)) {
				return objectToBoolean(o);
			}
			if (TypeUtil.DATE.equals(type)) {
				return objectToDate(o);
			}
			if (TypeUtil.BIGDECIMAL.equals(type)) {
				return objectToBigDecimal(o);
			}
			if (TypeUtil.NUMBER.equals(type)) {
				return objectToBigDecimal(o);
			}
		} catch (AppException e) {
			throw new AppException(e.getMessage());

		}

		return types;
	}

	public static Blob objectToBlob(Object o) throws AppException {
		if (null == o) {
			return null;
		}
		String cname = o.getClass().getName();
		if (o instanceof Blob || cname.equals("java.sql.Blob")) {
			return (Blob) o;
		}
		throw new AppException("不支持的跨类型转换");
	}

	public static Clob objectToClob(Object o) throws AppException {
		if (null == o) {
			return null;
		}
		String cname = o.getClass().getName();
		if (o instanceof Clob || cname.equals("java.sql.Clob")) {
			return (Clob) o;
		}
		throw new AppException("不支持的跨类型转换");
	}

	public static DataObject objectToDataObject(Object o) throws AppException {
		if (null == o) {
			return null;
		}
		String cname = o.getClass().getName();
		if (o instanceof DataObject || cname.equals(DataObject.class.getName())) {
			return (DataObject) o;
		} else if (o instanceof String || cname.equals("java.lang.String") || o instanceof StringBuffer
				|| cname.equals("java.lang.StringBuffer") || o instanceof StringBuilder
				|| cname.equals("java.lang.StringBuilder")) {
			try {
				return DataObject.parseFromXML(o.toString());
			} catch (Exception e) {
				return DataObject.parseFromJSON(o.toString());
			}

		}
		throw new AppException("不支持的跨类型转换");
	}

	public static DataStore objectToDataStore(Object o) throws AppException {
		if (null == o) {
			return null;
		}
		String cname = o.getClass().getName();
		if (o instanceof DataStore || cname.equals(DataStore.class.getName())) {
			return (DataStore) o;
		} else if (o instanceof String || cname.equals("java.lang.String") || o instanceof StringBuffer
				|| cname.equals("java.lang.StringBuffer") || o instanceof StringBuilder
				|| cname.equals("java.lang.StringBuilder")) {
			try {
				return DataStore.parseFromXML(o.toString());
			} catch (Exception e) {
				return DataStore.parseFromJSON(o.toString());
			}

		}
		throw new AppException("不支持的跨类型转换");
	}

	public static String objectToString(Object o) throws AppException {
		if (null == o) {
			return null;
		}
		String cname = o.getClass().getName();

		if (o instanceof String || cname.equals("java.lang.String") || o instanceof StringBuffer
				|| cname.equals("java.lang.StringBuffer") || o instanceof StringBuilder
				|| cname.equals("java.lang.StringBuilder")) {
			return o.toString();

		}
		if (o instanceof Double || cname.equals("java.lang.Double")) {
			return o.toString();
		}
		if (o instanceof Integer || cname.equals("java.lang.Integer")) {
			return o.toString();
		}
		if (o instanceof Float || cname.equals("java.lang.Float")) {
			return o.toString();
		}
		if (o instanceof Long || cname.equals("java.lang.Long")) {
			return o.toString();
		}
		if (o instanceof BigDecimal || cname.equals("java.math.BigDecimal")) {
			return o.toString();
		}
		if (o instanceof DataObject || cname.equals(DataObject.class.getName())) {
			return ((DataObject) o).toJSON();
		}
		if (o instanceof DataStore || cname.equals(DataStore.class.getName())) {
			return ((DataStore) o).toJSON();
		}
		if (o instanceof Boolean || cname.equals("java.lang.Boolean")) {
			return o.toString();
		}
		if (o instanceof Date || cname.equals("java.util.Date")) {

			return DateUtil.formatDate((Date) o, "yyyyMMddhhmmss");
		}
		if (o instanceof java.sql.Date || cname.equals("java.sql.Date")) {
			Date d = new Date(((java.sql.Date) o).getTime());
			return DateUtil.formatDate(d, "yyyyMMddhhmmss");
		}
		if (o instanceof java.sql.Timestamp || cname.equals("java.sql.Timestamp")) {
			Date d = new Date(((java.sql.Timestamp) o).getTime());
			return DateUtil.formatDate(d, "yyyyMMddhhmmss");
		}

		return null;

	}

	public static int objectToInt(Object o) {
		if (null == o) {
			return 0;
		}
		String cname = o.getClass().getName();

		if (o instanceof String || cname.equals("java.lang.String") || o instanceof StringBuffer
				|| cname.equals("java.lang.StringBuffer") || o instanceof StringBuilder
				|| cname.equals("java.lang.StringBuilder")) {
			return Integer.parseInt(o.toString());

		}
		if (o instanceof Double || cname.equals("java.lang.Double")) {
			return Integer.parseInt(o.toString());
		}
		if (o instanceof Integer || cname.equals("java.lang.Integer")) {
			return Integer.parseInt(o.toString());
		}
		if (o instanceof Float || cname.equals("java.lang.Float")) {
			return Integer.parseInt(o.toString());
		}
		if (o instanceof Long || cname.equals("java.lang.Long")) {
			return Integer.parseInt(o.toString());
		}
		if (o instanceof BigDecimal || cname.equals("java.math.BigDecimal")) {
			return Integer.parseInt(o.toString());
		}
		if (o instanceof DataObject || cname.equals(DataObject.class.getName())) {
			return Integer.parseInt(o.toString());
		}
		if (o instanceof DataStore || cname.equals(DataStore.class.getName())) {
			return Integer.parseInt(o.toString());
		}
		if (o instanceof Boolean || cname.equals("java.lang.Boolean")) {
			return Integer.parseInt(o.toString());
		}
		if (o instanceof Date || cname.equals("java.util.Date")) {

			return Integer.parseInt(DateUtil.formatDate((Date) o, "yyyyMMddHH"));
		}
		if (o instanceof java.sql.Date || cname.equals("java.sql.Date")) {
			Date d = new Date(((java.sql.Date) o).getTime());
			return Integer.parseInt(DateUtil.formatDate(d, "yyyyMMddHH"));
		}
		if (o instanceof java.sql.Timestamp || cname.equals("java.sql.Timestamp")) {
			Date d = new Date(((java.sql.Timestamp) o).getTime());
			return Integer.parseInt(DateUtil.formatDate(d, "yyyyMMddHH"));
		}

		return 0;
	}

	public static float objectToFloat(Object o) {
		if (null == o) {
			return 0;
		}
		String cname = o.getClass().getName();

		if (o instanceof String || cname.equals("java.lang.String") || o instanceof StringBuffer
				|| cname.equals("java.lang.StringBuffer") || o instanceof StringBuilder
				|| cname.equals("java.lang.StringBuilder")) {
			return Float.parseFloat(o.toString());

		}
		if (o instanceof Double || cname.equals("java.lang.Double")) {
			return Float.parseFloat(o.toString());
		}
		if (o instanceof Integer || cname.equals("java.lang.Integer")) {
			return Float.parseFloat(o.toString());
		}
		if (o instanceof Float || cname.equals("java.lang.Float")) {
			return Float.parseFloat(o.toString());
		}
		if (o instanceof Long || cname.equals("java.lang.Long")) {
			return Float.parseFloat(o.toString());
		}
		if (o instanceof BigDecimal || cname.equals("java.math.BigDecimal")) {
			return Float.parseFloat(o.toString());
		}
		if (o instanceof DataObject || cname.equals(DataObject.class.getName())) {
			return Float.parseFloat(o.toString());
		}
		if (o instanceof DataStore || cname.equals(DataStore.class.getName())) {
			return Float.parseFloat(o.toString());
		}
		if (o instanceof Boolean || cname.equals("java.lang.Boolean")) {
			return Float.parseFloat(o.toString());
		}
		if (o instanceof Date || cname.equals("java.util.Date")) {

			return Float.parseFloat(DateUtil.formatDate((Date) o, "yyyyMMddHHmmss"));
		}
		if (o instanceof java.sql.Date || cname.equals("java.sql.Date")) {
			Date d = new Date(((java.sql.Date) o).getTime());
			return Float.parseFloat(DateUtil.formatDate(d, "yyyyMMddHHmmss"));
		}
		if (o instanceof java.sql.Timestamp || cname.equals("java.sql.Timestamp")) {
			Date d = new Date(((java.sql.Timestamp) o).getTime());
			return Float.parseFloat(DateUtil.formatDate(d, "yyyyMMddHHmmss"));
		}

		return 0;
	}

	public static long objectToLong(Object o) {
		if (null == o) {
			return 0;
		}
		String cname = o.getClass().getName();

		if (o instanceof String || cname.equals("java.lang.String") || o instanceof StringBuffer
				|| cname.equals("java.lang.StringBuffer") || o instanceof StringBuilder
				|| cname.equals("java.lang.StringBuilder")) {
			return Long.parseLong(o.toString());

		}
		if (o instanceof Double || cname.equals("java.lang.Double")) {
			return Long.parseLong(o.toString());
		}
		if (o instanceof Integer || cname.equals("java.lang.Integer")) {
			return Long.parseLong(o.toString());
		}
		if (o instanceof Float || cname.equals("java.lang.Float")) {
			return Long.parseLong(o.toString());
		}
		if (o instanceof Long || cname.equals("java.lang.Long")) {
			return Long.parseLong(o.toString());
		}
		if (o instanceof BigDecimal || cname.equals("java.math.BigDecimal")) {
			return Long.parseLong(o.toString());
		}
		if (o instanceof DataObject || cname.equals(DataObject.class.getName())) {
			return Long.parseLong(o.toString());
		}
		if (o instanceof DataStore || cname.equals(DataStore.class.getName())) {
			return Long.parseLong(o.toString());
		}
		if (o instanceof Boolean || cname.equals("java.lang.Boolean")) {
			return Long.parseLong(o.toString());
		}
		if (o instanceof Date || cname.equals("java.util.Date")) {

			return Long.parseLong(DateUtil.formatDate((Date) o, "yyyyMMddHHmmss"));
		}
		if (o instanceof java.sql.Date || cname.equals("java.sql.Date")) {
			Date d = new Date(((java.sql.Date) o).getTime());
			return Long.parseLong(DateUtil.formatDate(d, "yyyyMMddHHmmss"));
		}
		if (o instanceof java.sql.Timestamp || cname.equals("java.sql.Timestamp")) {
			Date d = new Date(((java.sql.Timestamp) o).getTime());
			return Long.parseLong(DateUtil.formatDate(d, "yyyyMMddHHmmss"));
		}

		return 0;
	}

	public static double objectToDouble(Object o) {
		if (null == o) {
			return 0;
		}
		String cname = o.getClass().getName();

		if (o instanceof String || cname.equals("java.lang.String") || o instanceof StringBuffer
				|| cname.equals("java.lang.StringBuffer") || o instanceof StringBuilder
				|| cname.equals("java.lang.StringBuilder")) {
			return Double.parseDouble(o.toString());

		}
		if (o instanceof Double || cname.equals("java.lang.Double")) {
			return Double.parseDouble(o.toString());
		}
		if (o instanceof Integer || cname.equals("java.lang.Integer")) {
			return Double.parseDouble(o.toString());
		}
		if (o instanceof Float || cname.equals("java.lang.Float")) {
			return Double.parseDouble(o.toString());
		}
		if (o instanceof Long || cname.equals("java.lang.Long")) {
			return Double.parseDouble(o.toString());
		}
		if (o instanceof BigDecimal || cname.equals("java.math.BigDecimal")) {
			return Double.parseDouble(o.toString());
		}
		if (o instanceof DataObject || cname.equals(DataObject.class.getName())) {
			return Double.parseDouble(o.toString());
		}
		if (o instanceof DataStore || cname.equals(DataStore.class.getName())) {
			return Double.parseDouble(o.toString());
		}
		if (o instanceof Boolean || cname.equals("java.lang.Boolean")) {
			return Double.parseDouble(o.toString());
		}
		if (o instanceof Date || cname.equals("java.util.Date")) {

			return Double.parseDouble(DateUtil.formatDate((Date) o, "yyyyMMddHHmmss"));
		}
		if (o instanceof java.sql.Date || cname.equals("java.sql.Date")) {
			Date d = new Date(((java.sql.Date) o).getTime());
			return Double.parseDouble(DateUtil.formatDate(d, "yyyyMMddHHmmss"));
		}
		if (o instanceof java.sql.Timestamp || cname.equals("java.sql.Timestamp")) {
			Date d = new Date(((java.sql.Timestamp) o).getTime());
			return Double.parseDouble(DateUtil.formatDate(d, "yyyyMMddHHmmss"));
		}

		return 0;
	}

	public static boolean objectToBoolean(Object o) throws AppException {
		String object = objectToString(o);
		return "true".equals(object);
	}

	public static Date objectToDate(Object o) throws AppException {
		if (null == o) {
			return null;
		}
		String cname = o.getClass().getName();

		if (o instanceof Date || cname.equals("java.util.Date")) {

			return (Date) o;
		}
		if (o instanceof java.sql.Date || cname.equals("java.sql.Date")) {
			Date d = new Date(((java.sql.Date) o).getTime());
			return d;
		}
		if (o instanceof java.sql.Timestamp || cname.equals("java.sql.Timestamp")) {
			Date d = new Date(((java.sql.Timestamp) o).getTime());
			return d;
		}
		String object = objectToString(o);

		return DateUtil.formatStrToDate(object);
	}

	public static BigDecimal objectToBigDecimal(Object o) throws AppException {

		return new BigDecimal(objectToString(o));
	}

	public static void main(String[] args) throws AppException {
		Date str = (Date) getValueByType(TypeUtil.DATE, new java.sql.Timestamp(new Date().getTime()));
		System.out.println(str);
	}

}
