package com.sept.datastructure.comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.util.TypeUtil;
import com.sept.util.bools.BooleanUtil;
import com.sept.util.bools.FilterUtil;
import com.sept.util.bools.comparator.Comparator;
import com.sept.util.bools.comparator.CompareCell;
import com.sept.util.compara.ObjectComparator;

public class Datas {

	/**
	 * 单线程计算 modify = false慎用
	 * 
	 * @param pds
	 * @param filterStr
	 * @param modify
	 * @return
	 * @throws AppException
	 */
	public static final DataStore filter(DataStore pds, String filterStr, boolean modify) throws AppException {
		/**
		 * 入参验证
		 */
		if (null == pds || pds.size() <= 0) {
			return pds;
		}
		if (null == filterStr || filterStr.trim().isEmpty()) {
			throw new AppException(pds.getClass().getName() + ":入参filterStr为null");
		}
		/**
		 * 获取比较器
		 */
		Comparator comparator = FilterUtil.getComparator(filterStr);
		/**
		 * 获取参数
		 */
		ArrayList<String> logicArray = comparator.getCompareConnectors();
		ArrayList<CompareCell> booleanArray = comparator.getCompareCells();

		/**
		 * 参数验证
		 */
		if (null == logicArray) {
			throw new AppException(pds.getClass().getName() + ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(pds.getClass().getName() + ":参数booleanArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(pds.getClass().getName() + ":参数不匹配");
		}

		/**
		 * 比较项验证
		 */
		DataObject pdo = pds.get(0);
		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = pdo.get(columnName);

			/**
			 * 检查是否可以检索
			 */
			String valueType = TypeUtil.getValueType(cmpLVal);

			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("列[" + columnName + "]类型为[" + valueType + "]不支持检索!");
			}
		}
		/**
		 * 循环比较，保存返回的bool式
		 */
		HashMap<String, Boolean> hmBools = new HashMap<>();

		DataStore dsNew = new DataStore();
		for (int i = pds.rowCount() - 1; i >= 0; i--) {
			String boolStr = "";
			for (int j = 0; j < booleanArray.size(); j++) {
				CompareCell compareCell = booleanArray.get(j);
				String columnName = compareCell.getCmpkey();
				String operator = compareCell.getCmpoperator();
				Object cmpRVal = compareCell.getCmpvalue();

				Object cmpLVal = pds.getObject(i, columnName);

				boolean nowBool = ObjectComparator.compare(cmpLVal, cmpRVal, operator);
				boolStr += nowBool + ",";
			}
			boolStr.substring(0, boolStr.length() - 1);
			boolean isTrue = false;
			if (hmBools.containsKey(boolStr)) {
				isTrue = hmBools.get(boolStr);
			} else {
				// 计算
				isTrue = BooleanUtil.calBoolean(logicArray, boolStr);
				hmBools.put(boolStr, isTrue);
			}
			if (isTrue) {
				if (!modify) {
					dsNew.addRow(pds.get(i));
				}
			} else {
				if (modify) {
					pds.remove(i);
				}
			}
		}

		return modify ? pds : dsNew;
	}

