<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="false">
	<stb:layout name="123" hidden="false">
		<stb:layoutCell name="ccc" region="c" labelValue="面板测试">
 			<stb:panel name="testpanel12" labelValue="1112" border="true" left_x="200" top_y="100" fit = "false" closable="true">
				<stb:tools name="testtt2">
					<stb:tbutton name="1213" iconCls="icon-ok" onClick="doIt('12123123')" />
				</stb:tools>
			</stb:panel> 

		</stb:layoutCell>
	</stb:layout>
</stb:body>
<script>
	function doIt(aaa) {
		alert(aaa);
	}
</script>

