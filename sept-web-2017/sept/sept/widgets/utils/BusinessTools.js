var BusinessTools = (function() {
	// 如果含有非法字符，由于转换关系，转回获取真实数据
	var addOthers = function(url) {
		if (!url) {
			alert("AjaxUtil.addPara入参【url】不能为空，请检查!");
			return null;
		}
		url = BusinessTools.addUser(url);
		return url;
	};
	var addUser = function(url) {
		if (!url) {
			alert("AjaxUtil.addPara入参【url】不能为空，请检查!");
			return null;
		}
		url = BusinessTools.addPara(url,"user","张三李四");
		return url;
	};
	
	/**
	 * 给url串拼接参数
	 */
	var addPara = function(url, key, value) {
		if (!url) {
			alert("AjaxUtil.addPara入参【url】不能为空，请检查!");
			return null;
		}

		if (url.indexOf("?") != -1) {
			url += "&" + key + "=" + value;
		} else {
			url += "?" + key + "=" + value;
		}
		return url;
	};
	return {
		addPara:addPara,
		addOthers : addOthers,
		addUser:addUser
	};
}());