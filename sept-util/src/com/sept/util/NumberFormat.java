package com.sept.util;

import java.text.DecimalFormat;
import java.util.Random;

import com.sept.exception.ApplicationException;

/**
 * �����ڲ����е��� ���С���λ�͸�����д�ĸ���Ʊ�ݺͽ���ƾ֤�ǰ���֧��������ֽ��ո�����Ҫ���ݣ�ֱ�ӹ�ϵ��֧�������׼ȷ����ʱ�Ͱ�ȫ��Ʊ�ݺͽ���ƾ֤�����С�
 * ��λ�͸���ƾ�Լ�������Ļ��ƾ֤
 * ���Ǽ��ؾ���ҵ�����ȷ�������ε�һ������֤������ˣ���дƱ�ݺͽ���ƾ֤����������׼�����淶����Ҫ����ȫ��������ȷ���ּ�����������©�����ʲݡ���ֹͿ�ġ�
 * ���Ĵ�д�������Ӧ��������������д
 * ����Ҽ���������������顢½���⡢�ơ�����ʰ���ۡ�Ǫ�����ڡ�Ԫ���ǡ��֡��㡢��(��)��������������һ����(��)�������ġ���
 * �������ߡ��ˡ��š�ʮ��ë����(��0)��д������������֡�������������д��ʹ�÷����֣��緡��½���ڡ���Բ�ģ�ҲӦ����
 * ����Ҵ�д����ȷд����Ӧע�����¼��
 * һ�����Ĵ�д������ֵ���Ԫ��Ϊֹ�ģ��ڡ�Ԫ��֮��Ӧд������(������)�֣��ڡ��ǡ�֮�󣬿��Բ�д������(������)
 * �֣���д��������С��֡��ģ����֡����治д������(������)�֡�
 * �������Ĵ�д�������ǰӦ����������ҡ���������д�������Ӧ���ӡ�����ҡ�������д���������пհ�
 * ����д�������ǰδӡ������ҡ������ģ�Ӧ�������ҡ����֣���Ʊ�ݺͽ���ƾ֤��д������ڲ���Ԥӡ�̶��ġ�Ǫ���ۡ�ʰ����Ǫ���ۡ�ʰ��Ԫ���ǡ��֡�������
 * ��������������Сд����������С�0��ʱ�����Ĵ�дӦ���պ������Թ��ɡ�������ֹ��ɺͷ�ֹͿ�ĵ�Ҫ�������д���������£�
 * 1�������������м��С�0��ʱ�����Ĵ�дҪд���㡱�֣��磤1409.50Ӧд�������ҼǪ�������Ԫ��ǣ�
 * 2�������������м������м�����0��ʱ�����Ĵ�д����м����ֻдһ�����㡱�֣��磤6007.14Ӧд�������½Ǫ����ԪҼ�����֡�
 * 3�����������������λ��Ԫλ��
 * ��0�������������м������м�����0������λ��ԪλҲ�ǡ�0����ǧλ����λ���ǡ�0��ʱ�����Ĵ�д����п���ֻдһ�����֣�Ҳ���Բ�д
 * ���㡱�֣��磤1680.32Ӧд�������ҼǪ½�۰�ʰԪ�����Ƿ���
 * ������д�������ҼǪ½�۰�ʰԪ���Ƿ��֡����磤107000.53Ӧд�������Ҽʰ����ǪԪ��������֣�����д�������Ҽʰ������ǪԪ������֡�
 * 4��������������ֽ�λ��
 * ��0������λ���ǡ�0��ʱ�����Ĵ�д��Ԫ������Ӧд���㡱�֣��磤16409.02Ӧд�������Ҽ��½Ǫ�������Ԫ�㷡�֣����磤325
 * .04Ӧд����������۷�ʰ��Ԫ�����֡� �ġ�������Сд�������ǰ���Ӧ��д����ҷ��š�������������Сд�������Ҫ������д��������д�ֱ治�塣
 * �塢Ʊ�ݵĳ�Ʊ���ڱ���ʹ�����Ĵ�д
 * ��Ϊ��ֹ����Ʊ�ݵĳ�Ʊ���ڣ�����д�¡���ʱ����ΪҼ������Ҽʰ�ģ���ΪҼ������Ҽʰ����ʰ����ʰ�ģ�Ӧ����ǰ�ӡ��㡱����ΪʰҼ��ʰ����Ӧ����ǰ��
 * ��Ҽ������1��15��Ӧд����Ҽ��Ҽʰ���գ�����10��20��Ӧд����Ҽʰ���㷡ʰ�ա�
 * ����Ʊ�ݳ�Ʊ����ʹ��Сд��д�ģ����в���������д����δ��Ҫ��淶��д�ģ����п����������ɴ������ʧ���ɳ�Ʊ�����ге���
 * 
 * @author �ų�
 * @version 1.0 ����ʱ�� 2017-5-27
 */
