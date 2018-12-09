package com.sept.io.local;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.sept.exception.AppException;

/**
 * 有关路径问题的一些工具类
 * 
 * @author zhangchao_lc
 *
 */
public class FilePathUtil {
	/**
	 * 遍历一个目录中的所有文件，包括子目录,写入文件中（防止文件过多）
	 * 
	 * @param path
	 * @param fliter ：要获取的后缀名串
	 * @author 张超
	 * @throws AppException
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	public final static HashMap<String, Object> getFilesFormPath(String path, String filter, String resultFile)
			throws AppException {
		File file = new File(path);
		return getFilesFormFile(file, filter, resultFile);
	}

	/**
	 * 遍历一个目录中的所有文件，包括子目录,写入文件中（防止文件过多）
	 * 
	 * @param path
	 * @param fliter ：要获取的后缀名串
	 * @author 张超
	 * @throws AppException
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	public final static HashMap<String, Object> getFilesFormFile(File file, String filter, String resultFile)
			throws AppException, AppException {
		HashMap<String, Object> hm = new HashMap<>();
		long size = 0;
		int fileNumber = 0;
		HashSet<String> hsFilter = new HashSet<String>();
		if (null != filter && !filter.trim().isEmpty()) {
			String[] filters = filter.split(",");
			for (int i = 0; i < filters.length; i++) {
				hsFilter.add(filters[i]);
			}
		}
		// 如果是文件直接返回
		if (file.isFile()) {
			if (hsFilter.size() > 0 && hsFilter.contains(getFileType(file))) {
				hm.put("length", 0L);
				hm.put("number", 0);

				return hm;
			}
			FileLineReader.saveFile(file.getAbsolutePath(), resultFile, true);
			hm.put("length", file.length());
			hm.put("number", 1);

			return hm;
		}

		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isFile()) {
				if (hsFilter.size() > 0 && !hsFilter.contains(getFileType(fileList[i]))) {
					continue;
				}
				// alFiles.add(fileList[i]);
				FileLineReader.saveFile(fileList[i].getAbsolutePath(), resultFile, true);
				fileNumber++;
				size += fileList[i].length();

			} else {
				HashMap<String, Object> hmReturn = getFilesFormFile(fileList[i], filter, resultFile);
				size += (long) hmReturn.get("length");
				fileNumber += (int) hmReturn.get("number");

			}
		}
		hm.put("length", size);
		hm.put("number", fileNumber);
		return hm;
	}

	/**
	 * 遍历一个目录中的所有文件，包括子目录
	 * 
	 * @param path
	 * @param fliter ：要获取的后缀名串
	 * @author 张超
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	public final static HashMap<String, Object> getFilesFormPath(String path, String filter) throws AppException {
		File file = new File(path);
		return getFilesFormFile(file, filter);
	}

	/**
	 * 遍历一个目录中的所有文件，包括子目录
	 * 
	 * @param path
	 * @param fliter ：要获取的后缀名串
	 * @author 张超
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	@SuppressWarnings("unchecked")
	public final static HashMap<String, Object> getFilesFormFile(File file, String filter) throws AppException {
		HashMap<String, Object> pdo = new HashMap<String, Object>();
		ArrayList<File> alFiles = new ArrayList<File>();
		long size = 0;
		HashSet<String> hsFilter = new HashSet<String>();
		if (null != filter && !filter.trim().isEmpty()) {
			String[] filters = filter.split(",");
			for (int i = 0; i < filters.length; i++) {
				hsFilter.add(filters[i]);
			}
		}
		// 如果是文件直接返回
		if (file.isFile()) {
			// 如果此文件不能通过过滤
			if (hsFilter.size() > 0 && !hsFilter.contains(getFileType(file))) {
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
				if (hsFilter.size() > 0 && !hsFilter.contains(getFileType(fileList[i]))) {
					continue;
				}
				alFiles.add(fileList[i]);
				size += fileList[i].length();

			} else {
				HashMap<String, Object> vdoTemp = getFilesFormFile(fileList[i], filter);
				alFiles.addAll((ArrayList<File>) vdoTemp.get("files"));
				size += (Long) vdoTemp.get("length");
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
	public final static String getFileType(File file) {
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
	public final static String getFileType(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	
}
