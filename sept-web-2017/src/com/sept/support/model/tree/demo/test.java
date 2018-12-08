package com.sept.support.model.tree.demo;

import com.sept.exception.AppException;

public class test {
	public static void main(String[] args) throws AppException {
		DemoTreeModel demoTreeModel = null;
		for (int i = 0; i < 10; i++) {
			DemoTreeNode stn1 = new DemoTreeNode(i+"", null, "节点-" + (i + 1), false);
			for (int j = 0; j < 10; j++) {
				DemoTreeNode stn2 = new DemoTreeNode(i+"-"+j, null, "节点-" + (i + 1) + "-" + (j + 1),
						false);
				for (int k = 0; k < 10; k++) {
					DemoTreeNode stn3 = new DemoTreeNode(i+"-"+j+"-"+k, null,
							"节点-" + (i + 1) + "-" + (j + 1) + "-" + (k + 1), false);
					stn2.addChildNode(stn3);

				}
				stn1.addChildNode(stn2);
			}
			if (null == demoTreeModel) {
				demoTreeModel = new DemoTreeModel(stn1);
			} else {
				demoTreeModel.addRoot(stn1);
			}

		}
		System.out.println(demoTreeModel.getNode("8-1-5"));
		System.out.println(demoTreeModel.getAllIds());
	}
	
	
	
	

}
