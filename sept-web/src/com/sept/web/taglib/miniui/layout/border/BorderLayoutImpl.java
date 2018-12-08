package com.sept.web.taglib.miniui.layout.border;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.web.taglib.AbstractImpl;

public class BorderLayoutImpl extends AbstractImpl {

	@Override
	public String genHtml() throws JspException {
		StringBuilder sbHtml = new StringBuilder();
		sbHtml.append("<div id=\"").append(this.attribute("id")).append("\" class=\"mini-layout\">\\r\\n");

		ArrayList<AbstractImpl> children = this.getChildrenImpl();

		for (int i = 0; i < children.size(); i++) {
			sbHtml.append(children.get(i).getHTML());
		}
		sbHtml.append("</div>");
		return sbHtml.toString();
	}

}
