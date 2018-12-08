package com.sept.framework.taglib.sept.body;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractImpl;
import com.sept.support.util.DateUtil;

public class BodyImpl extends AbstractImpl {
	@Override
	public String genHTML() throws JspException {
		StringBuffer html = new StringBuffer();
		String version= DateUtil.getCurrentDate("yyyyMMddhhmmss");
		if(this.getBooleanPara("root")){
			html.append("<html>");
			html.append("<head>");
			html.append("<base href=\""+this.getStringPara("currentUrl","")+"\">");
			html.append("<title>"+this.getStringPara("title","")+"</title>");
			// 图标
			html.append("<link rel=\"shortcut icon\" href=\""+ this.getStringPara("urlHead")+ this.getStringPara("pageIcon", "store/ico/sept.ico")+ "\" type=\"image/x-icon\">");
			html.append("<meta http-equiv=\"pragma\" content=\"no-cache\">");
			html.append("<meta http-equiv=\"cache-control\" content=\"no-cache\">");
			html.append("<meta http-equiv=\"expires\" content=\"0\">");
			html.append("<meta http-equiv=\"keywords\" content=\""+this.getStringPara("keywords","")+"\">");
			html.append("<meta http-equiv=\"description\" content=\""+this.getStringPara("description","")+"\">");
			html.append("<script type=\"text/javascript\" src=\""+ this.getStringPara("urlHead") + "/sept/boot.js?version="+version+"\"></script>");
			html.append("<script type=\"text/javascript\"  src=\""+ this.getStringPara("urlHead") + "/sept/boot.javascript.js?version="+version+"\"></script>");
			html.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ this.getStringPara("urlHead") + "sept/themes/sept/global/sept.body.css?version="+version+"\">");
			html.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ this.getStringPara("urlHead") + "sept/themes/sept/global/buttons.css?version="+version+"\">");
			html.append("<link id = \"_easyui_Theme_\" rel=\"stylesheet\" type=\"text/css\" href=\""+ this.getStringPara("urlHead") + "sept/themes/"+this.getStringPara("style")+"/easyui.css?version="+version+"\">");
			html.append("<link id = \"_easyui_Theme_icon_\" rel=\"stylesheet\" type=\"text/css\" href=\""+ this.getStringPara("urlHead") + "sept/themes/"+this.getStringPara("style")+"/icon.css?version="+version+"\">");
			html.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ this.getStringPara("urlHead") + "sept/themes/"+this.getStringPara("style")+"/icon.css?version="+version+"\">");
			html.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ this.getStringPara("urlHead") + "sept/themes/insdep/insdep_theme_default.css?version="+version+"\">");
			html.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ this.getStringPara("urlHead") + "sept/themes/icon.css?version="+version+"\">");
			html.append("</head>");
			html.append("<body class=\"sept-body-container\">");
			html.append("  <div class=\"sept-div-container\" style=\"position:absolute;\">");
			html.append(this.getPageInfo());

			ArrayList<AbstractImpl> children = this.getChildren();
	
			for (int i = 0; i < children.size(); i++) {
				html.append(children.get(i).genHTML());
			}
			html.append("  </div>");

			html.append("	<div id=\"_sys_menu_\" class=\"easyui-menu\" data-options=\"\"");
			html.append("          >");
			html.append("	</div>");





			html.append("</body>");
			html.append("</html>");
			//js
			html.append("<script>");
//			html.append("	function menuHandler(item) {");
//			html.append("		alert('Click Item: ');");
//			html.append("	}");
//			html.append("	$(function() {");
//			html.append("   	 $(document).bind('contextmenu', function(e) {");
//			html.append("     	 e.preventDefault();");
//			html.append("      	$('#mm').menu('show', {");
//			html.append("      	  left : e.pageX,");
//			html.append("      	  top : e.pageY");
//			html.append("      	});");
//			html.append("   	 });");
//			html.append(" 	 });");
			html.append("</script>");


		}else{
			html.append("  <div class=\"sept-div-container\">");
			html.append(this.getPageInfo());

			ArrayList<AbstractImpl> children = this.getChildren();
	
			for (int i = 0; i < children.size(); i++) {
				html.append(children.get(i).genHTML());
			}
			html.append("  </div>");
		}
		return html.toString();
	}


}
