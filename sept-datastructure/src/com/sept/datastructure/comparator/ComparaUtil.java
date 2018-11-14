package com.sept.datastructure.comparator;

import java.util.ArrayList;
import java.util.HashMap;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.comparator.ObjectComparator;
import com.sept.datastructure.util.TypeUtil;
import com.sept.exception.AppException;
import com.sept.util.bools.BooleanUtil;
import com.sept.util.bools.FilterUtil;
import com.sept.util.bools.comparator.Comparator;
import com.sept.util.bools.comparator.CompareCell;

public class ComparaUtil {
	/**
	 * ���ˣ���ı䴫��ļ���
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
			throw new AppException(al.getClass().getName() + ":����logicArrayΪnull");
		}
		if (null == booleanArray) {
			throw new AppException(al.getClass().getName() + ":����booleanArrayΪnull");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(al.getClass().getName() + ":������ƥ��");
		}

		if (al.size() <= 0) {
			return new ArrayList<HashMap<String, Object>>();
		}

		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = al.get(0).get(columnName);

			// ����Ƿ���Լ���
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("��[" + columnName + "]����Ϊ[" + valueType + "]��֧�ּ���!");
			}

		}
		int index = al.size() - 1;
		for (int i = index; i >= 0; i--) {
			if (!ComparaUtil.matchWithoutCheck(al.get(i), filterStr)) {
				al.remove(i);
			}
		}
		return al;
	}

	/**
	 * ���ˣ���ı䴫��ļ���
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
			throw new AppException(al.getClass().getName() + ":����logicArrayΪnull");
		}
		if (null == booleanArray) {
			throw new AppException(al.getClass().getName() + ":����booleanArrayΪnull");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(al.getClass().getName() + ":������ƥ��");
		}

		if (al.size() <= 0) {
			return new ArrayList<HashMap<String, Object>>();
		}

		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = al.get(0).get(columnName);

			// ����Ƿ���Լ���
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("��[" + columnName + "]����Ϊ[" + valueType + "]��֧�ּ���!");
			}

		}
		ArrayList<HashMap<String, Object>> alReturn = new ArrayList<>();
		for (int i = 0; i < al.size(); i++) {
			if (ComparaUtil.matchWithoutCheck(al.get(i), filterStr)) {
				alReturn.add(al.get(i));
			}
		}
		return alReturn;
	}

	/**
	 * �������е�ƥ����
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
			throw new AppException(al.getClass().getName() + ":����logicArrayΪnull");
		}
		if (null == booleanArray) {
			throw new AppException(al.getClass().getName() + ":����booleanArrayΪnull");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(al.getClass().getName() + ":������ƥ��");
		}

		if (al.size() <= 0) {
			return null;
		}
		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = al.get(0).get(columnName);

			// ����Ƿ���Լ���
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("��[" + columnName + "]����Ϊ[" + valueType + "]��֧�ּ���!");
			}

		}
		ArrayList<Integer> indexArray = new ArrayList<>();
		for (int i = 0; i < al.size(); i++) {
			if (ComparaUtil.matchWithoutCheck(al.get(i), filterStr)) {
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
	 * ���ҵ�һ��ƥ���λ��
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
			throw new AppException(al.getClass().getName() + ":����logicArrayΪnull");
		}
		if (null == booleanArray) {
			throw new AppException(al.getClass().getName() + ":����booleanArrayΪnull");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(al.getClass().getName() + ":������ƥ��");
		}

		if (al.size() <= 0) {
			return -1;
		}
		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = al.get(0).get(columnName);

			// ����Ƿ���Լ���
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("��[" + columnName + "]����Ϊ[" + valueType + "]��֧�ּ���!");
			}

		}
		for (int i = 0; i < al.size(); i++) {
			if (ComparaUtil.matchWithoutCheck(al.get(i), filterStr)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * ����
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
	 * ����
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
	 * ����
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
			throw new AppException(hm.getClass().getName() + ":����logicArrayΪnull");
		}
		if (null == booleanArray) {
			throw new AppException(hm.getClass().getName() + ":����booleanArrayΪnull");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(hm.getClass().getName() + ":������ƥ��");
		}

		for (int i = 0; i < booleanArray.size(); i++) {
			CompareCell compareCell = booleanArray.get(i);
			String columnName = compareCell.getCmpkey();
			Object cmpLVal = hm.get(columnName);

			// ����Ƿ���Լ���
			String valueType = TypeUtil.getValueType(cmpLVal);
			if (!(TypeUtil.DATE + "," + TypeUtil.NUMBER + "," + TypeUtil.BOOLEAN + "," + TypeUtil.STRING)
					.contains(valueType)) {
				throw new AppException("��[" + columnName + "]����Ϊ[" + valueType + "]��֧�ּ���!");
			}

		}

		return matchWithoutCheck(hm, comparator);
	}

	/**
	 * ����
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
			throw new AppException(hm.getClass().getName() + ":����logicArrayΪnull");
		}
		if (null == booleanArray) {
			throw new AppException(hm.getClass().getName() + ":����booleanArrayΪnull");
		}
		if (logicArray.size() != booleanArray.size() - 1) {
			throw new AppException(hm.getClass().getName() + ":������ƥ��");
		}
		// ����Ƿ���Լ���

		// ����
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
		// ����
		boolean isTrue = BooleanUtil.calBoolean(logicArray, boolArr);

		return isTrue;
	}

	public static void main(String[] args) throws AppException {
		// HashMap<String, Object> hm = new HashMap<>();
		DataObject hm = new DataObject();
		// for (int i = 0; i < 10; i++) {
		hm.put("xm", "����");
		hm.put("nl", 20);
		hm.put("size", 110.5);
		// }

		String filterStr = "nl >= 5 and size > 102.25 ";
		System.out.println(ComparaUtil.match(hm, filterStr));

	}
}
