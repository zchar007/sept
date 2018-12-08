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
			<test:diy>
				<button onclick="doIt()">测试fwd</button>
				<button onclick="openIt()">打开RESPONSE</button>
				<button onclick="closeIt()">关闭RESPONSE</button>
				<button onclick="downloadIt()">下载</button>
				<a onclick="alert('hello')" class="button button-royal ">确定</a>
				<button class="button button-highlight button-square button-large">确定</button>


				<div align="left">
					<button class="button button-primary button-rounded button-small"
						onclick="alert('click')">
						sss</i>
					</button>
					<button class="button button-primary button-rounded button-small">
						sss</i>
					</button>

					<button class="button button-primary button-rounded button-small">
						sss</i>
					</button>
					<a id="btn" class="easyui-linkbutton" data-options="onClick:openIt">确定</a>
				</div>
			</test:diy>

		</test:layoutCell>
		<test:layoutCell name="sss" region="s" labelValue="<%=name%>"></test:layoutCell>
		<test:layoutCell name="eee" region="e"></test:layoutCell>
		<test:layoutCell name="www" region="w" split="true">
		</test:layoutCell>
		<test:layoutCell name="ccc" region="c" labelValue="欢迎!">
			<test:tab name="sss">
				<test:tabPage name="2432" labelValue="首页面"
					url="test.do?method=testRedirect2" collapsible="false"
					closable="true"></test:tabPage>
				<test:tabPage name="321321" labelValue="外部页面" url="index.jsp"></test:tabPage>
				<test:tabPage name="gridtest" labelValue="GRID测试"
					url="test.do?method=fwdGridPage"></test:tabPage>
			</test:tab>

		</test:layoutCell>
	</test:layout>

</test:body>
<script>
	var response = null;
	function showit(r) {
		alert(r);
	}
	function doIt() {
		var url = new URL("test.do?method=testRedirect");
		var x = AjaxUtil.ajaxRequest(url);
		alert(x);
	}
	function openIt() {
		response = new Response("打开测试", new URL("test.do?method=testRedirect"));
		response.open();
	}
	function closeIt() {
		if (response) {
			response.close();
		}
	}
	function downloadIt() {
		var url = new URL("test.do?method=testDown");
		url.addPara("hello", "你好");
		var form = new Form();
		form.submit4Dynamic(url);

	}
</script>

