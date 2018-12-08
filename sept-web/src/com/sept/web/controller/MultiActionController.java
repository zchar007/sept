package com.sept.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.exception.BizException;
import com.sept.project.context.GlobalContext;

/**
 * 
 * @description 所有Controller的父类
 *
 * @author zchar.2018年11月19日下午3:34:37
 */
public class MultiActionController extends AbstractController {

	private Map<String, Method> handlerMethodMap = new HashMap<String, Method>();
	public static final String PAGE_NOT_FOUND_LOG_CATEGORY = "org.springframework.web.servlet.PageNotFound";
	protected static final Log pageNotFoundLogger = LogFactory.getLog(PAGE_NOT_FOUND_LOG_CATEGORY);

	public ModelAndView demo(HttpServletRequest request, HttpServletResponse response, DataObject para)
			throws Exception {
		System.out.println(para);
		DataStore pds = new DataStore();

		for (int i = 0; i < 3; i++) {
			DataObject pdo = new DataObject();
			pdo.put("name", "张三" + i);
			pdo.put("xl", "本科" + i);
			pdo.put("age", i);
			pds.add(pdo);
		}
		DataObject pdo = new DataObject();
		pdo.put("name", "张三");
		pdo.put("xl", "本科");
		pdo.put("age", 10);
		throw new BizException("我这边错了");

		//throw new AppException("我这边错了");
		// return MVUtil.writeDataObject(response,
		// pdo);//{"xl":"本科","name":"张三","age":"10"}
		// return MVUtil.writeDataStore(response,
		// pds);//[{"xl":"本科0","name":"张三0","age":"0"},{"xl":"本科1","name":"张三1","age":"1"},{"xl":"本科2","name":"张三2","age":"2"}]
		// return MVUtil.writeMessage(response, "这是一个测试的信息");//这是一个测试的信息
		// return MVUtil.writeCookies(response, "name","张三");
		// return MVUtil.redirect("index2.jsp");
	}

	public MultiActionController() {
		this.registerHandlerMethods();
	}

	/**
	 * 重写此方法，设置字符编码
	 * 
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(GlobalContext.DEFAULT_CHARACTER_ENCODING);
		response.setCharacterEncoding(GlobalContext.DEFAULT_CHARACTER_ENCODING);
		return super.handleRequest(request, response);
	}

	/**
	 * 入口
	 */
	/**
	 * 
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String methodName = this.getHandlerMethodName(request);
			if (null == methodName || methodName.trim().isEmpty()) {
				return new ModelAndView();
			}
			return invokeNamedMethod(methodName, request, response);
		} catch (NoSuchRequestHandlingMethodException ex) {
			return handleNoSuchRequestHandlingMethod(ex, request, response);
		}
	}

	private ModelAndView handleNoSuchRequestHandlingMethod(NoSuchRequestHandlingMethodException ex,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ex.printStackTrace();
		LogHandler.warn(ex.getMessage());
		pageNotFoundLogger.warn(ex.getMessage());
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

	/**
	 * 
	 * @description 调用method
	 * @param methodName
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zchar.2018年11月19日下午3:26:33
	 */
	private ModelAndView invokeNamedMethod(String methodName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Method method = this.handlerMethodMap.get(methodName);
		if (method == null) {
			throw new NoSuchRequestHandlingMethodException(methodName, getClass());
		}
		DataObject param = this.getParamsFromRequest(request);
		if (null == param) {
			param = new DataObject();
		}
		ModelAndView mv = this.doMethod(method, param, request, response);
		return mv;

	}

	/**
	 * @description 执行方法
	 * @param MethodName
	 * @param para
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zchar.2018年11月19日下午3:25:42
	 */
	protected ModelAndView doMethod(String MethodName, DataObject para, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Method methodz = this.getClass().getMethod(MethodName, HttpServletRequest.class, HttpServletResponse.class,
				DataObject.class);
		return this.doMethod(methodz, para, request, response);
	}

	/**
	 * 
	 * @description 执行方法
	 * @param MethodName
	 * @param para
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zchar.2018年11月19日下午3:25:42
	 */
	protected ModelAndView doMethod(Method MethodName, DataObject para, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = null;
		try {
			Class<?> class1;
			class1 = this.getClass();
			MultiActionController obj;
			obj = (MultiActionController) class1.newInstance();
			Method methodz = class1.getMethod(MethodName.getName(), HttpServletRequest.class, HttpServletResponse.class,
					DataObject.class);
			methodz.setAccessible(true);
			mv = (ModelAndView) methodz.invoke(obj, request, response, para);
			return mv;
		} catch (Exception e) {
			Throwable ex = e.getCause();
			int index = 0;
			while (InvocationTargetException.class.equals(ex)) {
				ex = ex.getCause();
				if (index++ > 10) {
					LogHandler.fatal("执行超过10次", e);
					break;
				}
			}
			if (!(e.getCause() instanceof BizException)) {
				LogHandler.fatal("执行Biz方法异常", e);
			}
			// 不是的话直接抛出
			if (e.getCause() instanceof BizException) {
				throw (BizException) (e.getCause());
			}
			if (e.getCause() instanceof AppException) {
				throw (AppException) (e.getCause());
			}
			throw new AppException(e);

		}
	}

