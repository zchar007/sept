package com.sept.framework.taglib.easyui.body;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptImpl;
import com.sept.support.util.StringUtil;

public class BodyTagImpl extends AbstractSeptImpl {
	private static final String DEFAULT_BQ_LAYOUT = "vertical";

	@Override
	protected String initHTML() throws JspException {
		StringBuffer html = new StringBuffer();
		html.append("<div id ='zc_div_body_"+StringUtil.getUUID()+"' class = 'zc-div-container' >");
		html.append( 		"<div id=\"callStackTraceInfo_"+StringUtil.getUUID()+"\" style=\"display:none;\" class = '__sept__debug__panel__'>"      );
		html.append( 			"<div class=\"__jspPath__\">"+this.getDiyPara("currentUrl","")+"</div>");
		html.append( 			"<div class=\"__callStack__\">"+this.getDiyPara("callStackJson","")+"</div>");
		html.append( 			"<div class=\"__request__\">"+this.getDiyPara("requestUrl","")+"</div>");
		html.append( 			"<div class=\"__json_data__\">"+this.getDiyPara("__JSON_DATA__","")+"</div>");
		html.append( 			"<div class=\"__json_data_type__\">"+this.getDiyPara("__JSON_DATA_TYPE__","")+"</div>");
		html.append( 		"</div>");
		ArrayList<AbstractSeptImpl> children = this.getChildren();
		// 设置存放信息的div，并赋予布局，r,a在此生效
		String layout = this.getDiyPara("layout", DEFAULT_BQ_LAYOUT);
		if ("relative,absolute".indexOf(layout) >= 0) {
			layout = DEFAULT_BQ_LAYOUT;
		}
		html.append("   <div  class=\"__inner__container__\" "
				+ "' style = 'position:" + layout + ";");
		if(Boolean.parseBoolean(this.getDiyPara("fit","true"))){
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
		
		
		
		html.append("</div>");
		return html.toString();
	}
}
