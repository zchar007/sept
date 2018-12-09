package com.sept.io.local;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;

/**
 * @see 安全读取与写入文件内容，按规定大小读取
 * @author 张超ZC6
 */
public class FileLineReader {
	private String str;
	private File file;
	private BufferedReader brForList = null;
	private BufferedReader brForJump = null;
	private int readNumber = 0;
	private Object key = new Object();

	/**
	 * 
	 * @param url
	 *            文件位置
	 * @throws AppException
	 */
	public FileLineReader(String url) throws AppException {
		try {
			this.file = new File(url);
			if (!this.file.exists()) {
				throw new AppException("'" + url + "'此文件不存在，无法读取！！");
			}

			this.brForList = new BufferedReader(new FileReader(file));
		} catch (Exception e) {

			throw new AppException(-11, e.getMessage());
		}
	}

	/**
	 * 读取一行数据，循环调用循环读取
	 * 
	 * @return
	 */
	public String readLine() {
		synchronized (key) {
			if (null == this.brForList) {
				return null;
			}
			this.str = null;
			try {
				this.readNumber++;
				this.str = this.brForList.readLine();

			} catch (Exception e) {
				this.str = null;
			}
			return this.str;
		}
	}

	/**
	 * 跳跃读取，读取指定行，不会影响顺序读取
	 * 
	 * @param line
	 * @return
	 * @throws AppException
	 */
	public String readJump(int line) throws AppException {
		synchronized (key) {
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
			} catch (Exception e) {
				throw new AppException(-11, e.getMessage());
			} finally {
				try {
					this.brForJump.close();
				} catch (Exception e) {

					throw new AppException(-11, e.getMessage());
				}
			}
			return this.str;
		}
	}

	/**
	 * 关闭流
	 * 
	 * @throws AppException
	 */
	public void close() throws AppException {
		synchronized (key) {
			try {
				this.brForList.close();
			} catch (Exception e) {
				LogHandler.error(e);
			}
			try {
				this.brForJump.close();
			} catch (Exception e) {
				LogHandler.error(e);
			}
		}
	}

	/**
	 * 读取所有行
	 * 
	 * @return
	 */
	public ArrayList<String> read() {
		synchronized (key) {
			ArrayList<String> aList = new ArrayList<String>();
			String string = null;
			while ((string = this.readLine()) != null) {
				aList.add(string);
			}
			return aList;
		}

	}

	/**
	 * 获取总行数
	 * 
	 * @return
	 */
	public long getLineCount() {
		synchronized (key) {
			return this.brForList.lines().count();
		}
	}

	/**
	 * 获取文件大小
	 * 
	 * @return
	 */
	public long getSize() {
		synchronized (key) {
			return this.file.length();
		}
	}

	/**
	 * 读取所有数据，每行以换行符连接
	 * 
	 * @return
	 */
	public String readAll() {
		synchronized (key) {
			String returnStr = "";
			ArrayList<String> aList = this.read();
			for (String string : aList) {
				returnStr += string + "\n";
			}
			return returnStr;
		}
	}

	/**
	 * 写入数据
	 * 
	 * @param message
	 * @param isAppend
	 * @throws AppException
	 */
	public void write(String message, boolean isAppend) throws AppException {
		synchronized (key) {
			ArrayList<String> alSave = new ArrayList<>();
			alSave.add(message);
			this.write(alSave, isAppend);
		}
	}

	/**
	 * 新的追加写入不会更改原读取顺序，但非追加性写入会重置原读写顺序
	 * 
	 * @param alSave
	 * @param isAppend
	 * @throws AppException
	 */
	public void write(ArrayList<String> alSave, boolean isAppend) throws AppException {
		synchronized (key) {
			try {
				PrintWriter pw = new PrintWriter(new FileOutputStream(this.file, isAppend));

				for (String str : alSave) {
					pw.println(str);
					pw.flush();
				}
				pw.flush();
				pw.close();
				this.brForList = new BufferedReader(new FileReader(file));

				if (isAppend) {
					for (int i = 0; i < readNumber; i++) {
						this.brForList.readLine();
					}
				} else {
					this.readNumber = 0;
				}
			} catch (Exception e) {
				throw new AppException(-12, e.getMessage());
			}
		}
	}

	/**
	 * 重置流
	 * 
	 * @throws AppException
	 */
	public void reset() throws AppException {
		synchronized (key) {
			try {
				this.brForList = new BufferedReader(new FileReader(file));
				this.readNumber = 0;
			} catch (Exception e) {
				throw new AppException(-11, e.getMessage());
			}
		}
	}

	/**
	 * 静态保存方法
	 * 
	 * @param alSave
	 * @param url
	 * @param isAppend
	 * @throws AppException
	 */
	public static final void saveFile(ArrayList<String> alSave, String url, boolean isAppend) throws AppException {
		try {
			File file = new File(url);
			if (!isAppend) {// 不是追加
				if (file.exists()) {
					throw new AppException("文件[" + url + "]已存在且不为追加！");
				} else {
					file.createNewFile();
				}
			} else {
				if (!file.exists()) {
					file.createNewFile();
				}
			}

			PrintWriter pw = new PrintWriter(new FileOutputStream(file, isAppend));

			for (String str : alSave) {
				pw.println(str);
				pw.flush();
			}

			pw.flush();
			pw.close();

		} catch (Exception e) {
			throw new AppException(-12, e.getMessage());
		}
	}

	/**
	 * 保存字符串到文件中，非追加不可往文件中重写数据
	 * 
	 * @param message
	 * @param url
	 * @param isAppend
	 * @throws AppException
	 */
	public static final void saveFile(String message, String url, boolean isAppend) throws AppException {
		try {
			File file = new File(url);
			if (!isAppend) {// 不是追加
				if (file.exists()) {
					throw new AppException("文件[" + url + "]已存在且不为追加！");
				} else {
					file.createNewFile();
				}
			} else {
				if (!file.exists()) {
					file.createNewFile();
				}
			}

			PrintWriter pw = new PrintWriter(new FileOutputStream(file, isAppend));

			pw.println(message);
			pw.flush();

			pw.flush();
			pw.close();

		} catch (Exception e) {
			throw new AppException(-12, e.getMessage());
		}
	}
}
