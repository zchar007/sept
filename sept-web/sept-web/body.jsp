<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/STag" prefix="s"%>
<s:body skin="blue">
	<s:diy>
		<button onclick="doIt('1')">alert</button>
		<button onclick="doIt('2')">confirm</button>
		<button onclick="doIt('3')">prompt</button>
		<button onclick="doIt('4')">loading</button>
		<button onclick="doIt('5')">showMessageBox</button>
				<button onclick="doIt('6')">window</button>
		
		<button onclick="closeIt()">关闭</button>

	</s:diy>
</s:body>
<script>
	var nowMsg = null;
	function doIt(type) {
		if ("1" == type) {
			nowMsg = MsgBox.alert("1111111");
		} else if ("2" == type) {
			nowMsg = MsgBox.confirm("1111111");
		} else if ("3" == type) {
			nowMsg = MsgBox.prompt("1111111");
		} else if ("4" == type) {
			nowMsg = MsgBox.loading("1111111");
		} else if ("5" == type) {
			var options = {
				title : "这是头",
				message : "这是消息",
				enableDragProxy:false,
				buttons : [ "ok", "no", "cancel" ],
				iconCls : "mini-messagebox-question",
				html : "<button>alert</button>",
				callback : function(action) {
				}
			};
			nowMsg = MsgBox.showMessageBox(options);
		}else if("6" == type){
			   mini.open({
	                url: "http://localhost:8080/sept-web/index.jsp",
	                title: "新增员工", width: 800, height: 400,enableDragProxy:false,
	showModal : true
			});
		}
	}
	function closeIt() {
		nowMsg.hide();
	}
</script>