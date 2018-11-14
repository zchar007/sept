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
	 * ������������ �߼����ʽд�� 1�Ƚ�������� ==��<��>��<=��>=��!=��<>��like��isnull��isnotnull ��2С���� (��) ע��
	 * ÿ���Ƚ������ǰ������пո񣬿����ж���ո�����ǰ��Ҳ��Ҫ�пո�
	 * 
	 * @param conditions
	 * @return
	 * @throws AppException
	 */
	public static final Comparator getComparator(String filterStr) throws AppException {
		
		//����
		filterStr = filterStr.replaceAll("\\|\\|", "or");
		filterStr = filterStr.replaceAll("&&", "and");
		// String regx4LogicalOperator =
		// "(?<=(\\w|[\\u4e00-\\u9fa5])\\s{0,10})(\\band\\b|\\bor\\b)(?=\\s+(\\w|[\\u4e00-\\u9fa5]))";
		String regx4LogicalOperator = "(?<=(\\w|[\\u4e00-\\u9fa5])\\s{0,10})(((\\)\\s){0,})\\band\\b((\\s\\(){0,})|((\\)\\s){0,})\\bor\\b((\\s\\(){0,}))(?=\\s+(\\w|[\\u4e00-\\u9fa5]))";
		Pattern pattern4LogicalOperator = Pattern.compile(regx4LogicalOperator);
		Matcher matcher4LogicalOperator = pattern4LogicalOperator.matcher(filterStr);

		// һ��һ�е�aaa == ss bbb > ccc
		String[] expStrArr = filterStr.split(regx4LogicalOperator);

		// �ṹΪ{cmpoperator=like, cmpkey=xm, cmpvalue=����2}
		ArrayList<CompareCell> expInfoObjArr = new ArrayList<CompareCell>();

		// �ṹΪ logicarray=[and (, or, ) and, and],
		ArrayList<String> logicOps = new ArrayList<String>();

		while (matcher4LogicalOperator.find()) {
			// ������ and or
			logicOps.add(matcher4LogicalOperator.group());
		}

		// ȥ����������
		// ����Ƿ���������ֻ����booleanֵ��,����ȥ��������(��������)
		for (int i = 0; i < logicOps.size() - 1; i++) {
			String str1 = logicOps.get(i).trim();
			String str2 = logicOps.get(i + 1).trim();

			// ���i�ǵ�һ��(0)�ҷ���Ϊ)�������һ���ҷ���Ϊ(.,��ȥ��booleanֵ�ϵ�ǰ������( ) and)

			if (i == 0 && str1.startsWith(")")) {
				while (str1.startsWith(")") && expStrArr[0].trim().startsWith("(")) {
					logicOps.set(i, str1.substring(1, str1.length()));
					// Ȼ��ȥ��boolean���е���ǰ������
					expStrArr[0] = expStrArr[0].trim().substring(1, expStrArr[0].trim().length());
					str1 = logicOps.get(i).trim();
					str2 = logicOps.get(i + 1).trim();
				}
			}
			if (i == logicOps.size() - 2 && str2.endsWith("(")) {
				while (str2.endsWith("(") && expStrArr[expStrArr.length - 1].trim().endsWith(")")) {
					logicOps.set(logicOps.size() - 1, str2.substring(0, str2.length() - 1));
					// Ȼ��ȥ��boolean���е���ǰ������
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
		// ���ϼ����������ܻ�����boolean��������β��������,����ֻ��ɾ������,�ٴ���ϲ����ַ�����,ȱ)���ں������,ȱ(����ǰ������
		// ɾ������
		while (expStrArr[0].trim().startsWith("(")) {
			expStrArr[0] = expStrArr[0].trim().substring(1, expStrArr[0].trim().length());
		}
		while (expStrArr[expStrArr.length - 1].trim().endsWith(")")) {
			expStrArr[expStrArr.length - 1] = expStrArr[expStrArr.length - 1].trim().substring(0,
					expStrArr[expStrArr.length - 1].trim().length() - 1);
		}
		String[] logicStrArr = new String[logicOps.size()];
		logicStrArr = logicOps.toArray(logicStrArr);

		// ��֤ÿһ��
		for (int i = 0; i < expStrArr.length; i++) {
			String currentExp = expStrArr[i].trim();
			String regx4ComparisonOperator = "(?<=\\s)(==|<|>|<=|>=|!=|like|<>)(?=\\s)|(?<=\\s)(isnull|isnotnull)";
			Pattern pattern4ComparisonOperator = Pattern.compile(regx4ComparisonOperator);
			Matcher matcher4ComparisonOperator = pattern4ComparisonOperator.matcher(currentExp);
			if (!matcher4ComparisonOperator.find()) {
				throw new AppException("�ڱ��ʽ��" + currentExp + "����δ�ҵ��Ƚ��������==��<��>��<=��>=��!=��<>��like��isnull��isnotnull��!");
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
				throw new AppException("���ʽ��" + currentExp + "���ṹ���󣬲����ϵ�Ŀ��˫Ŀ������ʽ�ṹ!");
			}
			expInfoObjArr.add(new CompareCell(operator, key, value));
		}

		return new Comparator(expInfoObjArr, logicOps);
	}

	/**
	 * ���߼����ʽ��ֳ����ӹؼ��ʣ�logicarray=[and (, or, ) and,
	 * and],��+�����Ƚ��߼���booleanarray=[{cmpoperator=like, cmpkey=xm, cmpvalue=����2},
	 * ������������ �߼����ʽд�� 1�Ƚ�������� ==��<��>��<=��>=��!=��<>��like��isnull��isnotnull ��2С���� (��) ע��
	 * ÿ���Ƚ������ǰ������пո񣬿����ж���ո�����ǰ��Ҳ��Ҫ�пո�
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
		// һ��һ�е�aaa == ss bbb > ccc
		String[] expStrArr = conditions.split(regx4LogicalOperator);
		// �ṹΪ{cmpoperator=like, cmpkey=xm, cmpvalue=����2}
		ArrayList<HashMap<String, Object>> expInfoObjArr = new ArrayList<HashMap<String, Object>>();
		// �ṹΪ logicarray=[and (, or, ) and, and],
		ArrayList<String> logicOps = new ArrayList<String>();
		while (matcher4LogicalOperator.find()) {
			// ������ and or
			logicOps.add(matcher4LogicalOperator.group());
		}
		// ȥ����������
		// ����Ƿ���������ֻ����booleanֵ��,����ȥ��������(��������)
		for (int i = 0; i < logicOps.size() - 1; i++) {
			String str1 = logicOps.get(i).trim();
			String str2 = logicOps.get(i + 1).trim();

			// ���i�ǵ�һ��(0)�ҷ���Ϊ)�������һ���ҷ���Ϊ(.,��ȥ��booleanֵ�ϵ�ǰ������( ) and)

			if (i == 0 && str1.startsWith(")")) {
				while (str1.startsWith(")") && expStrArr[0].trim().startsWith("(")) {
					logicOps.set(i, str1.substring(1, str1.length()));
					// Ȼ��ȥ��boolean���е���ǰ������
					expStrArr[0] = expStrArr[0].trim().substring(1, expStrArr[0].trim().length());
					str1 = logicOps.get(i).trim();
					str2 = logicOps.get(i + 1).trim();
				}
			}
			if (i == logicOps.size() - 2 && str2.endsWith("(")) {
				while (str2.endsWith("(") && expStrArr[expStrArr.length - 1].trim().endsWith(")")) {
					logicOps.set(logicOps.size() - 1, str2.substring(0, str2.length() - 1));
					// Ȼ��ȥ��boolean���е���ǰ������
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
		// ���ϼ����������ܻ�����boolean��������β��������,����ֻ��ɾ������,�ٴ���ϲ����ַ�����,ȱ)���ں������,ȱ(����ǰ������
		// ɾ������
		while (expStrArr[0].trim().startsWith("(")) {
			expStrArr[0] = expStrArr[0].trim().substring(1, expStrArr[0].trim().length());
		}
		while (expStrArr[expStrArr.length - 1].trim().endsWith(")")) {
			expStrArr[expStrArr.length - 1] = expStrArr[expStrArr.length - 1].trim().substring(0,
					expStrArr[expStrArr.length - 1].trim().length() - 1);
		}
//		String[] logicStrArr = new String[logicOps.size()];
//		logicStrArr = logicOps.toArray(logicStrArr);

		// ��֤ÿһ��
		for (int i = 0; i < expStrArr.length; i++) {
			String currentExp = expStrArr[i].trim();
			String regx4ComparisonOperator = "(?<=\\s)(==|<|>|<=|>=|!=|like|<>)(?=\\s)|(?<=\\s)(isnull|isnotnull)";
			Pattern pattern4ComparisonOperator = Pattern.compile(regx4ComparisonOperator);
			Matcher matcher4ComparisonOperator = pattern4ComparisonOperator.matcher(currentExp);
			if (!matcher4ComparisonOperator.find()) {
				throw new AppException("�ڱ��ʽ��" + currentExp + "����δ�ҵ��Ƚ��������==��<��>��<=��>=��!=��<>��like��isnull��isnotnull��!");
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
				throw new AppException("���ʽ��" + currentExp + "���ṹ���󣬲����ϵ�Ŀ��˫Ŀ������ʽ�ṹ!");
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