@Deprecated
public class NumberFormat {
	private static final char[] CHINESE_NUMBER = { '��', 'Ҽ', '��', '��', '��', '��', '½', '��', '��', '��' };
	private static final char[] CHINESE_CODE = { '��', '��', '��' };
	private static final char[] CHINESE_CARRY = { 'Ԫ', 'ʰ', '��', 'Ǫ', '��', 'ʰ', '��', 'Ǫ', '��', 'ʰ', '��', 'Ǫ', '��', 'ʰ',
			'��', 'Ǫ', '��', 'ʰ', '��', 'Ǫ' };

	public static void main(String[] args) throws ApplicationException {
		// String str = "11118200000.34";
		// System.out.println(formatToChinese(str));
		// System.out.println(DataFormat.numberToChinese(11118200000.34));
		// Ҽʰ��Ǫ�۰�ʰ����½Ǫ���ʰ������Ǫ��ʰԪ��Ƿ�
		System.out.println(formatToChinese("736900400.01"));
		System.out.println(DataFormat.numberToChinese(736900400.01));
		Random random = new Random();
		for (int i = 0; i < 999999999; i++) {
			double d = (double) random.nextInt(999999999) + random.nextInt(100) * 0.01;
			String str = DecimalFormat.getInstance().format(d);
			String result = str.replaceAll(",", "");
			String s1 = formatToChinese(result);
			String s2 = DataFormat.numberToChinese(d);
			if (!s1.equals(s2)) {
				System.out.println(result + " : mine--" + s1 + "  othe--" + s2);
			}
			// System.out.println(formatToChinese("1007800120010.00"));
			// System.out.println(DataFormat.numberToChinese(1007800120010.00));
		}
		System.out.println("over");

		// String chineseStr = "Ҽʰ������Ǫ��۰�ʰ����½Ǫ���Ҽʰ������Ǫ�����ʰ��Ԫ������";
		// Ҽʰ��������۰�ʰ����½Ǫ��Ҽʰ�����������Ԫ�����

		// chineseStr =
		// chineseStr.replaceAll("(?<=((\\w|[\\u4e00-\\u9fa5])��))([^(��|��|��)])(?=(\\w|[\\u4e00-\\u9fa5]))",
		// "");
		// System.out.println(chineseStr);

	}

	public static String formatToChinese(double vMoney) throws ApplicationException {
		return formatToChinese(Double.toString(vMoney));
	}

