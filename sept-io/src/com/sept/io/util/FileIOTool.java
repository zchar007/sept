package com.sept.io.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import oracle.sql.BLOB;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sept.exception.AppException;

public class FileIOTool {

	/**
	 * 写文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param fileContent
	 *            文件内容
	 * @param appended
	 *            是否追加
	 */
	@SuppressWarnings("resource")
	public static void fileWrite(String filePath, String fileContent,
			boolean appended) throws AppException {
		String path = filePath.substring(0,
				filePath.lastIndexOf(File.separator));
		File dirFile = new File(path);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(filePath, appended);
		} catch (Exception e) {
			throw new AppException("创建文件错误:" + e.getMessage());
		}
		try {
			writer.write(fileContent);
		} catch (Exception e) {
			throw new AppException("写文件内容错误:" + e.getMessage());
		}
		try {
			writer.close();
		} catch (Exception e) {
			throw new AppException("关闭文件错误:" + e.getMessage());
		}
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param fileContent
	 *            文件内容
	 */
	@Deprecated
	public static void fileWrite(String filePath, String fileContent)
			throws AppException {
		fileWrite(filePath, fileContent, false);
	}

	public static void writeBlobToResponse(Blob blob, String filename,
			HttpServletResponse response, DataObject para) throws AppException {
		writeBlobToResponse(blob, filename, response);
	}

	@Deprecated
	public static void writeByteToResponse(byte[] filebyte, String filename,
			HttpServletResponse response, DataObject para) throws AppException {
		writeByteToResponse(filebyte, filename, response);
	}

	/**
	 * 说明：将文件流放到resposne中。 主要用于从数据库中读取文件。
	 * 
	 * @author:郑其荣 Oct 20, 2008
	 * @param response
	 * @param filebyte
	 * @param filename
	 * @throws AppException
	 * @throws
	 */
	public static void writeByteToResponse(byte[] filebyte, String filename,
			HttpServletResponse response) throws AppException {
		OutputStream toClient = null;
		if (filebyte != null) {
			try {
				response.reset();
				if (filename.length() > 150) {// 解决IE 6.0 bug
					filename = new String(filename.getBytes("GBK"),
							"ISO-8859-1");
				} else {
					filename = URLEncoder.encode(filename, "UTF-8");
				}

				response.setContentType("application/x-download");
				response.addHeader("Content-Disposition",
						"attachment; filename=" + filename);
				toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
				toClient.write(filebyte); // 输出数据
				toClient.flush();
			} catch (IOException e) {
				// 用户取消下载
			} finally {
				try {
					if (toClient != null) {
						toClient.close();
					}
				} catch (Exception e) {
					throw new AppException("文件读取异常，可能是文件损坏或不存在!错误信息为："
							+ e.getMessage());
				}
			}
		} else {
			throw new AppException("该附件不存在");
		}
	}

	/**
	 * 说明：说明：将Blob放到resposne中,主要用于从数据库中读取文件。
	 * 
	 * @author:郑其荣 May 5, 2009
	 * @param filebyte
	 * @param filename
	 * @param response
	 * @throws AppException
	 * @throws
	 */
	public static void writeBlobToResponse(Blob blob, String filename,
			HttpServletResponse response) throws AppException {
		byte[] filebyte = null;
		if (blob != null) {
			try {
				int zbnrLen = (new BigDecimal(blob.length())).intValue();
				filebyte = blob.getBytes(1, zbnrLen);
				writeByteToResponse(filebyte, filename, response);
			} catch (SQLException e) {
				throw new AppException("文件读取异常，可能是文件损坏或不存在!错误信息为："
						+ e.getMessage());
			}
		} else {
			throw new AppException("该附件不存在");
		}
	}

