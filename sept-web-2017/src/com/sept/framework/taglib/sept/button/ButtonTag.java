package com.sept.framework.taglib.sept.button;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractTag;

public class ButtonTag extends AbstractTag{
	private static final long serialVersionUID = 1L;
	// width number 组件的宽度。（该属性自1.4版开始可用） null
	// height number 组件的高度。（该属性自1.4版开始可用） null
	// id string 组件的ID属性。 null -name
	// disabled boolean 为true时禁用按钮。 false
	private boolean disabled = false;
	// toggle boolean 为true时允许用户切换其状态是被选中还是未选择，可实现checkbox复选效果。（该属性自1.3.3版开始可用）
	// false
	private boolean toggle = false;
	// selected boolean 定义按钮初始的选择状态，true为被选中，false为未选中。（该属性自1.3.3版开始可用） false
	private boolean selected = false;
	// group string 指定相同组名称的按钮同属于一个组，可实现radio单选效果。（该属性自1.3.3版开始可用） null
	private String group = null;
	// plain boolean 为true时显示简洁效果。 false
	private boolean plain = false;
	// text string 按钮文字。 '' --labelValue
	// iconCls string 显示在按钮文字左侧的图标(16x16)的CSS类ID。 null
	private String iconCls = null;
	// iconAlign string
	// 按钮图标位置。可用值有：'left','right'（该属性自1.3.2版开始可用）'top','bottom'（该属性自1.3.6版开始可用）
	// left
	private String iconAlign = "left";
	// size string 按钮大小。可用值有：'small','large'。（该属性自1.3.6版开始可用） small
	private String size = "small";

	private String onClick = null;

	@Override
	public void init() throws JspException {
		this.impl = new ButtonImpl();
		this.setFunctionPara("onClick");
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		if (null == this.getParent()) {
			this.jspException("Button标签必须含有父标签！");
		}
		if (null != this.getOnClick() && !this.getOnClick().trim().isEmpty()) {
			this.setOnClick("function(){" + this.getOnClick() + "()}");
		}
		return null;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		// TODO 张超 Auto-generated method stub
		return null;
	}

	public boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean getToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public boolean getPlain() {
		return plain;
	}

	public void setPlain(boolean plain) {
		this.plain = plain;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getIconAlign() {
		return iconAlign;
	}

	public void setIconAlign(String iconAlign) {
		this.iconAlign = iconAlign;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

}
