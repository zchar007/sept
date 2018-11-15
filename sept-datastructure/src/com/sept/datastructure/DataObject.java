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
 * �µ�DataObject ,ȡֵʱ������������
 * 
 * @author zchar
 *
 */
public class DataObject extends HashMap<String, Object> implements Serializable {
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<String, String> typeList = null;
	protected boolean isLowerKey = false;// Ĭ�϶�����key����Сд������

	public DataObject() {
		typeList = new LinkedHashMap<String, String>();
	}

	// �������Ƿ�keyȫ����ΪСд
	public DataObject(boolean isLowerKey) {
		this.isLowerKey = isLowerKey;
		typeList = new LinkedHashMap<String, String>();
	}

	@Override
	public Object put(String key, Object value) {
		// key����Ϊ��
		if (null == key || key.trim().isEmpty()) {
			return null;
		}
		// ����key����Ϊtypelist�������Сд˳�����
		key = getKey(key).trim();
		if (key.toUpperCase().equals("TYPELIST")) {
			return null;
		}
		// ̫��ʱ�ˣ��ⲿ��ɶʱ���ȡɶʱ����
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
	 * ��ȡObject
	 * 
	 * @param key
	 * @return
	 * @throws AppException
	 */
	public Object get(String key) throws AppException {
		if (!this.containsKey(key)) {
			throw new AppException("������keyΪ[" + key + "]��ֵ��");
		}
		return super.get(key);
	}

	/**
	 * ��Ĭ��ֵ�Ļ�ȡObject
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
	 * ��ȡObject
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(String key) {
		return super.get(key);
	}

	/**
	 * ��Ĭ��ֵ�Ļ�ȡObject
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
			throw new AppException("��ȡint���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public double getDouble(String key) throws AppException {
		key = getKey(key);
		try {
			return (double) TypeUtil.getValueByType(TypeUtil.DOUBLE, this.get(key));
		} catch (Exception e) {
			throw new AppException("��ȡdouble���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public long getLong(String key) throws AppException {
		key = getKey(key);
		try {
			return (long) TypeUtil.getValueByType(TypeUtil.LONG, this.get(key));
		} catch (Exception e) {
			throw new AppException("��ȡlong���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public float getFloat(String key) throws AppException {
		key = getKey(key);
		try {
			return (float) TypeUtil.getValueByType(TypeUtil.FLOAT, this.get(key));
		} catch (Exception e) {
			throw new AppException("��ȡfloat���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public String getString(String key) throws AppException {
		key = getKey(key);
		try {
			return (String) TypeUtil.getValueByType(TypeUtil.STRING, this.get(key));
		} catch (Exception e) {
			throw new AppException("��ȡString���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public String getDateToString(String key, String formatStr) throws AppException {
		key = getKey(key);
		try {
			Date d = (Date) TypeUtil.getValueByType(TypeUtil.DATE, this.get(key));
			return DateUtil.formatDate(d, formatStr);
		} catch (Exception e) {
			throw new AppException("��ȡDataStore���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public Date getDate(String key) throws AppException {
		key = getKey(key);
		try {
			return (Date) TypeUtil.getValueByType(TypeUtil.DATE, this.get(key));
		} catch (Exception e) {
			throw new AppException("��ȡDate���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public BigDecimal getBigDecimal(String key) throws AppException {
		key = getKey(key);
		try {
			return (BigDecimal) TypeUtil.getValueByType(TypeUtil.BIGDECIMAL, this.get(key));
		} catch (Exception e) {
			throw new AppException("��ȡBigDecimal���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public boolean getBoolean(String key) throws AppException {
		key = getKey(key);
		try {
			return (boolean) TypeUtil.getValueByType(TypeUtil.BOOLEAN, this.get(key));
		} catch (Exception e) {
			throw new AppException("��ȡboolean���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public Blob getBlob(String key) throws AppException {
		key = getKey(key);
		try {
			return (Blob) TypeUtil.getValueByType(TypeUtil.BLOB, this.get(key));
		} catch (Exception e) {
			throw new AppException("��ȡBlob���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public Clob getClob(String key) throws AppException {
		key = getKey(key);
		try {
			return (Clob) TypeUtil.getValueByType(TypeUtil.CLOB, this.get(key));
		} catch (Exception e) {
			throw new AppException("��ȡClob���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public DataObject getDataObject(String key) throws AppException {
		key = getKey(key);
		try {
			return (DataObject) TypeUtil.getValueByType(TypeUtil.DATAOBJECT, this.get(key));
		} catch (Exception e) {
			throw new AppException("��ȡDataObject���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public DataStore getDataStore(String key) throws AppException {
		key = getKey(key);
		try {
			return (DataStore) TypeUtil.getValueByType(TypeUtil.DATASTORE, this.get(key));
		} catch (Exception e) {
			throw new AppException("��ȡDataStore���͵ġ�" + key + "��ʱ����" + e.getMessage());
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
	 * ��ȡ��������
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
	 * ���ò�������
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
				// ��������ͣ�ֱ�Ӳ���
				if (TypeUtil.isType(value)) {
					this.typeList.put(key, value);
				} else {
					// ����鿴��ǰ�����ֵ,������Ĭ������
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
	 * ��ȡ����
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
	 * ����
	 * 
	 * @param comparator
	 * @return
	 * @throws AppException
	 */
	public boolean filter(Comparator comparator) throws AppException {
		return ComparaUtil.match(this, comparator);
	}

	/**
	 * �ڲ������ã������ж�����
	 * 
	 * @param comparator
	 * @return
	 * @throws AppException
	 */
	public boolean filterWithoutCheck(Comparator comparator) throws AppException {
		return ComparaUtil.matchWithoutCheck(this, comparator);
	}

	/**
	 * ��ǰ���¡ֻ�ܸ���DataStore��DataObject
	 */
	public DataObject clone() {
		// ��Ҫ��д
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
	 * ��ǰ���¡ֻ�ܸ���DataStore��DataObject
	 * 
	 * @throws AppException
	 */
	public DataObject clone(boolean isLowerKey) throws AppException {
		// ��Ҫ��д
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
