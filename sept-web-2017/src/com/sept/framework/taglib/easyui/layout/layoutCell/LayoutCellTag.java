package com.sept.framework.taglib.easyui.layout.layoutCell;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.easyui.AbstractSeptTag;
import com.sept.framework.taglib.easyui.layout.LayoutTag;

public class LayoutCellTag extends AbstractSeptTag {
	private static final long serialVersionUID = 1L;

	// title string 布局面板标题文本。 null
	private String title = null;
	// region string 定义布局面板位置，可用的值有：north, south, east, west, center。
	private String region = null;
	// border boolean 为true时显示布局面板边框。 true
	private boolean border = true;
	// split boolean 为true时用户可以通过分割栏改变面板大小。 false
	private boolean split = false;
	// iconCls string 一个包含图标的CSS类ID，该图标将会显示到面板标题上。 null
	private String iconCls = null;
	// href string 用于读取远程站点数据的URL链接 null
	// private String href = null;
	// collapsible boolean 定义是否显示折叠按钮。（该属性自1.3.3版开始可用） true
	private boolean collapsible = true;
	// minWidth number 最小面板宽度。（该属性自1.3.3版开始可用） 10
	private int minWidth = 10;
	// minHeight number 最小面板高度。（该属性自1.3.3版开始可用） 10
	private int minHeight = 10;
	// maxWidth number 最大面板宽度。（该属性自1.3.3版开始可用） 10000
	private int maxWidth = 10000;
	// maxHeight number 最大面板高度。（该属性自1.3.3版开始可用） 10000
	private int maxHeight = 10000;
	// expandMode string 在点击折叠面板时候的扩展模式。可用值有：“float”、“dock”和null
	// float：区域面板将展开并浮动在顶部，在鼠标焦点离开面板时会自动隐藏。
	// dock：区域面板将展开并钉在面板上，在鼠标焦点离开面板时不会自动隐藏。
	// null：什么也不会发生。
	// （该属性自1.4.4版开始可用）
	private String expandMode = "float";
	// collapsedSize number 折叠后的面板大小。（该属性自1.4.4版开始可用） 28
	private int collapsedSize = 28;
	// hideExpandTool boolean 为true时隐藏折叠面板上的扩展面板工具。（该属性自1.4.4版开始可用） false
	private boolean hideExpandTool = false;
	// hideCollapsedContent boolean 为true时隐藏折叠面板上的标题栏。（该属性自1.4.4版开始可用） true
	private boolean hideCollapsedContent = true;// 貌似没怎么又用
	// collapsedContent string,function(title) 定义在折叠面板上要显示标题内容。
	// 1. 标题字符串；
	// 2. 通过函数返回标题内容。
	// （该方法自1.4.4版开始可用）
	// 代码示例：
	//
	// collapsedContent: function(title){
	// var region = $(this).panel('options').region;
	// if (region == 'north' || region == 'south'){
	// return title;
	// } else {
	// return '<div class="mytitle">'+title+'</div>';
	// }
	// }
	private String collapsedContent = null;// 不知道怎么用啊

	@Override
	public void init() throws JspException {
		this.impl = new LayoutCellTagImpl();

	}

	@Override
	protected void checkParent() throws JspException {
		if (null == this.parent) {
			throw new JspException("LayoutCell标签必须含有父标签！");
		}
		if (!(this.parent instanceof LayoutTag)) {
			throw new JspException("LayoutCell标签的父标签必须为Layout！");
		}

	}

	@Override
	protected void checkParam() throws JspException {
		if (null == this.getName() || this.getName().trim().isEmpty()) {
			throw new JspException("Layout标签name属性为必填！");
		}
		if (null == this.getRegion() || this.getRegion().trim().isEmpty()) {
			throw new JspException("Layout标签region属性为必填！");
		}
		String regionTemp = this.getRegion().toLowerCase();
		if ("north".startsWith(regionTemp)) {
			this.setRegion("north");
		} else if ("south".startsWith(regionTemp)) {
			this.setRegion("south");
		} else if ("east".startsWith(regionTemp)) {
			this.setRegion("east");
		} else if ("west".startsWith(regionTemp)) {
			this.setRegion("west");
		} else if ("center".startsWith(regionTemp)) {
			this.setRegion("center");
		} else {
			throw new JspException("Layout标签当前region属性为--" + this.getRegion()
					+ ",不合法！");
		}
		if (null != this.getUrl() && !this.getUrl().trim().isEmpty()) {
			this.addPara("href", this.getUrl());
		}
	}

	@Override
	protected void doOther() throws JspException {
		// TODO 自动生成的方法存根

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public boolean isBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public boolean isSplit() {
		return split;
	}

	public void setSplit(boolean split) {
		this.split = split;
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

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public String getExpandMode() {
		return expandMode;
	}

	public void setExpandMode(String expandMode) {
		this.expandMode = expandMode;
	}

	public int getCollapsedSize() {
		return collapsedSize;
	}

	public void setCollapsedSize(int collapsedSize) {
		this.collapsedSize = collapsedSize;
	}

	public boolean isHideExpandTool() {
		return hideExpandTool;
	}

	public void setHideExpandTool(boolean hideExpandTool) {
		this.hideExpandTool = hideExpandTool;
	}

	public boolean isHideCollapsedContent() {
		return hideCollapsedContent;
	}

	public void setHideCollapsedContent(boolean hideCollapsedContent) {
		this.hideCollapsedContent = hideCollapsedContent;
	}

	public String getCollapsedContent() {
		return collapsedContent;
	}

	public void setCollapsedContent(String collapsedContent) {
		this.collapsedContent = collapsedContent;
	}

}
