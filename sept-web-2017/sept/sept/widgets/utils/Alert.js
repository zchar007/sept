var Alert= ( function(){
//显示加载中...不会终止
var showLoading = function() {
	mini.mask({
		el : document.body,
		cls : 'mini-mask-loading',
		html : '加载中...'
	});
};
//终止加载中遮罩，立即执行
var endLoading = function() {
	mini.unmask(document.body);
};
//几秒后关闭加载中遮罩
var endLoadingByTime = function(time){
	setTimeout(function() {
		mini.unmask(document.body);
	}, time);
};
//显示自定义内容，可以是html代码，为null默认“加载中...”,在time秒后关闭,time为null则默认2000毫秒
var showLoadingByVT = function(value,time) {
	if(null == value || "" == value){
		value = "加载中...";
	}
	if(null == time || "" == time){
		time = 2000;
	}
	
	mini.mask({
		el : document.body,
		cls : 'mini-mask-loading',
		html : value
	});
	setTimeout(function() {
		mini.unmask(document.body);
	}, time);
};

//显示加载中...不会终止
var showMask = function(el) {
	mini.mask({
		el : el,
		cls : 'mini-mask-loading',
		html : '加载中...'
	});
};
//显示加载中...不会终止
var unMask = function(el) {
	mini.unmask(el);
};

//////////////////////////////提示性作用的提示框，不会产生遮罩
//在页面上部中间弹出提示，默认3秒后关闭
var showInfoTips = function(value) {
    var x = "center";
    var y = "top";
    var state = "info";
    mini.showTips({
        content: "<b>提示</b> <br/>"+value,
        state: state,
        x: x,
        y: y,
        timeout: 3000
    });
};
var showWarningTips = function(value) {
    var x = "center";
    var y = "top";
    var state = "warning";
    mini.showTips({
        content: "<b>警告</b> <br/>"+value,
        state: state,
        x: x,
        y: y,
        timeout: 3000
    });
};
var showSuccessTips = function (value) {
    var x = "center";
    var y = "top";
    var state = "success";
    mini.showTips({
        content: "<b></b> <br/>"+value,
        state: state,
        x: x,
        y: y,
        timeout: 3000
    });
};
   
var showDangerTips = function(value) {
    var x = "center";
    var y = "top";
    var state = "danger";
    mini.showTips({
        content: "<b>警告</b> <br/>"+value,
        state: state,
        x: x,
        y: y,
        timeout: 3000
    });
};
   
var showTips = function(value) {
    var x = "center";
    var y = "top";
    var state = "default";
    mini.showTips({
        content: value,
        state: state,
        x: x,
        y: y,
        timeout: 3000
    });
};

var showDiyTips = function(x,y,state,message,timeout) {
    mini.showTips({
        content: message,
        state: state,
        x: x,
        y: y,
        timeout: 3000
    });
};


var alertHtml = function(callback,id){
    var htmlContent = document.getElementById(id);
        htmlContent.style.display = "";
         mini.showMessageBox({
            width: 250,
            title: "自定义Html",
            buttons: ["ok", "cancel"],
            message: "自定义Html",
            html: htmlContent,
            showModal: false,
            callback: function (action){
            	callback(action);
            	 document.getElementById(id).innerHTML = htmlContent;
            }
        }); 
};
var alert = function(value){
	mini.alert(value);	
};

return {
	showLoading : showLoading,
	endLoading : endLoading,
	endLoadingByTime: endLoadingByTime,
	showLoadingByVT : showLoadingByVT,
	showInfoTips : showInfoTips,
	showWarningTips : showWarningTips,
	showSuccessTips : showSuccessTips,
	showDangerTips : showDangerTips,
	showDiyTips : showDiyTips,
	alertHtml : alertHtml,
	alert:alert,
	showMask:showMask,
	umMask:unMask
};

}());
   









