package com.sept.framework.web.requestTrace;

import com.sept.exception.AppException;
import com.sept.support.common.DebugManager;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;

public class CallStackTracer {
	private static ThreadLocal<TraceNode> rootNode = new ThreadLocal<TraceNode>();
	private static ThreadLocal<TraceNode> currentNode = new ThreadLocal<TraceNode>();

	private static void setRootNode(TraceNode node) {
		rootNode.set(node);
	}

	private static TraceNode getRootNode() {
		return rootNode.get();
	}

	private static void removeRootNode() {
		rootNode.remove();
	}

	private static void setCurrentNode(TraceNode node) {
		currentNode.set(node);
	}

	private static TraceNode getCurrentNode() {
		return currentNode.get();
	}

	private static void removeCurrentNode() {
		currentNode.remove();
	}

	private static TraceNode addNode(String className, String methodName) {
		TraceNode traceNode = new TraceNode(className, methodName);
		traceNode.setStartTime(System.currentTimeMillis());

		if (getRootNode() == null) {
			setRootNode(traceNode);
		} else {
			getCurrentNode().addChild(traceNode);
		}

		return traceNode;
	}

	public static void startNode(String className, String methodName) {
		try {
			if (DebugManager.isDebugModel()) {
				TraceNode node = addNode(className, methodName);
				setCurrentNode(node);
			}
		} catch (Exception e) {
		}
	}

	public static void endNode() {
		try {
			if (DebugManager.isDebugModel()) {
				TraceNode currentNode = getCurrentNode();
				if (currentNode != null) {
					currentNode.setEndTime(System.currentTimeMillis());
					currentNode.setCost(currentNode.getEndTime()
							- currentNode.getStartTime());
					setCurrentNode(currentNode.getParentNode());
				}
			}
		} catch (Exception e) {
		}
	}

	public static void removeThreadLocalNode() {
		try {
			removeCurrentNode();
			removeRootNode();
		} catch (Exception e) {
		}
	}

	private static DataObject getNodeDataObject(TraceNode node) {
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

	private static void recursiveTraceNode(TraceNode node, DataStore resultDs) throws AppException {
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

	public static String getCallStackJson() {
		String resultStr = "";
		try {
			if (DebugManager.isDebugModel()) {
				DataStore resultDS = new DataStore();
				TraceNode rootNode = getRootNode();
				if (rootNode != null) {
					// �ݹ�Ӹ�ڵ����Node,������Node�浽resultDS��
					recursiveTraceNode(rootNode, resultDS);
				}

				resultStr = resultDS.toJSON();
			}
		} catch (Exception e) {
			return "";
		}
		return resultStr;
	}

}