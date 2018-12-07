package com.sept.drop.pbf.access;

import java.io.File;
import java.io.IOException;

import com.healthmarketscience.jackcess.Database;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public class AccessFactory {
	/**
	 * 创建一个Access,文件已存在则无法创建
	 * @param url
	 * @param fileFormat
	 * @return
	 * @throws AppException
	 */
	public static final Access createdAccess(String url, Database.FileFormat fileFormat) throws AppException {
		File file = new File(url);
		if (file.exists()) {
			throw new AppException("Access文件【" + url + "】已存在！");
		}
		return new Access(url, fileFormat);
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws AppException
	 */
	public static Access createdAccess(String url) throws AppException {
		File file = new File(url);

		if (file.exists()) {
			throw new AppException("Access文件【" + url + "】已存在！");
		}
		return new Access(url);
	}

	public static Access openAccess(String url) throws AppException {
		File file = new File(url);

		if (!file.exists()) {
			throw new AppException("Access文件【" + url + "】不存在！");

		}
		return new Access().openAccess(url);
	}

	public static void main(String[] args) throws AppException, IOException {

		Access access = AccessFactory.openAccess("D://test.mdb");
//		// access.newTable("test", "ryid:12,shbzhm:12,xm:12");
//		for (int i = 0; i < 10; i++) {
//			HashMap<String, String> hm = new HashMap<>();
//			hm.put("ryid", i + "" + i + "" + i);
//			hm.put("shbzhm", i + "" + i + "" + i + "" + i + "" + i + "" + i);
//			hm.put("xm", "张" + i);
//			access.addRow("test", hm);
//		}
//		access.save();
//		access = AccessFactory.openAccess("D://test.mdb");
//		EncryptUtil.encryptFile(new File("D://test.mdb"), "D://", "12345");

		DataStore ds = access.getDataStore("test");
		System.out.println(ds);

		System.out.println(ds.findAll(" ryid > 888"));

	}

}
