var sept = (function() {
	// 循环
	var ForEach = function(start, end, doSomeThing) {
		for (var int = start; int < end; int++) {
			doSomeThing(int);
		}
	};
	return {
		ForEach : ForEach
	};
}());
