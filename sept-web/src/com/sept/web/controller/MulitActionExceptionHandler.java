package com.sept.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.sept.datastructure.DataObject;
import com.sept.exception.BizException;

/**
 * 
 * @description:这里是对异常的处理
 *
 * @author zchar.2018年11月19日下午10:57:26
 */
public class MulitActionExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception ex) {
		DataObject pdo = new DataObject();
		if (BizException.class.isInstance(ex)) {
			pdo.put("exception", ex.getMessage());
			//return new ModelAndView("sept-web/error/appexception", pdo);
		} else {
			Throwable throwable = ex;
			StackTraceElement[] st = throwable.getStackTrace();
			String error = "<br/>" + throwable.toString();
			String errortext = "";
			for (int i = 0; i < st.length; i++) {
				errortext += "<br/>" + st[i].toString();
			}
			error += errortext;
			pdo.put("exception", error);
		}
		return new ModelAndView("sept-web/error/appexception", pdo);
	}

}
