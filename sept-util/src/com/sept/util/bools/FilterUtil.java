package com.sept.util.bools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sept.exception.AppException;
import com.sept.util.bools.comparator.Comparator;
import com.sept.util.bools.comparator.CompareCell;

public class FilterUtil {
	/**
	 * 。。。。。） 逻辑表达式写法 1比较运算符： ==、<、>、<=、>=、!=、<>、like、isnull、isnotnull 和2小括号 (、) 注意
	 * 每个比较运算符前后必须有空格，可以有多个空格，括号前后也需要有空格
	 * 
	 * @param conditions
	 * @return
	 * @throws AppException
	 */
	public static final Comparator getComparator(String filterStr) throws AppException {
		
		//处理
		filterStr = filterStr.replaceAll("\\|\\|", "or");
		filterStr = filterStr.replaceAll("&&", "and");
		// String regx4LogicalOperator =
		// "(?<=(\\w|[\\u4e00-\\u9fa5])\\s{0,10})(\\band\\b|\\bor\\b)(?=\\s+(\\w|[\\u4e00-\\u9fa5]))";
		String regx4LogicalOperator = "(?<=(\\w|[\\u4e00-\\u9fa5])\\s{0,10})(((\\)\\s){0,})\\band\\b((\\s\\(){0,})|((\\)\\s){0,})\\bor\\b((\\s\\(){0,}))(?=\\s+(\\w|[\\u4e00-\\u9fa5]))";
		Pattern pattern4LogicalOperator = Pattern.compile(regx4LogicalOperator);
		Matcher matcher4LogicalOperator = pattern4LogicalOperator.matcher(filterStr);

		// 一行一行的aaa == ss bbb > ccc
		String[] expStrArr = filterStr.split(regx4LogicalOperator);

		// 结构为{cmpoperator=like, cmpkey=xm, cmpvalue=张三2}
		ArrayList<CompareCell> expInfoObjArr = new ArrayList<CompareCell>();

		// 结构为 logicarray=[and (, or, ) and, and],
		ArrayList<String> logicOps = new ArrayList<String>();

		while (matcher4LogicalOperator.find()) {
			// 关联词 and or
			logicOps.add(matcher4LogicalOperator.group());
		}

		// 去除无用括号
		// 检查是否有括号中只加了boolean值得,有则去除此括号(无用括号)
		for (int i = 0; i < logicOps.size() - 1; i++) {
			String str1 = logicOps.get(i).trim();
			String str2 = logicOps.get(i + 1).trim();

			// 如果i是第一个(0)且方向为)或者最后一个且方向为(.,则去除boolean值上的前后括号( ) and)

			if (i == 0 && str1.startsWith(")")) {
				while (str1.startsWith(")") && expStrArr[0].trim().startsWith("(")) {
					logicOps.set(i, str1.substring(1, str1.length()));
					// 然后去除boolean数列的最前方括号
					expStrArr[0] = expStrArr[0].trim().substring(1, expStrArr[0].trim().length());
					str1 = logicOps.get(i).trim();
					str2 = logicOps.get(i + 1).trim();
				}
			}
			if (i == logicOps.size() - 2 && str2.endsWith("(")) {
				while (str2.endsWith("(") && expStrArr[expStrArr.length - 1].trim().endsWith(")")) {
					logicOps.set(logicOps.size() - 1, str2.substring(0, str2.length() - 1));
					// 然后去除boolean数列的最前方括号
					expStrArr[expStrArr.length - 1] = expStrArr[expStrArr.length - 1].trim().substring(0,
							expStrArr[expStrArr.length - 1].trim().length() - 1);
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
			expStrArr[0] = expStrArr[0].trim().substring(1, expStrArr[0].trim().length());
		}
		while (expStrArr[expStrArr.length - 1].trim().endsWith(")")) {
			expStrArr[expStrArr.length - 1] = expStrArr[expStrArr.length - 1].trim().substring(0,
					expStrArr[expStrArr.length - 1].trim().length() - 1);
		}
		String[] logicStrArr = new String[logicOps.size()];
		logicStrArr = logicOps.toArray(logicStrArr);

		// 验证每一个
		for (int i = 0; i < expStrArr.length; i++) {
			String currentExp = expStrArr[i].trim();
			String regx4ComparisonOperator = "(?<=\\s)(==|<|>|<=|>=|!=|like|<>)(?=\\s)|(?<=\\s)(isnull|isnotnull)";
			Pattern pattern4ComparisonOperator = Pattern.compile(regx4ComparisonOperator);
			Matcher matcher4ComparisonOperator = pattern4ComparisonOperator.matcher(currentExp);
			if (!matcher4ComparisonOperator.find()) {
				throw new AppException("在表达式【" + currentExp + "】中未找到比较运算符【==、<、>、<=、>=、!=、<>、like、isnull、isnotnull】!");
			}

			String operator = matcher4ComparisonOperator.group().trim();
			String[] expArr = currentExp.split(regx4ComparisonOperator);
			String key = null;
			String value = null;
			if (expArr.length == 2) {
				key = expArr[0].trim();
				value = expArr[1].trim();
			} else if (expArr.length == 1) {
				key = expArr[0].trim();
				value = null;
			} else {
				throw new AppException("表达式【" + currentExp + "】结构错误，不符合单目、双目运算表达式结构!");
			}
			expInfoObjArr.add(new CompareCell(operator, key, value));
		}

		return new Comparator(expInfoObjArr, logicOps);
	}

	/**
	 * 将逻辑表达式拆分成连接关键词（logicarray=[and (, or, ) and,
	 * and],）+单个比较逻辑（booleanarray=[{cmpoperator=like, cmpkey=xm, cmpvalue=张三2},
	 * 。。。。。） 逻辑表达式写法 1比较运算符： ==、<、>、<=、>=、!=、<>、like、isnull、isnotnull 和2小括号 (、) 注意
	 * 每个比较运算符前后必须有空格，可以有多个空格，括号前后也需要有空格
	 * 
	 * @param conditions
	 * @return
	 * @throws AppException
	 */
	@Deprecated
	public static HashMap<String, Object> filter(String conditions) throws AppException {
		// String regx4LogicalOperator =
		// "(?<=(\\w|[\\u4e00-\\u9fa5])\\s{0,10})(\\band\\b|\\bor\\b)(?=\\s+(\\w|[\\u4e00-\\u9fa5]))";
		String regx4LogicalOperator = "(?<=(\\w|[\\u4e00-\\u9fa5])\\s{0,10})(((\\)\\s){0,})\\band\\b((\\s\\(){0,})|((\\)\\s){0,})\\bor\\b((\\s\\(){0,}))(?=\\s+(\\w|[\\u4e00-\\u9fa5]))";
		Pattern pattern4LogicalOperator = Pattern.compile(regx4LogicalOperator);
		Matcher matcher4LogicalOperator = pattern4LogicalOperator.matcher(conditions);
		// 一行一行的aaa == ss bbb > ccc
		String[] expStrArr = conditions.split(regx4LogicalOperator);
		// 结构为{cmpoperator=like, cmpkey=xm, cmpvalue=张三2}
		ArrayList<HashMap<String, Object>> expInfoObjArr = new ArrayList<HashMap<String, Object>>();
		// 结构为 logicarray=[and (, or, ) and, and],
		ArrayList<String> logicOps = new ArrayList<String>();
		while (matcher4LogicalOperator.find()) {
			// 关联词 and or
			logicOps.add(matcher4LogicalOperator.group());
		}
		// 去除无用括号
		// 检查是否有括号中只加了boolean值得,有则去除此括号(无用括号)
		for (int i = 0; i < logicOps.size() - 1; i++) {
			String str1 = logicOps.get(i).trim();
			String str2 = logicOps.get(i + 1).trim();

			// 如果i是第一个(0)且方向为)或者最后一个且方向为(.,则去除boolean值上的前后括号( ) and)

			if (i == 0 && str1.startsWith(")")) {
				while (str1.startsWith(")") && expStrArr[0].trim().startsWith("(")) {
					logicOps.set(i, str1.substring(1, str1.length()));
					// 然后去除boolean数列的最前方括号
					expStrArr[0] = expStrArr[0].trim().substring(1, expStrArr[0].trim().length());
					str1 = logicOps.get(i).trim();
					str2 = logicOps.get(i + 1).trim();
				}
			}
			if (i == logicOps.size() - 2 && str2.endsWith("(")) {
				while (str2.endsWith("(") && expStrArr[expStrArr.length - 1].trim().endsWith(")")) {
					logicOps.set(logicOps.size() - 1, str2.substring(0, str2.length() - 1));
					// 然后去除boolean数列的最前方括号
					expStrArr[expStrArr.length - 1] = expStrArr[expStrArr.length - 1].trim().substring(0,
							expStrArr[expStrArr.length - 1].trim().length() - 1);
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
			expStrArr[0] = expStrArr[0].trim().substring(1, expStrArr[0].trim().length());
		}
		while (expStrArr[expStrArr.length - 1].trim().endsWith(")")) {
			expStrArr[expStrArr.length - 1] = expStrArr[expStrArr.length - 1].trim().substring(0,
					expStrArr[expStrArr.length - 1].trim().length() - 1);
		}
//		String[] logicStrArr = new String[logicOps.size()];
//		logicStrArr = logicOps.toArray(logicStrArr);

		// 验证每一个
		for (int i = 0; i < expStrArr.length; i++) {
			String currentExp = expStrArr[i].trim();
			String regx4ComparisonOperator = "(?<=\\s)(==|<|>|<=|>=|!=|like|<>)(?=\\s)|(?<=\\s)(isnull|isnotnull)";
			Pattern pattern4ComparisonOperator = Pattern.compile(regx4ComparisonOperator);
			Matcher matcher4ComparisonOperator = pattern4ComparisonOperator.matcher(currentExp);
			if (!matcher4ComparisonOperator.find()) {
				throw new AppException("在表达式【" + currentExp + "】中未找到比较运算符【==、<、>、<=、>=、!=、<>、like、isnull、isnotnull】!");
			}

			String operator = matcher4ComparisonOperator.group().trim();
			String[] expArr = currentExp.split(regx4ComparisonOperator);
			String key = null;
			String value = null;
			if (expArr.length == 2) {
				key = expArr[0].trim();
				value = expArr[1].trim();
			} else if (expArr.length == 1) {
				key = expArr[0].trim();
				value = null;
			} else {
				throw new AppException("表达式【" + currentExp + "】结构错误，不符合单目、双目运算表达式结构!");
			}
			HashMap<String, Object> o = new HashMap<String, Object>();
			o.put("cmpKey", key);
			o.put("cmpOperator", operator);
			o.put("cmpValue", value);
			expInfoObjArr.add(o);
		}

		HashMap<String, Object> pdo = new HashMap<String, Object>();
		pdo.put("logicArray", logicOps);
		pdo.put("booleanArray", expInfoObjArr);
		return pdo;
	}

}
