package com.sept.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.sept.exception.ApplicationException;

/**
 * @author ZC6
 */
public final class DateUtil {
	/*
	 * ��ȡ��ǰʱ��ĸ�������
	 */
	public final static HashMap<String, Integer> getCurrentHMTime() {
		return getHMTime(new Date());
	}

	/*
	 * ��ȡʱ��ĸ�������
	 */
	public final static HashMap<String, Integer> getHMTime(Date date) {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		hm.put("year", calendar.get(Calendar.YEAR));
		hm.put("month", (calendar.get(Calendar.MONTH) + 1));
		hm.put("day", calendar.get(Calendar.DAY_OF_MONTH));
		hm.put("hour", calendar.get(Calendar.HOUR_OF_DAY));
		hm.put("minute", calendar.get(Calendar.MINUTE));
		hm.put("second", calendar.get(Calendar.SECOND));
		hm.put("week", calendar.get(Calendar.DAY_OF_WEEK) - 1);
		hm.put("am_pm", calendar.get(Calendar.AM_PM));
		return hm;
	}

	/**
	 * ��ָ����ʽ��ȡ��ǰʱ��
	 * 
	 * @param formatStr
	 * @return
	 */
	public final static String getCurrentDate(String formatStr) {
		Calendar now = Calendar.getInstance();
		return formatDate(now.getTime(), formatStr);

	}

	/**
	 * ��ָ����ʽ��ȡ��ǰʱ��
	 * 
	 * @param formatStr
	 * @return
	 */
	public final static Date getCurrentDate() {
		Calendar now = Calendar.getInstance();
		return now.getTime();
	}

	/**
	 * ��ʽ��һ��ʱ��
	 * 
	 * @param d
	 * @param formatStr
	 * @return
	 */
	public final static String formatDate(Date d, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(d);
	}

