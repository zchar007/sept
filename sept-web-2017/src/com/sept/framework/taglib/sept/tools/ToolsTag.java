package com.sept.framework.taglib.sept.tools;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractTag;
import com.sept.framework.taglib.sept.panel.PanelTag;

public class ToolsTag extends AbstractTag{
	private static final long serialVersionUID = 1L;
	// 仅有一个name属性
	@Override
	public void init() throws JspException {
		this.impl = new ToolsImpl();
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		if (null == this.getParent()) {
			this.jspException("Tools标签必须含有父标签！");
		}
		// 暂时只会再panel下出现
		if (!(this.getParent() instanceof PanelTag)) {
			this.jspException("Tools标签父标签必须是Panel！");
		}
		return null;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		// TODO 张超 Auto-generated method stub
		return null;
	}

}
