package com.sept.util.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.imageio.ImageIO;

import com.lowagie.text.pdf.Barcode128;
import com.sept.exception.AppException;

public class BarCodeUtil {
	/**
	 * 获取不带字符串的条码byte
	 * 
	 * @param barCodeWidth
	 * @param barCodeHeight
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static final String getBarBase64(int barCodeWidth, int barCodeHeight, String code) throws Exception {
		String base64ImgStr = Base64Util.encode(getBarBytes(barCodeWidth, barCodeHeight, code));
		return base64ImgStr;
	}

	/**
	 * 获取不带字符串的条码byte
	 * 
	 * @param barCodeWidth
	 * @param barCodeHeight
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static final byte[] getBarBytes(int barCodeWidth, int barCodeHeight, String code) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(getBarImg(barCodeWidth, barCodeHeight, code), "JPEG", out);
		byte[] data = out.toByteArray();
		out = null;
		return data;
	}

	/**
	 * 获取不带字符串的条码BufferedImage
	 * 
	 * @param barCodeWidth
	 * @param barCodeHeight
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static final BufferedImage getBarImg(int barCodeWidth, int barCodeHeight, String code) throws Exception {
		Barcode128 barcode128 = new Barcode128();
		barcode128.setCode(code);
		Image codeImg = barcode128.createAwtImage(Color.black, Color.white);

		// 设置图像大小
		codeImg = codeImg.getScaledInstance(barCodeWidth, barCodeHeight, Image.SCALE_DEFAULT);
		// 内存中创建图像
		BufferedImage bufferImage = new BufferedImage(barCodeWidth, barCodeHeight, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics2D graph = (Graphics2D) bufferImage.getGraphics();
		// 绘制图像到目标位置
		graph.drawImage(codeImg, 0, 0, codeImg.getWidth(null), codeImg.getHeight(null), 0, 0, barCodeWidth,
				barCodeHeight, null);
		// 销毁Graphics
		graph.dispose();

		graph = null;
		barcode128 = null;

		return bufferImage;
	}

	/**
	 * 获取不带字符串的条码byte
	 * 
	 * @param barCodeWidth
	 * @param barCodeHeight
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static final String getBarWithCodeBase64(int barCodeWidth, int barCodeHeight, String code) throws Exception {
		String base64ImgStr = Base64Util.encode(getBarWithCodeBytes(barCodeWidth, barCodeHeight, code));
		return base64ImgStr;
	}

	/**
	 * 获取不带字符串的条码byte
	 * 
	 * @param barCodeWidth
	 * @param barCodeHeight
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static final byte[] getBarWithCodeBytes(int barCodeWidth, int barCodeHeight, String code) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(getBarWithCodeImage(barCodeWidth, barCodeHeight, code), "JPEG", out);
		byte[] data = out.toByteArray();
		out = null;
		return data;
	}

	/**
	 * 获得条形码图片带字符串
	 * 
	 * @param barCodeWidth
	 * @param barCodeHeight
	 * @param code
	 * @return
	 * @throws Exception
	 * @author sgs 2010-09-24
	 */
	public static BufferedImage getBarWithCodeImage(int barCodeWidth, int barCodeHeight, String code) throws Exception {

		int imageWidth = barCodeWidth;
		int imageHeight = barCodeHeight + 45;

		BufferedImage bufferImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graph = (Graphics2D) bufferImage.getGraphics();
		graph.fillRect(0, 0, imageWidth, imageHeight);
		Font font = new java.awt.Font("abc", java.awt.Font.PLAIN, 24);
		FontRenderContext fontRenderContext = graph.getFontRenderContext();

		int codeWidth = (int) font.getStringBounds(code, fontRenderContext).getWidth();
		int codeHeight = (int) font.getStringBounds(code, fontRenderContext).getHeight();

		Barcode128 barcode128 = new Barcode128();
		barcode128.setCode(code);
		Image codeImg = barcode128.createAwtImage(Color.black, Color.white);
		graph.drawImage(codeImg, 0, 0, barCodeWidth, barCodeHeight, Color.white, null);

		// 为图片添加条形码（文字），位置为条形码图片的下部居中
		AttributedString ats = new AttributedString(code);
		ats.addAttribute(TextAttribute.FONT, font, 0, code.length());
		AttributedCharacterIterator iter = ats.getIterator();
		// 设置条形码（文字）的颜色为黑色
		graph.setColor(Color.black);
		// 绘制条形码（文字）
		graph.drawString(iter, (barCodeWidth - codeWidth) / 2, barCodeHeight + codeHeight);
		graph.dispose();

		bufferImage = null;
		graph = null;
		barcode128 = null;
		font = null;
		fontRenderContext = null;
		codeImg = null;
		ats = null;
		iter = null;
		return bufferImage;
	}

