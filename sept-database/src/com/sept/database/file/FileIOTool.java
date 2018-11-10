package com.sept.database.file;

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


import org.apache.commons.io.IOUtils;

import com.sept.framework.exception.Alert;
import com.sept.framework.exception.AppException;
import com.sept.framework.exception.ExceptionCode;
import com.sept.framework.util.SecUtil;
import com.sept.framework.util.data.DataObject;

import oracle.sql.BLOB;

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
			Alert.FileError("�����ļ�����:" + e.getMessage());
		}
		try {
			writer.write(fileContent);
		} catch (Exception e) {
			Alert.FileError("д�ļ����ݴ���:" + e.getMessage());
		}
		try {
			writer.close();
		} catch (Exception e) {
			Alert.FileError("�ر��ļ�����:" + e.getMessage());
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

	/**
	 * ˵�������ֽ���д�뵽�������˵��ļ��С�
	 * 
	 * @author:ZC Oct 20, 2008
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
			Alert.FileError("�ļ���ȡ�쳣���������ļ��𻵻򲻴���!������ϢΪ��" + e.getMessage());
		} finally {
			try {
				if (outputstream != null) {
					outputstream.close();
				}
				if (inputstream != null) {
					inputstream.close();
				}
			} catch (Exception e) {
				Alert.FileError("�ļ���ȡ�쳣���������ļ��𻵻򲻴���!������ϢΪ��" + e.getMessage());
			}
		}
	}

	/**
	 * ˵�������������˵��ĵ�д�����ݿ��е�blob�С�
	 * 
	 * @author:ZC Oct 20, 2008
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
				Alert.FileError("�ļ�д���쳣���������ļ��𻵻򲻴���!������ϢΪ��" + e.getMessage());
			} finally {
				try {
					if (outputstream != null) {
						outputstream.close();
					}
					if (inputstream != null) {
						inputstream.close();
					}
				} catch (Exception e) {
					Alert.FileError("�ļ�д���쳣���������ļ��𻵻򲻴���!������ϢΪ��"
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
	public static byte[] getBytesFromFile(File file) throws AppException, IOException {
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
			Alert.FileError("û���ҵ�ָ���ļ�.������ϢΪ��" + e.getMessage());
		} catch (IOException e) {
			Alert.IOError("��ȡ�ļ������쳣.������ϢΪ��" + e.getMessage());
		}finally{
			if(is !=null)
				is.close();
		}
		return null;
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
			Alert.FileError("û���ҵ�ָ���ļ�.������ϢΪ��" + e.getMessage());
		} catch (IOException e) {
			Alert.IOError("��ȡ�ļ������쳣.������ϢΪ��" + e.getMessage());
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
			throw new AppException(ExceptionCode.IOError, "ͼƬת��Base64����ʱ����");
		}
		// ���ֽ�����Base64����
		return SecUtil.base64Encode(data); // ����Base64��������ֽ������ַ���
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
