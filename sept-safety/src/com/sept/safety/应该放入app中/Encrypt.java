package com.sept.safety.应该放入app中;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.sept.exception.AppException;
import com.sept.util.StringUtil;

public class Encrypt implements IEncrypt {
	private String pwd;
	private HashMap<String, String> config;
	private EncryptListener encryptListener;
	private String fromHead;
	private String fromUrl;
	private String toUrl;
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 10, TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue<Runnable>(5), new ThreadPoolExecutor.CallerRunsPolicy());

	/**
	 * @param pwd      密码
	 * @param fromFile 文件/文件夹来源
	 * @param toUrl    加密/解密后的去向
	 * @throws AppException
	 */
	public Encrypt(String pwd) throws AppException {
		super();
		this.pwd = pwd;
		this.setConfig(new HashMap<>());
	}

	public Encrypt(String pwd, HashMap<String, String> config, String fromUrl, String toUrl) {
		super();
		this.pwd = pwd;
		this.setConfig(config);
		this.fromUrl = fromUrl;
		this.toUrl = toUrl;
	}

	public Encrypt(String pwd, String fromUrl, String toUrl) {
		super();
		this.pwd = pwd;
		this.fromUrl = fromUrl;
		this.toUrl = toUrl;
		this.setConfig(new HashMap<>());
	}

	@Override
	public void doEncrypt() throws AppException {
		/** 入参检查 **/
		checkPwd(pwd);
		File fromFile = checkFromUrl(fromUrl);
		File toFile = checkToUrl(toUrl);
		if (fromFile.isDirectory()) {
			this.doEncryptDirectory(fromFile, toFile);
		} else {
			this.doEncryptFile(fromFile, toFile);
		}
	}

	@Override
	public void doEncryptFile(File fromFile, File toFile) throws AppException {
		if (null == fromHead) {
			fromHead = fromFile.getAbsolutePath();
		}
		executor.execute(new EncryptFileTread(pwd, fromHead, fromFile, toFile, config, encryptListener));
	}

	@Override
	public void doEncryptDirectory(File fromFile, File toFile) throws AppException {
		executor.execute(new EncryptDirectoryTread(pwd, fromFile, toFile, config, encryptListener));
	}

	/**
	 * 检查密码
	 * 
	 * @param pwd
	 * @throws AppException
	 */
	public static final void checkPwd(String pwd) throws AppException {
		// 密码不能包含中文
		if (StringUtil.containsChn(pwd)) {
			throw new AppException("密码不能含有中文！");
		}
		if (pwd.length() < 4) {
			throw new AppException("密码最少4位！");
		}
	}

	/**
	 * 检查文件来源
	 * 
	 * @param url
	 * @throws AppException
	 */
	public static final File checkFromUrl(String url) throws AppException {
		if (null == url) {
			throw new AppException("fromurl 不能为空！");
		}
		return checkFromFile(new File(url));
	}

	/**
	 * 检查文件来源
	 * 
	 * @param url
	 * @throws AppException
	 */
	public static final File checkFromFile(File file) throws AppException {
		if (!file.exists()) {
			throw new AppException("文件来源不存在，请检查！");
		}
		return file;
	}

	/**
	 * 检查目标文件夹,并提前创建好根目录
	 * 
	 * @param url
	 * @throws AppException
	 */
	public static final File checkToUrl(String url) throws AppException {
		if (null == url) {
			throw new AppException("tourl 不能为空！");
		}
		return checkToFile(new File(url));
	}

	/**
	 * 检查目标文件夹,并提前创建好根目录
	 * 
	 * @param url
	 * @throws AppException
	 */
	public static final File checkToFile(File file) throws AppException {
		if (!file.isDirectory()) {
			throw new AppException("目标必须是文件夹，请检查！");
		}
		return file;
	}

	@Override
	public EncryptListener getEncryptListener() {
		return this.encryptListener;
	}

	@Override
	public void addEncryptListener(EncryptListener encryptListener) {
		if (null != encryptListener) {
			encryptListener.clear();
		}
		this.encryptListener = encryptListener;
	}

	@Override
	public void addConfig(String key, String value) {
		this.config.put(key, value);
	}

	@Override
	public void setPwd(String pwd) throws AppException {
		this.pwd = pwd;
	}

	@Override
	public String getPwd() throws AppException {
		// TODO Auto-generated method stub
		return this.pwd;
	}

	public HashMap<String, String> getConfig() {
		return config;
	}

	public void setConfig(HashMap<String, String> config) {
		if (null == config) {
			config = new HashMap<>();
		}
		this.config = config;
	}

	public String getFromUrl() {
		return fromUrl;
	}

	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}

	public String getToUrl() {
		return toUrl;
	}

	public void setToUrl(String toUrl) {
		this.toUrl = toUrl;
	}

}
