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

			urls = URLStrUtil.addPara(urls, "_random", Math.random());
			var returnMsg = null;
			$.ajax({
				type : "POST",
				async : false,
				url : urls,
				data : {
					_SYSTEM_INFO_ : JSON.stringify(dataSys),
					_DATA_ : JSON.stringify(datas)
				},
				dataType : "text",
				scriptCharset : 'GBK',
				contentType : "application/x-www-form-urlencoded;charset=GBK",
				success : function(data, textStatus, jqXHR) {
					var isSucess = true;
					try {
						alert(data);
						$('#exception').append(data);
						// 过来的可能不是json数据
						var jsonObj = eval('(' + data + ')');
						returnMsg = jsonObj;
					} catch (e) {
						alert(e);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//一般是页面找不到的错误
					throw new Error("发起请求【" + requestUrl.getUrlString()
							+ "】时发生网络异常，数据未正常返回，请检查网络环境!--" + textStatus + "--"
							+ errorThrown + "-" + XMLHttpRequest.status + "--"
							+ XMLHttpRequest.responseText); // 这里为了让工作流停掉服务器后不报错，这里不提示
				}
			});
			return returnMsg;
		} catch (oE) {
			//alert(oE);
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
				alert("AjaxUtil.asynchAjaxRequest入参【requestUrl】不能为空，请检查!");
				return;
			}
			var urls = requestUrl.getUrlString();
			var jsonHead = requestUrl.getJsonHead();
			var jsonData = requestUrl.getJsonData();
			var dataSys = eval('(' + jsonHead + ')');
			var datas = eval('(' + jsonData + ')');

			urls = URLStrUtil.addPara(urls, "_random", Math.random());
			$.ajax({
				type : "POST",
				url : urls,
				data : {
					_SYSTEM_INFO_ : JSON.stringify(dataSys),
					_DATA_ : JSON.stringify(datas)
				},
				dataType : "text",
				scriptCharset : 'GBK',
				contentType : "application/x-www-form-urlencoded;charset=GBK"
			}).done(function(data, textStatus, jqXHR) {
				var returnMsg = null;
				var isSucess = true;
				try {
					// 过来的可能不是json数据,直接转，如果成功了说明无有错误
					var jsonObj = eval('(' + data + ')');
					returnMsg = jsonObj;
				} catch (e) {
					alert(e);
				}

				if (doneCallback) {
					doneCallback(returnMsg);
				}
			}).fail(
					function(XMLHttpRequest, textStatus, errorThrown) {
						if (failCallback) {
							failCallback();
						}
						throw new Error("发起请求【" + requestUrl.getUrlString()
								+ "】时发生网络异常，数据未正常返回，请检查网络环境!--" + textStatus);
					});
			return null;
		} catch (oE) {
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