package com.sept.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.sept.exception.AppException;;

@Deprecated
public class DataFormat {

	private static final char[] CHINESE_NUMBER = { 'Áã', 'Ò¼', '·¡', 'Èþ', 'ËÁ', 'Îé', 'Â½', 'Æâ', '°Æ', '¾Á' };
	private static final char[] CHINESE_CODE = { '·Ö', '½Ç', 'Õû' };
	private static final char[] CHINESE_CARRY = { 'Ôª', 'Ê°', '°Û', 'Çª', 'Íò', 'Ê°', '°Û', 'Çª', 'ÒÚ', 'Ê°', '°Û', 'Çª', 'Õ×', 'Ê°',
			'°Û', 'Çª', 'Íò', 'Ê°', '°Û', 'Çª' };
	private static int maxMoneyLength = 16;

	public static void main(String[] args) throws AppException {
		System.out.println(numberToChinese(10002333420.24));
	}

	public static String formatString(String vString) {
		if ((vString == null) || (vString.length() == 0)) {
			return "";
		}
		return vString;
	}

	public static String formatString(Object vObject) {
		if (vObject == null) {
			return "";
		}
		return formatString(vObject.toString());
	}

	public static String formatInt(int vInt) {
		NumberFormat DF = new DecimalFormat("##0");

		return DF.format(vInt);
	}

	public static String formatInt(Integer vInt) throws AppException {
		if (vInt == null) {
			// Alert.isNull("????????????");
		}
		NumberFormat DF = new DecimalFormat("##0");

		return DF.format(vInt);
	}

	public static String formatInt(int vInt, String vFormat) throws AppException {
		if ((vFormat == null) || (vFormat.length() == 0)) {
			return formatInt(vInt);
		}
		return new DecimalFormat(vFormat).format(vInt);
	}

	public static String formatLong(long vLong) {
		NumberFormat DF = new DecimalFormat("##0");

		return DF.format(vLong);
	}

	public static String formatLong(Long vLong) throws AppException {
		if (vLong == null) {
			// Alert.isNull("????????????");
		}
		NumberFormat DF = new DecimalFormat("##0");

		return DF.format(vLong);
	}

	public static String formatLong(long vLong, String vFormat) throws AppException {
		if ((vFormat == null) || (vFormat.length() == 0)) {
			return formatLong(vLong);
		}
		return new DecimalFormat(vFormat).format(vLong);
	}

	public static String formatDouble(double vDouble) throws AppException {
		return formatDouble(vDouble, 2);
	}

	public static String formatDouble(double vDouble, int n) throws AppException {
		return formatDouble(new Double(vDouble), n);
	}

	public static String formatDouble(String vDouble) throws AppException {
		return formatDouble(vDouble, 2);
	}

	public static String formatDouble(String vDouble, int n) throws AppException {
		try {
			return formatDouble(new Double(vDouble), n);
		} catch (NumberFormatException e) {
			// Alert.FormatError("??[" + vDouble + "]????????String??????????!");
		}
		return null;
	}

	public static String formatDouble(double vDouble, String vFormat) throws AppException {
		if ((vFormat == null) || (vFormat.length() == 0)) {
			return formatDouble(vDouble);
		}
		DecimalFormat df = new DecimalFormat(vFormat);
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(vDouble);
	}

	public static String formatDouble(Double vDouble, String vFormat) throws AppException {
		return formatDouble(vDouble.doubleValue(), vFormat);
	}

	public static String formatDouble(String vDouble, String vFormat) throws AppException {
		try {
			return formatDouble(new Double(vDouble), vFormat);
		} catch (NumberFormatException e) {
			// Alert.FormatError("??[" + vDouble + "]????????double??????????!");
		}
		return null;
	}

	public static String formatDouble(Double vDouble) throws AppException {
		return formatDouble(vDouble, 2);
	}

	public static String formatDouble(Double vDouble, int n) throws AppException {
		if (Double.isNaN(vDouble.doubleValue())) {
			// Alert.FormatError("??[" + vDouble + "]??????????????");
		}
		if (Double.isInfinite(vDouble.doubleValue())) {
			// Alert.FormatError("??[" + vDouble + "]??????????????????");
		}
		String f = "##0";
		int i = 0;
		while (i < n) {
			f = f + (i++ == 0 ? ".0" : "0");
		}
		DecimalFormat DF = new DecimalFormat(f);
		DF.setRoundingMode(RoundingMode.HALF_UP);
		return DF.format(vDouble.doubleValue());
	}

