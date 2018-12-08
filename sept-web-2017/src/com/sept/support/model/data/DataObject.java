package com.sept.support.model.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sept.exception.AppException;
import com.sept.support.model.data.filter.ObjectComparator;
import com.sept.support.util.BooleanUtil;
import com.sept.support.util.DateUtil;
import com.sept.support.util.JSONUtil;
import com.sept.support.util.TypeUtil;
import com.sept.support.util.XMLUtil;

/**
 * 新的DataObject ,取值时不被限制类型
 * 
 * @author zchar
 *
 */
public class DataObject extends HashMap<String, Object> implements Serializable {
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<String, String> typeList = null;
	protected boolean isLowerKey = false;// 默认对所有key进行小写化操作

	public DataObject() {
		typeList = new LinkedHashMap<String, String>();
	}

	// 可设置是否将key全部置为小写
	public DataObject(boolean isLowerKey) {
		this.isLowerKey = isLowerKey;
		typeList = new LinkedHashMap<String, String>();
	}

	@Override
	public synchronized Object put(String key, Object value) {
		// key不能为空
		if (null == key || key.trim().isEmpty()) {
			return null;
		}
		// 数据key不能为typelist的任意大小写顺序组合
		key = getKey(key).trim();
		if (key.toUpperCase().equals("TYPELIST")) {
			return null;
		}
		String type = TypeUtil.getValueType(value);
		this.typeList.put(key, type);
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
		for (String key : map.keySet()) {
			this.put(key, map.get(key));

		}
	}

	/**
	 * 获取Object
	 * 
	 * @param key
	 * @return
	 * @throws AppException
	 */
	public synchronized Object get(String key) throws AppException {
		if (!this.containsKey(key)) {
			throw new AppException("不含有key为[" + key + "]的值！");
		}
		return super.get(key);
	}

	/**
	 * 带默认值的获取Object
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public synchronized Object get(String key, Object defaultValue) {
		try {
			return super.get(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 获取Object
	 * 
	 * @param key
	 * @return
	 */
	public synchronized Object getObject(String key) {
		return super.get(key);
	}

