package com.sept.support.ftp;

import java.util.HashMap;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;
import com.sept.support.util.IDManager;

/**
 * 这个可以考虑做成一个数据类型的，现在还未验证普适性 <br>
 * 请参照AbstractTree
 * 
 * @author zchar
 * 
 */
@Deprecated
public class LogicTree {
	public static HashMap<String, LogicTree> hmAll = new HashMap<String, LogicTree>();// 子节点
	private String id = "";// id标记一个节点
	private String fatherId = "";// 父节点ID，没有父节点则为null
	private String name = "";// 用于显示的名字
	private String path = "";// 其路径，一般包含其名字
	protected HashMap<String, LogicTree> hmChilds;// 子节点
	protected DataObject datas;

	public LogicTree() {
		hmChilds = new HashMap<String, LogicTree>();
		datas = new DataObject();
		// 指定ID
		this.id = IDManager.getRandomIDNoHan(8);
		this.datas.put("id", this.id);
		this.datas.put("fatherId", null == this.fatherId ? "" : this.fatherId);
		// System.out.println(this.id);
		hmAll.put(this.id, this);
	}

	public LogicTree(String name, String path) {
		this();
		this.name = name;
		this.path = path;
	}

	public LogicTree(String fatherId, String name, String path) {
		this();
		this.fatherId = fatherId;
		this.name = name;
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public String getFatherId() {
		return fatherId;
	}

	public LogicTree setFatherId(String fatherId) {
		this.fatherId = fatherId;
		this.datas.put("fatherId", this.fatherId);
		return this;

	}

	public String getName() {
		return name;
	}

	public LogicTree setName(String name) {
		this.name = name;
		this.datas.put("name", this.name);
		return this;

	}

	public String getPath() {
		return this.path;
	}

	public String getRealPath() {
		String realPath = this.getName();
		String fid = this.fatherId;
		LogicTree LogicTree = this;
		// 当fid部位空
		while (null != fid && !fid.trim().isEmpty()) {
			LogicTree = hmAll.get(fid);
			realPath = LogicTree.getName() + "/" + realPath;
			fid = LogicTree.getFatherId();
		}
		return realPath;
	}

	public LogicTree setPath(String path) {
		this.path = path;
		this.datas.put("path", this.path);
		return this;
	}

	public LogicTree addChild(LogicTree logicTree) {
		logicTree.setFatherId(this.id);
		this.hmChilds.put(logicTree.getId(), logicTree);

		return this;
	}

	public DataStore getDataStore() throws AppException {
		// String pathTemp = this.path;
		this.setPath(this.getRealPath());
		DataStore vds = new DataStore();
		vds.addRow(this.datas);
		for (LogicTree LogicTree : this.hmChilds.values()) {
			vds.addAll(LogicTree.getDataStore());
		}
		// this.setPath(pathTemp);
		return vds;
	}

	public boolean isHaveChild() {
		return this.hmChilds.size() != 0;
	}

	public HashMap<String, LogicTree> getChildMap() {
		return this.hmChilds;
	}

	/**
	 * 获取key为1234的name从小到大排序的树
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public HashMap<Integer, LogicTree> getChildMapASC() throws AppException {
		DataStore vds = new DataStore();
		for (LogicTree tree : hmChilds.values()) {
			vds.addRow();
			vds.put(vds.rowCount() - 1, "name", tree.getName());
			vds.put(vds.rowCount() - 1, "id", tree.getId());
		}
		vds.sortASC("name");
		HashMap<Integer, LogicTree> hmChildTemp = new HashMap<Integer, LogicTree>();
		for (int i = 0; i < vds.rowCount(); i++) {
			hmChildTemp.put(i, hmChilds.get(vds.getString(i, "id")));
		}
		return hmChildTemp;
	}

	/**
	 * 获取key为1234的name从大到小排序的树
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public HashMap<Integer, LogicTree> getChildMapDESC() throws AppException {
		DataStore vds = new DataStore();
		for (LogicTree tree : hmChilds.values()) {
			vds.addRow();
			vds.put(vds.rowCount() - 1, "name", tree.getName());
			vds.put(vds.rowCount() - 1, "id", tree.getId());
		}
		vds.sortDESC("name");
		HashMap<Integer, LogicTree> hmChildTemp = new HashMap<Integer, LogicTree>();
		for (int i = 0; i < vds.rowCount(); i++) {
			hmChildTemp.put(i, hmChilds.get(vds.getString(i, "id")));
		}
		return hmChildTemp;
	}

}
