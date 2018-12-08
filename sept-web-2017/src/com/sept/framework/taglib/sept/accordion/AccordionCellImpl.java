package com.sept.framework.taglib.sept.accordion;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractImpl;
import com.sept.framework.taglib.sept.tools.ToolsImpl;

public class AccordionCellImpl extends AbstractImpl {
	private static final String DEFAULT_WIDTH = "1024px";
	private static final String DEFAULT_HEIGHT = "600px";

	@Override
	public String genHTML() throws JspException {
		boolean hidden = this.getBooleanPara("hidden", false);
		String sizeStr = "display:" + (hidden ? "none" : "");

		StringBuffer html = new StringBuffer();
		String href = this.getStringPara("url", "");
		String tools = "";
		ToolsImpl toolsImpl = null;
		ArrayList<AbstractImpl> children = this.getChildren();
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) instanceof ToolsImpl) {
				toolsImpl = (ToolsImpl) children.get(i);
				String toolsname = toolsImpl.getStringPara("name");
				tools = ",tools:'#sept-name-" + toolsname + "'";
				break;
			}
		}
		html.append("<div id=\"sept-name-" + this.getStringPara("name")
				+ "\" title=\"" + this.getStringPara("labelValue")
				+ "\" data-options=\"" + this.getEUIOptions() + ",href:'"
				+ href + "'" + tools + "\" style=\"" + sizeStr + "\">\n");

		for (int i = 0; i < children.size(); i++) {
			if (!(children.get(i) instanceof ToolsImpl)) {
				html.append(children.get(i).genHTML());
			}
		}

		html.append("</div>\n");
		if (null != toolsImpl) {
			html.append(toolsImpl.genHTML());
		}
		return html.toString();
	}

}
