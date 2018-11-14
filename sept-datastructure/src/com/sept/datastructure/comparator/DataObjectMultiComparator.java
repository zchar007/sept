package com.sept.datastructure.comparator;

import com.sept.datastructure.DataObject;
import com.sept.exception.AppException;

public class DataObjectMultiComparator extends DataObjectComparator {

	private static final String ORDER_ASC = "asc";
	private static final String ORDER_DESC = "desc";
	private String[] keys = null;
	private String[] orders = null;

	public DataObjectMultiComparator(String[] keys, String[] orders) {
		super(null);
		this.keys = new String[keys.length];
		this.orders = new String[orders.length];

		for (int i = 0; i < keys.length; i++) {
			this.keys[i] = keys[i].trim();
		}
		for (int i = 0; i < orders.length; i++) {
			this.orders[i] = orders[i].trim();
		}

	}

	public int compare(DataObject o1, DataObject o2) {
		Object o1Value, o2Value;
		int result = 0;
		for (int i = 0; i < keys.length; i++) {
			o1Value = o1.getObject(keys[i], null);
			o2Value = o2.getObject(keys[i], null);
			int tmpResult = 0;
			try {
				tmpResult = compareCell(o1Value, o2Value);
			} catch (AppException e) {
				e.printStackTrace();
			}
			if (tmpResult == 0)
				continue;
			else if (ORDER_ASC.equals(orders[i])) {
				result = tmpResult;
				return result;
			} else if (ORDER_DESC.equals(orders[i])) {
				if (tmpResult > 0) {
					result = -1;
				} else if (tmpResult < 0) {
					result = 1;
				} else {
					result = 0;
				}
				return result;
			}
		}
		return result;
	}

}
