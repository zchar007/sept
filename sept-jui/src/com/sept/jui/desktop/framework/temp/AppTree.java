package com.sept.jui.desktop.framework.temp;

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
 * ����ʱ��Ҫ��ȡ��������
 * @author zchar
 *
 */
public class AppTree extends JTree {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppTree() {

		// ��������
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(AppController.getRootModel().getRoot());
		DefaultTreeModel defaultTreeModel = new DefaultTreeModel(createModel(root, AppController.getRootModel()));

		// ��������
		this.setModel(defaultTreeModel);

	
		// ������ʾ�Ѵ򿪵�app,˫������app�¼�
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
						//֪ͨ���ƶ˴�app
						AppController.openApp(appModel.getAppID());
					}
				}
			}
		});
		//
		// �����Զ���������
		this.setCellRenderer(new MyDefaultTreeCellRenderer());

	}

	/**
	 * ��������ʽΪ a-a1,a2,a3 b-b1,b2,b3 ֻ����ڶ����˵���app
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
		 * ��д����DefaultTreeCellRenderer�ķ���
		 */
		FileSystemView fsv = FileSystemView.getFileSystemView();

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {

			// ִ�и���ԭ�Ͳ���
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

			// �ж����ĸ��ı��Ľڵ����ö�Ӧ��ֵ����������ڵ㴫�����һ��ʵ��,����Ը���ʵ�������һ��������������ʾ��Ӧ��ͼ�꣩
			if (null != icon)
				this.setIcon(icon);

			return this;
		}

	}
}
