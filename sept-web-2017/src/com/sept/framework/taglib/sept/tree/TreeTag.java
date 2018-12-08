package com.sept.framework.taglib.sept.tree;

import javax.servlet.jsp.JspException;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCheck;
import com.sept.framework.taglib.sept.AbstractTag;

public class TreeTag extends AbstractTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// url string 检索远程数据的URL地址。 null 以classz代替，请求时附带此信息
	private String classz = null;
	// method string 检索数据的HTTP方法。（POST / GET） post
	private String method = "post";
	// animate boolean 定义节点在展开或折叠的时候是否显示动画效果。 false
	private boolean animate = false;
	// checkbox boolean 定义是否在每一个借点之前都显示复选框。 false
	private boolean checkbox = false;
	// cascadeCheck boolean 定义是否层叠选中状态。 true
	private boolean cascadeCheck = true;
	// onlyLeafCheck boolean 定义是否只在末级节点之前显示复选框。 false
	private boolean onlyLeafCheck = false;
	// lines boolean 定义是否显示树控件上的虚线。 false
	private boolean lines = false;
	// dnd boolean 定义是否启用拖拽功能。 false
	private boolean dnd = false;
	// data array 参考api,暂时不建议使用 null
	private String dataArray = null;
	// queryParams object 在请求远程数据的时候增加查询参数并发送到服务器。（该属性自1.4版开始可用） {} 暂不启用
	// formatter function(node) 定义如何渲染节点的文本。 暂不启用
	// filter function(q,node) 定义如何过滤本地展示的数据，返回true将允许节点进行展示。（该属性自1.4.2版开始可用）
	// json loader 暂不启用
	// loader function(param,success,error)
	// 定义如何从远程服务器加载数据。返回false可以忽略本操作。该函数具备以下参数： 暂不启用
	// loadFilter function(data,parent) 返回过滤过的数据进行展示。返回数据是标准树格式。该函数具备以下参数： 暂不启用

	// 方法
	// onClick node 在用户点击一个节点的时候触发。
	private String onClick = null;
	// onDblClick node 在用户双击一个节点的时候触发。
	private String onDblClick = null;
	// onBeforeLoad node, param 在请求加载远程数据之前触发，返回false可以取消加载操作。
	private String onBeforeLoad = null;
	// onLoadSuccess node, data 在数据加载成功以后触发。
	private String onLoadSuccess = null;
	// onLoadError arguments
	// 在数据加载失败的时候触发，arguments参数和jQuery的$.ajax()函数里面的'error'回调函数的参数相同。
	private String onLoadError = null;
	// onBeforeExpand node 在节点展开之前触发，返回false可以取消展开操作。
	private String onBeforeExpand = null;
	// onExpand node 在节点展开的时候触发。
	private String onExpand = null;
	// onBeforeCollapse node 在节点折叠之前触发，返回false可以取消折叠操作。
	private String onBeforeCollapse = null;
	// onCollapse node 在节点折叠的时候触发。
	private String onCollapse = null;
	// onBeforeCheck node, checked
	// 在用户点击勾选复选框之前触发，返回false可以取消选择动作。（该事件自1.3.1版开始可用）
	private String onBeforeCheck = null;
	// onCheck node, checked 在用户点击勾选复选框的时候触发。
	private String onCheck = null;
	// onBeforeSelect node 在用户选择一个节点之前触发，返回false可以取消选择动作。
	private String onBeforeSelect = null;
	// onSelect node 在用户选择节点的时候触发。
	private String onSelect = null;
	// onContextMenu e, node 在右键点击节点的时候触发。
	private String onContextMenu = null;
	// onBeforeDrag node 在开始拖动节点之前触发，返回false可以拒绝拖动。（该事件自1.3.2版开始可用）
	private String onBeforeDrag = null;
	// onStartDrag node 在开始拖动节点的时候触发。（该事件自1.3.2版开始可用）
	private String onStartDrag = null;
	// onStopDrag node 在停止拖动节点的时候触发。（该事件自1.3.2版开始可用）
	private String onStopDrag = null;
	// onDragEnter target, source
	// 在拖动一个节点进入到某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
	private String onDragEnter = null;
	// onDragOver target, source
	// 在拖动一个节点经过某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
	private String onDragOver = null;
	// onDragLeave target, source
	// 在拖动一个节点离开某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
	private String onDragLeave = null;
	// onBeforeDrop target, source, point
	// 在拖动一个节点之前触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。point：表示哪一种拖动操作，可用值有：'append','top'
	// 或 'bottom'。（该事件自1.3.3版开始可用）
	private String onBeforeDrop = null;
	// onDrop target, source, point
	// 当节点位置被拖动时触发。target：DOM对象，需要被拖动动的目标节点。source：源节点。point：表示哪一种拖动操作，可用值有：'append','top'
	// 或 'bottom'。
	private String onDrop = null;
	// onBeforeEdit node 在编辑节点之前触发。
	private String onBeforeEdit = null;
	// onAfterEdit node 在编辑节点之后触发。
	private String onAfterEdit = null;
	// onCancelEdit node 在取消编辑操作的时候触发。
	private String onCancelEdit = null;

	@Override
	public void init() throws JspException {
		this.impl = new TreeImpl();
		// onClick node 在用户点击一个节点的时候触发。
		this.setFunctionPara("onClick");
		// onDblClick node 在用户双击一个节点的时候触发。
		this.setFunctionPara("onDblClick");
		// onBeforeLoad node, param 在请求加载远程数据之前触发，返回false可以取消加载操作。
		this.setFunctionPara("onBeforeLoad");
		// onLoadSuccess node, data 在数据加载成功以后触发。
		this.setFunctionPara("onLoadSuccess");
		// onLoadError arguments
		// 在数据加载失败的时候触发，arguments参数和jQuery的$.ajax()函数里面的'error'回调函数的参数相同。
		this.setFunctionPara("onLoadError");
		// onBeforeExpand node 在节点展开之前触发，返回false可以取消展开操作。
		this.setFunctionPara("onBeforeExpand");
		// onExpand node 在节点展开的时候触发。
		this.setFunctionPara("onExpand");
		// onBeforeCollapse node 在节点折叠之前触发，返回false可以取消折叠操作。
		this.setFunctionPara("onBeforeCollapse");
		// onCollapse node 在节点折叠的时候触发。
		this.setFunctionPara("onCollapse");
		// onBeforeCheck node, checked
		// 在用户点击勾选复选框之前触发，返回false可以取消选择动作。（该事件自1.3.1版开始可用）
		this.setFunctionPara("onBeforeCheck");
		// onCheck node, checked 在用户点击勾选复选框的时候触发。
		this.setFunctionPara("onCheck");
		// onBeforeSelect node 在用户选择一个节点之前触发，返回false可以取消选择动作。
		this.setFunctionPara("onBeforeSelect");
		// onSelect node 在用户选择节点的时候触发。
		this.setFunctionPara("onSelect");
		// onContextMenu e, node 在右键点击节点的时候触发。
		this.setFunctionPara("onContextMenu");
		// onBeforeDrag node 在开始拖动节点之前触发，返回false可以拒绝拖动。（该事件自1.3.2版开始可用）
		this.setFunctionPara("onBeforeDrag");
		// onStartDrag node 在开始拖动节点的时候触发。（该事件自1.3.2版开始可用）
		this.setFunctionPara("onStartDrag");
		// onStopDrag node 在停止拖动节点的时候触发。（该事件自1.3.2版开始可用）
		this.setFunctionPara("onStopDrag");
		// onDragEnter target, source
		// 在拖动一个节点进入到某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
		this.setFunctionPara("onDragEnter");
		// onDragOver target, source
		// 在拖动一个节点经过某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
		this.setFunctionPara("onDragOver");
		// onDragLeave target, source
		// 在拖动一个节点离开某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
		this.setFunctionPara("onDragLeave");
		// onBeforeDrop target, source, point
		// 在拖动一个节点之前触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。point：表示哪一种拖动操作，可用值有：'append','top'
		// 或 'bottom'。（该事件自1.3.3版开始可用）
		this.setFunctionPara("onBeforeDrop");
		// onDrop target, source, point
		// 当节点位置被拖动时触发。target：DOM对象，需要被拖动动的目标节点。source：源节点。point：表示哪一种拖动操作，可用值有：'append','top'
		// 或 'bottom'。
		this.setFunctionPara("onDrop");
		// onBeforeEdit node 在编辑节点之前触发。
		this.setFunctionPara("onBeforeEdit");
		// onAfterEdit node 在编辑节点之后触发。
		this.setFunctionPara("onAfterEdit");
		// onCancelEdit node 在取消编辑操作的时候触发。
		this.setFunctionPara("onCancelEdit");

		this.setDiyPara("dataArray");
		this.setDiyPara("classz");
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		if (null == this.parent) {
			throw new JspException("Tree标签必须含有父标签！");
		}
		String dataArray = this.getDataArray();
		if (null != dataArray && !dataArray.trim().isEmpty()) {
			this.addParam("data", dataArray, "s");
		}
		// onClick node 在用户点击一个节点的时候触发
		if (null != this.getOnClick() && !this.getOnClick().trim().isEmpty()) {
			this.setOnClick("function(node){" + this.getOnClick() + "(node)}");
		}
		// onDblClick node 在用户双击一个节点的时候触发。
		if (null != this.getOnDblClick()
				&& !this.getOnDblClick().trim().isEmpty()) {
			this.setOnDblClick("function(node){" + this.getOnDblClick()
					+ "(node)}");
		}
		// onBeforeLoad node, param 在请求加载远程数据之前触发，返回false可以取消加载操作。
		if (null != this.getOnBeforeLoad()
				&& !this.getOnBeforeLoad().trim().isEmpty()) {
			this.setOnBeforeLoad("function(node, param){"
					+ this.getOnBeforeLoad() + "(node, param)}");
		}
		// onLoadSuccess node, data 在数据加载成功以后触发
		if (null != this.getOnLoadSuccess()
				&& !this.getOnLoadSuccess().trim().isEmpty()) {
			this.setOnLoadSuccess("function(node, data){"
					+ this.getOnLoadSuccess() + "(node, data)}");
		}
		// onLoadError arguments
		// 在数据加载失败的时候触发，arguments参数和jQuery的$.ajax()函数里面的'error'回调函数的参数相同
		if (null != this.getOnLoadError()
				&& !this.getOnLoadError().trim().isEmpty()) {
			this.setOnLoadError("function(arguments){" + this.getOnLoadError()
					+ "(arguments)}");
		}
		// onBeforeExpand node 在节点展开之前触发，返回false可以取消展开操作。
		if (null != this.getOnBeforeExpand()
				&& !this.getOnBeforeExpand().trim().isEmpty()) {
			this.setOnBeforeExpand("function(node){" + this.getOnBeforeExpand()
					+ "(node)}");
		}
		// onExpand node 在节点展开的时候触发
		if (null != this.getOnExpand() && !this.getOnExpand().trim().isEmpty()) {
			this.setOnExpand("function(node){" + this.getOnExpand() + "(node)}");
		}
		// onBeforeCollapse node 在节点折叠之前触发，返回false可以取消折叠操作
		if (null != this.getOnBeforeCollapse()
				&& !this.getOnBeforeCollapse().trim().isEmpty()) {
			this.setOnBeforeCollapse("function(node){"
					+ this.getOnBeforeCollapse() + "(node)}");
		}
		// onCollapse node 在节点折叠的时候触发。
		if (null != this.getOnCollapse()
				&& !this.getOnCollapse().trim().isEmpty()) {
			this.setOnCollapse("function(node){" + this.getOnCollapse()
					+ "(node)}");
		}
		// onBeforeCheck node, checked
		// 在用户点击勾选复选框之前触发，返回false可以取消选择动作。（该事件自1.3.1版开始可用）
		if (null != this.getOnBeforeCheck()
				&& !this.getOnBeforeCheck().trim().isEmpty()) {
			this.setOnBeforeCheck("function(node, checked){"
					+ this.getOnBeforeCheck() + "(node, checked)}");
		}
		// onCheck node, checked 在用户点击勾选复选框的时候触发
		if (null != this.getOnCheck() && !this.getOnCheck().trim().isEmpty()) {
			this.setOnCheck("function(node, checked){" + this.getOnCheck()
					+ "(node, checked)}");
		}
		// onBeforeSelect node 在用户选择一个节点之前触发，返回false可以取消选择动作
		if (null != this.getOnBeforeSelect()
				&& !this.getOnBeforeSelect().trim().isEmpty()) {
			this.setOnBeforeSelect("function(node){" + this.getOnBeforeSelect()
					+ "(node)}");
		}
		// onSelect node 在用户选择节点的时候触发
		if (null != this.getOnSelect() && !this.getOnSelect().trim().isEmpty()) {
			this.setOnSelect("function(node){" + this.getOnSelect() + "(node)}");
		}
		// onContextMenu e, node 在右键点击节点的时候触发
		if (null != this.getOnContextMenu()
				&& !this.getOnContextMenu().trim().isEmpty()) {
			this.setOnContextMenu("function(e, node){"
					+ this.getOnContextMenu() + "(e, node)}");
		}
		// onBeforeDrag node 在开始拖动节点之前触发，返回false可以拒绝拖动。（该事件自1.3.2版开始可用）
		if (null != this.getOnBeforeDrag()
				&& !this.getOnBeforeDrag().trim().isEmpty()) {
			this.setOnBeforeDrag("function(node){" + this.getOnBeforeDrag()
					+ "(node)}");
		}
		// onStartDrag node 在开始拖动节点的时候触发。（该事件自1.3.2版开始可用）
		if (null != this.getOnStartDrag()
				&& !this.getOnStartDrag().trim().isEmpty()) {
			this.setOnStartDrag("function(node){" + this.getOnStartDrag()
					+ "(node)}");
		}
		// onStopDrag node 在停止拖动节点的时候触发。（该事件自1.3.2版开始可用）
		if (null != this.getOnStopDrag()
				&& !this.getOnStopDrag().trim().isEmpty()) {
			this.setOnStopDrag("function(node){" + this.getOnStopDrag()
					+ "(node)}");
		}
		// onDragEnter target, source
		// 在拖动一个节点进入到某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
		if (null != this.getOnDragEnter()
				&& !this.getOnDragEnter().trim().isEmpty()) {
			this.setOnDragEnter("function(target, source){"
					+ this.getOnDragEnter() + "(target, source)}");
		}
		// onDragOver target, source
		// 在拖动一个节点经过某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
		if (null != this.getOnDragOver()
				&& !this.getOnDragOver().trim().isEmpty()) {
			this.setOnDragOver("function(target, source){"
					+ this.getOnDragOver() + "(target, source)}");
		}
		// onDragLeave target, source
		// 在拖动一个节点离开某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
		if (null != this.getOnDragLeave()
				&& !this.getOnDragLeave().trim().isEmpty()) {
			this.setOnDragLeave("function(target, source){"
					+ this.getOnDragLeave() + "(target, source)}");
		}
		// onBeforeDrop target, source, point
		// 在拖动一个节点之前触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。point：表示哪一种拖动操作，可用值有：'append','top'或
		// 'bottom'。（该事件自1.3.3版开始可用）
		if (null != this.getOnBeforeDrop()
				&& !this.getOnBeforeDrop().trim().isEmpty()) {
			this.setOnBeforeDrop("function(target, source, point){"
					+ this.getOnBeforeDrop() + "(target, source, point)}");
		}
		// onDrop target, source, point
		// 当节点位置被拖动时触发。target：DOM对象，需要被拖动动的目标节点。source：源节点。point：表示哪一种拖动操作，可用值有：'append','top'
		// 或 'bottom'。
		if (null != this.getOnDrop() && !this.getOnDrop().trim().isEmpty()) {
			this.setOnDrop("function(target, source, point){"
					+ this.getOnDrop() + "(target, source, point)}");
		}
		// onBeforeEdit node 在编辑节点之前触发。
		if (null != this.getOnBeforeEdit()
				&& !this.getOnBeforeEdit().trim().isEmpty()) {
			this.setOnBeforeEdit("function(node){" + this.getOnBeforeEdit()
					+ "(node)}");
		}
		// onAfterEdit node 在编辑节点之后触发。
		if (null != this.getOnAfterEdit()
				&& !this.getOnAfterEdit().trim().isEmpty()) {
			this.setOnAfterEdit("function(node){" + this.getOnAfterEdit()
					+ "(node)}");
		}
		// onCancelEdit node 在取消编辑操作的时候触发。
		if (null != this.getOnCancelEdit()
				&& !this.getOnCancelEdit().trim().isEmpty()) {
			this.setOnCancelEdit("function(node){" + this.getOnCancelEdit()
					+ "(node)}");
		}

		return null;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		return null;
	}

	public String getClassz() {
		return classz;
	}

	public void setClassz(String classz) {
		this.classz = classz;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public boolean getAnimate() {
		return animate;
	}

	public void setAnimate(boolean animate) {
		this.animate = animate;
	}

	public boolean getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public boolean getCascadeCheck() {
		return cascadeCheck;
	}

	public void setCascadeCheck(boolean cascadeCheck) {
		this.cascadeCheck = cascadeCheck;
	}

	public boolean getOnlyLeafCheck() {
		return onlyLeafCheck;
	}

	public void setOnlyLeafCheck(boolean onlyLeafCheck) {
		this.onlyLeafCheck = onlyLeafCheck;
	}

	public boolean getLines() {
		return lines;
	}

	public void setLines(boolean lines) {
		this.lines = lines;
	}

	public boolean getDnd() {
		return dnd;
	}

	public void setDnd(boolean dnd) {
		this.dnd = dnd;
	}

	public String getDataArray() {
		return dataArray;
	}

	public void setDataArray(String dataArray) {
		this.dataArray = dataArray;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getOnDblClick() {
		return onDblClick;
	}

	public void setOnDblClick(String onDblClick) {
		this.onDblClick = onDblClick;
	}

	public String getOnBeforeLoad() {
		return onBeforeLoad;
	}

	public void setOnBeforeLoad(String onBeforeLoad) {
		this.onBeforeLoad = onBeforeLoad;
	}

	public String getOnLoadSuccess() {
		return onLoadSuccess;
	}

	public void setOnLoadSuccess(String onLoadSuccess) {
		this.onLoadSuccess = onLoadSuccess;
	}

	public String getOnLoadError() {
		return onLoadError;
	}

	public void setOnLoadError(String onLoadError) {
		this.onLoadError = onLoadError;
	}

	public String getOnBeforeExpand() {
		return onBeforeExpand;
	}

	public void setOnBeforeExpand(String onBeforeExpand) {
		this.onBeforeExpand = onBeforeExpand;
	}

	public String getOnExpand() {
		return onExpand;
	}

	public void setOnExpand(String onExpand) {
		this.onExpand = onExpand;
	}

	public String getOnBeforeCollapse() {
		return onBeforeCollapse;
	}

	public void setOnBeforeCollapse(String onBeforeCollapse) {
		this.onBeforeCollapse = onBeforeCollapse;
	}

	public String getOnCollapse() {
		return onCollapse;
	}

	public void setOnCollapse(String onCollapse) {
		this.onCollapse = onCollapse;
	}

	public String getOnBeforeCheck() {
		return onBeforeCheck;
	}

	public void setOnBeforeCheck(String onBeforeCheck) {
		this.onBeforeCheck = onBeforeCheck;
	}

	public String getOnCheck() {
		return onCheck;
	}

	public void setOnCheck(String onCheck) {
		this.onCheck = onCheck;
	}

	public String getOnBeforeSelect() {
		return onBeforeSelect;
	}

	public void setOnBeforeSelect(String onBeforeSelect) {
		this.onBeforeSelect = onBeforeSelect;
	}

	public String getOnSelect() {
		return onSelect;
	}

	public void setOnSelect(String onSelect) {
		this.onSelect = onSelect;
	}

	public String getOnContextMenu() {
		return onContextMenu;
	}

	public void setOnContextMenu(String onContextMenu) {
		this.onContextMenu = onContextMenu;
	}

	public String getOnBeforeDrag() {
		return onBeforeDrag;
	}

	public void setOnBeforeDrag(String onBeforeDrag) {
		this.onBeforeDrag = onBeforeDrag;
	}

	public String getOnStartDrag() {
		return onStartDrag;
	}

	public void setOnStartDrag(String onStartDrag) {
		this.onStartDrag = onStartDrag;
	}

	public String getOnStopDrag() {
		return onStopDrag;
	}

	public void setOnStopDrag(String onStopDrag) {
		this.onStopDrag = onStopDrag;
	}

	public String getOnDragEnter() {
		return onDragEnter;
	}

	public void setOnDragEnter(String onDragEnter) {
		this.onDragEnter = onDragEnter;
	}

	public String getOnDragOver() {
		return onDragOver;
	}

	public void setOnDragOver(String onDragOver) {
		this.onDragOver = onDragOver;
	}

	public String getOnDragLeave() {
		return onDragLeave;
	}

	public void setOnDragLeave(String onDragLeave) {
		this.onDragLeave = onDragLeave;
	}

	public String getOnBeforeDrop() {
		return onBeforeDrop;
	}

	public void setOnBeforeDrop(String onBeforeDrop) {
		this.onBeforeDrop = onBeforeDrop;
	}

	public String getOnDrop() {
		return onDrop;
	}

	public void setOnDrop(String onDrop) {
		this.onDrop = onDrop;
	}

	public String getOnBeforeEdit() {
		return onBeforeEdit;
	}

	public void setOnBeforeEdit(String onBeforeEdit) {
		this.onBeforeEdit = onBeforeEdit;
	}

	public String getOnAfterEdit() {
		return onAfterEdit;
	}

	public void setOnAfterEdit(String onAfterEdit) {
		this.onAfterEdit = onAfterEdit;
	}

	public String getOnCancelEdit() {
		return onCancelEdit;
	}

	public void setOnCancelEdit(String onCancelEdit) {
		this.onCancelEdit = onCancelEdit;
	}

}
