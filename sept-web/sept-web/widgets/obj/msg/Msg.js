function Msg(messageId) {
	this.messageId = messageId;

}
Msg.prototype.hide = function() {
	mini.hideMessageBox(this.messageId);
};