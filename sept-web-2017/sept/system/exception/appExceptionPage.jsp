<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String exceptiontext = (String) request.getAttribute("_errortext_");
%>
<stb:body root="false">
	<stb:layout name="app_exception" hidden="false">
		<stb:layoutCell region="w" name="www" maxWidth="15" border="0" split="false"></stb:layoutCell>
		<stb:layoutCell region="e" name="eee" maxWidth="15"></stb:layoutCell>

		<stb:layoutCell name="ccc" region="c">
			<stb:diy>
				<font size="3" style="color:red;"><%=exceptiontext%></font>
			</stb:diy>
		</stb:layoutCell>
	</stb:layout>
</stb:body>
