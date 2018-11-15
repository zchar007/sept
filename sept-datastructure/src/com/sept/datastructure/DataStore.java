package com.sept.datastructure;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;

import com.sept.datastructure.comparator.DataObjectComparator;
import com.sept.datastructure.comparator.DataObjectMultiComparator;
import com.sept.datastructure.comparator.Datas;
import com.sept.datastructure.util.JSONUtil;
import com.sept.datastructure.util.XMLUtil;
import com.sept.exception.AppException;

public class DataStore extends ArrayList<DataObject> implements Serializable {
	protected boolean isLowerKey = false;// Ĭ�϶�����key������Сд������
	// ɸѡ���
	/*
	 * private Object filterKey = new Object(); private ArrayList<Integer> filterAl
	 * = new ArrayList<Integer>(); private int nowIndex = 0; private ExecutorService
	 * fixedThreadPool; private static int minSerchCount = 1000; private static int
	 * maxSerchCount = 100000;// ÿ���̴߳���10W�����ݻ��ǱȽ��ŵģ��ٶ��˾��±���
	 * 
	 * protected long debug_costTime; protected int debug_threadcount; protected int
	 * debug_every; protected int debug_last;
	 */

	public DataStore() {
	}

	public DataStore(boolean isLowerKey) {
		this.isLowerKey = isLowerKey;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean addRow() {
		return this.add(new DataObject(this.isLowerKey));
	}

	public boolean addRow(DataObject vdo) throws AppException {
		if (null == vdo) {
			throw new AppException("DataStore ���ɴ���Ϊ null �� DataObject");
		}
		vdo.setLowerKey(isLowerKey);
		return this.add(vdo);
	}

	public boolean addAll(DataStore vds) throws AppException {
		if (null == vds) {
			throw new AppException("DataStore ���ɴ���Ϊ null �� DataStore");
		}
//		for (DataObject pdo : vds) {
//			this.addRow(pdo);
//		}
		super.addAll(vds);
		return true;
	}

	public Object put(int row, String key, Object value) throws AppException {
		this.checkRow("����λ��", row);
		return this.getRow(row).put(key, value);
	}

	public DataObject getRow(int row) throws AppException {
		this.checkRow("��ȡλ��", row);
		return this.get(row);
	}

	// String
	public String getString(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getString(key);
	}

	public String getString(int row, String key, String defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getString(key, defaul);
	}

	// Integer
	public int getInt(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getInt(key);
	}

	public int getInt(int row, String key, int defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getInt(key, defaul);
	}

	// Double
	public double getDouble(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDouble(key);
	}

	public double getDouble(int row, String key, double defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDouble(key, defaul);
	}

	// Float
	public float getFloat(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getFloat(key);
	}

	public float getFloat(int row, String key, float defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getFloat(key, defaul);
	}

	// Long
	public long getLong(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getLong(key);
	}

	public long getLong(int row, String key, long defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getLong(key, defaul);
	}

	// Long
	public BigDecimal getBigDecimal(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBigDecimal(key);
	}

	public BigDecimal getBigDecimal(int row, String key, BigDecimal defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBigDecimal(key, defaul);
	}

	// Blob
	public Blob getBlob(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBlob(key);
	}

	public Blob getBlob(int row, String key, Blob defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBlob(key, defaul);
	}

	public Clob getClob(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getClob(key);
	}

	public Clob getClob(int row, String key, Clob defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getClob(key, defaul);
	}

	// Date
	public Date getDate(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDate(key);
	}

	public Date getDate(int row, String key, Date defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDate(key, defaul);
	}

	// Boolean
	public boolean getBoolean(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBoolean(key);
	}

	public boolean getBoolean(int row, String key, boolean defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getBoolean(key, defaul);
	}

	// DataStore
	public DataStore getDataStore(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDataStore(key);
	}

	public DataStore getDataStore(int row, String key, DataStore defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDataStore(key, defaul);
	}

	// DataObject
	public DataObject getDataObject(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDataObject(key);
	}

	public DataObject getDataObject(int row, String key, DataObject defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getDataObject(key, defaul);
	}

	// Object
	public Object getObject(int row, String key) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getObject(key);
	}

	public Object getObject(int row, String key, Object defaul) throws AppException {
		this.checkRow("��ȡ����", row);
		return this.get(row).getObject(key, defaul);
	}

	private void checkRow(String message, int row) throws AppException {
		if (row >= this.size()) {
			throw new AppException(message + ":[" + row + "] �����˱� DataStore���������" + (this.size() - 1));
		}
	}

