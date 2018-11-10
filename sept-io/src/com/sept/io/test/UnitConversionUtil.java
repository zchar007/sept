package com.sept.io.test;

import java.text.DecimalFormat;

/**
 * ���ڵ�λת������
 * 
 * @author zchar
 */
public final class UnitConversionUtil{
	private static final char[] CHINESE_NUMBER = { '��', 'Ҽ', '��', '��', '��', '��', '½', '��', '��', '��' };
	private static final char[] CHINESE_CODE = { '��', '��', '��' };
	private static final char[] CHINESE_CARRY = { 'Ԫ', 'ʰ', '��', 'Ǫ', '��', 'ʰ', '��', 'Ǫ', '��', 'ʰ', '��', 'Ǫ', '��', 'ʰ', '��', 'Ǫ', '��', 'ʰ', '��', 'Ǫ' };

	/**
	 * �ɴ����bitת�ɺ��ʵĵ�λ��ʾ ���ֲ�̫����
	 * 
	 * @param bits
	 * @return
	 */
	@Deprecated
	public final static String BitToAnySuitableUnit(long bits) {
		long sizeTemp = bits;
		String returnStr = bits + "Bit";
		DecimalFormat format = new DecimalFormat("0.00");
		if (sizeTemp >= 1024) {
			sizeTemp = bits / 1024l;
			String xs = bits % 1024l + "";
			xs = format.format((Double.parseDouble(xs) / 1024.0d)).substring(2);
			returnStr = sizeTemp + "." + xs + "KB";
		}
		if (sizeTemp >= 1024) {
			sizeTemp = bits / (1024l * 1024l);
			String xs = bits % (1024l * 1024l) + "";
			xs = format.format((Double.parseDouble(xs) / 1024.0d / 1024.0d))
				.substring(2);
			returnStr = sizeTemp + "." + xs + "MB";
		}
		if (sizeTemp >= 1024) {
			sizeTemp = bits / (1024l * 1024l * 1024l);
			String xs = bits % (1024l * 1024l * 1024l) + "";
			xs = format.format((Double.parseDouble(xs) / 1024.0d / 1024.0d / 1024.0d))
				.substring(2);

			returnStr = sizeTemp + "." + xs + "GB";
		}
		if (sizeTemp >= 1024) {
			sizeTemp = bits / (1024l * 1024l * 1024l * 1024l);
			String xs = bits % (1024l * 1024l * 1024l * 1024l) + "";
			xs = format.format((Double.parseDouble(xs) / 1024.0d / 1024.0d / 1024.0d / 1024.0d))
				.substring(2);

			returnStr = sizeTemp + "." + xs + "TB";
		}
		if (sizeTemp >= 1024) {
			sizeTemp = bits / (1024l * 1024l * 1024l * 1024l * 1024l);
			String xs = bits % (1024l * 1024l * 1024l * 1024l * 1024l) + "";
			xs = format.format((Double.parseDouble(xs) / 1024.0d / 1024.0d
					/ 1024.0d / 1024.0d / 1024.0d)).substring(2);

			returnStr = sizeTemp + "." + xs + "PB";
		}
		return returnStr;
	}

	/**
	 * �ɴ����bitת�ɺ��ʵĵ�λ��ʾ
	 * 
	 * @param bits
	 * @return
	 */
	public final static String formatBitToASUnit(long bits) {
		long sizeTemp = bits;
		String returnStr = bits + "Bit";
		DecimalFormat format = new DecimalFormat("0.00");
		if (sizeTemp >= 1024) {
			sizeTemp = bits / 1024l;
			String xs = bits % 1024l + "";
			xs = format.format((Double.parseDouble(xs) / 1024.0d)).substring(2);
			returnStr = sizeTemp + "." + xs + "KB";
		}
		if (sizeTemp >= 1024) {
			sizeTemp = bits / (1024l * 1024l);
			String xs = bits % (1024l * 1024l) + "";
			xs = format.format((Double.parseDouble(xs) / 1024.0d / 1024.0d))
				.substring(2);
			returnStr = sizeTemp + "." + xs + "MB";
		}
		if (sizeTemp >= 1024) {
			sizeTemp = bits / (1024l * 1024l * 1024l);
			String xs = bits % (1024l * 1024l * 1024l) + "";
			xs = format.format((Double.parseDouble(xs) / 1024.0d / 1024.0d / 1024.0d))
				.substring(2);

			returnStr = sizeTemp + "." + xs + "GB";
		}
		if (sizeTemp >= 1024) {
			sizeTemp = bits / (1024l * 1024l * 1024l * 1024l);
			String xs = bits % (1024l * 1024l * 1024l * 1024l) + "";
			xs = format.format((Double.parseDouble(xs) / 1024.0d / 1024.0d / 1024.0d / 1024.0d))
				.substring(2);

			returnStr = sizeTemp + "." + xs + "TB";
		}
		if (sizeTemp >= 1024) {
			sizeTemp = bits / (1024l * 1024l * 1024l * 1024l * 1024l);
			String xs = bits % (1024l * 1024l * 1024l * 1024l * 1024l) + "";
			xs = format.format((Double.parseDouble(xs) / 1024.0d / 1024.0d
					/ 1024.0d / 1024.0d / 1024.0d)).substring(2);

			returnStr = sizeTemp + "." + xs + "PB";
		}
		return returnStr;
	}

