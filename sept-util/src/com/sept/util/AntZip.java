package com.sept.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipInputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.sept.exception.AppException;

public class AntZip {
	static final int BUFFER = 2048;

	// zip方法对byte进行压缩
	public static byte[] zip(byte[] data) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipEntry ze = new ZipEntry("servletservice");
		ZipOutputStream zos = new ZipOutputStream(baos);
		zos.putNextEntry(ze);
		zos.write(data, 0, data.length);
		zos.close();
		byte[] zipBytes = baos.toByteArray();
		return zipBytes;
	}

	// zip方法对byte进行解压缩
	public static byte[] unzip(byte[] zipBytes) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(zipBytes);
		ZipInputStream zis = new ZipInputStream(bais);
		zis.getNextEntry();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// final int BUFSIZ = 4096;
		byte inbuf[] = new byte[BUFFER];
		int n;
		while ((n = zis.read(inbuf, 0, BUFFER)) != -1) {
			baos.write(inbuf, 0, n);
		}
		byte[] data = baos.toByteArray();
		zis.close();
		return data;
	}

	/**
	 * 
	 * 说明：压缩方法：filepath是源文件路径，zipPath是压缩文件路径
	 * 
	 * @param filePath
	 * @param zipPath
	 * @throws AppException
	 */
	public static void zip(String filePath, String zipPath) throws AppException {
		BufferedInputStream origin = null;
		FileOutputStream dest = null;
		ZipOutputStream out = null;
		FileInputStream fi = null;
		File f = new File(filePath);
		try {
			dest = new FileOutputStream(zipPath);
			out = new ZipOutputStream(new BufferedOutputStream(dest));
			byte data[] = new byte[BUFFER];
			File files[] = f.listFiles();

			for (int i = 0; i < files.length; i++) {
				fi = new FileInputStream(files[i]);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(files[i].getName());
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (IOException e) {
			throw new AppException("压缩文件出错");
		} finally {
			while (f.listFiles().length > 0) {
				f.listFiles()[0].delete();
			}
			f.delete();
			try {
				if (fi != null) {
					fi.close();
				}
				if (origin != null) {
					origin.close();
				}
				if (dest != null) {
					dest.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				throw new AppException("关闭文件流出错" + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * 说明：压缩方法,压缩方法同源文件同级
	 * 
	 * @param filePath :filepath是源文件路径
	 * @throws AppException
	 */
	public static void zip(String filePath) throws AppException {
		BufferedInputStream origin = null;
		FileOutputStream dest = null;
		ZipOutputStream out = null;
		FileInputStream fi = null;
		File f = new File(filePath);
		try {
			String zipPath = filePath.substring(0, filePath.length() - 1) + ".zip";
			dest = new FileOutputStream(zipPath);
			out = new ZipOutputStream(new BufferedOutputStream(dest));
			byte data[] = new byte[BUFFER];
			File files[] = f.listFiles();

			for (int i = 0; i < files.length; i++) {
				fi = new FileInputStream(files[i]);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(files[i].getName());
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (Exception e) {
			throw new AppException("压缩文件出错");
		} finally {
			while (f.listFiles().length > 0) {
				f.listFiles()[0].delete();
			}
			f.delete();
			try {
				if (fi != null) {
					fi.close();
				}
				if (origin != null) {
					origin.close();
				}
				if (dest != null) {
					dest.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				throw new AppException("关闭文件流出错" + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * 说明：解压缩的方法，filepath是源文件路径，zipPath是压缩文件路径
	 * 
	 * @param filePath
	 * @param zipPath
	 * @throws IOException
	 * @throws AppException
	 */

	public static void unZip(String filePath, String zipPath) throws IOException, AppException {
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		int count = 0;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipPath);
			Enumeration<?> emu = zipFile.getEntries();
			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(filePath + entry.getName()).mkdirs();
					continue;
				}
				bis = new BufferedInputStream(zipFile.getInputStream(entry));
				File file = new File(filePath + entry.getName());
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, BUFFER);
				byte data[] = new byte[BUFFER];
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				bos.close();
				fos.close();
				bis.close();
			}
		} catch (Exception e) {
			throw new AppException("解压缩文件时出现错误");
		} finally {
			try {
				zipFile.close();
				if (bis != null) {
					bis.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				throw new AppException("关闭文件流出错");
			}
		}
	}

	/**
	 * 
	 * 说明：解压缩方法,压缩方法同源文件同级
	 * 
	 * @param filePath :filepath是源文件路径
	 * @throws AppException
	 */

	public static void unZip(String filePath) throws AppException {
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		int count = 0;
		ZipFile zipFile = null;
		try {
			String zipPath = filePath.substring(0, filePath.length() - 1) + ".zip";
			zipFile = new ZipFile(zipPath);
			Enumeration<?> emu = zipFile.getEntries();
			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(filePath + entry.getName()).mkdirs();
					continue;
				}
				bis = new BufferedInputStream(zipFile.getInputStream(entry));
				File file = new File(filePath + entry.getName());
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, BUFFER);

				byte data[] = new byte[BUFFER];
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
		} catch (Exception e) {
			throw new AppException("解压缩文件时出现错误");
		} finally {
			try {
				zipFile.close();
				if (bis != null) {
					bis.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				throw new AppException("关闭文件流出错");
			}
		}
	}
}
