package com.sept.framework.taglib.sept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.jsp.JspException;

import com.sept.global.InteractiveDict;
import com.sept.support.util.StringUtil;

public abstract class AbstractImpl {
	private HashMap<String, Object> hmPara = null; // 所有参数
	protected HashSet<String> stringPara = null;
	protected HashSet<String> numberPara = null;
	protected HashSet<String> booleanPara = null;
	protected HashSet<String> functionPara = null;
	protected HashSet<String> diyPara = null;
	private String layout = null;// v纵向，h横向
	private String deflayout = "v";// v纵向，h横向

	private ArrayList<AbstractImpl> childrenImpl = null;

	/**
	 * 默认纵向排列，一般快状态结构会用到
	 * 
	 * @return
	 */
	public abstract String genHTML() throws JspException ;//
//	{
//		String nowLayout = (null == layout || layout.trim().isEmpty()) ? ((null == deflayout || deflayout
//				.trim().isEmpty()) ? "v" : deflayout) : layout;
//		StringBuffer html = new StringBuffer();
//
//		if ("H".equals(nowLayout.toUpperCase())) {
//			html.append("<div class=\"sept-layout sept-layout-hlayout\" style=\"background-color: red\">");
//
//		} else {
//			html.append("<div class=\"sept-layout sept-layout-vlayout\" style=\"background-color: blue\">");
//		}
//		html.append(initHTML());
//		html.append("</div>");
//		return null;
//	}

	//public abstract String initHTML() throws JspException;

	/**
	 * region:'north',title:'North Title',split:true
	 */
	protected String getEUIOptions() {
//		System.out.println(this.getClass().getName()+"stringPara"+stringPara);
//		System.out.println(this.getClass().getName()+"booleanPara"+booleanPara);
//		System.out.println(this.getClass().getName()+"functionPara"+functionPara);
//		System.out.println(this.getClass().getName()+"numberPara"+numberPara);
		String options = "";
		for (String key : hmPara.keySet()) {
			if (diyPara.contains(key)) {
				continue;
			}
			if (stringPara.contains(key) && !functionPara.contains(key)) {
				options += key + ":'" + hmPara.get(key) + "',";
			} else if (booleanPara.contains(key)) {
				options += key + ":" + hmPara.get(key) + ",";
			} else if (functionPara.contains(key)) {
				options += key + ":" + hmPara.get(key) + ",";
			} else if (numberPara.contains(key)) {
				options += key + ":" + hmPara.get(key) + ",";
			}

		}
		if (options.length() > 0) {
			options = options.substring(0, options.length() - 1);
		}
		//System.out.println(this.getClass().getName()+options);
		return options;

	}

	public ArrayList<AbstractImpl> getChildren() {
		if (null == this.childrenImpl) {
			return new ArrayList<AbstractImpl>();
		}
		return childrenImpl;
	}

	/**
	 * 添加子impl
	 * 
	 * @param child
	 * @throws JspException
	 */
	public void addChild(AbstractImpl child) throws JspException {
		if (null == this.childrenImpl) {
			this.childrenImpl = new ArrayList<AbstractImpl>();
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
			this.childrenImpl = new ArrayList<AbstractImpl>();
		}
		this.childrenImpl.remove(index);
	}

	/**
	 * 按impl移除子impl
	 * 
	 * @param child
	 * @throws JspException
	 */
	public void removeChild(AbstractImpl child) throws JspException {
		if (null == this.childrenImpl) {
			this.childrenImpl = new ArrayList<AbstractImpl>();
		}
		if (null == child) {
			throw new JspException("要删除的第【" + this.childrenImpl.size()
					+ "】个子标签序为空!");
		}
		this.childrenImpl.remove(child);
	}

	@SuppressWarnings("unchecked")
	public void setParams(HashMap<String, Object> hmPara) throws JspException {
		if (null == hmPara) {
			throw new JspException("Tag要为Impl设置参数时，DataObject为null!");
		}
		this.hmPara = (HashMap<String, Object>) hmPara.clone();
	}

