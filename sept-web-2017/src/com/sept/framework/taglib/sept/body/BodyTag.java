package com.sept.framework.taglib.sept.body;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.html.HtmlTag;
import com.sept.framework.taglib.sept.AbstractTag;
import com.sept.framework.web.util.CookieUtil;

public class BodyTag extends AbstractTag {
	private static final long serialVersionUID = 1L;
	protected final static HashMap<String, String> styleMap = new HashMap<String, String>();

	private boolean root = false;// 是不是主页面，主页面要添加一些东西,
	private String style = "sept";
	private String title = "ANT-TAGLIB-API";
	private String pageIcon = "store/ico/ant.ico";
	private String description = null;
	private String keywords = null;
	// private String html = null;
	// private String layout = "absolute";//
	// relative(相对布局，可简写为r/R),absolute(绝对布局，a/A),horizontal(横向布局，h/H),vertical(纵向布局

	static {
		styleMap.put("black", "black");
		styleMap.put("bootstrap", "bootstrap");
		styleMap.put("default", "default");
		styleMap.put("gray", "gray");
		styleMap.put("material", "material");
		styleMap.put("metro", "metro");
		styleMap.put("sept", "sept");
		styleMap.put("insdep", "insdep");
		styleMap.put("cupertino", "ui-cupertino");
		styleMap.put("dark-hive", "ui-dark-hive");
		styleMap.put("pepper-grinder", "ui-pepper-grinder");
		styleMap.put("sunny", "ui-sunny");
	}

	@Override
	public void init() throws JspException {
		this.impl = new BodyImpl();
		this.setDiyPara("root");
		this.setDiyPara("style");
		this.setDiyPara("pageIcon");
		this.setDiyPara("description");
		this.setDiyPara("keywords");
		// this.setDiyPara("html");
		this.setDiyPara("layout");
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		// 不能有任何父标签
		if (null != this.parent) {
			throw new JspException("body标签的root属性为true只允许作为顶级标签,不能有父标签！");
		}

		return null;
	}

	@Override
	public int doAfterBody() throws JspException {
		// System.out.println("这里"+bodyContent.getString());
		return SKIP_BODY;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		String html = this.impl.genHTML();
		try {
			// System.out.println("这里"+bodyContent.getString());
			this.pageContext.getOut().print(html + bodyContent.getString());
		} catch (IOException e) {
			throw new JspException("body标签IO读写出错！");
		}
		return null;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String styleDefult = CookieUtil.getCookie(request, "_main_style_");
		if (null == styleDefult || styleDefult.trim().isEmpty()) {
			if (null == style || style.trim().isEmpty()) {
				this.style = "sept";
			} else {
				this.style = styleMap.get(style);
			}
		} else {
			this.style = styleDefult;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPageIcon() {
		return pageIcon;
	}

	public void setPageIcon(String pageIcon) {
		this.pageIcon = pageIcon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

}