	/**
	 * 带默认值的获取Object
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public synchronized Object getObject(String key, Object defaultValue) {
		try {
			return super.get(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public synchronized int getInt(String key) throws AppException {
		key = getKey(key);
		try {
			return (int) TypeUtil.getValueByType(TypeUtil.INTEGER,
					this.get(key));
		} catch (Exception e) {
			throw new AppException("获取int类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public synchronized double getDouble(String key) throws AppException {
		key = getKey(key);
		try {
			return (double) TypeUtil.getValueByType(TypeUtil.DOUBLE,
					this.get(key));
		} catch (Exception e) {
			throw new AppException("获取double类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized long getLong(String key) throws AppException {
		key = getKey(key);
		try {
			return (long) TypeUtil.getValueByType(TypeUtil.LONG, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取long类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized float getFloat(String key) throws AppException {
		key = getKey(key);
		try {
			return (float) TypeUtil.getValueByType(TypeUtil.FLOAT,
					this.get(key));
		} catch (Exception e) {
			throw new AppException("获取float类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized String getString(String key) throws AppException {
		key = getKey(key);
		try {
			return (String) TypeUtil.getValueByType(TypeUtil.STRING,
					this.get(key));
		} catch (Exception e) {
			throw new AppException("获取String类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized String getDateToString(String key, String formatStr)
			throws AppException {
		key = getKey(key);
		try {
			Date d = (Date) TypeUtil.getValueByType(TypeUtil.DATE,
					this.get(key));
			return DateUtil.formatDate(d, formatStr);
		} catch (Exception e) {
			throw new AppException("获取DataStore类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized Date getDate(String key) throws AppException {
		key = getKey(key);
		try {
			return (Date) TypeUtil.getValueByType(TypeUtil.DATE, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取Date类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized BigDecimal getBigDecimal(String key)
			throws AppException {
		key = getKey(key);
		try {
			return (BigDecimal) TypeUtil.getValueByType(TypeUtil.BIGDECIMAL,
					this.get(key));
		} catch (Exception e) {
			throw new AppException("获取BigDecimal类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized boolean getBoolean(String key) throws AppException {
		key = getKey(key);
		try {
			return (boolean) TypeUtil.getValueByType(TypeUtil.BOOLEAN,
					this.get(key));
		} catch (Exception e) {
			throw new AppException("获取boolean类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized Blob getBlob(String key) throws AppException {
		key = getKey(key);
		try {
			return (Blob) TypeUtil.getValueByType(TypeUtil.BLOB, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取Blob类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized Clob getClob(String key) throws AppException {
		key = getKey(key);
		try {
			return (Clob) TypeUtil.getValueByType(TypeUtil.CLOB, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取Clob类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized DataObject getDataObject(String key)
			throws AppException {
		key = getKey(key);
		try {
			return (DataObject) TypeUtil.getValueByType(TypeUtil.DATAOBJECT,
					this.get(key));
		} catch (Exception e) {
			throw new AppException("获取DataObject类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized DataStore getDataStore(String key) throws AppException {
		key = getKey(key);
		try {
			return (DataStore) TypeUtil.getValueByType(TypeUtil.DATASTORE,
					this.get(key));
		} catch (Exception e) {
			throw new AppException("获取DataStore类型的【" + key + "】时出错："
					+ e.getMessage());
		}
	}

	public synchronized int getInt(String key, int defaultValue)
			throws AppException {
		key = getKey(key);
		try {
			return (int) TypeUtil.getValueByType(TypeUtil.INTEGER,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public synchronized double getDouble(String key, double defaultValue)
			throws AppException {
		key = getKey(key);
		try {
			return (double) TypeUtil.getValueByType(TypeUtil.DOUBLE,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized long getLong(String key, long defaultValue)
			throws AppException {
		key = getKey(key);
		try {
			return (long) TypeUtil.getValueByType(TypeUtil.LONG, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized float getFloat(String key, float defaultValue)
			throws AppException {
		key = getKey(key);
		try {
			return (float) TypeUtil.getValueByType(TypeUtil.FLOAT,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized String getString(String key, String defaultValue)
			throws AppException {
		key = getKey(key);
		try {
			return (String) TypeUtil.getValueByType(TypeUtil.STRING,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized String getDateToString(String key, String formatStr,
			String defaultValue) throws AppException {
		key = getKey(key);
		try {
			Date d = (Date) TypeUtil.getValueByType(TypeUtil.DATE,
					this.get(key));
			return DateUtil.formatDate(d, formatStr);
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized Date getDate(String key, Date defaultValue)
			throws AppException {
		key = getKey(key);
		try {
			return (Date) TypeUtil.getValueByType(TypeUtil.DATE, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized BigDecimal getBigDecimal(String key,
			BigDecimal defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (BigDecimal) TypeUtil.getValueByType(TypeUtil.BIGDECIMAL,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized boolean getBoolean(String key, boolean defaultValue)
			throws AppException {
		key = getKey(key);
		try {
			return (boolean) TypeUtil.getValueByType(TypeUtil.BOOLEAN,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized Blob getBlob(String key, Blob defaultValue)
			throws AppException {
		key = getKey(key);
		try {
			return (Blob) TypeUtil.getValueByType(TypeUtil.BLOB, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized Clob getClob(String key, Clob defaultValue)
			throws AppException {
		key = getKey(key);
		try {
			return (Clob) TypeUtil.getValueByType(TypeUtil.CLOB, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized DataObject getDataObject(String key,
			DataObject defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (DataObject) TypeUtil.getValueByType(TypeUtil.DATAOBJECT,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized DataStore getDataStore(String key,
			DataStore defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (DataStore) TypeUtil.getValueByType(TypeUtil.DATASTORE,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	/**
	 * 获取参数类型
	 * 
	 * @param key
	 * @return
	 * @throws AppException
	 */
	public synchronized String getType(String key) throws AppException {
		if (this.typeList.containsKey(key)) {
			return this.typeList.get(key);
		}
		if (this.containsKey(key)) {
			TypeUtil.getValueType(this.get(key));
		}
		return null;
	}

	/**
	 * 设置参数类型
	 * 
	 * @param key
	 * @param type
	 */
	public synchronized void setType(String key, String type) {
		if (this.typeList.containsKey(key)) {
			this.typeList.remove(key);
		}
		this.typeList.put(key, type);
	}

	public synchronized void setTypeList(LinkedHashMap<String, String> typeList) {
		this.typeList = typeList;
	}