	public static String formatToChinese(String vMoney) throws ApplicationException {
		// �ǲ������ָ�ʽ,������ֱ�ӱ���
		Double.parseDouble(vMoney);
		String zheng = vMoney;
		String xiao = "";
		// ���Ǯ������С����,��
		if (vMoney.indexOf('.') > 0) {
			String[] strTemp = vMoney.split("\\.");
			zheng = strTemp[0];
			xiao = strTemp[1];
		}
		if (zheng.length() > CHINESE_CARRY.length || xiao.length() > 2) {
			throw new ApplicationException("����̫���С��̫��:" + vMoney);
		}
		String chineseStr = "";
		int carry = zheng.length() - 1;
		for (int i = 0; i < zheng.length(); i++) {
			chineseStr += (CHINESE_NUMBER[Integer.parseInt(zheng.charAt(i) + "")]) + (CHINESE_CARRY[carry - i] + "");
		}
		// if(xiao.length()>0){
		// chineseStr = chineseStr.substring(0,chineseStr.length()-1);
		// }
		for (int i = 0; i < xiao.length(); i++) {
			chineseStr += (CHINESE_NUMBER[Integer.parseInt(xiao.charAt(i) + "")]) + (CHINESE_CODE[i] + "");
		}

		/**
		 * 1.ȥ������,��,��,Ԫ֮��������ֵΪ0 ��λ 2.���϶�����Ϊ*(����ֱ��ת����,ȥ��4,5����) 3,ȥ���������� 4.�ָ��ɶ�������϶��ɵ���
		 * 5.ȥ����,��,��,Ԫǰ�ߵ��� 6.ȥ��ǰ���0 7.�����Ԫ��β,�������,8.ɾ���ؼ�λ������
		 */
		// System.out.println(chineseStr);
		// 1.ȥ������,��,��֮��������ֵΪ0 ��λ
		chineseStr = chineseStr
				.replaceAll("(?<=((\\w|[\\u4e00-\\u9fa5])��))([^(��|��|��|Ԫ)])(?=(\\w|[\\u4e00-\\u9fa5]){0,})", "");
		// System.out.println(chineseStr);
		// 2.���϶�����Ϊ*
		chineseStr = chineseStr.replaceAll("��{2,}", "��");
		// 3,ȥ����������
		// chineseStr = chineseStr.replaceAll("��", "");
		// 4.�ָ��ɶ�������϶��ɵ���
		// chineseStr = chineseStr.replaceAll("\\*", "��");
		// 5.ȥ����,��,��,Ԫǰ�ߵ���
		chineseStr = chineseStr.replaceAll("(��)(?=(��|��|��|Ԫ)(\\w|[\\u4e00-\\u9fa5]){0,})", "");
		// 6.ȥ��ǰ���0
		while (chineseStr.startsWith("��")) {
			chineseStr = chineseStr.substring(2, chineseStr.length());
		}
		while (chineseStr.endsWith("��")) {
			chineseStr = chineseStr.substring(0, chineseStr.length() - 1);
		}
		// 7.�����Ԫ��β,�������
		if (chineseStr.endsWith("Ԫ")) {
			chineseStr += "��";
		} else {
			// ��ʮ��Ԫ������Ԫ��ǧ��Ԫ��Ԫȥ��
			chineseStr = chineseStr.replaceAll("(?<=(ʰ��|����|Ǫ��))(Ԫ)(?=(\\w|[\\u4e00-\\u9fa5]){0,})", "");
			// chineseStr = chineseStr.replaceAll("ʰ��Ԫ", "ʰ��");
			// chineseStr = chineseStr.replaceAll("����Ԫ", "����");
			// chineseStr = chineseStr.replaceAll("Ǫ��Ԫ", "Ǫ��");
		}
		while (chineseStr.startsWith("Ԫ")) {
			chineseStr = chineseStr.substring(1);
		}

		// 8.ɾ���ؼ�λ������
		chineseStr = chineseStr.replaceAll("(?<=(��|��))(��|��)(?=(\\w|[\\u4e00-\\u9fa5]){0,})", "");
		// ɾ��ǰ����������,��������Ԫ,�����Ϊ����Ͽ��,������ֿ�ܵĹ�����ĥ����,�Ӳ���Ԫ����û�����ԵĲ�ͬ
		// chineseStr =
		// chineseStr.replaceAll("(?<=(��|��|��))(Ԫ)(?=((\\w|[\\u4e00-\\u9fa5])){1,})","");

		// ����Ҫɾ����ǰ�ߵ���

		while (chineseStr.startsWith("��")) {
			chineseStr = chineseStr.substring(1);
		}
		while (chineseStr.endsWith("��")) {
			chineseStr = chineseStr.substring(0, chineseStr.length() - 1);
		}
		return chineseStr;

	}

}
