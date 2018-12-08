package com.sept.framework.taglib.easyui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.jsp.JspException;

import com.sept.exception.AppException;

/**
 * 每一个
 * 
 * @author zchar
 * 
 */
public abstract class AbstractSeptImpl {
	private HashMap<String, Object> Params = null;
	private ArrayList<AbstractSeptImpl> childrenImpl = null;
	private HashSet<String> noQuoteList = null; // 没有引号的集合，对于没有引号的集合，所有的boolean全部是没引号的，这在setParam中会自动执行
	private HashSet<String> diyParaList = null; // 自定义参数的集合
	private String jsStr = null;

	/**
	 * 对于非块状接口，必须重写此方法 对于块状结构，每一个都默认盛满此div
	 * 
	 * @return
	 * @throws JspException
	 */
	public String genHTML() throws JspException {
		String inHtml = this.initHTML();
		StringBuffer html = new StringBuffer();
		try {
			if (null == this.childrenImpl) {
				this.childrenImpl = new ArrayList<AbstractSeptImpl>();
			}
			// 获取width,height,left,top支持带%或者带px，不带则默认成px
			String width = this.getDiyPara("width", "100%");
			String height = this.getDiyPara("height", null);
			String left = this.getDiyPara("left", null);
			String top = this.getDiyPara("top", null);
			if (null != width && !width.trim().isEmpty()) {
				if (!width.endsWith("%") && !width.endsWith("px")) {
					width += "px";
				}
			}
			if (null != height && !height.trim().isEmpty()) {
				if (!height.endsWith("%") && !height.endsWith("px")) {
					height += "px";
				}
			}
			if (null != left && !left.trim().isEmpty()) {
				if (!left.endsWith("%") && !left.endsWith("px")) {
					left += "px";
				}
			}
			if (null != top && !top.trim().isEmpty()) {
				if (!top.endsWith("%") && !top.endsWith("px")) {
					top += "px";
				}
			}
			// 不使用top，left等则默认相对布局
			if ((null != top && !top.trim().isEmpty())
					|| (null != left && !left.trim().isEmpty())) {
				html.append("<div style=\"position:absolute;margin-left: "
						+ left + ";margin-top: " + top + ";");
			} else {
				html.append("<div style=\"position:relative;");
			}
			if (null != width && !width.trim().isEmpty()) {
				html.append("width:" + width + ";");
			}
			if (null != height && !height.trim().isEmpty()) {
				html.append("height:" + height + ";");
			}
			//System.out.println(width+"--"+height);
			// 如果宽高有一个不为空，就把overflow加上
			if (!((null == width || width.trim().isEmpty()) && !(null == height || height
					.trim().isEmpty()))) {
				html.append("overflow: auto;");
			}else{
				html.append("overflow: hidden;");

			}
			html.append("display:"
					+ (Boolean.parseBoolean(this.getDiyPara("hidden", "false")) ? "none"
							: "") + ";");

			if (null != this.getDiyPara("style", null)
					&& !this.getDiyPara("style", "").trim().isEmpty()) {
				html.append(this.getDiyPara("style"));
				// System.out.println(this.getDiyPara("style"));
			} else {
				html.append("background-color:rgba(255,255,255,0)");
			}
			html.append("\">");
			html.append(inHtml);
			html.append("</div>");
		} catch (Exception e) {
			throw new JspException(e);
		}
		return html.toString();
	}

	protected abstract String initHTML() throws JspException;

	// private int width;//关于高宽，之后再决定怎么处理
	/**
	 * 添加子impl
	 * 
	 * @param child
	 * @throws JspException
	 */
	public void addChild(AbstractSeptImpl child) throws JspException {
		if (null == this.childrenImpl) {
			this.childrenImpl = new ArrayList<AbstractSeptImpl>();
		}
		if (null == child) {
			throw new JspException("要添加的第【" + this.childrenImpl.size()
					+ "】个子标签序为空!");
		}
		this.childrenImpl.add(child);

	}

	/**
	 * 按序号移除子impl
	 * 
	 * @param index
	 * @throws JspException
	 */
	public void removeChild(int index) throws JspException {
		if (index < 0) {
			throw new JspException("要删除的子标签序列号【" + index + "】小于0!");
		}
		if (null == this.childrenImpl) {
			this.childrenImpl = new ArrayList<AbstractSeptImpl>();
		}
		this.childrenImpl.remove(index);
	}

	/**
	 * 按impl移除子impl
	 * 
	 * @param child
	 * @throws JspException
	 */
	public void removeChild(AbstractSeptImpl child) throws JspException {
		if (null == this.childrenImpl) {
			this.childrenImpl = new ArrayList<AbstractSeptImpl>();
		}
		if (null == child) {
			throw new JspException("要删除的第【" + this.childrenImpl.size()
					+ "】个子标签序为空!");
		}
		this.childrenImpl.remove(child);
	}

	/**
	 * 设置参数集合
	 * 
	 * @param param
	 * @throws JspException
	 */
	@SuppressWarnings("unchecked")
	public void setParams(HashMap<String, Object> param) throws JspException {
		if (null == param) {
			throw new JspException("Tag要为Impl设置参数时，DataObject为null!");
		}
		this.Params = (HashMap<String, Object>) param.clone();
	}

