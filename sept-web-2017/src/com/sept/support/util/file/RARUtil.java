package com.sept.support.util.file;

import java.io.File;

import com.sept.exception.AppException;
import com.sept.global.GlobalNames;
import com.sept.support.model.data.DataObject;
import com.sept.support.util.CmdUtil;

public class RARUtil{
	private static DataObject RarVdo = null;
	static{
		try {
			RarVdo = GlobalNames.getDeploy("rar");
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	public static boolean unRarFile(String urlFrom, String urlTo,
			String passWord) throws Exception {
		//
		if (urlFrom == null
				|| !(urlFrom.endsWith("rar") || urlFrom.endsWith("RAR"))) {
			throw new Exception("要解压的文件不存在或不是RAR文件！！");
		}
		File fileFrom = new File(urlFrom);
		File fileTo = urlTo == null ? fileFrom.getParentFile() : new File(urlTo);
		// System.out.println(fileFrom.getAbsolutePath());
		// System.out.println(fileTo.getAbsolutePath());

		File fRar = new File(RarVdo.getString("UNRAR"));
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
		String cmd = fRar.getAbsolutePath() + " X  " + pwStr
				+ fileFrom.getAbsolutePath() + " " + filePath.getAbsolutePath()
				+ "\\";
		try {
			CmdUtil.run(cmd);
		} catch (Exception e) {
			String error = "未找到Rar.exe";
			throw new Exception(e.getMessage() + error);
		}

		return true;

	}

	/**
	 * 压缩文件
	 * 
	 * @param urlFrom :文件来源 可以使文件夹，也可以是文件
	 * @param urlTo : 不可以是文件 应该是文件夹
	 * @return
	 * @throws Exception
	 */
	public static boolean RarFile(String urlFrom, String urlTo, String passWord) throws Exception {
		if (urlFrom == null) {
			throw new Exception("入参错误！！");
		}
		File fileFrom = new File(urlFrom);
		urlTo = urlTo == null ? fileFrom.getParentFile().getAbsolutePath() : urlTo;
		File fileTo = new File(urlTo);
		// System.out.println(fileFrom.getAbsolutePath());
		// System.out.println(fileTo.getAbsolutePath());
		// 文件来源是否存在
		if (!fileFrom.exists()) {
			throw new Exception("要压缩的文件不存在！！");
		}
		// 如果文件去向不是RAR结尾的，那么，在其文件夹下新增以源文件命名并且以rar结尾的文件

		fileTo = new File(fileTo.getAbsolutePath() + "//" + fileFrom.getName()
				+ ".rar");
		// 获取其父文件夹
		File fTemp = fileTo.getParentFile();
		// 如果不存在，则创建
		if (!fTemp.exists()) {
			fTemp.mkdirs();
		}
		// 如果rar文件不存在，则创建(不能创建，如果创建了会提示文件错误)
		// if(!fileTo.exists()){
		// fileTo.createNewFile();
		// }

		File fRar = new File(RarVdo.getString("RAR"));
		//
		String pwStr = "";
		if (null != passWord && !"".equals(passWord)) {
			pwStr = " -p" + passWord + "  ";
		}
		String cmd = fRar.getAbsolutePath() + " a  " + pwStr
				+ fileTo.getAbsolutePath() + " \"" + fileFrom.getAbsolutePath()
				+ "\"";
		try {
			CmdUtil.run(cmd);
		} catch (Exception e) {
			String error = "未找到Rar.exe文件";
			throw new Exception(e.getMessage() + error);
		}

		return true;

	}

	// WinRAR a Info.rar "c:\latest data"
	public static void main(String[] args) throws Exception {
		 RarFile("D://距离测量工具.exe", "D://", "12345");
		//unRarFile("D://距离测量工具.exe.rar", "D://test//", "12345");
	}

}
