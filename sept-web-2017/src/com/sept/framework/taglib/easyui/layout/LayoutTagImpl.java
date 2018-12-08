package com.sept.framework.taglib.easyui.layout;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptImpl;
import com.sept.framework.taglib.easyui.layout.layoutCell.LayoutCellTagImpl;

public class LayoutTagImpl extends AbstractSeptImpl {

	@Override
	protected String initHTML() throws JspException {
		if (Boolean.parseBoolean(this.getDiyPara("fit", "true"))) {
			this.setPara("width", "100%");
			this.setPara("height", "100%");
		} else {
			this.setPara("width", this.getDiyPara("width", ""));
			this.setPara("width", this.getDiyPara("height", ""));
		}
		StringBuffer html = new StringBuffer();
		html.append("<div id=\"zc_tag_" + this.getDiyPara("name")
				+ "\" class=\"easyui-layout\" data-options='"
				+ this.getEUIOptions() + "' fit=\""
				+ this.getDiyPara("fit", "true") + "\"");
		html.append("style=\"display:"
				+ (Boolean.parseBoolean(this.getDiyPara("hidden", "false")) ? "none"
						: ""));
		html.append("\" >");
		ArrayList<AbstractSeptImpl> children = this.getChildren();
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) instanceof LayoutCellTagImpl) {
				html.append(children.get(i).genHTML());
			}
		}

		html.append("<div>");
		return html.toString();
	}

}
