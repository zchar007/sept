var MessageFilter = (function() {
	/**
	 * 替换所有 把str中，所有strA转换成strB
	 */
	var getVarFromJsonVar = function(jsonVar) {
		if (!jsonVar || jsonVar == "") {
			return jsonVar;
		}
		var strVar = jsonVar;
		strVar = StringUtil.replaceAll(strVar, "<mh\\/>", ":");
		strVar = StringUtil.replaceAll(strVar, "<yh\\/>", "\"");
		strVar = StringUtil.replaceAll(strVar, "<zz\\/>", "[");
		strVar = StringUtil.replaceAll(strVar, "<yz\\/>", "]");
		strVar = StringUtil.replaceAll(strVar, "<zd\\/>", "{");
		strVar = StringUtil.replaceAll(strVar, "<yd\\/>", "}");
		strVar = StringUtil.replaceAll(strVar, "<dh\\/>", ",");

		return strVar;
	};
	/**
	 * 替换所有 把str中，所有strA转换成strB
	 */
	var getJsonVarFromVar = function(strVar) {
		if (!strVar || strVar == "") {
			return strVar;
		}
		var jsonVar = strVar;
		jsonVar = StringUtil.replaceAll(jsonVar, ":", "<mh/>");
		jsonVar = StringUtil.replaceAll(jsonVar, "\"", "<yh/>");
		jsonVar = StringUtil.replaceAll(jsonVar, "\\[", "<zz/>");
		jsonVar = StringUtil.replaceAll(jsonVar, "\\]", "<yz/>");
		jsonVar = StringUtil.replaceAll(jsonVar, "\\{", "<zd/>");
		jsonVar = StringUtil.replaceAll(jsonVar, "\\}", "<yd/>");
		jsonVar = StringUtil.replaceAll(jsonVar, ",", "<dh/>");
		// jsonVar = StringUtil.replaceAll(jsonVar, "\n", "");
		jsonVar = jsonVar.replace(/\n/g, "<br/>");
		return jsonVar;
	};

	var fliterSystemMessage = function(jsonObj) {
		if (jsonObj.__ERROR__FLAG__ == "__ERROR__BUSI__") {
			MsgTools.alert("提示", jsonObj["__ERROR__TEXT__"]);
			return false;
		}
		if (jsonObj.__ERROR__FLAG__ == "__ERROR__APP__") {
			var url = new URL("sept.do?method=fwdAppExceptionPage");
			url.addPara("_errortext_", jsonObj["__ERROR__TEXT__"]);
			var response = new Response("AppException",url);
			response.setDataOptions("resizable", false);
			response.setDataOptions("maximizable", false);
			response.setDataOptions("minimizable", false);
			response.open();
			return false;
		}
		if (jsonObj.__ERROR__FLAG__ == "__ERROR__FWD__MAIN__") {
			window.location.href = _getRootPath() + jsonObj["__ERROR__TEXT__"];
			return false;
		}
		return true;
	};

	return {
		getVarFromJsonVar : getVarFromJsonVar,
		getJsonVarFromVar : getJsonVarFromVar,
		fliterSystemMessage : fliterSystemMessage
	};
}());