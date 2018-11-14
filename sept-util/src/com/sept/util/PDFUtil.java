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
 * 类描述:PDF工具类
 * 
 * @author 邵玉先 2015-9-10
 */
public class PDFUtil{
	/**
	 * 方法简介.根据流来获取可写的Document
	 * 
	 * @author 邵玉先 2015-9-6
	 */
	public static Document createDocument(ByteArrayOutputStream outputstream) throws AppException {
		// 创建一个文档对象
		Document doc = new Document();

		// 定义输出文件的位置
		try {
			PdfWriter.getInstance(doc, outputstream);
		} catch (Exception e) {
			throw new AppException("创建pdf文档对象出错!错误信息为：" + e.getMessage());
		}

		doc.open();
		return doc;
	}

	/**
	 * 方法简介.创建一个可写的pdf文档对象
	 * 
	 * @author 邵玉先 2015-9-6
	 */
	public static Document createDocument(String filepath, String filename) throws AppException {
		// 创建一个文档对象
		Document doc = new Document();

		OutputStream outputstream = null;
		try {
			outputstream = new FileOutputStream(filepath + File.separator + filename);
		} catch (Exception e) {
			throw new AppException("读取文件出错!错误信息为：" + e.getMessage());
		}

		// 定义输出文件的位置
		try {
			PdfWriter.getInstance(doc, outputstream);
		} catch (Exception e) {
			throw new AppException("创建pdf文档对象出错!错误信息为：" + e.getMessage());
		}

		doc.open();
		return doc;
	}

	/**
	 * 方法简介.向pdf文档对象中添加图片
	 * 
	 * @author 邵玉先 2015-9-6
	 */
	public static void addImage(Document doc, byte[] fileByte) throws AppException {

		// 创建一个图片对象
		Image img = null;
		try {
			img = Image.getInstance(fileByte);
		} catch (Exception e) {
			throw new AppException("初始化图片对象时出错!错误信息为：" + e.getMessage());
		}
        
		// 设置图片居中显示
		img.setAlignment(Image.LEFT);

		// 处理图片太大的情况
		float width=doc.getPageSize().getWidth()-70;
        if(img.getWidth()>width){  
        	img.scalePercent(width/img.getWidth()*100);//用页面显示宽度除以图片宽度算出缩小的合适百分比  
        }  
        
		// 添加图片
		try {
			doc.add(img);
			doc.add(new Paragraph(""));// 换行
		} catch (Exception e) {
			throw new AppException("pdf文件中添加图片时出错!错误信息为：" + e.getMessage());
		}
	}

	/**
	 * 方法简介.向pdf文档对象中添加图片，自动缩放成pdf页面大小
	 * 
	 * @author 铉克峰 2015-10-22
	 */
	public static void addImageFitPageSize(Document doc, byte[] fileByte) throws AppException {

		// 创建一个图片对象
		Image img = null;
		try {
			img = Image.getInstance(fileByte);
		} catch (Exception e) {
			throw new AppException("初始化图片对象时出错!错误信息为：" + e.getMessage());
		}
        

		//当宽大于高时，旋转90°
		if(img.getWidth() > img.getHeight()){
			img.setRotationDegrees(90);
		}
		
		// 设置图片居中显示
		img.setAlignment(Image.ALIGN_MIDDLE);
		img.setAlignment(Image.ALIGN_CENTER);
		
		if (img.getWidth() > doc.getPageSize().getWidth()
				|| img.getHeight() > doc.getPageSize().getHeight()) {
			img.scaleToFit(doc.getPageSize().getWidth(), doc.getPageSize()
				.getHeight());
		}
		
		img.setAbsolutePosition(0,0);
        
		// 添加图片
		try {
			doc.add(img);
			doc.newPage();
		} catch (Exception e) {
			throw new AppException("pdf文件中添加图片时出错!错误信息为：" + e.getMessage());
		}
	}
	
	/**
	 * 方法简介.关闭读取的pdf文件.
	 * 
	 * @author 邵玉先 2015-9-6
	 */
	public static void closeDocument(Document doc) throws AppException {
		try {
			doc.close();
		} catch (Exception e) {
			throw new AppException("文件读取异常，关闭pdf文件流时出错!错误信息为：" + e.getMessage());
		}
	}

	/**
	 * 方法简介.测试方法
	 * 
	 * @author 邵玉先 2015-9-11
	 */
	public static void main(String[] args) {
		try {
			// 读入照片
//			byte[] data1 = FileIOTool.getBytesFromFile(new File("c:/1.jpg"));
//			
//			// 创建一个文档对象
//			ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
//			Document doc = PDFUtil.createDocument(outputstream);
//			doc.setMargins(0, 0, 0, 0);
//			
//			// 压缩后图片添加到pdf中
//			PDFUtil.addImageFitPageSize(doc, data1);
//			// 关闭文档对象
//			PDFUtil.closeDocument(doc);
//			
//			byte[] bytes = outputstream.toByteArray();
//			FileIOTool.writeByteToServer(bytes, "C:/", "bytepdf.pdf");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
