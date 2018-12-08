package com.sept.framework.taglib.sept.panel;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractImpl;
import com.sept.framework.taglib.sept.tools.ToolsImpl;

public class PanelImpl extends AbstractImpl{
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
				+ "\" class=\"easyui-panel\" title=\""
				+ this.getStringPara("labelValue","") + "\" data-options=\""
				+ this.getEUIOptions() + ",href:'" + href + "'" + tools
				+ "\" fit=\"" + fit + "\" style=\"" + sizeStr + "\">\n");

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
