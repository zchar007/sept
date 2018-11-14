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
	 * д�ļ�
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @param fileContent
	 *            �ļ�����
	 * @param appended
	 *            �Ƿ�׷��
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
			throw new AppException("�����ļ�����:" + e.getMessage());
		}
		try {
			writer.write(fileContent);
		} catch (Exception e) {
			throw new AppException("д�ļ����ݴ���:" + e.getMessage());
		}
		try {
			writer.close();
		} catch (Exception e) {
			throw new AppException("�ر��ļ�����:" + e.getMessage());
		}
	}

	/**
	 * д�ļ�
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @param fileContent
	 *            �ļ�����
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
	 * ˵�������ļ����ŵ�resposne�С� ��Ҫ���ڴ����ݿ��ж�ȡ�ļ���
	 * 
	 * @author:֣���� Oct 20, 2008
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
				if (filename.length() > 150) {// ���IE 6.0 bug
					filename = new String(filename.getBytes("GBK"),
							"ISO-8859-1");
				} else {
					filename = URLEncoder.encode(filename, "UTF-8");
				}

				response.setContentType("application/x-download");
				response.addHeader("Content-Disposition",
						"attachment; filename=" + filename);
				toClient = response.getOutputStream(); // �õ���ͻ���������������ݵĶ���
				toClient.write(filebyte); // �������
				toClient.flush();
			} catch (IOException e) {
				// �û�ȡ������
			} finally {
				try {
					if (toClient != null) {
						toClient.close();
					}
				} catch (Exception e) {
					throw new AppException("�ļ���ȡ�쳣���������ļ��𻵻򲻴���!������ϢΪ��"
							+ e.getMessage());
				}
			}
		} else {
			throw new AppException("�ø���������");
		}
	}

	/**
	 * ˵����˵������Blob�ŵ�resposne��,��Ҫ���ڴ����ݿ��ж�ȡ�ļ���
	 * 
	 * @author:֣���� May 5, 2009
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
				throw new AppException("�ļ���ȡ�쳣���������ļ��𻵻򲻴���!������ϢΪ��"
						+ e.getMessage());
			}
		} else {
			throw new AppException("�ø���������");
		}
	}

	/**
	 * ˵�������������е��ļ����ص�����
	 * 
	 * @author:֣���� Oct 20, 2008
	 * @param response
	 * @param filepath
	 *            ���������е��ļ�·��
	 * @param filename
	 * @throws AppException
	 */
	public static void saveFileToResponse(String filepath, String filename,
			HttpServletResponse response) throws AppException {
		OutputStream outputstream = null; // ������
		FileInputStream inputstream = null;

		try {
			inputstream = new FileInputStream(filepath + File.separator
					+ filename);

			filename = URLEncoder.encode(filename, "UTF-8");
			response.reset();
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename);
			outputstream = response.getOutputStream(); // �õ���ͻ���������������ݵĶ���

			byte[] byteValue = new byte[1024];
			int tempValue = 0;
			while ((tempValue = inputstream.read(byteValue)) > 0) {
				outputstream.write(byteValue, 0, tempValue);
			}
			outputstream.flush();
		} catch (IOException e) {
			// �û�ȡ������
		} finally {
			try {
				if (outputstream != null) {
					outputstream.close();
				}
				if (inputstream != null) {
					inputstream.close();
				}
			} catch (Exception e) {
				throw new AppException("�ļ���ȡ�쳣���������ļ��𻵻򲻴���!������ϢΪ��"
						+ e.getMessage());
			}
		}
	}

	/**
	 * ˵�������ֽ���д�뵽�������˵��ļ��С�
	 * 
	 * @author:֣���� Oct 20, 2008
	 * @param filebyte
	 * @param filepath
	 *            :·����+�ļ���
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
			throw new AppException("�ļ���ȡ�쳣���������ļ��𻵻򲻴���!������ϢΪ��" + e.getMessage());
		} finally {
			try {
				if (outputstream != null) {
					outputstream.close();
				}
				if (inputstream != null) {
					inputstream.close();
				}
			} catch (Exception e) {
				throw new AppException("�ļ���ȡ�쳣���������ļ��𻵻򲻴���!������ϢΪ��"
						+ e.getMessage());
			}
		}
	}

	/**
	 * ˵�����������е��ļ��ϴ�����������
	 * 
	 * @author:֣���� Oct 20, 2008
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
			throw new AppException("�ļ�д���쳣���������ļ��𻵻򲻴���!������ϢΪ��" + e.getMessage());
		} finally {
			try {
				if (outputstream != null) {
					outputstream.close();
				}
				if (inputstream != null) {
					inputstream.close();
				}
			} catch (Exception e) {
				throw new AppException("�ļ�д���쳣���������ļ��𻵻򲻴���!������ϢΪ��"
						+ e.getMessage());
			}
		}
	}

	/**
	 * ˵�����������е��ĵ�д�����ݿ��е�blob�С�
	 * 
	 * @author:֣���� Oct 20, 2008
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
				throw new AppException("�ļ�д���쳣���������ļ��𻵻򲻴���!������ϢΪ��"
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
					throw new AppException("�ļ�д���쳣���������ļ��𻵻򲻴���!������ϢΪ��"
							+ e.getMessage());
				}
			}
		}
	}

	/**
	 * ˵�������������˵��ĵ�д�����ݿ��е�blob�С�
	 * 
	 * @author:֣���� Oct 20, 2008
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
				throw new AppException("�ļ�д���쳣���������ļ��𻵻򲻴���!������ϢΪ��"
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
					throw new AppException("�ļ�д���쳣���������ļ��𻵻򲻴���!������ϢΪ��"
							+ e.getMessage());
				}
			}
		}
	}

	/**
	 * ��ָ���ļ��л�ȡ�ֽ�������
	 * 
	 * @author wf
	 * @throws AppException
	 * @throws IOException
	 * @date ����ʱ�� May 29, 2010
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
			throw new AppException("û���ҵ�ָ���ļ�.������ϢΪ��" + e.getMessage());
		} catch (IOException e) {
			throw new AppException("��ȡ�ļ������쳣.������ϢΪ��" + e.getMessage());
		} finally {
			if (is != null)
				is.close();
		}
	}

	/**
	 * ���ļ���д�������������
	 * 
	 * @author wf
	 * @date ����ʱ�� May 29, 2010
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
			throw new AppException("û���ҵ�ָ���ļ�.������ϢΪ��" + e.getMessage());
		} catch (IOException e) {
			throw new AppException("��ȡ�ļ������쳣.������ϢΪ��" + e.getMessage());
		}
	}

	public static String getImageStrByImage(File image) throws AppException {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��
		InputStream in = null;
		byte[] data = null;
		// ��ȡͼƬ�ֽ�����
		try {
			in = new FileInputStream(image.getAbsolutePath());
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			throw new AppException("ͼƬת��Base64����ʱ����");
		}
		// ���ֽ�����Base64����
		return SecUtil.base64Encode(data); // ����Base64��������ֽ������ַ���
	}

	public static String getImageStrByMultipartImage(CommonsMultipartFile image)
			throws AppException {
		// ���ֽ�����Base64����
		return SecUtil.base64Encode(image.getBytes()); // ����Base64��������ֽ������ַ���
	}

	public static String getImageStrByBlob(Blob image) throws SQLException,
			AppException {
		int zbnrLen = (new BigDecimal(image.length())).intValue();
		byte[] filebyte = image.getBytes(1, zbnrLen);
		// ���ֽ�����Base64����
		return SecUtil.base64Encode(filebyte); // ����Base64��������ֽ������ַ���
	}

	public static byte[] getImageByImageStr(String imgStr) throws AppException { // ���ֽ������ַ�������Base64���벢����ͼƬ
		if (imgStr == null) // ͼ������Ϊ��
			return null;
		// Base64����
		byte[] data = SecUtil.base64Decode(imgStr);
		for (int i = 0; i < data.length; i++) {
			if (data[i] < 0) { // �����쳣����
				data[i] += 256;
			}
		}
		return data;
	}

}
