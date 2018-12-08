// 当前js所在路径
var bootPATH = __CreateJSPath("boot.js");
document.write('<script src="' + bootPATH + 'obj/Date.js" type="text/javascript"></script>');

var version = new Date().getTime();
//jquery
document.write('<script src="' + bootPATH + 'jquery/jquery.min.js?version=' + version + '" type="text/javascript"></script>');

//miniui
document.write('<script src="' + bootPATH + '/miniui/miniui.js?version=' + version + '" type="text/javascript" ></sc' + 'ript>');
//obj
document.write('<script src="' + bootPATH + 'obj/URL.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'obj/DataObject.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'obj/DataStore.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'obj/msg/Msg.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'obj/msg/MsgBox.js?version=' + version + '" type="text/javascript"></script>');
//util
document.write('<script src="' + bootPATH + 'util/CookieUtil.js?version='+ version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'util/AjaxUtil.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'util/URLStrUtil.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'util/StringUtil.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'bootstrap/js/bootstrap.js?version=' + version + '" type="text/javascript"></script>');
document.write('<link href="' + bootPATH + 'bootstrap/css/bootstrap.css?vesion=' + version + '" rel="stylesheet" type="text/css" />');
document.write('<script src="' + bootPATH + 'jqueryui/jquery-ui.js?version=' + version + '" type="text/javascript"></script>');
document.write('<link href="' + bootPATH + 'jqueryui/jquery-ui.css?vesion=' + version + '" rel="stylesheet" type="text/css" />');

//css
var skin = getCookie("miniuiSkin") || 'cupertino';             //skin cookie   cupertino
var mode = getCookie("miniuiMode") || 'medium';                 //mode cookie     medium     

//skin
if (skin && skin != "default") document.write('<link href="' + bootPATH + 'miniui/themes/' + skin + '/skin.css?vesion=' + version + '" rel="stylesheet" type="text/css" />');
//mode
if (mode && mode != "default") document.write('<link href="' + bootPATH + 'miniui/themes/default/' + mode + '-mode.css?vesion=' + version + '" rel="stylesheet" type="text/css" />');




//获取js的路径
function __CreateJSPath(js) {
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
function _getRootPath() {
	var url = window.location.href;
	var centerUrl = "http://" + window.location.host;
	var appName = url.substring(centerUrl.length, url.length).split("/");
	var index = 0;
	while (null == appName[index] || appName[index] == "") {
		index++;
	}
	return centerUrl + "/" + appName[index] + "/";
};
function _getRootPathNoApp() {
	var centerUrl = "http://" + window.location.host;
	return centerUrl;
};
function getCookie(sName) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
            lastMatch = aCrumb;
        }
    }
    if (lastMatch) {
        var v = lastMatch[1];
        if (v === undefined) return v;
        return unescape(v);
    }
    return null;
}