	/**
	 * 获取39条码byte
	 * 
	 * @param barCodeWidth
	 * @param barCodeHeight
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static final String get39BarBase64(int barCodeWidth, int barCodeHeight, String code) throws Exception {
		String base64ImgStr = Base64Util.encode(get39BarBytes(barCodeWidth, barCodeHeight, code));
		return base64ImgStr;
	}

	/**
	 * 获取39条码byte
	 * 
	 * @param barCodeWidth
	 * @param barCodeHeight
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static final byte[] get39BarBytes(int barCodeWidth, int barCodeHeight, String code) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(get39BarImg(barCodeWidth, barCodeHeight, code), "JPEG", out);
		byte[] data = out.toByteArray();
		out = null;
		return data;
	}

	/**
	 * 获取39条形码
	 * 
	 * @param barCodeWidth
	 * @param barCodeHeight
	 * @param code
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static BufferedImage get39BarImg(int barCodeWidth, int barCodeHeight, String code)
			throws IOException, AppException {
		if (null == code || 0 == code.length()) {
			return null;
		} else {
			code = "*" + code + "*";
		}

		int rate = 3;
		int nImageWidth = (code.length() * (3 * rate + 7) * barCodeWidth) + 20;

		BufferedImage bi = new BufferedImage(nImageWidth, barCodeHeight + 15, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, nImageWidth, barCodeHeight);

		g.setColor(Color.BLACK);
		int startx = 10;
		for (int i = 0; i < code.length(); i++) {
			short sCode = getCharCode(code.charAt(i));
			for (int j = 0; j < 9; j++) {
				int width = barCodeWidth;

				if (((0x100 >>> j) & sCode) != 0) {
					width *= rate;
				}

				if ((j & 0x1) == 0) {
					g.fillRect(startx, 10, width, barCodeHeight);
				}

				startx += width;
			}
			startx = startx + barCodeWidth;
		}

		g.setColor(Color.WHITE);
		g.fillRect(0, barCodeHeight, nImageWidth, 15);
		g.setColor(Color.BLACK);
		g.drawString(code, (nImageWidth - code.length() * 5) / 2, barCodeHeight + 12);
		return bi;
	}

	/**
	 * ???
	 * 
	 * @param ch
	 * @return
	 */
	private static final short getCharCode(char ch) {
		char[] m_chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
				'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '-', '.', ' ',
				'*', '$', '/', '+', '%' };

		short[] m_codes = { 0x34, 0x121, 0x61, 0x160, 0x31, 0x130, 0x70, 0x25, 0x124, 0x64, 0x109, 0x49, 0x148, 0x19,
				0x118, 0x58, 0xd, 0x10c, 0x4c, 0x1c, 0x103, 0x43, 0x142, 0x13, 0x112, 0x52, 0x7, 0x106, 0x46, 0x16,
				0x181, 0xc1, 0x1c0, 0x91, 0x190, 0xd0, 0x85, 0x184, 0xc4, 0x94, 0xa8, 0xa2, 0x8a, 0x2a };
		for (int i = 0; i < m_chars.length; i++) {
			if (ch == m_chars[i])
				return m_codes[i];
		}
		return 0;
	}
}
