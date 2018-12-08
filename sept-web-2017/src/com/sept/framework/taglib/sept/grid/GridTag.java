package com.sept.framework.taglib.sept.grid;

import javax.servlet.jsp.JspException;
import com.sept.framework.taglib.sept.AbstractTag;

public class GridTag extends AbstractTag {
	private static final long serialVersionUID = 1L;
	// DataGrid列配置对象，详见列属性说明中更多的细节。 undefined
	private String columns = null;
	// 同列属性，但是这些列将会被冻结在左侧。 undefined
	private String frozenColumns = null;
	// 真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。 false
	private boolean fitColumns;
	// 调整列的位置，可用的值有：'left','right','both'。在使用'right'的时候用户可以通过拖动右侧边缘的列标题调整列，等等。（该属性自1.3.2版开始可用）
	// right
	private String resizeHandle = null;
	// 定义设置行的高度，根据该行的内容。设置为false可以提高负载性能。 true
	private boolean autoRowHeight;
	// 顶部工具栏的DataGrid面板。可能的值：1) 一个数组，每个工具属性都和linkbutton一样。2) 选择器指定的工具栏。
	// 在<div>标签上定义工具栏： $('#dg').datagrid({toolbar: '#tb'});<div id="tb"><a href="#"
	// class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"/a><a
	// href="#" class="easyui-linkbutton"
	// data-options="iconCls:'icon-help',plain:true"/a></div>通过数组定义工具栏：$('#dg').datagrid({toolbar:
	// [{iconCls: 'icon-edit',handler: function(){alert('编辑按钮')}},'-',{iconCls:
	// 'icon-help',handler: function(){alert('帮助按钮')}}]}); null
	private String toolbar = null;
	// 是否显示斑马线效果。 false
	private boolean striped;
	// 该方法类型请求远程数据。 post
	private String method = null;
	// 如果为true，则在同一行中显示数据。设置为true可以提高加载性能。 true
	private boolean nowrap;
	// 指明哪一个字段是标识字段。 null
	private String idField = null;
	// 一个URL从远程站点请求数据。 null
	private String url = null;
	// 数据加载（该属性自1.3.2版开始可用） 代码示例：$('#dg').datagrid({data: [{f1:'value11',
	// f2:'value12'},{f1:'value21', f2:'value22'}]}); null
	private String data = null;
	// 在从远程站点加载数据的时候显示提示消息。 Processing, please wait …
	private String loadMsg = null;
	// 如果为true，则在DataGrid控件底部显示分页工具栏。 false
	private boolean pagination;
	// 如果为true，则显示一个行号列。 false
	private boolean rownumbers;
	// 如果为true，则只允许选择一行。 false
	private boolean singleSelect;
	// 在启用多行选择的时候允许使用Ctrl键+鼠标点击的方式进行多选操作。（该属性自1.3.6版开始可用） false
	private boolean ctrlSelect;
	// 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。（该属性自1.3版开始可用）
	// true
	private boolean checkOnSelect;
	// 如果为true，单击复选框将永远选择行。如果为false，选择行将不选中复选框。（该属性自1.3版开始可用）true
	private boolean selectOnCheck;
	// 定义分页工具栏的位置。可用的值有：'top','bottom','both'。（该属性自1.3版开始可用） bottom
	private String pagePosition = null;
	// 在设置分页属性的时候初始化页码。 1 pageSize number 在设置分页属性的时候初始化页面大小。 10
	private String pageNumber = null;
	// 在设置分页属性的时候 初始化页面大小选择列表。 [10,20,30,40,50]
	private String pageList = null;
	// 在请求远程数据的时候发送额外的参数。 代码示例：$('#dg').datagrid({queryParams: {name:
	// 'easyui',subject: 'datagrid'}}); {}
	private String queryParams = null;
	// 定义哪些列可以进行排序。 null
	private String sortName = null;
	// 定义列的排序顺序，只能是'asc'或'desc'。 asc
	private String sortOrder = null;
	// 定义是否允许多列排序。（该属性自1.3.4版开始可用） false
	private boolean multiSort;
	// 定义从服务器对数据进行排序。 true
	private boolean remoteSort;
	// 定义是否显示行头。 true
	private boolean showHeader;
	// 定义是否显示行脚。 false
	private boolean showFooter;
	// 滚动条的宽度(当滚动条是垂直的时候)或高度(当滚动条是水平的时候)。 18
	private String scrollbarSize = null;
	// 返回样式如'background:red'。带2个参数的函数：index：该行索引从0开始row：与此相对应的记录行。
	// 代码示例：$('#dg').datagrid({rowStyler: function(index,row){if
	// (row.listprice>80){return
	// 'background-color:#6293BB;color:#fff;';}}});译者注（1.3.4新增方式）：$('#dg').datagrid({rowStyler:
	// function(index,row){if (row.listprice>80){return 'rowStyle'; //
	// rowStyle是一个已经定义了的ClassName(类名)}}});
	private String rowStyler = null;
	// 定义如何从远程服务器加载数据。返回false可以放弃本次请求动作。该函数接受以下参数：param：参数对象传递给远程服务器。success(data)：当检索数据成功的时候会调用该回调函数。error()：当检索数据失败的时候会调用该回调函数。
	// json loader
	private String loader = null;
	// 返回过滤数据显示。该函数带一个参数'data'用来指向源数据（即：获取的数据源，比如Json对象）。您可以改变源数据的标准数据格式。这个函数必须返回包含'total'和'rows'属性的标准数据对象。
	// 代码示例：// 从Web
	// Service（asp.net、java、php等）输出的JSON对象中移除'd'对象$('#dg').datagrid({loadFilter:
	// function(data){if (data.d){return data.d;} else {return data;}}});
	private String loadFilter = null;
	// 定义在编辑行的时候使用的编辑器。 预定义编辑器
	private String editors = null;
	// 定义DataGrid的视图。 默认视图
	private String view = null;

