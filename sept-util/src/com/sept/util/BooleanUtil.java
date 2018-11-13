package com.sept.util;

import java.util.ArrayList;
import java.util.Date;

import com.sept.exception.ApplicationException;

public final class BooleanUtil {
	/**
	 * ����boolean�ַ���(����)
	 * 
	 * @author �ų�
	 * @throws Exception
	 * @date ����ʱ�� 2017-5-31
	 * @since V1.0
	 */
	public final static boolean calBoolean(ArrayList<String> logicStrArr, ArrayList<String> boolStrArr)
			throws ApplicationException {
		/**
		 * String currentExp = "true and ( true and ( false or true ) and ( false ) ) ";
		 * true true false true false and (, and (, or, ) and
		 */
		if (logicStrArr.size() != boolStrArr.size() - 1) {
			throw new ApplicationException("����������!!");
		}
		String calStr = "";
		for (int i = 0; i < boolStrArr.size(); i++) {

			calStr += boolStrArr.get(i) + " ";
			if (i < logicStrArr.size()) {
				String string = logicStrArr.get(i).replace("and", "&&");
				string = string.replace("or", "||");

				calStr += string + " ";
			}
		}
		int leftCount = getStrCount(calStr, "(");
		int rightCount = getStrCount(calStr, ")");
		int index = 0;
		if (leftCount > rightCount) {
			index = leftCount - rightCount;
			while (index != 0) {
				calStr = calStr + " ) ";
				index--;
			}

		}
		if (leftCount < rightCount) {
			index = rightCount - leftCount;
			while (index != 0) {
				calStr = " ( " + calStr;
				index--;
				;
			}
		}
		// System.out.println(calStr);
		return calBoolean(calStr);
//		Object result = engine.eval(calStr);
//		if (!result.getClass().getName().equals("java.lang.Boolean")) {
//			throw new Exception("����������booleanֵ!!");
//		}
//		Boolean bool = (Boolean) result;
//		// System.out.println("�������:" + result.getClass().getName() + ",������:" +
		// result);
		// return bool.booleanValue();
	}

	/**
	 * ����boolean�ַ���(����)
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-31
	 * @since V1.0
	 */
	public final static boolean calBoolean(String booleanStr) {
		int[] index = null;

		String start = null, nowStr = null, end = null;
		while ((index = getInStr(booleanStr)) != null) {
			start = booleanStr.substring(0, index[0]);
			nowStr = booleanStr.substring(index[0], index[1] + 1);
			end = booleanStr.substring(index[1] + 1, booleanStr.length());

			booleanStr = start + calNoParenthesesBoolean(nowStr) + end;
		}
		return calNoParenthesesBoolean(booleanStr);

	}

	/**
	 * ��ȡ���ڲ���ĳ�������ڵ�����
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-31
	 * @since V1.0
	 */
	private static int[] getInStr(String booleanStr) {
		int[] index = new int[2];
		int start = 0;
		for (int i = 0; i < booleanStr.length(); i++) {
			if (booleanStr.charAt(i) == '(') {
				start = i;
			}
			if (booleanStr.charAt(i) == ')') {
				index[0] = start;
				index[1] = i;
				return index;
			}
		}
		return null;
	}

	/**
	 * ֱ�Ӽ��������ţ�����and(&&) or(||) true false��ɵ�
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-31
	 * @since V1.0
	 */
	private static boolean calNoParenthesesBoolean(String booleanStr) {
		String[] bools = booleanStr.split(" ");
		boolean returnBoolean = false;
		String calLink = "or";
		for (int i = 0; i < bools.length; i++) {
			if (bools[i].equals("true") || bools[i].equals("false")) {
				if ("or".equals(calLink) || "||".equals(calLink)) {
					returnBoolean = (bools[i].equals("true") ? true : false) || returnBoolean;
				} else {
					returnBoolean = (bools[i].equals("true") ? true : false) && returnBoolean;
				}
			} else {
				calLink = bools[i];
			}
		}
		return returnBoolean;
	}

	/**
	 * isRepeat:�Ƿ�Ҫ�ظ����� ���� aaaaa��ƥ��aa ����ν���ƥ�������,Ŀǰû�кõĽ���취,ֻ����Ĭ�ϻ��ظ�����
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-27
	 * @since V1.0
	 */
	private static int getStrCount(String str, String strmate) {
		int lengthStr = str.length();
		int lengthMate = strmate.length();
		int count = 0;
		if (lengthMate > lengthStr) {
			return -1;
		}
		String strTemp = "";
		for (int i = 0; i <= lengthStr - lengthMate; i++) {
			strTemp = str.substring(i, i + lengthMate);
			if (strTemp.equals(strmate)) {
				count++;
			}

		}
		return count;

	}

	public final static void main(String[] args) {
		String calBooleanStr = "true or ( ( false or false ) and true or ( false and true) )";
		// System.out.println(true || ((false || false) && true || (false && true)));
		Date d = new Date();
		for (int i = 0; i < 2000000; i++) {
			calBoolean(calBooleanStr);
		}
		System.out.println(new Date().getTime() - d.getTime());
		System.out.println(calBoolean(calBooleanStr));
	}
}
