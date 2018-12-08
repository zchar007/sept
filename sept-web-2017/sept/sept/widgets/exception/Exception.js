
var Exception = (function(){
	
	function throwErrorMessage(method,message){
		if(!method){
			Exception.throwErrorMessage("Exception.throwErrorMessage","method为空！");
		}
		if(!message){
			Exception.throwErrorMessage("Exception.throwErrorMessage","message为空！");
		}
		
		MsgTools.alert(method,message);
		throw method+":"+message;
	}
	
	function throwError(method,oE){
		MsgTools.alert(method,oE.message);
		throw method+":"+oE.message;
	}
	
	function handler(method,oE){
		alert(method+":" + oE.message);
	}
	
	function showException(errHTML){
		if(!errHTML){
			return;
		}
		var url = "syspage/error/exception.jsp?errorhtml="+errHTML;
		var response = RESController.openRES("异常信息",url);
		response.show();
	}
	
	return {
		throwErrorMessage : throwErrorMessage,
		throwError : throwError,
		handler : handler,
		showException : showException
	};
}());