	@Override
	public void init() throws JspException {
		this.impl = new GridImpl();
		this.setFunctionPara("rowStyler");
		this.setFunctionPara("loader");
		this.setFunctionPara("loadFilter");
	}

	@Override
	public String checkBeforeSetParams() throws JspException {
		if (null == this.getParent()) {
			this.jspException("Grid标签必须含有父标签！");
		}
		if (null != this.getRowStyler() && !this.getRowStyler().trim().isEmpty()) {
			this.setRowStyler("function(){" + this.getRowStyler() + "(index,row)}");
		}
		if (null != this.getLoader() && !this.getLoader().trim().isEmpty()) {
			this.setLoader("function(){" + this.getLoader() + "(data)}");
		}
		if (null != this.getLoadFilter() && !this.getLoadFilter().trim().isEmpty()) {
			this.setLoadFilter("function(){" + this.getLoadFilter() + "(data)}");
		}
		return null;
	}

	@Override
	public String checkAfterSetParams() throws JspException {
		return null;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getColumns() {
		return this.columns;
	}

	public void setFrozenColumns(String frozenColumns) {
		this.frozenColumns = frozenColumns;
	}

	public String getFrozenColumns() {
		return this.frozenColumns;
	}

	public void setFitColumns(boolean fitColumns) {
		this.fitColumns = fitColumns;
	}

	public boolean getFitColumns() {
		return this.fitColumns;
	}

	public void setResizeHandle(String resizeHandle) {
		this.resizeHandle = resizeHandle;
	}

	public String getResizeHandle() {
		return this.resizeHandle;
	}

	public void setAutoRowHeight(boolean autoRowHeight) {
		this.autoRowHeight = autoRowHeight;
	}

	public boolean getAutoRowHeight() {
		return this.autoRowHeight;
	}

	public void setToolbar(String toolbar) {
		this.toolbar = toolbar;
	}

	public String getToolbar() {
		return this.toolbar;
	}

	public void setStriped(boolean striped) {
		this.striped = striped;
	}

	public boolean getStriped() {
		return this.striped;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return this.method;
	}

	public void setNowrap(boolean nowrap) {
		this.nowrap = nowrap;
	}

	public boolean getNowrap() {
		return this.nowrap;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getIdField() {
		return this.idField;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return this.data;
	}

	public void setLoadMsg(String loadMsg) {
		this.loadMsg = loadMsg;
	}

	public String getLoadMsg() {
		return this.loadMsg;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public boolean getPagination() {
		return this.pagination;
	}

	public void setRownumbers(boolean rownumbers) {
		this.rownumbers = rownumbers;
	}

	public boolean getRownumbers() {
		return this.rownumbers;
	}

	public void setSingleSelect(boolean singleSelect) {
		this.singleSelect = singleSelect;
	}

	public boolean getSingleSelect() {
		return this.singleSelect;
	}

	public void setCtrlSelect(boolean ctrlSelect) {
		this.ctrlSelect = ctrlSelect;
	}

	public boolean getCtrlSelect() {
		return this.ctrlSelect;
	}

	public void setCheckOnSelect(boolean checkOnSelect) {
		this.checkOnSelect = checkOnSelect;
	}

	public boolean getCheckOnSelect() {
		return this.checkOnSelect;
	}

	public void setSelectOnCheck(boolean selectOnCheck) {
		this.selectOnCheck = selectOnCheck;
	}

	public boolean getSelectOnCheck() {
		return this.selectOnCheck;
	}

	public void setPagePosition(String pagePosition) {
		this.pagePosition = pagePosition;
	}

	public String getPagePosition() {
		return this.pagePosition;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getPageNumber() {
		return this.pageNumber;
	}

	public void setPageList(String pageList) {
		this.pageList = pageList;
	}

	public String getPageList() {
		return this.pageList;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	public String getQueryParams() {
		return this.queryParams;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortOrder() {
		return this.sortOrder;
	}

	public void setMultiSort(boolean multiSort) {
		this.multiSort = multiSort;
	}

	public boolean getMultiSort() {
		return this.multiSort;
	}

	public void setRemoteSort(boolean remoteSort) {
		this.remoteSort = remoteSort;
	}

	public boolean getRemoteSort() {
		return this.remoteSort;
	}

	public void setShowHeader(boolean showHeader) {
		this.showHeader = showHeader;
	}

	public boolean getShowHeader() {
		return this.showHeader;
	}

	public void setShowFooter(boolean showFooter) {
		this.showFooter = showFooter;
	}

	public boolean getShowFooter() {
		return this.showFooter;
	}

	public void setScrollbarSize(String scrollbarSize) {
		this.scrollbarSize = scrollbarSize;
	}

	public String getScrollbarSize() {
		return this.scrollbarSize;
	}

	public void setRowStyler(String rowStyler) {
		this.rowStyler = rowStyler;
	}

	public String getRowStyler() {
		return this.rowStyler;
	}

	public void setLoader(String loader) {
		this.loader = loader;
	}

	public String getLoader() {
		return this.loader;
	}

	public void setLoadFilter(String loadFilter) {
		this.loadFilter = loadFilter;
	}

	public String getLoadFilter() {
		return this.loadFilter;
	}

	public void setEditors(String editors) {
		this.editors = editors;
	}

	public String getEditors() {
		return this.editors;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getView() {
		return this.view;
	}
}
