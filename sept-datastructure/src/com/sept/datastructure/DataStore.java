package com.sept.datastructure;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sept.datastructure.comparator.DataObjectComparator;
import com.sept.datastructure.comparator.DataObjectMultiComparator;
import com.sept.datastructure.exception.DataException;
import com.sept.datastructure.util.JSONUtil;
import com.sept.datastructure.util.TypeUtil;
import com.sept.datastructure.util.XMLUtil;
import com.sept.exception.ApplicationException;
import com.sept.util.DateUtil;
import com.sept.util.FilterUtil;
import com.sept.util.MathUtil;

public class DataStore extends ArrayList<DataObject> implements Serializable {
	protected boolean isLowerKey = false;// Ĭ�϶�����key������Сд������
	// ɸѡ���
	private Object filterKey = new Object();
	private ArrayList<Integer> filterAl = new ArrayList<Integer>();
	private int nowIndex = 0;
	private ExecutorService fixedThreadPool;
	private static int minSerchCount = 3000;

	public DataStore() {
	}

	public DataStore(boolean isLowerKey) {
		this.isLowerKey = isLowerKey;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public synchronized boolean addRow() {
		return this.add(new DataObject(this.isLowerKey));
	}

	public synchronized boolean addRow(DataObject vdo) throws DataException {
		if (null == vdo) {
			throw new DataException("DataStore ���ɴ���Ϊ null �� DataObject");
		}
		vdo.setLowerKey(isLowerKey);
		return this.add(vdo);
	}

	public synchronized boolean addAll(DataStore vds) throws DataException {
		if (null == vds) {
			throw new DataException("DataStore ���ɴ���Ϊ null �� DataStore");
		}
		for (int i = 0; i < vds.rowCount(); i++) {
			this.addRow(vds.get(i).clone());
		}
		return true;
	}

	public synchronized Object put(int row, String key, Object value) throws DataException {
		this.checkRow("����λ��", row);
		return this.getRow(row).put(key, value);
	}

	public synchronized DataObject getRow(int row) throws DataException {
		this.checkRow("��ȡλ��", row);
		return this.get(row);
	}

	// String
	public synchronized String getString(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getString(key);
	}

	public synchronized String getString(int row, String key, String defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getString(key, defaul);
	}

	// Integer
	public synchronized int getInt(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getInt(key);
	}

	public synchronized int getInt(int row, String key, int defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getInt(key, defaul);
	}

	// Double
	public synchronized double getDouble(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDouble(key);
	}

	public synchronized double getDouble(int row, String key, double defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDouble(key, defaul);
	}

	// Float
	public synchronized float getFloat(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getFloat(key);
	}

	public synchronized float getFloat(int row, String key, float defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getFloat(key, defaul);
	}

	// Long
	public synchronized long getLong(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getLong(key);
	}

	public synchronized long getLong(int row, String key, long defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getLong(key, defaul);
	}

	// Long
	public synchronized BigDecimal getBigDecimal(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBigDecimal(key);
	}

	public synchronized BigDecimal getBigDecimal(int row, String key, BigDecimal defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBigDecimal(key, defaul);
	}

	// Blob
	public synchronized Blob getBlob(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBlob(key);
	}

	public synchronized Blob getBlob(int row, String key, Blob defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBlob(key, defaul);
	}

	public synchronized Clob getClob(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getClob(key);
	}

	public synchronized Clob getClob(int row, String key, Clob defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getClob(key, defaul);
	}

	// Date
	public synchronized Date getDate(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDate(key);
	}

	public synchronized Date getDate(int row, String key, Date defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDate(key, defaul);
	}

	// Boolean
	public synchronized boolean getBoolean(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBoolean(key);
	}

	public synchronized boolean getBoolean(int row, String key, boolean defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBoolean(key, defaul);
	}

	// DataStore
	public synchronized DataStore getDataStore(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDataStore(key);
	}

	public synchronized DataStore getDataStore(int row, String key, DataStore defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDataStore(key, defaul);
	}

	// DataObject
	public synchronized DataObject getDataObject(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDataObject(key);
	}

	public synchronized DataObject getDataObject(int row, String key, DataObject defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDataObject(key, defaul);
	}

	// Object
	public synchronized Object getObject(int row, String key) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getObject(key);
	}

