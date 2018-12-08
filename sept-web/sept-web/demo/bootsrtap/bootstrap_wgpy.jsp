<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/STag" prefix="s"%>
<s:body skin="blue">
	<s:diy>
		<!-- 按钮触发模态框 -->
		<button class="btn btn-primary btn-lg" data-toggle="modal"
			data-target="#myModal">开始演示模态框</button>
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
											<h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
					
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
					</div>
					<div class="modal-body">按下 ESC 按钮退出。</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary">提交更改</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</s:diy>
</s:body>
<script>
$(document).on("show.bs.modal", ".modal", function(){
    $(this).draggable({
        handle: ".modal-header",  // 只能点击头部拖动
        cursor: 'crosshair',//cursor: "move"
        delay: 0
        //containment: "#sept-main"
    });
    $(this).css("overflow", "hidden"); // 防止出现滚动条，出现的话，你会把滚动条一起拖着走的
});
	
</script>