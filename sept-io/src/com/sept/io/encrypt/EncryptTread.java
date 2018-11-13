package com.sept.io.encrypt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.sept.util.pool.MessagePool;

public class EncryptTread implements Runnable {
	// private File fromFile;
	// String toUrl;
	// String key;
	private ArrayList<HashMap<String, Object>> vdsFiles;
	private HashMap<String, Object> pdoConfig;
	private String key;
	MessageStore mp;

	public EncryptTread(ArrayList<HashMap<String, Object>> vdsFiles, String key, MessageStore mp,
			HashMap<String, Object> pdoConfig) {
		super();
		this.vdsFiles = vdsFiles;
		this.key = key;
		this.mp = mp;
		this.pdoConfig = pdoConfig;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < vdsFiles.size(); i++) {
				File file = (File) vdsFiles.get(i).get("file");
				String toUrl = (String) vdsFiles.get(i).get("tourl");
				EncryptUtil.encryptFile(file, toUrl, key, mp, pdoConfig);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
