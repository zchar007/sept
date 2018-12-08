package com.sept.datastructure.tree;

import java.util.LinkedList;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.util.StringUtil;

public class SNode implements ITree<SNode> {
	private String id;
	private String name;
	private DataObject attribute;
	private SNode fatherNode;
	private LinkedList<SNode> childrenNode;

	public SNode() {
		this(null);
	}

	public SNode(String name) {
		this.id = StringUtil.getUUID();
		this.name = name;
		this.attribute = new DataObject(false);
		this.childrenNode = new LinkedList<>();
	}

	@Override
	public void addChild(SNode node) throws AppException {
		if (this.childrenNode.contains(node)) {
			throw new AppException(
					"[" + this.id + "][" + this.name + "]" + "已存在子代[" + node.id + "][" + node.name + "]");
		}
		node.setFatherNode(this);
		this.childrenNode.add(node);
	}

	@Override
	public void removeChild(SNode node) throws AppException {
		if (this.childrenNode.contains(node)) {
			this.childrenNode.remove(node);
		}
	}

	@Override
	public LinkedList<SNode> getChildren() throws AppException {
		return this.childrenNode;
	}

	@Override
	public LinkedList<SNode> getPosterity() throws AppException {
		LinkedList<SNode> list = new LinkedList<>();
		for (SNode node : this.childrenNode) {
			list.add(node);
		}
		for (SNode node : this.childrenNode) {
			list.addAll(node.getPosterity());
		}
		return list;
	}

	@Override
	public DataObject getDataObject() throws AppException {
		DataObject pdo = new DataObject(false);
		pdo.put("id", this.id);
		pdo.put("name", this.name);

		for (String key : this.attribute.keySet()) {
			pdo.put(key, this.attribute.get(key));
		}
		DataStore vds = new DataStore(false);
		for (SNode node : this.childrenNode) {
			vds.addRow(node.getDataObject());
		}
		if (vds.rowCount() > 0) {
			pdo.put("children", vds);
		}
		return pdo;
	}

	@Override
	public DataStore getDataStore() throws AppException {
		DataStore vds = new DataStore(false);
		vds.addRow(this.getDataObject());
		return vds;
	}

	public Object attribute(String key) throws AppException {
		return attribute.get(key);
	}

	public void setAttribute(String name, Object value) {
		this.attribute.put(name, value);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataObject getAttribute() {
		return attribute;
	}

	public void setAttribute(DataObject attribute) {
		this.attribute = attribute;
	}

	public SNode getFatherNode() {
		return fatherNode;
	}

	public void setFatherNode(SNode fatherNode) {
		this.fatherNode = fatherNode;
	}

}
