package com.sept.web.taglib;

import java.lang.reflect.Field;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.sept.util.StringUtil;

public abstract class AbstractTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected AbstractImpl impl = null;
	protected AbstractTag parent;
	private HashMap<String, String> attributes = null;
	private String id = null;// 控件唯一标识符。
	private String name = null;// 控件名称。
	private String visible = null;// 是否显示控件
	private String enabled = null;// 是否禁用控件
	private String width = null;// 宽度
	private String height = null;// 高度
	private String cls = null;// 样式类
	private String style = null;// 样式
	private String borderStyle = null;// 边框样式。针对datagrid,panel,textbox,combobox等。
	private String tooltip = null;//提示信息	

	private String mUrlHead = null;

	@Override
	public int doStartTag() throws JspException {
		this.attributes = new HashMap<>();
		this.beforStart();
		this.setId(StringUtil.getUUID());
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		this.setmUrlHead("http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() // 端口号
				+ request.getContextPath() + "/");// 项目名称
		this.start();
		return super.doStartTag();
	}
	public abstract void beforStart() throws JspException;

	public abstract void start() throws JspException;

	@Override
	public int doEndTag() throws JspException {
		this.beforEnd();
		// 获取父标签
		this.parent = (AbstractTag) getParent();

		this.initAttribute();
		// 如果有父标签，吧自己和福标签联系起来
		if (null != this.parent) {
			this.parent.getImpl().addChild(this.impl);
		}
		this.end();
		return super.doEndTag();
	}

	public abstract void beforEnd() throws JspException;


	public abstract void end() throws JspException;

	/**
	 * 设置Attribute
	 */
	private void initAttribute() {
		try {
			Class<? extends AbstractTag> classz = this.getClass();
			Field[] fields = classz.getDeclaredFields();// 只会得到共有变量名

			Field.setAccessible(fields, true);
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName();
				Object value = fields[i].get(this);
				int type = fields[i].getModifiers();
				if (2 == type) {
					this.setAttribute(name, (String) value);
				}
			}
			this.impl.setAttributes(this.attributes);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		this.attributes.put("id", this.id);
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
		this.attributes.put("visible", this.visible);
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
		this.attributes.put("enabled", this.enabled);
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
		this.attributes.put("cls", this.cls);
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
		this.attributes.put("style", this.style);
	}

	public String getBorderStyle() {
		return borderStyle;
	}

	public void setBorderStyle(String borderStyle) {
		this.borderStyle = borderStyle;
		this.attributes.put("borderStyle", this.borderStyle);
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
		this.attributes.put("tooltip", this.tooltip);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.attributes.put("name", this.name);
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
		this.attributes.put("width", this.width);

	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
		this.attributes.put("height", this.height);

	}

	public void setAttribute(String key, String value) {
		if (null == this.attributes) {
			this.attributes = new HashMap<>();
		}
		this.attributes.put(key, value);
	}

	public HashMap<String, String> getAttributes() {
		if (null == this.attributes) {
			this.attributes = new HashMap<>();
		}
		return attributes;
	}

	public AbstractImpl getImpl() {
		return impl;
	}

	public void setImpl(AbstractImpl impl) {
		this.impl = impl;
	}

	public String getmUrlHead() {
		return mUrlHead;
	}

	public void setmUrlHead(String mUrlHead) {
		this.mUrlHead = mUrlHead;
		this.attributes.put("mUrlHead", this.mUrlHead);
	}

}
