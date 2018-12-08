package com.sept.framework.taglib.sept;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.sept.global.InteractiveDict;
import com.sept.support.common.DebugManager;

public abstract class AbstractTag extends BodyTagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected AbstractImpl impl = null;
	private HashMap<String, Object> hmPara = null; // 所有参数
	protected HashSet<String> stringPara = null;
	protected HashSet<String> numberPara = null;
	protected HashSet<String> booleanPara = null;
	protected HashSet<String> functionPara = null;
	protected HashSet<String> diyPara = null;
	protected AbstractTag parent = null;

	// 下面是一些基本的标签参数
	private String width = "";
	private String height = "";
	private boolean fit = true;// 默认撑满其父容器
	private String name = null;// 标签主键
	private String value = null;// 标签值，唯一意义：可作为输入框的标签的值
	private String labelValue = null;// 展示框的值，唯一意义：仅仅用于显示的值
	private boolean hidden = false;// 是否隐藏，默认不隐藏
	private String layout = null;// 是否隐藏，默认不隐藏

	// 关于操作的
	private String urlHead = null;
	private String url = null;// url作为自定义参数存在，如需非自定义，可自行删除
	private String currentUrl = "";
	private String callStackJson = "";
	private String requestUrl = "";
	private String __SYSTEM_INFO__ = "";
	private String __DATA__ = "";

	// 基本的标签参数结束
	@Override
	public int doStartTag() throws JspException {
		// 初始化
		hmPara = new HashMap<String, Object>();
		stringPara = new HashSet<String>();
		numberPara = new HashSet<String>();
		booleanPara = new HashSet<String>();
		functionPara = new HashSet<String>();

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
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
				this.setCallStackJson((String) request.getAttribute("__requestTraceStr__"));
			} else {
				this.setCallStackJson("");
			}
			// 设置请求，用于可局部刷新的组件进行局部刷新
			if (request.getAttribute("__requestUrl__") != null) {
				this.setRequestUrl((String) request.getAttribute("__requestUrl__"));
			} else {
				this.setRequestUrl("");
			}
			// 设置请求的json数据，用于可局部刷新的组件进行局部刷新
			if (request.getAttribute(InteractiveDict.INTERACTIVE_DATA) != null) {
				this.set__DATA__((String) request.getAttribute(InteractiveDict.INTERACTIVE_DATA));
			} else {
				this.set__DATA__("");
			}
			// 设置请求的json数据的类型，用于可局部刷新的组件进行局部刷新
			if (request.getAttribute(InteractiveDict.INTERACTIVE_SYSTEM_JSON) != null) {
				this.set__SYSTEM_INFO__((String) request.getAttribute(InteractiveDict.INTERACTIVE_SYSTEM_JSON));
			} else {
				this.set__SYSTEM_INFO__("");
			}
		} else {
			this.setCurrentUrl("");
			this.setCallStackJson("");
			this.setRequestUrl("");
		}
		this.init();
		return super.doStartTag();
	}

	public abstract void init() throws JspException;

	@Override
	public int doEndTag() throws JspException {

		// 设置自定义的参数
		this.setDiyPara("width");
		this.setDiyPara("height");
		this.setDiyPara("fit");
		this.setDiyPara("name");
		this.setDiyPara("value");
		this.setDiyPara("labelValue");
		this.setDiyPara("hidden");
		this.setDiyPara("layout");

		this.setDiyPara("urlHead");
		this.setDiyPara("url");
		this.setDiyPara("currentUrl");
		this.setDiyPara("callStackJson");
		this.setDiyPara("requestUrl");
		this.setDiyPara("__SYSTEM_INFO__");
		this.setDiyPara("__DATA__");
		// 这里会不会因为获取到null而报错？
		this.parent = (AbstractTag) getParent();
		this.checkBeforeSetParams();
		// 设置参数以及参数属性
		this.setParams();
		if (null != this.url && !this.url.trim().isEmpty()) {
			this.setUrl(this.urlHead + this.url);
		}

		// 加入基本的参数
		this.impl.setParams(this.hmPara);
		this.impl.setParamType(stringPara, numberPara, booleanPara, functionPara, diyPara);
		// 如果有父标签，吧自己和福标签联系起来
		if (null != this.parent) {
			this.parent.getImpl().addChild(this.impl);
		}
		this.checkAfterSetParams();
		release();
		return super.doEndTag();
	}

	public abstract String checkBeforeSetParams() throws JspException;

	public abstract String checkAfterSetParams() throws JspException;

	@Override
	public void release() {
		super.release();
		// 将所有参数释放
		Class<? extends AbstractTag> classz;
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
					e.printStackTrace();
				}

			}
			impl = null;
			hmPara = null; // 所有参数
			stringPara = null;
			numberPara = null;
			booleanPara = null;
			functionPara = null;
			diyPara = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addParam(String key, Object value, String type) {
		if ("f".equals(type.toLowerCase())) {
			this.functionPara.add(key);
		} else if ("n".equals(type.toLowerCase())) {
			this.numberPara.add(key);
		} else if ("b".equals(type.toLowerCase())) {
			this.booleanPara.add(key);
		} else {
			this.stringPara.add(key);
		}
		this.hmPara.put(key, value);
	}

	/**
	 * 把所有属性全部给impl，自动解析是否需要加引号，但对于函数型参数，需要手动添加
	 * 
	 * @throws JspException
	 */
	protected void setParams() throws JspException {
		if (null == this.hmPara) {
			this.hmPara = new HashMap<String, Object>();
		}
		Class<? extends AbstractTag> classz;
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
						|| value instanceof Float) {
					this.hmPara.put(name, value);
					this.setNumberPara(name);
				} else if (value instanceof Boolean) {
					this.hmPara.put(name, value);
					this.setBooleanPara(name);
				}
				if (value instanceof String) {
					this.hmPara.put(name, value);
					this.setStringPara(name);
				}
			}
			// 加入基本的参数
			if (null != url && !url.trim().isEmpty()) {
				this.hmPara.put("url", this.getUrl());
				this.setStringPara("url");

			}
			if (null != name && !name.trim().isEmpty()) {
				this.hmPara.put("name", this.getName());
				this.setStringPara("name");

			}
			if (null != value && !value.trim().isEmpty()) {
				this.hmPara.put("value", this.getValue());
				this.setStringPara("value");

			}
			if (null != labelValue && !labelValue.trim().isEmpty()) {
				this.hmPara.put("labelValue", this.getLabelValue());
				this.setStringPara("labelValue");

			}
			if (null != urlHead && !urlHead.trim().isEmpty()) {
				this.hmPara.put("urlHead", this.getUrlHead());
				this.setStringPara("urlHead");

			}
			if (null != width && !width.trim().isEmpty()) {
				this.hmPara.put("width", this.getWidth());
				this.setStringPara("width");

			}
			if (null != height && !height.trim().isEmpty()) {
				this.hmPara.put("height", this.getHeight());
				this.setStringPara("height");

			}

			if (null != currentUrl && !currentUrl.trim().isEmpty()) {
				this.hmPara.put("currentUrl", this.getCurrentUrl());
				this.setStringPara("currentUrl");

			}
			if (null != callStackJson && !callStackJson.trim().isEmpty()) {
				this.hmPara.put("callStackJson", this.getCallStackJson());
				this.setStringPara("callStackJson");

			}
			if (null != requestUrl && !requestUrl.trim().isEmpty()) {
				this.hmPara.put("requestUrl", this.getRequestUrl());
				this.setStringPara("requestUrl");

			}
			if (null != __DATA__ && !__DATA__.trim().isEmpty()) {
				this.hmPara.put(InteractiveDict.INTERACTIVE_DATA, this.get__DATA__());
				this.setStringPara(InteractiveDict.INTERACTIVE_DATA);

			}
			if (null != __SYSTEM_INFO__ && !__SYSTEM_INFO__.trim().isEmpty()) {
				this.hmPara.put(InteractiveDict.INTERACTIVE_SYSTEM_JSON, this.get__SYSTEM_INFO__());
				this.setStringPara(InteractiveDict.INTERACTIVE_SYSTEM_JSON);

			}
			if (null != layout && !layout.trim().isEmpty()) {
				this.hmPara.put("layout", this.getLayout());
				this.setStringPara("layout");

			}
			this.hmPara.put("hidden", this.isHidden());
			this.setBooleanPara("hidden");
			this.hmPara.put("fit", this.isFit());
			this.setBooleanPara("fit");
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
	protected void setStringPara(String paraName) throws JspException {
		if (null == this.stringPara) {
			this.stringPara = new HashSet<String>();
		}
		if (null == paraName || paraName.trim().isEmpty()) {
			throw new JspException("设置第【" + this.stringPara.size() + "】【"
					+ paraName + "】String无引号参数时，参数为空或null");
		}
		this.stringPara.add(paraName);
	}

	/**
	 * 设置数字型参数
	 * 
	 * @param paraName
	 * @throws JspException
	 */
	protected void setNumberPara(String paraName) throws JspException {
		if (null == this.numberPara) {
			this.stringPara = new HashSet<String>();
		}
		if (null == paraName || paraName.trim().isEmpty()) {
			throw new JspException("设置第【" + this.numberPara.size() + "】【"
					+ paraName + "】Number无引号参数时，参数为空或null");
		}
		this.numberPara.add(paraName);
	}

	/**
	 * 设置bool型参数
	 * 
	 * @param paraName
	 * @throws JspException
	 */
	protected void setBooleanPara(String paraName) throws JspException {
		if (null == this.booleanPara) {
			this.booleanPara = new HashSet<String>();
		}
		if (null == paraName || paraName.trim().isEmpty()) {
			throw new JspException("设置第【" + this.booleanPara.size() + "】【"
					+ paraName + "】Boolean无引号参数时，参数为空或null");
		}
		this.booleanPara.add(paraName);
	}

	/**
	 * 设置函数参数
	 * 
	 * @param paraName
	 * @throws JspException
	 */
	protected void setFunctionPara(String paraName) throws JspException {
		if (null == this.functionPara) {
			this.functionPara = new HashSet<String>();
		}
		if (null == paraName || paraName.trim().isEmpty()) {
			throw new JspException("设置第【" + this.functionPara.size() + "】【"
					+ paraName + "】Function无引号参数时，参数为空或null");
		}
		this.functionPara.add(paraName);
	}

	/**
	 * 自定义参数设置
	 * 
	 * @param paraName
	 * @throws JspException
	 */
	protected void setDiyPara(String paraName) throws JspException {
		if (null == this.diyPara) {
			this.diyPara = new HashSet<String>();
		}
		if (null == paraName || paraName.trim().isEmpty()) {
			throw new JspException("设置第【" + this.diyPara.size() + "】【"
					+ paraName + "】自定义参数时，参数为空或null");
		}
		this.diyPara.add(paraName);
	}

	/**
	 * 自定义参数移除
	 * 
	 * @param paraName
	 * @throws JspException
	 */
	protected void removeDiyPara(String paraName) throws JspException {
		if (null == this.diyPara) {
			this.diyPara = new HashSet<String>();
		}
		if (null == paraName || paraName.trim().isEmpty()) {
			throw new JspException("删除参数自定义属性【" + paraName + "】时，参数为空或null");
		}
		this.diyPara.remove(paraName);
	}

	public AbstractImpl getImpl() {
		return impl;
	}

	public void setImpl(AbstractImpl impl) {
		this.impl = impl;
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

	public boolean isFit() {
		return fit;
	}

	public void setFit(boolean fit) {
		this.fit = fit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
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

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
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

	public String get__SYSTEM_INFO__() {
		return __SYSTEM_INFO__;
	}

	public void set__SYSTEM_INFO__(String __SYSTEM_INFO__) {
		this.__SYSTEM_INFO__ = __SYSTEM_INFO__;
	}

	public String get__DATA__() {
		return __DATA__;
	}

	public void set__DATA__(String __DATA__) {
		this.__DATA__ = __DATA__;
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

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}
}
