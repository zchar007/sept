package com.sept.web.taglib.common.body;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.util.DateUtil;
import com.sept.web.taglib.AbstractImpl;
import com.sept.web.taglib.TagNames;

public class BodyImpl extends AbstractImpl {

	@Override
	public String genHtml() throws JspException {
		String root = this.attribute("root", "true");
		StringBuffer html = new StringBuffer();

		if ("true".equals(root)) {
			String version = DateUtil.getCurrentDate("yyyyMMddhhmmss");
			html.append("<html>");
			html.append("<head>");
			html.append("<base href=\"" + this.attribute("currentUrl", "") + "\">");
			html.append("<title>" + this.attribute("title", "") + "</title>");
			// ͼ��
			html.append("<link rel=\"shortcut icon\" href=\"" + this.attribute("mUrlHead")
					+ this.attribute("pageIcon", "widgets/themes/ico/sept.ico") + "\" type=\"image/x-icon\">");
			html.append("<meta http-equiv=\"pragma\" content=\"no-cache\">");
			html.append("<meta http-equiv=\"cache-control\" content=\"no-cache\">");
			html.append("<meta http-equiv=\"expires\" content=\"0\">");
			html.append("<meta http-equiv=\"keywords\" content=\"" + this.attribute("keywords", "") + "\">");
			html.append("<meta http-equiv=\"description\" content=\"" + this.attribute("description", "") + "\">");
			html.append("<script type=\"text/javascript\" src=\"" + this.attribute("mUrlHead")
					+ "/widgets/boot.js?version=" + version + "\"></script>");
			html.append("</head>");
			html.append("<body id=\"sept-main\" class=\"" + TagNames.TAG_BODY_CLASS + "\">");

			ArrayList<AbstractImpl> children = this.getChildrenImpl();

			for (int i = 0; i < children.size(); i++) {
				html.append(children.get(i).getHTML());
			}
			html.append("</body>");
			html.append("</html>");
		} else {
			html.append("  <div class=\"").append(TagNames.TAG_DIV_CLASS).append("\">");

			ArrayList<AbstractImpl> children = this.getChildrenImpl();

			for (int i = 0; i < children.size(); i++) {
				html.append(children.get(i).getHTML());
			}
			html.append("  </div>");
		}

		return html.toString();
	}

	public static void main(String[] args) throws JspException {
		BodyImpl bodyImpl = new BodyImpl();
		System.out.println(bodyImpl.getHTML());
	}
}
