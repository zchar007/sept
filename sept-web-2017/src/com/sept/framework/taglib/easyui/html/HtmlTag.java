package com.sept.framework.taglib.easyui.html;

import java.util.HashMap;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptTag;

public class HtmlTag extends AbstractSeptTag {
	private static final long serialVersionUID = 1L;
	private final static HashMap<String, String> styleMap = new HashMap<String, String>();
	private String title = "ANT-TAGLIB-API";
	private String pageIcon = "store/ico/ant.ico";
	private String description = null;
	private String keywords = null;
	private String html = null;
	private String layout = "absolute";// relative(相对布局，可简写为r/R),absolute(绝对布局，a/A),horizontal(横向布局，h/H),vertical(纵向布局
										// v/V)
	//
	static {
		styleMap.put("black", "black/easyui.css");
		styleMap.put("bootstrap", "bootstrap/easyui.css");
		styleMap.put("default", "default/easyui.css");
		styleMap.put("gray", "gray/easyui.css");
		styleMap.put("material", "material/easyui.css");
		styleMap.put("metro", "metro/easyui.css");
		styleMap.put("ant", "ant/easyui.css");
	}

	@Override
	public void init() throws JspException {
		this.impl = new HtmlTagImpl();
		this.setDiyPara("title");
		this.setDiyPara("pageIcon");
		this.setDiyPara("description");
		this.setDiyPara("keywords");
		this.setDiyPara("html");
		this.setDiyPara("layout");
	}

	@Override
	protected void checkParent() throws JspException {
		if (null != this.parent) {
			this.jspException("标签[html]必须作为顶级标签！");
		}
	}

	@Override
	protected void checkParam() throws JspException {
		// 设置style
		if (null == this.getStyle() || this.getStyle().trim().isEmpty()) {
			this.setStyle("ant");
		}
		if (!HtmlTag.styleMap.containsKey(this.getStyle())) {
			this.setStyle("ant");
		}
		this.setStyle(HtmlTag.styleMap.get(this.getStyle()));
		if (null == this.getTitle() || this.title.trim().isEmpty()) {
			this.setTitle("ANT-FRAMEWORK");
		}
		String lay = this.getLayout();
		if (null == lay || lay.trim().isEmpty()) {
			lay = lay.toLowerCase();
			// relative(相对布局，可简写为r/R),absolute(绝对布局，a/A),horizontal(横向布局，h/H),vertical(纵向布局
			// v/V)
			if ("relative".startsWith(lay)) {
				this.setLayout("relative");
			}
			if ("absolute".startsWith(lay)) {
				this.setLayout("absolute");
			}
			if ("horizontal".startsWith(lay)) {
				this.setLayout("horizontal");
			}
			if ("vertical".startsWith(lay)) {
				this.setLayout("vertical");
			}
		}

	}

	@Override
	public int doAfterBody() throws JspException {
		this.setHtml(bodyContent.getString());
		return SKIP_BODY;
	}

	@Override
	protected void doOther() throws JspException {
		try {
			String html = this.impl.genHTML();
			String js = this.impl.getJsStr();
			String outH = html + "\n" + js + "\n";
			this.pageContext.getOut().print(outH);
		} catch (Exception e) {
			this.jspException(e);
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

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

}
