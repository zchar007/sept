package com.sept.drop;

import java.io.File;
import java.util.HashMap;

import com.sept.datastructure.DataStore;
import com.sept.datastructure.common.SharedInformationPool;

class EncryptTread implements Runnable {
	// private File fromFile;
	// String toUrl;
	// String key;
	private DataStore vdsFiles;
	private HashMap<String, Object> pdoConfig;
	private String key;
	SharedInformationPool mp;

	public EncryptTread(DataStore vdsFiles, String key, SharedInformationPool mp,
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
				File file = (File)vdsFiles.get(i).get("file");
				String toUrl = (String) vdsFiles.get(i).get("tourl");
				EncryptUtil.encryptFile(file, toUrl, key, mp, pdoConfig);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
