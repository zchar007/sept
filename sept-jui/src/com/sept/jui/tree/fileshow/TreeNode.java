package com.sept.jui.tree.fileshow;

import java.io.File;
import java.util.LinkedList;

import com.sept.jui.tree.JUINode;

class TreeNode implements JUINode {
	private String url;
	private boolean isHasChild = false;
	private LinkedList<JUINode> children;

	public TreeNode(String url) {
		this.url = url;
	}

	@Override
	public String getShowName() {
		File f = new File(this.url);
		return f.getName();
	}

	@Override
	public boolean hasChild() {
		File f = new File(this.url);
		if (f.isFile()) {
			this.isHasChild = false;
		} else {
			File[] files = f.listFiles();
			if ((files == null) || (files.length <= 0)) {
				this.isHasChild = false;
			} else {
				this.isHasChild = true;
			}
		}
		return this.isHasChild;
	}

	@Override
	public LinkedList<JUINode> getChildren() {
		File f = new File(this.url);
		this.children = new LinkedList<>();
		if (!f.isFile()) {
			File[] files = f.listFiles();
			if (files == null) {
				return this.children;
			}
			for (int i = 0; i < files.length; i++) {
				this.children.add(new TreeNode(files[i].getAbsolutePath()));
			}
		}
		return this.children;
	}

	public String toString() {
		return this.url;
	}
}
