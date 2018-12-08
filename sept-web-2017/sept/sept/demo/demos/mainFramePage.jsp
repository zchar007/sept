<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="false">
	<stb:layout name="123" hidden="false">
		<stb:layoutCell name="lll" labelValue="Demo菜单" region="w">
			<stb:tree name="11112"
				classz="com.sept.framework.taglib.sept.tree.model.TestBulider"
				onClick="openIt" />
		</stb:layoutCell>
		<stb:layoutCell name="ccc" region="c">
			<stb:panel name="kkkk" border="0"/>

		</stb:layoutCell>
	</stb:layout>
</stb:body>
<script>
	function openIt(node) {
		$('#sept-name-kkkk').panel({
			href : node.attributes.target
		});
	}
</script>

