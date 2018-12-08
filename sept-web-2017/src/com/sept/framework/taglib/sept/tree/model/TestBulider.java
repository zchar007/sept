package com.sept.framework.taglib.sept.tree.model;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;
import com.sept.support.thread.GlobalToolkit;

public class TestBulider extends TreeBuilder {
	@Override
	protected void initTree(DataObject para) throws AppException {
		// System.out.println("TestBulider当前用户："+GlobalToolkit.getCurrentUser());
		TagNode nodeRoot = this.addRootNode("1", false, true, "icon-save",
				"demo.do?method=fwdButtonDemoPage");
		TagNode nodeChild = new TagNode("1-3", false, true, "icon-save",
				"demo.do?method=fwdButtonDemoPage");
		nodeChild.addChildNode(new TagNode("1-3-1", false, true, "icon-save",
				"demo.do?method=fwdButtonDemoPage"));
		nodeRoot.addChildNode(nodeChild);

		TagNode nodeRoot2 = this.addRootNode("2", false, true, "icon-save",
				"demo.do?method=fwdButtonDemoPage");
		TagNode nodeChild2 = new TagNode("2-3", false, true, "icon-save",
				"demo.do?method=fwdButtonDemoPage");
		nodeChild2.addChildNode(new TagNode("2-3-1", false, true, "icon-save",
				"demo.do?method=fwdButtonDemoPage"));
		nodeRoot2.addChildNode(nodeChild2);
	}

	public static void main(String[] args) throws AppException {
		DataStore vds = new TestBulider().bulider(null);
		System.out.println(vds.toJSON());
	}

}
