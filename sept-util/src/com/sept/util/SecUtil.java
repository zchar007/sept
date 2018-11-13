package com.sept.util;

import java.io.UnsupportedEncodingException;

/*
 * �ַ��� DESede(3DES) ����
 */
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.sept.exception.ApplicationException;

public class SecUtil {

	private static final String Algorithm = "DESede"; // ���� �����㷨,����
	// DES,DESede,Blowfish
	private static final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79,
			0x51, (byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 };// 24�ֽڵ���Կ

	// keybyteΪ������Կ������Ϊ24�ֽ�
	// srcΪ�����ܵ����ݻ�������Դ��
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// ������Կ
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// ����
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 2009-12-3 �¿�ƽ�޸ģ�����String2String�ļ���
	public static String encryptMode(byte[] keybyte, String src) {
		try {
			// ������Կ
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// ����
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] srcBytes = src.getBytes("UTF-8");
			byte[] resultBytes = c1.doFinal(srcBytes);
			Base64 base64 = new Base64();
			return new String(base64.encode(resultBytes), "UTF-8");
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static String encryptMode(String src) {
		return SecUtil.encryptMode(keyBytes, src);
	}

	// keybyteΪ������Կ������Ϊ24�ֽ�
	// srcΪ���ܺ�Ļ�����
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// ������Կ
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// ����
			// Cipher c1 = Cipher.getInstance(Algorithm);
			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 2009-12-3 �¿�ƽ�޸ģ�����String2String�Ľ���
	public static String decryptMode(byte[] keybyte, String src) {
		try {
			// ������Կ
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// ����
			// Cipher c1 = Cipher.getInstance(Algorithm);
			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.DECRYPT_MODE, deskey);
			Base64 base64 = new Base64();
			byte[] srcBytes = base64.decode(src.getBytes("UTF-8"));
			byte[] resultBytes = c1.doFinal(srcBytes);
			return new String(resultBytes, "UTF-8");
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static String decryptMode(String src) {
		return SecUtil.decryptMode(keyBytes, src);
	}

	/**
	 * ���ڶ�src����base64����
	 * 
	 * @param src
	 * @return
	 * @throws ApplicationException
	 */
	public static String base64Encode(byte[] src) throws ApplicationException {
		Base64 base64 = new Base64();
		byte[] bin = base64.encode(src);
		return new String(bin);
	}

	public static String base64Encode(byte[] src, String charset) throws ApplicationException {
		try {
			Base64 base64 = new Base64();
			byte[] bin = base64.encode(src);
			return new String(bin, charset);
		} catch (UnsupportedEncodingException e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * ���ڶ�src����base64����
	 * 
	 * @param src
	 * @return
	 * @throws ApplicationException
	 */
	public static byte[] base64Decode(String src) throws ApplicationException {
		Base64 base64 = new Base64();
		byte[] binBytes;
		binBytes = base64.decode(src.getBytes());
		return binBytes;
	}

	public static byte[] base64Decode(String src, String charset) throws ApplicationException {
		try {
			Base64 base64 = new Base64();
			byte[] binBytes;
			binBytes = base64.decode(src.getBytes(charset));
			return binBytes;
		} catch (UnsupportedEncodingException e) {
			throw new ApplicationException(e);
		}
	}

	// ת����ʮ�������ַ���
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}
}