	/**
	 * 说明：将服务器中的文件下载到本地
	 * 
	 * @author:郑其荣 Oct 20, 2008
	 * @param response
	 * @param filepath
	 *            ：服务器中的文件路径
	 * @param filename
	 * @throws AppException
	 */
	public static void saveFileToResponse(String filepath, String filename,
			HttpServletResponse response) throws AppException {
		OutputStream outputstream = null; // 下载流
		FileInputStream inputstream = null;

		try {
			inputstream = new FileInputStream(filepath + File.separator
					+ filename);

			filename = URLEncoder.encode(filename, "UTF-8");
			response.reset();
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename);
			outputstream = response.getOutputStream(); // 得到向客户端输出二进制数据的对象

			byte[] byteValue = new byte[1024];
			int tempValue = 0;
			while ((tempValue = inputstream.read(byteValue)) > 0) {
				outputstream.write(byteValue, 0, tempValue);
			}
			outputstream.flush();
		} catch (IOException e) {
			// 用户取消下载
		} finally {
			try {
				if (outputstream != null) {
					outputstream.close();
				}
				if (inputstream != null) {
					inputstream.close();
				}
			} catch (Exception e) {
				throw new AppException("文件读取异常，可能是文件损坏或不存在!错误信息为："
						+ e.getMessage());
			}
		}
	}

	/**
	 * 说明：将字节流写入到服务器端的文件中。
	 * 
	 * @author:郑其荣 Oct 20, 2008
	 * @param filebyte
	 * @param filepath
	 *            :路径名+文件名
	 * @throws AppException
	 */
	public static void writeByteToServer(byte[] filebyte, String filepath,
			String filename) throws AppException {
		ByteArrayInputStream inputstream = null;
		FileOutputStream outputstream = null;
		try {
			if (filename != null && !filename.equals("")) {
				File file = new File(filepath + File.separator + filename);
				file.createNewFile();
				if (filebyte != null && filebyte.length != 0) {
					inputstream = new ByteArrayInputStream(filebyte);
					outputstream = new FileOutputStream(file);
					byte[] b = new byte[1024];
					int len = 0;
					while ((len = inputstream.read(b)) != -1) {
						outputstream.write(b, 0, len);
					}
					outputstream.flush();
					outputstream.close();
					inputstream.close();
				}
			}
		} catch (IOException e) {
			throw new AppException("文件读取异常，可能是文件损坏或不存在!错误信息为：" + e.getMessage());
		} finally {
			try {
				if (outputstream != null) {
					outputstream.close();
				}
				if (inputstream != null) {
					inputstream.close();
				}
			} catch (Exception e) {
				throw new AppException("文件读取异常，可能是文件损坏或不存在!错误信息为："
						+ e.getMessage());
			}
		}
	}

	/**
	 * 说明：将附件中的文件上传到服务器端
	 * 
	 * @author:郑其荣 Oct 20, 2008
	 * @param file
	 * @param attachFile
	 * @throws AppException
	 */
	public static void saveAttachFileToServer(CommonsMultipartFile attachFile,
			String filepath, String filename) throws AppException {

		FileOutputStream outputstream = null;
		ByteArrayInputStream inputstream = null;
		File file = null;
		try {
			file = new File(filepath + File.separator + filename);
			file.createNewFile();

			inputstream = new ByteArrayInputStream(attachFile.getBytes());
			outputstream = new FileOutputStream(file);
			byte[] byteValue = new byte[1024];
			int len = 0;
			while ((len = inputstream.read(byteValue)) != -1) {
				outputstream.write(byteValue, 0, len);
			}
			outputstream.flush();
		} catch (Exception e) {
			throw new AppException("文件写入异常，可能是文件损坏或不存在!错误信息为：" + e.getMessage());
		} finally {
			try {
				if (outputstream != null) {
					outputstream.close();
				}
				if (inputstream != null) {
					inputstream.close();
				}
			} catch (Exception e) {
				throw new AppException("文件写入异常，可能是文件损坏或不存在!错误信息为："
						+ e.getMessage());
			}
		}
	}

	/**
	 * 说明：将附件中的文档写进数据库中的blob中。
	 * 
	 * @author:郑其荣 Oct 20, 2008
	 * @param blob
	 * @param attachfile
	 * @throws AppException
	 */
	public static void saveAttachFileToBlob(CommonsMultipartFile attachfile,
			BLOB blob) throws AppException {
		if (attachfile != null) {
			OutputStream outputstream = null;
			ByteArrayInputStream inputstream = null;
			try {
				outputstream = blob.setBinaryStream(0l);
				inputstream = new ByteArrayInputStream(attachfile.getBytes());
				byte[] buffer = new byte[blob.getBufferSize()];
				int len = 0;
				while ((len = inputstream.read(buffer)) != -1) {
					outputstream.write(buffer, 0, len);
				}
				outputstream.flush();
			} catch (Exception e) {
				throw new AppException("文件写入异常，可能是文件损坏或不存在!错误信息为："
						+ e.getMessage());
			} finally {
				try {
					if (outputstream != null) {
						outputstream.close();
					}
					if (inputstream != null) {
						inputstream.close();
					}
				} catch (Exception e) {
					throw new AppException("文件写入异常，可能是文件损坏或不存在!错误信息为："
							+ e.getMessage());
				}
			}
		}
	}

	/**
	 * 说明：将服务器端的文档写进数据库中的blob中。
	 * 
	 * @author:郑其荣 Oct 20, 2008
	 * @param blob
	 * @param attachfile
	 * @throws AppException
	 */

	public static void saveFileToBlob(File file, BLOB blob) throws AppException {
		if (file != null) {
			OutputStream outputstream = null;
			FileInputStream inputstream = null;
			try {
				outputstream = blob.setBinaryStream(0l);
				inputstream = new FileInputStream(file);
				byte[] buffer = new byte[blob.getBufferSize()];
				int len = 0;
				while ((len = inputstream.read(buffer)) != -1) {
					outputstream.write(buffer, 0, len);
				}
				outputstream.flush();
			} catch (Exception e) {
				throw new AppException("文件写入异常，可能是文件损坏或不存在!错误信息为："
						+ e.getMessage());
			} finally {
				try {
					if (outputstream != null) {
						outputstream.close();
					}
					if (inputstream != null) {
						inputstream.close();
					}
				} catch (Exception e) {
					throw new AppException("文件写入异常，可能是文件损坏或不存在!错误信息为："
							+ e.getMessage());
				}
			}
		}
	}

	/**
	 * 从指定文件中获取字节流数据
	 * 
	 * @author wf
	 * @throws AppException
	 * @throws IOException
	 * @date 创建时间 May 29, 2010
	 * @since V1.0
	 */
	public static byte[] getBytesFromFile(File file) throws AppException,
			IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			long length = file.length();
			byte[] bytes = new byte[(int) length];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}
			is.close();
			return bytes;
		} catch (FileNotFoundException e) {
			throw new AppException("没有找到指定文件.错误信息为：" + e.getMessage());
		} catch (IOException e) {
			throw new AppException("读取文件内容异常.错误信息为：" + e.getMessage());
		} finally {
			if (is != null)
				is.close();
		}
	}

	/**
	 * 向文件中写入二进制数据流
	 * 
	 * @author wf
	 * @date 创建时间 May 29, 2010
	 * @since V1.0
	 */
	public static void writeBytesToFile(byte[] bytes, File file)
			throws AppException {
		OutputStream out;
		try {
			out = new FileOutputStream(file);
			out.write(bytes);
			out.close();
		} catch (FileNotFoundException e) {
			throw new AppException("没有找到指定文件.错误信息为：" + e.getMessage());
		} catch (IOException e) {
			throw new AppException("读取文件内容异常.错误信息为：" + e.getMessage());
		}
	}

	public static String getImageStrByImage(File image) throws AppException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(image.getAbsolutePath());
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			throw new AppException("图片转成Base64编码时出错");
		}
		// 对字节数组Base64编码
		return SecUtil.base64Encode(data); // 返回Base64编码过的字节数组字符串
	}

	public static String getImageStrByMultipartImage(CommonsMultipartFile image)
			throws AppException {
		// 对字节数组Base64编码
		return SecUtil.base64Encode(image.getBytes()); // 返回Base64编码过的字节数组字符串
	}

	public static String getImageStrByBlob(Blob image) throws SQLException,
			AppException {
		int zbnrLen = (new BigDecimal(image.length())).intValue();
		byte[] filebyte = image.getBytes(1, zbnrLen);
		// 对字节数组Base64编码
		return SecUtil.base64Encode(filebyte); // 返回Base64编码过的字节数组字符串
	}

	public static byte[] getImageByImageStr(String imgStr) throws AppException { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return null;
		// Base64解码
		byte[] data = SecUtil.base64Decode(imgStr);
		for (int i = 0; i < data.length; i++) {
			if (data[i] < 0) { // 调整异常数据
				data[i] += 256;
			}
		}
		return data;
	}

}
