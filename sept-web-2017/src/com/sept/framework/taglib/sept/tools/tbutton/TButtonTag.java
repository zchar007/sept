package com.sept.framework.taglib.sept.tools.tbutton;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractTag;
import com.sept.framework.taglib.sept.tools.ToolsTag;

public class TButtonTag extends AbstractTag{
	private static final long serialVersionUID = 1L;
	private String iconCls = null;
	private String onClick = null;

	@Override
	public void init() throws JspException {
		this.impl = new TButtonImpl();
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		if (null == this.getParent()) {
			this.jspException("TButton标签必须含有父标签！");
		}
		if (!(this.getParent() instanceof ToolsTag)) {
			this.jspException("TButton父标签必须是Tools！");
		}
//		if (null != this.getOnClick() && !this.getOnClick().trim().isEmpty()) {
//			this.setOnClick("javascript:" + this.getOnClick());
//		}
		return null;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		// TODO 张超 Auto-generated method stub
		return null;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

}
