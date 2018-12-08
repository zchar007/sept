package com.sept.support.util;

import java.util.HashSet;
import java.util.Set;


public class RandomManager {
	private static Set<String> randomHis = new HashSet<String>();

	public static String getRandomStr(int length) {
		String str = RandomFactory.newInstance().getRandomStr(length);
		while (randomHis.contains(str)) {
			str = RandomFactory.newInstance().getRandomStr(length);
		}
		randomHis.add(str);
		return str;
	}
	public static String getRandomStrNoHan(int length) {
		String str = RandomFactory.newInstance().useHan(false).useCommonHan(false).getRandomStr(length);
		while (randomHis.contains(str)) {
			str = RandomFactory.newInstance().getRandomStr(length);
		}
		randomHis.add(str);
		return str;
	}

}
