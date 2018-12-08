package com.sept.support.ftp;

import org.apache.commons.net.ftp.FTPClient;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataStore;
import com.sept.support.pool.MessagePool;

/**
 * 类描述-下载线程
 * 
 * @author 张超
 * @version 1.0 创建时间 2017-6-7
 */
class DownloadThread implements Runnable{
	private DataStore vdsFiles;
	private MessagePool mp;
	private String url;
	private String username;
	private String password;

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
	public DownloadThread(DataStore vdsFiles, MessagePool mp, String url,
		String username, String password) {
		super();
		this.vdsFiles = vdsFiles;
		this.mp = mp;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < vdsFiles.rowCount(); i++) {
				String toUrl = vdsFiles.getString(i, "tourl");
				String fromUrl = vdsFiles.getString(i, "fromurl");
				FTPClient client = FTPUtil.getFtpConnection(url, username, password);
				// 执行上传
				FTPUtil.FTPDownloadFile(fromUrl, toUrl, client, mp);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}

	}

}