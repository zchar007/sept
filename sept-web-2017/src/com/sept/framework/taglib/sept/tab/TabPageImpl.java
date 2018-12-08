package com.sept.framework.taglib.sept.tab;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractImpl;

public class TabPageImpl extends AbstractImpl{
	private static final String DEFAULT_WIDTH = "1024px";
	private static final String DEFAULT_HEIGHT = "700px";

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

		html.append("<div id=\"sept-name-" + this.getStringPara("name")
				+ "\" title=\"" + this.getStringPara("labelValue")
				+ "\" data-options=\"" + this.getEUIOptions() + ",href:'"
				+ href + "'\" fit=\"" + fit +"\" style=\"" + sizeStr
				+ "\">\n");

		ArrayList<AbstractImpl> children = this.getChildren();
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) instanceof TabPageImpl) {
				html.append(children.get(i).genHTML());
			}
		}

		html.append("</div>\n");

		return html.toString();
	}
}
