package com.sept.safety;

import com.sept.exception.AppException;
import com.sept.util.SecUtil;

public class Test {
	public static void main(String[] args) throws AppException {
		String s1 = "12345中文,";
		String s2 = SecUtil.base64Encode(s1.getBytes());
		byte[] s3 = SecUtil.base64Decode(s2);
		System.out.println(new String(s3));
	}
}
