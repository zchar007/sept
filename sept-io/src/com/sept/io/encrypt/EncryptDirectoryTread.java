package com.sept.io.encrypt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.sept.exception.AppException;
import com.sept.io.local.FileUtil;

class EncryptDirectoryTread implements Runnable {
	private String key;
	private String fromHead;
	private File fromFile;
	private File toFile;
	private HashMap<String, String> pdoConfig;
	private EncryptListener listener;
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 10, TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue<Runnable>(5), new ThreadPoolExecutor.CallerRunsPolicy());

	public EncryptDirectoryTread(String key, File fromFile, File toFile, HashMap<String, String> pdoConfig,
			EncryptListener listener) {
		super();
		this.key = key;
		this.fromFile = fromFile;
		this.toFile = toFile;
		this.pdoConfig = pdoConfig;
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			this.encryptDirectory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void encryptDirectory() throws AppException {
		if (!fromFile.isDirectory()) {
			throw new AppException("EncryptDirectoryThread只允许加密文件夹");
		}
		this.fromHead = fromFile.getAbsolutePath();
		if (!toFile.isDirectory()) {
			throw new AppException("EncryptDirectoryThread只允许加密到文件夹");
		}
		HashMap<String, Object> pdo = FileUtil.getFilesFormPath(fromFile.getAbsolutePath(), "");
		long length = Long.parseLong(pdo.get("length").toString());
		ArrayList<File> alFiles = (ArrayList<File>) pdo.get("files");

		// 给listener传值
		if (null != listener) {
			// 为了不影响加密/解密程速度，采用异步方式
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (!EncryptDirectoryTread.this.listener.hasInit()) {
						EncryptDirectoryTread.this.listener.onStart(alFiles.size(), length);
					}
				}
			}).start();
		}
		//启用多线程
		if (EncryptNames.MULTITHREADING) {
			// 以下是拆成10个线程，不足10个则一个个均摊
			int[] everys = new int[10];
			// 小于等于10，开10个线程，每个线程处理一个
			int every = alFiles.size() / 10;
			int yushu = alFiles.size() % 10;

			for (int i = 0; i < everys.length; i++) {
				everys[i] = every;
				if (i < yushu) {
					everys[i] = everys[i] + 1;
				}
			}
			int index = 0;
			// 加入线程池，最后返回此线程池，方便外部控制
			for (int i = 0; i < everys.length; i++) {
				if (everys[i] > 0) {
					final ArrayList<File> alFileTemp = new ArrayList<>();
					for (int j = index; j < index + everys[i]; j++) {
						alFileTemp.add(alFiles.get(j));
					}
					index += everys[i];
					executor.execute(new Runnable() {

						@Override
						public void run() {
							for (int j = 0; j < alFileTemp.size(); j++) {
								final File file = alFileTemp.get(j);

								try {
									EncryptFileTread encrypt = new EncryptFileTread(key, fromHead, file, toFile,
											pdoConfig, listener);
									encrypt.encryptFile();
								} catch (AppException e) {
									// 给listener传值
									if (null != listener) {
										// 为了不影响加密/解密程速度，采用异步方式
										new Thread(new Runnable() {
											@Override
											public void run() {
												EncryptDirectoryTread.this.listener
														.onMessageChanged("文件[" + file.getAbsolutePath() + "]加密/解密失败！");
											}
										}).start();
									} else {
										e.printStackTrace();
									}
								}
							}
						}
					});
				}
			}
		} else {//不启用多线程

			for (int i = 0; i < alFiles.size(); i++) {
				final File file = alFiles.get(i);
				try {
					EncryptFileTread encrypt = new EncryptFileTread(key, fromHead, file, toFile,
							pdoConfig, listener);
					encrypt.encryptFile();
				} catch (AppException e) {
					// 给listener传值
					if (null != listener) {
						// 为了不影响加密/解密程速度，采用异步方式
						new Thread(new Runnable() {
							@Override
							public void run() {
								EncryptDirectoryTread.this.listener
										.onMessageChanged("文件[" + file.getAbsolutePath() + "]加密/解密失败！");
							}
						}).start();
					} else {
						e.printStackTrace();
					}
				}
			}
		
		}
	}

}
