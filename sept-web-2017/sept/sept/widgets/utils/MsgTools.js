/**
 * 此处所有方法均不是阻塞方法
 */
var MsgTools = (function() {

	/**
	 * 弹出提示 这个应该是在最顶层的
	 */
	var alert = function(title, msg, icon, fn) {
		if (!title) {
			$.messager.alert("错误", "MsgTools.alert:传入title为空");
		}
		if (!msg) {
			$.messager.alert("错误", "MsgTools.alert:传入msg为空");
		}
		return $.messager.alert(title, msg, icon, fn);
	};

	/**
	 * 弹出提示 这个应该是在最顶层的 showType：定义将如何显示该消息。可用值有：null,slide,fade,show。默认：slide。
	 * showSpeed：定义窗口显示的过度时间。默认：600毫秒。 width：定义消息窗口的宽度。默认：250px。
	 * height：定义消息窗口的高度。默认：100px。 title：在头部面板显示的标题文本。 msg：显示的消息文本。
	 * style：定义消息窗体的自定义样式。
	 * timeout：如果定义为0，消息窗体将不会自动关闭，除非用户关闭他。如果定义成非0的树，消息窗体将在超时后自动关闭。默认：4秒。
	 */
	var show = function(title, msg, timeout, width, height, showSpeed,
			showType, style) {
		if (!title) {
			$.messager.alert("错误", "MsgTools.alert:传入title为空");
		}
		if (!msg) {
			$.messager.alert("错误", "MsgTools.alert:传入msg为空");
		}
		if (!timeout) {
			timeout = 4000;
		}
		if (!width) {
			width = 250;
		}
		if (!height) {
			height = 100;
		}
		if (!showSpeed) {
			showSpeed = 600;
		}
		if (!showType) {
			showType = "slide";
		}
		return $.messager.show({
			title : title,
			msg : msg,
			timeout : timeout,
			showType : showType,
			width : width,
			height : height,
			showSpeed : showSpeed,
			style : style
		});

	};

	/**
	 * 弹出提示 这个应该是在最顶层的
	 */
	var confirm = function(title, msg, fnSuccess, fnFail) {
		if (!title) {
			$.messager.alert("错误", "MsgTools.alert:传入title为空");
		}
		if (!msg) {
			$.messager.alert("错误", "MsgTools.alert:传入msg为空");
		}
		if (!fnSuccess) {
			Exception.throwErrorMessage("MsgTools.confirm",
					"fnSuccess-确定的方法不能为空");
			return;
		}
		$.messager.confirm(title, msg, function(r) {
			if (r) {
				fnSuccess();
			} else {
				if (fnFail) {
					fnFail();
				}
			}
		});
	};
	return {
		alert : alert,
		show : show,
		confirm : confirm
	};
}());