<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="true" keywords="asdsa,asdas" style="default">
	<stb:layout name="123" hidden="false">
		<stb:layoutCell name="nnn" region="n" split="true"></stb:layoutCell>
		<stb:layoutCell name="ccc" region="c">
			<stb:tab name="demotab">
				<stb:tabPage name="ymzs" labelValue="首页面"
					url="demo.do?method=fwdMainFramePage">
				</stb:tabPage>
				<stb:tabPage name="paneltest" labelValue="Panel测试"
					url="demo.do?method=fwdPanelDemoPage" />
				<stb:tabPage name="buttontest" labelValue="Button测试"
					url="demo.do?method=fwdButtonDemoPage" />
				<stb:tabPage name="accordiontest" labelValue="Accordion测试"
					url="demo.do?method=fwdAccordionDemoPage" />
				<stb:tabPage name="responsetest" labelValue="Response测试"
					url="demo.do?method=fwdResponseDemoPage" />
				<stb:tabPage name="downloadtest" labelValue="Download测试"
					url="demo.do?method=fwdDownloadDemoPage" />
				<stb:tabPage name="exceptiontest" labelValue="Exception测试"
					url="demo.do?method=fwdExceptionDemoPage" />
				<stb:tabPage name="menubuttontest" labelValue="MenuButton测试"
					url="demo.do?method=fwdMenuButtonDemoPage" />
				<stb:tabPage name="menubuttontest" labelValue="Grid测试"
					url="demo.do?method=fwdGridDemoPage" />
				<stb:tabPage name="menubuttontest" labelValue="Form测试"
					url="demo.do?method=fwdFormDemoPage" />
			</stb:tab>
		</stb:layoutCell>
	</stb:layout>
</stb:body>

