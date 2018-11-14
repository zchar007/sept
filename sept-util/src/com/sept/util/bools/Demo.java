package com.sept.util.bools;

import java.util.ArrayList;
import java.util.HashMap;

import com.sept.exception.AppException;
import com.sept.util.bools.comparator.Comparator;

class Demo {

	public static void main(String[] args) throws AppException {
		String filterStr = " xm == ÕÅÈý  && id >= 10 && ( nl > 30 || rp > 100 ) ";
	
		System.out.println(filterStr);
		Comparator c = FilterUtil.getComparator(filterStr);
		System.out.println(c.toString());
		System.out.println(parse(FilterUtil.filter(filterStr)));
		
	}

	private static String parse(HashMap<String, Object> para) {
		@SuppressWarnings("unchecked")
		ArrayList<String> logicArray = (ArrayList<String>) para.get("logicArray");
		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, Object>> booleanArray = (ArrayList<HashMap<String, Object>>) para.get("booleanArray");

		String str = "Comparator [compareCells=";
		for (HashMap<String, Object> compareCell : booleanArray) {

			str += "{" + "CompareCell [cmpoperator=" + compareCell.get("cmpOperator") + ", cmpkey="
					+ compareCell.get("cmpKey") + ", cmpvalue=" + compareCell.get("cmpValue") + "]" + "},";
		}

		str += ", compareConnectors=" + logicArray + "]";
		return str;

	}
}
