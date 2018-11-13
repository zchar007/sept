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
	 * 获取当前时间的各个参数
	 */
	public final static HashMap<String, Integer> getCurrentHMTime() {
		return getHMTime(new Date());
	}

	/*
	 * 获取时间的各个参数
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
	 * 以指定格式获取当前时间
	 * 
	 * @param formatStr
	 * @return
	 */
	public final static String getCurrentDate(String formatStr) {
		Calendar now = Calendar.getInstance();
		return formatDate(now.getTime(), formatStr);

	}

	/**
	 * 以指定格式获取当前时间
	 * 
	 * @param formatStr
	 * @return
	 */
	public final static Date getCurrentDate() {
		Calendar now = Calendar.getInstance();
		return now.getTime();
	}

	/**
	 * 格式化一个时间
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
	 * 获取一个数据库时间
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
	 * @since V1.0
	 */
	public final static java.sql.Date getCurrentDBDate() {
		return new java.sql.Date(new Date().getTime());
	}

	/**
	 * sqldate转javadate
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
	 * @since V1.0
	 */
	public final static Date formatSqlDateToJavaDate(java.sql.Date date) {
		return new Date(date.getTime());
	}

	/**
	 * javadate转sqldate
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
	 * @since V1.0
	 */
	public final static java.sql.Date formatSqlDateToJavaDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 以字符串设定当前时间 2016 10 14 09 08 07
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
			throw new ApplicationException("【" + timeStr + "】日期格式错误！！");
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
			throw new ApplicationException("格式化字符串时,月份出错！【" + month + "】");
		}
		if (day < 1) {
			throw new ApplicationException("格式化字符串时,日出错！【" + day + "】");
		}
		// 如果是闰年
		if (month == 2) {
			if (isLeapYear(year)) {
				if (day > 29) {
					throw new ApplicationException("格式化字符串时,日出错！【" + day + "】,年份【" + year + "】是闰年，二月不得大于29天");
				}
			} else {
				if (day > 28) {
					throw new ApplicationException("格式化字符串时,日出错！【" + day + "】,年份【" + year + "】是平年，二月不得大于28天");
				}
			}
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day > 30) {
				throw new ApplicationException("当前月份为【" + month + "】，此月份不得大于30天");
			}
		}
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			if (day > 31) {
				throw new ApplicationException("当前月份为【" + month + "】，此月份不得大于31天");
			}
		}
		// 小时的范围为0时到24时 [0,24)
		if (hour >= 24 || hour < 0) {
			throw new ApplicationException("小时的范围为 [0,24)，当前录入为【" + hour + "】");
		}
		// 小时的范围为0时到24时 [0,24)
		if (minutes >= 60 || minutes < 0) {
			throw new ApplicationException("分钟的范围为 [0,60)，当前录入为【" + minutes + "】");
		}
		// 小时的范围为0时到24时 [0,24)
		if (seconds >= 60 || seconds < 0) {
			throw new ApplicationException("秒的范围为 [0,60)，当前录入为【" + seconds + "】");
		}
		cal.set(year, month - 1, day, hour, minutes, seconds);
		return cal.getTime();
	}

	/**
	 * 格式化任意格式的String 为date
	 * 
	 * @author 张超
	 * @throws Exception
	 * @date 创建时间 2017-6-3
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
	 * 给一个日期字符串增加一年
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 给一个日期增加一年
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 给一个日期字符串增加一月
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 给一个日期增加一月
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 给一个日期字符串增加一天
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 给一个日期增加一天
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 给一个日期字符串增加一小时
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 给一个日期增加一小时
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 给一个日期字符串增加一分钟
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 给一个日期增加一分钟
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 给一个日期字符串增加一秒
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 给一个日期增加一秒
	 * 
	 * @author 张超
	 * @date 创建时间 2017-5-30
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
	 * 1.从前往后yyyy ---->ss的随意从后面减少的数据 2.可以不写yyyy
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-6
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
			throw new ApplicationException("字符串【" + dateStr + "】不是有效的日期字符串！");
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
		// 如果含有小时
		if (formatStr.indexOf("hh") >= 0) {
			int hh = Integer.parseInt(dateStr.substring(formatStr.indexOf("hh"), formatStr.indexOf("hh") + 2));
			if (hh > 12) {
				formatStr = formatStr.replaceFirst("hh", "HH");
			}
		}

		return formatStr;

	}

	/**
	 * 判断是不是日期型字符串
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
	 * 此方法仅能判断公元后的年份，且年份不能多于四位数
	 * 
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public final static boolean isLeapYear(String year) throws ApplicationException {
		if (null == year || year.trim().isEmpty()) {
			throw new ApplicationException("判断是否为闰年时，year为null");
		}
		String nowYear = StringUtil.trimAll(year);
		if (nowYear.length() != 4) {
			throw new ApplicationException("判断是否为闰年时，year不是4位的年");
		}

		int intYear = StringUtil.stringToInt(year);
		return isLeapYear(intYear);

	}

	public final static boolean isLeapYear(int intYear) throws ApplicationException {
		if (intYear > 9999 || intYear < 0) {
			throw new ApplicationException("此方法仅能判断公元后的年份，且年份不能多于四位数");
		}

		// 整百年：能被400整除的是闰年
		if (intYear % 100 == 0) {
			if (intYear % 400 == 0) {
				return true;
			} else {
				return false;
			}
		}
		// 非整百年：能被4整除的为闰年。
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
		// "yyyy年MM月dd日 hh:mm:ss"));
		// System.out.println(addYearsToStr("20161020162710", -5));
		System.out.println(addMonthsToStr("2015年-04月-03日 13时:23分:30秒", -1));
		// System.out.println(calculateDateStrFormat("2015年-04月-03日 13时:23分:30秒"));
		System.out.println(calculateDateStrFormat("2015年"));
		System.out.println(formatNormStrToDate("20181298"));
		System.out.println(StringUtil.stringToInt("0012"));
	}

	/**
	 * 说明： 求两个日期之间相差的月数
	 * 
	 * @author:张超
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws ApplicationException
	 */
	public final static int getMonthDifferenceBetweenTwoDate(Date beginDate, Date endDate) throws ApplicationException {
		if (beginDate == null)
			throw new ApplicationException("传入参数[开始时间]为空");
		if (endDate == null)
			throw new ApplicationException("传入参数[结束时间]为空");
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
		// 如果是闰年
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
	 * 获得指定年指定月份的天数
	 * 
	 * @param year  年份
	 * @param month 月份
	 * @return 天数
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