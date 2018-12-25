package com.sept.util.code;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import com.sept.exception.AppException;

public class Base64Util {
	/**
	 * 用于对src进行base64编码
	 * 
	 * @param src
	 * @return
	 * @throws AppException
	 */
	public static String encode(byte[] src) throws AppException {
		Base64 base64 = new Base64();
		byte[] bin = base64.encode(src);
		return new String(bin);
	}

	/**
	 * 以指定编码格式用Base64加密字符串
	 * 
	 * @param src
	 * @param charset
	 * @return
	 * @throws AppException
	 */
	/**
	 */
	public static String encode(byte[] src, String charset) throws AppException {
		try {
			Base64 base64 = new Base64();
			byte[] bin = base64.encode(src);
			return new String(bin, charset);
		} catch (UnsupportedEncodingException e) {
			throw new AppException(e);
		}
	}

	/**
	 * 用于对src进行base64解码
	 * 
	 * @param src
	 * @return
	 * @throws AppException
	 */
	public static byte[] decode(String src) throws AppException {
		Base64 base64 = new Base64();
		byte[] binBytes;
		binBytes = base64.decode(src.getBytes());
		return binBytes;
	}

	/**
	 * 以指定编码格式用Base64解码字符串
	 * 
	 * @param src
	 * @param charset
	 * @return
	 * @throws AppException
	 */
	public static byte[] decode(String src, String charset) throws AppException {
		try {
			Base64 base64 = new Base64();
			byte[] binBytes;
			binBytes = base64.decode(src.getBytes(charset));
			return binBytes;
		} catch (UnsupportedEncodingException e) {
			throw new AppException(e);
		}
	}
}
