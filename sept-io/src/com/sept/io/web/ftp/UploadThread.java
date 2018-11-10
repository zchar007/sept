package com.sept.io.web.ftp;

import java.io.File;

import org.apache.commons.net.ftp.FTPClient;

import com.sept.datastructure.pool.MessagePool;
import com.sept.datastructure.set.DataStore;
import com.sept.exception.AppException;

/**
 * ������-�ϴ��߳�
 * 
 * @author �ų�
 * @version 1.0 ����ʱ�� 2017-6-7
 */
public class UploadThread implements Runnable {
	private DataStore vdsFiles;
	private MessagePool mp;
	private String url;
	private int port;
	private String username;
	private String password;

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
	public UploadThread(DataStore vdsFiles, MessagePool mp, String url, int port, String username, String password) {
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

				File file = (File) vdsFiles.getObject(i, "file");
				String toUrl = vdsFiles.getString(i, "tourl");
				FTPClient client = FTPUtil.getFtpConnection(url, port, username, password);
				// �����ļ���
				FTPUtil.mkdirs(client, toUrl);
				client = FTPUtil.getFtpConnection(url, port, username, password);
				// ִ���ϴ�
				FTPUtil.FTPUploadFile(file, toUrl, client, mp);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}

	}

}