<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.sept.support.model.data.DataStore"%>
<%@ page import="com.sept.support.model.data.DataObject"%>
<%@ taglib uri="/SeptTagBeta" prefix="stb"%>
<%
	DataStore dsTrace = (DataStore) request.getAttribute("_tracktext_");
	int index = (int) request.getAttribute("jspindex");
%>
<stb:body root="false">
	<stb:layout name="track_" hidden="false">
		<stb:layoutCell region="w" name="www" maxWidth="15" border="0"
			split="false"></stb:layoutCell>
		<stb:layoutCell region="e" name="eee" maxWidth="15"></stb:layoutCell>

		<stb:layoutCell name="ccc" region="c">
			<%
				String cost, methodName, className, type, nodeId;
							if (index >= 0) {
								DataObject jspNode = dsTrace.get(index);
								cost = jspNode.getString("cost");
								methodName = jspNode.getString("methodName");
								className = jspNode.getString("className");
								type = jspNode.getString("type");
								nodeId = jspNode.getString("className");
			%>
			<stb:panel name="<%=nodeId%>" labelValue="当前页面" fit="false"
				height="125px">
				<stb:diy>
					<input class="easyui-textbox" data-options="editable:false,label:'类'"
						value="<%=className%>" style="width:950px">
					<br />
					<input class="easyui-textbox" data-options="editable:false,label:'方法'"
						value="<%=methodName%>" style="width:950px">
					<br />
					<input class="easyui-textbox" data-options="editable:false,label:'用时（毫秒）'"
						value="<%=cost%>" style="width:950px">
					<br />
				</stb:diy>
			</stb:panel>


			<%
				}
			%>
			<stb:panel name="trace___path__" labelValue="请求路径">

				<%
					for (int i = 0; i < dsTrace.rowCount(); i++) {
										cost = dsTrace.getString(i, "cost");
										methodName = dsTrace.getString(i, "methodName");
										className = dsTrace.getString(i, "className");
										type = dsTrace.getString(i, "type");
										nodeId = dsTrace.getString(i, "nodeId");
				%>
				<stb:panel name="<%=nodeId%>" labelValue="<%=type%>" fit="false"
					height="125px">
					<stb:diy>
						<input class="easyui-textbox" data-options="editable:false,label:'类'"
							value="<%=className%>" style="width:950px">
						<br />
						<input class="easyui-textbox" data-options="editable:false,label:'方法'"
							value="<%=methodName%>" style="width:950px">
						<br />
						<input class="easyui-textbox" data-options="editable:false,label:'用时（毫秒）'"
							value="<%=cost%>" style="width:950px">
						<br />
					</stb:diy>
				</stb:panel>
				<%
					}
				%>
			</stb:panel>

			<stb:diy>
				<font size="3" style="color:black;"><%=dsTrace.toJSON()%></font>
			</stb:diy>
		</stb:layoutCell>
	</stb:layout>
</stb:body>