	public void insertRow(int row, DataObject vdo) throws AppException {
		if (null == vdo) {
			throw new AppException("DataStore ���ɴ���Ϊ null �� DataObject");
		}
		super.add(row, vdo);
//		if (row > this.size()) {
//			throw new AppException("�������λ�� �� " + row + " �����˱� DataStore����һ������" + this.size());
//		}
//		if (null == vdo) {
//			throw new AppException("DataStore ���ɴ���Ϊ null �� DataObject");
//		}
//		if (row == this.size()) {
//			this.add(vdo);
//			return;
//		}
//
//		this.addRow();
//		for (int i = this.size() - 1; i > row; i++) {
//			this.set(i, this.get(i - 1));
//		}
//		this.set(row, vdo);

	}

	public Object delRow(int row) throws AppException {
		this.checkRow("ɾ������", row);
		return super.remove(row);
	}

	public boolean containsItem(int row, String column) throws AppException {
		return getRow(row).containsKey(column);
	}

	/*
	 * //
	 * ������أ���ʱ���������̺߳���Դ�����˲��޸ĵ�ǰDataStore����ͦ��ģ����ֵ�ǰDataStore��������½�DataStore�Ļ���ռ����Դ���ࣨ
	 * �����̣߳��ᵼ�����л���,���Ծ���ʱ�õ�ʱ��ֱ���ñ��׵�Filter����
	 *//**
		 * == and <> a == 0 and x <> 0 and
		 * 
		 * @param fliterStr
		 * @param isModifyMine �Ƿ��޸��������ݣ�true�޸ģ�false���޸�
		 * @return
		 * @throws AppException
		 * @throws Exception
		 */
	/*
	 * public DataStore filter(String conditions, boolean isModifyMine) throws
	 * AppException { DataStore vdsReturn = new DataStore(this.isLowerKey); int[]
	 * all = this.findAll(conditions); for (int i = 0; i < all.length; i++) {
	 * vdsReturn.addRow(this.get(all[i])); } if (isModifyMine) { this.clear();
	 * this.addAll(vdsReturn); } return this; }
	 * 
	 *//**
		 * ɸѡ���޸�����
		 * 
		 * @param conditions
		 * @return
		 * @throws Exception
		 */
	/*
	 * public DataStore filterModify(String conditions) throws AppException { return
	 * this.filter(conditions, true); }
	 * 
	 *//**
		 * ɸѡ���ݣ�����ɸѡ�������ԭ���ݲ����޸�
		 * 
		 * @param conditions
		 * @return
		 * @throws AppException
		 * @throws Exception
		 */
	/*
	 * public DataStore filterKeep(String conditions) throws AppException { return
	 * this.filter(conditions, false); }
	 * 
	 *//**
		 * �������з������������������� == and <> a == 0 and x <> 0 and ���޸��������ݣ�����row��
		 * 
		 * @return
		 * @throws AppException
		 * @throws AppException
		 * @throws Exception
		 */
	/*
	 * public int[] findAll(String filterStr) throws AppException { Comparator
	 * comparator = FilterUtil.getComparator(filterStr); ArrayList<String>
	 * logicArray = comparator.getCompareConnectors(); ArrayList<CompareCell>
	 * booleanArray = comparator.getCompareCells();
	 * 
	 * if (null == logicArray) { throw new AppException(this.getClass().getName() +
	 * ":����logicArrayΪnull"); } if (null == booleanArray) { throw new
	 * AppException(this.getClass().getName() + ":����booleanArrayΪnull"); } if
	 * (logicArray.size() != booleanArray.size() - 1) { throw new
	 * AppException(this.getClass().getName() + ":������ƥ��"); }
	 * 
	 * if (this.size() <= 0) { return null; } for (int i = 0; i <
	 * booleanArray.size(); i++) { CompareCell compareCell = booleanArray.get(i);
	 * String columnName = compareCell.getCmpkey(); Object cmpLVal =
	 * this.get(0).get(columnName);
	 * 
	 * // ����Ƿ���Լ��� String valueType = TypeUtil.getValueType(cmpLVal); if
	 * (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," +
	 * TypeUtil.STRING) .contains(valueType)) { throw new AppException("��[" +
	 * columnName + "]����Ϊ[" + valueType + "]��֧�ּ���!"); }
	 * 
	 * }
	 * 
	 * ArrayList<Integer> al = new ArrayList<>(); // Date d2 = new Date(); int index
	 * = 0; int onceCount = minSerchCount;// ��С��һ������ int threadCount = 9;//
	 * �߳���,���������һ���߳� // ����С�ڵ���10W if (this.rowCount() <= minSerchCount * 10) {
	 * threadCount = this.rowCount() / minSerchCount;// �õ�0-10���� if (threadCount >
	 * 0) { onceCount = minSerchCount; } else { onceCount = this.rowCount();
	 * threadCount = 0; } } else if (this.rowCount() > minSerchCount * 10 &&
	 * this.rowCount() <= maxSerchCount * 10) {// ��������10W threadCount = 9; onceCount
	 * = this.rowCount() / 10;// ƽ�� } else { threadCount = this.rowCount() /
	 * maxSerchCount; onceCount = maxSerchCount; } fixedThreadPool =
	 * Executors.newFixedThreadPool(threadCount * 2); for (int i = 0; i <
	 * threadCount; i++) { final int indexTemp = index; final int onceCountTemp =
	 * onceCount; final int iTemp = i; fixedThreadPool.execute(() ->
	 * threadDoFilter(comparator, indexTemp, onceCountTemp + onceCountTemp *
	 * iTemp)); index = onceCount + onceCount * i; } if (index < this.rowCount()) {
	 * final int indexTemp = index; final int end = this.rowCount();
	 * fixedThreadPool.execute(() -> threadDoFilter(comparator, indexTemp, end));
	 * 
	 * } System.out.println(this.rowCount() + "���վ�����" + (threadCount + 1) +
	 * "���̣߳�ÿ���߳�" + onceCount + "�������һ��" + (this.rowCount() - index) + "��");
	 * synchronized (filterKey) { while (this.rowCount() != this.nowIndex) { try {
	 * filterKey.wait(); } catch (InterruptedException e) { e.printStackTrace(); } }
	 * } al = this.filterAl;
	 * 
	 * // ��ʼ�����в����� fixedThreadPool.shutdownNow(); fixedThreadPool = null; filterAl =
	 * new ArrayList<Integer>(); nowIndex = 0;
	 * 
	 * int[] ii = new int[al.size()]; for (int i = 0; i < al.size(); i++) { ii[i] =
	 * al.get(i); } return ii; }
	 * 
	 *//**
		 * ���ҵ�һ���������������������У�δ�ҵ�����-1 == and <> a == 0 and x <> 0 and ���޸���������
		 * 
		 * @param conditions
		 * @return
		 * @throws AppException
		 * @throws Exception
		 */
	/*
	 * public int find(String filterStr) throws AppException { Comparator comparator
	 * = FilterUtil.getComparator(filterStr); ArrayList<String> logicArray =
	 * comparator.getCompareConnectors(); ArrayList<CompareCell> booleanArray =
	 * comparator.getCompareCells();
	 * 
	 * if (null == logicArray) { throw new AppException(this.getClass().getName() +
	 * ":����logicArrayΪnull"); } if (null == booleanArray) { throw new
	 * AppException(this.getClass().getName() + ":����booleanArrayΪnull"); } if
	 * (logicArray.size() != booleanArray.size() - 1) { throw new
	 * AppException(this.getClass().getName() + ":������ƥ��"); }
	 * 
	 * if (this.size() <= 0) { return -1; } for (int i = 0; i < booleanArray.size();
	 * i++) { CompareCell compareCell = booleanArray.get(i); String columnName =
	 * compareCell.getCmpkey(); Object cmpLVal = this.get(0).get(columnName);
	 * 
	 * // ����Ƿ���Լ��� String valueType = TypeUtil.getValueType(cmpLVal); if
	 * (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," +
	 * TypeUtil.STRING) .contains(valueType)) { throw new AppException("��[" +
	 * columnName + "]����Ϊ[" + valueType + "]��֧�ּ���!"); }
	 * 
	 * } for (int i = 0; i < this.size(); i++) { if
	 * (ComparaUtil.matchWithoutCheck(this.get(i), comparator)) { return i; } }
	 * return -1; }
	 * 
	 * // ////////////////////////////////////////////// //
	 * ///////����������ɸѡ���ݵ�/////////////////////////
	 *//**
		 * ���̼߳������
		 * 
		 * @author �ų�
		 * @version 1.0 ����ʱ�� 2017-5-31
		 */
	/*
	 * class CalThread implements Runnable {
	 * 
	 * private Comparator comparator = null; private DataStore vds = null; private
	 * int start;; private int end; private ArrayList<Integer> al = new
	 * ArrayList<Integer>();
	 * 
	 * public CalThread(Comparator comparator, DataStore vds, int start, int end) {
	 * this.comparator = comparator; this.vds = vds; this.start = start; this.end =
	 * end; }
	 * 
	 * @Override public void run() { for (int i = start; i < end; i++) { try { if
	 * (vds.get(i).filterWithoutCheck(comparator)) { this.al.add(i); } } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * } setIndex(al, end - start); } }
	 * 
	 * private void threadDoFilter(Comparator comparator, int start, int end) {
	 * ArrayList<Integer> al = new ArrayList<Integer>();
	 * 
	 * for (int i = start; i < end; i++) { try { if
	 * (this.get(i).filterWithoutCheck(comparator)) { al.add(i); } } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * } setIndex(al, end - start); }
	 * 
	 *//**
		 * ����ɸѡ�õ�index
		 * 
		 * @author �ų�
		 * @date ����ʱ�� 2017-5-31
		 * @since V1.0
		 *//*
			 * private void setIndex(ArrayList<Integer> indexs, int allIndex) { synchronized
			 * (filterKey) { this.filterAl.addAll(indexs); this.nowIndex += allIndex;
			 * filterKey.notifyAll(); }
			 * 
			 * }
			 */

