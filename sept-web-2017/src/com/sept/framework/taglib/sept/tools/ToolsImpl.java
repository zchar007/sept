package com.sept.framework.taglib.sept.tools;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractImpl;
import com.sept.framework.taglib.sept.tools.tbutton.TButtonImpl;

public class ToolsImpl extends AbstractImpl{

	@Override
	public String genHTML() throws JspException {
		StringBuffer html = new StringBuffer();
		html.setLength(0);
		html.append("<div id=\"sept-name-" + this.getStringPara("name") + "\">");
		ArrayList<AbstractImpl> children = this.getChildren();
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) instanceof TButtonImpl) {
				html.append(children.get(i).genHTML());
			}
		}
		html.append("</div>");

		return html.toString();
	}

}