	/**
	 * @description 获取参数信息
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zchar.2018年11月19日下午3:25:21
	 */
	private DataObject getParamsFromRequest(HttpServletRequest request) throws Exception {
		DataObject para = new DataObject();
		Enumeration<String> names = request.getParameterNames();

		while (names.hasMoreElements()) {
			String paraName = names.nextElement();
			String value = request.getParameter(paraName);// gb2312
			para.put(paraName, value);
		}
		if (!para.containsKey(GlobalContext.DEFAULT_SYS_PARAM_KEY)) {
			return new DataObject();
		}
		String jsonSysStr = request.getParameter(GlobalContext.DEFAULT_SYS_PARAM_KEY);
		DataObject paraSys = DataObject.parseFromJSON(jsonSysStr);

		String data_type = paraSys.getString(GlobalContext.DEFAULT_PARAM_TYPE_KEY);
		DataObject param = new DataObject();
		if (GlobalContext.DEFAULT_PARAM_TYPE_JSON.equals(data_type)) {
			param = this.getJsonPara(request);
		} else if (GlobalContext.DEFAULT_PARAM_TYPE_XML.equals(data_type)) {
			param = this.getXmlPara(request);
		}
		param.putAll(paraSys);
		return param;
	}

	/**
	 * 
	 * @description 获取json形式的DataObject
	 * @param request
	 * @return
	 * @throws AppException
	 * @author zchar.2018年11月19日下午3:25:03
	 */
	private DataObject getJsonPara(HttpServletRequest request) throws AppException {
		DataObject para = new DataObject();
		String jsonStr = request.getParameter(GlobalContext.DEFAULT_PARAM_KEY);
		para = DataObject.parseFromJSON(jsonStr);
		return para;
	}

	/**
	 * 
	 * @description 获取xml形式的DataObject
	 * @param request
	 * @return
	 * @throws AppException
	 * @author zchar.2018年11月19日下午3:24:36
	 */
	private DataObject getXmlPara(HttpServletRequest request) throws AppException {
		DataObject para = new DataObject();
		String xmlStr = request.getParameter(GlobalContext.DEFAULT_PARAM_KEY);
		para = DataObject.parseFromXML(xmlStr);
		return para;
	}

	/**
	 * 
	 * @description 获取方法名
	 * @param request
	 * @return
	 * @throws AppException
	 * @throws NoSuchRequestHandlingMethodException
	 * @author zchar.2018年11月19日下午3:24:18
	 */
	private String getHandlerMethodName(HttpServletRequest request)
			throws AppException, NoSuchRequestHandlingMethodException {
		// 默认的method_key是method,如果连method_key和默认的method都没有，说明需要返回cookie信息
		String method_key = GlobalContext.DEFAULT_METHOD_KEY;
		if (null == method_key) {
			method_key = "method";
		}
		String method = null;
		try {
			method = request.getParameter(method_key);// 这里看一下如果找不到会怎样//当前先要求必须是method
		} catch (Exception e) {
			LogHandler.fatal("未查询Url中有对应method方法[" + request + "]");
		}
		// 获取的method是空的才返回cookie，否则默人获取到了方法名
		if (null == method || method.trim().isEmpty()) {
			// throw new AppException("获取前台发送的消息时，方法名为空，请检查数据！");
			return null;
		}
		if (!this.handlerMethodMap.containsKey(method)) {
			throw new NoSuchRequestHandlingMethodException(method, this.getClass());
		}
		return method;
	}

	/**
	 * 
	 * @description 是不是对外提供功能的method
	 * @param method
	 * @return
	 * @author zchar.2018年11月19日下午3:22:34
	 */
	private boolean isHandlerMethod(Method method) {
		if ("org.springframework.web.servlet.ModelAndView".equals(method.getReturnType().getName())// 返回值是ModelAndView
				&& ModelAndView.class.equals(method.getReturnType())// 返回值是ModelAndView
				&& method.getModifiers() == 1) {// 修饰符仅仅是public
			Class<?>[] parameterTypes = method.getParameterTypes();
			// 两个入参,
			if (parameterTypes.length == 2) {
				// 入参为request和response
				if (HttpServletRequest.class.equals(parameterTypes[0])
						&& HttpServletResponse.class.equals(parameterTypes[1])) {
					return !("handleRequest".equals(method.getName()));
				}
				return false;
			}

			if (parameterTypes.length == 3) {
				// 入参为request和response和Map
				if (HttpServletRequest.class.equals(parameterTypes[0])
						&& HttpServletResponse.class.equals(parameterTypes[1])
						&& (Map.class.equals(parameterTypes[2]) || DataObject.class.equals(parameterTypes[2]))) {
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	/**
	 * @description 注册所有的方法
	 * @author zchar.2018年11月19日下午3:22:58
	 */
	private void registerHandlerMethods() {
		this.handlerMethodMap.clear();
		Method[] methods = this.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			// We're looking for methods with given parameters.
			Method method = methods[i];
			if (isHandlerMethod(method)) {
				registerHandlerMethod(method);
			}
		}
	}

	/**
	 * @description 注册方法
	 * @param method
	 * @author zchar.2018年11月19日下午3:21:59
	 */
	private void registerHandlerMethod(Method method) {
		if (logger.isDebugEnabled()) {
			logger.debug("Found action method [" + method + "]");
		}
		this.handlerMethodMap.put(method.getName(), method);
	}
}
