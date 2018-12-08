/**
 * 用来存储hashMap对象
 */
// 创建一个Map
function DataObject() {
	this.values = {};
	this.isValidating = false;
	this.keyArray = [];
}

// 清空一个map
DataObject.prototype.clear = function() {
	this.values = {};
	this.keyArray = [];
};

// 判断是否存在关键字
DataObject.prototype.containsKey = function(key) {
	return typeof (this.values[key]) != "undefined";
};

// 判断是否存在值
DataObject.prototype.containsValue = function(value) {
	for ( var key in this.values) {
		var val = this.values[key];
		if (val == value) {
			return true;
		}
	}
	return false;
};
// Returns value for the key.
DataObject.prototype.get = function(key) {
	if (this.containsKey(key)) {
		return this.values[key];
	} else {
		if (this.isValidating) {
			throw new Error("HashMap.get方法：找不到关键字'" + key + "'");
		}
		return null;
	}
};

// Tests if this map has no elements.
DataObject.prototype.isEmpty = function() {
	return !this.size();
};

// Returns an array of the keys contained in this map.
DataObject.prototype.keySet = function() {
	return this.keyArray;
};

// 放入一个值
DataObject.prototype.put = function(key, value) {
	if (this.containsKey(key)) {
		Exception.throwErrorMessage("HashMap.put", "已存在名称为【" + key + "】的对象");
		return this.get(key);
	}
	// alert("插入前是不是DataStore?");
	// alert(value instanceof DataStore);
	this.values[key] = value;
	this.keyArray.push(key);
	// alert("插入后是不是DataStore?");
	// alert(this.get(key) instanceof DataStore);
	return value;
};
// 放入个hm值
DataObject.prototype.putAll = function(hm) {
	if (!hm) {
		Exception.throwErrorMessage("HashMap.putAll", "传入hm为空");
	}
	var keys = hm.keySet();
	// 添加当前form的参数
	for (var i = 0; i < keys.length; i++) {
		if (this.containsKey(keys[i])) {
			continue;
		}
		this.values[keys[i]] = hm.get(keys[i]);
		this.keyArray.push(keys[i]);
	}

};

// 移除一个值
DataObject.prototype.remove = function(key) {
	var value = this.containsKey(key) ? this.get(key) : undefined;
	if (value || (typeof (value) == "number" && value === 0)) {
		delete this.values[key];
		for (var i = 0; i < this.keyArray.length; i++) {
			if (this.keyArray[i] == key) {
				this.keyArray.splice(i, 1);
			}
		}
	}
	return value;
};

// 获取hashMap的值
DataObject.prototype.size = function() {
	var count = 0;
	for ( var key in this.values) {
		key;
		count = count + 1;
	}
	return count;
};
// 获取hashMap的值
DataObject.prototype.toJson = function() {
	var jsonStr = "{";
	for ( var key in this.values) {
		var val = this.values[key];
		if (val instanceof DataObject) {
			jsonStr += "\"" + key + "\":" + val.toJson() + ",";
		} else if (val instanceof DataStore) {
			jsonStr += "\"" + key + "\":" + val.toJson() + ",";
		} else {
			jsonStr += "\"" + key + "\":\"" + val + "\",";
		}
	}
	if (jsonStr.length > 1) {
		jsonStr = jsonStr.substring(0, jsonStr.length - 1);
	}
	jsonStr += "}";
	return jsonStr;
};
