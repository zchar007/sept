// 当前js所在路径
var bootPATH = __CreateJSPath("boot.js");

document.write('<script src="' + bootPATH + 'widgets/object/Date.js" type="text/javascript"></script>');

var version = new Date().getTime();
// jquery
document.write('<script src="' + bootPATH + 'widgets/jquery/jquery.min.js?version=' + version + '" type="text/javascript"></script>');
// jquery-ui
document.write('<script src="' + bootPATH + 'widgets/jquery/jquery.easyui.min.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'widgets/jquery/jquery.insdep-extend.min.js?version=' + version + '" type="text/javascript"></script>');

//loader
document.write('<script src="' + bootPATH + 'widgets/loader/Loader.js?version=' + version + '" type="text/javascript"></script>');
//Exception 
document.write('<script src="' + bootPATH + 'widgets/exception/Exception.js?version=' + version + '" type="text/javascript"></script>');
// DataObject ,DataStore
document.write('<script src="' + bootPATH + 'widgets/object/DataObject.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'widgets/object/DataStore.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'widgets/object/Form.js?version=' + version + '" type="text/javascript"></script>');

//ui  ui/Response.js
document.write('<script src="' + bootPATH + 'widgets/object/ui/Response.js?version=' + version + '" type="text/javascript"></script>');

// URL
document.write('<script src="' + bootPATH + 'widgets/object/URL.js?version='+ version + '" type="text/javascript"></script>');
// utils
document.write('<script src="' + bootPATH + 'widgets/utils/StringUtil.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'widgets/utils/RandomUtil.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'widgets/utils/BusinessTools.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'widgets/utils/AjaxUtil.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'widgets/utils/MsgTools.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'widgets/utils/MessageFilter.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'widgets/utils/CookieUtil.js?version=' + version + '" type="text/javascript"></script>');
document.write('<script src="' + bootPATH + 'widgets/utils/Alert.js?version=' + version + '" type="text/javascript"></script>');


//debug
document.write('<script src="' + bootPATH + 'widgets/debug/Debug.js?version=' + version + '" type="text/javascript"></script>');
