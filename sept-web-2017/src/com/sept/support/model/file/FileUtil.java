package com.sept.support.model.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.HashSet;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;

public final class FileUtil {
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
	public final static DataObject getFilesFormPath(String path, String filter)
			throws AppException {
		DataObject pdo = new DataObject();
		ArrayList<File> alFiles = new ArrayList<File>();
		File file = new File(path);
		long size = 0;
		HashSet<String> hsFilter = new HashSet<String>();
		String[] filters = filter.split(",");
		
		for (int i = 0; i < filters.length; i++) {
			hsFilter.add(filters[i]);
		}
		// 如果是文件直接返回
		if (file.isFile()) {
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
				if (hsFilter.size() > 0 && !hsFilter.contains(getFileType(fileList[i]))) {
					continue;
				}
				alFiles.add(fileList[i]);
				size += fileList[i].length();

			} else {
				DataObject vdoTemp = getFilesFormPath(
						fileList[i].getAbsolutePath(), filter);
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
	public final static DataObject getFilesFormFile(File file, String filter)
			throws AppException {
		DataObject pdo = new DataObject();
		ArrayList<File> alFiles = new ArrayList<File>();
		long size = 0;
		HashSet<String> hsFilter = new HashSet<String>();
		String[] filters = filter.split(",");
		for (int i = 0; i < filters.length; i++) {
			hsFilter.add(filters[i]);
		}
		// 如果是文件直接返回
		if (file.isFile()) {
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
				DataObject vdoTemp = getFilesFormFile(fileList[i], filter);
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

	// ///////////////////////////////////
	// /////////以下还未验证正确性//////////////
	// ///////////////////////////////////
	public final static byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length
				&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		fi.close();
		return buffer;
	}

	/**
	 * the traditional io way
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public final static byte[] toByteArray(String filename) throws IOException {

		File f = new File(filename);
		if (!f.exists()) {
			throw new FileNotFoundException(filename);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}

	/**
	 * NIO way
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public final static byte[] toByteArray2(String filename) throws IOException {

		File f = new File(filename);
		if (!f.exists()) {
			throw new FileNotFoundException(filename);
		}

		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Mapped File way MappedByteBuffer
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public final static byte[] toByteArray3(String filename) throws IOException {

		FileChannel fc = null;
		try {
			fc = new RandomAccessFile(filename, "r").getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
					fc.size()).load();
			System.out.println(byteBuffer.isLoaded());
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				// System.out.println("remain");
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws AppException {
		DataObject pdo = FileUtil.getFilesFormFile(new File("E:\\Ant_Framework\\SEPT_FRAMEWORK\\sept"), "jsp");
		System.out.println(pdo);

	}
}
