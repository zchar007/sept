package com.sept.util.bools;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.sept.util.UnitConverUtil;

/**
 * 接受boolean串并计算结果 太慢，建议使用BooleanUtil
 * 
 * @author 张超
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
		System.out.println(UnitConverUtil.msToAnySuit(new Date()
				.getTime() - d1.getTime()));
		 d1 = new Date();
		for (int i = 0; i < 200; i++) {
			 b = BooleanUtil.calBoolean(calBooleanStr);
		}
		System.out.println(UnitConverUtil.msToAnySuit(new Date()
				.getTime() - d1.getTime()));
		System.out.println(a);
		System.out.println(b);

	}

	/**
	 * 计算 a == b and c == d这种串的bool值
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	public final static boolean calBoolStr(String calBooleanStr)
			throws Exception {
		// 首先检查此串的括号是否匹配
		if (!parenthesesPattern(calBooleanStr)) {
			throw new Exception("括号不匹配:" + calBooleanStr);
		}
		// 本正则表达式会把带括号的等等全部陪陪出来,如上字符串
		String regx = "(?<=(\\btrue\\b|\\bfalse\\b)\\s{1,10})(((\\)\\s){0,})\\band\\b((\\s\\(){0,})|((\\)\\s){0,})\\bor\\b((\\s\\(){0,}))(?=\\s+(\\btrue\\b|\\bfalse\\b))";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(calBooleanStr);

		// 一行一行的aaa == ss bbb > ccc
		String[] expStrArr = calBooleanStr.split(regx);
		ArrayList<String> logicOps = new ArrayList<String>();
		while (matcher.find()) {
			// 关联词 and or
			logicOps.add(matcher.group());
		}
		// 去除无用括号
		// 检查是否有括号中只加了boolean值得,有则去除此括号(无用括号)
		for (int i = 0; i < logicOps.size() - 1; i++) {
			String str1 = logicOps.get(i).trim();
			String str2 = logicOps.get(i + 1).trim();

			// 如果i是第一个(0)且方向为)或者最后一个且方向为(.,则去除boolean值上的前后括号( ) and)

			if (i == 0 && str1.startsWith(")")) {
				while (str1.startsWith(")")
						&& expStrArr[0].trim().startsWith("(")) {
					logicOps.set(i, str1.substring(1, str1.length()));
					// 然后去除boolean数列的最前方括号
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
					// 然后去除boolean数列的最前方括号
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
		// 以上计算下来可能会余下boolean数组中首尾还有括号,所以只需删除即可,再次组合布尔字符串是,缺)则在后边填上,缺(则在前边填上
		// 删除括号
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
	 *            and or 串,可能也为 &&,|| 或者是带括号的
	 * @param expStrArr
	 *            true false 串
	 * @author 张超
	 * @date 创建时间 2017-5-27
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
			throw new Exception("布尔串错误!!");
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
			throw new Exception("计算结果不是boolean值!!");
		}
		Boolean bool = (Boolean) result;
		// System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" +
		// result);
		return bool.booleanValue();
	}

	/**
	 * 括号是否匹配
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	private static boolean parenthesesPattern(String parentStr) {
		Stack<Character> stack = new Stack<Character>();
		stack.trimToSize(); // 容量调整为零
		// 左括号数量等于右括号数量且等于匹配数量则认为是正确的
		int leftCount = getStrCount(parentStr, "(");
		int rightCount = getStrCount(parentStr, ")");
		// 有效数量
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
	 * isRepeat:是否要重复计算 例如 aaaaa中匹配aa 是如何进行匹配的问题,目前没有好的解决办法,只能先默认会重复查找
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-27
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