	public static String formatBoolean(int vInt) {
		if (vInt == 1) {
			return "true";
		}
		return "false";
	}

	public static String formatBoolean(String vString) {
		if (("1".equals(vString)) || ("true".equalsIgnoreCase(vString)) || ("yes".equalsIgnoreCase(vString))
				|| ("y".equalsIgnoreCase(vString))) {
			return "true";
		}
		return "false";
	}

	public static String formatBoolean(Boolean vBool) {
		if (vBool.booleanValue()) {
			return "true";
		}
		return "false";
	}

	public static String formatBoolean(boolean vBool) {
		if (vBool) {
			return "true";
		}
		return "false";
	}

	public static String formatNumber(double vDouble, String vFormat) throws AppException {
		return formatDouble(vDouble, vFormat);
	}

	public static String formatNumber(double vDouble) throws AppException {
		return formatDouble(vDouble);
	}

	public static String formatNumber(int vInt) throws AppException {
		return formatInt(vInt);
	}

	public static String formatNumber(int vInt, String vFormat) throws AppException {
		return formatInt(vInt, vFormat);
	}

	public static String numberToChinese(long vMoney) throws AppException {
		int leng = CHINESE_CARRY.length > maxMoneyLength ? maxMoneyLength : CHINESE_CARRY.length;
		if (Math.abs(vMoney) < Math.pow(10.0D, leng)) {
			DecimalFormat DF = new DecimalFormat("#.00");

			String numStr = DF.format(vMoney);
			return numberToChinese(numStr);
		}
		// Alert.FormatError("??[" + vMoney + "]??????????????????????????");
		return null;
	}

	public static String numberToChinese(double vMoney) throws AppException {
		int leng = CHINESE_CARRY.length > maxMoneyLength ? maxMoneyLength : CHINESE_CARRY.length;
		if (Math.abs(vMoney) < Math.pow(10.0D, leng)) {
			DecimalFormat DF = new DecimalFormat("#.00");
			String numStr = DF.format(vMoney);
			return numberToChinese(numStr);
		}
		// Alert.FormatError("??[" + vMoney + "]??????????????????????????");
		return null;
	}

	private static String numberToChinese(String vMoneyString) throws AppException {
		String intStr = vMoneyString.substring(0, vMoneyString.length() - 3);
		String decStr = vMoneyString.substring(vMoneyString.length() - 2);
		StringBuffer moneyStr = new StringBuffer();
		if (intStr.indexOf("-") != -1) {
			moneyStr.append("????");
			intStr = intStr.substring(intStr.indexOf("-") + 1);
		}
		boolean hasZero = false;
		boolean hasNumber = true;

		int intLen = intStr.length();
		int zeroCount = 0;
		for (int i = 0; i < intLen; i++) {
			char oneNum = intStr.charAt(i);
			if (oneNum == '0') {
				zeroCount++;
			} else {
				zeroCount = 0;
			}
			if (((intLen - i - 1) % 4 == 0) && (oneNum == '0')) {
				if ((hasNumber) && (zeroCount <= 4)) {
					if ((zeroCount != 4) || (intLen == i + 1)) {
						moneyStr.append(CHINESE_CARRY[(intLen - i - 1)]);
					}
					hasNumber = false;
				}
				hasZero = false;
			} else if ((oneNum > '0') && (oneNum <= '9')) {
				hasNumber = true;
				if (hasZero) {
					moneyStr.append(CHINESE_NUMBER[0]);
				}
				moneyStr.append(CHINESE_NUMBER[(oneNum - '0')]);
				moneyStr.append(CHINESE_CARRY[(intLen - i - 1)]);
				hasZero = false;
			} else {
				hasZero = true;
			}
		}
		int decLen = decStr.length();
		hasZero = false;
		if (decStr.equals("00")) {
			if (intLen == 0) {
				moneyStr.append("" + CHINESE_NUMBER[0] + CHINESE_CARRY[0]);
			}
			moneyStr.append(CHINESE_CODE[2]);
		} else {
			for (int i = 0; i < decLen; i++) {
				char oneNum = decStr.charAt(i);
				if ((oneNum > '0') && (oneNum <= '9')) {
					if (hasZero) {
						moneyStr.append(CHINESE_NUMBER[0]);
						hasZero = false;
					}
					moneyStr.append(CHINESE_NUMBER[(oneNum - '0')]);
					moneyStr.append(CHINESE_CODE[(decLen - i - 1)]);
				} else {
					hasZero = true;
				}
			}
		}
		return moneyStr.toString();
	}

