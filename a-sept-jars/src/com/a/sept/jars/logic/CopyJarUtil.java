package com.a.sept.jars.logic;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.a.sept.jars.ui.JarCopyFrame;
import com.sept.exception.AppException;

public class CopyJarUtil {
	public static void main(String[] args) {
		// System.out.println(parseClasspath("D:\\zchar\\workspace_20181206\\sept\\sept-database\\.classpath"));

	}

	public static final boolean copyJars(String classUrl) throws AppException, IOException {
		ArrayList<String> alJars = parseClasspath(classUrl);
		File directory = new File("");
		String fromPath = directory.getAbsolutePath() + File.separator + "lib" + File.separator;

		String toPath = new File(classUrl).getParentFile().getAbsolutePath() + File.separator + "lib" + File.separator;
		File file = new File(toPath);
		//System.out.println(toPath+file.exists());
		if(!file.exists()) {
			file.mkdirs();
		}
		for (int i = 0; i < alJars.size(); i++) {
			JarCopyFrame.appendMessage("���ڿ���["+alJars.get(i)+"]");
			byte[] datas = getBytesFromFile(new File(fromPath + alJars.get(i)));
			writeBytesToFile(datas, toPath, alJars.get(i));
			JarCopyFrame.appendMessage("�������["+alJars.get(i)+"]");
		}

		return true;
	}

	/**
	 * 
	 * @param classUrl
	 * @return abspath:file
	 */
	private static final ArrayList<String> parseClasspath(String classUrl) {
		ArrayList<String> alReturn = new ArrayList<String>();
		// ����һ��DocumentBuilderFactory�Ķ���
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// ����һ��DocumentBuilder�Ķ���
		try {
			// ����DocumentBuilder����
			DocumentBuilder db = dbf.newDocumentBuilder();
			// ͨ��DocumentBuilder�����parser��������books.xml�ļ�����ǰ��Ŀ��
			Document document = db.parse(classUrl);
			NodeList jars = document.getElementsByTagName("classpathentry");
			for (int i = 0; i < jars.getLength(); i++) {
				Node jar = jars.item(i);
				NamedNodeMap nnm = jar.getAttributes();
				String kind = nnm.getNamedItem("kind").getNodeValue();
				String path = nnm.getNamedItem("path").getNodeValue();
				if (kind.equals("lib")) {
					String jarName = path.substring(path.lastIndexOf(File.separator) + 1, path.length());
					jarName = jarName.substring(jarName.lastIndexOf("/") + 1, jarName.length());
					alReturn.add(jarName);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return alReturn;
	}

	/**
	 * ˵�������ֽ���д�뵽�������˵��ļ��С�
	 * 
	 * @author:ZC Oct 20, 2008
	 * @param filebyte
	 * @param filepath :·����+�ļ���
	 * @throws AppException
	 */
	public static void writeBytesToFile(byte[] filebyte, String filepath, String filename) throws AppException {
		ByteArrayInputStream inputstream = null;
		FileOutputStream outputstream = null;
		try {
			if (filename != null && !filename.equals("")) {
				File file = new File(filepath + File.separator + filename);
				if (file.exists()) {
					file.delete();
				}
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
				throw new AppException("�ļ���ȡ�쳣���������ļ��𻵻򲻴���!������ϢΪ��" + e.getMessage());
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
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file " + file.getName());
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

}
