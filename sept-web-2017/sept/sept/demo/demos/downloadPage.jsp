<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="false">
	<stb:layout name="123" hidden="false">
		<stb:layoutCell name="ccc" region="c" labelValue="按钮测试">
			<stb:button name="bt1" labelValue="下载" iconCls="icon-search"
				onClick="downloadIt()" />
		</stb:layoutCell>
	</stb:layout>
</stb:body>
<script type="text/javascript">
	function downloadIt() {
		var url = new URL("demo.do?method=downloadExcel");
		url.addPara("hello", "你好");
		var form = new Form();
		form.submit4Dynamic(url);
	}
</script>

