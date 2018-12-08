package com.sept.framework.taglib.sept.panel;

import javax.servlet.jsp.JspException;

import com.sept.framework.taglib.sept.AbstractTag;

public class PanelTag extends AbstractTag{
	private static final long serialVersionUID = 1L;

	// id string 面板的ID属性。 null
	// title string 在面板头部显示的标题文本。 null
	// iconCls string 设置一个16x16图标的CSS类ID显示在面板左上角。 null
	private String iconCls = null;
	// width number 设置面板宽度。 auto
	// height number 设置面板高度。 auto
	// left number 设置面板距离左边的位置（即X轴位置）。 null
	private int left_x = 0;
	// top number 设置面板距离顶部的位置（即Y轴位置）。 null
	private int top_y = 0;
	// cls string 添加一个CSS类ID到面板。 null
	private String cls = null;
	// headerCls string 添加一个CSS类ID到面板头部。 null
	private String headerCls = "";
	// bodyCls string 添加一个CSS类ID到面板正文部分。 null
	private String bodyCls = null;
	// style object 添加一个当前指定样式到面板。 {}
	private String style = null;
	// border boolean 定义是否显示面板边框。 true
	private boolean border = true;
	// doSize boolean 如果设置为true，在面板被创建的时候将重置大小和重新布局。 true
	private boolean doSize = true;
	// noheader boolean 如果设置为true，那么将不会创建面板标题。 false header未使用
	// content string 面板主体内容。 null
	private String content = null;
	// collapsible boolean 定义是否显示可折叠按钮。 false
	private boolean collapsible = false;
	// minimizable boolean 定义是否显示最小化按钮。 false
	private boolean minimizable = false;
	// maximizable boolean 定义是否显示最大化按钮。 false
	private boolean maximizable = false;
	// closable boolean 定义是否显示关闭按钮。 false
	private boolean closable = false;
	// tools array,selector 自定义工具菜单 []以标签的形式出现在此标签内
	// header selector 亲测没什么用
	// footer selector 页脚自定义工具菜单 []以标签的形式出现在此标签内-暂时不管用
	// openAnimation string
	// 定义打开面板的动画，可用值有：'slide','fade','show'。（该属性自1.4.1版开始可用）
	private String openAnimation = null;
	// openDuration number 定义打开面板的持续时间。（该属性自1.4.1版开始可用） 400
	private int openDuration = 400;
	// closeAnimation string
	// 定义关闭面板的动画，可用值有：'slide','fade','show'。（该属性自1.4.1版开始可用）
	private String closeAnimation = null;
	// closeDuration number 定义关闭面板的持续时间。（该属性自1.4.1版开始可用） 400
	private int closeDuration = 400;
	// collapsed boolean 定义是否在初始化的时候折叠面板。 false
	private boolean collapsed = false;
	// minimized boolean 定义是否在初始化的时候最小化面板。 false
	private boolean minimized = false;
	// maximized boolean 定义是否在初始化的时候最大化面板。 false
	private boolean maximized = false;
	// closed boolean 定义是否在初始化的时候关闭面板。 false
	private boolean closed = false;
	// href string url代替
	// cache boolean 如果为true，在超链接载入时缓存面板内容。 true
	private boolean cache = true;
	// loadingMessage string 在加载远程数据的时候在面板内显示一条消息。 Loading…
	private String loadingMessage = "Loading… ";
	// extractor function 定义如何从ajax应答数据中提取内容，返回提取数据。不使用
	// method string 使用HTTP的哪一种方法读取内容页。可用值：'get','post'。（该属性自1.3.6版开始可用） get
	private String method = "get";
	// queryParams object 在加载内容页的时候添加的请求参数。（该属性自1.3.6版开始可用） {}
	private String queryParams = "{}";
	// loader function
	// 定义了如何从远程服务器加载内容页，该函数接受以下参数：param：参数对象发送给远程服务器。success(data)：在检索数据成功的时候调用的回调函数。error()：在检索数据失败的时候调用的回调函数。//暂时不使用

