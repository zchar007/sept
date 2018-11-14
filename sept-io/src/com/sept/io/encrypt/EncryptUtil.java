package com.sept.io.encrypt;

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

public class EncryptUtil {

	public static final String ENCRYPT_ALL_SIZE = "E_A_S";// encryptFiles�����У������ļ���С��key
	public static final String ENCRYPT_DIRECTORY_NAME = "E_D_N";// encryptFiles�����У����ڼ��ܵ���Ŀ¼����
	public static final String ENCRYPT_FILE_SIZE = "E_F_S";// ��ǰ���ڼ���/���ܵ��ļ���С��key
	public static final String ENCRYPT_NOW_SIZE = "E_N_S";// ��ǰ�Ѿ�����/���ܵ����д�С��Сkey
	public static final String ENCRYPT_FILE_NAME = "E_F_N";// ��ǰ���ڼ���/���ܵ��ļ���

	/**
	 * ��byte���ո����ַ��������ܣ�ѭ�������ַ��� ���ս��Ϊͬ1����0��������Կ�ͼ�����Կ��ͬ<br>
	 * �����ַ����ļ��ܣ���ȡ��byte��Ҫ��iso-8859-1��ʽ�����ַ���������iso-8859-1��ʽ��ʽ��ȡbyte���н��� <br>
	 * ����<br>
	 * String str = "hello���";<br>
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
	 * �ڲ����õ���Index������Ϊȡ��һ�������ַ��ļ���/���ܷ���
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-9
	 * @since V1.0
	 */
	public static byte[] encryptByte(byte[] byteData, Index key) {
		for (int i = 0; i < byteData.length; i++) {

			byteData[i] = (byte) (byteData[i] ^ (key.getNextKey() | 0xFF));
		}
		return byteData;
	}

	/**
	 * �����ļ��ļ��ܽ���
	 * 
	 * @param fromFile file��Դ���������ļ����������ļ���
	 * @param toUrl    �ļ�������·�����η���ֻ�������յ�·��
	 * @param key      ��Կ
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-9
	 * @since V1.0
	 */
	public static void encryptFile(File fromFile, String toUrl, String key, HashMap<String, Object> pdoConfig)
			throws AppException {
		encryptFile(fromFile, toUrl, key, null, pdoConfig);
	}

	/**
	 * �����ļ��ļ��ܽ���
	 * 
	 * @param fromFile file��Դ���������ļ����������ļ���
	 * @param toUrl    �ļ�������·�����η���ֻ�������յ�·��
	 * @param key      ��Կ
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-9
	 * @since V1.0
	 */
	public static void encryptFile(File fromFile, String toUrl, String key) throws AppException {
		encryptFile(fromFile, toUrl, key, null, null);
	}

	/**
	 * �����ļ��ļ��ܽ���
	 * 
	 * @param fromFile file��Դ���������ļ����������ļ���
	 * @param toUrl    �ļ�������·�����η���ֻ�������յ�·��
	 * @param key      ��Կ
	 * @param mp       :����/���ܹ��������ɵĸ������ݴ�ŵ�SharedInformationPool
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-9
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
				throw new AppException("ֻ�ܼ���/�����ļ�����");
			}
			File fto = new File(toUrl);
			// ��δ�������޷��жϣ����ļ���ֱ�Ӵ������ļ�����
			// if (!fto.isDirectory()) {
			// throw new
			// AppException("Ŀ��λ��ֻ�����ļ��У���"+fto+"--"+fto.isDirectory());
			// }
			fto.mkdirs();
			fto = new File(toUrl + File.separator + fromFile.getName());
			String type = FileUtil.getFileType(fromFile);
			String urlTemp = fto.getAbsolutePath().substring(0,
					(int) (fto.getAbsolutePath().length() - type.length() - 1));// ȥ����
			type = pdoConfig.containsKey(type.toUpperCase()) ? pdoConfig.get(type.toUpperCase()).toString() : type;
			fto = new File(urlTemp + "." + type);
			int index = 0;
			while (fto.exists()) {
				fto = new File(urlTemp + "(" + (++index) + ")." + type);
			}
			// ����ѭ���ַ���ָ��
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
	 * ��������/�����ļ�
	 * 
	 * @param fromFile file��Դ���ļ�/�ļ��о���
	 * @param toUrl    �ļ�������·�����η���ֻ�������յ�·��
	 * @param key      ��Կ
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-9
	 * @since V1.0
	 */
	public static ExecutorService encryptFiles(File fromFile, String toUrl, String key,
			HashMap<String, Object> doConfig) throws AppException {
		return encryptFiles(fromFile, toUrl, key, null, doConfig);
	}

	/**
	 * ��������/�����ļ�
	 * 
	 * @param fromFile file��Դ���ļ�/�ļ��о���
	 * @param toUrl    �ļ�������·�����η���ֻ�������յ�·��
	 * @param key      ��Կ
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-9
	 * @since V1.0
	 */
	public static ExecutorService encryptFiles(File fromFile, String toUrl, String key) throws AppException {
		return encryptFiles(fromFile, toUrl, key, null, null);
	}

	/**
	 * ��������/�����ļ�
	 * 
	 * @param fromFile file��Դ���ļ�/�ļ��о���
	 * @param toUrl    �ļ�������·�����η���ֻ�������յ�·��
	 * @param key      ��Կ
	 * @param mp       :����/���ܹ��������ɵĸ������ݴ�ŵ�SharedInformationPool
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-9
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
				throw new AppException("Ŀ��·������Ϊ�ļ���");
			}

			String realToUrl = tf.getAbsolutePath();
			for (int i = 0; i < alFiles.size(); i++) {
				File file = alFiles.get(i);
				// ���������/���ܵ�λ�ã������ļ���
				String toUrlCell = realToUrl + File.separator
						+ file.getAbsolutePath().substring(needRemovePath.length());
				toUrlCell = toUrlCell.substring(0, toUrlCell.lastIndexOf(File.separator));
				vdsFiles.addRow();
				vdsFiles.put(vdsFiles.rowCount() - 1, "file", file);
				vdsFiles.put(vdsFiles.rowCount() - 1, "tourl", toUrlCell);
			}
			// �����ǲ��10���̣߳�����10����һ������̯
			int[] everys = new int[10];
			// С�ڵ���10����10���̣߳�ÿ���̴߳���һ��
			int every = vdsFiles.size() / 10;
			int yushu = vdsFiles.size() % 10;

			for (int i = 0; i < everys.length; i++) {
				everys[i] = every;
				if (i < yushu) {
					everys[i] = everys[i] + 1;
				}
			}
			int index = 0;
			// �����̳߳أ���󷵻ش��̳߳أ������ⲿ����
			ExecutorService uploadThreadPool = Executors.newFixedThreadPool(1000);
			for (int i = 0; i < everys.length; i++) {
				if (everys[i] > 0) {
					DataStore ds = new DataStore();
					for (int j = index; j < index + everys[i]; j++) {
						ds.add(vdsFiles.get(i));
					}
					index += everys[i];
					// ����/�����߳�����
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
