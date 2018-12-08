package com.sept.support.util.file;

import java.io.File;
import java.util.ArrayList;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;

@Deprecated
public class FileTools {

	/**
	 * 遍历一个目录中的所有文件，包括子目录
	 * 
	 * @param path
	 * @param fliter
	 *            ：要获取的后缀名串
	 * @author 张超
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	@SuppressWarnings("unchecked")
	public static DataObject getFilesFormPath(String path, String fliter)
			throws AppException {
		DataObject pdo = new DataObject();
		ArrayList<File> alFiles = new ArrayList<File>();
		File file = new File(path);
		long size = 0;
		if (null != fliter && !fliter.trim().isEmpty()) {
			fliter = "kkkkk" + fliter;
		}
		// 如果是文件直接返回
		if (file.isFile()) {
			if (fliter.indexOf(getFileType(file)) < 0) {
				pdo.put("files", alFiles);
				pdo.put("length", 0L);
				return pdo;
			}
			alFiles.add(file);
			pdo.put("files", alFiles);
			pdo.put("length", file.length());
			return pdo;
		}

		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isFile()) {
				if (fliter.indexOf(getFileType(fileList[i])) < 0) {
					continue;
				}
				alFiles.add(fileList[i]);
				size += fileList[i].length();

			} else {
				DataObject vdoTemp = getFilesFormPath(
						fileList[i].getAbsolutePath(), fliter);
				alFiles.addAll((ArrayList<File>) vdoTemp.getObject("files"));
				size += vdoTemp.getLong("length");
			}

		}
		pdo.put("files", alFiles);
		pdo.put("length", size);
		return pdo;
	}

	/**
	 * 遍历一个目录中的所有文件，包括子目录
	 * 
	 * @param path
	 * @param fliter
	 *            ：要获取的后缀名串
	 * @author 张超
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	@SuppressWarnings("unchecked")
	public static DataObject getFilesFormFile(File file, String fliter)
			throws AppException {
		DataObject pdo = new DataObject();
		ArrayList<File> alFiles = new ArrayList<File>();
		long size = 0;
		if (null != fliter && !fliter.trim().isEmpty()) {
			fliter = "kkkkk" + fliter;
		}
		// 如果是文件直接返回
		if (file.isFile()) {
			if (fliter.indexOf(getFileType(file)) < 3) {
				pdo.put("files", alFiles);
				pdo.put("length", 0L);
				return pdo;
			}
			alFiles.add(file);
			pdo.put("files", alFiles);
			pdo.put("length", file.length());
			return pdo;
		}

		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isFile()) {
				// System.out.println(fileList[i]+"--"+fliter+"--"+getFileType(fileList[i]));
				if (fliter.indexOf(getFileType(fileList[i])) < 3) {
					continue;
				}
				alFiles.add(fileList[i]);
				size += fileList[i].length();

			} else {
				DataObject vdoTemp = getFilesFormFile(fileList[i], fliter);
				alFiles.addAll((ArrayList<File>) vdoTemp.getObject("files"));
				size += vdoTemp.getLong("length");
			}

		}
		pdo.put("files", alFiles);
		pdo.put("length", size);
		return pdo;
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileType(File file) {
		if (!file.isFile()) {
			return "";
		}
		return file.getName().substring(file.getName().lastIndexOf(".") + 1);
	}
	/**
	 * 获取文件后缀名
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileType(String fileName) {

		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	

	public static void main(String[] args) throws AppException {
		// DataObject pdo = getFilesFormPath("E:\\", "dat");
		// System.out.println(pdo.getLong("length"));
		// System.out.println(((ArrayList<File>)
		// pdo.getObject("files")).size());
//		File f = new File("D://小工具//");
//		File[] files = f.listFiles();
//		for (int i = 0; i < files.length; i++) {
//			System.out.println(getFileType(files[i]));
//			System.out.println("xxtxt,pap".indexOf(getFileType(files[i])));
//
//		}
		System.out.println(getFileType("xxx.jsp"));

	}
}
