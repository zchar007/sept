<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="false">
	<stb:layout name="123" hidden="false">
		<stb:layoutCell name="ccc" region="c" labelValue="面板测试">
		<stb:button name="111" labelValue="加载" onClick="loadGrdiData"/>
			<%--  			<stb:panel name="testpanel12" labelValue="1112" border="true" left_x="200" top_y="100" fit = "false" closable="true">
				<stb:tools name="testtt2">
					<stb:tbutton name="1213" iconCls="icon-ok" onClick="doIt('12123123')" />
				</stb:tools>
			</stb:panel>  --%>
				<stb:diy>

					<table id="dg" class="easyui-datagrid" style="width: 400px; height: 250px"
						data-options="fitColumns:true,singleSelect:true,fit:true">
						<thead>
							<tr>
								<th data-options="field:'code',width:100">编码</th>
								<th data-options="field:'name',width:100">名称</th>
								<th data-options="field:'price',width:100,align:'right'">价格</th>
							</tr>
						</thead>
					</table>
				</stb:diy>
		</stb:layoutCell>
	</stb:layout>
</stb:body>
<script>
	function loadGrdiData() {
		MsgTools.alert("nihao","这是消息");
		$('#dg').datagrid('loadData',[{
			code: '01',
			name: 'name01',
			price: 'price01'
		}]);


	}
</script>

