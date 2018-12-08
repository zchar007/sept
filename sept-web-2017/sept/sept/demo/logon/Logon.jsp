<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="true" style="sept">
	<stb:diy>
	</stb:diy>
	<stb:layout name="123">
		<stb:layoutCell name="nnn" region="n" maxHeight="70">
			<stb:diy>
				<div class="mini-clearfix "><!--  style="background-color: #2c5887;" -->
					<div class="mini-col-1">
						<img alt="" src="./cogs-icon50.png">
					</div>
					<div class="mini-col-8">
						<h1>Super Market System</h1>
					</div>
				</div>
			</stb:diy>
		</stb:layoutCell>
		<stb:layoutCell name="ccc" region="c">
			<stb:diy>
				<div align="right" style="margin-right:10%;margin-top:10%">
					<div id="w" class="easyui-panel" title="请登录" align="center"
						data-options="modal:true,closed:false,iconCls:'Lockgo',closable:false,minimizable:false"
						style="width:350px;padding:20px 70px 20px 70px;">

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
						<div>
							<a href="javascript:;" onclick="dologin()"
								class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
								style="padding:5px 0px;width:100%;"> <span
								style="font-size:14px;">登录</span>
							</a>
						</div>
					</div>
				</div>
			</stb:diy>
		</stb:layoutCell>
	</stb:layout>

</stb:body>
<script>
	function dologin() {
		var username = $("#username").textbox("getValue");
		var password = $("#logpass").textbox("getValue");
		var url = new URL("pub.do?method=checkLogin");
		url.addPara("username", username);
		url.addPara("password", password);
		var data = AjaxUtil.ajaxRequest(url);
		window.location.href = _getRootPath() + data.url;
	}
</script>

