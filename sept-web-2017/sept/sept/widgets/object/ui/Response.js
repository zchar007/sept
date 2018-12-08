function Response(vTitle, vRequestUrl, vWidth, vHeight) {
	// 托盘的ID
	this.panelId = RandomUtil.getRandomStr(4) + new Date().getTime();
	// window的ID
	this.responseId = RandomUtil.getRandomStr(4) + new Date().getTime();
	this.title = vTitle;
	this.requestUrl = vRequestUrl;
	this.width = 1024;
	this.height = 600;
	if (vWidth) {
		this.width = vWidth;
	}
	if (vHeight) {
		this.height = vHeight;
	}
	// 以下是可以设置的data-options
	this.data_option = {
		proxy : 'clone',
		// content : this.content,
		// collapsible boolean 定义是否显示可折叠按钮。 true
		collapsible : true,
		// minimizable boolean 定义是否显示最小化按钮。 true
		minimizable : true,
		// maximizable boolean 定义是否显示最大化按钮。 true
		maximizable : true,
		// closable boolean 定义是否显示关闭按钮。 true
		closable : true,
		// closed boolean 定义是否可以关闭窗口。 false
		closed : false,
		// zIndex number 窗口Z轴坐标。 9000
		zIndex : 9000,// 最上层
		// draggable boolean 定义是否能够拖拽窗口。 true '
		draggable : true,
		// resizable boolean 定义是否能够改变窗口大小。 true
		resizable : true,
		// shadow boolean 如果设置为true，在窗体显示的时候显示阴影。 true
		shadow : true,
		// inline boolean 定义如何布局窗口，如果设置为true，窗口将显示在它的父容器中，否则将显示在所有元素的上面。 false
		inline : false,
		// modal boolean 定义是否将窗体显示为模式化窗口。 true
		modal : true,
		// border boolean,string
		// 定义窗体边框的样式。可用值：true，false，'thin'，'thick'。（该方法自1.4.5版开始可用） true
		border : 'thin',
		// constrain boolean 定义是否限制窗体的位置。（该方法自1.5版开始可用） false
		constrain : false,
		iconCls : ''
	};
}
Response.prototype.open = function() {
	// 查看是否存在panel和window
	var panel = $("div#" + this.panelId);
	var response = $("div#" + this.responseId);
	// 如果都存在，直接显示,也只有这种情况说明之前创建过此window
	if (panel.length != 0 && response.length != 0) {
		response.window('open');
		return;
	}
	// 单独存在的话重新生成ID，直至没有重复的
	while (panel.length != 0) {
		this.panelId = RandomUtil.getRandomStr(4) + new Date().getTime();
		panel = $("div#" + this.panelId);
	}
	while (response.length != 0) {
		this.responseId = RandomUtil.getRandomStr(4) + new Date().getTime();
		response = $("div#" + this.responseId);
	}
	// 生成承载window的panel
	var panelHtml = "<div id = \"" + this.panelId + "\">\n</div>";
	$("body.sept-body-container").append(panelHtml);

	this.html = "<div id=\"" + this.responseId
			+ "\" class=\"easyui-window\" title=\"" + this.title + "\"\n"
			+ "  style=\"width:" + this.width + "px;height:" + this.height
			+ "px\"\n" + "  data-options=\"\">\n";

	var data = AjaxUtil.ajaxRequest(this.requestUrl);
	this.html += data;

	this.html += "\n" + "</div>";
	$("div#" + this.panelId).append(this.html);
	$.parser.parse("div#" + this.panelId);
	$("div#" + this.responseId).window(this.data_option);

};
// 设置data-options,在显示之前修改，否则不生效
Response.prototype.setDataOptions = function(name, value) {
	if (typeof value === "undefined") {
		// 删除属性
		delete this.data_option[prop];
	} else {
		this.data_option[name] = value;
	}
};
// 获取data-options
Response.prototype.getDataOptions = function() {
	return this.datas_option;
};
// 获取data-options
Response.prototype.destroy = function() {
	// 查看是否存在panel和window
	var panel = $("div#" + this.panelId);
	var response = $("div#" + this.responseId);
	// 如果都存在，直接显示,也只有这种情况说明之前创建过此window
	if (panel.length != 0 && response.length != 0) {
		response.window('close');
	}
	response.window("destroy");
	panel.remove();

	return true;
};
// 获取data-options
Response.prototype.close = function() {
	// 查看是否存在panel和window
	var panel = $("div#" + this.panelId);
	var response = $("div#" + this.responseId);
	// 如果都存在，直接显示,也只有这种情况说明之前创建过此window
	if (panel.length != 0 && response.length != 0) {
		response.window('close');
	}
	return true;
};
Response.prototype.getId = function(){
	return this.responseId;
};