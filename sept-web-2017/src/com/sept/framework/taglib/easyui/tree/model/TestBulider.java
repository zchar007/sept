package com.sept.framework.taglib.easyui.tree.model;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;

public class TestBulider extends TreeBuilder {
	@Override
	protected void initTree(DataObject para) throws AppException {
		TagNode nodeRoot = this.addRootNode("1", false, true, "icon-save",
				"this is url");
		TagNode nodeChild = new TagNode("1-3", false, true, "icon-save");
		nodeChild.addChildNode(new TagNode("1-3-1", false, true, "icon-save"));
		nodeRoot.addChildNode(nodeChild);

		TagNode nodeRoot2 = this.addRootNode("2", false, true, "icon-save",
				"this is url");
		TagNode nodeChild2 = new TagNode("2-3", false, true, "icon-save");
		nodeChild2.addChildNode(new TagNode("2-3-1", false, true, "icon-save"));
		nodeRoot2.addChildNode(nodeChild2);
	}
	
	
	public static void main(String[] args) throws AppException {
		DataStore vds = new TestBulider().bulider(null);
		System.out.println(vds.toJSON());
	}

}
