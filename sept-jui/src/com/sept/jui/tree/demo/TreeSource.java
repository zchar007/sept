package com.sept.jui.tree.demo;

import java.util.LinkedList;

import com.sept.datastructure.tree.SNode;
import com.sept.datastructure.tree.STree;
import com.sept.exception.AppException;

public class TreeSource extends STree {
	private String url;

	public TreeSource(String url) {
		this.url = url;
	}
	@Override
	public LinkedList<SNode> getChildren() throws AppException {
		TreeNode root = new TreeNode(this.url);
		LinkedList<SNode> alNodes = new LinkedList<>();
		alNodes.add(root);
		return alNodes;
	}

}
