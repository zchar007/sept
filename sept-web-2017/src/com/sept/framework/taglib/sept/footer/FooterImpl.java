package com.sept.framework.taglib.sept.footer;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractImpl;
import com.sept.framework.taglib.sept.button.ButtonImpl;

public class FooterImpl extends AbstractImpl{

	@Override
	public String genHTML() throws JspException {
		StringBuffer html = new StringBuffer();
		html.setLength(0);
		html.append("<div id=\"sept-name-" + this.getStringPara("name") + "\">");
		ArrayList<AbstractImpl> children = this.getChildren();
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) instanceof ButtonImpl) {
				html.append(children.get(i).genHTML());
			}
		}
		html.append("</div>");

		return html.toString();
	}

}
