package com.sept.framework.taglib.sept.tab;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractTag;

public class TabTag extends AbstractTag {

	private static final long serialVersionUID = 1L;
	// plain boolean 设置为true时，将不显示控制面板背景。 false
	private boolean plain = false;
	// fit boolean 设置为true时，选项卡的大小将铺满它所在的容器。 false
	// border boolean 设置为true时，显示选项卡容器边框。 true
	private boolean border = true;
	// scrollIncrement number 选项卡滚动条每次滚动的像素值。 100
	private String scrollIncrement;
	// scrollDuration number 每次滚动动画持续的时间，单位：毫秒。 400
	private String scrollDuration;
	// tools array,selector 工具栏添加在选项卡面板头的左侧或右侧。可用的值有：1. 一个工具菜单数组，每个工具选项都和linkbutton相同。2. 一个指向<div/>容器工具菜单的选择器。 null
	private String tools;
	// toolPosition string 工具栏位置。可用值：'left','right'。（该属性自1.3.2版开始可用） right
	private String toolPosition = "right";
	// tabPosition string
	// 选项卡位置。可用值：'top','bottom','left','right'。（该属性自1.3.2版开始可用） top
	private String tabPosition = "top";
	// headerWidth number
	// 选项卡标题宽度，在tabPosition属性设置为'left'或'right'的时候才有效。（该属性自1.3.2版开始可用） 150
	private String headerWidth = "150";
	// tabWidth number 标签条的宽度。（该属性自1.3.4版开始可用） auto
	private String tabWidth = "auto";

	// tabHeight number 标签条的高度。（该属性自1.3.4版开始可用） 27
	private String tabHeight = "40";

	// selected number 初始化选中一个标签页。（该属性自1.3.5版开始可用） 0
	private int selected = 0;

	// showHeader boolean 设置为true时，显示标签页标题。（该属性自1.3.5版开始可用） true
	private boolean showHeader = true;

	// justified boolean 设置为true时，生成等宽标题选项卡。（该属性自1.4.2版开始可用） false
	private boolean justified = false;

	// narrow boolean 设置为true时，删除选项卡标题之间的空间。（该属性自1.4.2版开始可用） false
	private boolean narrow = false;
	// pill boolean 设置为true时，选项卡标题样式改为气泡状。（该属性自1.4.2版开始可用） false
	private boolean pill = false;

	// 方法
	// onLoad panel 在ajax选项卡面板加载完远程数据的时候触发。
	private String onLoad;
	// onSelect title,index 用户在选择一个选项卡面板的时候触发。
	private String onSelect;
	// onUnselect title,index 用户在取消选择一个选项卡面板的时候触发。（该属性自1.3.5版开始可用）
	private String onUnselect;
	// onBeforeClose title,index 在选项卡面板关闭的时候触发，返回false取消关闭操作。下面的例子展示了在关闭选项卡面板之前以何种方式显示确认对话框。
	private String onBeforeClose;
	// onClose title,index 在用户关闭一个选项卡面板的时候触发。
	private String onClose;
	// onAdd title,index 在添加一个新选项卡面板的时候触发。
	private String onAdd;
	// onUpdate title,index 在更新一个选项卡面板的时候触发。
	private String onUpdate;
	// onContextMenu e, title,index 在右键点击一个选项卡面板的时候触发。
	private String onContextMenu;

	@Override
	public void init() throws JspException {
		this.impl = new TabImpl();
		this.setFunctionPara("onLoad");
		this.setFunctionPara("onSelect");
		this.setFunctionPara("onUnselect");
		this.setFunctionPara("onBeforeClose");
		this.setFunctionPara("onClose");
		this.setFunctionPara("onAdd");
		this.setFunctionPara("onUpdate");
		this.setFunctionPara("onContextMenu");
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		if(null == this.getParent()){
			this.jspException("Tab标签必须含有父标签！");
		}
		
		return null;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		return null;
	}

	public boolean isPlain() {
		return plain;
	}

	public void setPlain(boolean plain) {
		this.plain = plain;
	}

	public boolean isBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public String getScrollIncrement() {
		return scrollIncrement;
	}

	public void setScrollIncrement(String scrollIncrement) {
		this.scrollIncrement = scrollIncrement;
	}

	public String getScrollDuration() {
		return scrollDuration;
	}

	public void setScrollDuration(String scrollDuration) {
		this.scrollDuration = scrollDuration;
	}

	public String getTools() {
		return tools;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	public String getToolPosition() {
		return toolPosition;
	}

	public void setToolPosition(String toolPosition) {
		this.toolPosition = toolPosition;
	}

	public String getTabPosition() {
		return tabPosition;
	}

	public void setTabPosition(String tabPosition) {
		this.tabPosition = tabPosition;
	}

	public String getHeaderWidth() {
		return headerWidth;
	}

	public void setHeaderWidth(String headerWidth) {
		this.headerWidth = headerWidth;
	}

	public String getTabWidth() {
		return tabWidth;
	}

	public void setTabWidth(String tabWidth) {
		this.tabWidth = tabWidth;
	}

	public String getTabHeight() {
		return tabHeight;
	}

	public void setTabHeight(String tabHeight) {
		this.tabHeight = tabHeight;
	}


	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public boolean isShowHeader() {
		return showHeader;
	}

	public void setShowHeader(boolean showHeader) {
		this.showHeader = showHeader;
	}

	public boolean isJustified() {
		return justified;
	}

	public void setJustified(boolean justified) {
		this.justified = justified;
	}

	public boolean isNarrow() {
		return narrow;
	}

	public void setNarrow(boolean narrow) {
		this.narrow = narrow;
	}

	public boolean isPill() {
		return pill;
	}

	public void setPill(boolean pill) {
		this.pill = pill;
	}

	public String getOnLoad() {
		return onLoad;
	}

	public void setOnLoad(String onLoad) {
		this.onLoad = onLoad;
	}

	public String getOnSelect() {
		return onSelect;
	}

	public void setOnSelect(String onSelect) {
		this.onSelect = onSelect;
	}

	public String getOnUnselect() {
		return onUnselect;
	}

	public void setOnUnselect(String onUnselect) {
		this.onUnselect = onUnselect;
	}

	public String getOnBeforeClose() {
		return onBeforeClose;
	}

	public void setOnBeforeClose(String onBeforeClose) {
		this.onBeforeClose = onBeforeClose;
	}

	public String getOnClose() {
		return onClose;
	}

	public void setOnClose(String onClose) {
		this.onClose = onClose;
	}

	public String getOnAdd() {
		return onAdd;
	}

	public void setOnAdd(String onAdd) {
		this.onAdd = onAdd;
	}

	public String getOnUpdate() {
		return onUpdate;
	}

	public void setOnUpdate(String onUpdate) {
		this.onUpdate = onUpdate;
	}

	public String getOnContextMenu() {
		return onContextMenu;
	}

	public void setOnContextMenu(String onContextMenu) {
		this.onContextMenu = onContextMenu;
	}

}
