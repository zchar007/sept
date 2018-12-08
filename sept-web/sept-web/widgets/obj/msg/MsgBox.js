var MsgBox = (function() {

	var alert = function(message, title, callback) {
		return new Msg(mini.alert(message, title, callback));
	};
	var confirm = function(message, title, callback) {
		return new Msg(mini.confirm(message, title, callback));
	};
	var prompt = function(message, title, callback, multi) {
		return new Msg(mini.prompt(message, title, callback, multi));
	};
	var loading = function(message, title) {
		return new Msg(mini.loading(message, title));
	};
	var showTips = function(options) {
		mini.showTips(options);
	};
	var showMessageBox = function(options) {
		return new Msg(mini.showMessageBox(options));
	};

	return {
		alert : alert,
		confirm : confirm,
		prompt : prompt,
		loading : loading,
		showTips : showTips,
		showMessageBox : showMessageBox
	};
}());