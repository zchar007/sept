function URL(url) {
	this.url = url;
	this.pdoData = new DataObject();
	this.pdoSystem = new DataObject();
	this.jsonHead = null;
	this.jsonData = null;

	this.dataType = "_json_";
	var urls = url.split("&");

	for (var int = 1; int < urls.length; int++) {
		var para = urls[int].trim().split("=");
		this.pdoData.put(para[0], para[1])
	}
	this.action = urls[0].split("?")[0];
	try {
		this.method = urls[0].split("?")[1].split("=")[1];
	} catch (e) {
		this.method = "";
	}
	if (StringUtil.startWith(url, "http")) {
		this.urlStr = this.action;
	} else {
		this.urlStr = _getRootPath() + this.action;
	}
	this.pdoSystem.put("_TYPE_", this.dataType);
	this.pdoSystem.put("_user_name_", CookieUtil.getCookie("_user_name_"));
	this.pdoSystem.put("_pass_word_", CookieUtil.getCookie("_pass_word_"));

}
URL.prototype.addPara = function(key, value) {
	this.pdoData.put(key, value);
};
URL.prototype.addForm = function(formObj) {
	if (formObj instanceof DataObject) {
		this.pdoData.putAll(formObj);
	}
};
URL.prototype.addGrid = function(key, gridObj) {
	if (gridObj instanceof DataStore) {
		this.pdoData.put(key, gridObj);
	}
};
URL.prototype.getUrlString = function() {
	// return this.action + "?method=" + this.method;
	return this.urlStr + "?method=" + this.method;
};
URL.prototype.getJsonHead = function() {
	if (null != this.jsonHead) {

		return this.jsonHead;
	}
	return this.pdoSystem.toJson();
};
URL.prototype.getJsonData = function() {
	if (null != this.jsonData) {
		return this.jsonData;
	}
	return this.pdoData.toJson();
};
URL.prototype.getMethod = function() {
	return this.method;
};
// 按理说头部是不能变的，这里先这样处理，应该得不能变
URL.prototype.setJsonHead = function(vJsonHead) {
	if ("{}" == vJsonHead || "" == vJsonHead || null == vJsonHead) {
		vJsonHead = "{" + vJsonHead + "}";
	}
	this.jsonHead = this.getJsonHead();
};
URL.prototype.setJsonData = function(vJsonData) {
	this.jsonData = vJsonData;
};
URL.prototype.setMethod = function(vMethod) {
	this.method = vMethod;
};