var Debug = {
	/**
	 * 获取页面路径
	 * 
	 * @returns
	 */
	_getPagePosition : function($object) {
		$object = Debug._getDebugObj($object);
		if (null == $object) {
			return "未知页面！";
		}
		// 先看自己是不是，自己不是再看其兄弟节点有没有，兄弟节点没有再找其父节点，然后在找其父节点的兄弟节点。。。。。
		if ($object.attr("class") == "sept-debug-panel") {
			var $children = $object.children();
			if ($children.length != 5) {
				return "框架错误，获取配置信息条数不正确";
			}
			var $request = $object.children("div.sept-request-stack");
			var nr = $request.html();
			if (null == nr || "" == nr) {
				nr = "未知页面！";
			}
			return nr;
		}
		return "nothing";
	},
	/**
	 * 刷新
	 * 
	 * @returns
	 */
	_refrsh : function($object) {
		$object = Debug._getDebugObj($object);
		if (null == $object) {
			alert("未刷新！");
			return;
		}
		if ($object.attr("class") == "sept-debug-panel") {
			var $children = $object.children();
			if ($children.length != 5) {
				alert("框架错误，获取配置信息条数不正确");
				return;
			}
			var request = $object.children("div.sept-request-url").text();
			if (request == location.href) {
				location.replace(location.href);
				return;
			}
			var jsonHead = $object.children("div.sept-request-head").text();
			var jsonData = $object.children("div.sept-request-data").text();
			if (null == jsonHead || "" == jsonHead) {
				jsonHead = "{}";
			}
			if (null == jsonData || "" == jsonData) {
				jsonData = "{}";
			}
			var url = new URL(request);
			url.setJsonHead(jsonHead);
			url.setJsonData(jsonData);
			var data = AjaxUtil.ajaxRequest(url);
			var $container = $object.parent().parent();
			$container.empty();
			$container.append(data);
			$.parser.parse($container);
		} else {
			alert("未刷新！");
		}
	},
	/**
	 * 单纯获取所点击的地方应该属于哪个刷新域
	 * 
	 * @param $object
	 * @returns
	 */
	_getDebugObj : function($object) {
		// 如果是class 是panel-title，则向下追溯
		if (!$object) {
			return $object;
		}
		//防止第一个点击的目标就是无class的
		while((typeof $object != "undefined") && ($object.attr("class") == undefined)){
			$object = $object.parent();
		 }
		// 直接向上追溯
		while ($object.attr("class").indexOf("sept-div-container", 0) < 0 && $object.attr("class").indexOf("sept-body-container", 0) < 0) {
			try {
				// 对于是以上是同级目录下子组件的，沿用以panel-title或panel-head为基准的查法
				$object = $object.parent();
				//object不是null,但是没有class属性的话
				while((typeof $object != "undefined") && ($object.attr("class") == undefined)){
					$object = $object.parent();
				 }
			//	alert($object+":"+$object.attr("class"));
			} catch (oE) {
				return null;
			}
			//alert($object.attr("class"));

			if ($object.attr("class") == undefined) {
				return null;
			}
		}
		if($object.attr("class").indexOf("sept-body-container", 0) >= 0){
			$object = $object.children();
		}
		// alert("找到了" + $object.attr("class"));
		var $objs = $object.children("div.sept-debug-panel");
		if ($objs.length == 1) {
			return $objs;
		}
		return null;
	}

};