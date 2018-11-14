package com.sept.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.sept.exception.AppException;

/**
 * ������:PDF������
 * 
 * @author ������ 2015-9-10
 */
public class PDFUtil{
	/**
	 * �������.����������ȡ��д��Document
	 * 
	 * @author ������ 2015-9-6
	 */
	public static Document createDocument(ByteArrayOutputStream outputstream) throws AppException {
		// ����һ���ĵ�����
		Document doc = new Document();

		// ��������ļ���λ��
		try {
			PdfWriter.getInstance(doc, outputstream);
		} catch (Exception e) {
			throw new AppException("����pdf�ĵ��������!������ϢΪ��" + e.getMessage());
		}

		doc.open();
		return doc;
	}

	/**
	 * �������.����һ����д��pdf�ĵ�����
	 * 
	 * @author ������ 2015-9-6
	 */
	public static Document createDocument(String filepath, String filename) throws AppException {
		// ����һ���ĵ�����
		Document doc = new Document();

		OutputStream outputstream = null;
		try {
			outputstream = new FileOutputStream(filepath + File.separator + filename);
		} catch (Exception e) {
			throw new AppException("��ȡ�ļ�����!������ϢΪ��" + e.getMessage());
		}

		// ��������ļ���λ��
		try {
			PdfWriter.getInstance(doc, outputstream);
		} catch (Exception e) {
			throw new AppException("����pdf�ĵ��������!������ϢΪ��" + e.getMessage());
		}

		doc.open();
		return doc;
	}

	/**
	 * �������.��pdf�ĵ����������ͼƬ
	 * 
	 * @author ������ 2015-9-6
	 */
	public static void addImage(Document doc, byte[] fileByte) throws AppException {

		// ����һ��ͼƬ����
		Image img = null;
		try {
			img = Image.getInstance(fileByte);
		} catch (Exception e) {
			throw new AppException("��ʼ��ͼƬ����ʱ����!������ϢΪ��" + e.getMessage());
		}
        
		// ����ͼƬ������ʾ
		img.setAlignment(Image.LEFT);

		// ����ͼƬ̫������
		float width=doc.getPageSize().getWidth()-70;
        if(img.getWidth()>width){  
        	img.scalePercent(width/img.getWidth()*100);//��ҳ����ʾ��ȳ���ͼƬ��������С�ĺ��ʰٷֱ�  
        }  
        
		// ���ͼƬ
		try {
			doc.add(img);
			doc.add(new Paragraph(""));// ����
		} catch (Exception e) {
			throw new AppException("pdf�ļ������ͼƬʱ����!������ϢΪ��" + e.getMessage());
		}
	}

	/**
	 * �������.��pdf�ĵ����������ͼƬ���Զ����ų�pdfҳ���С
	 * 
	 * @author ��˷� 2015-10-22
	 */
	public static void addImageFitPageSize(Document doc, byte[] fileByte) throws AppException {

		// ����һ��ͼƬ����
		Image img = null;
		try {
			img = Image.getInstance(fileByte);
		} catch (Exception e) {
			throw new AppException("��ʼ��ͼƬ����ʱ����!������ϢΪ��" + e.getMessage());
		}
        

		//������ڸ�ʱ����ת90��
		if(img.getWidth() > img.getHeight()){
			img.setRotationDegrees(90);
		}
		
		// ����ͼƬ������ʾ
		img.setAlignment(Image.ALIGN_MIDDLE);
		img.setAlignment(Image.ALIGN_CENTER);
		
		if (img.getWidth() > doc.getPageSize().getWidth()
				|| img.getHeight() > doc.getPageSize().getHeight()) {
			img.scaleToFit(doc.getPageSize().getWidth(), doc.getPageSize()
				.getHeight());
		}
		
		img.setAbsolutePosition(0,0);
        
		// ���ͼƬ
		try {
			doc.add(img);
			doc.newPage();
		} catch (Exception e) {
			throw new AppException("pdf�ļ������ͼƬʱ����!������ϢΪ��" + e.getMessage());
		}
	}
	
	/**
	 * �������.�رն�ȡ��pdf�ļ�.
	 * 
	 * @author ������ 2015-9-6
	 */
	public static void closeDocument(Document doc) throws AppException {
		try {
			doc.close();
		} catch (Exception e) {
			throw new AppException("�ļ���ȡ�쳣���ر�pdf�ļ���ʱ����!������ϢΪ��" + e.getMessage());
		}
	}

	/**
	 * �������.���Է���
	 * 
	 * @author ������ 2015-9-11
	 */
	public static void main(String[] args) {
		try {
			// ������Ƭ
//			byte[] data1 = FileIOTool.getBytesFromFile(new File("c:/1.jpg"));
//			
//			// ����һ���ĵ�����
//			ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
//			Document doc = PDFUtil.createDocument(outputstream);
//			doc.setMargins(0, 0, 0, 0);
//			
//			// ѹ����ͼƬ��ӵ�pdf��
//			PDFUtil.addImageFitPageSize(doc, data1);
//			// �ر��ĵ�����
//			PDFUtil.closeDocument(doc);
//			
//			byte[] bytes = outputstream.toByteArray();
//			FileIOTool.writeByteToServer(bytes, "C:/", "bytepdf.pdf");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
