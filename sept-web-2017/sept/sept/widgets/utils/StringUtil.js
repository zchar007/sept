var StringUtil = (function() {
	/**
	 * 替换所有 把str中，所有strA转换成strB
	 */
	var replaceAll = function(str, strA, strB) {
		if (!str || str == "") {
			return str;
		}
		if (!strA) {
			strA = "";
		}
		if (!strB) {
			strB = "";
		}
		strA = "/" + strA + "/g";

		str = str.replace(eval(strA), strB);
		return str;
	};
	var startWith = function(str1, str) {
		if (str == null || str == "" || str1.length == 0
				|| str.length > str1.length)
			return false;
		str1 = str1+"";
		if (str1.substring(0, str.length) == str)
			return true;
		else
			return false;
		return true;
	};

	var endWith = function(str1, str) {
		if (str == null || str == "" || str1.length == 0
				|| str.length > str1.length)
			return false;
		str1 = str1+"";
		if (str1.substring(str1.length - str.length) == str)
			return true;
		else
			return false;
		return true;
	};
	return {
		replaceAll : replaceAll,
		startWith : startWith,
		endWith : endWith
	};
}());