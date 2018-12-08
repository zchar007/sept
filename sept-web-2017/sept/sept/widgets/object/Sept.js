var Sept = (function(){
	//设置cookie
	function getObject(name){
		var Days=2;
		if(day){
			Days=day;
		}
		var exp=new Date();
		exp.setTime(exp.getTime()+Days*24*60*60*1000);   
		document.cookie = name+"="+escape(value)+";expires="+exp.toGMTString();
	}
	
	return{
		getObject:getObject
	};
}());