	/**
	 * ��ȡһ�����ݿ�ʱ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static java.sql.Date getCurrentDBDate() {
		return new java.sql.Date(new Date().getTime());
	}

	/**
	 * sqldateתjavadate
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static Date formatSqlDateToJavaDate(java.sql.Date date) {
		return new Date(date.getTime());
	}

	/**
	 * javadateתsqldate
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static java.sql.Date formatSqlDateToJavaDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * ���ַ����趨��ǰʱ�� 2016 10 14 09 08 07
	 * 
	 * @param formatStr
	 * @return
	 * @throws Exception
	 */
	private static Date formatNormStrToDate(String timeStr) throws ApplicationException {
		Calendar cal = Calendar.getInstance();
		String yx = "4,6,8,10,12,14";
		String dq = timeStr.length() + "";
		if (yx.indexOf(dq) < 0) {
			throw new ApplicationException("��" + timeStr + "�����ڸ�ʽ���󣡣�");
		}
		while (timeStr.length() != 14) {
			timeStr += "01";
		}
		int year = Integer.parseInt(timeStr.substring(0, 4));
		int month = Integer.parseInt(timeStr.substring(4, 6));
		int day = Integer.parseInt(timeStr.substring(6, 8));
		int hour = Integer.parseInt(timeStr.substring(8, 10));
		int minutes = Integer.parseInt(timeStr.substring(10, 12));
		int seconds = Integer.parseInt(timeStr.substring(12, 14));
		if (month > 12 || month < 1) {
			throw new ApplicationException("��ʽ���ַ���ʱ,�·ݳ�����" + month + "��");
		}
		if (day < 1) {
			throw new ApplicationException("��ʽ���ַ���ʱ,�ճ�����" + day + "��");
		}
		// ���������
		if (month == 2) {
			if (isLeapYear(year)) {
				if (day > 29) {
					throw new ApplicationException("��ʽ���ַ���ʱ,�ճ�����" + day + "��,��ݡ�" + year + "�������꣬���²��ô���29��");
				}
			} else {
				if (day > 28) {
					throw new ApplicationException("��ʽ���ַ���ʱ,�ճ�����" + day + "��,��ݡ�" + year + "����ƽ�꣬���²��ô���28��");
				}
			}
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day > 30) {
				throw new ApplicationException("��ǰ�·�Ϊ��" + month + "�������·ݲ��ô���30��");
			}
		}
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			if (day > 31) {
				throw new ApplicationException("��ǰ�·�Ϊ��" + month + "�������·ݲ��ô���31��");
			}
		}
		// Сʱ�ķ�ΧΪ0ʱ��24ʱ [0,24)
		if (hour >= 24 || hour < 0) {
			throw new ApplicationException("Сʱ�ķ�ΧΪ [0,24)����ǰ¼��Ϊ��" + hour + "��");
		}
		// Сʱ�ķ�ΧΪ0ʱ��24ʱ [0,24)
		if (minutes >= 60 || minutes < 0) {
			throw new ApplicationException("���ӵķ�ΧΪ [0,60)����ǰ¼��Ϊ��" + minutes + "��");
		}
		// Сʱ�ķ�ΧΪ0ʱ��24ʱ [0,24)
		if (seconds >= 60 || seconds < 0) {
			throw new ApplicationException("��ķ�ΧΪ [0,60)����ǰ¼��Ϊ��" + seconds + "��");
		}
		cal.set(year, month - 1, day, hour, minutes, seconds);
		return cal.getTime();
	}

	/**
	 * ��ʽ�������ʽ��String Ϊdate
	 * 
	 * @author �ų�
	 * @throws Exception
	 * @date ����ʱ�� 2017-6-3
	 * @since V1.0
	 */
	public final static Date formatStrToDate(String dateStr) throws ApplicationException {
		String str = "";
		for (int i = 0; i < dateStr.length(); i++) {
			if (MathUtil.isNumber(dateStr.charAt(i) + "")) {
				str += dateStr.charAt(i);
			}
		}
		return formatNormStrToDate(str);
	}

	/**
	 * ��һ�������ַ�������һ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static String addYearsToStr(String date, int years) throws Exception {
		int length = date.length();
		String format = calculateDateStrFormat(date);
		Date d = formatStrToDate(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.YEAR, years);
		d = calendar.getTime();
		format = format.substring(0, length);
		return formatDate(d, format);
	}

	/**
	 * ��һ����������һ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static Date addYearsToDate(Date date, int years) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, years);
		date = calendar.getTime();
		return date;
	}

	/**
	 * ��һ�������ַ�������һ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static String addMonthsToStr(String date, int months) throws Exception {
		int length = date.length();
		String format = calculateDateStrFormat(date);
		Date d = formatStrToDate(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.MONTH, months);
		d = calendar.getTime();
		format = format.substring(0, length);
		return formatDate(d, format);
	}

	/**
	 * ��һ����������һ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static Date addMonthsToDate(Date date, int months) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		date = calendar.getTime();
		return date;
	}

	/**
	 * ��һ�������ַ�������һ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static String addDaysToStr(String date, int days) throws Exception {
		int length = date.length();
		String format = calculateDateStrFormat(date);
		Date d = formatStrToDate(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, days);
		d = calendar.getTime();
		format = format.substring(0, length);
		return formatDate(d, format);
	}

	/**
	 * ��һ����������һ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static Date addDaysToDate(Date date, int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		date = calendar.getTime();
		return date;
	}

	/**
	 * ��һ�������ַ�������һСʱ
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static String addHoursToStr(String date, int hours) throws Exception {

		int length = date.length();
		String format = calculateDateStrFormat(date);
		Date d = formatStrToDate(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.HOUR, hours);
		d = calendar.getTime();
		format = format.substring(0, length);
		return formatDate(d, format);
	}

	/**
	 * ��һ����������һСʱ
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static Date addHoursToDate(Date date, int hours) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hours);
		date = calendar.getTime();
		return date;
	}

	/**
	 * ��һ�������ַ�������һ����
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static String addMinutesToStr(String date, int minutes) throws Exception {

		int length = date.length();
		String format = calculateDateStrFormat(date);
		Date d = formatStrToDate(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.MINUTE, minutes);
		d = calendar.getTime();
		format = format.substring(0, length);
		return formatDate(d, format);
	}

	/**
	 * ��һ����������һ����
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static Date addMinutesToDate(Date date, int minutes) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		date = calendar.getTime();
		return date;
	}

	/**
	 * ��һ�������ַ�������һ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static String addSecondToStr(String date, int second) throws Exception {

		int length = date.length();
		String format = calculateDateStrFormat(date);
		Date d = formatStrToDate(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.SECOND, second);
		d = calendar.getTime();
		format = format.substring(0, length);
		return formatDate(d, format);
	}

	/**
	 * ��һ����������һ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-5-30
	 * @since V1.0
	 */
	public final static Date addSecondToDate(Date date, int second) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, second);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 1.��ǰ����yyyy ---->ss������Ӻ�����ٵ����� 2.���Բ�дyyyy
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-6
	 * @since V1.0
	 */
	public final static String calculateDateStrFormat(String dateStr) throws ApplicationException {
		// String str = "yyyyMMddhhmmss";
		// int[] index = {4,6,8,10,12};
		// int nowIndex = 0;
		String[] format = { "MM", "dd", "hh", "mm", "ss" };
		int count = 0;
		StringBuffer formatSB = new StringBuffer(dateStr);
		for (int i = 0; i < formatSB.length(); i++) {
			if (MathUtil.isNumber(formatSB.charAt(i) + "")) {
				formatSB.setCharAt(i, 'y');
				count++;
			}
		}
		if (count > 14) {
			throw new ApplicationException("�ַ�����" + dateStr + "��������Ч�������ַ�����");
		}
		String head = "";
		String formatStr = formatSB.toString();
		if (formatStr.startsWith("yyyy")) {
			head = "yyyy";
			formatStr = formatStr.substring(4);
		}
		int index = 0;
		while (formatStr.indexOf("yy") >= 0) {
			formatStr = formatStr.replaceFirst("yy", format[index++]);
		}
		formatStr = head + formatStr;
		// �������Сʱ
		if (formatStr.indexOf("hh") >= 0) {
			int hh = Integer.parseInt(dateStr.substring(formatStr.indexOf("hh"), formatStr.indexOf("hh") + 2));
			if (hh > 12) {
				formatStr = formatStr.replaceFirst("hh", "HH");
			}
		}

		return formatStr;

	}

	/**
	 * �ж��ǲ����������ַ���
	 * 
	 * @param dateStr
	 * @return
	 */
	public final static boolean isDate(String dateStr) {
		try {
			formatNormStrToDate(dateStr);
		} catch (ApplicationException e) {
			return false;
		}
		return true;
	}

	/**
	 * �˷��������жϹ�Ԫ�����ݣ�����ݲ��ܶ�����λ��
	 * 
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public final static boolean isLeapYear(String year) throws ApplicationException {
		if (null == year || year.trim().isEmpty()) {
			throw new ApplicationException("�ж��Ƿ�Ϊ����ʱ��yearΪnull");
		}
		String nowYear = StringUtil.trimAll(year);
		if (nowYear.length() != 4) {
			throw new ApplicationException("�ж��Ƿ�Ϊ����ʱ��year����4λ����");
		}

		int intYear = StringUtil.stringToInt(year);
		return isLeapYear(intYear);

	}

	public final static boolean isLeapYear(int intYear) throws ApplicationException {
		if (intYear > 9999 || intYear < 0) {
			throw new ApplicationException("�˷��������жϹ�Ԫ�����ݣ�����ݲ��ܶ�����λ��");
		}

		// �����꣺�ܱ�400������������
		if (intYear % 100 == 0) {
			if (intYear % 400 == 0) {
				return true;
			} else {
				return false;
			}
		}
		// �������꣺�ܱ�4������Ϊ���ꡣ
		else {
			if (intYear % 4 == 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	public final static void main(String[] args) throws Exception {
		// HashMap<String, Integer> hm = getCurrentTime();
		// System.out.println(hm.toString());
		// System.out.println(formatTime(formatAnyStrToDate("20170808080808"),
		// "yyyy��MM��dd�� hh:mm:ss"));
		// System.out.println(addYearsToStr("20161020162710", -5));
		System.out.println(addMonthsToStr("2015��-04��-03�� 13ʱ:23��:30��", -1));
		// System.out.println(calculateDateStrFormat("2015��-04��-03�� 13ʱ:23��:30��"));
		System.out.println(calculateDateStrFormat("2015��"));
		System.out.println(formatNormStrToDate("20181298"));
		System.out.println(StringUtil.stringToInt("0012"));
	}

	/**
	 * ˵���� ����������֮����������
	 * 
	 * @author:�ų�
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws ApplicationException
	 */
	public final static int getMonthDifferenceBetweenTwoDate(Date beginDate, Date endDate) throws ApplicationException {
		if (beginDate == null)
			throw new ApplicationException("�������[��ʼʱ��]Ϊ��");
		if (endDate == null)
			throw new ApplicationException("�������[����ʱ��]Ϊ��");
		int year1 = StringUtil.stringToInt(formatDate(beginDate, "yyyy"));
		int year2 = StringUtil.stringToInt(formatDate(endDate, "yyyy"));
		int month1 = StringUtil.stringToInt(formatDate(beginDate, "MM"));
		int month2 = StringUtil.stringToInt(formatDate(endDate, "MM"));
		int day1 = StringUtil.stringToInt(formatDate(beginDate, "dd"));
		int day2 = StringUtil.stringToInt(formatDate(endDate, "dd"));

		double months = (year2 - year1) * 12 + month2 - month1;

		if (day1 == day2) {
		} else if (day1 == getLastDayOfMonth(beginDate) && day2 == getLastDayOfMonth(endDate)) {
		} else {
			months += (day2 - day1) / 31.00;
		}
		return (int) MathUtil.truncate(months, 0);

	}

	private static int getLastDayOfMonth(Date date) throws ApplicationException {
		int year = StringUtil.stringToInt(formatDate(date, "yyyy"));
		int month = StringUtil.stringToInt(formatDate(date, "MM"));
		// ���������
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		return -1;
	}

	/**
	 * ���ָ����ָ���·ݵ�����
	 * 
	 * @param year  ���
	 * @param month �·�
	 * @return ����
	 * @throws ApplicationException 
	 */
	public static final int getDaysInMonth(int year, int month) throws ApplicationException {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			if (isLeapYear(year)) {
				return 29;
			}
			return 28;
		default:
			return 0;
		}
	}

}