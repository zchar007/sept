package com.sept.framework.taglib.sept.tree.model;

import java.util.LinkedHashMap;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;
import com.sept.support.model.tree.AbstractNode;
import com.sept.support.util.RandomManager;

public class TagNode extends AbstractNode {
	private static final long serialVersionUID = 1L;

	// id：节点ID，对加载远程数据很重要。已有
	private String text = null; // text：显示节点文本。
	private boolean open = true;// state：节点状态，'open' 或
								// 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
	private String iconCls = null;
	private boolean checked = true;// checked：表示该节点是否被选中。
	private String target = null;

	// attributes: 被添加到节点的自定义属性。
	// children: 一个节点数组声明了若干节点。

	public TagNode(String id) {
		super(id);
	}

	public TagNode() {
		super(RandomManager.getRandomStrNoHan(8));
	}

	public TagNode(String text, boolean isOpen, boolean checked, String iconCls) {
		this(RandomManager.getRandomStrNoHan(8));
		this.text = text;
		this.open = isOpen;
		this.checked = checked;
		this.iconCls = iconCls;
	}

	public TagNode(String id, String text, boolean isOpen, boolean checked,
			String iconCls) {
		this(id);
		this.text = text;
		this.open = isOpen;
		this.checked = checked;
		this.iconCls = iconCls;
	}

	public TagNode(String text, boolean isOpen, boolean checked,
			String iconCls, String target) {
		this();
		this.text = text;
		this.open = isOpen;
		this.checked = checked;
		this.iconCls = iconCls;
		this.target = target;
		this.setParam("target", target);
	}

	public TagNode(String id, String text, boolean isOpen, boolean checked,
			String iconCls, String target) {
		this();
		this.text = text;
		this.open = isOpen;
		this.checked = checked;
		this.iconCls = iconCls;
		this.target = target;
		this.setParam("target", target);
	}

	public String getText() {
		return null == text ? "" : text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getIconCls() {
		return null == iconCls ? "" : iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Override
	public String getNodeData() throws AppException {
		DataObject param = this.getParams();
		LinkedHashMap<String, AbstractNode> children = this.getHmChildren();
		String returnStr = "{";
		returnStr += "\"id\":\"" + this.getId() + "\"";
		returnStr += ",\"text\":\"" + this.getText() + "\"";
		// 有子节点才能设置为想设置的state,否则只能是默认open（防止前台死循环加载tree）
		if (children.size() > 0) {
			returnStr += ",\"state\":\"" + (this.isOpen() ? "open" : "closed")
					+ "\"";
		}
		returnStr += ",\"checked\":" + this.isChecked();
		returnStr += ",\"iconCls\":\"" + this.getIconCls() + "\"";

		String returnStrTemp = "";
		if (param.size() > 0) {
			returnStrTemp = ",\"attributes\":{";
			for (String key : param.keySet()) {
				returnStrTemp += key + ":" + param.get(key, "") + ",";
			}
			if (returnStrTemp.length() > 14) {
				returnStrTemp = returnStrTemp.substring(0,
						returnStrTemp.length() - 1);
			}
			returnStrTemp += "}";
			returnStr += returnStrTemp;
		}

		if (children.size() > 0) {
			returnStrTemp = ",\"children\":[";
			for (AbstractNode node : children.values()) {
				returnStrTemp += node.getNodeData() + ",";
			}
			if (returnStrTemp.length() > 14) {
				returnStrTemp = returnStrTemp.substring(0,
						returnStrTemp.length() - 1);
			}
			returnStrTemp += "]";
			returnStr += returnStrTemp;
		}
		returnStr += "}";
		return returnStr;
	}

	/**
	 * 获取一个节点的DataObject ,结构为</br>
	 * 
	 * @return
	 * @throws AppException 
	 */
	public DataObject getNodeDo() throws AppException {
		DataObject pdo = new DataObject(false);
		LinkedHashMap<String, AbstractNode> children = this.getHmChildren();

		pdo.put("id", this.getId());
		if (!"".equals(this.getText())) {
			pdo.put("text", this.getText());
		}
		// 有子节点才能设置为想设置的state,否则只能是默认open（防止前台死循环加载tree）
		if (children.size() > 0) {
			pdo.put("state", this.isOpen() ? "open" : "closed");
		}
		pdo.put("checked", this.isChecked());
		if (!"".equals(this.getIconCls())) {
			pdo.put("iconCls", this.getIconCls());
		}
		if (this.getParams().size() > 0) {
			pdo.put("attributes", this.getParams());
		}

		DataStore vds = new DataStore(false);
		for (AbstractNode node : children.values()) {
			vds.addRow(node.getNodeDo());
		}
		if (vds.rowCount() > 0) {
			pdo.put("children", vds);
		}

		return pdo;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
		this.setParam("target", target);
	}
}
