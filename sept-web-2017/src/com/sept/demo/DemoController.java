package com.sept.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.sept.exception.AppException;
import com.sept.exception.BusinessException;
import com.sept.framework.web.servlet.def.BusinessController;
import com.sept.framework.web.util.ActionUtil;
import com.sept.support.excel.ExcelColumns;
import com.sept.support.excel.ExcelUtil;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;
import com.sept.support.util.RandomFactory;

public class DemoController extends BusinessController {

	/**
	 * 方法简介.面板测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdMainFramePage(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		return ActionUtil.forward("/sept/demo/demos/mainFramePage.jsp", para);
	}

	/**
	 * 方法简介.面板测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdPanelDemoPage(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		return ActionUtil.forward("/sept/demo/demos/panelPage.jsp", para);
	}

	/**
	 * 方法简介.按钮测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdButtonDemoPage(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		return ActionUtil.forward("/sept/demo/demos/buttonPage.jsp", para);
	}

	/**
	 * 方法简介.Accordion测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdAccordionDemoPage(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		return ActionUtil.forward("/sept/demo/demos/accordionPage.jsp", para);
	}

	/**
	 * 方法简介.Response测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdResponseDemoPage(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		return ActionUtil.forward("/sept/demo/demos/responsePage.jsp", para);
	}

	/**
	 * 方法简介.Download测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdResponsePage(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		return ActionUtil.forward("/sept/demo/demos/responsePage_child.jsp", para);
	}

	/**
	 * 方法简介.Download测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdDownloadDemoPage(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		return ActionUtil.forward("/sept/demo/demos/downloadPage.jsp", para);
	}

	/**
	 * 方法简介.Download
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView downloadExcel(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		DataStore vds = new DataStore();
		for (int i = 0; i < 50000; i++) {
			vds.addRow();
			vds.put(vds.rowCount() - 1, "xm", "张超" + i);
			vds.put(vds.rowCount() - 1, "nl", "20" + i);
			vds.put(vds.rowCount() - 1, "tz", "10" + i);
			vds.put(vds.rowCount() - 1, "sfzh", RandomFactory.newInstance().useHan(false).useCommonHan(false)
					.useUpLetter(false).useLowLetter(false).getRandomStr(18));
		}
		System.out.println("进来了");
		System.out.println(para.getString("hello"));
		ExcelColumns columns = new ExcelColumns();
		columns.addColumn(0, "xm", "姓名", "张超");
		columns.addColumn(1, "nl", "年龄", "18");
		columns.addColumn(2, "tz", "体重", "120");
		columns.addColumn(3, "sfzh", "身份证号", "378888888888888888");

		byte[] bytes = ExcelUtil.getExcelFromDataStore(columns, vds);
		ActionUtil.writeFileToResponse(response, bytes, para.getString("hello"), "xls");
		return new ModelAndView();
	}

	/**
	 * 方法简介.Download测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdExceptionDemoPage(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		return ActionUtil.forward("/sept/demo/demos/exceptionDemoPage.jsp", para);
	}

	/**
	 * 方法简介.Download测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView exceptionDemo(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		String type = para.getString("type");
		if ("buss".equals(type)) {
			throw new BusinessException("测试的BusinessException!!");
		} else {
			throw new AppException("测试的AppException!!");
		}
	}

	/**
	 * 方法简介.Download测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdMenuButtonDemoPage(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		return ActionUtil.forward("/sept/demo/demos/menuButtonDemoPage.jsp", para);
	}
	/**
	 * 方法简介.Download测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdGridDemoPage(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		return ActionUtil.forward("/sept/demo/demos/gridDemoPage.jsp", para);
	}
	/**
	 * 方法简介.Download测试
	 * 
	 * @author 张超 2018年4月14日
	 */
	public ModelAndView fwdFormDemoPage(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		return ActionUtil.forward("/sept/demo/demos/formDemoPage.jsp", para);
	}
}
