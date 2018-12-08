package com.sept.framework.web.requestTrace;

import java.util.ArrayList;

import com.sept.support.util.StringUtil;

public class TraceNode {

	private String nodeId;
	private String className;
	private String methodName;
	private long startTime;
	private long endTime;
	private long cost;
	private TraceNode parentNode;
	private ArrayList<TraceNode> children = new ArrayList<TraceNode>();

	public TraceNode(String className, String methodName) {
		this.nodeId = StringUtil.getUUID();
		this.className = className;
		this.methodName = methodName;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void addChild(TraceNode node) {
		children.add(node);
		node.setParentNode(this);
	}

	public ArrayList<TraceNode> getChildren() {
		return children;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public long getCost() {
		return cost;
	}

	public void setParentNode(TraceNode parentNode) {
		this.parentNode = parentNode;
	}

	public TraceNode getParentNode() {
		return parentNode;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getEndTime() {
		return endTime;
	}
}
