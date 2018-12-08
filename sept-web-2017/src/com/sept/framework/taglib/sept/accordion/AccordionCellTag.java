package com.sept.framework.taglib.sept.accordion;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractTag;
import com.sept.framework.taglib.sept.panel.PanelTag;

public class AccordionCellTag extends PanelTag{
	private static final long serialVersionUID = 1L;
	// selected boolean 如果设置为true将展开面板。 false
	private boolean selected = false;
	// collapsible boolean 如果设置为true将显示折叠按钮。 true
	private boolean collapsible = true;

	@Override
	public void init() throws JspException {
		this.impl = new AccordionCellImpl();
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		if (null == this.getParent()) {
			this.jspException("Accordion标签必须含有父标签！");
		}
		if (!(this.getParent() instanceof AccordionTag)) {
			this.jspException("Accordion标签的父标签必须是Accordions！");
		}

		return null;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		// TODO 张超 Auto-generated method stub
		return null;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean getCollapsible() {
		return collapsible;
	}

	public void setCollapsible(boolean collapsible) {
		this.collapsible = collapsible;
	}

}
