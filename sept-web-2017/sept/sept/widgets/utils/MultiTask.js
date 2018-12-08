var MultiTask = (function() {
	var doResponse = new DataObject();
	var newTask = function(vTitle, vRequestUrl, vWidth, vHeight) {
		var response = new Response(vTitle, vRequestUrl, vWidth, vHeight);
		var panelId = response.getId();
		doResponse.put(panelId, response);
		return panelId;
	};
	var openTask = function(panelId) {
		if(doResponse.containsKey(panelId)){
			var response = doResponse.get(panelId);
			response.op
		}
	};

	return {
		newTask : newTask,
		openTask : openTask
	};
}());