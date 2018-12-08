package com.sept.web.taglib.miniui.layout.border.region;

import javax.servlet.jsp.JspException;

import com.sept.web.taglib.AbstractTag;
import com.sept.web.taglib.miniui.layout.border.BorderLayoutTag;

public class RegionTag extends AbstractTag {
	private static final long serialVersionUID = 1L;

	@Override
	public void beforStart() throws JspException {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws JspException {
		this.impl = new RegionImpl();
	}

	@Override
	public void beforEnd() throws JspException {
		// TODO Auto-generated method stub

	}

	@Override
	public void end() throws JspException {
		if (null == this.parent) {
			throw new JspException("RegionTag标签不能单独存在！");
		}
		if (!(this.parent instanceof BorderLayoutTag)) {
			throw new JspException("RegionTag父标签必须为BorderLayoutTag！");
		}
	}

}
