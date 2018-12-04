package com.sept.io.local;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.sept.exception.AppException;
import com.sept.util.SecUtil;

public class FileIOTool {
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

	/**
	 * 写文本到文件
	 * 
	 * @param filePath    文件路径
	 * @param fileContent 文件内容
	 * @param appended    是否追加
	 */
	public static void writeStrToFile(String filePath, String fileContent, boolean appended) throws AppException {
		String path = filePath.substring(0, filePath.lastIndexOf(File.separator));
		File dirFile = new File(path);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(filePath, appended);
		} catch (Exception e) {
			throw new AppException("创建文件错误:" + e.getMessage());
		}
		try {
			writer.write(fileContent);
		} catch (Exception e) {
			throw new AppException("写文件内容错误:" + e.getMessage());
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				throw new AppException("关闭文件错误:" + e.getMessage());
			}
		}

	}

	/**
	 * 写文件，不追加
	 * 
	 * @param filePath    文件路径
	 * @param fileContent 文件内容
	 */
	public static void writeStrToFile(String filePath, String fileContent) throws AppException {
		writeStrToFile(filePath, fileContent, false);
	}

	/**
	 * 说明：将字节流写入到服务器端的文件中。
	 * 
	 * @author:ZC Oct 20, 2008
	 * @param filebyte
	 * @param filepath :路径名+文件名
	 * @throws AppException
	 */
	public static void writeBytesToFile(byte[] filebyte, String filepath, String filename) throws AppException {
		ByteArrayInputStream inputstream = null;
		FileOutputStream outputstream = null;
		try {
			if (filename != null && !filename.equals("")) {
				File file = new File(filepath + File.separator + filename);
				file.createNewFile();
				if (filebyte != null && filebyte.length != 0) {
					inputstream = new ByteArrayInputStream(filebyte);
					outputstream = new FileOutputStream(file);
					byte[] b = new byte[1024];
					int len = 0;
					while ((len = inputstream.read(b)) != -1) {
						outputstream.write(b, 0, len);
					}
					outputstream.flush();
					outputstream.close();
					inputstream.close();
				}
			}
		} catch (IOException e) {
			throw new AppException("文件读取异常，可能是文件损坏或不存在!错误信息为：" + e.getMessage());
		} finally {
			try {
				if (outputstream != null) {
					outputstream.close();
				}
				if (inputstream != null) {
					inputstream.close();
				}
			} catch (Exception e) {
				throw new AppException("文件读取异常，可能是文件损坏或不存在!错误信息为：" + e.getMessage());
			}
		}
	}

	/**
	 * 从指定文件中获取字节流数据
	 * 
	 * @author wf
	 * @throws AppException
	 * @throws IOException
	 * @date 创建时间 May 29, 2010
	 * @since V1.0
	 */
	public static byte[] getBytesFromFile(File file) throws AppException, IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			long length = file.length();
			byte[] bytes = new byte[(int) length];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file " + file.getName());
			}
			is.close();
			return bytes;
		} catch (FileNotFoundException e) {
			throw new AppException("没有找到指定文件.错误信息为：" + e.getMessage());
		} catch (IOException e) {
			throw new AppException("读取文件内容异常.错误信息为：" + e.getMessage());
		} finally {
			if (is != null)
				is.close();
		}
	}

	/**
	 * 向文件中写入二进制数据流
	 * 
	 * @author wf
	 * @date 创建时间 May 29, 2010
	 * @since V1.0
	 */
	public static void writeBytesToFile(byte[] bytes, File file) throws AppException {
		OutputStream out;
		try {
			out = new FileOutputStream(file);
			out.write(bytes);
			out.close();
		} catch (FileNotFoundException e) {
			throw new AppException("没有找到指定文件.错误信息为：" + e.getMessage());
		} catch (IOException e) {
			throw new AppException("读取文件内容异常.错误信息为：" + e.getMessage());
		}
	}

	public static String getImageStrByImage(File image) throws AppException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(image.getAbsolutePath());
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			throw new AppException(-1, "图片转成Base64编码时出错");
		}
		// 对字节数组Base64编码
		return SecUtil.base64Encode(data); // 返回Base64编码过的字节数组字符串
	}

	public static byte[] getImageByImageStr(String imgStr) throws AppException { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return null;
		// Base64解码
		byte[] data = SecUtil.base64Decode(imgStr);
		for (int i = 0; i < data.length; i++) {
			if (data[i] < 0) { // 调整异常数据
				data[i] += 256;
			}
		}
		return data;
	}

}
