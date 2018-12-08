<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/septTag" prefix="st"%>
<st:html>
<st:layout name="main">
	<st:layoutCell region="n" name="shang" collapsible="false" split="true">

	</st:layoutCell>
	<st:layoutCell region="s" name="xia" title="下"></st:layoutCell>
	<st:layoutCell region="w" name="zuo" title="左"></st:layoutCell>
	<st:layoutCell region="e" name="you" title="右"></st:layoutCell>
	<st:layoutCell region="c" name="zhong" title="nihao"
		url="test.do?method=fwdIncloudPage"></st:layoutCell>
	<st:diy>
		<div id="mm" class="easyui-menu" data-options="onClick:menuHandler"
			style="width:120px;">
			<div data-options="name:'new'">New</div>
			<div data-options="name:'save',iconCls:'icon-save'">Save</div>
			<div data-options="name:'print',iconCls:'icon-print'">Print</div>
			<div class="menu-sep"></div>
			<div data-options="name:'exit'">Exit</div>
		</div>
	</st:diy>
</st:layout>
<script>
	function menuHandler(item) {
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
	});
</script>

</st:html>