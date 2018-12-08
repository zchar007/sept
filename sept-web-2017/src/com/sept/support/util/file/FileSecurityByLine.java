package com.sept.support.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.sept.support.common.Alert;

/**
 * @see 安全读取与写入文件内容，按规定大小读取
 * @author 张超ZC6
 */
public class FileSecurityByLine {
	private String str;
	private File file;
	private BufferedReader brForList = null;
	private BufferedReader brForJump = null;

	public FileSecurityByLine(String url) throws FileNotFoundException {

		this.file = new File(url);
		if (!this.file.exists()) {
			throw new FileNotFoundException("'" + url + "'此文件不存在，无法读取！！");
		}

		this.brForList = new BufferedReader(new FileReader(file));
	}

	public String readLine() {
		if (null == this.brForList) {
			return null;
		}
		this.str = null;
		try {
			this.str = this.brForList.readLine();
		} catch (Exception e) {
			this.str = null;
		}
		return this.str;
	}

	public String readAnyLine(int line) throws IOException {
		if (line <= 0)
			return null;
		try {
			this.brForJump = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			return null;
		}
		this.str = null;
		int k = 1;
		try {
			while ((this.str = this.brForJump.readLine()) != null) {
				if (k == line)
					break;
				k++;
			}
		} catch (IOException e) {
			return null;
		} finally {
			this.brForJump.close();
		}
		return this.str;
	}

	public void close() throws IOException {
		try {
			this.brForList.close();
		} catch (Exception e) {

		}
		try {
			this.brForJump.close();
		} catch (Exception e) {

		}

	}

	public ArrayList<String> getArrayList() {
		ArrayList<String> aList = new ArrayList<String>();
		String string = null;
		while ((string = this.readLine()) != null) {
			aList.add(string);
		}
		return aList;

	}

	public static boolean SaveFileByLine(ArrayList<String> alSave, String url, boolean isAppend) throws IOException {
		File file = new File(url);
		if (!file.exists()) {
			boolean isCreate = Alert.comfrim("文件：【" + url + "】不存在，是否创建？？");
			if (isCreate)
				file.createNewFile();
			else
				return isCreate;
		}
		PrintWriter pw = new PrintWriter(new FileOutputStream(file, isAppend));

		for (String str : alSave) {
			pw.println(str);
			pw.flush();
		}

		pw.flush();
		pw.close();

		return true;

	}

	public String getAll() {
		String returnStr = "";
		ArrayList<String> aList = this.getArrayList();
		for (String string : aList) {
			returnStr += string + "\n";
		}
		return returnStr;
	}

	public static void main(String[] args) throws IOException {
		FileSecurityByLine fs = new FileSecurityByLine("D://test.txt");
		System.out.println(fs.readAnyLine(2));
		System.out.println(fs.readLine());

	}

}
