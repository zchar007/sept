package com.sept.support.model.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;

/**
 * 树状数据，每个节点都只有一个父节点，一个父节点可以有多个子节点
 * 
 * @author zchar
 * 
 */
public abstract class AbstractNode implements Serializable {
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<String, AbstractNode> hmChildren = null;
	private DataObject params = null; // 存放其他参数的，对于不同的tree，可以给予不同的参数
	private String id = null;
	private String fatherId = null;

	public AbstractNode(String id) {
		this.id = id;
		this.hmChildren = new LinkedHashMap<String, AbstractNode>();
		this.params = new DataObject(false);
	}

	/**
	 * 获取数据
	 * 
	 * @return
	 */
	public abstract String getNodeData() throws AppException;

	public abstract DataObject getNodeDo() throws AppException;

	public void addChildNode(AbstractNode childNode) throws AppException {
		if (null == childNode || null == childNode.getId()
				|| childNode.getId().trim().isEmpty()) {
			throw new AppException("Node-[" + this.id + "]所要添加的第["
					+ this.hmChildren.size() + "]个子节点为null或没有ID!");
		}
		childNode.setFatherId(this.id);
		this.hmChildren.put(childNode.getId(), childNode);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	/**
	 * 返回的是个clone的数据
	 * 
	 * @return
	 */
	public DataObject getParams() {
		return params.clone();
	}

	protected void setParams(DataObject params) {
		this.params = params.clone();
	}

	public void setParam(String name, Object value) {
		this.params.put(name, value);
	}

	public LinkedHashMap<String, AbstractNode> getHmChildren() {
		return hmChildren;
	}

	public void setHmChildren(LinkedHashMap<String, AbstractNode> hmChildren) {
		this.hmChildren = hmChildren;
	}

	public AbstractNode getNode(String nodeId) {
		if (this.id.equals(nodeId)) {
			return this;
		}
		for (AbstractNode node : this.hmChildren.values()) {
			AbstractNode rightNode = node.getNode(nodeId);
			if (null != rightNode) {
				return rightNode;
			}
		}
		return null;
	}

	public ArrayList<String> getChildIds() {
		ArrayList<String> alIds = new ArrayList<String>();
		for (String string : this.hmChildren.keySet()) {
			alIds.add(string);
		}
		return alIds;
	}

	public ArrayList<String> getAllIds() {
		ArrayList<String> alIds = new ArrayList<String>();
		alIds.add(id);
		for (AbstractNode abstractNode : this.hmChildren.values()) {
			alIds.addAll(abstractNode.getAllIds());
		}
		return alIds;
	}
}
