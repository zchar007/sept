package com.sept.framework.taglib.sept.button;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractImpl;

public class ButtonImpl extends AbstractImpl{
	@Override
	public String genHTML() throws JspException {
		boolean hidden = this.getBooleanPara("hidden", false);
		String sizeStr = "display:" + (hidden ? "none" : "");
		String width = this.getStringPara("width", null);
		String height = this.getStringPara("height", null);

		if (null != width && !width.trim().isEmpty()) {
			sizeStr += ";width:" + width + ";";
		}
		if (null != height && !height.trim().isEmpty()) {
			sizeStr += ";height:" + height + ";";
		}

		StringBuffer html = new StringBuffer();
		String labelValue = this.getStringPara("labelValue", "");

		html.append("<div id=\"sept-name-" + this.getStringPara("name")
				+ "\" class=\"easyui-linkbutton\"  data-options=\""
				+ this.getEUIOptions() + "\"  style=\"" + sizeStr + "\">\n");
		html.append(labelValue);

		html.append("</div>\n");
		return html.toString();
	}

}
