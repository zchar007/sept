package com.sept.framework.taglib.sept.tools.tbutton;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractImpl;

public class TButtonImpl extends AbstractImpl{
	@Override
	public String genHTML() throws JspException {
		boolean hidden = this.getBooleanPara("hidden", false);
		String sizeStr = "display:" + (hidden ? "none" : "");

		StringBuffer html = new StringBuffer();
		String labelValue = this.getStringPara("labelValue", "");

		html.append("<a id=\"sept-name-" + this.getStringPara("name")
				+ "\" class=\"" + this.getStringPara("iconCls")
				+ "\" onclick=\"" + this.getStringPara("onClick")
				+ "\" style=\"" + sizeStr + "\">\n");
		html.append(labelValue);

		html.append("</a>\n");
		return html.toString();
	}

}
