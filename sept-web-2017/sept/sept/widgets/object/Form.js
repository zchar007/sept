/**
 * 创建一个Form 本form目前供提交from使用 （当前并未定义jqueryui的from实体类）
 * 
 */
function Form(formObj) {
	this.formObj = formObj;
	this.values = new DataObject();
}
/**
 * panel none 返回面板对象。
 */
Form.prototype.addPara = function(key, value) {
	this.values.put(key, value);
};
/**
 * panel none 返回面板对象。
 */
Form.prototype.addHashMap = function(hm) {
	this.values.putAll(hm);
};
Form.prototype.removePara = function(key) {
	this.values.remove(key);
};
Form.prototype.submit = function() {
	this.formObj.form('submit');
};
Form.prototype.load = function(data) {
	this.formObj.form('load', data);
};
Form.prototype.clear = function() {
	this.formObj.form('clear');
};
Form.prototype.reset = function() {
	this.formObj.form('reset');
};
Form.prototype.validate = function() {
	this.validation(true);
	var bool = this.formObj.form('validate');
	// this.validation(false);
	return bool;
};
// 重置验证设置为默认值。（该方法自1.4.5版开始可用）
Form.prototype.resetValidation = function() {
	this.formObj.form('resetValidation');
};
// 是否启用验证
Form.prototype.validation = function(bool) {
	if (bool) {
		this.formObj.form('enableValidation');
	} else {
		this.formObj.form('disableValidation');
	}
};

/**
 * 
 * @param key
 */
Form.prototype.submit4Dynamic = function(requestUrl) {
	try {
		//试探性执行一下，没有错误再执行下载，权宜之计
		AjaxUtil
				.asynchAjaxRequest(
						requestUrl,
						function() {
							// 创建Form
							var form = $("<form onsubmit=\"document.charset='UTF-8';\"></form>");
							// 设置属性
							form.attr('action', requestUrl.getUrlString());
							form.attr('method', 'GET');
							// form的target属性决定form在哪个页面提交
							// _self -> 当前页面 _blank -> 新页面
							form.attr('target', '_self');
							var methodV = $('<input type="text" name="method" />');
							methodV.attr('value', requestUrl.getMethod());
							form.append(methodV);
							// 创建Input
							var typeV = $('<input type="text" name="__SYSTEM_INFO__" />');
							typeV.attr('value', requestUrl.getJsonHead());
							form.append(typeV);
							var dataV = $('<input type="text" name="__DATA__" />');
							dataV.attr('value', requestUrl.getJsonData());
							form.append(dataV);

							// var keys = this.values.keySet();
							// // 添加当前form的参数
							// for ( var i = 0; i < keys.length; i++) {
							// // 创建Input
							// var my_input = $('<input type="text" name="' +
							// keys[i] + '" />');
							// my_input.attr('value', this.values.get(keys[i]));
							// //alert(this.values.get(keys[i]));
							// // 附加到Form
							// form.append(my_input);
							// }
							$(document.body).append(form);
							// 提交表单
							form.submit();
							form.remove();
						});

		// 注意return false取消链接的默认动作
		return false;
	} catch (e) {
		Exception.throwError("Form.submit", e);
	}

};
