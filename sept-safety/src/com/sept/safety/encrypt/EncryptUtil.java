package com.sept.safety.encrypt;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.sept.exception.AppException;
import com.sept.io.local.FileByteReader;
import com.sept.io.local.FileUtil;

public class EncryptUtil {
	public static void main(String[] args) throws AppException {
		File f = encryptFile("8848ttt", "D:\\笔记.docx", "D:\\");
		File f2 = encryptFile("8848ttt", f.getAbsolutePath(), "D:\\");

	}

	/**
	 * 加密一些小文件
	 * 
	 * @param key
	 * @param fromurl
	 * @param tourl
	 * @return
	 * @throws AppException
	 */
	public static final File encryptFile(String key, String fromurl, String tourl) throws AppException {
		FileByteReader fbr = new FileByteReader(fromurl, 1024 * 1024);
		File toUrl = new File(tourl);
		File fromUrl = new File(fromurl);
		File toFile = null;
		try {
			if (toUrl.exists()) {
				if (!toUrl.isDirectory()) {
					toFile = toUrl;
				} else {
					toFile = new File(toUrl.getAbsolutePath() + File.separator + fromUrl.getName());
					String fileName = toFile.getName().substring(0, toFile.getName().lastIndexOf("."));
					String fileType = toFile.getName().substring(toFile.getName().lastIndexOf("."),
							toFile.getName().length());
					int index = 0;
					while (toFile.exists()) {
						toFile = new File(tourl + File.separator + fileName + "(" + (++index) + ")" + fileType);
					}
					toFile.createNewFile();
				}
			} else {
				toUrl.mkdir();
				toFile = new File(toUrl.getAbsolutePath() + File.separator + fromUrl.getName());
				String fileName = toFile.getName().substring(0, toFile.getName().lastIndexOf("."));
				String fileType = toFile.getName().substring(toFile.getName().lastIndexOf("."),
						toFile.getName().length());

				int index = 0;
				while (toFile.exists()) {
					toFile = new File(tourl + File.separator + fileName + "(" + (++index) + ")" + fileType);
				}
				toFile.createNewFile();
			}
			if (!toFile.exists()) {

			}

			FileByteReader fbrTo = new FileByteReader(toFile.getAbsolutePath());
			Index index = new Index(key);
			byte[] datas;
			while ((datas = fbr.read()) != null) {
				datas = encryptByte(datas, index);
				fbrTo.write(datas, true);
			}
			fbrTo.close();
			fbr.close();
		} catch (IOException e) {
			throw new AppException(e);
		}
		return toFile;
	}

	/**
	 * 把byte按照给定字符串来加密，循环给定字符串 最终结果为同1，异0，解密秘钥和加密秘钥相同<br>
	 * 对于字符串的加密，获取到byte后要以iso-8859-1格式创建字符串后再以iso-8859-1格式形式获取byte进行解密 <br>
	 * 例如<br>
	 * String str = "hello你好";<br>
	 * str = new String(encryptByte(str.getBytes(),"12345678"), "iso-8859-1");<br>
	 * System.out.println(str); <br>
	 * str = new String(encryptByte(str.getBytes("iso-8859-1"), "12345678"));<br>
	 * System.out.println(str);<br>
	 * 
	 * @param byteData
	 * @param size
	 * @return
	 */
	public static final byte[] encryptByte(byte[] byteData, String key) {
		int k = 0;
		for (int i = 0; i < byteData.length; i++) {
			if (k == key.length()) {
				k = 0;
			}
			byteData[i] = (byte) (byteData[i] ^ (key.charAt(k++) | 0xFF));
		}
		return byteData;
	}

	/**
	 * 内部调用的以Index容器作为取下一个加密字符的加密/解密方法
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public static final byte[] encryptByte(byte[] byteData, Index key) {
		for (int i = 0; i < byteData.length; i++) {
			byteData[i] = (byte) (byteData[i] ^ (key.getNextKey() | 0xFF));
		}
		return byteData;
	}

	/**
	 * 处理复制中的文件夹复制问题
	 * 
	 * @param fromFileHead [Directory]
	 * @param fromUrl      [File]
	 * @param toUrl        [Directory]
	 * @param config
	 * @return
	 * @throws AppException
	 */
	public static final String dealPath4ToUrl(final String fromFileHead, final String fromUrl, final String toUrl,
			final HashMap<String, String> config) throws AppException {
		return dealPath4ToUrl(fromFileHead, fromUrl, toUrl, config, true);
	}

	/**
	 * 处理复制中的文件夹复制问题
	 * 
	 * @param fromFileHead [Directory]
	 * @param fromUrl      [File]
	 * @param toUrl        [Directory]
	 * @param config
	 * @param autoMkdirs
	 * @return
	 * @throws AppException
	 */
	public static final String dealPath4ToUrl(final String fromFileHead, final String fromUrl, final String toUrl,
			final HashMap<String, String> config, final boolean autoMkdirs) throws AppException {
		if (!fromUrl.startsWith(fromFileHead)) {
			throw new AppException("文件来源头部与文件来源不匹配！");
		}
		File fromFileHeadFile = new File(fromFileHead);
//		if (!fromFileHeadFile.isDirectory()) {
//			throw new AppException("文件来源头必须是文件[Directory]！");
//		}
		File fromFile = new File(fromUrl);
		if (!fromFile.isFile()) {
			throw new AppException("文件来源必须是文件[File]！");
		}
		File toFile = new File(toUrl);
		if (!toFile.isDirectory()) {
			throw new AppException("文件来源必须是文件夹[Directory]！");
		}

		String fromFileHead_t = fromFileHeadFile.getAbsolutePath();
		String fromUrl_t = fromFile.getAbsolutePath();
		String toUrl_t = toFile.getAbsolutePath();
		String returnUrl = toUrl_t;

		// 把文件夹拼接到toUrl
		String head_new = fromFileHead_t.substring(0, fromFileHead_t.length() - fromFileHeadFile.getName().length());
		String directory = fromUrl.substring(head_new.length(), fromUrl_t.length() - fromFile.getName().length());

		returnUrl += File.separator + directory;
		if (autoMkdirs) {
			File file = new File(returnUrl);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		String type = FileUtil.getFileType(fromFile);

		returnUrl += File.separator + fromFile.getName().substring(0, fromFile.getName().length() - type.length())
				+ (config.containsKey(type) ? config.get(type) : type);

		return returnUrl;
	}
}
