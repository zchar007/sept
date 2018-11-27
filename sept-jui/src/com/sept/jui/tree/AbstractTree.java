package com.sept.jui.tree;

import java.awt.Component;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.sept.datastructure.tree.SNode;
import com.sept.datastructure.tree.STree;
import com.sept.exception.AppException;

public class AbstractTree extends JTree {
	private static final long serialVersionUID = 1L;
	private STree dataTree;
	private TreeCellRenderer cellRenderer;

	public AbstractTree(STree dataTree) throws AppException {
		this.dataTree = dataTree;
		this.cellRenderer = new STreeCellRenderer();
		initialize();
	}

	public AbstractTree(STree dataTree, TreeCellRenderer cellRenderer) throws AppException {
		this.dataTree = dataTree;
		this.cellRenderer = cellRenderer;
		initialize();
	}

	private void initialize() throws AppException {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		LinkedList<SNode> alNodes = this.dataTree.getChildren();
		for (int i = 0; i < alNodes.size(); i++) {
			DefaultMutableTreeNode root_any = new DefaultMutableTreeNode(alNodes.get(i));
			root.add(root_any);
		}
		DefaultTreeModel treeModel = new DefaultTreeModel(setNextNode(root));

		setModel(treeModel);

		addTreeWillExpandListener(new TreeWillExpandListener() {
			public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
				try {
					TreePath path = event.getPath();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
					node = setNextNode(node);
				} catch (AppException e) {
					e.printStackTrace();
				}
			}

			public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
			}
		});
		setRootVisible(false);

		setCellRenderer(this.cellRenderer);

		expandPath(new TreePath(((DefaultMutableTreeNode) root.getChildAt(0)).getPath()));
	}

	private DefaultMutableTreeNode setNextNode(DefaultMutableTreeNode root) throws AppException {
		int count = root.getChildCount();
		for (int i = 0; i < count; i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
			SNode childNode = (SNode) node.getUserObject();

			ArrayList<String> nodes = new ArrayList<>();

			for (int j = 0; j < node.getChildCount(); j++) {
				DefaultMutableTreeNode node_child = (DefaultMutableTreeNode) node.getChildAt(j);
				SNode treeNode = (SNode) node_child.getUserObject();
				nodes.add(treeNode.toString());
			}

			if (childNode.getChildren().size() > 0) {
				LinkedList<SNode> alNodes = childNode.getChildren();
				for (int j = 0; j < alNodes.size(); j++) {
					if (!nodes.contains(alNodes.get(j).toString())) {
						node.add(new DefaultMutableTreeNode(alNodes.get(j)));
					}
				}
			}
		}
		return root;
	}

	/**
	 * 刷新某一结点
	 * 
	 * @param node
	 * @throws AppException
	 */
	protected void refreshNode(DefaultMutableTreeNode node) throws AppException {
		collapsePath(new TreePath((node).getPath()));
		// node.removeAllChildren();

		int count = node.getChildCount();
		ArrayList<String> nodes = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			DefaultMutableTreeNode node_child = (DefaultMutableTreeNode) node.getChildAt(i);
			SNode treeNode = (SNode) node_child.getUserObject();
			nodes.add(treeNode.toString());
		}
		SNode childNode = (SNode) node.getUserObject();
		if (childNode.getChildren().size() > 0) {
			LinkedList<SNode> alNodes = childNode.getChildren();
			for (int j = 0; j < alNodes.size(); j++) {
				SNode nodeT = alNodes.get(j);
				if (!nodes.contains(nodeT.toString())) {
					node.add(new DefaultMutableTreeNode(nodeT));
				}
			}
		}
		expandPath(new TreePath((node).getPath()));

		this.updateUI();
	}
	
	class STreeCellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;
		FileSystemView fsv = FileSystemView.getFileSystemView();

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			setText(((SNode) node.getUserObject()).getName());

			return this;
		}
	}

}
