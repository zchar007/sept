package com.sept.framework.taglib.easyui.layout;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptTag;

public class LayoutTag extends AbstractSeptTag {
	private static final long serialVersionUID = 1L;
	// onCollapse region 在折叠区域面板的时候触发。（该事件自1.4.4版开始可用）
	private String onCollapse = null;
	// onExpand region 在展开区域面板的时候触发。（该事件自1.4.4版开始可用）
	private String onExpand = null;
	// onAdd region 在新增区域面板的时候触发。（该事件自1.4.4版开始可用）
	private String onAdd = null;
	// onRemove region 在移除区域面板的时候触发。（该事件自1.4.4版开始可用）
	private String onRemove = null;

	@Override
	public void init() throws JspException {
		this.impl = new LayoutTagImpl();
		this.setNoQuotePara("onCollapse");
		this.setNoQuotePara("onExpand");
		this.setNoQuotePara("onAdd");
		this.setNoQuotePara("onRemove");

	}

	@Override
	protected void checkParent() throws JspException {
		if (null == this.parent) {
			throw new JspException("Layout标签不可没有父标签！！");
		}
	}

	@Override
	protected void checkParam() throws JspException {
		if(null == this.getName() || this.getName().trim().isEmpty()){
			throw new JspException("Layout标签name属性为必填！");
		}
		if (null != this.getOnCollapse()
				&& !this.getOnCollapse().trim().isEmpty()) {
			this.addPara("onCollapse",
					"function(region){" + this.getOnCollapse() + "(region)}");
		}
		if (null != this.getOnAdd() && !this.getOnAdd().trim().isEmpty()) {
			this.addPara("onAdd", "function(region){" + this.getOnAdd()
					+ "(region)}");
		}
		if (null != this.getOnExpand() && !this.getOnExpand().trim().isEmpty()) {
			this.addPara("onExpand", "function(region){" + this.getOnExpand()
					+ "(region)}");
		}
		if (null != this.getOnRemove() && !this.getOnRemove().trim().isEmpty()) {
			this.addPara("onnRemove", "function(region){" + this.getOnRemove()
					+ "(region)}");
		}
	}

	@Override
	protected void doOther() throws JspException {
	}

	public String getOnCollapse() {
		return onCollapse;
	}

	public void setOnCollapse(String onCollapse) {
		this.onCollapse = onCollapse;
	}

	public String getOnExpand() {
		return onExpand;
	}

	public void setOnExpand(String onExpand) {
		this.onExpand = onExpand;
	}

	public String getOnAdd() {
		return onAdd;
	}

	public void setOnAdd(String onAdd) {
		this.onAdd = onAdd;
	}

	public String getOnRemove() {
		return onRemove;
	}

	public void setOnRemove(String onRemove) {
		this.onRemove = onRemove;
	}

}
