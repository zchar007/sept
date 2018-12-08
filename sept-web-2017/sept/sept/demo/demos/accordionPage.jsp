<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="false">
	<stb:layout name="123" hidden="false">
		<stb:layoutCell name="lll" labelValue="左边Accordion" region="w" minWidth="400">
			<stb:accordions name="acccc">
				<%
					for (int i = 0; i < 10; i++) {
										String acname = "" + i + "Accordion按钮"; 
										String aclabel = "第" + i + "个Accordion";
										String btname = "" + i + "button";
										String btlabel = "第" + i + "个Accordion按钮";
				%>
				<stb:accordion name="<%=acname%>" labelValue="<%=aclabel%>" closable="true">
					<stb:button name="<%=btname%>" labelValue="<%=btlabel%>" onClick="alert('sdfds')" />
				</stb:accordion>
				<%
					}
				%>
			</stb:accordions>
		</stb:layoutCell>
		<stb:layoutCell name="ccc" region="c" labelValue="面板测试">


		</stb:layoutCell>
	</stb:layout>
</stb:body>
<script>
	function doIt(aaa) {
		alert(aaa);
	}
</script>

