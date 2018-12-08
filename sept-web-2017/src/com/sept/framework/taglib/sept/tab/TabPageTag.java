package com.sept.framework.taglib.sept.tab;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractTag;

public class TabPageTag extends AbstractTag {

	private static final long serialVersionUID = 1L;
	// id string 选项卡面板的ID属性。 null
	private String id;
	// title string 选项卡面板的标题文本。
	private String title;
	// content string 选项卡面板的内容。
	private String content;
	// href string 从URL加载远程数据内容填充到选项卡面板。 null
	private String href;
	// cache boolean 如果为true，在'href'属性设置了有效值的时候缓存选项卡面板。 true
	private boolean cache = true;
	// iconCls string 定义了一个图标的CSS类ID显示到选项卡面板标题。 null
	private String iconCls;
	// collapsible boolean 如果为true，则允许选项卡摺叠。 false
	private boolean collapsible = false;
	// closable boolean 在设置为true的时候，选项卡面板将显示一个关闭按钮，在点击的时候会关闭选项卡面板。 false
	private boolean closable = false;
	// selected boolean 在设置为true的时候，选项卡面板会被选中。 false
	private boolean selected = false;
	// disabled boolean 在设置为true的时候，选项卡面板会被禁用。（该属性自1.4.4版开始可用） false
	private boolean disabled = false;

	@Override
	public void init() throws JspException {
		this.impl = new TabPageImpl();
		this.setDiyPara("id");
		this.setDiyPara("title");
		this.setDiyPara("id");
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		if (null == this.getParent()) {
			this.jspException("TabPage标签必须含有父标签！");
		}
		if (!(this.getParent() instanceof TabTag)) {
			this.jspException("TabPage标签父标签必须是Tab！");
		}
		return null;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isCollapsible() {
		return collapsible;
	}

	public void setCollapsible(boolean collapsible) {
		this.collapsible = collapsible;
	}

	public boolean isClosable() {
		return closable;
	}

	public void setClosable(boolean closable) {
		this.closable = closable;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}
