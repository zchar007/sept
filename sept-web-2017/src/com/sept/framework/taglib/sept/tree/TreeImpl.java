package com.sept.framework.taglib.sept.tree;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractImpl;
import com.sept.framework.taglib.sept.tools.ToolsImpl;

public class TreeImpl extends AbstractImpl {
	private static final String DEFAULT_WIDTH = "1024px";
	private static final String DEFAULT_HEIGHT = "600px";

	@Override
	public String genHTML() throws JspException {
		boolean fit = this.getBooleanPara("fit", true);
		boolean hidden = this.getBooleanPara("hidden", false);
		String sizeStr = "display:" + (hidden ? "none" : "");
		if (!fit) {
			String width = this.getStringPara("width", DEFAULT_WIDTH);
			String height = this.getStringPara("height", DEFAULT_HEIGHT);

			if (null == width || width.trim().isEmpty()) {
				width = DEFAULT_WIDTH;
			}
			if (null == height || height.trim().isEmpty()) {
				height = DEFAULT_HEIGHT;
			}

			// TODO 这里应该校验合不合法，先不校验了
			sizeStr += ";width:" + width + ";height:" + height;
		}
		StringBuffer html = new StringBuffer();

		html.append("<ul id=\"sept-name-" + this.getStringPara("name")
				+ "\" class=\"easyui-tree\" data-options=\""
				+ this.getEUIOptions()
				+ ",url:'sept.do?method=getTree&classz="
				+ this.getStringPara("classz") + "'\" fit=\"" + fit
				+ "\" style=\"" + sizeStr + "\">\n");

		html.append("</ul>\n");
		return html.toString();
	}

}
