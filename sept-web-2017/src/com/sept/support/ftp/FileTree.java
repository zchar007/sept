package com.sept.support.ftp;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataStore;

/**
 * 模拟文件树
 * 
 * @author 张超
 * @version 1.0 创建时间 2017-6-9
 */
public class FileTree extends LogicTree{
	private boolean isFolder = false;// 毕竟文件夹不多，默认不是
	private long size = 0;

	public String getFileType() {
		if (this.isFolder) {
			return "";
		}
		String[] strs = this.getName().split("\\.");
		return strs[strs.length - 1];
	}

	public boolean isFolder() {
		return isFolder;
	}

	public FileTree setFolder(boolean isFolder) {
		this.isFolder = isFolder;
		this.datas.put("isFolder", isFolder);
		return this;
	}

	public long getSize() {
		return size;
	}

	public FileTree setSize(long size) {
		this.size = size;
		this.datas.put("size", size);
		return this;
	}

	public long getAllSize() {
		long size = this.size;
		for (LogicTree tree : this.hmChilds.values()) {
			size += ((FileTree) tree).getAllSize();
		}
		return size;
	}

	/**
	 * @author 张超
	 * @date 创建时间 2017-6-9
	 * @since V1.0
	 */
	public DataStore getAllFiles() throws AppException {
		this.setPath(this.getRealPath());
		DataStore vds = new DataStore();
		if (!this.isFolder) {
			vds.addRow(this.datas);
		}
		for (LogicTree tree : this.hmChilds.values()) {
			vds.addAll(((FileTree) tree).getAllFiles());
		}
		return vds;
	}

}
