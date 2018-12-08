//默认执行的代码↓
var now_click_object = null;
$(function() {
	// 追加一个顶层菜单项
	$('#_sys_menu_').menu('appendItem', {
		text : '查看请求轨迹',
		// iconCls : 'icon-add',
		onclick : function() {
			var txt = Debug._getPagePosition(now_click_object);			
			var ds = new DataStore();
			ds.initByStr(txt);
			var url = new URL("sept.do?method=fwdTrackPage");
			url.addGrid("_tracktext_", ds);
			var response = new Response("请求轨迹",url);
			response.setDataOptions("resizable", false);
			response.setDataOptions("maximizable", false);
			response.setDataOptions("minimizable", false);
			response.open();
		},
	});

	// 查看点击到的元素的class值
	$('#_sys_menu_').menu('appendItem', {
		text : '查看元素',
		// iconCls : 'icon-add',
		onclick : function() {

			MsgTools.alert("元素", now_click_object.attr("class"));
		},
	});
	
	// 追加一个顶层菜单项
	$('#_sys_menu_').menu('appendItem', {
		// parent : $('#_sys_menu_').menu('findItem', '查看请求轨迹').target,
		text : '刷新',
		// iconCls : 'icon-add',
		onclick : function() {
			Debug._refrsh(now_click_object);
		},
	});

	// 主题
	$('#_sys_menu_').menu('appendItem', {
		// parent : $('#_sys_menu_').menu('findItem', '查看请求轨迹').target,
		text : '主题设置',
		iconCls : 'icon-large-picture',
	});
	var doThems = new DataObject();
	doThems.put("black", "black");
	doThems.put("bootstrap", "bootstrap");
	doThems.put("default", "default");
	doThems.put("gray", "gray");
	doThems.put("material", "material");
	doThems.put("metro", "metro");
	doThems.put("sept", "sept");
	doThems.put("insdep", "insdep");
	doThems.put("cupertino", "ui-cupertino");
	doThems.put("dark-hive", "ui-dark-hive");
	doThems.put("pepper-grinder", "ui-pepper-grinder");
	doThems.put("sunny", "ui-sunny");
	$.each(doThems.keySet(), function(index, value, array) {
		$('#_sys_menu_').menu('appendItem', {
			parent : $('#_sys_menu_').menu('findItem', '主题设置').target,
			text : value,
			iconCls : 'icon-large-picture',
			onclick : function() {
				_changeTheme(doThems.get(value));
			},
		});
	});

	$(document).bind('contextmenu', function(e) {
		e.preventDefault();
		var ee = e || window.event;
		now_click_object = $(ee.target);
		// if (now_click_object[0].tagName == "BUTTON") {
		// now_click_object = null;
		// return false;
		// }
		$('#_sys_menu_').menu('show', {
			left : e.pageX,
			top : e.pageY
		});
	});
});
