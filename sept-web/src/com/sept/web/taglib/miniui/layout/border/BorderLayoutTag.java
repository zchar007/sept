package com.sept.web.taglib.miniui.layout.border;

import javax.servlet.jsp.JspException;

import com.sept.web.taglib.AbstractTag;

public class BorderLayoutTag extends AbstractTag {
	private static final long serialVersionUID = 1L;
	// property
	// {Type=Number, Tag?=√, Description=分割条尺寸, Get?=√, Set?=√, Default=6,
	// Name=splitSize}
	private String splitSize = "6";
	// event
	// {Description=折叠面板前发生, EventObject=e : { sender: Object, region: Object,
	// cancel: false}, Name=beforecollapse}
	private String beforecollapse = null;
	// {Description=展开面板前发生, EventObject=e : { sender: Object, region: Object,
	// cancel: false}, Name=beforeexpand}
	private String beforeexpand = null;
	// {Description=折叠面板时发生, EventObject=e : { sender: Object, region: Object},
	// Name=collapse}
	private String collapse = null;
	// {Description=展开面板时发生, EventObject=e : { sender: Object, region: Object},
	// Name=expand}
	private String expand = null;

	@Override
	public void beforStart() throws JspException {
		// 这里一般写一些初始化的东西，一般用不到
	}

	@Override
	public void start() throws JspException {
		this.impl = new BorderLayoutImpl();
	}

	@Override
	public void beforEnd() throws JspException {
		// 这里写对attribute的处理
	}

	@Override
	public void end() throws JspException {
		if (null == this.parent) {
			throw new JspException("RegionTag标签不能单独存在！");
		}
	}

	public String getsplitSize() {
		return this.splitSize;
	}

	public void setsplitSize(String splitSize) {
		this.splitSize = splitSize;
	}

	public String getbeforecollapse() {
		return this.beforecollapse;
	}

	public void setbeforecollapse(String beforecollapse) {
		//别忘了事件的特殊处理//this.attributes.put("name", this.name);
	}

	public String getbeforeexpand() {
		return this.beforeexpand;
	}

	public void setbeforeexpand(String beforeexpand) {
		//别忘了事件的特殊处理//this.attributes.put("name", this.name);
	}

	public String getcollapse() {
		return this.collapse;
	}

	public void setcollapse(String collapse) {
		//别忘了事件的特殊处理//this.attributes.put("name", this.name);
	}

	public String getexpand() {
		return this.expand;
	}

	public void setexpand(String expand) {
		//别忘了事件的特殊处理//this.attributes.put("name", this.name);
	}
}