	public synchronized Object getObject(int row, String key, Object defaul) throws DataException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getObject(key, defaul);
	}

	private void checkRow(String message, int row) throws DataException {
		if (row >= this.size()) {
			throw new DataException(message + ":[" + row + "] �����˱� DataStore���������" + (this.size() - 1));
		}
	}

	public synchronized void insertRow(int row, DataObject vdo) throws DataException {
		if (row > this.size()) {
			throw new DataException("�������λ�� �� " + row + " �����˱� DataStore����һ������" + this.size());
		}
		if (null == vdo) {
			throw new DataException("DataStore ���ɴ���Ϊ null �� DataObject");
		}
		if (row == this.size()) {
			this.add(vdo);
			return;
		}

		this.addRow();
		for (int i = this.size() - 1; i > row; i++) {
			this.set(i, this.get(i - 1));
		}
		this.set(row, vdo);

	}

	public synchronized Object delRow(int row) throws DataException {
		this.checkRow("ɾ������", row);
		return super.remove(row);
	}

	public synchronized boolean containsItem(int row, String column) throws DataException {
		return getRow(row).containsKey(column);
	}

	// �������
	/**
	 * == and <> a == 0 and x <> 0 and
	 * 
	 * @param fliterStr
	 * @param isModifyMine �Ƿ��޸��������ݣ�true�޸ģ�false���޸�
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public synchronized DataStore filter(String conditions, boolean isModifyMine)
			throws DataException, ApplicationException {
		DataStore vdsReturn = new DataStore(this.isLowerKey);
		int[] all = this.findAll(conditions);
		for (int i = 0; i < all.length; i++) {
			vdsReturn.addRow(this.get(all[i]));
		}
		if (isModifyMine) {
			this.clear();
			this.addAll(vdsReturn);
		}
		return vdsReturn;
	}

	/**
	 * ɸѡ���޸�����
	 * 
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public synchronized DataStore filterModify(String conditions) throws Exception {
		return this.filter(conditions, true);
	}

	/**
	 * ɸѡ���ݣ�����ɸѡ�������ԭ���ݲ����޸�
	 * 
	 * @param conditions
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public synchronized DataStore filterKeep(String conditions) throws DataException, ApplicationException {
		return this.filter(conditions, false);
	}

	/**
	 * �������з������������������� == and <> a == 0 and x <> 0 and ���޸��������ݣ�����row��
	 * 
	 * @return
	 * @throws DataException
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public synchronized int[] findAll(String conditions) throws DataException, ApplicationException {
		// Date d1 = new Date();
		ArrayList<Integer> al = new ArrayList<Integer>();
		DataObject pdo = new DataObject();
		pdo.putAll(FilterUtil.filter(conditions));
		@SuppressWarnings("unchecked")
		ArrayList<DataObject> booleanArray = (ArrayList<DataObject>) pdo.getObject("booleanArray");

		// System.out.println(pdo);
		for (int i = 0; i < booleanArray.size(); i++) {
			DataObject exp = booleanArray.get(i);
			String colName = exp.getString("cmpKey");
			String cmpValue = exp.getString("cmpValue");
			String colType = getColumnType(colName);
			if (colType.equals(TypeUtil.DATE)) {
				exp.put("cmpRealValue", DateUtil.formatStrToDate(cmpValue));
			} else if (colType.equals(TypeUtil.NUMBER)) {
				exp.put("cmpRealValue", MathUtil.round(Double.parseDouble(cmpValue), 10));
			} else if (colType.equals(TypeUtil.BOOLEAN)) {
				exp.put("cmpRealValue", Boolean.parseBoolean(cmpValue));
			} else if (colType.equals(TypeUtil.STRING)) {
				exp.put("cmpRealValue", cmpValue);
			} else {
				throw new DataException("��" + colName + "����Ϊ" + colType + "��֧�ּ���!");
			}
		}
		pdo.put("booleanArray", booleanArray);

		// Date d2 = new Date();
		// System.out.println("�㹫ʽ��ʱ--"+UnitConversionUtils.MillisecondToAnySuitableUnit(d2.getTime()-d1.getTime()));
		DataStore vds = null;
		int index = 0;
		fixedThreadPool = Executors.newFixedThreadPool(1000);
		int onceCount = minSerchCount;
		// ������ݳ���3��Ҳ���ǰ�ԭ����������10���̣߳���ô
		if (this.rowCount() / minSerchCount > 10) {
			onceCount = this.rowCount() / 10;
		}
		while ((vds = this.subDataStore(index, index + onceCount)).rowCount() == onceCount) {
			CalThread ct = new CalThread(pdo, vds, index);
			fixedThreadPool.execute(ct);
			index += vds.rowCount();
		}
		if (vds.rowCount() != onceCount && vds.rowCount() != 0) {
			CalThread ct = new CalThread(pdo, vds, index);
			fixedThreadPool.execute(ct);
		}

		while (!getIsFinsh()) {
		}
		al = this.getIndex();

		// ��ʼ�����в�����
		fixedThreadPool.shutdownNow();
		fixedThreadPool = null;
		vds = null;
		filterAl = new ArrayList<Integer>();
		nowIndex = 0;

		int[] ii = new int[al.size()];
		for (int i = 0; i < al.size(); i++) {
			ii[i] = al.get(i);
		}
		return ii;
	}

	/**
	 * ���ҵ�һ���������������������У�δ�ҵ�����-1 == and <> a == 0 and x <> 0 and ���޸���������
	 * 
	 * @param conditions
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public synchronized int find(String conditions) throws DataException, ApplicationException {
		DataObject pdo = new DataObject();
		pdo.putAll(FilterUtil.filter(conditions));
		@SuppressWarnings("unchecked")
		ArrayList<DataObject> booleanArray = (ArrayList<DataObject>) pdo.getObject("booleanArray");
		for (int i = 0; i < booleanArray.size(); i++) {
			DataObject exp = booleanArray.get(i);
			String colName = exp.getString("cmpKey");
			String cmpValue = exp.getString("cmpValue");
			String colType = getColumnType(colName);
			if (colType.equals("Date")) {
				exp.put("cmpRealValue", DateUtil.formatStrToDate(cmpValue));
			} else if (colType.equals("Number")) {
				exp.put("cmpRealValue", MathUtil.round(Double.parseDouble(cmpValue), 10));
			} else if (colType.equals("Boolean")) {
				exp.put("cmpRealValue", Boolean.parseBoolean(cmpValue));
			} else if (colType.equals("String")) {
				exp.put("cmpRealValue", cmpValue);
			} else {
				throw new DataException("��" + colName + "����Ϊ" + colType + "��֧�ּ���!");
			}
		}
		pdo.put("booleanArray", booleanArray);
		for (int i = this.rowCount() - 1; i >= 0; i--) {
			if (this.get(i).filter(pdo)) {
				return i;
			}
		}

		return -1;
	}

	// //////////////////////////////////////////////
	// ///////����������ɸѡ���ݵ�/////////////////////////
	/**
	 * ���̼߳������
	 * 
	 * @author �ų�
	 * @version 1.0 ����ʱ�� 2017-5-31
	 */
	class CalThread implements Runnable {

		private DataObject pdo = null;
		private DataStore vds = null;
		private int start = 0;
		private ArrayList<Integer> al = new ArrayList<Integer>();

		public CalThread(DataObject pdo, DataStore vds, int start) {
			this.pdo = pdo;
			this.vds = (DataStore) vds.clone();
			this.start = start;
		}

		@Override
		public void run() {
			for (int i = 0; i < vds.rowCount(); i++) {
				try {
					if (vds.get(i).filter(pdo)) {
						this.al.add(i + start);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			setIndex(al, this.vds.rowCount());
		}
	}

	/**
	 * ����ɸѡ�õ�index
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-31
	 * @since V1.0
	 */
	private void setIndex(ArrayList<Integer> indexs, int allIndex) {
		synchronized (filterKey) {
			this.filterAl.addAll(indexs);
			this.nowIndex += allIndex;
		}

	}

	/**
	 * ��ȡ����õķ�����������������index
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-31
	 * @since V1.0
	 */
	private ArrayList<Integer> getIndex() {
		synchronized (filterAl) {
			return this.filterAl;
		}

	}

	/**
	 * ��ȡ���̼߳����Ƿ����
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-31
	 * @since V1.0
	 */
	private boolean getIsFinsh() {
		synchronized (filterAl) {
			return this.rowCount() == this.nowIndex;
		}

	}

	/**
	 * ���������ֶ�,��С��������
	 * 
	 * @param column
	 */
	public synchronized DataStore sortASC(String colName) {
		if (rowCount() == 0) {
			return this;
		}
		Collections.sort(this, new DataObjectComparator(colName));
		return this;
	}

	/**
	 * ���������ֶδӴ�С����
	 * 
	 * @param column
	 */
	public synchronized DataStore sortDESC(String colName) {
		if (rowCount() == 0) {
			return this;
		}
		Collections.sort(this, Collections.reverseOrder(new DataObjectComparator(colName)));

		return this;
	}

	/**
	 * ��ȡ���vds
	 * 
	 * @author �ų�
	 * @throws DataException
	 * @date ����ʱ�� 2017-5-31
	 * @since V1.0
	 */
	public synchronized DataStore subDataStore(int fromIndex, int toIndex) throws DataException {
		DataStore vds = new DataStore(this.isLowerKey);
		for (int i = fromIndex; i < toIndex; i++) {
			if (i < this.rowCount()) {
				vds.addRow(this.get(i));
			} else {
				break;
			}
		}
		return vds;
	}

	public synchronized DataStore clone() {
		DataStore dsTemp = new DataStore(isLowerKey);
		try {
			dsTemp.addAll(this);
		} catch (DataException e) {
			dsTemp = null;
		}

		return dsTemp;
	}

	public synchronized DataStore clone(boolean isLowerKey) {
		DataStore dsTemp = new DataStore(isLowerKey);
		try {
			dsTemp.addAll(this);
		} catch (DataException e) {
			dsTemp = null;
		}

		return dsTemp;
	}

	/**
	 * ��ȡĳ�е�����
	 * 
	 * @param colName
	 * @return
	 * @throws DataException
	 */
	private String getColumnType(String colName) throws DataException {
		if (this.rowCount() <= 0) {
			throw new DataException("��DataStore����Ϊ0");
		}
		DataObject vDataObject = this.get(0);
		String colType = vDataObject.getType(colName);
		return colType;
	}

	public synchronized void setTypeList(LinkedHashMap<String, String> typeList) {
		for (int i = 0; i < this.rowCount(); i++) {
			this.get(i).setTypeList(typeList);

		}
	}

	public synchronized void setTypeList(String typeListString) throws DataException {
		for (int i = 0; i < this.rowCount(); i++) {
			this.get(i).setTypeList(typeListString);

		}
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 * @throws DataException
	 */
	public synchronized String getTypeList() throws DataException {
		if (this.rowCount() <= 0) {
			throw new DataException("��DataStore����Ϊ0");
		}
		DataObject vDataObject = this.get(0);
		return vDataObject.getTypeList();
	}

	public synchronized int setRowCount(int rowCount) throws DataException {
		if (rowCount <= 0) {
			this.clear();
		}
		int nowCount = this.rowCount();

		for (int i = nowCount - 1; i >= 0; i--) {
			int index = i + 1;
			if (index > rowCount) {
				this.delRow(i);
			}
		}
		while (this.rowCount() < rowCount) {
			this.addRow();
		}

		return rowCount;
	}

	/**
	 * ��ȡds��С
	 * 
	 * @return
	 */
	public synchronized int rowCount() {
		return this.size();
	}

	public synchronized String toJSON() throws DataException, ApplicationException {
		return JSONUtil.toJson(this);
	}

	public synchronized String toXML() throws DataException {
		return XMLUtil.DataStoreToXmlString(this);
	}

	public synchronized final static DataStore parseFromJSON(String jsonStr) throws DataException {
		return JSONUtil.JsonToDataStore(jsonStr);

	}

	public synchronized final static DataStore parseFromXML(String xmlStr) throws DataException, ApplicationException {
		return XMLUtil.XmlToDataStore(xmlStr);
	}

	public void insertRowWithDefaultColumns(int row) throws DataException {
		this.insertRow(row, getRowWithDefaultColumns());
	}

	public void addRowWithDefaultColumns() throws DataException {
		addRow(getRowWithDefaultColumns());
	}

	private DataObject getRowWithDefaultColumns() throws DataException {
		DataObject dob = new DataObject();
		// �Զ���typelist�и��ж���Ϊ��ֵ
		LinkedHashMap<String, String> all = this.get(0).getTypelistByList();
		for (String key : all.keySet()) {
			dob.put(key, null);
		}
		return dob;
	}

	public DataStore multiSort(String conditionString) throws DataException {
		if (this.rowCount() == 0)
			return this;
		if (conditionString == null || conditionString.equals(""))
			return this;
		String[] conditions = conditionString.split(",");
		String[] cols = new String[conditions.length];
		String[] colOrders = new String[conditions.length];
		for (int i = 0; i < conditions.length; i++) {
			String[] tmp = conditions[i].split(":");
			cols[i] = tmp[0];
			colOrders[i] = tmp[1];
		}
		Collections.sort(this, new DataObjectMultiComparator(cols, colOrders));
		return this;
	}

	@Override
	public synchronized void clear() {
		super.clear();
	}

}
