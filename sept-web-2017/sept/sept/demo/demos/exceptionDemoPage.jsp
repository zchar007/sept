<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="false">
	<stb:layout name="123" hidden="false">
		<stb:layoutCell name="ccc" region="c" labelValue="Exception测试">
			<stb:button name="bt1" labelValue="BussinessException"
				iconCls="icon-search" onClick="testException('buss')" />
			<stb:button name="bt2" labelValue="AppException"
				iconCls="icon-search" onClick="testException('app')" />
		</stb:layoutCell>
	</stb:layout>
</stb:body>
<script>
	function testException(type) {
		var url = new URL("demo.do?method=exceptionDemo");
		url.addPara("type", type);
		var x = AjaxUtil.ajaxRequest(url);
		alert("2342");
	}
</script>

