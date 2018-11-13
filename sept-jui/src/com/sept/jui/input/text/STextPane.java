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

		pop_main.add(menuItem_copy = new JMenuItem("复制"));
		pop_main.add(menuItem_paste = new JMenuItem("粘贴"));
		pop_main.add(menuItem_cut = new JMenuItem("剪切"));
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
	 * 设置拷贝按钮是否可用
	 * 
	 * @param isEnabled
	 */
	public void setCopyEnabled(boolean isEnabled) {
		menuItem_copy.setEnabled(isEnabled);
	}

	/**
	 * 设置粘贴按钮是否可用
	 * 
	 * @param isEnabled
	 */
	public void setPasteEnabled(boolean isEnabled) {
		menuItem_paste.setEnabled(isEnabled);
	}

	/**
	 * 设置剪切按钮是否可用
	 * 
	 * @param isEnabled
	 */
	public void setCutEnabled(boolean isEnabled) {
		menuItem_cut.setEnabled(isEnabled);
	}

	/**
	 * 增加item
	 * 
	 * @param item
	 */
	public void addMenuItem(JMenuItem item) {
		pop_main.add(item);
	}

	/**
	 * 增加menu
	 * 
	 * @param menu
	 */
	public void addMenu(JMenu menu) {
		pop_main.add(menu);
	}

	/**
	 * 获取底层JTextPane,一般用户到
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
	 * 剪切板中是否有文本数据可供粘贴
	 * 
	 * @return true为有文本数据
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
	 * 文本组件中是否具备复制的条件
	 * 
	 * @return true为具备
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
