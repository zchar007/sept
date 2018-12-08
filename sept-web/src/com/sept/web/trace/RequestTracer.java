package com.sept.web.trace;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

/**
 * 
 * @description 路径记录
 *
 * @author zchar.2018年11月19日下午11:01:44
 */
public class RequestTracer {
	private static final ThreadLocal<TraceNode> rootNode = new ThreadLocal<TraceNode>();
	private static final ThreadLocal<TraceNode> currentNode = new ThreadLocal<TraceNode>();

	private static final void setRootNode(TraceNode node) {
		rootNode.set(node);
	}

	private static final TraceNode getRootNode() {
		return rootNode.get();
	}

	private static final void removeRootNode() {
		rootNode.remove();
	}

	private static final void setCurrentNode(TraceNode node) {
		currentNode.set(node);
	}

	private static final TraceNode getCurrentNode() {
		return currentNode.get();
	}

	private static final void removeCurrentNode() {
		currentNode.remove();
	}

	private static final TraceNode addNode(String className, String methodName) {
		TraceNode traceNode = new TraceNode(className, methodName);
		traceNode.setStartTime(System.currentTimeMillis());

		if (getRootNode() == null) {
			setRootNode(traceNode);
		} else {
			getCurrentNode().addChild(traceNode);
		}

		return traceNode;
	}

	public static final void startNode(String className, String methodName) {
		try {
			TraceNode node = addNode(className, methodName);
			setCurrentNode(node);
		} catch (Exception e) {
		}
	}

	public static final void endNode() {
		try {
			TraceNode currentNode = getCurrentNode();
			if (currentNode != null) {
				currentNode.setEndTime(System.currentTimeMillis());
				currentNode.setCost(currentNode.getEndTime() - currentNode.getStartTime());
				setCurrentNode(currentNode.getParentNode());
			}
		} catch (Exception e) {
		}
	}

	public static final void removeThreadLocalNode() {
		try {
			removeCurrentNode();
			removeRootNode();
		} catch (Exception e) {
		}
	}

	private static final DataObject getNodeDataObject(TraceNode node) {
		DataObject nodeObject = new DataObject();
		nodeObject.put("nodeId", node.getNodeId());
		nodeObject.put("className", node.getClassName());
		nodeObject.put("methodName", node.getMethodName());
		if (node.getParentNode() != null) {
			nodeObject.put("parentNodeId", node.getParentNode().getNodeId());
		} else {
			nodeObject.put("parentNodeId", "");
		}
		nodeObject.put("cost", node.getCost());
		return nodeObject;
	}

	private static final void recursiveTraceNode(TraceNode node, DataStore resultDs) throws AppException {
		DataObject vdo = null;
		TraceNode childNode = null;
		if (node != null) {
			vdo = new DataObject();
			vdo = getNodeDataObject(node);
			resultDs.addRow(vdo);

			for (int i = 0; i < node.getChildren().size(); i++) {
				childNode = node.getChildren().get(i);
				recursiveTraceNode(childNode, resultDs);
			}
		}
	}

	public static final String getRequestTraceJson() {
		String resultStr = "";
		try {
			DataStore resultDS = new DataStore();
			TraceNode rootNode = getRootNode();
			if (rootNode != null) {
				recursiveTraceNode(rootNode, resultDS);
			}

			resultStr = resultDS.toJSON();
		} catch (Exception e) {
			return "";
		}
		return resultStr;
	}

}