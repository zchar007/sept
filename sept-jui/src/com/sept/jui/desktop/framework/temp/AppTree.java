package com.zchar.appFramework.CompantCell;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.zchar.appFramework.AppController;
import com.zchar.appFramework.uiCell.AppModel;
import com.zchar.appFramework.uiCell.RootModel;

/**
 * 创建时需要获取树的数据
 * @author zchar
 *
 */
public class AppTree extends JTree {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppTree() {

		// 创建数据
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(AppController.getRootModel().getRoot());
		DefaultTreeModel defaultTreeModel = new DefaultTreeModel(createModel(root, AppController.getRootModel()));

		// 设置数据
		this.setModel(defaultTreeModel);

	
		// 单击显示已打开的app,双击打开新app事件
		this.addMouseListener(new MouseAdapter() {
			int n = 0;

			public void mouseClicked(MouseEvent e) {
				n = e.getClickCount();
				if (n == 2) {
					int n = getRowForLocation(e.getX(), e.getY());
					TreePath selTree = getPathForRow(n);

					if (selTree.getPath().length >= 3) {
						//DefaultMutableTreeNode dtFather = (DefaultMutableTreeNode) (selTree.getPath())[selTree
							//	.getPathCount() - 2];
						DefaultMutableTreeNode dt = (DefaultMutableTreeNode) (selTree.getPath())[selTree.getPathCount()
								- 1];
					//	AppModel appModelFather = (AppModel) dtFather.getUserObject();
						AppModel appModel = (AppModel) dt.getUserObject();
						//通知控制端打开app
						AppController.openApp(appModel.getAppID());
					}
				}
			}
		});
		//
		// 设置自定义描述类
		this.setCellRenderer(new MyDefaultTreeCellRenderer());

	}

	/**
	 * 创建的形式为 a-a1,a2,a3 b-b1,b2,b3 只会存在二级菜单的app
	 * 
	 * @param root
	 * @param rm
	 * @return
	 */
	public DefaultMutableTreeNode createModel(DefaultMutableTreeNode root, RootModel rm) {
		ArrayList<AppModel> aList = rm.getAppModels();
		for (AppModel appModel : aList) {
			DefaultMutableTreeNode b = new DefaultMutableTreeNode(appModel);
			for (AppModel appModel2 : appModel.getAppModels()) {
				DefaultMutableTreeNode c = new DefaultMutableTreeNode(appModel2);
				b.add(c);
			}
			root.add(b);
		}
		return root;
	}

	class MyDefaultTreeCellRenderer extends DefaultTreeCellRenderer {
		/**
		 * ID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 重写父类DefaultTreeCellRenderer的方法
		 */
		FileSystemView fsv = FileSystemView.getFileSystemView();

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {

			// 执行父类原型操作
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			DefaultMutableTreeNode dt = (DefaultMutableTreeNode) value;
			AppModel appModel = (AppModel) dt.getUserObject();
			Image image = appModel.getImageIcon();
			ImageIcon icon = null;
			if (null != image)
				icon = new ImageIcon(image);
			setText(appModel.getRootName());

			if (sel) {
				setForeground(getTextSelectionColor());
			} else {
				setForeground(getTextNonSelectionColor());
			}

			// 判断是哪个文本的节点设置对应的值（这里如果节点传入的是一个实体,则可以根据实体里面的一个类型属性来显示对应的图标）
			if (null != icon)
				this.setIcon(icon);

			return this;
		}

	}
}
