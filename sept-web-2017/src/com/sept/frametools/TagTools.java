package com.sept.frametools;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataStore;

public class TagTools {
	public static void main(String[] args) throws AppException {
		DataStore ds = new DataStore();
		// onClick node 在用户点击一个节点的时候触发。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onClick");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在用户点击一个节点的时候触发");
		// onDblClick node 在用户双击一个节点的时候触发。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onDblClick");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在用户双击一个节点的时候触发。");
		// onBeforeLoad node, param 在请求加载远程数据之前触发，返回false可以取消加载操作。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onBeforeLoad");
		ds.put(ds.rowCount() - 1, "param", "node, param");
		ds.put(ds.rowCount() - 1, "deciption", "在请求加载远程数据之前触发，返回false可以取消加载操作。");
		// onLoadSuccess node, data 在数据加载成功以后触发。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onLoadSuccess");
		ds.put(ds.rowCount() - 1, "param", "node, data");
		ds.put(ds.rowCount() - 1, "deciption", "在数据加载成功以后触发");
		// onLoadError arguments
		// 在数据加载失败的时候触发，arguments参数和jQuery的$.ajax()函数里面的'error'回调函数的参数相同。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onLoadError");
		ds.put(ds.rowCount() - 1, "param", "arguments");
		ds.put(ds.rowCount() - 1, "deciption",
				"在数据加载失败的时候触发，arguments参数和jQuery的$.ajax()函数里面的'error'回调函数的参数相同");
		// onBeforeExpand node 在节点展开之前触发，返回false可以取消展开操作。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onBeforeExpand");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在节点展开之前触发，返回false可以取消展开操作。");
		// onExpand node 在节点展开的时候触发。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onExpand");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在节点展开的时候触发");
		// onBeforeCollapse node 在节点折叠之前触发，返回false可以取消折叠操作。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onBeforeCollapse");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在节点折叠之前触发，返回false可以取消折叠操作");
		// onCollapse node 在节点折叠的时候触发。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onCollapse");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在节点折叠的时候触发。");
		// onBeforeCheck node, checked
		// 在用户点击勾选复选框之前触发，返回false可以取消选择动作。（该事件自1.3.1版开始可用）
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onBeforeCheck");
		ds.put(ds.rowCount() - 1, "param", "node, checked");
		ds.put(ds.rowCount() - 1, "deciption",
				"在用户点击勾选复选框之前触发，返回false可以取消选择动作。（该事件自1.3.1版开始可用）");
		// onCheck node, checked 在用户点击勾选复选框的时候触发。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onCheck");
		ds.put(ds.rowCount() - 1, "param", "node, checked");
		ds.put(ds.rowCount() - 1, "deciption", "在用户点击勾选复选框的时候触发");
		// onBeforeSelect node 在用户选择一个节点之前触发，返回false可以取消选择动作。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onBeforeSelect");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在用户选择一个节点之前触发，返回false可以取消选择动作");
		// onSelect node 在用户选择节点的时候触发。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onSelect");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在用户选择节点的时候触发");
		// onContextMenu e, node 在右键点击节点的时候触发。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onContextMenu");
		ds.put(ds.rowCount() - 1, "param", "e, node");
		ds.put(ds.rowCount() - 1, "deciption", "在右键点击节点的时候触发");
		// onBeforeDrag node 在开始拖动节点之前触发，返回false可以拒绝拖动。（该事件自1.3.2版开始可用）
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onBeforeDrag");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption",
				"在开始拖动节点之前触发，返回false可以拒绝拖动。（该事件自1.3.2版开始可用）");
		// onStartDrag node 在开始拖动节点的时候触发。（该事件自1.3.2版开始可用）
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onStartDrag");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在开始拖动节点的时候触发。（该事件自1.3.2版开始可用）");
		// onStopDrag node 在停止拖动节点的时候触发。（该事件自1.3.2版开始可用）
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onStopDrag");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在停止拖动节点的时候触发。（该事件自1.3.2版开始可用）");
		// onDragEnter target, source
		// 在拖动一个节点进入到某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onDragEnter");
		ds.put(ds.rowCount() - 1, "param", "target, source");
		ds.put(ds.rowCount() - 1,
				"deciption",
				"在拖动一个节点进入到某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）");
		// onDragOver target, source
		// 在拖动一个节点经过某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onDragOver");
		ds.put(ds.rowCount() - 1, "param", "target, source");
		ds.put(ds.rowCount() - 1,
				"deciption",
				"在拖动一个节点经过某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）");
		// onDragLeave target, source
		// 在拖动一个节点离开某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onDragLeave");
		ds.put(ds.rowCount() - 1, "param", "target, source");
		ds.put(ds.rowCount() - 1,
				"deciption",
				"在拖动一个节点离开某个目标节点并释放的时候触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。（该事件自1.3.2版开始可用）");
		// onBeforeDrop target, source, point
		// 在拖动一个节点之前触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。point：表示哪一种拖动操作，可用值有：'append','top'或
		// 'bottom'。（该事件自1.3.3版开始可用）
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onBeforeDrop");
		ds.put(ds.rowCount() - 1, "param", "target, source, point");
		ds.put(ds.rowCount() - 1,
				"deciption",
				"在拖动一个节点之前触发，返回false可以拒绝拖动。target：释放的目标节点元素。source：开始拖动的源节点。point：表示哪一种拖动操作，可用值有：'append','top'或 'bottom'。（该事件自1.3.3版开始可用）");
		// onDrop target, source, point
		// 当节点位置被拖动时触发。target：DOM对象，需要被拖动动的目标节点。source：源节点。point：表示哪一种拖动操作，可用值有：'append','top'
		// 或 'bottom'。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onDrop");
		ds.put(ds.rowCount() - 1, "param", "target, source, point");
		ds.put(ds.rowCount() - 1,
				"deciption",
				"当节点位置被拖动时触发。target：DOM对象，需要被拖动动的目标节点。source：源节点。point：表示哪一种拖动操作，可用值有：'append','top' 或 'bottom'。");
		// onBeforeEdit node 在编辑节点之前触发。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onBeforeEdit");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在编辑节点之前触发。");
		// onAfterEdit node 在编辑节点之后触发。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onAfterEdit");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在编辑节点之后触发。");
		// onCancelEdit node 在取消编辑操作的时候触发。
		ds.addRow();
		ds.put(ds.rowCount() - 1, "name", "onCancelEdit");
		ds.put(ds.rowCount() - 1, "param", "node");
		ds.put(ds.rowCount() - 1, "deciption", "在取消编辑操作的时候触发。");

		System.out.println(createFunctionPara(ds));
	}

	public static String createFunctionPara(DataStore ds) throws AppException {
		StringBuffer sqlBF_init = new StringBuffer();
		StringBuffer sqlBF_set = new StringBuffer();
		StringBuffer sqlBF_change = new StringBuffer();
		StringBuffer sqlBF_tag = new StringBuffer();
		for (int i = 0; i < ds.rowCount(); i++) {
			String name = ds.getString(i, "name");
			String param = ds.getString(i, "param");
			String deciption = ds.getString(i, "deciption");
			sqlBF_init.append("//" + name + "  " + param + "  " + deciption
					+ "\n");
			sqlBF_init.append(" String " + name + " = null;\n");

			sqlBF_set.append("//" + name + "  " + param + "  " + deciption
					+ "\n");
			sqlBF_set.append("this.setFunctionPara(\"" + name + "\");\n");

			sqlBF_change.append("//" + name + "  " + param + "  " + deciption
					+ "\n");
			sqlBF_change.append("if (null != this.get"
					+ name.substring(0, 1).toUpperCase() + name.substring(1)
					+ "() && !this.get" + name.substring(0, 1).toUpperCase()
					+ name.substring(1) + "().trim().isEmpty()) {\n");
			sqlBF_change.append("	this.set"
					+ name.substring(0, 1).toUpperCase() + name.substring(1)
					+ "(\"function(" + param + "){\" + this.get"
					+ name.substring(0, 1).toUpperCase() + name.substring(1)
					+ "() + \"(" + param + ")}\");\n");
			sqlBF_change.append("}\n");


			sqlBF_tag.append("<attribute>\n");
			sqlBF_tag.append("<description> " + name + "  " + param + "  "
					+ deciption + "</description>\n");
			sqlBF_tag.append("<name>"+name+"</name>\n");
			sqlBF_tag.append("<required>false</required>\n");
			sqlBF_tag.append("<rtexprvalue>true</rtexprvalue>\n");
			sqlBF_tag.append("</attribute>\n");

		}
		return sqlBF_init.toString() + sqlBF_set.toString()
				+ sqlBF_change.toString()+sqlBF_tag.toString();

	}

	public static String createTagClass(String tagName, String paramPath) {
		String initPara;
		String setPara;

		StringBuffer sqlBF = new StringBuffer();
		sqlBF.setLength(0);
		sqlBF.append("package com.sept.framework.taglib.sept." + tagName + ";");
		sqlBF.append("");
		sqlBF.append("import javax.servlet.jsp.JspException;");
		sqlBF.append("");
		sqlBF.append("import com.sept.framework.taglib.sept.AbstractTag;");
		sqlBF.append("");
		sqlBF.append("public class " + tagName + "Tag extends AbstractTag{");
		sqlBF.append("  private static final long serialVersionUID = 1L;");
		sqlBF.append("");
		sqlBF.append("  @Override");
		sqlBF.append("  public void init() throws JspException {");
		sqlBF.append("    this.impl = new " + tagName + "+Impl();");
		sqlBF.append("");
		sqlBF.append("  }");
		sqlBF.append("");
		sqlBF.append("  @Override");
		sqlBF.append("  public String checkBeforeSetParams() throws JspException {");
		sqlBF.append("    // TODO 张超 Auto-generated method stub");
		sqlBF.append("    return null;");
		sqlBF.append("  }");
		sqlBF.append("");
		sqlBF.append("  @Override");
		sqlBF.append("  public String checkAfterSetParams() throws JspException {");
		sqlBF.append("    // TODO 张超 Auto-generated method stub");
		sqlBF.append("    return null;");
		sqlBF.append("  }");
		sqlBF.append("}");

		return sqlBF.toString();
	}
}
