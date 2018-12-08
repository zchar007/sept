package com.sept.framework.taglib.easyui;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.sept.global.InteractiveDict;
import com.sept.support.common.DebugManager;

public abstract class AbstractSeptTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> hmPara = null; // 所有参数

	private HashSet<String> noQuoteList = null; // 没有引号的集合，对于没有引号的集合，所有的boolean全部是没引号的，这在setParam中会自动执行
	private HashSet<String> diyParaList = null; // 自定义参数的集合
	protected AbstractSeptImpl impl = null;
	protected AbstractSeptTag parent = null;

	private String urlHead = null;
	private String url = null;// url作为自定义参数存在，如需非自定义，可自行删除
	private String value = null;// 值
	private String labelValue = null;// 文本框值或标题名等
	private String name = null;// 名字，标识一个标签，= id
	private boolean hidden = false;// 是否隐藏，默认不隐藏
	// 以下三个参数是对此标签所在框的定义（暂定每一个标签都会被一个没有头的panel承载）
	private String style = null;
	private String width = null;
	private String height = null;
	private String left = null;
	private String top = null;
	private String currentUrl = "";
	private String callStackJson = "";
	private String requestUrl = "";
	private String __JSON_DATA__ = "";
	private String __JSON_DATA_TYPE__ = "";
	private boolean fit = true;// 默认全屏

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		this.setUrlHead("http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() // 端口号
				+ request.getContextPath() + "/");// 项目名称
		// 设置页面位置
		this.setCurrentUrl(request.getRequestURI());
		// 调试模式才增加这些url参数
		if (DebugManager.isDebugModel()) {
			// 设置url的头

			// 设置运行轨迹
			if (request.getAttribute("__requestTraceStr__") != null) {
				this.setCallStackJson((String) request
						.getAttribute("__requestTraceStr__"));
			} else {
				this.setCallStackJson("");
			}
			// 设置请求，用于可局部刷新的组件进行局部刷新
			if (request.getAttribute("__requestUrl__") != null) {
				this.setRequestUrl((String) request
						.getAttribute("__requestUrl__"));
			} else {
				this.setRequestUrl("");
			}
			// 设置请求的json数据，用于可局部刷新的组件进行局部刷新
			if (request.getAttribute(InteractiveDict.INTERACTIVE_SYSTEM_JSON) != null) {
				this.set__JSON_DATA__((String) request
						.getAttribute(InteractiveDict.INTERACTIVE_DATA));
			} else {
				this.set__JSON_DATA__("");
			}
			// 设置请求的json数据的类型，用于可局部刷新的组件进行局部刷新
			if (request
					.getAttribute(InteractiveDict.INTERACTIVE_SYSTEM_JSON) != null) {
				this.set__JSON_DATA_TYPE__((String) request
						.getAttribute(InteractiveDict.INTERACTIVE_DATA));
			} else {
				this.set__JSON_DATA_TYPE__("");
			}
		} else {
			this.setCurrentUrl("");
			this.setCallStackJson("");
			this.setRequestUrl("");
		}

		if (null != this.url && !this.url.trim().isEmpty()) {
			this.url = this.urlHead + this.url;
		}

		init();
		return super.doStartTag();
	}

	/**
	 * 对impl的定义，以及对初始参数性质的定义，参数默认是有引号，非自定义参数
	 */
	public abstract void init() throws JspException;

	@Override
	public int doEndTag() throws JspException {
		// 设置自定义的参数
		this.setDiyPara("url");
		this.setDiyPara("name");
		this.setDiyPara("value");
		this.setDiyPara("labelValue");
		this.setDiyPara("hidden");
		this.setDiyPara("urlHead");
		this.setDiyPara("style");
		this.setDiyPara("width");
		this.setDiyPara("height");
		this.setDiyPara("left");
		this.setDiyPara("top");
		this.setDiyPara("currentUrl");
		this.setDiyPara("callStackJson");
		this.setDiyPara("requestUrl");
		this.setDiyPara("__JSON_DATA__");
		this.setDiyPara("__JSON_DATA_TYPE__");
		this.setDiyPara("fit");
		// 这里会不会因为获取到null而报错？
		this.parent = (AbstractSeptTag) getParent();
		this.checkParent();
		this.checkParam();
		// 设置参数以及参数属性
		this.setParams();
		this.impl.setDiyParaList(diyParaList);
		this.impl.setNoQuoteList(noQuoteList);
		// 如果有父标签，吧自己和福标签联系起来
		if (null != this.parent) {
			this.parent.getImpl().addChild(this.impl);
		}
		doOther();
		release();
		return super.doEndTag();
	}

	@Override
	public void release() {
		super.release();
		// 将所有参数释放
		Class<? extends AbstractSeptTag> classz;
		try {
			classz = this.getClass();
			Object objOther = this.getClass().newInstance();
			Field[] fields = classz.getDeclaredFields();// 只会得到共有变量名
			Field.setAccessible(fields, true);
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName();
				Object value = fields[i].get(this);
				// 不加入为null的参数
				if ("serialVersionUID".equals(name) || null == value) {
					continue;
				}
				try {
					// 将当前类资源设为初始值
					Field field = objOther.getClass().getDeclaredField(name);
					field.setAccessible(true);
					int type = field.getModifiers();
					// public none private protected
					if (type == 1 || type == 0 || type == 2 || type == 4) {
						fields[i].set(this, field.get(objOther));
					}
				} catch (Exception e) {
				}

			}
			hmPara = null;
			noQuoteList = null;
			diyParaList = null;
			impl = null;
			parent = null;
			urlHead = null;
			url = null;
			value = null;
			labelValue = null;
			name = null;
			hidden = false;
			style = null;
			width = null;
			height = null;
			left = null;
			top = null;
			currentUrl = null;
			callStackJson = null;
			requestUrl = null;
			__JSON_DATA__ = null;
			__JSON_DATA_TYPE__ = null;
			fit = true;// 默认全屏

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * endTag中做一些其他事，检查其父标签
	 * 
	 * @throws JspException
	 */
	protected abstract void checkParent() throws JspException;

	/**
	 * endTag中做一些其他事，对参数的进一步校验
	 * 
	 * @throws JspException
	 */
	protected abstract void checkParam() throws JspException;

	/**
	 * endTag中做一些其他事，一般像BodyTag，需要往前台写html代码的时候
	 * 
	 * @throws JspException
	 */
	protected abstract void doOther() throws JspException;

	/**
	 * 把所有属性全部给impl，关于属性是否需要加引号之类的，开发人员自行解释
	 * 
	 * @throws JspException
	 */
	protected void setParams() throws JspException {
		if (null == this.hmPara) {
			this.hmPara = new HashMap<String, Object>();
		}
		Class<? extends AbstractSeptTag> classz;
		try {
			classz = this.getClass();
			Field[] fields = classz.getDeclaredFields();// 只会得到共有变量名
			Field.setAccessible(fields, true);
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName();
				Object value = fields[i].get(this);
				// 不加入为null的参数
				if ("serialVersionUID".equals(name) || null == value) {
					continue;
				}
				if (value instanceof Number || value instanceof Integer
						|| value instanceof Long || value instanceof Double
						|| value instanceof Float || value instanceof Boolean) {
					this.hmPara.put(name, value);
					this.setNoQuotePara(name);
				}
				if (value instanceof String) {
					this.hmPara.put(name, value);
				}
			}
			// 加入基本的参数
			if (null != url) {
				this.hmPara.put("url", this.getUrl());
			}
			if (null != name) {
				this.hmPara.put("name", this.getName());
			}
			if (null != value) {
				this.hmPara.put("value", this.getValue());
			}
			if (null != labelValue) {
				this.hmPara.put("labelValue", this.getLabelValue());
			}
			this.hmPara.put("hidden", this.isHidden());
			if (null != urlHead) {
				this.hmPara.put("urlHead", this.getUrlHead());
			}
			if (null != style) {
				this.hmPara.put("style", this.getStyle());
			}
			if (null != width) {
				this.hmPara.put("width", this.getWidth());
			}
			if (null != height) {
				this.hmPara.put("height", this.getHeight());
			}
			if (null != left) {
				this.hmPara.put("left", this.getLeft());
			}
			if (null != top) {
				this.hmPara.put("top", this.getTop());
			}
			if (null != currentUrl) {
				this.hmPara.put("currentUrl", this.getCurrentUrl());
			}
			if (null != callStackJson) {
				this.hmPara.put("callStackJson", this.getCallStackJson());
			}
			if (null != requestUrl) {
				this.hmPara.put("requestUrl", this.getRequestUrl());
			}
			if (null != __JSON_DATA__) {
				this.hmPara.put("__JSON_DATA__", this.get__JSON_DATA__());
			}
			if (null != __JSON_DATA_TYPE__) {
				this.hmPara.put("__JSON_DATA_TYPE__",
						this.get__JSON_DATA_TYPE__());
			}
			this.setNoQuotePara("fit");
			this.hmPara.put("fit", this.isFit());

			this.impl.setParams(this.hmPara);
		} catch (Exception e) {
			throw new JspException(e);

		}
	}

	/**
	 * 设置无印引号参数
	 * 
	 * @param paraName
	 * @throws JspException
	 */
	protected void setNoQuotePara(String paraName) throws JspException {
		if (null == this.noQuoteList) {
			this.noQuoteList = new HashSet<String>();
		}
		if (null == paraName || paraName.trim().isEmpty()) {
			throw new JspException("设置第【" + this.noQuoteList.size()
					+ "】无引号参数时，参数为空或null");
		}
		this.noQuoteList.add(paraName);
	}

	/**
	 * 去除参数的无引号性质
	 * 
	 * @param paraName
	 * @throws JspException
	 */
	protected void removeNoQuotePara(String paraName) throws JspException {
		if (null == this.noQuoteList) {
			this.noQuoteList = new HashSet<String>();
		}
		if (null == paraName || paraName.trim().isEmpty()) {
			throw new JspException("删除参数的无引号性质时，参数为空或null");
		}
		this.noQuoteList.remove(paraName);
	}

	/**
	 * 设置无印引号参数
	 * 
	 * @param paraName
	 * @throws JspException
	 */
	protected void setDiyPara(String paraName) throws JspException {
		if (null == this.diyParaList) {
			this.diyParaList = new HashSet<String>();
		}
		if (null == paraName || paraName.trim().isEmpty()) {
			throw new JspException("设置第【" + this.diyParaList.size()
					+ "】自定义参数时，参数为空或null");
		}
		this.diyParaList.add(paraName);
	}

	/**
	 * 去除参数的无引号性质
	 * 
	 * @param paraName
	 * @throws JspException
	 */
	protected void removeDiyPara(String paraName) throws JspException {
		if (null == this.diyParaList) {
			this.diyParaList = new HashSet<String>();
		}
		if (null == paraName || paraName.trim().isEmpty()) {
			throw new JspException("删除参数自定义属性时，参数为空或null");
		}
		this.diyParaList.remove(paraName);
	}

	protected void addPara(String key, Object value) throws JspException {
		if (null == this.hmPara) {
			this.hmPara = new HashMap<String, Object>();
		}
		if (null == key || key.trim().isEmpty()) {
			this.jspException("key不能为 null");
		}
		if (null != value) {
			this.hmPara.put(key, value);
		}
	}

	public AbstractSeptImpl getImpl() {
		return impl;
	}

	public void setImpl(AbstractSeptImpl impl) {
		this.impl = impl;
	}

	public String getUrlHead() {
		return urlHead;
	}

	public void setUrlHead(String urlHead) {
		this.urlHead = urlHead;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabelValue() {
		return labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	protected void jspException(Object value) throws JspException {
		if (value instanceof Exception) {
			throw new JspException((Exception) value);
		} else if (value instanceof Throwable) {
			throw new JspException((Throwable) value);
		} else {
			throw new JspException(value.toString());
		}
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		// System.out.println(top);
		this.top = top;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	public boolean isFit() {
		return fit;
	}

	public void setFit(boolean fit) {
		this.fit = fit;
	}

	public String getCallStackJson() {
		return callStackJson;
	}

	public void setCallStackJson(String callStackJson) {
		this.callStackJson = callStackJson;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String get__JSON_DATA__() {
		return __JSON_DATA__;
	}

	public void set__JSON_DATA__(String __JSON_DATA__) {
		this.__JSON_DATA__ = __JSON_DATA__;
	}

	public String get__JSON_DATA_TYPE__() {
		return __JSON_DATA_TYPE__;
	}

	public void set__JSON_DATA_TYPE__(String __JSON_DATA_TYPE__) {
		this.__JSON_DATA_TYPE__ = __JSON_DATA_TYPE__;
	}

}
