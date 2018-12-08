package com.sept.support.util;

import java.util.HashSet;
import java.util.Set;


public final class IDManager{
	
	private static final Set<String> randomHis = new HashSet<String>();

	public final static String getRandomID(int length) {
		String str = RandomFactory.newInstance().getRandomStr(length);
		while (randomHis.contains(str)) {
			str = RandomFactory.newInstance().getRandomStr(length);
		}
		randomHis.add(str);
		return str;
	}

	public final static String getRandomIDNoHan(int length) {
		String str = RandomFactory.newInstance()
			.useHan(false)
			.useCommonHan(false)
			.getRandomStr(length);
		while (randomHis.contains(str)) {
			str = RandomFactory.newInstance().getRandomStr(length);
		}
		randomHis.add(str);
		return str;
	}

}