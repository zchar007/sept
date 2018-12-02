package com.sept.util.stack;

public class test {
	public static void main(String[] args) {
		tests();
	}
	private static void tests() {
		StackTraceElement[] stackTrace = StackTraceUtil.getStackTraceInfo();
		for (StackTraceElement s : stackTrace) {
			System.out.println(StackTraceUtil.getPrintInfo(s));
		}
	}

}
