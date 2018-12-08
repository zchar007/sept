package com.sept.framework.taglib.easyui.html;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptImpl;
import com.sept.support.util.StringUtil;

public class HtmlTagImpl extends AbstractSeptImpl {
	private static final String DEFAULT_DIY_LAYOUT = "absolute";
	private static final String DEFAULT_BQ_LAYOUT = "vertical";

	@Override
	public String genHTML() throws JspException {
		return this.initHTML();
	}

	@Override
	protected String initHTML() throws JspException {
		String version = new Date().getTime() + "";

		StringBuffer html = new StringBuffer();
		html.setLength(0);
		html.append("<html>");
		html.append("  <head>");
		html.append("    <base href=\"" + this.getDiyPara("currentUrl") + "\">");
		// 标题
		html.append("    <title>" + this.getDiyPara("title", "SEPT-TAGLIB-API")
				+ "</title>");
		// 图标
		html.append("<link rel=\"shortcut icon\" href=\""
				+ this.getDiyPara("urlHead")
				+ this.getDiyPara("pageIcon", "store/ico/sept.ico")
				+ "\" type=\"image/x-icon\">");
		// 关键词
		html.append("    <meta http-equiv=\"keywords\" content=\""
				+ this.getDiyPara("keywords", "") + "\">");
		// 描述
		html.append("    <meta http-equiv=\"description\" content=\""
				+ this.getDiyPara("description", "") + "\">");
		// css
		html.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ this.getDiyPara("urlHead") + "sept/themes/"
				+ this.getDiyPara("style") + "?version=" + version + "\">\n");
		// 让html,body的边距为0
		html.append("<style>\n");
		html.append("html,body {\n");
		html.append("\tmargin: 0;\n");
		html.append("\tpadding: 0;\n");
		html.append("\tborder: 0;\n");
		html.append("}\n");
		html.append("</style>\n");
		// script
		html.append("<script type=\"text/javascript\" src=\""
				+ this.getDiyPara("urlHead") + "sept/boot.js?version="
				+ version + "\" ></script>\n");
		html.append("<script type=\"text/javascript\"  src=\""+ this.getDiyPara("urlHead") + "/sept/boot.javascript.js\"></script>");
		html.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ this.getDiyPara("urlHead") + "sept/themes/sept/global/sept.body.css\">");
		html.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ this.getDiyPara("urlHead") + "sept/themes/sept/global/buttons.css\">");
		html.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ this.getDiyPara("urlHead") + "sept/themes/sept/easyui.css\">");
		html.append("  </head>");
		html.append("  <body id = 'zc_body_" + StringUtil.getUUID()
				+ "' class = 'zc-body-container'>");
		html.append("<div id=\"callStackTraceInfo_"
				+ StringUtil.getUUID()
				+ "\" style=\"display:none;\" class = '__sept__debug__panel__'>");
		html.append("<div class=\"__jspPath__\">"
				+ this.getDiyPara("currentUrl", "") + "</div>");
		html.append("<div class=\"__callStack__\">"
				+ this.getDiyPara("callStackJson", "") + "</div>");
		html.append("<div class=\"__request__\">"
				+ this.getDiyPara("requestUrl", "") + "</div>");
		html.append("<div class=\"__json_data__\">"
				+ this.getDiyPara("__JSON_DATA__", "") + "</div>");
		html.append("<div class=\"__json_data_type__\">"
				+ this.getDiyPara("__JSON_DATA_TYPE__", "") + "</div>");
		html.append("</div>");
		ArrayList<AbstractSeptImpl> children = this.getChildren();
		if (children.size() == 0) {
			// 设置存放信息的div，并赋予布局，r,a在此生效
			String layout = this.getDiyPara("layout", DEFAULT_DIY_LAYOUT);
			if ("horizontal,vertical".indexOf(layout) >= 0) {
				layout = DEFAULT_DIY_LAYOUT;
			}
			html.append("   <div  class=\"__main__container__\" "
					+ " style = 'position:" + layout + ";");
			if (Boolean.parseBoolean(this.getDiyPara("fit", "true"))) {
				html.append("width:100%;height:100%;overflow: hidden;");
			}
			html.append("'>");

			// 有自定义标签则只用自定义标签，否则，用html代码

			html.append(this.getDiyPara("html", ""));
		} else {
			// 设置存放信息的div，并赋予布局，r,a在此生效
			String layout = this.getDiyPara("layout", DEFAULT_BQ_LAYOUT);
			if ("relative,absolute".indexOf(layout) >= 0) {
				layout = DEFAULT_BQ_LAYOUT;
			}
			html.append("   <div  class=\"__main__container__\" "
					+ " style = 'position:" + layout + ";");
			if (Boolean.parseBoolean(this.getDiyPara("fit", "true"))) {
				html.append("width:100%;height:100%;");
			}
			html.append("'>");
			if ("vertical".equals(layout)) {
				for (int i = 0; i < children.size(); i++) {
					html.append(children.get(i).genHTML() + "\n");
				}
			} else {
				html.append("<table border = '0' width='100%' style='background-color:rgba(255,255,255,0.15)' ><tr>");

				for (int i = 0; i < children.size(); i++) {
					String width = children.get(i).getDiyPara("width", null);
					String height = children.get(i).getDiyPara("height", null);
					children.get(i).setPara("width", "100%");
					String htmlT = children.get(i).genHTML();

					// System.out.println(width+":"+height);
					boolean hasWidth = true, hasHeight = true;
					if (null != width && !width.trim().isEmpty()) {
						if (!width.endsWith("%") && !width.endsWith("px")) {
							width += "px";
							hasWidth = true;
						}
					} else {
						hasWidth = false;
					}
					if ("100%".equals(width)) {
						width = null;
						hasWidth = false;
					}
					if (null != height && !height.trim().isEmpty()) {
						if (!height.endsWith("%") && !height.endsWith("px")) {
							height += "px";
							hasHeight = true;
						}
					} else {
						hasHeight = false;
					}
					html.append("<td ");

					if (hasWidth) {
						html.append(" width='" + width + "'");
					}
					if (hasHeight) {
						html.append(" height='" + height + "'");
					}
					html.append(" >");
					html.append(htmlT + "\n");
					html.append("</td>");
				}
				html.append("</tr></table>");
			}
		}

		html.append("</div>  </body>");
		html.append("</html>");

		return html.toString();
	}
}
