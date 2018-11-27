package com.sept.jui.tree.fileshow;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.sept.exception.AppException;
import com.sept.jui.alert.Alert;
import com.sept.jui.tree.AbstractTree;
import com.sept.jui.tree.JUITree;

class FileTree extends AbstractTree implements MouseListener, TreeSelectionListener {
	private static final long serialVersionUID = 1L;
	private JPopupMenu popupMenu;
	private TreeNode selectedNode = null;
	private JMenuItem jmi_newDirectory;
	private JMenuItem jmi_newFile;
	private DefaultMutableTreeNode dmtn;

	public FileTree(JUITree dataTree, TreeCellRenderer cellRenderer) throws AppException {
		super(dataTree, cellRenderer);
		addMouseListener(this);
		addPop();
	}

	public FileTree(JUITree dataTree) throws AppException {
		super(dataTree, new FileTreeCellRenderer());
		addMouseListener(this);
		this.addTreeSelectionListener(this);
		addPop();
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
				TreeNode treeNode = (TreeNode) node.getUserObject();
				if (null == treeNode) {
					return;
				}
				File f = new File(treeNode.toString());
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

			Object temp = selTree.getPath();
			if (temp == null) {
				return;
			}
			Object temp2 = selTree.getPath()[(selTree.getPathCount() - 1)];
			if (temp2 == null) {
				return;
			}
			File file = new File(selectedNode.toString());
			if (!file.isDirectory()) {
				jmi_newDirectory.setEnabled(false);
				jmi_newFile.setEnabled(false);
			} else {
				jmi_newDirectory.setEnabled(true);
				jmi_newFile.setEnabled(true);
			}
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
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

	private void addPop() {
		popupMenu = new JPopupMenu();

		jmi_newDirectory = new JMenuItem("新建文件夹");
		popupMenu.add(jmi_newDirectory);
		jmi_newDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (null != selectedNode) {
					String name = Alert.alertInput("请输入", "文件夹名称");
					if (null != name && !name.trim().isEmpty()) {
						File f = new File(selectedNode.toString() + File.separator + name);
						f.mkdirs();
						try {
							FileTree.this.refreshNode(dmtn);
						} catch (AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		jmi_newFile = new JMenuItem("新建文件");
		popupMenu.add(jmi_newFile);
		jmi_newFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (null != selectedNode) {
					String name = Alert.alertInput("请输入", "文件名称");
					if (null != name && !name.trim().isEmpty()) {
						File f = new File(selectedNode.toString() + File.separator + name + ".xml");
						if (f.exists()) {
							Alert.alertError("已存在同名文件！");
							return;
						}
						try {
							f.createNewFile();
							FileTree.this.refreshNode(dmtn);
						} catch (IOException | AppException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath temp2 = e.getPath();
		dmtn = (DefaultMutableTreeNode) temp2.getLastPathComponent();

		selectedNode = (TreeNode) dmtn.getUserObject();
	}
}
