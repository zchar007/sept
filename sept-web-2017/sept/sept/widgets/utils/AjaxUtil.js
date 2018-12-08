var AjaxUtil = (function() {
	/**
	 * 同步Ajax请求 如需返回字符串，isGetText设为true即可
	 */
	var ajaxRequest = function(requestUrl) {

		try {
			if (!requestUrl) {
				alert("AjaxUtil.ajaxRequest入参【requestUrl】不能为空，请检查!");
				return;
			}
			var urls = requestUrl.getUrlString();
			var jsonHead = requestUrl.getJsonHead();
			var jsonData = requestUrl.getJsonData();
			var dataSys = eval('(' + jsonHead + ')');
			var datas = eval('(' + jsonData + ')');

			urls = BusinessTools.addPara(urls, "_random", Math.random());
			// urls = BusinessTools.addOthers(urls);
			var returnMsg = null;
			// $.easyui.loading({ msg: "正在加载..." });
			$
					.ajax({
						type : "POST",
						async : false,
						url : urls,
						data : {
							__SYSTEM_INFO__ : JSON.stringify(dataSys),
							__DATA__ : JSON.stringify(datas)
						},
						dataType : "text",
						scriptCharset : 'utf-8',
						contentType : "application/x-www-form-urlencoded;charset=UTF-8",
						success : function(data, textStatus, jqXHR) {
							var isSucess = true;
							try {
								// 过来的可能不是json数据
								var jsonObj = eval('(' + data + ')');
								isSucess = MessageFilter
										.fliterSystemMessage(jsonObj);
								returnMsg = jsonObj;
							} catch (e) {
								returnMsg = data;
								isSucess = true;
							}
							// 过滤系统消息，并返回true，如果返回的是false，说明出了什么状况
							if (!isSucess) {
								throw new Error("NOTSHOW__发起请求【"
										+ requestUrl.getUrlString() + "】时发生异常!");
							}
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							throw new Error("发起请求【" + requestUrl.getUrlString()
									+ "】时发生网络异常，数据未正常返回，请检查网络环境!--"
									+ textStatus + "--" + errorThrown + "-"
									+ XMLHttpRequest.status + "--"
									+ XMLHttpRequest.responseText); // 这里为了让工作流停掉服务器后不报错，这里不提示
						}
					});
			// $.easyui.loaded("div.sept-body-container");
			return returnMsg;
		} catch (oE) {
			if (!StringUtil.startWith(oE, "Error: NOTSHOW__")) {
				Exception.throwError("Ajax.ajaxRequest", oE);
			}
			throw oE;
		}
	};

	/**
	 * 异步Ajax请求 如需返回字符串，isGetText设为true即可
	 */
	var asynchAjaxRequest = function(requestUrl, doneCallback, failCallback) {
		try {
			// 入参合法性校验
			if (!requestUrl) {
				alert("AjaxUtil.ajaxRequest入参【requestUrl】不能为空，请检查!");
				return;
			}
			var urls = requestUrl.getUrlString();
			var jsonHead = requestUrl.getJsonHead();
			var jsonData = requestUrl.getJsonData();
			var dataSys = eval('(' + jsonHead + ')');
			var datas = eval('(' + jsonData + ')');

			urls = BusinessTools.addPara(urls, "_random", Math.random());
			$.ajax({
				type : "POST",
				url : urls,
				data : {
					__SYSTEM_INFO__ : JSON.stringify(dataSys),
					__DATA__ : JSON.stringify(datas)
				},
				contentType : "application/x-www-form-urlencoded;charset=UTF-8"
			}).done(
					function(data, textStatus, jqXHR) {
						var returnMsg = null;
						var isSucess = true;
						try {
							// 过来的可能不是json数据,直接转，如果成功了说明无有错误
							var jsonObj = eval('(' + data + ')');
							isSucess = MessageFilter
									.fliterSystemMessage(jsonObj);
							returnMsg = jsonObj;
						} catch (e) {
							returnMsg = data;
						}

						if (!isSucess) {
							throw new Error("NOTSHOW__发起请求【" + requestUrl.getUrlString()
									+ "】时发生异常!");
						} else {
							if (doneCallback) {
								doneCallback(returnMsg);
							}
						}
					}).fail(
					function(XMLHttpRequest, textStatus, errorThrown) {
						Alert.endLoading();
						if (failCallback) {
							failCallback();
						}
						throw new Error("发起请求【" + requestUrl.getUrlString()
								+ "】时发生网络异常，数据未正常返回，请检查网络环境!--" + textStatus);
					});
			// Alert.endLoading();
			return null;
		} catch (oE) {
			if (!StringUtil.startWith(oE, "Error: NOTSHOW__")) {
				Exception.throwError("Ajax.asynchAjaxRequest", oE);
			}
			throw oE;
		}
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
		ajaxRequest : ajaxRequest,
		asynchAjaxRequest : asynchAjaxRequest,
		addPara : addPara
	};
}());