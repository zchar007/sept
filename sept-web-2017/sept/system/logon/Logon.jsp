<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/testTag" prefix="test"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<test:body root="true" keywords="asdsa,asdas" style="sept">
	<test:diy>
	</test:diy>
	<test:layout name="123" hidden="false" onAdd="showit">
		<test:layoutCell name="nnn" region="n" labelValue="你好" split="true">
		</test:layoutCell>
		<test:layoutCell name="ccc" region="c" labelValue="欢迎!">
			<test:diy>
				<div style="margin:20px 0;">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="$('#w').window('open')">Open</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						onclick="$('#w').window('close')">Close</a>
				</div>
				<div id="w" class="easyui-window" title="请先登录"
					data-options="modal:true,closed:true,iconCls:'Lockgo',closable:false,minimizable:false"
					style="width:400px;padding:20px 70px 20px 70px;">

					<div style="margin-bottom:10px">
						<input class="easyui-textbox" id="username"
							style="width:100%;height:30px;padding:12px"
							data-options="prompt:'用户名',iconCls:'icon-man',iconWidth:38">
					</div>
					<div style="margin-bottom:20px">
						<input class="easyui-textbox" id="logpass" type="password"
							style="width:100%;height:30px;padding:12px"
							data-options="prompt:'登录密码',iconCls:'icon-lock',iconWidth:38">
					</div>
					<!-- 					<div style="margin-bottom:20px">
						<input class="easyui-textbox" type="text" id="logyzm"
							style="width:50%;height:30px;padding:12px"
							data-options="prompt:'验证码'"> <a href="javascript:;"
							class="showcode" onclick="changeVeryfy()"><img
							style=" margin:0 0 0 3px ; vertical-align:middle; height:26px;"
							src="/index.php?s=/Xjadmin/verifyCode"></a>
					</div> -->
					<div>
						<a href="javascript:;" onclick="dologin()"
							class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
							style="padding:5px 0px;width:100%;"> <span
							style="font-size:14px;">登录</span>
						</a>
					</div>
				</div>

			</test:diy>
		</test:layoutCell>
	</test:layout>

</test:body>
<script>
	function dologin() {
		var username = $("#username").textbox("getValue");
		
		var password = $("#logpass").textbox("getValue");
		var url = new URL("pub.do?method=checkLogin");
		url.addPara("username",username);
		url.addPara("password",password);
		var data = AjaxUtil.ajaxRequest(url);
		window.location.href=_getRootPath()+data.url;
	}
</script>

