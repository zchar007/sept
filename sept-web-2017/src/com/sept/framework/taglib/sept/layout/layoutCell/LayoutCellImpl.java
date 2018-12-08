package com.sept.framework.taglib.sept.layout.layoutCell;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractImpl;

public class LayoutCellImpl extends AbstractImpl {
	private static final String DEFAULT_WIDTH = "200px";
	private static final String DEFAULT_HEIGHT = "100px";

	@Override
	public String genHTML() throws JspException {
		boolean hidden = this.getBooleanPara("hidden", false);

		String width = this.getStringPara("width", DEFAULT_WIDTH);
		String height = this.getStringPara("height", DEFAULT_HEIGHT);

		if (null == width || width.trim().isEmpty()) {
			width = DEFAULT_WIDTH;
		}
		if (null == height || height.trim().isEmpty()) {
			height = DEFAULT_HEIGHT;
		}

		String href = this.getStringPara("url", "");
		// TODO 这里应该校验合不合法，先不校验了
		String sizeStr = "display:" + (hidden ? "none" : "");

		sizeStr += ";width:" + width + ";height:" + height;
		StringBuffer html = new StringBuffer();
		html.append("<div  id=\"sept-name-" + this.getStringPara("name")
				+ "\" title=\"" + this.getStringPara("labelValue","")
				+ "\" data-options=\"" + this.getEUIOptions() + ",href:'"
				+ href + "'\" style=\"" + sizeStr + "\">\n");
		ArrayList<AbstractImpl> children = this.getChildren();
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) instanceof AbstractImpl) {
				html.append(children.get(i).genHTML());
			}
		}
		html.append("</div>\n");
		return html.toString();
	}
}
