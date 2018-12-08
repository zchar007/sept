<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="false">
	<stb:layout name="123" hidden="false">
		<stb:layoutCell name="ccc" region="c" labelValue="按钮测试">
			<stb:button name="bt1" labelValue="普通按钮(非简洁效果)" iconCls="icon-search"
				onClick="alert('普通按钮(非简洁效果)')" />
			<stb:button name="bt1" labelValue="普通按钮(简洁效果)" plain="true"
				iconCls="icon-search" onClick="alert('普通按钮(简洁效果)')" />
			<stb:button name="bt2" labelValue="开关按钮" toggle="true" />
			<stb:button name="bt3" labelValue="大按钮" iconCls="icon-search"
				size="large" onClick="alert('大按钮')" />


			<stb:button name="bt4" labelValue="图标位置(上)" iconCls="icon-search"
				onClick="alert('图标位置(上)')" iconAlign="top" />
			<stb:button name="bt5" labelValue="图标位置(下)" iconCls="icon-search"
				onClick="alert('图标位置(下)')" iconAlign="bottom" />
			<stb:button name="bt6" labelValue="图标位置(右)" iconCls="icon-search"
				onClick="alert('图标位置(右)')" iconAlign="right" />
		</stb:layoutCell>
	</stb:layout>
</stb:body>

