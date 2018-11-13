package com.sept.jui.floating;

import com.sept.jui.exception.JUIException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class FloatingPanel extends JComponent implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private int pressX;
	private int pressY;
	private boolean moveAble = false;

	public FloatingPanel() {
		setLayout(new BorderLayout(0, 0));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		setOpaque(false);
		this.pressX = e.getX();
		this.pressY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		if ((getParent() instanceof JPanel)) {
			setLocation((int) (e.getXOnScreen() - getParent().getLocationOnScreen().getX() - this.pressX),
					(int) (e.getYOnScreen() - getParent().getLocationOnScreen().getY() - this.pressY));
		} else {
			try {
				throw new JUIException("FloatingComponent ¸¸ÈÝÆ÷±ØÐëÊÇJPanel!");
			} catch (JUIException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public boolean isMoveAble() {
		return this.moveAble;
	}

	public void setMoveAble(boolean moveAble) {
		this.moveAble = moveAble;
		if (isMoveAble()) {
			addMouseListener(this);
			addMouseMotionListener(this);
		} else {
			removeMouseListener(this);
			removeMouseMotionListener(this);
		}
	}
}
