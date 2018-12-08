package com.sept.support.ftp;

import java.util.HashMap;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;

/**
 * 
 * 
 * @author ZC6
 * 
 */
public class TreeModel {
	private String id;
	private DataObject alValueNames = new DataObject();// 主要属性名称
	private DataObject attributes = new DataObject();// 附加属性
	private HashMap<String, TreeModel> hmChildren = new HashMap<String, TreeModel>();// 子节点
	private HashMap<Integer, String> hmKeys = new HashMap<Integer, String>();// 按添加顺序增加
	private int childIndex = 0;

	public TreeModel(String id, DataObject alValueNames, DataObject attributes) {
		super();
		this.id = id;
		this.alValueNames = alValueNames;
		this.attributes = attributes;
	}

	public TreeModel() {
	}

	/**
	 * 获取是否有子节点
	 * 
	 * @return
	 */
	public boolean hasChild() {
		return !hmChildren.isEmpty();
	}

	/**
	 * 获取一个节点的DataObject ,结构为</br>
	 * <p>
	 * 属性
	 * </p>
	 * <p>
	 * 附加属性do
	 * </p>
	 * <p>
	 * 叶子ds
	 * </p>
	 * 
	 * @return
	 * @throws AppException 
	 */
	public DataObject getTreeDo() throws AppException {
		alValueNames.put("id", this.id);
		alValueNames.put("attributes", this.attributes);

		DataStore vds = new DataStore();
		for (int i = 0; i < this.hmKeys.size(); i++) {
			vds.addRow(this.hmChildren.get(this.hmKeys.get(i)).getTreeDo());
		}
		alValueNames.put("children", vds);

		return alValueNames;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addProperty(String key, String value) {
		this.alValueNames.put(key, value);
	}

	public void getProperty(String key) throws AppException {
		this.alValueNames.get(key);
	}

	public DataObject getPropertys() {
		return this.alValueNames;
	}

	public void setPropertys(DataObject alValueNames) {
		this.alValueNames = alValueNames;
	}

	public void addAttribute(String key, String value) {
		this.attributes.put(key, value);
	}

	public Object getAttribute(String key) throws AppException {
		return this.attributes.get(key);
	}

	public DataObject getAttributes() {
		return attributes;
	}

	public void setAttributes(DataObject attributes) {
		this.attributes = attributes;
	}

	public TreeModel addChild(String id, DataObject propertys,
			DataObject attributes) {
		TreeModel tree = new TreeModel(id, propertys, attributes);
		this.hmChildren.put(id, tree);
		this.hmKeys.put(childIndex++, id);
		return tree;
	}

	public TreeModel addChild(TreeModel tm) {
		this.hmChildren.put(tm.getId(), tm);
		this.hmKeys.put(childIndex++, tm.getId());
		return tm;
	}

	public HashMap<String, TreeModel> getHmChildren() {
		return hmChildren;
	}

	public void setHmChildren(HashMap<String, TreeModel> hmChildren) {
		this.hmChildren = hmChildren;
	}
}
