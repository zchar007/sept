package com.sept.util;

import java.math.BigDecimal;

import com.sept.exception.ApplicationException;

public final class MathUtil{
	public final static double abs(double num) {
		if (num < 0.0D) {
			return -1.0D * num;
		}
		return num;
	}

	/**
	 * 直接截取对应小数位double
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-12
	 * @since V1.0
	 */
	public final static double truncate(double d, int i) {
		double tmp = Math.pow(10.0D, i);
		return Math.floor(d * tmp) / tmp;
	}

	/**
	 * 根据给定小数位，实行四舍五入
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-12
	 * @since V1.0
	 */
	public final static double round(double v, int scale) throws ApplicationException {
		if (scale < 0) {
			throw new ApplicationException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		double i = b.divide(one, 10, 4).doubleValue();
		b = new BigDecimal(Double.toString(i));
		return b.divide(one, scale, 4).doubleValue();
	}

	/**
	 * 判断字符串是不是数字类型
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-12
	 * @since V1.0
	 */
	public final static boolean isNumber(String numberString) {
		try {
			Double.parseDouble(numberString);
			return true;
		} catch (NumberFormatException ex) {
		}
		return false;
	}

	public final static void main(String[] args) throws ApplicationException {
		System.out.println(round(12.6, 0));
	}
}
