package com.sept.jui.tree.fileshow;

import java.util.LinkedList;

import com.sept.jui.tree.JUINode;
import com.sept.jui.tree.JUITree;
class TreeSource implements JUITree {
	private String url;

	public TreeSource(String url) {
		this.url = url;
	}

	@Override
	public LinkedList<JUINode> getRootNodes() {
		JUINode root = new TreeNode(this.url);
		LinkedList<JUINode> alNodes = new LinkedList<>();
		alNodes.add(root);
		return alNodes;
	}

}
