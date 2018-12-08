<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/testTag" prefix="test"%>
<%
	String jsonData = (String) request.getAttribute("dsper");
%>
<test:body root="false" keywords="asdsa,asdas">
	<test:diy>
		<table class="easyui-datagrid" title="DataGrid with Toolbar"
			style="width:700px;height:250px"
			data-options="rownumbers:true,singleSelect:true,data:<%=jsonData%>,method:'get',toolbar:toolbar,fit:true">
			<thead>
				<tr>
					<th data-options="field:'xm',width:80">姓名</th>
					<th data-options="field:'nl',width:100">年龄</th>
					<th data-options="field:'sfzhm',width:80,align:'right'">身份证号</th>
				</tr>
			</thead>
		</table>

	</test:diy>
</test:body>
<script>
	var toolbar = [ {
		text : 'Add',
		iconCls : 'icon-add',
		handler : function() {
			alert('add')
		}
	}, {
		text : 'Cut',
		iconCls : 'icon-cut',
		handler : function() {
			alert('cut')
		}
	}, '-', {
		text : 'Save',
		iconCls : 'icon-save',
		handler : function() {
			alert('save')
		}
	} ];
</script>
