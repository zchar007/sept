package com.sept.io.local;

import java.io.File;

import com.sept.exception.AppException;
import com.sept.util.CmdUtil;

/**
 * ��������webʹ��
 * 
 * @author zchar
 * 
 */
public final class RARUtil {
	private static String rarExeFile = "./exe/Rar.exe";
	private static String unrarExeFile = "./exe/UnRaR.exe";

	public final static boolean unRarFile(String urlFrom, String urlTo, String passWord) throws AppException {
		//
		if (urlFrom == null || !(urlFrom.endsWith("rar") || urlFrom.endsWith("RAR"))) {
			throw new AppException("Ҫ��ѹ���ļ������ڻ���RAR�ļ�����");
		}
		File fileFrom = new File(urlFrom);
		File fileTo = urlTo == null ? fileFrom.getParentFile() : new File(urlTo);
		File fRar = new File(RARUtil.unrarExeFile);
		String path = fileTo.getAbsolutePath();
		File filePath = new File(path);
		int index = 1;
		while (filePath.exists()) {
			filePath = new File(path + "(" + index + ")");
			index++;
		}
		filePath.mkdirs();
		String pwStr = "";
		if (null != passWord && !"".equals(passWord)) {
			pwStr = " -p" + passWord + "  ";
		}
		String cmd = fRar.getAbsolutePath() + " X  " + pwStr + fileFrom.getAbsolutePath() + " "
				+ filePath.getAbsolutePath() + "\\";
		try {
			CmdUtil.run(cmd);
		} catch (Exception e) {
			String error = "δ�ҵ�Rar.exe";
			throw new AppException(e.getMessage() + error);
		}

		return true;

	}

	/**
	 * ѹ���ļ�
	 * 
	 * @param urlFrom
	 *            :�ļ���Դ ����ʹ�ļ��У�Ҳ�������ļ�
	 * @param urlTo
	 *            : ���������ļ� Ӧ�����ļ���
	 * @return
	 * @throws Exception
	 */
	public final static boolean RarFile(String urlFrom, String urlTo, String passWord) throws AppException {
		if (urlFrom == null) {
			throw new AppException("��δ��󣡣�");
		}
		File fileFrom = new File(urlFrom);
		urlTo = urlTo == null ? fileFrom.getParentFile().getAbsolutePath() : urlTo;
		File fileTo = new File(urlTo);
		// �ļ���Դ�Ƿ����
		if (!fileFrom.exists()) {
			throw new AppException("Ҫѹ�����ļ������ڣ���");
		}
		// ����ļ�ȥ����RAR��β�ģ���ô�������ļ�����������Դ�ļ�����������rar��β���ļ�

		fileTo = new File(fileTo.getAbsolutePath() + "//" + fileFrom.getName() + ".rar");
		// ��ȡ�丸�ļ���
		File fTemp = fileTo.getParentFile();
		// ��������ڣ��򴴽�
		if (!fTemp.exists()) {
			fTemp.mkdirs();
		}
		// ���rar�ļ������ڣ��򴴽�(���ܴ�������������˻���ʾ�ļ�����)
		File fRar = new File(RARUtil.rarExeFile);
		//
		String pwStr = "";
		if (null != passWord && !"".equals(passWord)) {
			pwStr = " -p" + passWord + "  ";
		}
		String cmd = fRar.getAbsolutePath() + " a  " + pwStr + fileTo.getAbsolutePath() + " \""
				+ fileFrom.getAbsolutePath() + "\"";
		try {
			CmdUtil.run(cmd);
		} catch (Exception e) {
			String error = "δ�ҵ�Rar.exe�ļ�";
			throw new AppException(e.getMessage() + error);
		}

		return true;

	}

	public static String getRarExeFile() {
		return rarExeFile;
	}

	public static void setRarExeFile(String rarExeFile) {
		RARUtil.rarExeFile = rarExeFile;
	}

	public static String getUnrarExeFile() {
		return unrarExeFile;
	}

	public static void setUnrarExeFile(String unrarExeFile) {
		RARUtil.unrarExeFile = unrarExeFile;
	}

	// WinRAR a Info.rar "c:\latest data"
	public final static void main(String[] args) throws Exception {
		//RarFile("D://AAA.jpg", "D://", "12345");
		unRarFile("D://AAA.jpg.rar", "D://test//", "12345");
	}
}
