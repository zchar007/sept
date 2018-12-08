<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="false">
	<stb:layout name="123" hidden="false">
		<stb:layoutCell name="ccc" region="c" labelValue="Exception测试">
			<stb:diy>
				<a href="javascript:void(0)" id="mb" class="easyui-menubutton"
					data-options="menu:'#mm',iconCls:'icon-edit'" plain="false" >Edit</a>
				<a href="javascript:void(0)" id="mb1" class="easyui-menubutton"
					data-options="menu:'#mm',iconCls:'icon-edit'" plain="false" >Edit</a>
				<a href="javascript:void(0)" id="mb2" class="easyui-menubutton"
					data-options="menu:'#mm',iconCls:'icon-edit'" plain="false" >Edit</a>
				<a href="javascript:void(0)" id="mb3" class="easyui-menubutton"
					data-options="menu:'#mm',iconCls:'icon-edit'" plain="false" >Edit</a>
				<div id="mm" style="width: 150px;">
					<div data-options="iconCls:'icon-undo'">Undo</div>
					<div data-options="iconCls:'icon-redo'">Redo</div>
					<div class="menu-sep"></div>
					<div>Cut</div>
					<div>Copy</div>
					<div>Paste</div>
					<div class="menu-sep"></div>
					<div data-options="iconCls:'icon-remove'">Delete</div>
					<div>Select All</div>
				</div>
			</stb:diy>
		</stb:layoutCell>
	</stb:layout>
</stb:body>
<script>
	function testException(type) {
		var url = new URL("demo.do?method=exceptionDemo");
		url.addPara("type", type);
		var x = AjaxUtil.ajaxRequest(url);
	}
</script>

