package com.sept.safety.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;

class EncryptFileTread implements Runnable {
	private String key;
	private String fromHead;
	private File fromFile;
	private File toFile;
	private HashMap<String, String> pdoConfig;
	private EncryptListener listener;

	public EncryptFileTread(String key, String fromHead, File fromFile, File toFile, HashMap<String, String> pdoConfig,
			EncryptListener listener) {
		super();
		this.key = key;
		this.fromHead = fromHead;
		this.fromFile = fromFile;
		this.toFile = toFile;
		this.pdoConfig = pdoConfig;
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			this.encryptFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void encryptFile() throws AppException {
		InputStream in = null;
		OutputStream os = null;
		try {
			if (null == pdoConfig) {
				pdoConfig = new HashMap<String, String>();
			}
			if (!fromFile.isFile()) {
				throw new AppException("只能加密/解密文件！！");
			}
			// 还未创建，无法判断，是文件就直接创建成文件夹了
			String toUrl_t = EncryptUtil.dealPath4ToUrl(fromHead, fromFile.getAbsolutePath(), toFile.getAbsolutePath(),
					this.pdoConfig);

			toFile = new File(toUrl_t);
			String urlTemp = toFile.getAbsolutePath().substring(0,
					toFile.getAbsolutePath().length() - toFile.getName().length());
			String fileName = toFile.getName().substring(0, toFile.getName().lastIndexOf("."));
			String fileType = toFile.getName().substring(toFile.getName().lastIndexOf("."), toFile.getName().length());

			int index = 0;
			while (toFile.exists()) {
				toFile = new File(urlTemp + fileName + "(" + (++index) + ")" + fileType);
			}
			// 创建循环字符串指针
			Index KEY = new Index(key);

			toFile.createNewFile();

			// 给listener传值
			if (null != listener) {
				// 为了不影响加密/解密程速度，采用异步方式
				new Thread(new Runnable() {
					@Override
					public void run() {
						if (EncryptFileTread.this.listener.hasInit()) {
							EncryptFileTread.this.listener.onStartFile(fromFile.getAbsolutePath(),
									toFile.getAbsolutePath(), fromFile.length());
						} else {
							EncryptFileTread.this.listener.onStart(1, fromFile.length());
							EncryptFileTread.this.listener.onStartFile(fromFile.getAbsolutePath(),
									toFile.getAbsolutePath(), fromFile.length());
						}
					}
				}).start();
			}
			in = new FileInputStream(fromFile);
			os = new FileOutputStream(toFile);
			byte[] datas = new byte[EncryptNames.getEvery_size()];
			int size;
			while ((size = in.read(datas)) != -1) {
				datas = EncryptUtil.encryptByte(datas, KEY);
				// 给listener传值
				if (null != listener) {
					// 为了不影响加密/解密程速度，采用异步方式
					final long size_t = size;
					new Thread(new Runnable() {
						@Override
						public void run() {
							EncryptFileTread.this.listener.onLengthChanged(Long.parseLong(size_t + ""));
						}
					}).start();
				}
				os.write(datas, 0, size);
				os.flush();
			}
			os.flush();
			// 给listener传值
			if (null != listener) {
				// 为了不影响加密/解密程速度，采用异步方式
				new Thread(new Runnable() {
					@Override
					public void run() {
						EncryptFileTread.this.listener.onFinshFile(fromFile.getAbsolutePath());
					}
				}).start();
			}
		} catch (Exception e) {
			LogHandler.fatal(e);
			throw new AppException(e);
		} finally {
			try {
				os.flush();
				os.close();
				in.close();
			} catch (IOException e) {
				LogHandler.warn(e);
			}
		}

	}

}