	/**
	 * ���������ֶ�,��С��������
	 * 
	 * @param column
	 */
	public DataStore sortASC(String colName) {
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
	public DataStore sortDESC(String colName) {
		if (rowCount() == 0) {
			return this;
		}
		Collections.sort(this, Collections.reverseOrder(new DataObjectComparator(colName)));

		return this;
	}

	/**
	 * ��ȡ���vds (��ʱ̫�ߣ������㷨��Ӧ�õ�����)
	 * 
	 * @author �ų�
	 * @throws AppException
	 * @date ����ʱ�� 2017-5-31
	 * @since V1.0
	 */
	public DataStore subDataStore(int fromIndex, int toIndex) throws AppException {
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

	/**
	 * * (��ʱ̫�ߣ������㷨��Ӧ�õ�����)
	 * 
	 */
	public DataStore clone() {
		DataStore dsTemp = new DataStore(isLowerKey);
		try {
			dsTemp.addAll(this);
		} catch (AppException e) {
			dsTemp = null;
		}

		return dsTemp;
	}

	/**
	 * * (��ʱ̫�ߣ������㷨��Ӧ�õ�����)
	 * 
	 */
	public DataStore clone(boolean isLowerKey) {
		DataStore dsTemp = new DataStore(isLowerKey);
		try {
			dsTemp.addAll(this);
		} catch (AppException e) {
			dsTemp = null;
		}

		return dsTemp;
	}

	/**
	 * ��ȡĳ�е�����
	 * 
	 * @param colName
	 * @return
	 * @throws AppException
	 */
	public String getColumnType(String colName) throws AppException {
		if (this.rowCount() <= 0) {
			throw new AppException("��DataStore����Ϊ0");
		}
		DataObject vDataObject = this.get(0);
		String colType = vDataObject.getType(colName);
		return colType;
	}

	public void setTypeList(LinkedHashMap<String, String> typeList) {
		for (int i = 0; i < this.rowCount(); i++) {
			this.get(i).setTypeList(typeList);

		}
	}

	public void setTypeList(String typeListString) throws AppException {
		for (int i = 0; i < this.rowCount(); i++) {
			this.get(i).setTypeList(typeListString);

		}
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 * @throws AppException
	 */
	public String getTypeList() throws AppException {
		if (this.rowCount() <= 0) {
			throw new AppException("��DataStore����Ϊ0");
		}
		DataObject vDataObject = this.get(0);
		return vDataObject.getTypeList();
	}

	public int limitRowCount(int rowCount) throws AppException {
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
	public int rowCount() {
		return this.size();
	}

	public String toJSON() throws AppException {
		return JSONUtil.toJson(this);
	}

	public String toXML() throws AppException {
		return XMLUtil.DataStoreToXmlString(this);
	}

	public final static DataStore parseFromJSON(String jsonStr) throws AppException {
		return JSONUtil.JsonToDataStore(jsonStr);

	}

	public final static DataStore parseFromXML(String xmlStr) throws AppException {
		return XMLUtil.XmlToDataStore(xmlStr);
	}

	public void insertRowWithDefaultColumns(int row) throws AppException {
		this.insertRow(row, getRowWithDefaultColumns());
	}

	public void addRowWithDefaultColumns() throws AppException {
		addRow(getRowWithDefaultColumns());
	}

	private DataObject getRowWithDefaultColumns() throws AppException {
		DataObject dob = new DataObject();
		// �Զ���typelist�и��ж���Ϊ��ֵ
		LinkedHashMap<String, String> all = this.get(0).getTypelistByList();
		for (String key : all.keySet()) {
			dob.put(key, null);
		}
		return dob;
	}

	public DataStore multiSort(String conditionString) throws AppException {
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

	/**
	 * ���ҵ�һ�����ʵ�
	 * 
	 * @param filterStr
	 * @return
	 * @throws AppException
	 */
	public int find(String filterStr) throws AppException {
		return Datas.find(this, filterStr);
	}

	/**
	 * ��������
	 * 
	 * @param filterStr
	 * @return
	 * @throws AppException
	 */
	public int[] findAll(String filterStr) throws AppException {
		return Datas.findAll(this, filterStr);
	}

	/**
	 * ���ˣ���ı䵱ǰ��������
	 * 
	 * @param filterStr
	 * @return
	 * @throws AppException
	 */
	public DataStore filter(String filterStr) throws AppException {
		Datas.filter(this, filterStr, true);
		return this;
	}

	@Override
	public void clear() {
		super.clear();
	}

}
