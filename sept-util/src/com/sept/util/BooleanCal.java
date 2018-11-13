package com.sept.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * ����boolean���������� ̫��������ʹ��BooleanUtil
 * 
 * @author �ų�
 */
@Deprecated
public final class BooleanCal {
	private static ScriptEngineManager manager = new ScriptEngineManager();
	private static ScriptEngine engine = manager.getEngineByName("js");

	@SuppressWarnings("unused")
	public final static void main(String[] args) throws Exception {
		String calBooleanStr = "true or ( ( false or false ) and true or ( false and true) )";
		System.out
				.println(true || ((false || false) && true || (false && true)));
		Date d1 = new Date();
		boolean a = false,b = false;
		for (int i = 0; i < 200; i++) {
			a = calBoolStr(calBooleanStr);
		}
		System.out.println(UnitConversionUtil.formatMSToASUnit(new Date()
				.getTime() - d1.getTime()));
		 d1 = new Date();
		for (int i = 0; i < 200; i++) {
			 b = BooleanUtil.calBoolean(calBooleanStr);
		}
		System.out.println(UnitConversionUtil.formatMSToASUnit(new Date()
				.getTime() - d1.getTime()));
		System.out.println(a);
		System.out.println(b);

	}

	/**
	 * ���� a == b and c == d���ִ���boolֵ
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-27
	 * @since V1.0
	 */
	public final static boolean calBoolStr(String calBooleanStr)
			throws Exception {
		// ���ȼ��˴��������Ƿ�ƥ��
		if (!parenthesesPattern(calBooleanStr)) {
			throw new Exception("���Ų�ƥ��:" + calBooleanStr);
		}
		// ��������ʽ��Ѵ����ŵĵȵ�ȫ���������,�����ַ���
		String regx = "(?<=(\\btrue\\b|\\bfalse\\b)\\s{1,10})(((\\)\\s){0,})\\band\\b((\\s\\(){0,})|((\\)\\s){0,})\\bor\\b((\\s\\(){0,}))(?=\\s+(\\btrue\\b|\\bfalse\\b))";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(calBooleanStr);

		// һ��һ�е�aaa == ss bbb > ccc
		String[] expStrArr = calBooleanStr.split(regx);
		ArrayList<String> logicOps = new ArrayList<String>();
		while (matcher.find()) {
			// ������ and or
			logicOps.add(matcher.group());
		}
		// ȥ����������
		// ����Ƿ���������ֻ����booleanֵ��,����ȥ��������(��������)
		for (int i = 0; i < logicOps.size() - 1; i++) {
			String str1 = logicOps.get(i).trim();
			String str2 = logicOps.get(i + 1).trim();

			// ���i�ǵ�һ��(0)�ҷ���Ϊ)�������һ���ҷ���Ϊ(.,��ȥ��booleanֵ�ϵ�ǰ������( ) and)

			if (i == 0 && str1.startsWith(")")) {
				while (str1.startsWith(")")
						&& expStrArr[0].trim().startsWith("(")) {
					logicOps.set(i, str1.substring(1, str1.length()));
					// Ȼ��ȥ��boolean���е���ǰ������
					expStrArr[0] = expStrArr[0].trim().substring(1,
							expStrArr[0].trim().length());
					str1 = logicOps.get(i).trim();
					str2 = logicOps.get(i + 1).trim();
				}
			}

			if (i == logicOps.size() - 2 && str2.endsWith("(")) {
				while (str2.endsWith("(")
						&& expStrArr[expStrArr.length - 1].trim().endsWith(")")) {
					logicOps.set(logicOps.size() - 1,
							str2.substring(0, str2.length() - 1));
					// Ȼ��ȥ��boolean���е���ǰ������
					expStrArr[expStrArr.length - 1] = expStrArr[expStrArr.length - 1]
							.trim().substring(
									0,
									expStrArr[expStrArr.length - 1].trim()
											.length() - 1);
					str1 = logicOps.get(i).trim();
					str2 = logicOps.get(i + 1).trim();
				}
			}

			if (str1.endsWith("(") && str2.startsWith(")")) {
				while (str1.endsWith("(") && str2.startsWith(")")) {
					logicOps.set(i, str1.substring(0, str1.length() - 1));
					logicOps.set(i + 1, str2.substring(1, str2.length()));
					str1 = logicOps.get(i).trim();
					str2 = logicOps.get(i + 1).trim();
				}
			}

		}
		// ���ϼ����������ܻ�����boolean��������β��������,����ֻ��ɾ������,�ٴ���ϲ����ַ�����,ȱ)���ں������,ȱ(����ǰ������
		// ɾ������
		while (expStrArr[0].trim().startsWith("(")) {
			expStrArr[0] = expStrArr[0].trim().substring(1,
					expStrArr[0].trim().length());
		}
		while (expStrArr[expStrArr.length - 1].trim().endsWith(")")) {
			expStrArr[expStrArr.length - 1] = expStrArr[expStrArr.length - 1]
					.trim()
					.substring(0,
							expStrArr[expStrArr.length - 1].trim().length() - 1);
		}
		ArrayList<String> expStrArry = new ArrayList<String>();
		for (String string : expStrArr) {
			expStrArry.add(string);

		}
		return calBoolStr(logicOps, expStrArry);

	}

	/**
	 * @param logicStrArr
	 *            and or ��,����ҲΪ &&,|| �����Ǵ����ŵ�
	 * @param expStrArr
	 *            true false ��
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-27
	 * @since V1.0
	 */
	public final static boolean calBoolStr(ArrayList<String> logicStrArr,
			ArrayList<String> boolStrArr) throws Exception {
		/**
		 * String currentExp =
		 * "true and ( true and ( false or true ) and ( false ) ) "; true true
		 * false true false and (, and (, or, ) and
		 */
		if (logicStrArr.size() != boolStrArr.size() - 1) {
			throw new Exception("����������!!");
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
			}
		}
		Object result = engine.eval(calStr);
		if (!result.getClass().getName().equals("java.lang.Boolean")) {
			throw new Exception("����������booleanֵ!!");
		}
		Boolean bool = (Boolean) result;
		// System.out.println("�������:" + result.getClass().getName() + ",������:" +
		// result);
		return bool.booleanValue();
	}

	/**
	 * �����Ƿ�ƥ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-27
	 * @since V1.0
	 */
	private static boolean parenthesesPattern(String parentStr) {
		Stack<Character> stack = new Stack<Character>();
		stack.trimToSize(); // ��������Ϊ��
		// �������������������������ҵ���ƥ����������Ϊ����ȷ��
		int leftCount = getStrCount(parentStr, "(");
		int rightCount = getStrCount(parentStr, ")");
		// ��Ч����
		int mateCount = 0;
		for (int i = 0; i < parentStr.length(); i++) {
			Character value = parentStr.charAt(i);

			if (value.equals('(')) {
				stack.push(value);
			}

			if (value.equals(')')) {
				if (!stack.empty()) {
					stack.pop();
					mateCount++;
				} else if ((stack.capacity() != 0)) {
					break;
				}
			}

		}
		boolean bool = leftCount == rightCount && rightCount == mateCount;
		return bool;
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

}
