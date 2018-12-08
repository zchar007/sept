package com.sept.web.taglib.common.body;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import com.sept.web.taglib.AbstractTag;
import com.sept.web.taglib.TagNames;

public class BodyTag extends AbstractTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String skin = null;
	private String root = null;// 是不是主页面，主页面要添加一些东西,
	private String title = null;
	private String pageIcon = null;
	private String description = null;
	private String keywords = null;

	@Override
	public void start() throws JspException {
		this.impl = new BodyImpl();
	}

	@Override
	public void end() throws JspException {
		if (null != this.parent) {
			throw new JspException("body标签必须作为顶级标签！");
		}
		try {
			String html = this.impl.getHTML();
			this.pageContext.getOut().print(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getSKin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
		Cookie cookie = new Cookie(TagNames.COOKIE_SKIN_NAME, this.skin);
		response.addCookie(cookie);
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
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

	@Override
	public void beforStart() throws JspException {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforEnd() throws JspException {
		// TODO Auto-generated method stub

	}

}
