package com.sept.framework.taglib.sept.tree.model;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;

public abstract class TreeBuilder {

	private TagTree tree = null;

	protected abstract void initTree(DataObject para) throws AppException;

	/**
	 * 无自定义的ID，自动生成ID
	 * 
	 * @param text
	 * @param isOpen
	 * @param checked
	 * @param iconCls
	 * @param target
	 * @return
	 * @throws AppException
	 */
	protected TagNode addRootNode(String text, boolean isOpen, boolean checked,
			String iconCls, String target) throws AppException {
		TagNode rootNode = new TagNode(text, isOpen, checked, iconCls, target);
		if (null == this.tree) {
			this.tree = new TagTree(rootNode);
		} else {
			this.tree.addRoot(rootNode);
		}
		return rootNode;
	}

	/**
	 * 有自定义的ID
	 * 
	 * @param text
	 * @param isOpen
	 * @param checked
	 * @param iconCls
	 * @param target
	 * @return
	 * @throws AppException
	 */
	protected TagNode addRootNode(String id, String text, boolean isOpen,
			boolean checked, String iconCls, String target) throws AppException {
		TagNode rootNode = new TagNode(id, text, isOpen, checked, iconCls,
				target);
		if (null == this.tree) {
			this.tree = new TagTree(rootNode);
		} else {
			this.tree.addRoot(rootNode);
		}
		return rootNode;
	}

	/**
	 * 获取tree的唯一入口
	 * 
	 * @param para
	 * @return
	 * @throws AppException
	 */
	public DataStore bulider(DataObject para) throws AppException {
		this.initTree(para);
		return this.tree.getTreeDs();
	}
	/*
	 * 有关静态类获取本类名，注意--静态方法在初始化时就已确定其属于哪个类，所以即使被集成，它还是属于其实际所在的类 String clazzName
	 * = new SecurityManager() { public String getClassName() { return
	 * getClassContext()[1].getName(); } }.getClassName();
	 * System.out.println(clazzName); String clazzName3 = new Object() { public
	 * String getClassName() { String clazzName = this.getClass().getName();
	 * return clazzName.substring(0, clazzName.lastIndexOf('$')); }
	 * }.getClassName(); System.out.println(clazzName3); Class<?> classz =
	 * MethodHandles.lookup().lookupClass();
	 * System.out.println(classz.getName());
	 */
}