	/**
	 * 设置各个参数的类型
	 * 
	 * @param stringPara
	 * @param numberPara
	 * @param booleanPara
	 * @param functionPara
	 * @param diyPara
	 */
	@SuppressWarnings("unchecked")
	public void setParamType(HashSet<String> stringPara,
			HashSet<String> numberPara, HashSet<String> booleanPara,
			HashSet<String> functionPara, HashSet<String> diyPara) {
		
		this.stringPara = null == stringPara?new HashSet<String>():(HashSet<String>) stringPara.clone();
		this.numberPara = null == numberPara?new HashSet<String>():(HashSet<String>) numberPara.clone();
		this.booleanPara = null == booleanPara?new HashSet<String>():(HashSet<String>) booleanPara.clone();
		this.functionPara = null == functionPara?new HashSet<String>():(HashSet<String>) functionPara.clone();
		this.diyPara = null == diyPara?new HashSet<String>():(HashSet<String>) diyPara.clone();
	}

	public Number getNumberPara(String paraName) throws JspException {
		if (this.numberPara.contains(paraName)) {
			return (Number) this.hmPara.get(paraName);
		} else {
			this.jspException("不含有数字型参数【" + paraName + "】");
		}
		return null;
	}

	public String getStringPara(String paraName) throws JspException {
		if (this.stringPara.contains(paraName)) {
			return (String) this.hmPara.get(paraName);
		} else {
			this.jspException("不含有字符串型参数【" + paraName + "】");
		}
		return null;
	}

	public boolean getBooleanPara(String paraName) throws JspException {
		if (this.booleanPara.contains(paraName)) {
			return Boolean.parseBoolean(this.hmPara.get(paraName).toString()) ;
		} else {
			this.jspException("不含有布尔型参数【" + paraName + "】");
		}
		return false;
	}

	public String getFunctionPara(String paraName) throws JspException {
		if (this.functionPara.contains(paraName)) {
			return (String) this.hmPara.get(paraName);
		} else {
			this.jspException("不含有函数型参数【" + paraName + "】");
		}
		return null;
	}

	public Number getNumberPara(String paraName, Number defValue)
			throws JspException {
		try {
			return getNumberPara(paraName);
		} catch (Exception e) {
			return defValue;
		}
	}

	public String getStringPara(String paraName, String defValue)
			throws JspException {
		try {
			return getStringPara(paraName);
		} catch (Exception e) {
			return defValue;
		}
	}

	public boolean getBooleanPara(String paraName, boolean defValue)
			throws JspException {
		try {
			return getBooleanPara(paraName);
		} catch (Exception e) {
			return defValue;
		}
	}

	public String getFunctionPara(String paraName, String defValue)
			throws JspException {
		try {
			return getStringPara(paraName);
		} catch (Exception e) {
			return defValue;
		}
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

	public String getDeflayout() {
		return deflayout;
	}

	public void setDeflayout(String deflayout) {
		this.deflayout = deflayout;
	}
	
	protected String getPageInfo() throws JspException{
		StringBuffer html = new StringBuffer();
		html.append( 		"<div id=\"callStackTraceInfo_"+StringUtil.getUUID()+"\" style=\"display:none;\" class = 'sept-debug-panel'>"      );
		html.append( 			"<div class=\"sept-request-jspurl\">"+this.getStringPara("currentUrl","")+"</div>");
		html.append( 			"<div class=\"sept-request-stack\">"+this.getStringPara("callStackJson","")+"</div>");
		html.append( 			"<div class=\"sept-request-url\">"+this.getStringPara("requestUrl","")+"</div>");
		html.append( 			"<div class=\"sept-request-data\">"+this.getStringPara(InteractiveDict.INTERACTIVE_DATA,"")+"</div>");
		html.append( 			"<div class=\"sept-request-head\">"+this.getStringPara(InteractiveDict.INTERACTIVE_SYSTEM_JSON,"")+"</div>");
		html.append( 		"</div>");
		return html.toString();
	}
	
}
