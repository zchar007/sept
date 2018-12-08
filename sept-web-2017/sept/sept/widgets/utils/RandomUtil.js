var RandomUtil = {
	/**
	 * 获取以当前时间为基准的随机数14位
	 * 
	 * @returns
	 */
	getRandomStrByTime : function(index) {
		var formatStr = "yyyyMMddhhmmss";
		if (index) {
			if (index == 6) {
				formatStr = "hhmmss";
			} else if (index == 8) {
				formatStr = "ddhhmmss";
			} else if (index == 10) {
				formatStr = "MMddhhmmss";
			}
		}
		return DateUtil.formatDate(new Date(), formatStr);
	},
	/**
	 * 获取页面路径
	 * 
	 * @returns
	 */
	getRandomStr : function(index) {
		var chars = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z' ];
		var res = "";
		for ( var i = 0; i < index; i++) {
			var id = Math.ceil(Math.random() * 59);
			res += chars[id];
		}
		return res;

	}

};