	public HashSet<String> getNoQuoteList() {
		if (null == this.noQuoteList) {
			this.noQuoteList = new HashSet<String>();
		}
		return this.noQuoteList;
	}

	public void setNoQuoteList(HashSet<String> noQuoteList) {
		if (null == noQuoteList) {
			noQuoteList = new HashSet<String>();
		}
		this.noQuoteList = noQuoteList;
	}

	public HashSet<String> getDiyParaList() {
		if (null == this.diyParaList) {
			this.diyParaList = new HashSet<String>();
		}
		return this.diyParaList;
	}

	public void setDiyParaList(HashSet<String> diyParaList) {
		if (null == diyParaList) {
			diyParaList = new HashSet<String>();
		}
		this.diyParaList = diyParaList;
	}

	/**
	 * 获取easyui需要的json参数
	 * 
	 * @return
	 * @throws JspException
	 * @throws AppException
	 */
	protected String getEUIOptions() {
		if (null == this.Params) {
			this.Params = new HashMap<String, Object>();
		}
		String data_options = "";
		for (String name : this.Params.keySet()) {
			// 非自定义参数才加入到options中
			if (!this.diyParaList.contains(name)) {
				if (this.noQuoteList.contains(name)) {
					data_options += name + ":"
							+ this.Params.get(name).toString() + ",";
				} else {
					data_options += name + ":'"
							+ this.Params.get(name).toString() + "',";
				}
			}
		}
		if (data_options.length() > 0) {
			data_options = data_options.substring(0, data_options.length() - 1);
		}
		return data_options;
	}

	/**
	 * 从自定义参数中获取参数
	 * 
	 * @param key
	 * @return
	 * @throws JspException
	 * @throws AppException
	 */
	public String getDiyPara(String key) throws JspException {
		if (!this.Params.containsKey(key)) {
			throw new JspException("不存在参数【" + key + "】");
		}
		if (!this.diyParaList.contains(key)) {
			throw new JspException("不存在自定义参数【" + key + "】");
		}

		if (null == this.Params.get(key)) {
			return null;
		}
		return this.Params.get(key).toString();
	}

	/**
	 * 从自定义参数中获取参数
	 * 
	 * @param key
	 * @return
	 * @throws JspException
	 * @throws AppException
	 */
	public void setPara(String key, Object value) throws JspException {

		this.Params.put(key, value);
	}

	/**
	 * 从自定义参数中获取参数
	 * 
	 * @param key
	 * @return
	 * @throws JspException
	 * @throws AppException
	 */
	public void removePara(String key) throws JspException {

		Object b = this.Params.remove(key);
		System.out.println(b + "--" + this.Params.keySet());
	}

	/**
	 * 从自定义参数中获取参数
	 * 
	 * @param key
	 * @return
	 * @throws JspException
	 * @throws AppException
	 */
	public String getDiyPara(String key, String defaul) throws JspException {
		if (!this.Params.containsKey(key) || !this.diyParaList.contains(key)) {
			return defaul;
		}
		if (null == this.Params.get(key)) {
			return null;
		}
		return this.Params.get(key).toString();
	}

	/**
	 * 是否存在参数
	 * 
	 * @param key
	 * @return
	 */
	protected boolean containsKey(String key) {

		return this.Params.containsKey(key);
	}

	/**
	 * 从所有参数中获取参数
	 * 
	 * @param key
	 * @return
	 * @throws JspException
	 * @throws AppException
	 */
	public String getPara(String key) throws JspException {
		if (!this.containsKey(key)) {
			throw new JspException("不存在参数【" + key + "】");
		}
		return this.Params.get(key).toString();
	}

	/**
	 * 从所有参数中获取参数
	 * 
	 * @param key
	 * @return
	 * @throws JspException
	 * @throws AppException
	 */
	public String getPara(String key, String defaul) throws JspException {
		if (!this.containsKey(key)) {
			return defaul;
		}
		if (null == this.Params.get(key)) {
			return null;
		}
		return this.Params.get(key).toString();

	}

	/**
	 * 对外的方法，获取js
	 * 
	 * @return
	 */
	public String getJsStr() {
		jsStr = (null == jsStr ? "" : jsStr);
		String js = jsStr + "\n";
		for (int i = 0; i < this.getChildren().size(); i++) {
			js += childrenImpl.get(i).getJsStr() + "\n";
		}
		return js;
	}

	/**
	 * 对内，设置js
	 * 
	 * @param jsStr
	 */
	public void setJsStr(String jsStr) {
		this.jsStr = jsStr;
	}

	public ArrayList<AbstractSeptImpl> getChildren() {
		if (null == this.childrenImpl) {
			return new ArrayList<AbstractSeptImpl>();
		}
		return childrenImpl;
	}

	public void setChildren(ArrayList<AbstractSeptImpl> childrenImpl) {
		if (null == childrenImpl) {
			childrenImpl = new ArrayList<AbstractSeptImpl>();
		}
		this.childrenImpl = childrenImpl;
	}

	/**
	 * 抛出tag异常
	 * 
	 * @param value
	 * @throws JspException
	 */
	protected void jspException(Object value) throws JspException {
		if (value instanceof Exception) {
			throw new JspException((Exception) value);
		} else if (value instanceof Throwable) {
			throw new JspException((Throwable) value);
		} else {
			throw new JspException(value.toString());
		}
	}
}