	public static String formatValue(Object value, String mask) throws AppException {
		if (value == null) {
			return "";
		}
		if (value.toString().equalsIgnoreCase("")) {
			return "";
		}
		if ((mask == null) || (mask.equals(""))) {
			return formatValueWithoutMask(value);
		}
		if (("date".equalsIgnoreCase(mask)) && ((value instanceof java.util.Date))) {
			return DateUtil.formatDate((java.util.Date) value, "yyyyMMddhhmmss");
		}
		if ((mask.indexOf("#") != -1) && (mask.indexOf(".") == -1)) {
			if (mask.indexOf("+") > -1) {
				mask = mask.replace("+", "");
			}
			if ((value instanceof BigDecimal)) {
				return formatInt(((BigDecimal) value).intValue(), mask);
			}
			if ((value instanceof Long)) {
				return formatLong(((Long) value).longValue(), mask);
			}
			if ((value instanceof Integer)) {
				return formatInt(((Integer) value).intValue(), mask);
			}
			if ((value instanceof Double)) {
				return formatLong(new BigDecimal(((Double) value).doubleValue()).longValue(), mask);
			}
			if ((value instanceof String)) {
				if (value.equals("")) {
					return "";
				}
				return value.toString();
			}
		} else if ((mask.indexOf("#") != -1) && (mask.indexOf(".") != -1)) {
			if (mask.indexOf("+") > -1) {
				mask = mask.replace("+", "");
			}
			if ((value instanceof BigDecimal)) {
				return formatDouble(((BigDecimal) value).doubleValue(), mask);
			}
			if ((value instanceof Long)) {
				return formatDouble(((Long) value).doubleValue(), mask);
			}
			if ((value instanceof Integer)) {
				return formatDouble(((Integer) value).doubleValue(), mask);
			}
			if ((value instanceof Double)) {
				return formatDouble(((Double) value).doubleValue(), mask);
			}
			if ((value instanceof String)) {
				if (value.equals("")) {
					return "";
				}
				return value.toString();
			}
		} else {
			if ((value instanceof java.util.Date)) {
				return DateUtil.formatDate((java.util.Date) value, mask);
			}
			return value.toString();
		}
		return value.toString();
	}

	public static String formatValueWithoutMask(Object value) throws AppException {
		if (((value instanceof BigDecimal)) || ((value instanceof Double))) {
			return formatValue(value, "##0.00");
		}
		if (((value instanceof Long)) || ((value instanceof Integer))) {
			return formatValue(value, "##0");
		}
		if (((value instanceof java.util.Date)) || ((value instanceof java.sql.Date))) {
			return DateUtil.formatDate((java.util.Date) value, "yyyyMMddhhmmss");
		}
		value = stripNonValidXMLCharacters(value.toString());
		return value.toString();
	}

	public static String filterSpecialChars(char in) {
		switch (in) {
		case '&':
			return "&amp;";
		case '<':
			return "&lt;";
		case '>':
			return "&gt;";
		case '\'':
			return "&#39;";
		case '"':
			return "&quot;";
		case '\n':
			return "&#xA;";
		case '\r':
			return "&#xD;";
		case '\t':
			return "&#x9;";
		}
		return String.valueOf(in);
	}

	public static Object stripNonValidXMLCharacters(String in) {
		StringBuffer out = new StringBuffer();
		if ((in == null) || ("".equals(in))) {
			return "";
		}
		for (int i = 0; i < in.length(); i++) {
			char current = in.charAt(i);
			if ((current == '\t') || (current == '\n') || (current == '\r') || ((current >= ' ') && (current <= 55295))
					|| ((current >= 57344) && (current <= 65533)) || ((current >= 65536) && (current <= 1114111))) {
				out.append(filterSpecialChars(current));
			}
		}
		return out.toString();
	}
}
