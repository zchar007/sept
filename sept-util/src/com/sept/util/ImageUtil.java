package com.sept.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

/**
 * @author zchar
 * 
 */
public class ImageUtil {
	/**
	 * d等比例放大缩小图片，按照给定的xy比例以及大小
	 * 
	 * @param img
	 * @param sizeX
	 * @param sizeY
	 * @return
	 * @throws IOException
	 */
	public static Image resizeImage(Image img, int sizeX, int sizeY) throws IOException {
		if (img == null)
			return null;
		BufferedImage bfi = toBufferedImage(img);

		Builder<BufferedImage> bbf = Thumbnails.of(bfi).size(sizeX, sizeY);
		bfi = bbf.asBufferedImage();

		return bfi;
	}

	/**
	 * image转BufferedImage
	 * 
	 * @param image
	 * @return
	 */
	public static BufferedImage toBufferedImage(Image image) {
		if (image == null)
			return null;
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		image = new ImageIcon(image).getImage();

		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			int transparency = Transparency.OPAQUE;

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	/**
	 * 截取图片
	 * 
	 * @param img
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public static BufferedImage subImage(Image img, int x, int y, int w, int h) {
		return subImage(toBufferedImage(img), x, y, w, h);
	}

	/**
	 * 截取图片
	 * 
	 * @param img
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public static BufferedImage subImage(BufferedImage img, int x, int y, int w, int h) {
		return img.getSubimage(x, y, w, h);
	}

	/**
	 * 获取index级别的模糊图片（毛玻璃效果）
	 * 
	 * @param url
	 * @param index
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getGsPic(String url, int index) throws IOException {
		File file = new File(url);
		BufferedImage bImage = getGsPic(ImageIO.read(file));
		for (int i = 0; i < index; i++) {
			bImage = getGsPic(bImage);
		}
		return bImage;
	}

	/**
	 * 获取index级别的模糊图片（毛玻璃效果）
	 * 
	 * @param url
	 * @param index
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getGsPic(BufferedImage img, int index) throws IOException {
		BufferedImage bImage = getGsPic(img);
		for (int i = 0; i < index; i++) {
			bImage = getGsPic(bImage);
		}
		return bImage;
	}

	/**
	 * 默认获取一级的pic
	 * 
	 * @param img
	 * @return
	 */
	public static BufferedImage getGsPic(BufferedImage img) {
		HashMap<String, int[]> hMap = new HashMap<String, int[]>();
		int width = img.getWidth();
		int height = img.getHeight();
		int minx = img.getMinX();
		int miny = img.getMinY();
		BufferedImage imgWriter = new BufferedImage(width, height, img.getType());
		Graphics2D g2d = (Graphics2D) imgWriter.getGraphics();
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				int pixel = img.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
				int[] rgb = new int[3];
				rgb[0] = (pixel & 0xff0000) >> 16;
				rgb[1] = (pixel & 0xff00) >> 8;
				rgb[2] = (pixel & 0xff);
				hMap.put(i + "_" + j, rgb);
			}
		}

		ArrayList<int[]> al = new ArrayList<int[]>();
		for (String key : hMap.keySet()) {
			al.clear();
			String[] str = key.split("_");
			int i = Integer.parseInt(str[0]);
			int j = Integer.parseInt(str[1]);
			// 上
			al.add(hMap.get(i + "_" + (j - 1 < 0 ? 0 : j - 1)));
			// 下
			al.add(hMap.get(i + "_" + (j + 1 >= height ? j : j + 1)));
			// 左
			al.add(hMap.get((i - 1 < 0 ? 0 : i - 1) + "_" + j));
			// 右
			al.add(hMap.get((i + 1 >= width ? i : i + 1) + "_" + j));
			// 左上
			al.add(hMap.get((i - 1 < 0 ? 0 : i - 1) + "_" + (j - 1 < 0 ? 0 : j - 1)));
			// 右上
			al.add(hMap.get((i + 1 >= width ? i : i + 1) + "_" + (j - 1 < 0 ? 0 : j - 1)));
			// 左下
			al.add(hMap.get((i - 1 < 0 ? 0 : i - 1) + "_" + (j + 1 >= height ? j : j + 1)));
			// 右下
			al.add(hMap.get((i + 1 >= width ? i : i + 1) + "_" + (j + 1 >= height ? j : j + 1)));
			// 加上自己
			al.add(hMap.get(i + "_" + j));
			int[] rgb = getAvageRGB(al);
			// int[] rgb = hMap.get(i+"_"+j);
			g2d.setColor(new Color(rgb[0], rgb[1], rgb[2]));
			g2d.drawLine(i, j, i, j);
		}
		return imgWriter;

	}

	/**
	 * 获取颜色的平均值
	 */
	public static int[] getAvageRGB(ArrayList<int[]> al) {
		int R = 0, G = 0, B = 0;
		for (int[] RGB : al) {
			R += RGB[0];
			G += RGB[1];
			B += RGB[2];
		}
		int[] rgb = new int[3];
		rgb[0] = R / al.size();
		rgb[1] = G / al.size();
		rgb[2] = B / al.size();

		return rgb;
	}
	
	
	

}
