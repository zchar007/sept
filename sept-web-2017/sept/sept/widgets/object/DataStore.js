function DataStore() {
	this.arr = [];

}
DataStore.prototype.size = function() {
	return this.arr.length;
};
DataStore.prototype.addRow = function(dataObject) {
	/*
	 * if (arguments.length == 1) { this.arr.push(arguments[0]); } else if
	 * (arguments.length >= 2) { var deleteItem = this.arr[arguments[0]];
	 * this.arr.splice(arguments[0], 1, arguments[1], deleteItem) }
	 */
	if (null == dataObject || !(dataObject instanceof DataObject)) {
		this.arr.push(new DataObject());
		return;
	}
	this.arr.push(dataObject);
	return this;
};
DataStore.prototype.init = function(arrObj) {
	if(!arrObj){
		return -1;
	}
	for (var i = 0; i < arrObj.length; i++) {
		var vdo = new DataObject();
		for(var key in arrObj[i]){
			vdo.put(key, arrObj[i][key]);
		}
		this.addRow(vdo);
	}
	return this.arr.length;
};
DataStore.prototype.initByStr = function(arrStr) {
	if(!arrStr){
		return -1;
	}
	return this.init(eval('(' + arrStr + ')'));
};
DataStore.prototype.get = function(index) {
	return this.arr[index];
};
DataStore.prototype.getValue = function(index, name) {
	return this.arr[index].get(hm);
};
DataStore.prototype.removeIndex = function(index) {
	this.arr.splice(index, 1);
};
DataStore.prototype.removeObj = function(obj) {
	this.removeIndex(this.indexOf(obj));
};
DataStore.prototype.indexOf = function(obj) {
	for (var i = 0; i < this.arr.length; i++) {
		if (this.arr[i] === obj) {
			return i;
		}
	}
	return -1;
};
DataStore.prototype.isEmpty = function() {
	return this.arr.length == 0;
};
DataStore.prototype.clear = function() {
	this.arr = [];
};
DataStore.prototype.contains = function(obj) {
	return this.indexOf(obj) != -1;
};
DataStore.prototype.put = function(index, key, value) {
	if (this.arr.length <= index) {
		return -1;
	}
	return this.arr[index].put(key, value);
	;
};
DataStore.prototype.toJson = function() {
	var jsonStr = "[";
	for (var i = 0; i < this.arr.length; i++) {
		jsonStr += this.arr[i].toJson() + ",";
	}
	if (jsonStr.length > 1) {
		jsonStr = jsonStr.substr(0, jsonStr.length - 1);
	}
	jsonStr += "]";
	return jsonStr;
};