	// 事件
	// onBeforeLoad none 在加载内容页之前触发，返回false将忽略该动作。（该事件自1.3.6版开始可用）
	private String onBeforeLoad = null;
	// onLoad none 在加载远程数据时触发。
	private String onLoad = null;
	// onLoadError none 在加载内容页发生错误时触发。（该事件自1.3.6版开始可用）
	private String onLoadError = null;
	// onBeforeOpen none 在打开面板之前触发，返回false可以取消打开操作。
	private String onBeforeOpen = null;
	// onOpen none 在打开面板之后触发。
	private String onOpen = null;
	// onBeforeClose none 在关闭面板之前触发，返回false可以取消关闭操作。下列的面板将不能关闭。
	private String onBeforeClose = null;
	// onClose none 在面板关闭之后触发。
	private String onClose = null;
	// onBeforeDestroy none 在面板销毁之前触发，返回false可以取消销毁操作。
	private String onBeforeDestroy = null;
	// onDestroy none 在面板销毁之后触发。
	private String onDestroy = null;
	// onBeforeCollapse none 在面板折叠之前触发，返回false可以终止折叠操作。
	private String onBeforeCollapse = null;
	// onCollapse none 在面板折叠之后触发。
	private String onCollapse = null;
	// onBeforeExpand none 在面板展开之前触发，返回false可以终止展开操作。
	private String onBeforeExpand = null;
	// onExpand none 在面板展开之后触发。
	private String onExpand = null;
	// onResize width, height 在面板改变大小之后触发。 width：新的宽度。height：新的高度。
	private String onResize = null;
	// onMove left,top 在面板移动之后触发。left：新的左边距位置。top：新的上边距位置。
	private String onMove = null;
	// onMaximize none 在窗口最大化之后触发。
	private String onMaximize = null;
	// onRestore none 在窗口恢复到原始大小以后触发。
	private String onRestore = null;
	// onMinimize none 在窗口最小化之后触发。
	private String onMinimize = null;

