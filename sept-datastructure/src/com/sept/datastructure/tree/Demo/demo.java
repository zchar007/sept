package com.sept.datastructure.tree.Demo;


import com.sept.datastructure.tree.SNode;
import com.sept.datastructure.tree.STree;
import com.sept.exception.AppException;

public class demo {
	public static void main(String[] args) throws AppException {
		STree tree = new STree();
		for (int i = 0; i < 3; i++) {
			SNode node = new SNode("张三_" + i);
			tree.addRoot(node);
			for (int j = 0; j < 3; j++) {
				SNode node2 = new SNode("张三_" + i + "_" + j);
				node.addChild(node2);
				for (int t = 0; t < 3; t++) {
					SNode node3 = new SNode("张三_" + i + "_" + j + "_" + t);
					node2.addChild(node3);
				}
			}
		}
		
		SNode node3 = new SNode("张三_1_2_2");
		tree.addRoot(node3);

		System.out.println(tree.getDataStore().toXML());
	}
}
