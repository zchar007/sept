package com.sept.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.sept.framework.taglib.sept.tree.model.TreeBuilder;
import com.sept.framework.web.servlet.def.BusinessController;
import com.sept.framework.web.servlet.def.SeptemberController;
import com.sept.framework.web.util.ActionUtil;
import com.sept.support.excel.ExcelColumns;
import com.sept.support.excel.ExcelUtil;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;
import com.sept.support.thread.GlobalToolkit;
import com.sept.support.util.RandomFactory;

public class SeptCommonController extends SeptemberController {
	/**
	 * 方法简介.打开错误response页面
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdAppExceptionPage(HttpServletRequest request,
			HttpServletResponse response, DataObject para) throws Exception {
		return ActionUtil.forward("/system/exception/appExceptionPage.jsp",
				para);
	}

	/**
	 * 方法简介.获取Tree数据
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView getTree(HttpServletRequest request,
			HttpServletResponse response, DataObject para) throws Exception {
		String classz = para.getString("classz");
		@SuppressWarnings("unchecked")
		Class<? extends TreeBuilder> classtree = (Class<? extends TreeBuilder>) Class
				.forName(classz);
		TreeBuilder treeBuilder = classtree.newInstance();
		DataStore data = treeBuilder.bulider(para);
		return ActionUtil.writeMessageToResponse(response, data.toJSON());
	}
}
