package com.sept.framework.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sept.exception.AppException;
import com.sept.exception.BusinessException;
import com.sept.global.InteractiveDict;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;

/**
 * 前后台交互必备 read:从前台获取 write:向前台写入
 * 
 * @author zchar
 */
public class ActionUtil {

	/**
	 * 获取前台传过来的文件
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static CommonsMultipartFile readFileFromRequest(
			HttpServletRequest request, String name) {
		MultipartHttpServletRequest multipartRequest;
		CommonsMultipartFile commonsMultipartFile;
		// 从上传文件中取出ds
		multipartRequest = (MultipartHttpServletRequest) request;
		commonsMultipartFile = (CommonsMultipartFile) multipartRequest
				.getFile(name);
		return commonsMultipartFile;
	}

	/**
	 * 把文件写入前台用于下载 ,可以区分浏览器进行下载
	 * 
	 * @param response
	 * @param bytes
	 * @param fileName
	 * @param fileType
	 * @return
	 * @throws Exception
	 */
	public static ModelAndView writeFileToResponse(
			HttpServletResponse response, HttpServletRequest request,
			byte[] bytes, String fileName, String fileType) throws Exception {
		String userAgent = request.getHeader("User-Agent");
		// String userAgent = "dsdasMSIEsas";
		// 针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			fileType = java.net.URLEncoder.encode("." + fileType, "UTF-8");
		} else {
			// 非IE浏览器的处理：
			// 这个处理还不确定
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			fileType = new String(("." + fileType).getBytes("UTF-8"),
					"ISO-8859-1");
		}
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename="
				+ String.format("%s", fileName) + String.format("%s", fileType));// 设定输出文件头
		response.setContentType("application/msexcel;charset=GBK");// 定义输出类型
		OutputStream os = response.getOutputStream();
		os.write(bytes, 0, bytes.length);
		os.flush();
		os.close();
		return null;
	}

	/**
	 * 把FTP文件写入前台用于下载 ,可以区分浏览器进行下载
	 * 
	 * @param response
	 * @param bytes
	 * @param fileName
	 * @param fileType
	 * @return
	 * @throws Exception
	 */
	public static ModelAndView writeFTPFileToResponse(
			HttpServletResponse response, HttpServletRequest request,
			FTPClient client, String fromUrl) throws Exception {

		InputStream in = null;
		OutputStream os = null;
		try {
			String fileName = "";
			if (client.changeWorkingDirectory(fromUrl)) {
				throw new AppException("只能下载文件！");
			}
			FTPFile[] filesTemp = client.listFiles(new String(fromUrl
					.getBytes("GBK"), "iso-8859-1"));

			String userAgent = request.getHeader("User-Agent");
			// 针对IE或者以IE为内核的浏览器：
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				fileName = java.net.URLEncoder.encode(filesTemp[0].getName(),
						"UTF-8");
			} else {
				// 非IE浏览器的处理：
				// 这个处理还不确定
				fileName = new String(filesTemp[0].getName().getBytes("UTF-8"),
						"ISO-8859-1");
			}
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ String.format("%s", fileName));// 设定输出文件头
			response.setContentType("application/msexcel;charset=GBK");// 定义输出类型
			client.enterLocalPassiveMode();

			in = client.retrieveFileStream(new String(fromUrl.getBytes("GBK"),
					"iso-8859-1"));
			os = response.getOutputStream();
			byte[] datas = new byte[1024];
			int tempbyte;
			while ((tempbyte = in.read(datas)) != -1) {
				os.write(datas, 0, tempbyte);
				os.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);

		} finally {
			try {
				os.flush();
				os.close();
				in.close();
				client.logout();
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	/**
	 * 把Blob写入前台用于下载 ,可以区分浏览器进行下载
	 * 
	 * @param response
	 * @param bytes
	 * @param fileName
	 * @param fileType
	 * @return
	 * @throws Exception
	 */
	public static ModelAndView writeBlobFileToResponse(
			HttpServletResponse response, HttpServletRequest request,
			Blob blob, String fileName, String fileType) throws Exception {
		String userAgent = request.getHeader("User-Agent");
		// String userAgent = "dsdasMSIEsas";
		// 针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			fileType = java.net.URLEncoder.encode("." + fileType, "UTF-8");
		} else {
			// 非IE浏览器的处理：
			// 这个处理还不确定
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			fileType = new String(("." + fileType).getBytes("UTF-8"),
					"ISO-8859-1");
		}
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename="
				+ String.format("%s", fileName) + String.format("%s", fileType));// 设定输出文件头
		response.setContentType("application/msexcel;charset=GBK");// 定义输出类型
		InputStream in = blob.getBinaryStream(); // 建立输出流
		OutputStream out = response.getOutputStream();
		int len = (int) blob.length();
		byte[] buffer = new byte[len]; // 建立缓冲区
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
			out.flush();
		}
		if (null != out) {
			out.close();
		}
		in.close();
		return null;
	}

	/**
	 * 把文件写入前台用于下载 ,可以区分浏览器进行下载
	 * 
	 * @param response
	 * @param bytes
	 * @param fileName
	 * @param fileType
	 * @return
	 * @throws Exception
	 */
	public static ModelAndView writeBlobToResponse(
			HttpServletResponse response, Blob blob) throws Exception {
		InputStream in = blob.getBinaryStream(); // 建立输出流
		OutputStream out = response.getOutputStream();
		int len = (int) blob.length();
		byte[] buffer = new byte[len]; // 建立缓冲区
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
			out.flush();
		}
		if (null != out) {
			out.close();
		}
		in.close();
		return null;
	}

	/**
	 * 把文件写入前台用于下载
	 * 
	 * @param response
	 * @param bytes
	 * @param fileName
	 * @param fileType
	 * @return
	 * @throws Exception
	 */
	public static ModelAndView writeFileToResponse(
			HttpServletResponse response, byte[] bytes, String fileName,
			String fileType) throws Exception {
		// String userAgent = request.getHeader("User-Agent");
		String userAgent = "dsdasMSIEsas";
		// 针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			fileType = java.net.URLEncoder.encode("." + fileType, "UTF-8");
		} else {
			// 非IE浏览器的处理：
			// 这个处理还不确定
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			fileType = new String(("." + fileType).getBytes("UTF-8"),
					"ISO-8859-1");
		}
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename="
				+ String.format("%s", fileName) + String.format("%s", fileType));// 设定输出文件头
		response.setContentType("application/msexcel;charset=GBK");// 定义输出类型
		OutputStream os = response.getOutputStream();
		os.write(bytes, 0, bytes.length);
		os.flush();
		os.close();
		return null;

	}

	/**
	 * 往前台写消息，仅仅是字符串的消息，接受端可直接使用
	 * 
	 * @author 张超
	 * @throws AppException
	 * @date 创建时间 2017-6-14
	 * @since V1.0
	 */
	public static ModelAndView writeMessageToResponse(
			HttpServletResponse response, String data) throws AppException {
		response.setContentType("text/html;charset=UTF-8");
		if (data == null) {
			data = "";
		}
		PrintWriter out = null;
		try {
			response.setContentLength(data.getBytes("UTF-8").length);
			out = response.getWriter();
			out.print(data);
			out.flush();
		} catch (IOException ex) {
			// 可以在这里记录一下日志
			// LogHandler.saveException("" +
			// ex.getMessage());
			throw new AppException(ex);
		} finally {
			if (null != out) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 以指定字符编码向前台写数据
	 * 
	 * @author 张超
	 * @throws AppException
	 * @date 创建时间 2017-6-14
	 * @since V1.0
	 */
	public static ModelAndView writeMessageToResponse(
			HttpServletResponse response, String data, String charset)
			throws AppException {
		response.setContentType("text/html;charset=" + charset);
		if (data == null) {
			data = "";
		}
		PrintWriter out = null;
		try {
			response.setContentLength(data.getBytes(charset).length);
			out = response.getWriter();
			out.print(data);
			out.flush();
		} catch (IOException ex) {
			// 可以在这里记录一下日志
			// LogHandler.saveException("" +
			// ex.getMessage());
			throw new AppException(ex);
		} finally {
			if (null != out) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 向前台发送一个DataObject的json串
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-14
	 * @since V1.0
	 */
	public static ModelAndView writeDataObjectToResponse(
			HttpServletResponse response, DataObject vdo) throws AppException {
		response.setContentType("text/html;charset=UTF-8");
		String data = vdo.toJSON();
		if (data == null) {
			data = "";
		}
		PrintWriter out = null;
		try {
			response.setContentLength(data.getBytes("UTF-8").length);
			out = response.getWriter();
			out.print(data);
			out.flush();
			// System.out.println(data);
		} catch (IOException ex) {
			// 可以在这里记录一下日志
			// LogHandler.saveException("" +
			// ex.getMessage());
			throw new AppException(ex);
		} finally {
			if (null != out) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 向前台发送一个DataStore的json串
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-14
	 * @since V1.0
	 */
	public static ModelAndView writeDataStoreToResponse(
			HttpServletResponse response, DataStore vds) throws AppException {
		response.setContentType("text/html;charset=UTF-8");
		String data = vds.toJSON();
		if (data == null) {
			data = "";
		}
		PrintWriter out = null;
		try {
			response.setContentLength(data.getBytes("UTF-8").length);
			out = response.getWriter();
			out.print(data);
			out.flush();
		} catch (IOException ex) {
			// 可以在这里记录一下日志
			// LogHandler.saveException("" +
			// ex.getMessage());
			throw new AppException(ex);
		} finally {
			if (null != out) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 跳转到Res页面
	 * 
	 * @return
	 */
	public static ModelAndView forward(String forwardUrl) {
		forwardUrl = checkForwardUrl(forwardUrl);
		return new ModelAndView(forwardUrl);
	}

	/**
	 * 跳转到Res页面
	 * 
	 * @return
	 */
	public static ModelAndView forward(String forwardUrl, String messageName,
			String message) {
		forwardUrl = checkForwardUrl(forwardUrl);
		return new ModelAndView(forwardUrl, messageName, message);
	}

	/**
	 * 跳转到Res页面
	 * 
	 * @return
	 */
	public static ModelAndView forward(String forwardUrl, String vdsName,
			DataStore vds) {
		forwardUrl = checkForwardUrl(forwardUrl);
		return new ModelAndView(forwardUrl, vdsName, vds);
	}

	/**
	 * 跳转到Res页面
	 * 
	 * @return
	 */
	public static ModelAndView forward(String forwardUrl, DataObject vdo) {
		forwardUrl = checkForwardUrl(forwardUrl);
		return new ModelAndView(forwardUrl, vdo);
	}

	/**
	 * 重定向
	 * 
	 * @return
	 */
	public static ModelAndView redirect(String redirectUrl) {
		redirectUrl = checkRedirectUrl(redirectUrl);
		return new ModelAndView(redirectUrl);
	}

	/**
	 * 重定向
	 * 
	 * @return
	 */
	public static ModelAndView redirect(String redirectUrl, String messageName,
			String message) {
		redirectUrl = checkRedirectUrl(redirectUrl);
		return new ModelAndView(redirectUrl, messageName, message);
	}

	/**
	 * 重定向
	 * 
	 * @return
	 */
	public static ModelAndView redirect(String redirectUrl, String vdsName,
			DataStore vds) {
		redirectUrl = checkRedirectUrl(redirectUrl);
		return new ModelAndView(redirectUrl, vdsName, vds);
	}

	/**
	 * 重定向
	 * 
	 * @return
	 */
	public static ModelAndView redirect(String redirectUrl, DataObject vdo) {
		redirectUrl = checkRedirectUrl(redirectUrl);
		return new ModelAndView(redirectUrl, vdo);
	}

	/**
	 * 把URL替换为跳转
	 * 
	 * @param forwardUrl
	 * @return
	 */
	private static String checkForwardUrl(String forwardUrl) {
		if ("redirect".startsWith(forwardUrl)) {
			forwardUrl.replaceAll("redirect:", "");
		}
		if (!"forward:".startsWith(forwardUrl)) {
			forwardUrl = "forward:" + forwardUrl;
		}
		return forwardUrl;
	}

	/**
	 * 把url改为重定向
	 * 
	 * @param redirectUrl
	 * @return
	 */
	private static String checkRedirectUrl(String redirectUrl) {
		if ("forward".startsWith(redirectUrl)) {
			redirectUrl.replaceAll("forward:", "");
		}
		if (!"redirect:".startsWith(redirectUrl)) {
			redirectUrl = "redirect:" + redirectUrl;
		}
		return redirectUrl;
	}

	public static ModelAndView writeExceptionToResponse(
			HttpServletResponse response, Throwable ex) {
		// e.printStackTrace();
		DataObject pdo = new DataObject();
		// PrintWriter out = response.getWriter();
		// 异常已在catch中转换成了两种--biz或app
		Throwable throwable = ex;
		// 抛出提示性消息
		if (throwable instanceof BusinessException) {
			// 抛出特定业务异常，提示
			pdo.put(InteractiveDict.INTERACTIVE_ERROR_FLAG,
					InteractiveDict.INTERACTIVE_ERROR_BUSI);
			pdo.put(InteractiveDict.INTERACTIVE_ERROR_TEXT,
					throwable.getMessage());

		}
		// 打开错误窗口res，显示错误信息
		else {
			ex.printStackTrace();
			StackTraceElement[] st = throwable.getStackTrace();
			String error = "<br/>" + throwable.toString();
			String errortext = "";
			for (int i = 0; i < st.length; i++) {
				errortext += "<br/>" + st[i].toString();
			}
			error += errortext;
			pdo.clear();
			pdo.put(InteractiveDict.INTERACTIVE_ERROR_FLAG,
					InteractiveDict.INTERACTIVE_ERROR_APP);
			pdo.put(InteractiveDict.INTERACTIVE_ERROR_TEXT, error);

		}
		try {
			writeMessageToResponse(response, pdo.toJSON());
		} catch (AppException e1) {
			e1.printStackTrace();
		}
		return new ModelAndView();
	}

	public static ModelAndView writeCookiesToResponse(
			HttpServletResponse response, DataObject pdo) throws AppException,
			Exception {
		for (String key : pdo.keySet()) {
			CookieUtil.addCookie(response, key, pdo.getString(key));
		}
		return null;
	}

	/**
 */
	protected static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.indexOf("0:") != -1) {
			ip = "localhost";
		}
		return ip;
	}
}
