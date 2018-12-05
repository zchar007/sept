package com.sept.util.compara;

import java.util.ArrayList;
import java.util.HashMap;

import com.sept.exception.AppException;
import com.sept.util.TypeUtil;
import com.sept.util.bools.BooleanUtil;
import com.sept.util.bools.FilterUtil;
import com.sept.util.bools.comparator.Comparator;
import com.sept.util.bools.comparator.CompareCell;

public class ComparaUtil {
	/**
	 * 过滤，会改变传入的集合
	 * 
	 * @param al
	 * @param filterStr
	 * @return
	 * @throws AppException
	 */
	public static final ArrayList<HashMap<String, Object>> filter(ArrayList<HashMap<String, Object>> al,
			String filterStr) throws AppException {
		Comparator comparator = FilterUtil.getComparator(filterStr);

		ArrayList<String> logicArray = comparator.getCompareConnectors();
		ArrayList<CompareCell> booleanArray = comparator.getCompareCells();

		if (null == logicArray) {
			throw new AppException(al.getClass().getName() + ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(al.getClass().getName() + ":参数booleanArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(al.getClass().getName() + ":参数不匹配");
		}

		if (al.size() <= 0) {
			return new ArrayList<HashMap<String, Object>>();
		}
		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = al.get(0).get(columnName);

			// 检查是否可以检索
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("列[" + columnName + "]类型为[" + valueType + "]不支持检索!");
			}

		}

		int index = al.size() - 1;
		for (int i = index; i >= 0; i--) {
			if (!ComparaUtil.matchWithoutCheck(al.get(i), comparator)) {
				al.remove(i);
			}
		}
		return al;
	}

	/**
	 * 过滤，会改变传入的集合
	 * 
	 * @param al
	 * @param filterStr
	 * @return
	 * @throws AppException
	 */
	public static final ArrayList<HashMap<String, Object>> filterKeep(ArrayList<HashMap<String, Object>> al,
			String filterStr) throws AppException {
		Comparator comparator = FilterUtil.getComparator(filterStr);
		ArrayList<String> logicArray = comparator.getCompareConnectors();
		ArrayList<CompareCell> booleanArray = comparator.getCompareCells();

		if (null == logicArray) {
			throw new AppException(al.getClass().getName() + ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(al.getClass().getName() + ":参数booleanArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(al.getClass().getName() + ":参数不匹配");
		}

		if (al.size() <= 0) {
			return new ArrayList<HashMap<String, Object>>();
		}

		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = al.get(0).get(columnName);

			// 检查是否可以检索
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("列[" + columnName + "]类型为[" + valueType + "]不支持检索!");
			}

		}
		ArrayList<HashMap<String, Object>> alReturn = new ArrayList<>();
		for (int i = 0; i < al.size(); i++) {
			if (ComparaUtil.matchWithoutCheck(al.get(i), comparator)) {
				alReturn.add(al.get(i));
			}
		}
		return alReturn;
	}

	/**
	 * 查找所有的匹配项
	 * 
	 * @param al
	 * @param filterStr
	 * @return
	 * @throws AppException
	 */
	public static final int[] findAll(ArrayList<HashMap<String, Object>> al, String filterStr) throws AppException {
		Comparator comparator = FilterUtil.getComparator(filterStr);
		ArrayList<String> logicArray = comparator.getCompareConnectors();
		ArrayList<CompareCell> booleanArray = comparator.getCompareCells();

		if (null == logicArray) {
			throw new AppException(al.getClass().getName() + ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(al.getClass().getName() + ":参数booleanArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(al.getClass().getName() + ":参数不匹配");
		}

		if (al.size() <= 0) {
			return null;
		}
		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = al.get(0).get(columnName);

			// 检查是否可以检索
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("列[" + columnName + "]类型为[" + valueType + "]不支持检索!");
			}

		}
		ArrayList<Integer> indexArray = new ArrayList<>();
		for (int i = 0; i < al.size(); i++) {
			if (ComparaUtil.matchWithoutCheck(al.get(i), comparator)) {
				indexArray.add(i);
			}
		}
		if (indexArray.size() != 0) {
			int[] index = new int[indexArray.size()];
			for (int i = 0; i < indexArray.size(); i++) {
				index[i] = indexArray.get(i);
			}
			return index;
		}
		return null;
	}

