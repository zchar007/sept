package com.sept.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.sept.demo.ao.TestBPO;
import com.sept.framework.web.ao.BPO;
import com.sept.framework.web.servlet.def.BusinessController;
import com.sept.framework.web.servlet.def.SeptemberController;
import com.sept.framework.web.util.ActionUtil;
import com.sept.support.excel.ExcelColumns;
import com.sept.support.excel.ExcelUtil;
import com.sept.support.interfaces.def.CurrentUser;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;
import com.sept.support.thread.GlobalToolkit;
import com.sept.support.util.RandomFactory;
import com.sept.support.util.RandomManager;

public class TestController extends BusinessController {

	public ModelAndView testRedirect(HttpServletRequest request,
			HttpServletResponse response, DataObject para) throws Exception {
		BPO mbpo = this.newBPO(TestBPO.class);
		DataObject result = mbpo.doMethod("testRedirect", para);
		return ActionUtil.forward("/jsp/index.jsp", result);
	}

	public ModelAndView testRedirectLayout(HttpServletRequest request,
			HttpServletResponse response, DataObject para) throws Exception {
		// BPO mbpo = this.newBPO(TestBPO.class);
		// DataObject result = mbpo.doMethod("testRedirect", para);
		return ActionUtil.forward("/layout.jsp", para);
	}

	public ModelAndView testRedirect2(HttpServletRequest request,
			HttpServletResponse response, DataObject para) throws Exception {
		BPO mbpo = this.newBPO(TestBPO.class);
		// DataObject result = mbpo.doMethod("testRedirect", para);
		return ActionUtil.forward("/jsp/index.jsp", para);
	}

	public ModelAndView testDown(HttpServletRequest request,
			HttpServletResponse response, DataObject para) throws Exception {
		DataStore vds = new DataStore();
		for (int i = 0; i < 50000; i++) {
			vds.addRow();
			vds.put(vds.rowCount() - 1, "xm", "张超" + i);
			vds.put(vds.rowCount() - 1, "nl", 20 + i);
			vds.put(vds.rowCount() - 1, "tz", "10" + i);
			vds.put(vds.rowCount() - 1, "sfzh", RandomFactory
					.newInstance().useHan(false).useCommonHan(false)
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
		ActionUtil.writeFileToResponse(response, bytes,
				para.getString("hello"), "xls");
		return new ModelAndView();
	}

	public ModelAndView fwdGridPage(HttpServletRequest request,
			HttpServletResponse response, DataObject para) throws Exception {
		BPO mbpo = this.newBPO(TestBPO.class);
		// DataObject result = mbpo.doMethod("testRedirect", para);
		DataStore dsPer = new DataStore();
		for (int i = 0; i < 100; i++) {
			dsPer.addRow();
			dsPer.put(dsPer.rowCount() - 1, "xm", RandomFactory.newInstance()
					.useCommonHan(true).getRandomStr(3));
			dsPer.put(dsPer.rowCount() - 1, "nl", i + 20);
			dsPer.put(dsPer.rowCount() - 1, "sfzhm", RandomFactory
					.newInstance().useHan(false).useCommonHan(false)
					.useUpLetter(false).useLowLetter(false).getRandomStr(18));
		}
		CurrentUser cu = (CurrentUser) GlobalToolkit.getCurrentUser();
		System.out.println(cu.getUserName());
		para.put("dsper", dsPer.toJSON().replace("\"", "'"));

		return ActionUtil.forward("/jsp/grid.jsp", para);
	}
}
