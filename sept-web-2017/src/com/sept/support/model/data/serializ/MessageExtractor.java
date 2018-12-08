package com.sept.support.model.data.serializ;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

public class MessageExtractor 
{
	private Cipher cipher;//密码
	private IvParameterSpec dps;
	private SecretKeySpec skeySpec;

	public MessageExtractor(String keyValue) throws NoSuchAlgorithmException, NoSuchPaddingException  {
		byte[] passkey = hex2Byte(keyValue);
		byte[] key = getAESKey(passkey);
		byte[] iv = getAESIV(passkey);
		dps = new IvParameterSpec(iv);
		skeySpec = new SecretKeySpec(key, "AES");
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	}
	
	private byte[] hex2Byte(String src) {
		if (src.length() < 1)
			return null;
		
		byte[] encrypted = new byte[src.length() / 2];
		for (int i = 0; i < src.length() / 2; i++) {
			int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);

			encrypted[i] = (byte) (high * 16 + low);
		}
		return encrypted;
	}
	
	private byte[] getAESIV(byte[] keyRaw) { 
		byte[] iv = new byte[16];
		System.arraycopy(keyRaw, 8, iv, 0, 16);
		return iv;
	}

	private byte[] getAESKey(byte[] keyRaw) {
		byte[] key = new byte[16];
		System.arraycopy(keyRaw, 0, key, 0, 8);
		System.arraycopy(keyRaw, 24, key, 8, 8);
		return key;
	}	
	
	private Cipher getCipher(int mode)  {
		try {
			cipher.init(mode, skeySpec, dps);
		} catch (Exception e) {
			return null;
		}
		return cipher;
	}
	
	
	public String getMessage(Object object) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Base64OutputStream b64aos = new Base64OutputStream(baos);
		CipherOutputStream cos = new  CipherOutputStream(b64aos,this.getCipher(Cipher.ENCRYPT_MODE));
		BufferedOutputStream bos = new  BufferedOutputStream(cos);

		ObjectOutputStream oos;

		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.flush();
			oos.close();
			return baos.toString();

		} catch (IOException e) {
			return null;
		}
	}
	
	public Object getMessageObject(String message) {
		ByteArrayInputStream bais= new ByteArrayInputStream(message.getBytes());
		BufferedInputStream bis = new BufferedInputStream(bais);
		Base64InputStream b64is = new Base64InputStream(bis);
		
		CipherInputStream cis = new CipherInputStream(b64is, getCipher(Cipher.DECRYPT_MODE));
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(cis);
			Object obj = ois.readObject();
			ois.close();
			return obj;
		} catch (Exception e) {
			return null;
		}
	}
	public static void main(String[] args) throws Exception {
		//密钥64位长度，前16位和后16位为key，中间32为iv,密钥为双方约定
		final String AES_HEX = "A12C1BA14AB0AC4C081C26C4974C03F8C41D40CBA3418BCA6C0203404CC470CF";

		Map<String, String> map = new HashMap<String,String>();
		map.put("name", "张三");
		map.put("address", "张三的地址");
		map.put("tel", "1234567890");
		
		Map<String, String> map2 = new HashMap<String,String>();
		map2.put("name", "张三");
		map2.put("address", "张三的地址");
		map2.put("tel", "1234567890");
		MessageExtractor me2 = new MessageExtractor(AES_HEX);
		String data2 = me2.getMessage(map2);
		map.put("values", data2);
		
		
		
		MessageExtractor me = new MessageExtractor(AES_HEX);
		String data = me.getMessage(map);
		System.out.println(data);
		
		Map<String, String> dataObject = (Map<String, String>) me.getMessageObject(data);
		
		String obj2 = dataObject.get("values");
				
		Map<String, String> dataObject2 = (Map<String, String>) me.getMessageObject(obj2);

		System.out.println(dataObject);
		System.out.println(dataObject2);
		
	}
}
