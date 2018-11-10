package com.sept.io.web.ftp;

import org.apache.commons.net.ftp.FTPClient;

import com.sept.datastructure.pool.MessagePool;
import com.sept.datastructure.set.DataStore;
import com.sept.exception.AppException;

/**
 * ������-�����߳�
 * 
 * @author �ų�
 * @version 1.0 ����ʱ�� 2017-6-7
 */
class DownloadThread implements Runnable {
	private DataStore vdsFiles;
	private MessagePool mp;
	private String url;
	private String username;
	private String password;
	private int port;

	/**
	 * ���췽�����.
	 * <p>
	 * ���췽������
	 * </p>
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 * @param vdsFiles
	 *            : file,tourl
	 * @param mp
	 * @param client
	 */
	public DownloadThread(DataStore vdsFiles, MessagePool mp, String url, int port, String username, String password) {
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
		try {
			for (int i = 0; i < vdsFiles.rowCount(); i++) {
				String toUrl = vdsFiles.getString(i, "tourl");
				String fromUrl = vdsFiles.getString(i, "fromurl");
				FTPClient client = FTPUtil.getFtpConnection(url, port, username, password);
				// ִ���ϴ�
				FTPUtil.FTPDownloadFile(fromUrl, toUrl, client, mp);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}

	}

}