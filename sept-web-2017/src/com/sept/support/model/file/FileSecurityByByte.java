package com.sept.support.model.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sept.support.common.Alert;

public class FileSecurityByByte {
	private File file;
	private File fileForWriter;
	private int byteSize;
	private FileInputStream fileForList;
	private FileInputStream fileForJump;
	private FileOutputStream fileForOut;

	private byte[] readByte;

	public FileSecurityByByte(String url, int byteSize) throws FileNotFoundException {
		if (byteSize <= 0 && byteSize > 1073741824) {
			Alert.alertError("每次读取大小最小是1byte,最大是1GB(1073741824byte)！！");
			return;
		}
		this.byteSize = byteSize;
		this.readByte = new byte[byteSize];
		this.file = new File(url);
		fileForList = new FileInputStream(file);
	}

	/**
	 * 用于对文件的写入
	 * 
	 * @param url
	 * @throws IOException
	 */
	public FileSecurityByByte(String url) throws IOException {

		this.fileForWriter = new File(url);
		if (this.fileForWriter.exists()) {
			throw new IOException("文件已存在！！");
		}
		this.fileForWriter.createNewFile();

		fileForOut = new FileOutputStream(this.fileForWriter);

	}

	public byte[] read() throws IOException {
		byte[] returnByte;
		int length = fileForList.read(readByte, 0, this.byteSize);
		if (length <= 0) {
			return null;
		}
		returnByte = new byte[length];
		for (int i = 0; i < returnByte.length; i++) {
			returnByte[i] = readByte[i];
		}
		readByte = new byte[byteSize];
		return returnByte;
	}

	public byte[] readJump(long start, long end) throws IOException {
		long size = this.file.length();
		byte[] byteTemp = null;
		if (start > size || end > size || start < 0 || end < 0) {
			Alert.alertError("文件大小为：" + size + "，请检查！！");
			return null;
		}
		if (end < start) {
			Alert.alertError("开始大于结束！！");
			return null;
		}
		fileForJump = new FileInputStream(file);
		long getSize = end - start;

		if (start > 0) {
			byteTemp = new byte[(int) start];
			fileForJump.read(byteTemp, 0, (int) start);
		}

		byteTemp = new byte[(int) getSize];
		int leng = fileForJump.read(byteTemp, 0, (int) getSize);
		fileForJump.close();

		if (leng == (int) getSize)
			return byteTemp;
		return null;
	}

	public void close() throws IOException {
		try {
			fileForList.close();
		} catch (Exception e) {

		}
		try {
			fileForJump.close();
		} catch (Exception e) {

		}
		try {
			fileForOut.close();
		} catch (Exception e) {

		}
	}

	public long getSize() {
		return this.file.length();
	}

	public void write(byte[] bytes) throws IOException {
		fileForOut.write(bytes);
		fileForOut.flush();
	}

	public static void main(String[] args) throws IOException {
		FileSecurityByByte fsb = new FileSecurityByByte("D://test.txt", 10);
		byte[] bb = fsb.read();
		for (int i = 0; i < bb.length; i++) {
			System.out.println(bb[i]);
		}

		// System.out.println(new String(fsb.read(),"ISO-8859-1"));
		System.out.println(new String(fsb.readJump(5, 10), "ISO-8859-1"));
	}

}
