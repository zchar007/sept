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

import com.sept.datastructure.comparator.ComparaUtil;
import com.sept.datastructure.comparator.DataObjectComparator;
import com.sept.datastructure.comparator.DataObjectMultiComparator;
import com.sept.datastructure.util.JSONUtil;
import com.sept.datastructure.util.TypeUtil;
import com.sept.datastructure.util.XMLUtil;
import com.sept.exception.AppException;
import com.sept.util.bools.FilterUtil;
import com.sept.util.bools.comparator.Comparator;
import com.sept.util.bools.comparator.CompareCell;

public class DataStore extends ArrayList<DataObject> implements Serializable {
	protected boolean isLowerKey = false;// 默认对所有key不进行小写化操作
	// 筛选相关
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

	public synchronized boolean addRow(DataObject vdo) throws AppException {
		if (null == vdo) {
			throw new AppException("DataStore 不可存入为 null 的 DataObject");
		}
		vdo.setLowerKey(isLowerKey);
		return this.add(vdo);
	}

	public synchronized boolean addAll(DataStore vds) throws AppException {
		if (null == vds) {
			throw new AppException("DataStore 不可存入为 null 的 DataStore");
		}
		for (int i = 0; i < vds.rowCount(); i++) {
			this.addRow(vds.get(i).clone());
		}
		return true;
	}

	public synchronized Object put(int row, String key, Object value) throws AppException {
		this.checkRow("存入位置", row);
		return this.getRow(row).put(key, value);
	}

	public synchronized DataObject getRow(int row) throws AppException {
		this.checkRow("获取位置", row);
		return this.get(row);
	}

	// String
	public synchronized String getString(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getString(key);
	}

	public synchronized String getString(int row, String key, String defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getString(key, defaul);
	}

	// Integer
	public synchronized int getInt(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getInt(key);
	}

	public synchronized int getInt(int row, String key, int defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getInt(key, defaul);
	}

	// Double
	public synchronized double getDouble(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDouble(key);
	}

	public synchronized double getDouble(int row, String key, double defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDouble(key, defaul);
	}

	// Float
	public synchronized float getFloat(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getFloat(key);
	}

	public synchronized float getFloat(int row, String key, float defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getFloat(key, defaul);
	}

	// Long
	public synchronized long getLong(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getLong(key);
	}

	public synchronized long getLong(int row, String key, long defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getLong(key, defaul);
	}

	// Long
	public synchronized BigDecimal getBigDecimal(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBigDecimal(key);
	}

	public synchronized BigDecimal getBigDecimal(int row, String key, BigDecimal defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBigDecimal(key, defaul);
	}

	// Blob
	public synchronized Blob getBlob(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBlob(key);
	}

	public synchronized Blob getBlob(int row, String key, Blob defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBlob(key, defaul);
	}

	public synchronized Clob getClob(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getClob(key);
	}

	public synchronized Clob getClob(int row, String key, Clob defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getClob(key, defaul);
	}

	// Date
	public synchronized Date getDate(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDate(key);
	}

	public synchronized Date getDate(int row, String key, Date defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDate(key, defaul);
	}

	// Boolean
	public synchronized boolean getBoolean(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBoolean(key);
	}

	public synchronized boolean getBoolean(int row, String key, boolean defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBoolean(key, defaul);
	}

	// DataStore
	public synchronized DataStore getDataStore(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDataStore(key);
	}

	public synchronized DataStore getDataStore(int row, String key, DataStore defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDataStore(key, defaul);
	}

	// DataObject
	public synchronized DataObject getDataObject(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDataObject(key);
	}

	public synchronized DataObject getDataObject(int row, String key, DataObject defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDataObject(key, defaul);
	}

	// Object
	public synchronized Object getObject(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getObject(key);
	}

	public synchronized Object getObject(int row, String key, Object defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getObject(key, defaul);
	}

	private void checkRow(String message, int row) throws AppException {
		if (row >= this.size()) {
			throw new AppException(message + ":[" + row + "] 超过了本 DataStore的最大序列" + (this.size() - 1));
		}
	}

