package com.sept.framework.taglib.sept.diy;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptTag;
import com.sept.framework.taglib.sept.AbstractTag;

public class DiyTag extends AbstractTag {
	private static final long serialVersionUID = 1L;
	private String html = "";

	@Override
	public int doStartTag() throws JspException {
		this.impl = new DiyImpl();
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doAfterBody() throws JspException {
		this.setHtml(bodyContent.getString());
		return SKIP_BODY;
	}

	@Override
	public void init() throws JspException {

	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		if (null == this.parent) {
			throw new JspException("标签Diy不能单独存在");

		}
		return null;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		// TODO Auto-generated method stub
		return null;
	}

}
