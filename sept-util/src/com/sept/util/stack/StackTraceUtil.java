package com.sept.util.stack;

import java.util.ArrayList;

public class StackTraceUtil {

	/**
	 * 获取路径信息
	 * 
	 * @return
	 */
	public static final StackTraceElement[] getStackTraceInfo() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		ArrayList<StackTraceElement> stackTraceArray = new ArrayList<>();
		for (int i = 0; i < stackTrace.length; i++) {
			if (!stackTrace[i].getClassName().equals(StackTraceUtil.class.getName())
					&& !stackTrace[i].getClassName().equals(Thread.class.getName())) {
				stackTraceArray.add(stackTrace[i]);
			}
		}
		return stackTraceArray.toArray(new StackTraceElement[stackTraceArray.size()]);
	}

	/**
	 * 获取路径第几层调用信息 正数为顺序查找，反之为倒序查找,0为初始调用者,-1为当前调用者
	 * 
	 * @return
	 */
	public static final StackTraceElement getStackTrace(int index) {
		// 获取到的是倒叙
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		if (index >= 0) {
			int k = 0;
			for (int i = stackTrace.length - 1; i > 0; i--) {
				if (!stackTrace[i].getClassName().equals(StackTraceUtil.class.getName())
						&& !stackTrace[i].getClassName().equals(Thread.class.getName())) {
					if (k == index) {
						return stackTrace[i];
					}
					k++;
				}
			}
		} else {
			int k = -1;
			for (int i = 0; i < stackTrace.length; i++) {
				if (!stackTrace[i].getClassName().equals(StackTraceUtil.class.getName())
						&& !stackTrace[i].getClassName().equals(Thread.class.getName())) {
					if (k == index) {
						return stackTrace[i];
					}
					k--;
				}
			}
		}

		return null;
	}

	/**
	 * 返回com.sept.debug.log4j.Demo.main(Demo.java:20)这种形式的
	 * 
	 * @param stackTrace
	 * @return
	 */
	public static final String getPrintInfo(StackTraceElement stackTrace) {
		StringBuilder traceBr = new StringBuilder();
		traceBr.append(" ");
		traceBr.append(stackTrace.getClassName());
		traceBr.append(".");
		traceBr.append(stackTrace.getMethodName());
		traceBr.append("(");
		traceBr.append(stackTrace.getFileName());
		traceBr.append(":");
		traceBr.append(stackTrace.getLineNumber());
		traceBr.append(")");
		return traceBr.toString();
	}
}
