package com.sept.util.bools;

import java.util.ArrayList;
import java.util.Date;

import com.sept.exception.AppException;

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
			throws AppException {
		/**
		 * String currentExp = "true and ( true and ( false or true ) and ( false ) ) ";
		 * true true false true false and (, and (, or, ) and
		 */
		if (logicStrArr.size() != boolStrArr.size() - 1) {
			throw new AppException("����������!!");
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
		return calBoolean(calStr);
	}

	/**
	 * ����boolean�ַ���(����)
	 * 
	 * @author �ų�
	 * @throws AppException 
	 * @date ����ʱ�� 2017-5-31
	 * @since V1.0
	 */
	public final static boolean calBoolean(String booleanStr) throws AppException {
		int[] index = null;

		String start = null, nowStr = null, end = null;
		while ((index = getInStr(booleanStr)) != null) {
			start = booleanStr.substring(0, index[0]);
			nowStr = booleanStr.substring(index[0] + 1, index[1]);
			end = booleanStr.substring(index[1] + 1, booleanStr.length());

			// �������������е�ֵ
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
	 * @throws AppException 
	 * @date ����ʱ�� 2017-5-31
	 * @since V1.0
	 */
	private static boolean calNoParenthesesBoolean(String booleanStr) throws AppException {
		String temp = booleanStr;
		temp = temp.replaceAll("\\|\\|", "");
		temp = temp.replaceAll("&&", "");
		temp = temp.replaceAll("and", "");
		temp = temp.replaceAll("or", "");
		temp = temp.replaceAll("true", "");
		temp = temp.replaceAll("false", "");
		temp = temp.replaceAll(" ", "");
		if(!temp.isEmpty()) {
			throw new AppException(BooleanUtil.class.getName()+".calNoParenthesesBoolean:�����߼�����");
		}

		String[] bools = booleanStr.split(" ");
		boolean returnBoolean = false;
		String calLink = "or";
		for (int i = 0; i < bools.length; i++) {
			if (null == bools[i] || bools[i].trim().isEmpty()) {
				continue;
			}
			if (bools[i].equals("true") || bools[i].equals("false")) {
				if ("or".equals(calLink) || "||".equals(calLink)) {
					returnBoolean = (bools[i].equals("true") ? true : false) || returnBoolean;
				} else if ("and".equals(calLink) || "&&".equals(calLink)) {
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

	public final static void main(String[] args) throws AppException {
//		String calBooleanStr = "true or ( ( false or false ) and true or ( false and true) )";
//		// System.out.println(true || ((false || false) && true || (false && true)));
//		Date d = new Date();
//		for (int i = 0; i < 2000000; i++) {
//			calBoolean(calBooleanStr);
//		}
//		System.out.println(new Date().getTime() - d.getTime());
//		System.out.println(calBoolean(calBooleanStr));

		System.out.println(calBoolean("true and true and (( true and true))"));
	}
}
