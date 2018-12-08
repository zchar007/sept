<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	String name = "你好";
	String labelValue = "";
%>
<stb:body root="false">
	<stb:layout name="123" hidden="false">
		<stb:layoutCell name="ccc" region="c" labelValue="面板测试">
			<stb:panel name="testpanel12" labelValue="1112" border="true"
				left_x="200" top_y="100" fit="false" closable="true">
				<stb:diy>
					<form id="ff" method="post">
						<label for="name">Textbox:</label> <input class="easyui-textbox" />
						<label for="email">Combo:</label><input class="easyui-combobox"
							data-options="
		valueField: 'label',
		textField: 'value',
		data: [{
			label: 'java',
			value: 'Java'
		},{
			label: 'perl',
			value: 'Perl'
		},{
			label: 'ruby',
			value: 'Ruby'
		}]" />

						<label for="numberbox">numberbox:</label><input type="text"
							class="easyui-numberbox" value="100"
							data-options="min:0,precision:3"></input>
<label for="numberbox">easyui-datetimebox:</label>
<input class="easyui-datetimebox" name="birthday"     
        data-options="required:true,showSeconds:false" value="3/4/2010 2:3" style="width:150px">  


					</form>
				</stb:diy>
			</stb:panel>

		</stb:layoutCell>
	</stb:layout>
</stb:body>
<script>
	$('#cc').combo({
		required : true,
		multiple : true
	});
	function doIt(aaa) {
		alert(aaa);
	}
</script>