	public synchronized void insertRow(int row, DataObject vdo) throws AppException {
		if (row > this.size()) {
			throw new AppException("所插入的位置 ： " + row + " 超过了本 DataStore的下一个序列" + this.size());
		}
		if (null == vdo) {
			throw new AppException("DataStore 不可存入为 null 的 DataObject");
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

	public synchronized Object delRow(int row) throws AppException {
		this.checkRow("删除数据", row);
		return super.remove(row);
	}

	public synchronized boolean containsItem(int row, String column) throws AppException {
		return getRow(row).containsKey(column);
	}

	// 查找相关
	/**
	 * == and <> a == 0 and x <> 0 and
	 * 
	 * @param fliterStr
	 * @param isModifyMine 是否修改自身数据，true修改，false不修改
	 * @return
	 * @throws AppException
	 * @throws Exception
	 */
	public synchronized DataStore filter(String conditions, boolean isModifyMine) throws AppException {
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
	 * 筛选并修改数据
	 * 
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public synchronized DataStore filterModify(String conditions) throws AppException {
		return this.filter(conditions, true);
	}

	/**
	 * 筛选数据，返回筛选结果，对原数据不做修改
	 * 
	 * @param conditions
	 * @return
	 * @throws AppException
	 * @throws Exception
	 */
	public synchronized DataStore filterKeep(String conditions) throws AppException {
		return this.filter(conditions, false);
	}

	/**
	 * 查找所有符合条件的数据所在行 == and <> a == 0 and x <> 0 and 不修改自身数据，返回row串
	 * 
	 * @return
	 * @throws AppException
	 * @throws AppException
	 * @throws Exception
	 */
	public synchronized int[] findAll(String filterStr) throws AppException {
		Comparator comparator = FilterUtil.getComparator(filterStr);
		ArrayList<String> logicArray = comparator.getCompareConnectors();
		ArrayList<CompareCell> booleanArray = comparator.getCompareCells();

		if (null == logicArray) {
			throw new AppException(this.getClass().getName() + ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(this.getClass().getName() + ":参数booleanArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(this.getClass().getName() + ":参数不匹配");
		}

		if (this.size() <= 0) {
			return null;
		}
		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = this.get(0).get(columnName);

			// 检查是否可以检索
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING).contains(valueType)) {
				throw new AppException("列[" + columnName + "]类型为[" + valueType + "]不支持检索!");
			}

		}

		ArrayList<Integer> al = new ArrayList<>();
		// Date d2 = new Date();
		DataStore vds = null;
		int index = 0;
		fixedThreadPool = Executors.newFixedThreadPool(1000);
		int onceCount = minSerchCount;
		// 如果数据超过3万，也就是按原定方案超过10个线程，那么
		if (this.rowCount() / minSerchCount > 10) {
			onceCount = this.rowCount() / 10;
		}
		while ((vds = this.subDataStore(index, index + onceCount)).rowCount() == onceCount) {
			CalThread ct = new CalThread(comparator, vds, index);
			fixedThreadPool.execute(ct);
			index += vds.rowCount();
		}
		if (vds.rowCount() != onceCount && vds.rowCount() != 0) {
			CalThread ct = new CalThread(comparator, vds, index);
			fixedThreadPool.execute(ct);
		}

		while (!getIsFinsh()) {
		}
		al = this.getIndex();

		// 初始化所有参数，
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
	 * 查找第一个符合条件的数据所在行，未找到返回-1 == and <> a == 0 and x <> 0 and 不修改自身数据
	 * 
	 * @param conditions
	 * @return
	 * @throws AppException
	 * @throws Exception
	 */
	public synchronized int find(String filterStr) throws AppException {
		Comparator comparator = FilterUtil.getComparator(filterStr);
		ArrayList<String> logicArray = comparator.getCompareConnectors();
		ArrayList<CompareCell> booleanArray = comparator.getCompareCells();

		if (null == logicArray) {
			throw new AppException(this.getClass().getName() + ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(this.getClass().getName() + ":参数booleanArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(this.getClass().getName() + ":参数不匹配");
		}

		if (this.size() <= 0) {
			return -1;
		}
		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = this.get(0).get(columnName);

			// 检查是否可以检索
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING).contains(valueType)) {
				throw new AppException("列[" + columnName + "]类型为[" + valueType + "]不支持检索!");
			}

		}
		for (int i = 0; i < this.size(); i++) {
			if (ComparaUtil.matchWithoutCheck(this.get(i), comparator)) {
				return i;
			}
		}
		return -1;
	}

	// //////////////////////////////////////////////
	// ///////以下是用于筛选数据的/////////////////////////
	/**
	 * 多线程计算的类
	 * 
	 * @author 张超
	 * @version 1.0 创建时间 2017-5-31
	 */
	class CalThread implements Runnable {

		private Comparator comparator = null;
		private DataStore vds = null;
		private int start = 0;
		private ArrayList<Integer> al = new ArrayList<Integer>();

		public CalThread(Comparator comparator, DataStore vds, int start) {
			this.comparator = comparator;
			this.vds = (DataStore) vds.clone();
			this.start = start;
		}

		@Override
		public void run() {
			for (int i = 0; i < vds.rowCount(); i++) {
				try {
					if (vds.get(i).filterWithoutCheck(comparator)) {
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
	 * 设置筛选好的index
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-31
	 * @since V1.0
	 */
	private void setIndex(ArrayList<Integer> indexs, int allIndex) {
		synchronized (filterKey) {
			this.filterAl.addAll(indexs);
			this.nowIndex += allIndex;
		}

	}

	/**
	 * 获取计算好的符合条件的数据所在index
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-31
	 * @since V1.0
	 */
	private ArrayList<Integer> getIndex() {
		synchronized (filterAl) {
			return this.filterAl;
		}

	}

	/**
	 * 获取多线程计算是否完毕
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-31
	 * @since V1.0
	 */
	private boolean getIsFinsh() {
		synchronized (filterAl) {
			return this.rowCount() == this.nowIndex;
		}

	}

	/**
	 * 根据所给字段,从小到大排序
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
	 * 根据所给字段从大到小排序
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
	 * 获取拆分vds
	 * 
	 * @author 张超
	 * @throws AppException
	 * @date 创建时间 2017-5-31
	 * @since V1.0
	 */
	public synchronized DataStore subDataStore(int fromIndex, int toIndex) throws AppException {
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
		} catch (AppException e) {
			dsTemp = null;
		}

		return dsTemp;
	}

	public synchronized DataStore clone(boolean isLowerKey) {
		DataStore dsTemp = new DataStore(isLowerKey);
		try {
			dsTemp.addAll(this);
		} catch (AppException e) {
			dsTemp = null;
		}

		return dsTemp;
	}

	/**
	 * 获取某列的类型
	 * 
	 * @param colName
	 * @return
	 * @throws AppException
	 */
	public String getColumnType(String colName) throws AppException {
		if (this.rowCount() <= 0) {
			throw new AppException("本DataStore行数为0");
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

	public synchronized void setTypeList(String typeListString) throws AppException {
		for (int i = 0; i < this.rowCount(); i++) {
			this.get(i).setTypeList(typeListString);

		}
	}

	/**
	 * 获取类型
	 * 
	 * @return
	 * @throws AppException
	 */
	public synchronized String getTypeList() throws AppException {
		if (this.rowCount() <= 0) {
			throw new AppException("本DataStore行数为0");
		}
		DataObject vDataObject = this.get(0);
		return vDataObject.getTypeList();
	}

	public synchronized int setRowCount(int rowCount) throws AppException {
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
	 * 获取ds大小
	 * 
	 * @return
	 */
	public synchronized int rowCount() {
		return this.size();
	}

	public synchronized String toJSON() throws AppException {
		return JSONUtil.toJson(this);
	}

	public synchronized String toXML() throws AppException {
		return XMLUtil.DataStoreToXmlString(this);
	}

	public synchronized final static DataStore parseFromJSON(String jsonStr) throws AppException {
		return JSONUtil.JsonToDataStore(jsonStr);

	}

	public synchronized final static DataStore parseFromXML(String xmlStr) throws AppException {
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
		// 自动将typelist中各列都置为空值
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

	@Override
	public synchronized void clear() {
		super.clear();
	}

}
