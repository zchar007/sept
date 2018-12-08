package com.sept.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.sept.debug.log4j.LogHandler;
import com.sept.util.cmd.CmdUtil;

public class DeleteFileUtil {
	public static void main(String[] args) throws Exception {
		ArrayList<String> al = CmdUtil.run("del /F /S /Q G:\\\\Oracle\\\\f4155_zh-cn.sql");
System.out.println(al);
		//deleteAllFilesOfDir(new File("G:\\Oracle\\f4155_zh-cn.sql"));
	}
	/**
	 * 删除文件夹（强制删除）
	 * 
	 * @param path
	 */
	public static void deleteAllFilesOfDir(File path) {
		if (null != path) {
			if (!path.exists())
				return;
			if (path.isFile()) {
				boolean result = path.delete();
				int tryCount = 0;
				while (!result && tryCount++ < 10) {
					System.gc(); // 回收资源
					result = path.delete();
				}
				LogHandler.debug((result?"已":"未")+"删除文件["+path.getName()+"]");
			}
			File[] files = path.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					deleteAllFilesOfDir(files[i]);
				}
			}
			path.delete();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param pathname
	 * @return
	 * @throws IOException
	 */
	public static boolean deleteFile(String pathname) {
		boolean result = false;
		File file = new File(pathname);
		if (file.exists()) {
			file.delete();
			result = true;
			System.out.println("文件已经被成功删除");
		}
		return result;
	}
}
