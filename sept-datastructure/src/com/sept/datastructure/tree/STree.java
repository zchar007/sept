package com.sept.datastructure.tree;

import java.util.LinkedList;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public class STree implements TreeInterface<SNode> {
	private LinkedList<SNode> rootNodes;

	public STree() {
		this.rootNodes = new LinkedList<>();
	}

	public void addRoot(SNode node) throws AppException {
		this.addChild(node);
	}

	@Override
	public void addChild(SNode node) throws AppException {
		if (this.rootNodes.contains(node)) {
			throw new AppException("已存在根节点[" + node.getId() + "][" + node.getName() + "]");
		}
		node.setFatherNode(null);
		this.rootNodes.add(node);
	}

	public void removeRoot(SNode node) throws AppException {
		if (this.rootNodes.contains(node)) {
			this.rootNodes.remove(node);
		}
	}

	@Override
	public void removeChild(SNode node) throws AppException {
		if (this.rootNodes.contains(node)) {
			this.rootNodes.remove(node);
		}
	}

	public LinkedList<SNode> getRoots() throws AppException {
		return this.getChildren();
	}

	@Override
	public LinkedList<SNode> getChildren() throws AppException {
		return this.rootNodes;
	}

	@Override
	public LinkedList<SNode> getPosterity() throws AppException {
		LinkedList<SNode> list = new LinkedList<>();
		for (SNode node : this.rootNodes) {
			list.add(node);
		}
		for (SNode node : this.rootNodes) {
			list.addAll(node.getPosterity());
		}
		return list;
	}

	@Override
	public DataObject getDataObject() throws AppException {
		if (this.rootNodes.size() == 0) {
			return null;
		}
		return this.rootNodes.get(0).getDataObject();
	}

	@Override
	public DataStore getDataStore() throws AppException {
		DataStore vds = new DataStore(false);
		for (SNode node : this.rootNodes) {
			vds.addRow(node.getDataObject());
		}

		return vds;
	}

	public void clear() {
		this.rootNodes.clear();
	}

}