	/**
	 * ��ʽ�����뵽���ʵĸ�ʽ ����̫���������еط�Ҫ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	@Deprecated
	public final static String MillisecondToAnySuitableUnit(long times) {
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;

		Long day = times / dd;
		Long hour = (times - day * dd) / hh;
		Long minute = (times - day * dd - hour * hh) / mi;
		Long second = (times - day * dd - hour * hh - minute * mi) / ss;
		Long milliSecond = times - day * dd - hour * hh - minute * mi - second
				* ss;

		StringBuffer sb = new StringBuffer();
		if (day > 0) {
			sb.append(day + "��");
		}
		if (hour > 0) {
			sb.append(hour + "Сʱ");
		}
		if (minute > 0) {
			sb.append(minute + "��");
		}
		if (second > 0) {
			sb.append(second + "��");
		}
		if (milliSecond > 0) {
			sb.append(milliSecond + "����");
		}
		return sb.toString();
	}

	/**
	 * ��ʽ�����뵽���ʵĸ�ʽ
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static String formatMSToASUnit(long times) {
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;

		Long day = times / dd;
		Long hour = (times - day * dd) / hh;
		Long minute = (times - day * dd - hour * hh) / mi;
		Long second = (times - day * dd - hour * hh - minute * mi) / ss;
		Long milliSecond = times - day * dd - hour * hh - minute * mi - second
				* ss;

		StringBuffer sb = new StringBuffer();
		if (day > 0) {
			sb.append(day + "��");
		}
		if (hour > 0) {
			sb.append(hour + "Сʱ");
		}
		if (minute > 0) {
			sb.append(minute + "��");
		}
		if (second > 0) {
			sb.append(second + "��");
		}
		if (milliSecond > 0) {
			sb.append(milliSecond + "����");
		}
		return sb.toString();
	}

	/**
	 * Ǯ��ת�����ĵ�Ǯ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static String formatMoneyToChinese(double vMoney) throws Exception {
		return formatMoneyToChinese(Double.toString(vMoney));
	}

	/**
	 * Ǯ��ת�����ĵ�Ǯ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static String formatMoneyToChinese(String vMoney) throws Exception {
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
			throw new Exception("����̫���С��̫��:" + vMoney);
		}
		String chineseStr = "";
		int carry = zheng.length() - 1;
		for (int i = 0; i < zheng.length(); i++) {
			chineseStr += (CHINESE_NUMBER[Integer.parseInt(zheng.charAt(i) + "")])
					+ (CHINESE_CARRY[carry - i] + "");
		}
		// if(xiao.length()>0){
		// chineseStr = chineseStr.substring(0,chineseStr.length()-1);
		// }
		for (int i = 0; i < xiao.length(); i++) {
			chineseStr += (CHINESE_NUMBER[Integer.parseInt(xiao.charAt(i) + "")])
					+ (CHINESE_CODE[i] + "");
		}

		/**
		 * 1.ȥ������,��,��,Ԫ֮��������ֵΪ0 ��λ 2.���϶�����Ϊ*(����ֱ��ת����,ȥ��4,5����) 3,ȥ����������
		 * 4.�ָ��ɶ�������϶��ɵ��� 5.ȥ����,��,��,Ԫǰ�ߵ��� 6.ȥ��ǰ���0 7.�����Ԫ��β,�������,8.ɾ���ؼ�λ������
		 */
		// System.out.println(chineseStr);
		// 1.ȥ������,��,��֮��������ֵΪ0 ��λ
		chineseStr = chineseStr.replaceAll("(?<=((\\w|[\\u4e00-\\u9fa5])��))([^(��|��|��|Ԫ)])(?=(\\w|[\\u4e00-\\u9fa5]){0,})", "");
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
		while (chineseStr.startsWith("��")) {
			chineseStr = chineseStr.substring(1);
		}
		while (chineseStr.endsWith("��")) {
			chineseStr = chineseStr.substring(0, chineseStr.length() - 1);
		}
		return chineseStr;

	}

	public final static void main(String[] args) {
		System.out.println(UnitConversionUtil.BitToAnySuitableUnit(1234247832647l));

	}
}
