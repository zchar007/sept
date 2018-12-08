package com.sept.web.taglib.common.diy;

import javax.servlet.jsp.JspException;

import com.sept.web.taglib.AbstractImpl;

public class DiyImpl extends AbstractImpl {

	@Override
	public String genHtml() throws JspException {
		return this.attribute("html", "");
	}

}
