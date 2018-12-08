package com.sept.framework.taglib.sept.tree.model;

import java.util.ArrayList;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataStore;
import com.sept.support.model.tree.AbstractNode;
import com.sept.support.model.tree.AbstractTree;

public class TagTree extends AbstractTree {
	private static final long serialVersionUID = 1L;

	public TagTree(AbstractNode rootNode) throws AppException {
		super(rootNode);
	}

	@Override
	public String getTreeData() throws AppException {
		String returnStr = "[";
		ArrayList<String> rootNodes = this.getRootIds();
		for (int i = 0; i < rootNodes.size(); i++) {
			returnStr += this.getNode(rootNodes.get(i)).getNodeData() + ",";

		}
		if (returnStr.length() > 1) {
			returnStr = returnStr.substring(0, returnStr.length() - 1);
		}
		returnStr += "]";
		return returnStr;
	}

	public DataStore getTreeDs() throws AppException {
		DataStore vds = new DataStore(false);
		ArrayList<String> rootNodes = this.getRootIds();
		for (int i = 0; i < rootNodes.size(); i++) {
			vds.addRow(this.getNode(rootNodes.get(i)).getNodeDo());

		}
		return vds;

	}

}