	public static final int find(DataStore pds, String filterStr) throws AppException {
		/**
		 * 入参验证
		 */
		if (null == pds || pds.size() <= 0) {
			return -1;
		}
		if (null == filterStr || filterStr.trim().isEmpty()) {
			throw new AppException(pds.getClass().getName() + ":入参filterStr为null");
		}
		/**
		 * 获取比较器
		 */
		Comparator comparator = FilterUtil.getComparator(filterStr);
		/**
		 * 获取参数
		 */
		ArrayList<String> logicArray = comparator.getCompareConnectors();
		ArrayList<CompareCell> booleanArray = comparator.getCompareCells();

		/**
		 * 参数验证
		 */
		if (null == logicArray) {
			throw new AppException(pds.getClass().getName() + ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(pds.getClass().getName() + ":参数booleanArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(pds.getClass().getName() + ":参数不匹配");
		}

		/**
		 * 比较项验证
		 */
		DataObject pdo = pds.get(0);
		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = pdo.get(columnName);

			/**
			 * 检查是否可以检索
			 */
			String valueType = TypeUtil.getValueType(cmpLVal);

			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("列[" + columnName + "]类型为[" + valueType + "]不支持检索!");
			}
		}
		/**
		 * 循环比较，保存返回的bool式
		 */
		HashMap<String, Boolean> hmBools = new HashMap<>();
		for (int i = 0; i < pds.rowCount(); i++) {
			String boolStr = "";
			for (int j = 0; j < booleanArray.size(); j++) {
				CompareCell compareCell = booleanArray.get(j);
				String columnName = compareCell.getCmpkey();
				String operator = compareCell.getCmpoperator();
				Object cmpRVal = compareCell.getCmpvalue();

				Object cmpLVal = pds.getObject(i, columnName);

				boolean nowBool = ObjectComparator.compare(cmpLVal, cmpRVal, operator);
				boolStr += nowBool + ",";
			}
			boolStr.substring(0, boolStr.length() - 1);
			boolean isTrue = false;
			if (hmBools.containsKey(boolStr)) {
				isTrue = hmBools.get(boolStr);
			} else {
				// 计算
				isTrue = BooleanUtil.calBoolean(logicArray, boolStr);
				if (isTrue) {
					return i;
				}
				hmBools.put(boolStr, isTrue);
			}
		}

		return -1;
	}

	public static final int[] findAll(DataStore pds, String filterStr) throws AppException {
		/**
		 * 入参验证
		 */
		if (null == pds || pds.size() <= 0) {
			return null;
		}
		if (null == filterStr || filterStr.trim().isEmpty()) {
			throw new AppException(pds.getClass().getName() + ":入参filterStr为null");
		}
		/**
		 * 获取比较器
		 */
		Comparator comparator = FilterUtil.getComparator(filterStr);
		/**
		 * 获取参数
		 */
		ArrayList<String> logicArray = comparator.getCompareConnectors();
		ArrayList<CompareCell> booleanArray = comparator.getCompareCells();

		/**
		 * 参数验证
		 */
		if (null == logicArray) {
			throw new AppException(pds.getClass().getName() + ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(pds.getClass().getName() + ":参数booleanArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(pds.getClass().getName() + ":参数不匹配");
		}

		/**
		 * 比较项验证
		 */
		DataObject pdo = pds.get(0);
		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = pdo.get(columnName);

			/**
			 * 检查是否可以检索
			 */
			String valueType = TypeUtil.getValueType(cmpLVal);

			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("列[" + columnName + "]类型为[" + valueType + "]不支持检索!");
			}
		}
		/**
		 * 循环比较，保存返回的bool式
		 */
		HashMap<String, Boolean> hmBools = new HashMap<>();

		int[] indexs = new int[0];
		for (int i = 0; i < pds.rowCount(); i++) {
			String boolStr = "";
			for (int j = 0; j < booleanArray.size(); j++) {
				CompareCell compareCell = booleanArray.get(j);
				String columnName = compareCell.getCmpkey();
				String operator = compareCell.getCmpoperator();
				Object cmpRVal = compareCell.getCmpvalue();

				Object cmpLVal = pds.getObject(i, columnName);

				boolean nowBool = ObjectComparator.compare(cmpLVal, cmpRVal, operator);
				boolStr += nowBool + ",";
			}
			boolStr.substring(0, boolStr.length() - 1);
			boolean isTrue = false;
			if (hmBools.containsKey(boolStr)) {
				isTrue = hmBools.get(boolStr);
			} else {
				// 计算
				isTrue = BooleanUtil.calBoolean(logicArray, boolStr);
				hmBools.put(boolStr, isTrue);
			}
			if (isTrue) {
				indexs = addToArray(indexs, i);
			}
		}

		return indexs;
	}

	private static int[] addToArray(int[] indexs, int number) {
		indexs = Arrays.copyOf(indexs, indexs.length + 1);
		indexs[indexs.length - 1] = number;
		return indexs;
	}
}
