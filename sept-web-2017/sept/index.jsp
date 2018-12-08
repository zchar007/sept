<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

	<div class="sept-div-container">
		<a class="sept-button" onclick="getUrl()" disabled="true">确定</a> <a
			onclick="alert('hello')" class="sept-button sept-button-royal ">确定</a>
		<button
			class="sept-button sept-button-3d sept-button-box sept-button-jumbo">
			<i class="fa fa-thumbs-up"></i>
		</button>
		<div class="sept-response-container">
			<iframe class="sept-iframe-container"
				src="http://localhost:8080/sept/test.do?method=testRedirect"></iframe>
		</div>
	</div>



	<script type="text/javascript" src="./sept/widgets/utils/sept.js"></script>
	<script type="text/javascript" src="./sept/boot.js"></script>
	<script type="text/javascript">
		function getUrl() {
			var url = new URL("test.do?method=testRedirect");
			alert(url.getUrlString());
		}
		$(function() {
			var tmp;
			$('.sept-response-head').each(function() {
				tmp = $(this).css('z-index');
				if (tmp > zIndex)
					zIndex = tmp;
			})
			make_draggable($('.sept-response-head'));

		});

		var zIndex = 0;
		function make_draggable(elements) {
			elements.draggable({
				opacity : 0.8,
				containment : 'parent',
				start : function(e, ui) {
					ui.helper.css('z-index', ++zIndex);
				},
				stop : function(e, ui) {
					//                        $.get('ajax.php', {
					//                            x: ui.position.left,
					//                            y: ui.position.top,
					//                            z: zIndex,
					//                            id: parseInt(ui.helper.find('span.data').html())
					//                        });
				}
			});
			$('.sept-response-container').draggable({
				opacity : 0.8,
				containment : 'parent',
				start : function(e, ui) {
					ui.helper.css('z-index', ++zIndex);
				},
				stop : function(e, ui) {
					//                        $.get('ajax.php', {
					//                            x: ui.position.left,
					//                            y: ui.position.top,
					//                            z: zIndex,
					//                            id: parseInt(ui.helper.find('span.data').html())
					//                        });
				}
			});
		}
		/* 		sept.ForEach(1, 1, function(i) {
			//alert(encodeURIComponent("你好%") + i);
		})
		var vdo = new DataObject();
		vdo.put("hello", "你好");
		var vds = new DataStore();
		vds.addRow();
		vds.put(0, "xm", "张三");
		vds.put(0, "nl", 30);

		vds.addRow();
		vds.put(1, "xm", "张二");
		vds.put(1, "nl", 20);
		vdo.put("ds", vds);

		var vdo2 = new DataObject();
		vdo2.put("hello", "你好");

		vdo.put("vdo2", vdo2);
		var url = new URL("test.do?method=fwdtestpage");
		url.addForm(vdo);
		alert(url.getUrlString());
		alert(url.getSystemJson());
		alert(url.getDataJson()); */
	</script>

