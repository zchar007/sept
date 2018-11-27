package com.sept.io.web.ftp;

import org.apache.commons.net.ftp.FTPClient;

import com.sept.datastructure.DataStore;
import com.sept.datastructure.common.SharedInformationPool;
import com.sept.exception.AppException;

/**
 * 类描述-下载线程
 * 
 * @author 张超
 * @version 1.0 创建时间 2017-6-7
 */
class DownloadThread implements Runnable {
	private DataStore vdsFiles;
	private SharedInformationPool mp;
	private String url;
	private String username;
	private String password;
	private int port;

	/**
	 * 构造方法简介.
	 * <p>
	 * 构造方法详述
	 * </p>
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 * @param vdsFiles : file,tourl
	 * @param mp
	 * @param client
	 */
	public DownloadThread(DataStore vdsFiles, SharedInformationPool mp, String url, int port, String username,
			String password) {
		super();
		this.vdsFiles = vdsFiles;
		this.mp = mp;
		this.url = url;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	@Override
	public void run() {
		for (int i = 0; i < vdsFiles.rowCount(); i++) {
			try {
				String fromUrl = vdsFiles.getString(i, "fromurl");
				String toUrl = vdsFiles.getString(i, "tourl");
				FTPClient client = FTPUtil.getFtpConnection(url, port, username, password);
				// 执行上传
				FTPUtil.FTPDownloadFile(fromUrl, toUrl, client, mp);
			} catch (AppException e) {
				e.printStackTrace();
			}

		}

	}

}