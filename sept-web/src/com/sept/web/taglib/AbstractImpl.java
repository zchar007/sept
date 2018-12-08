package com.sept.web.taglib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;

public abstract class AbstractImpl {
	private ArrayList<AbstractImpl> childrenImpl;
	private HashMap<String, String> attributes = null;

	public String getHTML() throws JspException {
		return genHtml();
	}

	public abstract String genHtml() throws JspException;

	public void addChild(AbstractImpl childImpl) {
		if (null == this.childrenImpl) {
			this.childrenImpl = new ArrayList<>();
		}
		this.childrenImpl.add(childImpl);
	}

	public ArrayList<AbstractImpl> getChildrenImpl() {
		if (null == this.childrenImpl) {
			this.childrenImpl = new ArrayList<>();
		}
		return childrenImpl;
	}

	public void setChildrenImpl(ArrayList<AbstractImpl> childrenImpl) {
		this.childrenImpl = childrenImpl;
	}

	protected String attribute(String key) {
		if (null == this.attributes) {
			this.attributes = new HashMap<>();
		}
		return this.attributes.get(key);
	}

	protected String attribute(String key, String defaultValue) {
		if (null == this.attributes) {
			this.attributes = new HashMap<>();
			return defaultValue;
		}
		if (!this.attributes.containsKey(key) || null == this.attributes.get(key)) {
			return defaultValue;
		}
		return this.attributes.get(key);
	}

	public HashMap<String, String> getAttributes() {
		if (null == this.attributes) {
			this.attributes = new HashMap<>();
		}
		return attributes;
	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}

	protected String getInitParam() {
		StringBuilder sbParams = new StringBuilder();
		for (Entry<String, String> entry : this.attributes.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			/**
			 * 加入非空，非null且不是公共参数的（比如mUrlHead）
			 */
			if (!TagNames.ATTRIBUTE_NO_JOIN_PARAMS.contains(key) && null != value && !value.trim().isEmpty()) {
				sbParams.append(key + "=\"" + value + "\n ");
			}
		}

		return sbParams.toString();
	}
}