	public synchronized void setTypeList(String typeListString)
			throws AppException {
		if (null == typeListString) {
			typeList = new LinkedHashMap<String, String>();
		}
		try {
			String[] tl = typeListString.split(","), tl2 = new String[2];
			String key, value;
			for (String str : tl) {
				tl2 = str.split(":");
				key = tl2[0];
				value = tl2[1];
				key = this.getKey(key);
				// 如果是类型，直接插入
				if (TypeUtil.isType(value)) {
					this.typeList.put(key, value);
				} else {
					// 否则查看当前存入的值,存入其默认类型
					Object o = this.get(key);
					this.typeList.put(key, TypeUtil.getValueType(o));

				}
			}
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public synchronized LinkedHashMap<String, String> getTypelistByList() {
		return typeList;

	}

	/**
	 * 获取类型
	 * 
	 * @return
	 * @throws AppException
	 */
	public synchronized String getTypeList() throws AppException {
		if (null == typeList) {
			typeList = new LinkedHashMap<String, String>();
		}
		String typeStr = "";

		for (String key : this.keySet()) {
			if (this.typeList.containsKey(key)) {
				typeStr += key + ":" + typeList.get(key) + ",";

			} else {
				typeStr += key + ":" + this.getType(key) + ",";
			}
		}

		if (typeStr.length() > 2) {
			typeStr = typeStr.substring(0, typeStr.length() - 1);
		}
		return typeStr;
	}

	private String getKey(String key) {
		if (null == key) {
			return null;
		}
		return this.isLowerKey ? key.toLowerCase() : key;
	}

	public synchronized boolean filter(DataObject para) throws AppException {
		@SuppressWarnings("unchecked")
		ArrayList<String> logicArray = (ArrayList<String>) para
				.getObject("logicArray");
		@SuppressWarnings("unchecked")
		ArrayList<DataObject> booleanArray = (ArrayList<DataObject>) para
				.getObject("booleanArray");
		/**
		 * 均不能是null logicArray可为空 (为空那么booleanArray长度不能大于1) booleanArray 可为空
		 * 为空则返回true(默认相同)
		 */
		if (null == logicArray) {
			throw new AppException(this.getClass().getName()
					+ ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(this.getClass().getName()
					+ ":参数logicArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(this.getClass().getName() + ":参数不匹配");
		}
		ArrayList<String> boolArr = new ArrayList<String>();
		for (int i = 0; i < booleanArray.size(); i++) {
			DataObject vdoTemp = booleanArray.get(i);
			String columnName = vdoTemp.getString("cmpKey");
			String operator = vdoTemp.getString("cmpOperator");
			Object cmpLVal = getObject(columnName, null);

			Object cmpRVal = vdoTemp.getObject("cmpRealValue");

			boolean nowBool = ObjectComparator.compare(cmpLVal, cmpRVal,
					operator);

			boolArr.add(nowBool + "");
		}
		boolean isTrue = BooleanUtil.calBoolean(logicArray, boolArr);
		return isTrue;
	}

	/**
	 * 当前深克隆只能覆盖DataStore和DataObject
	 */
	public synchronized DataObject clone() {
		// 需要重写
		DataObject doTemp = new DataObject(this.isLowerKey);
		doTemp.putAll(this);
		for (String key : this.keySet()) {
			Object obj;
			try {
				obj = this.get(key);

				if (obj instanceof DataObject) {
					obj = ((DataObject) obj).clone();
				}
				if (obj instanceof DataStore) {
					obj = ((DataStore) obj).clone();
				}
				doTemp.put(key, obj);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doTemp;
	}

	/**
	 * 当前深克隆只能覆盖DataStore和DataObject
	 * 
	 * @throws AppException
	 */
	public synchronized DataObject clone(boolean isLowerKey)
			throws AppException {
		// 需要重写
		DataObject doTemp = new DataObject(isLowerKey);
		doTemp.putAll(this);
		for (String key : this.keySet()) {
			Object obj = this.get(key);
			if (obj instanceof DataObject) {
				obj = ((DataObject) obj).clone(isLowerKey);
			}
			if (obj instanceof DataStore) {
				obj = ((DataStore) obj).clone(isLowerKey);
			}
			doTemp.put(key, obj);
		}
		return doTemp;
	}

	public synchronized boolean isLowerKey() {
		return isLowerKey;
	}

	protected void setLowerKey(boolean isLowerKey) {
		this.isLowerKey = isLowerKey;
	}

	public synchronized static void main(String[] args) throws AppException {
		DataObject pdo = new DataObject();

		pdo.put("ny", DateUtil.getCurrentDate());

		System.out.println(pdo.getDateToString("ny", "yyyy-MM-dd HH:mm:ss"));

	}

	public String toJSON() throws AppException {
		return JSONUtil.toJson(this);
	}

	public String toXML() throws AppException {
		return XMLUtil.DataObjectToXmlString(this);
	}

	public final static DataObject parseFromJSON(String jsonStr)
			throws AppException {
		return JSONUtil.JsonToDataObject(jsonStr);

	}

	public final static DataObject parseFromXML(String xmlStr)
			throws AppException {
		return XMLUtil.XmlToDataObject(xmlStr);
	}
}
