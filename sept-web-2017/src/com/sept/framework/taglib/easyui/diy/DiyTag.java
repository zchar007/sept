package com.sept.framework.taglib.easyui.diy;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptTag;

public class DiyTag extends AbstractSeptTag {
	private static final long serialVersionUID = 1L;
	private String html = "";

	@Override
	public int doStartTag() throws JspException {
		this.impl = new DiyTagImpl();
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

	@Override
	protected void checkParent() throws JspException {
		if (null == this.parent) {
			throw new JspException("标签Diy不能单独存在");

		}
	}

	@Override
	protected void checkParam() throws JspException {

	}

	@Override
	protected void doOther() throws JspException {

	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
