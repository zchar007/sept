<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="false">
	<stb:layout name="123" hidden="false">
		<stb:layoutCell name="ccc" region="c" labelValue="按钮测试">
			<stb:button name="bt1" labelValue="打开Response" iconCls="icon-search"
				onClick="openIt()" />
			<stb:button name="bt2" labelValue="关闭Response" iconCls="icon-search"
				onClick="closeIt()" />
			<stb:diy>
				<div id="cc" class="easyui-calendar"
					style="width:180px;height:180px;"></div>
				<input class="easyui-datetimebox" name="birthday"
					data-options="required:true,showSeconds:false" value="3/4/2010 2:3"
					style="width:150px">

				<form id="ff" method="post">
					<div>
						<label for="name">Name:</label> <input class="easyui-validatebox"
							type="text" name="name" data-options="required:true" />
					</div>
					<div>
						<label for="email">Email:</label> <input
							class="easyui-validatebox" type="text" name="email"
							data-options="validType:'email'" />
					</div>
					...
				</form>
			</stb:diy>
			<stb:button name="bt3" labelValue="提交" iconCls="icon-search"
				onClick="sub()" />
		</stb:layoutCell>
	</stb:layout>
</stb:body>
<script defer="defer">
	var response = null;
	function openIt() {
		response = new Response("打开Response例子", new URL(
				"demo.do?method=fwdResponsePage"));
		response.open();
	}
	function closeIt() {
		if (response) {
			response.close();
		}
	}
	function sub(){
		$('#ff').form('submit', {    
    url:"demo.do?method=fwdResponsePage",    
    onSubmit: function(param){    
	alert(param);
	return false;  
    }    
});
	}
	
	function onBe(param){
	alert(param);
	return false;
	}
</script>

