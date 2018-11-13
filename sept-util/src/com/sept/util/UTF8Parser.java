package com.sept.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @function 将批量的gbk的乱码文件转换到utf8
 *           将gbk的代码放到srcDir之下，转码置destDir之下，不支持srcDir之下有目录，srcDir目录与destDir目录不能一样
 * @author Jacksile E-mail:tufujietec@foxmail.com
 * @date 2016年1月16日 下午3:02:07
 */
public class UTF8Parser {

	static File srcDir = new File("E://encode/from"); // 待转码的GBK格式文件夹
	static File destDir = new File("E://encode/to"); // 转码成UTF8的目标文件夹

	public static void main(String[] args) throws Exception {
		// 1.判断是目录
		if (!srcDir.isDirectory()) {
			return;
		}
		// 2.遍历所有目录
		File[] fs = srcDir.listFiles();

		// 创建目标目录
		if (!destDir.exists()) {
			destDir.mkdirs();
		}

		try {
			new UTF8Parser().parse(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isUTF8(File file) throws IOException {
		InputStream in = new java.io.FileInputStream(file);
		byte[] b = new byte[3];
		in.read(b);
		in.close();
		if (b[0] == -17 && b[1] == -69 && b[2] == -65)
			return true;
		else
			return false;
	}

	/**
	 * 判断文件的编码格式
	 * 
	 * @param fileName
	 *            :file
	 * @return 文件编码格式
	 * @throws Exception
	 */
	public static String codeString(String fileName) throws Exception {
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
				fileName));
		int p = (bin.read() << 8) + bin.read();
		String code = null;
		// 其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		case 0x5c75:
			code = "ANSI|ASCII";
			break;
		default:
			code = "GBK";
		}
		bin.close();
		return code;
	}

	public static String codeStrin2(String fileName) throws Exception {

		InputStream inputStream = new FileInputStream(fileName);
		byte[] head = new byte[3];
		inputStream.read(head);
		String code = "";
		code = "gb2312";
		if (head[0] == -1 && head[1] == -2)
			code = "UTF-16";
		if (head[0] == -2 && head[1] == -1)
			code = "Unicode";
		if (head[0] == -17 && head[1] == -69 && head[2] == -65)
			code = "UTF-8";
		inputStream.close();
		return code + "[" + head[0] + "," + head[1] + "," + head[2] + "]";
	}

	/**
	 * 目录就迭代遍历；文件就重编码
	 * 
	 * @throws Exception
	 */
	private void parse(File[] fs) throws Exception {
		for (int i = 0; i < fs.length; i++) {
			File file = fs[i];
			if (!file.isDirectory()) {
				if (file.getAbsolutePath().endsWith(".java")
						|| file.getAbsolutePath().endsWith(".jsp")
						|| file.getAbsolutePath().endsWith(".xml")
						|| file.getAbsolutePath().endsWith(".tld")
						|| file.getAbsolutePath().endsWith(".css")
						|| file.getAbsolutePath().endsWith(".js")) {

					File destFile = new File(getToPath(file,
							destDir.getAbsolutePath()));
					if (!destFile.exists()) {
						destFile.createNewFile();
					}

					parse2UTF_8(file, destFile);
					System.out.println(file.getAbsolutePath());

					// System.out.println(file.getAbsolutePath() + "----"
					// + codeStrin2(file.getAbsolutePath()));
				}
			} else {
				// 创建文件夹
				File file2 = new File(
						getToPath(file, destDir.getAbsolutePath()));
				// System.out.println(file2.getAbsolutePath());
				if (!file2.exists()) {
					file2.mkdirs();
				}
				parse(file.listFiles());
			}
		}
	}

	private String getToPath(File fromFile, String toPathHead) {
		String fromPath = fromFile.getAbsolutePath();
		String toPath = toPathHead
				+ fromPath.replace(srcDir.getAbsolutePath(), "");
		// System.out.println(toPath);
		return toPath;
	}

	/** 
     */
	private void parse2UTF_8(File file, File destFile) throws IOException {
		StringBuffer msg = new StringBuffer();
		// 读写对象
		PrintWriter ps = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(destFile, false), "utf8"));
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "gbk"));

		// 读写动作
		String line = br.readLine();
		while (line != null) {
			msg.append(line).append("\r\n");
			line = br.readLine();
		}
		ps.write(msg.toString());
		br.close();
		ps.flush();
		ps.close();
	}

}