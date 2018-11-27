package com.sept.datastructure;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.sept.datastructure.util.JSONUtil;
import com.sept.datastructure.util.TypeUtil;
import com.sept.datastructure.util.XMLUtil;
import com.sept.exception.AppException;
import com.sept.util.DateUtil;
import com.sept.util.bools.comparator.Comparator;
import com.sept.util.compara.ComparaUtil;

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
	public Object put(String key, Object value) {
		// key不能为空
		if (null == key || key.trim().isEmpty()) {
			return null;
		}
		// 数据key不能为typelist的任意大小写顺序组合
		key = getKey(key).trim();
		if (key.toUpperCase().equals("TYPELIST")) {
			return null;
		}
		// 太耗时了，这部分啥时候获取啥时候用
//		String type = TypeUtil.getValueType(value);
//		this.typeList.put(key, type);
		return super.put(key, value);
	}

//	@Override
//	public void putAll(Map<? extends String, ? extends Object> map) {
//		super.putAll(map);
//		for (Entry<String, Object> entry : this.entrySet()) {
//			String key = entry.getKey();
//			Object obj = entry.getValue();
//			this.put(key, obj);
//		}
//	}

	/**
	 * 获取Object
	 * 
	 * @param key
	 * @return
	 * @throws AppException
	 */
	public Object get(String key) throws AppException {
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
	public Object get(String key, Object defaultValue) {
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
	public Object getObject(String key) {
		return super.get(key);
	}

	/**
	 * 带默认值的获取Object
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Object getObject(String key, Object defaultValue) {
		try {
			return super.get(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public int getInt(String key) throws AppException {
		key = getKey(key);
		try {
			return (int) TypeUtil.getValueByType(TypeUtil.INTEGER, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取int类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public double getDouble(String key) throws AppException {
		key = getKey(key);
		try {
			return (double) TypeUtil.getValueByType(TypeUtil.DOUBLE, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取double类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public long getLong(String key) throws AppException {
		key = getKey(key);
		try {
			return (long) TypeUtil.getValueByType(TypeUtil.LONG, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取long类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public float getFloat(String key) throws AppException {
		key = getKey(key);
		try {
			return (float) TypeUtil.getValueByType(TypeUtil.FLOAT, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取float类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public String getString(String key) throws AppException {
		key = getKey(key);
		try {
			return (String) TypeUtil.getValueByType(TypeUtil.STRING, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取String类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public String getDateToString(String key, String formatStr) throws AppException {
		key = getKey(key);
		try {
			Date d = (Date) TypeUtil.getValueByType(TypeUtil.DATE, this.get(key));
			return DateUtil.formatDate(d, formatStr);
		} catch (Exception e) {
			throw new AppException("获取DataStore类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public Date getDate(String key) throws AppException {
		key = getKey(key);
		try {
			return (Date) TypeUtil.getValueByType(TypeUtil.DATE, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取Date类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public BigDecimal getBigDecimal(String key) throws AppException {
		key = getKey(key);
		try {
			return (BigDecimal) TypeUtil.getValueByType(TypeUtil.BIGDECIMAL, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取BigDecimal类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public boolean getBoolean(String key) throws AppException {
		key = getKey(key);
		try {
			return (boolean) TypeUtil.getValueByType(TypeUtil.BOOLEAN, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取boolean类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public Blob getBlob(String key) throws AppException {
		key = getKey(key);
		try {
			return (Blob) TypeUtil.getValueByType(TypeUtil.BLOB, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取Blob类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public Clob getClob(String key) throws AppException {
		key = getKey(key);
		try {
			return (Clob) TypeUtil.getValueByType(TypeUtil.CLOB, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取Clob类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public DataObject getDataObject(String key) throws AppException {
		key = getKey(key);
		try {
			return (DataObject) TypeUtil.getValueByType(TypeUtil.DATAOBJECT, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取DataObject类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public DataStore getDataStore(String key) throws AppException {
		key = getKey(key);
		try {
			return (DataStore) TypeUtil.getValueByType(TypeUtil.DATASTORE, this.get(key));
		} catch (Exception e) {
			throw new AppException("获取DataStore类型的【" + key + "】时出错：" + e.getMessage());
		}
	}

	public int getInt(String key, int defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (int) TypeUtil.getValueByType(TypeUtil.INTEGER, this.get(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public double getDouble(String key, double defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (double) TypeUtil.getValueByType(TypeUtil.DOUBLE, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public long getLong(String key, long defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (long) TypeUtil.getValueByType(TypeUtil.LONG, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public float getFloat(String key, float defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (float) TypeUtil.getValueByType(TypeUtil.FLOAT, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public String getString(String key, String defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (String) TypeUtil.getValueByType(TypeUtil.STRING, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public String getDateToString(String key, String formatStr, String defaultValue) throws AppException {
		key = getKey(key);
		try {
			Date d = (Date) TypeUtil.getValueByType(TypeUtil.DATE, this.get(key));
			return DateUtil.formatDate(d, formatStr);
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public Date getDate(String key, Date defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (Date) TypeUtil.getValueByType(TypeUtil.DATE, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (BigDecimal) TypeUtil.getValueByType(TypeUtil.BIGDECIMAL, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public boolean getBoolean(String key, boolean defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (boolean) TypeUtil.getValueByType(TypeUtil.BOOLEAN, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public Blob getBlob(String key, Blob defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (Blob) TypeUtil.getValueByType(TypeUtil.BLOB, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public Clob getClob(String key, Clob defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (Clob) TypeUtil.getValueByType(TypeUtil.CLOB, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public DataObject getDataObject(String key, DataObject defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (DataObject) TypeUtil.getValueByType(TypeUtil.DATAOBJECT, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public DataStore getDataStore(String key, DataStore defaultValue) throws AppException {
		key = getKey(key);
		try {
			return (DataStore) TypeUtil.getValueByType(TypeUtil.DATASTORE, this.get(key));
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
	public String getType(String key) throws AppException {
		if (this.typeList.containsKey(key)) {
			return this.typeList.get(key);
		}
		if (this.containsKey(key)) {
			return TypeUtil.getValueType(this.get(key));
		}
		return null;
	}

	/**
	 * 设置参数类型
	 * 
	 * @param key
	 * @param type
	 */
	public void setType(String key, String type) {
		if (this.typeList.containsKey(key)) {
			this.typeList.remove(key);
		}
		this.typeList.put(key, type);
	}

	public void setTypeList(LinkedHashMap<String, String> typeList) {
		this.typeList = typeList;
	}

	public void setTypeList(String typeListString) throws AppException {
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

	public LinkedHashMap<String, String> getTypelistByList() throws AppException {
		for (Entry<String, Object> entry : this.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (!this.typeList.containsKey(key)) {
				this.typeList.put(key, TypeUtil.getValueType(value));
			}
		}
		return typeList;

	}

	/**
	 * 获取类型
	 * 
	 * @return
	 * @throws AppException
	 */
	public String getTypeList() throws AppException {
		if (null == typeList) {
			typeList = new LinkedHashMap<String, String>();
		}
		StringBuilder sb = new StringBuilder();

		for (Entry<String, Object> entry : this.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (this.typeList.containsKey(key)) {
				sb.append(key);
				sb.append(":");
				sb.append(typeList.get(key));
				sb.append(",");
			} else {
				sb.append(key);
				sb.append(":");
				sb.append(TypeUtil.getValueType(value));
				sb.append(",");
			}
		}

		if (sb.length() > 2) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	private String getKey(String key) {
		if (null == key) {
			return null;
		}
		return this.isLowerKey ? key.toLowerCase() : key;
	}

	/**
	 * 检索
	 * 
	 * @param comparator
	 * @return
	 * @throws AppException
	 */
	public boolean filter(Comparator comparator) throws AppException {
		return ComparaUtil.match(this, comparator);
	}

	/**
	 * 内部检索用，不会判断类型
	 * 
	 * @param comparator
	 * @return
	 * @throws AppException
	 */
	public boolean filterWithoutCheck(Comparator comparator) throws AppException {
		return ComparaUtil.matchWithoutCheck(this, comparator);
	}

	/**
	 * 当前深克隆只能覆盖DataStore和DataObject
	 */
	public DataObject clone() {
		// 需要重写
		DataObject doTemp = new DataObject(this.isLowerKey);
		doTemp.putAll(this);
		for (Entry<String, Object> entry : this.entrySet()) {
			String key = entry.getKey();
			Object obj = entry.getValue();
			if (obj instanceof DataObject) {
				obj = ((DataObject) obj).clone();
			}
			if (obj instanceof DataStore) {
				obj = ((DataStore) obj).clone();
			}
			doTemp.put(key, obj);
		}
		return doTemp;
	}

	/**
	 * 当前深克隆只能覆盖DataStore和DataObject
	 * 
	 * @throws AppException
	 */
	public DataObject clone(boolean isLowerKey) throws AppException {
		// 需要重写
		DataObject doTemp = new DataObject(isLowerKey);
		doTemp.putAll(this);
		for (Entry<String, Object> entry : this.entrySet()) {
			String key = entry.getKey();
			Object obj = entry.getValue();
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

	public boolean isLowerKey() {
		return isLowerKey;
	}

	protected void setLowerKey(boolean isLowerKey) {
		this.isLowerKey = isLowerKey;
	}

	public static void main(String[] args) throws AppException {
		DataObject pdo = new DataObject();

		pdo.put("ny", DateUtil.getCurrentDate());

		System.out.println(pdo.getString("ny"));

	}

	public String toJSON() throws AppException {
		return JSONUtil.toJson(this);
	}

	public String toXML() throws AppException {
		return XMLUtil.DataObjectToXmlString(this);
	}

	public final static DataObject parseFromJSON(String jsonStr) throws AppException {
		return JSONUtil.JsonToDataObject(jsonStr);

	}

	public final static DataObject parseFromXML(String xmlStr) throws AppException {
		return XMLUtil.XmlToDataObject(xmlStr);
	}
}
