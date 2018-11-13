package com.sept.jui.input.text;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.BorderLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

public class STextPane extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private JPopupMenu pop_main;
	private JMenuItem menuItem_copy;
	private JMenuItem menuItem_paste;
	private JMenuItem menuItem_cut;

	/**
	 * Create the panel.
	 */
	public STextPane() {
		setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		textPane = new JTextPane();
		textPane.addMouseListener(this);
		scrollPane.setViewportView(textPane);
		this.initPop();
	}

	private void initPop() {
		pop_main = new JPopupMenu();

		pop_main.add(menuItem_copy = new JMenuItem("����"));
		pop_main.add(menuItem_paste = new JMenuItem("ճ��"));
		pop_main.add(menuItem_cut = new JMenuItem("����"));
		menuItem_copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		menuItem_paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		menuItem_cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));

		menuItem_copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.copy();
			}
		});
		menuItem_paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.paste();
			}
		});
		menuItem_cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.cut();
			}
		});

		textPane.add(pop_main);
	}

	/**
	 * ���ÿ�����ť�Ƿ����
	 * 
	 * @param isEnabled
	 */
	public void setCopyEnabled(boolean isEnabled) {
		menuItem_copy.setEnabled(isEnabled);
	}

	/**
	 * ����ճ����ť�Ƿ����
	 * 
	 * @param isEnabled
	 */
	public void setPasteEnabled(boolean isEnabled) {
		menuItem_paste.setEnabled(isEnabled);
	}

	/**
	 * ���ü��а�ť�Ƿ����
	 * 
	 * @param isEnabled
	 */
	public void setCutEnabled(boolean isEnabled) {
		menuItem_cut.setEnabled(isEnabled);
	}

	/**
	 * ����item
	 * 
	 * @param item
	 */
	public void addMenuItem(JMenuItem item) {
		pop_main.add(item);
	}

	/**
	 * ����menu
	 * 
	 * @param menu
	 */
	public void addMenu(JMenu menu) {
		pop_main.add(menu);
	}

	/**
	 * ��ȡ�ײ�JTextPane,һ���û���
	 * 
	 * @return
	 */
	public JTextPane getJTextPane() {
		return textPane;
	}

	public String getSelectText() {
		return textPane.getSelectedText();
	}

	/**
	 * ���а����Ƿ����ı����ݿɹ�ճ��
	 * 
	 * @return trueΪ���ı�����
	 */
	protected boolean isCanPaste() {
		boolean b = false;
		Clipboard clipboard = this.getToolkit().getSystemClipboard();
		Transferable content = clipboard.getContents(this);
		try {
			if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {
				b = true;
			}
		} catch (Exception e) {
		}
		return b;
	}

	/**
	 * �ı�������Ƿ�߱����Ƶ�����
	 * 
	 * @return trueΪ�߱�
	 */
	protected boolean isCanCopy() {
		boolean b = false;
		int start = textPane.getSelectionStart();
		int end = textPane.getSelectionEnd();
		if (start != end)
			b = true;
		return b;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			menuItem_copy.setEnabled(isCanCopy());
			menuItem_paste.setEnabled(isCanPaste());
			menuItem_cut.setEnabled(isCanCopy());
			pop_main.show(this, e.getX(), e.getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
}
