package com.sept.drop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sept.datastructure.DataStore;
import com.sept.datastructure.common.SharedInformationPool;
import com.sept.exception.AppException;
import com.sept.io.local.FileUtil;

class EncryptUtil {

	public static final String ENCRYPT_ALL_SIZE = "E_A_S";// encryptFiles方法中，所有文件大小的key
	public static final String ENCRYPT_DIRECTORY_NAME = "E_D_N";// encryptFiles方法中，正在加密的总目录名字
	public static final String ENCRYPT_FILE_SIZE = "E_F_S";// 当前正在加密/解密的文件大小的key
	public static final String ENCRYPT_NOW_SIZE = "E_N_S";// 当前已经加密/解密的所有大小大小key
	public static final String ENCRYPT_FILE_NAME = "E_F_N";// 当前正在加密/解密的文件名

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
	public static byte[] encryptByte(byte[] byteData, String key) {
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
	public static byte[] encryptByte(byte[] byteData, Index key) {
		for (int i = 0; i < byteData.length; i++) {

			byteData[i] = (byte) (byteData[i] ^ (key.getNextKey() | 0xFF));
		}
		return byteData;
	}

	/**
	 * 单个文件的加密解密
	 * 
	 * @param fromFile file来源，必须是文件，不可是文件夹
	 * @param toUrl    文件的最终路径，次方法只接收最终的路径
	 * @param key      秘钥
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public static void encryptFile(File fromFile, String toUrl, String key, HashMap<String, Object> pdoConfig)
			throws AppException {
		encryptFile(fromFile, toUrl, key, null, pdoConfig);
	}

	/**
	 * 单个文件的加密解密
	 * 
	 * @param fromFile file来源，必须是文件，不可是文件夹
	 * @param toUrl    文件的最终路径，次方法只接收最终的路径
	 * @param key      秘钥
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public static void encryptFile(File fromFile, String toUrl, String key) throws AppException {
		encryptFile(fromFile, toUrl, key, null, null);
	}

	/**
	 * 单个文件的加密解密
	 * 
	 * @param fromFile file来源，必须是文件，不可是文件夹
	 * @param toUrl    文件的最终路径，次方法只接收最终的路径
	 * @param key      秘钥
	 * @param mp       :加密/解密过程中生成的各种数据存放的SharedInformationPool
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public static void encryptFile(File fromFile, String toUrl, String key, SharedInformationPool mp,
			HashMap<String, Object> pdoConfig) throws AppException {
		InputStream in = null;
		OutputStream os = null;
		try {
			if (null == pdoConfig) {
				pdoConfig = new HashMap<String, Object>();
			}
			if (!fromFile.isFile()) {
				throw new AppException("只能加密/解密文件！！");
			}
			File fto = new File(toUrl);
			// 还未创建，无法判断，是文件就直接创建成文件夹了
			// if (!fto.isDirectory()) {
			// throw new
			// AppException("目标位置只能是文件夹！！"+fto+"--"+fto.isDirectory());
			// }
			fto.mkdirs();
			fto = new File(toUrl + File.separator + fromFile.getName());
			String type = FileUtil.getFileType(fromFile);
			String urlTemp = fto.getAbsolutePath().substring(0,
					(int) (fto.getAbsolutePath().length() - type.length() - 1));// 去掉点
			type = pdoConfig.containsKey(type.toUpperCase()) ? pdoConfig.get(type.toUpperCase()).toString() : type;
			fto = new File(urlTemp + "." + type);
			int index = 0;
			while (fto.exists()) {
				fto = new File(urlTemp + "(" + (++index) + ")." + type);
			}
			// 创建循环字符串指针
			Index KEY = new Index(key);

			fto.createNewFile();
			if (null != mp) {
				mp.asynchPut(ENCRYPT_FILE_SIZE, fromFile.length());
				mp.asynchPut(ENCRYPT_FILE_NAME, fromFile.getAbsolutePath());
			}
			in = new FileInputStream(fromFile);
			os = new FileOutputStream(fto);
			byte[] datas = new byte[1024];
			int size;
			while ((size = in.read(datas)) != -1) {
				datas = encryptByte(datas, KEY);
				if (null != mp) {
					mp.asynchPut4Add(ENCRYPT_NOW_SIZE, Long.parseLong(size + ""));
				}
				os.write(datas, 0, size);
				os.flush();
			}
			os.flush();

		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		} finally {
			try {
				os.flush();
				os.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 批量加密/解密文件
	 * 
	 * @param fromFile file来源，文件/文件夹均可
	 * @param toUrl    文件的最终路径，次方法只接收最终的路径
	 * @param key      秘钥
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public static ExecutorService encryptFiles(File fromFile, String toUrl, String key,
			HashMap<String, Object> doConfig) throws AppException {
		return encryptFiles(fromFile, toUrl, key, null, doConfig);
	}

	/**
	 * 批量加密/解密文件
	 * 
	 * @param fromFile file来源，文件/文件夹均可
	 * @param toUrl    文件的最终路径，次方法只接收最终的路径
	 * @param key      秘钥
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public static ExecutorService encryptFiles(File fromFile, String toUrl, String key) throws AppException {
		return encryptFiles(fromFile, toUrl, key, null, null);
	}

	/**
	 * 批量加密/解密文件
	 * 
	 * @param fromFile file来源，文件/文件夹均可
	 * @param toUrl    文件的最终路径，次方法只接收最终的路径
	 * @param key      秘钥
	 * @param mp       :加密/解密过程中生成的各种数据存放的SharedInformationPool
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public static ExecutorService encryptFiles(File fromFile, String toUrl, String key, SharedInformationPool mp,
			HashMap<String, Object> pdoConfig) throws AppException {
		try {

			String needRemovePath = fromFile.getAbsolutePath().substring(0,
					fromFile.getAbsolutePath().lastIndexOf(File.separator));
			System.out.println(needRemovePath);
			HashMap<String, Object> pdo = FileUtil.getFilesFormPath(fromFile.getAbsolutePath(), "");
			long length = Long.parseLong(pdo.get("length").toString());
			@SuppressWarnings("unchecked")
			ArrayList<File> alFiles = (ArrayList<File>) pdo.get("files");
			if (null != mp) {
				mp.asynchPut(ENCRYPT_ALL_SIZE, length);
				mp.asynchPut(ENCRYPT_DIRECTORY_NAME, fromFile.getAbsoluteFile());
			}
			DataStore vdsFiles = new DataStore();
			File tf = new File(toUrl);
			if (!tf.exists()) {
				tf.mkdirs();
			}
			if (!tf.isDirectory()) {
				throw new AppException("目标路径必须为文件夹");
			}

			String realToUrl = tf.getAbsolutePath();
			for (int i = 0; i < alFiles.size(); i++) {
				File file = alFiles.get(i);
				// 整理出加密/解密的位置，不带文件名
				String toUrlCell = realToUrl + File.separator
						+ file.getAbsolutePath().substring(needRemovePath.length());
				toUrlCell = toUrlCell.substring(0, toUrlCell.lastIndexOf(File.separator));
				vdsFiles.addRow();
				vdsFiles.put(vdsFiles.rowCount() - 1, "file", file);
				vdsFiles.put(vdsFiles.rowCount() - 1, "tourl", toUrlCell);
			}
			// 以下是拆成10个线程，不足10个则一个个均摊
			int[] everys = new int[10];
			// 小于等于10，开10个线程，每个线程处理一个
			int every = vdsFiles.size() / 10;
			int yushu = vdsFiles.size() % 10;

			for (int i = 0; i < everys.length; i++) {
				everys[i] = every;
				if (i < yushu) {
					everys[i] = everys[i] + 1;
				}
			}
			int index = 0;
			// 加入线程池，最后返回此线程池，方便外部控制
			ExecutorService uploadThreadPool = Executors.newFixedThreadPool(1000);
			for (int i = 0; i < everys.length; i++) {
				if (everys[i] > 0) {
					DataStore ds = new DataStore();
					for (int j = index; j < index + everys[i]; j++) {
						ds.add(vdsFiles.get(i));
					}
					index += everys[i];
					// 加密/解密线程启动
					EncryptTread encrypt = new EncryptTread(ds, key, mp, pdoConfig);
					uploadThreadPool.execute(encrypt);
				}
			}
			return uploadThreadPool;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
