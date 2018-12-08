package com.sept.framework.taglib.easyui.diy;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptImpl;

public class DiyTagImpl extends AbstractSeptImpl {
	@Override
	protected String initHTML() throws JspException {
		String html = "";
		html = this.getPara("html", "");
		return html;
	}

}
