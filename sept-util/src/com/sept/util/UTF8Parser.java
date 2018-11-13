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
 * @function ��������gbk�������ļ�ת����utf8
 *           ��gbk�Ĵ���ŵ�srcDir֮�£�ת����destDir֮�£���֧��srcDir֮����Ŀ¼��srcDirĿ¼��destDirĿ¼����һ��
 * @author Jacksile E-mail:tufujietec@foxmail.com
 * @date 2016��1��16�� ����3:02:07
 */
public class UTF8Parser {

	static File srcDir = new File("E://encode/from"); // ��ת���GBK��ʽ�ļ���
	static File destDir = new File("E://encode/to"); // ת���UTF8��Ŀ���ļ���

	public static void main(String[] args) throws Exception {
		// 1.�ж���Ŀ¼
		if (!srcDir.isDirectory()) {
			return;
		}
		// 2.��������Ŀ¼
		File[] fs = srcDir.listFiles();

		// ����Ŀ��Ŀ¼
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
	 * �ж��ļ��ı����ʽ
	 * 
	 * @param fileName
	 *            :file
	 * @return �ļ������ʽ
	 * @throws Exception
	 */
	public static String codeString(String fileName) throws Exception {
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
				fileName));
		int p = (bin.read() << 8) + bin.read();
		String code = null;
		// ���е� 0xefbb��0xfffe��0xfeff��0x5c75��Щ��������ļ���ǰ�������ֽڵ�16������
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
	 * Ŀ¼�͵����������ļ����ر���
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
				// �����ļ���
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
		// ��д����
		PrintWriter ps = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(destFile, false), "utf8"));
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "gbk"));

		// ��д����
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