	@Override
	public void init() throws JspException {
		this.impl = new PanelImpl();
		this.setDiyPara("style");
		this.setDiyPara("left_x");
		this.setDiyPara("top_y");
		// onBeforeLoad none 在加载内容页之前触发，返回false将忽略该动作。（该事件自1.3.6版开始可用）
		this.setFunctionPara("onBeforeLoad");
		// onLoad none 在加载远程数据时触发。
		this.setFunctionPara("onLoad");
		// onLoadError none 在加载内容页发生错误时触发。（该事件自1.3.6版开始可用）
		this.setFunctionPara("onLoadError");
		// onBeforeOpen none 在打开面板之前触发，返回false可以取消打开操作。
		this.setFunctionPara("onBeforeOpen");
		// onOpen none 在打开面板之后触发。
		this.setFunctionPara("onOpen");
		// onBeforeClose none 在关闭面板之前触发，返回false可以取消关闭操作。下列的面板将不能关闭。
		this.setFunctionPara("onBeforeClose");
		// onClose none 在面板关闭之后触发。
		this.setFunctionPara("onClose");
		// onBeforeDestroy none 在面板销毁之前触发，返回false可以取消销毁操作。
		this.setFunctionPara("onBeforeDestroy");
		// onDestroy none 在面板销毁之后触发。
		this.setFunctionPara("onDestroy");
		// onBeforeCollapse none 在面板折叠之前触发，返回false可以终止折叠操作。
		this.setFunctionPara("onBeforeCollapse");
		// onCollapse none 在面板折叠之后触发。
		this.setFunctionPara("onCollapse");
		// onBeforeExpand none 在面板展开之前触发，返回false可以终止展开操作。
		this.setFunctionPara("onBeforeExpand");
		// onExpand none 在面板展开之后触发。
		this.setFunctionPara("onExpand");
		// onResize width, height 在面板改变大小之后触发。 width：新的宽度。height：新的高度。
		this.setFunctionPara("onResize");
		// onMove left,top 在面板移动之后触发。left：新的左边距位置。top：新的上边距位置。
		this.setFunctionPara("onMove");
		// onMaximize none 在窗口最大化之后触发。
		this.setFunctionPara("onMaximize");
		// onRestore none 在窗口恢复到原始大小以后触发。
		this.setFunctionPara("onRestore");
		// onMinimize none 在窗口最小化之后触发。
		this.setFunctionPara("onMinimize");
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		if (null == this.parent) {
			throw new JspException("Panel标签必须含有父标签！");
		}
		int left = this.getLeft_x();
		int top = this.getTop_y();
		this.addParam("left", left, "n");
		this.addParam("top", top, "n");
		// 事件
		// onBeforeLoad none 在加载内容页之前触发，返回false将忽略该动作。（该事件自1.3.6版开始可用）
		if (null != this.getOnBeforeLoad()
				&& !this.getOnBeforeLoad().trim().isEmpty()) {
			this.setOnBeforeLoad("function(){" + this.getOnBeforeLoad() + "()}");
		}
		// onLoad none 在加载远程数据时触发。
		if (null != this.getOnLoad() && !this.getOnLoad().trim().isEmpty()) {
			this.setOnLoad("function(){" + this.getOnLoad() + "()}");
		}
		// onLoadError none 在加载内容页发生错误时触发。（该事件自1.3.6版开始可用）
		if (null != this.getOnLoadError()
				&& !this.getOnLoadError().trim().isEmpty()) {
			this.setOnLoadError("function(){" + this.getOnLoadError() + "()}");
		}
		// onBeforeOpen none 在打开面板之前触发，返回false可以取消打开操作。
		if (null != this.getOnBeforeOpen()
				&& !this.getOnBeforeOpen().trim().isEmpty()) {
			this.setOnBeforeOpen("function(){" + this.getOnBeforeOpen() + "()}");
		}
		// onOpen none 在打开面板之后触发。
		if (null != this.getOnOpen() && !this.getOnOpen().trim().isEmpty()) {
			this.setOnOpen("function(){" + this.getOnOpen() + "()}");
		}
		// onBeforeClose none 在关闭面板之前触发，返回false可以取消关闭操作。下列的面板将不能关闭。
		if (null != this.getOnBeforeClose()
				&& !this.getOnBeforeClose().trim().isEmpty()) {
			this.setOnBeforeClose("function(){" + this.getOnBeforeClose()
					+ "()}");
		}
		// onClose none 在面板关闭之后触发。
		if (null != this.getOnClose() && !this.getOnClose().trim().isEmpty()) {
			this.setOnClose("function(){" + this.getOnClose() + "()}");
		}
		// onBeforeDestroy none 在面板销毁之前触发，返回false可以取消销毁操作。
		if (null != this.getOnBeforeDestroy()
				&& !this.getOnBeforeDestroy().trim().isEmpty()) {
			this.setOnBeforeDestroy("function(){" + this.getOnBeforeDestroy()
					+ "()}");
		}
		// onDestroy none 在面板销毁之后触发。
		if (null != this.getOnDestroy()
				&& !this.getOnDestroy().trim().isEmpty()) {
			this.setOnDestroy("function(){" + this.getOnDestroy() + "()}");
		}
		// onBeforeCollapse none 在面板折叠之前触发，返回false可以终止折叠操作。
		if (null != this.getOnBeforeCollapse()
				&& !this.getOnBeforeCollapse().trim().isEmpty()) {
			this.setOnBeforeCollapse("function(){" + this.getOnBeforeCollapse()
					+ "()}");
		}
		// onCollapse none 在面板折叠之后触发。
		if (null != this.getOnCollapse()
				&& !this.getOnCollapse().trim().isEmpty()) {
			this.setOnCollapse("function(){" + this.getOnCollapse() + "()}");
		}
		// onBeforeExpand none 在面板展开之前触发，返回false可以终止展开操作。
		if (null != this.getOnBeforeExpand()
				&& !this.getOnBeforeExpand().trim().isEmpty()) {
			this.setOnBeforeExpand("function(){" + this.getOnBeforeExpand()
					+ "()}");
		}
		// onExpand none 在面板展开之后触发。
		if (null != this.getOnExpand() && !this.getOnExpand().trim().isEmpty()) {
			this.setOnExpand("function(){" + this.getOnExpand() + "()}");
		}
		// onResize width, height 在面板改变大小之后触发。 width：新的宽度。height：新的高度。
		if (null != this.getOnResize() && !this.getOnResize().trim().isEmpty()) {
			this.setOnResize("function(width, height){" + this.getOnResize()
					+ "(width, height)}");
		}
		// onMove left,top 在面板移动之后触发。left：新的左边距位置。top：新的上边距位置。
		if (null != this.getOnMove() && !this.getOnMove().trim().isEmpty()) {
			this.setOnMove("function(left,top){" + this.getOnMove()
					+ "(left,top)}");
		}
		// onMaximize none 在窗口最大化之后触发。
		if (null != this.getOnMaximize()
				&& !this.getOnMaximize().trim().isEmpty()) {
			this.setOnMaximize("function(){" + this.getOnMaximize() + "()}");
		}
		// onRestore none 在窗口恢复到原始大小以后触发。
		if (null != this.getOnRestore()
				&& !this.getOnRestore().trim().isEmpty()) {
			this.setOnRestore("function(){" + this.getOnRestore() + "()}");
		}
		// onMinimize none 在窗口最小化之后触发。
		if (null != this.getOnMinimize()
				&& !this.getOnMinimize().trim().isEmpty()) {
			this.setOnMinimize("function(){" + this.getOnMinimize() + "()}");
		}

		return null;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		// TODO 张超 Auto-generated method stub
		return null;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public int getLeft_x() {
		return left_x;
	}

	public void setLeft_x(int left_x) {
		this.left_x = left_x;
	}

	public int getTop_y() {
		return top_y;
	}

	public void setTop_y(int top_y) {
		this.top_y = top_y;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getHeaderCls() {
		return headerCls;
	}

	public void setHeaderCls(String headerCls) {
		this.headerCls = headerCls;
	}

	public String getBodyCls() {
		return bodyCls;
	}

	public void setBodyCls(String bodyCls) {
		this.bodyCls = bodyCls;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public boolean getBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public boolean getDoSize() {
		return doSize;
	}

	public void setDoSize(boolean doSize) {
		this.doSize = doSize;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getCollapsible() {
		return collapsible;
	}

	public void setCollapsible(boolean collapsible) {
		this.collapsible = collapsible;
	}

	public boolean getMinimizable() {
		return minimizable;
	}

	public void setMinimizable(boolean minimizable) {
		this.minimizable = minimizable;
	}

	public boolean getMaximizable() {
		return maximizable;
	}

	public void setMaximizable(boolean maximizable) {
		this.maximizable = maximizable;
	}

	public boolean getClosable() {
		return closable;
	}

	public void setClosable(boolean closable) {
		this.closable = closable;
	}

	public String getOpenAnimation() {
		return openAnimation;
	}

	public void setOpenAnimation(String openAnimation) {
		this.openAnimation = openAnimation;
	}

	public int getOpenDuration() {
		return openDuration;
	}

	public void setOpenDuration(int openDuration) {
		this.openDuration = openDuration;
	}

	public String getCloseAnimation() {
		return closeAnimation;
	}

	public void setCloseAnimation(String closeAnimation) {
		this.closeAnimation = closeAnimation;
	}

	public int getCloseDuration() {
		return closeDuration;
	}

	public void setCloseDuration(int closeDuration) {
		this.closeDuration = closeDuration;
	}

	public boolean getCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

	public boolean getMinimized() {
		return minimized;
	}

	public void setMinimized(boolean minimized) {
		this.minimized = minimized;
	}

	public boolean getMaximized() {
		return maximized;
	}

	public void setMaximized(boolean maximized) {
		this.maximized = maximized;
	}

	public boolean getClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean getCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public String getLoadingMessage() {
		return loadingMessage;
	}

	public void setLoadingMessage(String loadingMessage) {
		this.loadingMessage = loadingMessage;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	public String getOnBeforeLoad() {
		return onBeforeLoad;
	}

	public void setOnBeforeLoad(String onBeforeLoad) {
		this.onBeforeLoad = onBeforeLoad;
	}

	public String getOnLoad() {
		return onLoad;
	}

	public void setOnLoad(String onLoad) {
		this.onLoad = onLoad;
	}

	public String getOnLoadError() {
		return onLoadError;
	}

	public void setOnLoadError(String onLoadError) {
		this.onLoadError = onLoadError;
	}

	public String getOnBeforeOpen() {
		return onBeforeOpen;
	}

	public void setOnBeforeOpen(String onBeforeOpen) {
		this.onBeforeOpen = onBeforeOpen;
	}

	public String getOnOpen() {
		return onOpen;
	}

	public void setOnOpen(String onOpen) {
		this.onOpen = onOpen;
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

	public String getOnBeforeDestroy() {
		return onBeforeDestroy;
	}

	public void setOnBeforeDestroy(String onBeforeDestroy) {
		this.onBeforeDestroy = onBeforeDestroy;
	}

	public String getOnDestroy() {
		return onDestroy;
	}

	public void setOnDestroy(String onDestroy) {
		this.onDestroy = onDestroy;
	}

	public String getOnBeforeCollapse() {
		return onBeforeCollapse;
	}

	public void setOnBeforeCollapse(String onBeforeCollapse) {
		this.onBeforeCollapse = onBeforeCollapse;
	}

	public String getOnCollapse() {
		return onCollapse;
	}

	public void setOnCollapse(String onCollapse) {
		this.onCollapse = onCollapse;
	}

	public String getOnBeforeExpand() {
		return onBeforeExpand;
	}

	public void setOnBeforeExpand(String onBeforeExpand) {
		this.onBeforeExpand = onBeforeExpand;
	}

	public String getOnExpand() {
		return onExpand;
	}

	public void setOnExpand(String onExpand) {
		this.onExpand = onExpand;
	}

	public String getOnResize() {
		return onResize;
	}

	public void setOnResize(String onResize) {
		this.onResize = onResize;
	}

	public String getOnMove() {
		return onMove;
	}

	public void setOnMove(String onMove) {
		this.onMove = onMove;
	}

	public String getOnMaximize() {
		return onMaximize;
	}

	public void setOnMaximize(String onMaximize) {
		this.onMaximize = onMaximize;
	}

	public String getOnRestore() {
		return onRestore;
	}

	public void setOnRestore(String onRestore) {
		this.onRestore = onRestore;
	}

	public String getOnMinimize() {
		return onMinimize;
	}

	public void setOnMinimize(String onMinimize) {
		this.onMinimize = onMinimize;
	}

}
