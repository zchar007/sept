//获取js的路径
__CreateJSPath = function(js) {
	var scripts = document.getElementsByTagName("script");
	var path = "";
	for (var i = 0, l = scripts.length; i < l; i++) {
		var src = scripts[i].src;
		if (src.indexOf(js) != -1) {
			var ss = src.split(js);
			path = ss[0];
			break;
		}
	}
	var href = location.href;
	href = href.split("#")[0];
	href = href.split("?")[0];
	var ss = href.split("/");
	ss.length = ss.length - 1;
	href = ss.join("/");
	if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1
			&& path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
		path = href + "/" + path;
	}
	return path;
};
_getRootPath = function() {
	var url = window.location.href;
	var centerUrl = "http://" + window.location.host;
	var appName = url.substring(centerUrl.length, url.length).split("/");
	var index = 0;
	while (null == appName[index] || appName[index] == "") {
		index++;
	}
	return centerUrl + "/" + appName[index] + "/";
};
_getRootPathNoApp = function() {
	var centerUrl = "http://" + window.location.host;
	return centerUrl;
};
// 换皮肤
_changeTheme = function(themeName) {
	var $easyuiTheme = $('#_easyui_Theme_');
	var $easyuiThemeIcon = $('#_easyui_Theme_icon_');
	var href = __CreateJSPath("boot.js") + 'themes/' + themeName
			+ '/easyui.css';
	var hrefIon = __CreateJSPath("boot.js") + 'themes/' + themeName
	+ '/icon.css';
	$easyuiTheme.attr('href', href);
	$easyuiThemeIcon.attr('href', hrefIon);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for (var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			$(ifr).contents().find('#_easyui_Theme_').attr('href', href);
			$(ifr).contents().find('#_easyui_Theme_icon_').attr('href', hrefIon);
		}
	}
	CookieUtil.setCookie("_main_style_", themeName);
};

_getCookie = function(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
};

_setCookie = function(c_name, value, expiredays) {
	var exdate = new Date()
	exdate.setDate(exdate.getDate() + expiredays)
	document.cookie = c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
};
