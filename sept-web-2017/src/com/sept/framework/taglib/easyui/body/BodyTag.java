package com.sept.framework.taglib.easyui.body;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptTag;
import com.sept.framework.taglib.easyui.html.HtmlTag;

public class BodyTag extends AbstractSeptTag {
	private static final long serialVersionUID = 1L;
	private String layout = "vertical";// relative(相对布局，可简写为r/R),absolute(绝对布局，a/A),horizontal(横向布局，h/H),vertical(纵向布局

	@Override
	public void init() throws JspException {
		this.impl = new BodyTagImpl();
		this.setDiyPara("layout");

	}

	@Override
	protected void checkParent() throws JspException {
		if (null != this.parent) {
			if (!(this.parent instanceof HtmlTag)) {
				throw new JspException("body标签只允许作为顶级标签或以html标签为父标签！");
			}
		}

	}

	@Override
	protected void checkParam() throws JspException {
		String lay = this.getLayout();
		if (null == lay || lay.trim().isEmpty()) {
			lay = lay.toLowerCase();
			// relative(相对布局，可简写为r/R),absolute(绝对布局，a/A),horizontal(横向布局，h/H),vertical(纵向布局
			// v/V)
			if ("relative".startsWith(lay)) {
				this.setLayout("relative");
			}
			if ("absolute".startsWith(lay)) {
				this.setLayout("absolute");
			}
			if ("horizontal".startsWith(lay)) {
				this.setLayout("horizontal");
			}
			if ("vertical".startsWith(lay)) {
				this.setLayout("vertical");
			}
		}

	}

	@Override
	protected void doOther() throws JspException {
		// 只有单独存在时才输出
		if (null == this.parent && !(this.parent instanceof HtmlTag)) {
			try {
				String html = this.impl.genHTML();
				String js = this.impl.getJsStr();
				String outH = html + "\n" + js + "\n";
				this.pageContext.getOut().print(outH);
			} catch (Exception e) {
				this.jspException(e);
			}
		}

	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

}
