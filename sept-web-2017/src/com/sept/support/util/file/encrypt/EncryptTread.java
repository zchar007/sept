package com.sept.support.util.file.encrypt;

import java.io.File;

import com.sept.support.model.data.DataStore;
import com.sept.support.pool.MessagePool;

public class EncryptTread implements Runnable{
	// private File fromFile;
	// String toUrl;
	// String key;
	private DataStore vdsFiles;
	private String key;
	MessagePool mp;

	public EncryptTread(DataStore vdsFiles, String key, MessagePool mp) {
		super();
		this.vdsFiles = vdsFiles;
		this.key = key;
		this.mp = mp;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < vdsFiles.rowCount(); i++) {
				File file = (File) vdsFiles.getObject(i, "file");
				String toUrl = vdsFiles.getString(i, "tourl");
				EncryptUtil.encryptFile(file, toUrl, key, mp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
