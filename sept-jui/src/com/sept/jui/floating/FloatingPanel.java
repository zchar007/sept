package com.sept.jui.floating;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class FloatingPanel extends JFrame implements WindowFocusListener, MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pressX;
	private int pressY;
	private boolean moveAble = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FloatingPanel frame = new FloatingPanel(new Point(10, 10), true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(0, 0, getWidth() - 2, getHeight() - 2);
	}

	public FloatingPanel(Point p) {
		this(p, false);
	}

	/**
	 * Create the frame.
	 */
	public FloatingPanel(Point p, boolean moveAble) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(p.x, p.y, 450, 300);
		this.setUndecorated(true);
		this.addWindowFocusListener(this);
		this.setMoveAble(moveAble);
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {

	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		this.setVisible(false);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation((int) (e.getXOnScreen() - this.pressX), (int) (e.getYOnScreen() - this.pressY));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.pressX = e.getX();
		this.pressY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean isMoveAble() {
		return moveAble;
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
