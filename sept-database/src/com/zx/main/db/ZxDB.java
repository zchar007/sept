package com.zx.main.db;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.sept.database.access.Access;
import com.sept.database.access.AccessFactory;
import com.sept.exception.AppException;

public class ZxDB implements DBNames {
	public static void main(String[] args) throws AppException, IOException {
		initDB();
	}
	public static final void initDB() throws AppException, IOException {
		File f = new File(PATH);
		if (!f.exists() || f.isFile()) {
			f.mkdirs();
		}
		Access access = AccessFactory.newAccess(PATH + File.separator + FILE_DB);
		access.newTable("god", "id:12,name:12");
		for (int i = 0; i < 10; i++) {
			HashMap<String, String> hm = new HashMap<>();
			hm.put("id","id" + i);
			hm.put("name", "name" +i);
			access.addRow("god", hm);
		}
		access.save();
	}

}