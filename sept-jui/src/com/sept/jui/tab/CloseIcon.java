package com.sept.jui.tab;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;

public class CloseIcon implements Icon {
	private int width;
	private int height;
	private Icon fileIcon;
	private Color color = Color.BLACK;
	private int x_pos;
	private int y_pos;

	public CloseIcon(Icon icon) {
		this.fileIcon = icon;
		width = 16;
		height = 16;
	}

	public CloseIcon(Color color) {
		this.color = color;
		width = 16;
		height = 16;
	}

	public CloseIcon() {
		width = 16;
		height = 16;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		x += 50;
		this.x_pos = x;
		this.y_pos = y;
		// Color col = g.getColor();
		g.setColor(Color.black);
		int y_p = y + 2;
		g.drawLine(x + 1, y_p, x + 12, y_p);
		g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);
		g.drawLine(x, y_p + 1, x, y_p + 12);
		g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);
		g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
		g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
		g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
		g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
		g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
		g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);

		g.setColor(color);
		if (fileIcon != null) {
			fileIcon.paintIcon(c, g, x + width, y_p);
		}
	}

	@Override
	public int getIconWidth() {
		return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
	}

	@Override
	public int getIconHeight() {
		return height;
	}

	public Rectangle getBounds() {
		return new Rectangle(x_pos, y_pos, width, height);
	}
}