	/**
	 * 查找第一个匹配的位置
	 * 
	 * @param al
	 * @param filterStr
	 * @return
	 * @throws AppException
	 */
	public static final int find(ArrayList<HashMap<String, Object>> al, String filterStr) throws AppException {
		Comparator comparator = FilterUtil.getComparator(filterStr);
		ArrayList<String> logicArray = comparator.getCompareConnectors();
		ArrayList<CompareCell> booleanArray = comparator.getCompareCells();

		if (null == logicArray) {
			throw new AppException(al.getClass().getName() + ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(al.getClass().getName() + ":参数booleanArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(al.getClass().getName() + ":参数不匹配");
		}

		if (al.size() <= 0) {
			return -1;
		}
		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = al.get(0).get(columnName);

			// 检查是否可以检索
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("列[" + columnName + "]类型为[" + valueType + "]不支持检索!");
			}

		}
		for (int i = 0; i < al.size(); i++) {
			if (ComparaUtil.matchWithoutCheck(al.get(i), comparator)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 检索
	 * 
	 * @param hm
	 * @param comparator
	 * @return
	 * @throws AppException
	 */
	public static final boolean match(HashMap<String, Object> hm, String filterStr) throws AppException {
		Comparator comparator = FilterUtil.getComparator(filterStr);
		return match(hm, comparator);
	}

	/**
	 * 检索
	 * 
	 * @param hm
	 * @param comparator
	 * @return
	 * @throws AppException
	 */
	public static final boolean matchWithoutCheck(HashMap<String, Object> hm, String filterStr) throws AppException {
		Comparator comparator = FilterUtil.getComparator(filterStr);
		return matchWithoutCheck(hm, comparator);
	}

	/**
	 * 检索
	 * 
	 * @param hm
	 * @param comparator
	 * @return
	 * @throws AppException
	 */
	public static final boolean match(HashMap<String, Object> hm, Comparator comparator) throws AppException {
		ArrayList<String> logicArray = comparator.getCompareConnectors();
		ArrayList<CompareCell> booleanArray = comparator.getCompareCells();

		if (null == logicArray) {
			throw new AppException(hm.getClass().getName() + ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(hm.getClass().getName() + ":参数booleanArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(hm.getClass().getName() + ":参数不匹配");
		}

		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = hm.get(columnName);

			// 检查是否可以检索
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("列[" + columnName + "]类型为[" + valueType + "]不支持检索!");
			}

		}

		return matchWithoutCheck(hm, comparator);
	}

	/**
	 * 检索
	 * 
	 * @param hm
	 * @param comparator
	 * @return
	 * @throws AppException
	 */
	public static final boolean matchWithoutCheck(HashMap<String, Object> hm, Comparator comparator)
			throws AppException {
		ArrayList<String> logicArray = comparator.getCompareConnectors();
		ArrayList<CompareCell> booleanArray = comparator.getCompareCells();

		if (null == logicArray) {
			throw new AppException(hm.getClass().getName() + ":参数logicArray为null");
		}
		if (null == booleanArray) {
			throw new AppException(hm.getClass().getName() + ":参数booleanArray为null");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(hm.getClass().getName() + ":参数不匹配");
		}
		// 检查是否可以检索

		// 处理
		ArrayList<String> boolArr = new ArrayList<String>();
		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			String operator = compareCell.getCmpoperator();
			Object cmpRVal = compareCell.getCmpvalue();

			Object cmpLVal = hm.get(columnName);

			boolean nowBool = ObjectComparator.compare(cmpLVal, cmpRVal, operator);

			boolArr.add(nowBool + "");
		}
		// 计算
		boolean isTrue = BooleanUtil.calBoolean(logicArray, boolArr);

		return isTrue;
	}
}
