package com.sept.support.model.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataStore;

/**
 * 继承此类以实现对tree的创建
 * 
 * @author zchar
 * 
 */
public abstract class AbstractTree implements Serializable {
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<String, AbstractNode> hmRootNodes = null;
	private boolean multiModel = false;
	private ArrayList<String> rootIds = null;

	public AbstractTree(AbstractNode rootNode) throws AppException {
		if (null == rootNode || null == rootNode.getId()
				|| rootNode.getId().trim().isEmpty()) {
			throw new AppException("要设置的根节点为null或没有ID!");
		}
		rootNode.setFatherId(null);
		rootIds = new ArrayList<String>();
		this.rootIds.add(rootNode.getId());
		this.hmRootNodes = new LinkedHashMap<String, AbstractNode>();
		this.hmRootNodes.put(rootNode.getId(), rootNode);
	}

	public void addRoot(AbstractNode rootNode) throws AppException {
		if (null == rootNode || null == rootNode.getId()
				|| rootNode.getId().trim().isEmpty()) {
			throw new AppException("要设置的根节点为null或没有ID!");
		}
		rootNode.setFatherId(null);
		this.rootIds.add(rootNode.getId());
		this.hmRootNodes.put(rootNode.getId(), rootNode);
	}

	public abstract String getTreeData() throws AppException;

	public abstract DataStore getTreeDs() throws AppException;

	public AbstractNode getNode(String nodeId) {
		for (AbstractNode node : this.hmRootNodes.values()) {
			AbstractNode rightNode = node.getNode(nodeId);
			if (null != rightNode) {
				return rightNode;
			}
		}
		return null;
	}

	public boolean isMultiModel() {
		return multiModel;
	}

	public void setMultiModel(boolean multiModel) {
		this.multiModel = multiModel;
	}

	public ArrayList<String> getRootIds() {
		return rootIds;
	}

	public ArrayList<String> getAllIds() {
		ArrayList<String> alIds = new ArrayList<String>();
		for (AbstractNode abstractNode : this.hmRootNodes.values()) {
			alIds.addAll(abstractNode.getAllIds());
		}
		return alIds;
	}

	public void setRootIds(ArrayList<String> rootIds) {
		this.rootIds = rootIds;
	}
}
