package com.sept.datastructure;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sept.datastructure.comparator.ObjectComparator;
import com.sept.datastructure.exception.DataException;
import com.sept.datastructure.util.JSONUtil;
import com.sept.datastructure.util.TypeUtil;
import com.sept.datastructure.util.XMLUtil;
import com.sept.exception.ApplicationException;
import com.sept.util.BooleanUtil;
import com.sept.util.DateUtil;

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
	public synchronized Object put(String key, Object value) {
		// key����Ϊ��
		if (null == key || key.trim().isEmpty()) {
			return null;
		}
		// ����key����Ϊtypelist�������Сд˳�����
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
	 * ��ȡObject
	 * 
	 * @param key
	 * @return
	 * @throws DataException
	 */
	public synchronized Object get(String key) throws DataException {
		if (!this.containsKey(key)) {
			throw new DataException("������keyΪ[" + key + "]��ֵ��");
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
	public synchronized Object get(String key, Object defaultValue) {
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
	public synchronized Object getObject(String key) {
		return super.get(key);
	}

	/**
	 * ��Ĭ��ֵ�Ļ�ȡObject
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

	public synchronized int getInt(String key) throws DataException {
		key = getKey(key);
		try {
			return (int) TypeUtil.getValueByType(TypeUtil.INTEGER,
					this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡint���͵ġ�" + key + "��ʱ����" + e.getMessage());
		}
	}

	public synchronized double getDouble(String key) throws DataException {
		key = getKey(key);
		try {
			return (double) TypeUtil.getValueByType(TypeUtil.DOUBLE,
					this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡdouble���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized long getLong(String key) throws DataException {
		key = getKey(key);
		try {
			return (long) TypeUtil.getValueByType(TypeUtil.LONG, this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡlong���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized float getFloat(String key) throws DataException {
		key = getKey(key);
		try {
			return (float) TypeUtil.getValueByType(TypeUtil.FLOAT,
					this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡfloat���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized String getString(String key) throws DataException {
		key = getKey(key);
		try {
			return (String) TypeUtil.getValueByType(TypeUtil.STRING,
					this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡString���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized String getDateToString(String key, String formatStr)
			throws DataException {
		key = getKey(key);
		try {
			Date d = (Date) TypeUtil.getValueByType(TypeUtil.DATE,
					this.get(key));
			return DateUtil.formatDate(d, formatStr);
		} catch (Exception e) {
			throw new DataException("��ȡDataStore���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized Date getDate(String key) throws DataException {
		key = getKey(key);
		try {
			return (Date) TypeUtil.getValueByType(TypeUtil.DATE, this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡDate���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized BigDecimal getBigDecimal(String key)
			throws DataException {
		key = getKey(key);
		try {
			return (BigDecimal) TypeUtil.getValueByType(TypeUtil.BIGDECIMAL,
					this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡBigDecimal���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized boolean getBoolean(String key) throws DataException {
		key = getKey(key);
		try {
			return (boolean) TypeUtil.getValueByType(TypeUtil.BOOLEAN,
					this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡboolean���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized Blob getBlob(String key) throws DataException {
		key = getKey(key);
		try {
			return (Blob) TypeUtil.getValueByType(TypeUtil.BLOB, this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡBlob���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized Clob getClob(String key) throws DataException {
		key = getKey(key);
		try {
			return (Clob) TypeUtil.getValueByType(TypeUtil.CLOB, this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡClob���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized DataObject getDataObject(String key)
			throws DataException {
		key = getKey(key);
		try {
			return (DataObject) TypeUtil.getValueByType(TypeUtil.DATAOBJECT,
					this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡDataObject���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized DataStore getDataStore(String key) throws DataException {
		key = getKey(key);
		try {
			return (DataStore) TypeUtil.getValueByType(TypeUtil.DATASTORE,
					this.get(key));
		} catch (Exception e) {
			throw new DataException("��ȡDataStore���͵ġ�" + key + "��ʱ����"
					+ e.getMessage());
		}
	}

	public synchronized int getInt(String key, int defaultValue)
			throws DataException {
		key = getKey(key);
		try {
			return (int) TypeUtil.getValueByType(TypeUtil.INTEGER,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public synchronized double getDouble(String key, double defaultValue)
			throws DataException {
		key = getKey(key);
		try {
			return (double) TypeUtil.getValueByType(TypeUtil.DOUBLE,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized long getLong(String key, long defaultValue)
			throws DataException {
		key = getKey(key);
		try {
			return (long) TypeUtil.getValueByType(TypeUtil.LONG, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized float getFloat(String key, float defaultValue)
			throws DataException {
		key = getKey(key);
		try {
			return (float) TypeUtil.getValueByType(TypeUtil.FLOAT,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized String getString(String key, String defaultValue)
			throws DataException {
		key = getKey(key);
		try {
			return (String) TypeUtil.getValueByType(TypeUtil.STRING,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized String getDateToString(String key, String formatStr,
			String defaultValue) throws DataException {
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
			throws DataException {
		key = getKey(key);
		try {
			return (Date) TypeUtil.getValueByType(TypeUtil.DATE, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized BigDecimal getBigDecimal(String key,
			BigDecimal defaultValue) throws DataException {
		key = getKey(key);
		try {
			return (BigDecimal) TypeUtil.getValueByType(TypeUtil.BIGDECIMAL,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized boolean getBoolean(String key, boolean defaultValue)
			throws DataException {
		key = getKey(key);
		try {
			return (boolean) TypeUtil.getValueByType(TypeUtil.BOOLEAN,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized Blob getBlob(String key, Blob defaultValue)
			throws DataException {
		key = getKey(key);
		try {
			return (Blob) TypeUtil.getValueByType(TypeUtil.BLOB, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized Clob getClob(String key, Clob defaultValue)
			throws DataException {
		key = getKey(key);
		try {
			return (Clob) TypeUtil.getValueByType(TypeUtil.CLOB, this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized DataObject getDataObject(String key,
			DataObject defaultValue) throws DataException {
		key = getKey(key);
		try {
			return (DataObject) TypeUtil.getValueByType(TypeUtil.DATAOBJECT,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	public synchronized DataStore getDataStore(String key,
			DataStore defaultValue) throws DataException {
		key = getKey(key);
		try {
			return (DataStore) TypeUtil.getValueByType(TypeUtil.DATASTORE,
					this.get(key));
		} catch (Exception e) {
			return defaultValue;

		}
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param key
	 * @return
	 * @throws DataException
	 */
	public synchronized String getType(String key) throws DataException {
		if (this.typeList.containsKey(key)) {
			return this.typeList.get(key);
		}
		if (this.containsKey(key)) {
			TypeUtil.getValueType(this.get(key));
		}
		return null;
	}

	/**
	 * ���ò�������
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
			throws DataException {
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
			throw new DataException(e);
		}
	}

	public synchronized LinkedHashMap<String, String> getTypelistByList() {
		return typeList;

	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 * @throws DataException
	 */
	public synchronized String getTypeList() throws DataException {
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

	public synchronized boolean filter(DataObject para) throws DataException, ApplicationException {
		@SuppressWarnings("unchecked")
		ArrayList<String> logicArray = (ArrayList<String>) para
				.getObject("logicArray");
		@SuppressWarnings("unchecked")
		ArrayList<DataObject> booleanArray = (ArrayList<DataObject>) para
				.getObject("booleanArray");
		/**
		 * ��������null logicArray��Ϊ�� (Ϊ����ôbooleanArray���Ȳ��ܴ���1) booleanArray ��Ϊ��
		 * Ϊ���򷵻�true(Ĭ����ͬ)
		 */
		if (null == logicArray) {
			throw new DataException(this.getClass().getName()
					+ ":����logicArrayΪnull");
		}
		if (null == booleanArray) {
			throw new DataException(this.getClass().getName()
					+ ":����logicArrayΪnull");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new DataException(this.getClass().getName() + ":������ƥ��");
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
	 * ��ǰ���¡ֻ�ܸ���DataStore��DataObject
	 */
	public synchronized DataObject clone() {
		// ��Ҫ��д
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
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doTemp;
	}

	/**
	 * ��ǰ���¡ֻ�ܸ���DataStore��DataObject
	 * 
	 * @throws DataException
	 */
	public synchronized DataObject clone(boolean isLowerKey)
			throws DataException {
		// ��Ҫ��д
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

	public synchronized static void main(String[] args) throws DataException {
		DataObject pdo = new DataObject();

		pdo.put("ny", DateUtil.getCurrentDate());

		System.out.println(pdo.getDateToString("ny", "yyyy-MM-dd HH:mm:ss"));

	}

	public String toJSON() throws DataException, ApplicationException {
		return JSONUtil.toJson(this);
	}

	public String toXML() throws DataException {
		return XMLUtil.DataObjectToXmlString(this);
	}

	public final static DataObject parseFromJSON(String jsonStr)
			throws DataException {
		return JSONUtil.JsonToDataObject(jsonStr);

	}

	public final static DataObject parseFromXML(String xmlStr)
			throws DataException, ApplicationException {
		return XMLUtil.XmlToDataObject(xmlStr);
	}
}
