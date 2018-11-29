package com.sept.demo.filetree;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.sept.datastructure.tree.STree;
import com.sept.exception.AppException;
import com.sept.jui.tree.AbstractTree;

public class FileTree extends AbstractTree implements MouseListener {
	private static final long serialVersionUID = 1L;

	public FileTree(STree dataTree, TreeCellRenderer cellRenderer) throws AppException {
		super(dataTree, cellRenderer);
		addMouseListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 1) {
			if (e.getClickCount() == 2) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();

				int number = getRowForLocation(e.getX(), e.getY());
				TreePath selTree = getPathForRow(number);
				if (selTree == null) {
					return;
				}
				Object temp = selTree.getPath();
				if (temp == null) {
					return;
				}
				Object temp2 = selTree.getPath()[(selTree.getPathCount() - 1)];
				if (temp2 == null) {
					return;
				}
				File f = new File(((TreeNode) node.getUserObject()).toString());
				if (f.isFile()) {
					try {
						Desktop.getDesktop().open(f);
					} catch (IOException e1) {
						System.out.println("用户取消或出现未知错误，就不显示了");
					}
				}
			}
		} else if (e.isMetaDown()) {
			int number = getRowForLocation(e.getX(), e.getY());
			final TreePath selTree = getPathForRow(number);
			setSelectionPath(selTree);
			if (selTree == null) {
				return;
			}
			JPopupMenu popupMenu = new JPopupMenu();

			Object temp = selTree.getPath();
			if (temp == null) {
				return;
			}
			Object temp2 = selTree.getPath()[(selTree.getPathCount() - 1)];
			if (temp2 == null) {
				return;
			}
			JMenuItem mntmNewMenuItem = new JMenuItem("下载" + temp2.toString());
			popupMenu.add(mntmNewMenuItem);
			mntmNewMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("这里要下载" + selTree.getPath()[(selTree.getPathCount() - 1)].toString());
				}
			});
			popupMenu.show(e.getComponent(), e.getX(), e.getY());

			System.out.println("弹出" + selTree.getPath()[(selTree.getPathCount() - 1)].toString());
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
