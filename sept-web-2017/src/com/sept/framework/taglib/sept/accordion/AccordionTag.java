package com.sept.framework.taglib.sept.accordion;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractTag;

public class AccordionTag extends AbstractTag {

	private static final long serialVersionUID = 1L;

	// border boolean 定义是否显示边框。 true
	private boolean border = true;
	// animate boolean 定义在展开和折叠的时候是否显示动画效果。 true
	private boolean animate = true;
	// multiple boolean 如果为true时，同时展开多个面板。 false
	private boolean multiple = false;
	// selected number 设置初始化时默认选中的面板索引号。 0
	private String selected = "0";

	@Override
	public void init() throws JspException {
		this.impl = new AccordionImpl();
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		if (null == this.getParent()) {
			this.jspException("Accordions标签必须含有父标签！");
		}

		return null;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		return null;
	}

	public boolean isBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public boolean isAnimate() {
		return animate;
	}

	public void setAnimate(boolean animate) {
		this.animate = animate;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

}
