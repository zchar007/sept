package com.sept.support.model.tree.demo;

import java.util.LinkedHashMap;

import javax.swing.Icon;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;
import com.sept.support.model.tree.AbstractNode;

public class DemoTreeNode extends AbstractNode {
	private static final long serialVersionUID = -2213974098306198770L;
	private Icon icon;
	private String showName;
	private boolean isExpand = false;

	public DemoTreeNode(String id) {
		super(id);
	}

	public DemoTreeNode(String id, Icon icon, String showName,boolean isExpand) {
		super(id);
		this.icon = icon;
		this.showName = showName;
		this.setExpand(isExpand);
	}

	@Override
	public String getNodeData() {
		return null;
	}

	@Override
	public DataObject getNodeDo() throws AppException {
		DataObject pdo = this.getParams();
		pdo.put("id", this.getId());
		pdo.put("showName", this.getShowName());
		pdo.put("icon", this.getIcon());
		pdo.put("isExpand", this.isExpand());


		DataStore vds = new DataStore(false);
		LinkedHashMap<String, AbstractNode> children = this.getHmChildren();
		for (AbstractNode node : children.values()) {
			vds.addRow(node.getNodeDo());
		}
		if (vds.rowCount() > 0) {
			pdo.put("children", vds);
		}
		return pdo;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public boolean isExpand() {
		return isExpand;
	}

	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
	}

}
