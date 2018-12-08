package com.sept.framework.taglib.easyui.layout.layoutCell;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptImpl;

public class LayoutCellTagImpl extends AbstractSeptImpl {
	private static final String DEFAULT_WIDTH = "200px";
	private static final String DEFAULT_HEIGHT = "100px";

	@Override
	public String genHTML() throws JspException {
		return this.initHTML();
	}

	@Override
	protected String initHTML() throws JspException {
		StringBuffer html = new StringBuffer();
		html.append("<div id = \"zc_tag_"
				+ this.getDiyPara("name")
				+ "\" data-options=\""
				+ this.getEUIOptions()
				+ "\""
				+ " style=\"height:"
				+ this.getDiyPara("height", DEFAULT_HEIGHT)
				+ ";width:"
				+ this.getDiyPara("width", DEFAULT_WIDTH)
				+ ";display:"
				+ (Boolean.parseBoolean(this.getDiyPara("hidden", "false")) ? "none"
						: "") + ";\">\n");
		ArrayList<AbstractSeptImpl> children = this.getChildren();
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) instanceof LayoutCellTagImpl) {
				html.append(children.get(i).genHTML());
			}
		}
		html.append("</div>");
		return html.toString();

	}
}
