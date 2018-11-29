package com.sept.demo.filetree;

import java.io.File;
import java.util.LinkedList;

import com.sept.datastructure.tree.SNode;
import com.sept.exception.AppException;

public class TreeNode extends SNode {
	private String url;

	public TreeNode(String url) {
		this.url = url;
	}

	@Override
	public LinkedList<SNode> getChildren() throws AppException {
		File f = new File(this.url);
		if (null == super.getChildren() || super.getChildren().size() <= 0) {
			if (!f.isFile()) {
				File[] files = f.listFiles();
				if (files == null) {
					return super.getChildren();
				}
				for (int i = 0; i < files.length; i++) {
					TreeNode node = new TreeNode(files[i].getAbsolutePath());
					super.addChild(node);
				}
			}
		}
		return super.getChildren();
	}

	public String toString() {
		return this.url;
	}
}
