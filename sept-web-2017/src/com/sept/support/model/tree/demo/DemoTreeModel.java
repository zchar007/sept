package com.sept.support.model.tree.demo;

import java.util.ArrayList;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataStore;
import com.sept.support.model.tree.AbstractNode;
import com.sept.support.model.tree.AbstractTree;

public class DemoTreeModel extends AbstractTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1558629265622983163L;

	public DemoTreeModel(AbstractNode rootNode) throws AppException {
		super(rootNode);
	}

	@Override
	public String getTreeData() {
		return null;
	}

	@Override
	public DataStore getTreeDs() throws AppException {
		DataStore vds = new DataStore(false);
		ArrayList<String> rootNodes = this.getRootIds();
		for (int i = 0; i < rootNodes.size(); i++) {
			vds.addRow(this.getNode(rootNodes.get(i)).getNodeDo());

		}
		return vds;
	}

}
