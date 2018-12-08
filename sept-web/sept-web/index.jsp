<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/STag" prefix="s"%>
<s:body>
	<s:diy>
		<button onclick="doIt()">发送</button>
		<div id="exception"></div>
	</s:diy>
</s:body>
<script type="text/javascript">
	function doIt() {
		var url = new URL("sept.do?method=demo");
		url.addPara("name", "张三");
		var data = AjaxUtil.ajaxRequest(url);
		alert(data);
	}
</script>
