package com.sept.framework.taglib.sept.diy;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptImpl;
import com.sept.framework.taglib.sept.AbstractImpl;

public class DiyImpl extends AbstractImpl {
	@Override
	public String genHTML() throws JspException {
		String html = "";
		html = this.getStringPara("html");
		return html;
	}

}
