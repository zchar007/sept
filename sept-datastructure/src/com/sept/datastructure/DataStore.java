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
	protected boolean isLowerKey = false;// 默认对所有key不进行小写化操作
	// 筛选相关
	/*
	 * private Object filterKey = new Object(); private ArrayList<Integer> filterAl
	 * = new ArrayList<Integer>(); private int nowIndex = 0; private ExecutorService
	 * fixedThreadPool; private static int minSerchCount = 1000; private static int
	 * maxSerchCount = 100000;// 每个线程处理10W条数据还是比较优的，再多了就懵逼了
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
			throw new AppException("DataStore 不可存入为 null 的 DataObject");
		}
		vdo.setLowerKey(isLowerKey);
		return this.add(vdo);
	}

	public boolean addAll(DataStore vds) throws AppException {
		if (null == vds) {
			throw new AppException("DataStore 不可存入为 null 的 DataStore");
		}
//		for (DataObject pdo : vds) {
//			this.addRow(pdo);
//		}
		super.addAll(vds);
		return true;
	}

	public Object put(int row, String key, Object value) throws AppException {
		this.checkRow("存入位置", row);
		return this.getRow(row).put(key, value);
	}

	public DataObject getRow(int row) throws AppException {
		this.checkRow("获取位置", row);
		return this.get(row);
	}

	// String
	public String getString(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getString(key);
	}

	public String getString(int row, String key, String defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getString(key, defaul);
	}

	// Integer
	public int getInt(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getInt(key);
	}

	public int getInt(int row, String key, int defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getInt(key, defaul);
	}

	// Double
	public double getDouble(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDouble(key);
	}

	public double getDouble(int row, String key, double defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDouble(key, defaul);
	}

	// Float
	public float getFloat(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getFloat(key);
	}

	public float getFloat(int row, String key, float defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getFloat(key, defaul);
	}

	// Long
	public long getLong(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getLong(key);
	}

	public long getLong(int row, String key, long defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getLong(key, defaul);
	}

	// Long
	public BigDecimal getBigDecimal(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBigDecimal(key);
	}

	public BigDecimal getBigDecimal(int row, String key, BigDecimal defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBigDecimal(key, defaul);
	}

	// Blob
	public Blob getBlob(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBlob(key);
	}

	public Blob getBlob(int row, String key, Blob defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBlob(key, defaul);
	}

	public Clob getClob(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getClob(key);
	}

	public Clob getClob(int row, String key, Clob defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getClob(key, defaul);
	}

	// Date
	public Date getDate(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDate(key);
	}

	public Date getDate(int row, String key, Date defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDate(key, defaul);
	}

	// Boolean
	public boolean getBoolean(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBoolean(key);
	}

	public boolean getBoolean(int row, String key, boolean defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getBoolean(key, defaul);
	}

	// DataStore
	public DataStore getDataStore(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDataStore(key);
	}

	public DataStore getDataStore(int row, String key, DataStore defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDataStore(key, defaul);
	}

	// DataObject
	public DataObject getDataObject(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDataObject(key);
	}

	public DataObject getDataObject(int row, String key, DataObject defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getDataObject(key, defaul);
	}

	// Object
	public Object getObject(int row, String key) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getObject(key);
	}

	public Object getObject(int row, String key, Object defaul) throws AppException {
		this.checkRow("获取数据", row);
		return this.get(row).getObject(key, defaul);
	}

	private void checkRow(String message, int row) throws AppException {
		if (row >= this.size()) {
			throw new AppException(message + ":[" + row + "] 超过了本 DataStore的最大序列" + (this.size() - 1));
		}
	}

	public void insertRow(int row, DataObject vdo) throws AppException {
		if (null == vdo) {
			throw new AppException("DataStore 不可存入为 null 的 DataObject");
		}
		super.add(row, vdo);
//		if (row > this.size()) {
//			throw new AppException("所插入的位置 ： " + row + " 超过了本 DataStore的下一个序列" + this.size());
//		}
//		if (null == vdo) {
//			throw new AppException("DataStore 不可存入为 null 的 DataObject");
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
		this.checkRow("删除数据", row);
		return super.remove(row);
	}

	public boolean containsItem(int row, String column) throws AppException {
		return getRow(row).containsKey(column);
	}

	/*
	 * //
	 * 查找相关，暂时废弃，多线程耗资源，过滤并修改当前DataStore倒是挺快的，保持当前DataStore不变对于新建DataStore的话，占用资源过多（
	 * 包括线程）会导致运行缓慢,所以尽量时用的时候直接用奔雷的Filter方法
	 *//**
		 * == and <> a == 0 and x <> 0 and
		 * 
		 * @param fliterStr
		 * @param isModifyMine 是否修改自身数据，true修改，false不修改
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
		 * 筛选并修改数据
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
		 * 筛选数据，返回筛选结果，对原数据不做修改
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
		 * 查找所有符合条件的数据所在行 == and <> a == 0 and x <> 0 and 不修改自身数据，返回row串
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
	 * ":参数logicArray为null"); } if (null == booleanArray) { throw new
	 * AppException(this.getClass().getName() + ":参数booleanArray为null"); } if
	 * (logicArray.size() != booleanArray.size() - 1) { throw new
	 * AppException(this.getClass().getName() + ":参数不匹配"); }
	 * 
	 * if (this.size() <= 0) { return null; } for (int i = 0; i <
	 * booleanArray.size(); i++) { CompareCell compareCell = booleanArray.get(i);
	 * String columnName = compareCell.getCmpkey(); Object cmpLVal =
	 * this.get(0).get(columnName);
	 * 
	 * // 检查是否可以检索 String valueType = TypeUtil.getValueType(cmpLVal); if
	 * (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," +
	 * TypeUtil.STRING) .contains(valueType)) { throw new AppException("列[" +
	 * columnName + "]类型为[" + valueType + "]不支持检索!"); }
	 * 
	 * }
	 * 
	 * ArrayList<Integer> al = new ArrayList<>(); // Date d2 = new Date(); int index
	 * = 0; int onceCount = minSerchCount;// 最小的一次搜索 int threadCount = 9;//
	 * 线程数,不包括最后一个线程 // 数量小于等于10W if (this.rowCount() <= minSerchCount * 10) {
	 * threadCount = this.rowCount() / minSerchCount;// 得到0-10的数 if (threadCount >
	 * 0) { onceCount = minSerchCount; } else { onceCount = this.rowCount();
	 * threadCount = 0; } } else if (this.rowCount() > minSerchCount * 10 &&
	 * this.rowCount() <= maxSerchCount * 10) {// 数量大于10W threadCount = 9; onceCount
	 * = this.rowCount() / 10;// 平分 } else { threadCount = this.rowCount() /
	 * maxSerchCount; onceCount = maxSerchCount; } fixedThreadPool =
	 * Executors.newFixedThreadPool(threadCount * 2); for (int i = 0; i <
	 * threadCount; i++) { final int indexTemp = index; final int onceCountTemp =
	 * onceCount; final int iTemp = i; fixedThreadPool.execute(() ->
	 * threadDoFilter(comparator, indexTemp, onceCountTemp + onceCountTemp *
	 * iTemp)); index = onceCount + onceCount * i; } if (index < this.rowCount()) {
	 * final int indexTemp = index; final int end = this.rowCount();
	 * fixedThreadPool.execute(() -> threadDoFilter(comparator, indexTemp, end));
	 * 
	 * } System.out.println(this.rowCount() + "最终决定用" + (threadCount + 1) +
	 * "个线程，每个线程" + onceCount + "个，最后一个" + (this.rowCount() - index) + "个");
	 * synchronized (filterKey) { while (this.rowCount() != this.nowIndex) { try {
	 * filterKey.wait(); } catch (InterruptedException e) { e.printStackTrace(); } }
	 * } al = this.filterAl;
	 * 
	 * // 初始化所有参数， fixedThreadPool.shutdownNow(); fixedThreadPool = null; filterAl =
	 * new ArrayList<Integer>(); nowIndex = 0;
	 * 
	 * int[] ii = new int[al.size()]; for (int i = 0; i < al.size(); i++) { ii[i] =
	 * al.get(i); } return ii; }
	 * 
	 *//**
		 * 查找第一个符合条件的数据所在行，未找到返回-1 == and <> a == 0 and x <> 0 and 不修改自身数据
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
	 * ":参数logicArray为null"); } if (null == booleanArray) { throw new
	 * AppException(this.getClass().getName() + ":参数booleanArray为null"); } if
	 * (logicArray.size() != booleanArray.size() - 1) { throw new
	 * AppException(this.getClass().getName() + ":参数不匹配"); }
	 * 
	 * if (this.size() <= 0) { return -1; } for (int i = 0; i < booleanArray.size();
	 * i++) { CompareCell compareCell = booleanArray.get(i); String columnName =
	 * compareCell.getCmpkey(); Object cmpLVal = this.get(0).get(columnName);
	 * 
	 * // 检查是否可以检索 String valueType = TypeUtil.getValueType(cmpLVal); if
	 * (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," +
	 * TypeUtil.STRING) .contains(valueType)) { throw new AppException("列[" +
	 * columnName + "]类型为[" + valueType + "]不支持检索!"); }
	 * 
	 * } for (int i = 0; i < this.size(); i++) { if
	 * (ComparaUtil.matchWithoutCheck(this.get(i), comparator)) { return i; } }
	 * return -1; }
	 * 
	 * // ////////////////////////////////////////////// //
	 * ///////以下是用于筛选数据的/////////////////////////
	 *//**
		 * 多线程计算的类
		 * 
		 * @author 张超
		 * @version 1.0 创建时间 2017-5-31
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
		 * 设置筛选好的index
		 * 
		 * @author 张超
		 * @date 创建时间 2017-5-31
		 * @since V1.0
		 *//*
			 * private void setIndex(ArrayList<Integer> indexs, int allIndex) { synchronized
			 * (filterKey) { this.filterAl.addAll(indexs); this.nowIndex += allIndex;
			 * filterKey.notifyAll(); }
			 * 
			 * }
			 */

	/**
	 * 根据所给字段,从小到大排序
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
	 * 根据所给字段从大到小排序
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
	 * 获取拆分vds (耗时太高，查找算法不应该调用它)
	 * 
	 * @author 张超
	 * @throws AppException
	 * @date 创建时间 2017-5-31
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
	 * * (耗时太高，查找算法不应该调用它)
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
	 * * (耗时太高，查找算法不应该调用它)
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
	 * 获取类型
	 * 
	 * @return
	 * @throws AppException
	 */
	public String getTypeList() throws AppException {
		if (this.rowCount() <= 0) {
			throw new AppException("本DataStore行数为0");
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
	 * 获取ds大小
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

	/**
	 * 查找第一个合适的
	 * 
	 * @param filterStr
	 * @return
	 * @throws AppException
	 */
	public int find(String filterStr) throws AppException {
		return Datas.find(this, filterStr);
	}

	/**
	 * 查找所有
	 * 
	 * @param filterStr
	 * @return
	 * @throws AppException
	 */
	public int[] findAll(String filterStr) throws AppException {
		return Datas.findAll(this, filterStr);
	}

	/**
	 * 过滤，会改变当前对象数据
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
