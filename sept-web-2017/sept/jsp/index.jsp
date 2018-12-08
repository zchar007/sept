<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/testTag" prefix="test"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<test:body root="false" keywords="asdsa,asdas">
	<test:layout name="1234" hidden="false" onAdd="showit">
		<test:layoutCell name="nnn" region="n" labelValue="你好" split="true"
			maxHeight="100" minHeight="50"></test:layoutCell>
		<test:layoutCell name="sss" region="s" labelValue="<%=name%>"></test:layoutCell>
		<test:layoutCell name="eee" region="e"></test:layoutCell>
		<test:layoutCell name="www" region="w" split="true">
		</test:layoutCell>
		<test:layoutCell name="ccc" region="c" labelValue="欢迎!">
			<test:diy>
<!-- 				<div id="mm" class="easyui-menu" data-options="onClick:menuHandler"
					style="width:120px;">
					<div data-options="name:'new'">New</div>
					<div data-options="name:'save',iconCls:'icon-save'">Save</div>
					<div data-options="name:'print',iconCls:'icon-print'">Print</div>
					<div class="menu-sep"></div>
					<div data-options="name:'exit'">Exit</div>
				</div> -->
			</test:diy>
		</test:layoutCell>
	</test:layout>
</test:body>
<script>
	/* function menuHandler(item) {
		alert('Click Item: ' + item.name);
	}
	$(function() {
		$(document).bind('contextmenu', function(e) {
			e.preventDefault();
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		});
	}); */
</script>
