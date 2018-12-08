package com.sept.web.taglib.common.diy;

import javax.servlet.jsp.JspException;

import com.sept.web.taglib.AbstractTag;

public class DiyTag extends AbstractTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		this.impl = new DiyImpl();
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doAfterBody() throws JspException {
		// 获取父标签
		this.parent = (AbstractTag) getParent();

		// 如果有父标签，吧自己和福标签联系起来
		if (null == this.parent) {
			throw new JspException("diy标签不可单独存在！");
		}
		this.setAttribute("html", bodyContent.getString());
		this.impl.setAttributes(this.getAttributes());
		
		return SKIP_BODY;
	}

	@Override
	public void start() throws JspException {

	}

	@Override
	public void end() throws JspException {

	}

	@Override
	public void beforStart() throws JspException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforEnd() throws JspException {
		// TODO Auto-generated method stub
		
	}

}
