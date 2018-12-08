package com.sept.io.web.ftp.ftp;

import java.util.ArrayList;

import com.sept.io.web.ftp.tree.FTPFileTree;

public interface IFtp{
	/**
	 * .获取初始节点开始的tree对象
	 * 
	 * @return
	 */
	FTPFileTree getFileTree();

	/**
	 * .获取当前节点的tree对象
	 * 
	 * @return
	 */
	FTPFileTree getCurrentFileTree();

	/**
	 * .获取当前节点的FTPFile对象
	 * 
	 * @return
	 */
	IFtp getCurrentFTPFile();

	/**
	 * .获取所有子节点
	 * 
	 * @return
	 */
	ArrayList<IFtp> getChildren();

	/**
	 * .下载此节点
	 */
	void downlod();

	/**
	 * .当前节点是不是文件夹
	 * 
	 * @return
	 */
	boolean isDirectory();

	/**
	 * .当前节点是不是文件
	 * 
	 * @return
	 */
	boolean isFile();

	/**
	 * .当前节点是不是顶级节点（定义此连接时传入的节点为根节点）
	 * 
	 * @return
	 */
	boolean isRoot();

	/**
	 * .当前节点是否含子节点
	 * 
	 * @return
	 */
	boolean hasChild();

}
