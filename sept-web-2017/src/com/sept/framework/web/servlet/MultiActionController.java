package com.sept.framework.web.servlet;

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
import org.springframework.web.util.NestedServletException;

import com.sept.exception.AppException;
import com.sept.exception.BusinessException;
import com.sept.exception.SeptException;
import com.sept.framework.web.requestTrace.CallStackTracer;
import com.sept.framework.web.util.ActionUtil;
import com.sept.global.GlobalNames;
import com.sept.global.InteractiveDict;
import com.sept.support.database.TransactionManager;
import com.sept.support.model.data.DataObject;

public abstract class MultiActionController extends AbstractController {

	private Map<String, Method> handlerMethodMap = new HashMap<String, Method>();
	public static final String PAGE_NOT_FOUND_LOG_CATEGORY = "org.springframework.web.servlet.PageNotFound";
	protected static final Log pageNotFoundLogger = LogFactory
			.getLog(PAGE_NOT_FOUND_LOG_CATEGORY);

	public MultiActionController() {
		// 注册所有public方法
		this.registerHandlerMethods();
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		return super.handleRequest(request, response);
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String methodName = this.getHandlerMethodName(request);
			if (null == methodName || methodName.trim().isEmpty()) {
				ActionUtil.writeCookiesToResponse(response,
						GlobalNames.getDeploy("interactivedict"));
				return new ModelAndView();
			}
			return invokeNamedMethod(methodName, request, response);
		} catch (NoSuchRequestHandlingMethodException ex) {
			return handleNoSuchRequestHandlingMethod(ex, request, response);
		}

	}

	/**
	 * 输出错误，不知道效果什么样,记日志相关的
	 * 
	 * @param ex
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ModelAndView handleNoSuchRequestHandlingMethod(
			NoSuchRequestHandlingMethodException ex,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ex.printStackTrace();
		pageNotFoundLogger.warn(ex.getMessage());
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

	/**
	 * 捕获异常
	 * 
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	protected ModelAndView handleException(HttpServletRequest request,
			HttpServletResponse response, Throwable ex) throws Exception {
		/*
		 * Method handler = getExceptionHandler(ex); if (handler != null) {
		 * return invokeExceptionHandler(handler, request, response, ex); }
		 */
		// If we get here, there was no custom handler
		if (ex instanceof Exception) {
			throw (Exception) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		// Should never happen!
		throw new NestedServletException("Unknown Throwable type encountered",
				ex);
	}

	/**
	 * 执行方法
	 * 
	 * @param MethodName
	 * @param para
	 * @throws Exception
	 * @throws Exception
	 */
	protected ModelAndView doMethod(String MethodName, DataObject para,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = null;

		Class<?> class1;
		class1 = this.getClass();
		MultiActionController obj;
		obj = (MultiActionController) class1.newInstance();
		Method methodz = class1.getMethod(MethodName, HttpServletRequest.class,
				HttpServletResponse.class, DataObject.class);
		methodz.setAccessible(true);
		mv = (ModelAndView) methodz.invoke(obj, request, response, para);
		return mv;
	}

	/**
	 * 执行方法
	 * 
	 * @param MethodName
	 * @param para
	 * @throws Exception
	 * @throws Exception
	 */
	protected ModelAndView doMethod(Method MethodName, DataObject para,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = null;

		try {
			Class<?> class1;
			class1 = this.getClass();
			MultiActionController obj;
			obj = (MultiActionController) class1.newInstance();
			Method methodz = class1.getMethod(MethodName.getName(),
					HttpServletRequest.class, HttpServletResponse.class,
					DataObject.class);
			methodz.setAccessible(true);
			mv = (ModelAndView) methodz.invoke(obj, request, response, para);
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				if (e.getCause() instanceof BusinessException) {
					throw (BusinessException) e.getCause();
				} else if (e.getCause() instanceof AppException) {
					throw (AppException) e.getCause();
				} else {
					return this
							.handleException(request, response, e.getCause());
				}
			} else {
				return this.handleException(request, response, e);
			}

		}
		return mv;
	}

	@Override
	public String toString() {
		return "MultiActionController [handlerMethodMap=" + handlerMethodMap
				+ "]";
	}

	/**
	 * 获取方法名
	 * 
	 * @param request
	 * @return
	 * @throws AppException
	 * @throws NoSuchRequestHandlingMethodException
	 */
	private String getHandlerMethodName(HttpServletRequest request)
			throws AppException, NoSuchRequestHandlingMethodException {
		// 默认的method_key是method,如果连method_key和默认的method都没有，说明需要返回cookie信息
		String method_key = GlobalNames.getDeploy("servlet", "method_key");
		if (null == method_key) {
			method_key = "method";
		}
		String method = null;
		try {
			method = request.getParameter(method_key);// 这里看一下如果找不到会怎样//当前先要求必须是method
		} catch (Exception e) {
		}
		// 获取的method是空的才返回cookie，否则默人获取到了方法名
		if (null == method || method.trim().isEmpty()) {
			// throw new AppException("获取前台发送的消息时，方法名为空，请检查数据！");
			return null;
		}
		if (!this.handlerMethodMap.containsKey(method)) {
			throw new NoSuchRequestHandlingMethodException(method,
					this.getClass());
		}
		return method;
	}

	/**
	 * 执行方法的逻辑
	 * 
	 * 
	 * @param methodName
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ModelAndView invokeNamedMethod(String methodName,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean isFen = false;// 分离模式，不用当前DataObject模式
		ModelAndView mv = null;
		if (isFen) {

		} else {
			try {
				// 执行
				Method method = this.handlerMethodMap.get(methodName);
				if (method == null) {
					throw new NoSuchRequestHandlingMethodException(methodName,
							getClass());
				}
				DataObject param = this.getParamsFromRequest(request);
				if (null == param || param.size() == 0) {
					// TODO 先不做这
					// ActionUtil.writeCookiesToResponse(response,
					// GlobalNames.getDeploy("interactivedict"));
					// return new ModelAndView();
				}

				// 数据监测
				// 数据检测放到获取参数之后
				if (!this.doBeforResponse(request, response, param, method)) {
					DataObject pdo = new DataObject();
					pdo.put(InteractiveDict.INTERACTIVE_ERROR_FLAG,
							InteractiveDict.INTERACTIVE_ERROR__FWD__MAIN);
					pdo.put(InteractiveDict.INTERACTIVE_ERROR_TEXT,
							"pub.do?method=logon");
					return ActionUtil.writeDataObjectToResponse(response, pdo);
				}

				// 调试模式下记录请求轨迹
				CallStackTracer.startNode(this.getClass().getName(),
						method.toString());
				if ("true"
						.equals(GlobalNames.getDeploy("sql", "SPRING-DBABLE"))) {
					TransactionManager.getTransaction().begin();
				}
				mv = this.doMethod(method, param, request, response);
				if (null == mv || mv.isEmpty()) {
					mv = null;
				}
				if (mv != null) {
					CallStackTracer.startNode(mv.getViewName(),
							mv.getViewName());
					CallStackTracer.endNode();
				}
				// 调试模式下记录请求轨迹
				CallStackTracer.endNode();
				// System.out.println( CallStackTracer.getCallStackJson());
				request.setAttribute("__requestTraceStr__",
						CallStackTracer.getCallStackJson());
				request.setAttribute(
						"__requestUrl__",
						"http://"
								+ request.getServerName() // 服务器地址
								+ ":"
								+ request.getServerPort()// 端口号
								+ request.getRequestURI() + "?"
								+ request.getQueryString());
				String jsonSys = param.getString(
						InteractiveDict.INTERACTIVE_SYSTEM_JSON, "");
				String jsonData = param.getString(
						InteractiveDict.INTERACTIVE_DATA, "");
				request.setAttribute(InteractiveDict.INTERACTIVE_SYSTEM_JSON,
						jsonSys);// 配置信息
				request.setAttribute(InteractiveDict.INTERACTIVE_DATA, jsonData);// 入参数据
				CallStackTracer.removeThreadLocalNode();
				if ("true"
						.equals(GlobalNames.getDeploy("sql", "SPRING-DBABLE"))) {
					TransactionManager.getTransaction().commit();
				}

				try {
				} catch (Exception e) {
					e.printStackTrace();
				}
				if ("true"
						.equals(GlobalNames.getDeploy("sql", "SPRING-DBABLE"))) {
					TransactionManager.getTransaction().commitWithoutStart();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					if ("true".equals(GlobalNames.getDeploy("sql",
							"SPRING-DBABLE"))) {
						TransactionManager.getTransaction()
								.rollbackWithoutStart();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return handleException(request, response, ex);
			} finally {
				// 移除，但是当前不会使用
				this.doAfterResponse(request, response);
				CallStackTracer.removeThreadLocalNode();
			}

		}
		return mv;
	}

	/**
	 * 这里是一些检测等，可以靠实现此方法来实现不同类型的Controller 此时的para中有数据，比如可以将用户等信息放入
	 * 
	 * @author zchar
	 * @date 创建时间：2017-7-28 上午11:11:38
	 * @param request
	 * @param response
	 * @param para
	 * @param method
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean doBeforResponse(HttpServletRequest request,
			HttpServletResponse response, DataObject para, Method method)
			throws Exception;

	/**
	 * 
	 * @author zchar
	 * @date 创建时间：2017-7-28 上午11:11:31
	 * @param request
	 * @param response
	 * @throws SeptException
	 */
	protected abstract void doAfterResponse(HttpServletRequest request,
			HttpServletResponse response) throws SeptException;

	/**
	 * 注册所有的方法
	 * 
	 * @author zchar
	 * @date 创建时间：2017-7-28 上午11:11:50
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
	 * 是不是对外提供功能的method
	 * 
	 * 
	 * @author zchar
	 * @date 创建时间：2017-7-28 上午11:12:00
	 * @param method
	 * @return
	 */
	private boolean isHandlerMethod(Method method) {
		if ("org.springframework.web.servlet.ModelAndView".equals(method
				.getReturnType().getName())// 返回值是ModelAndView
				&& ModelAndView.class.equals(method.getReturnType())// 返回值是ModelAndView
				&& method.getModifiers() == 1) {// 修饰符仅仅是public
			Class<?>[] parameterTypes = method.getParameterTypes();
			// System.out.println(method.getName() + "["
			// + parameterTypes[parameterTypes.length - 1] + "][");

			// 两个入参
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
						&& (Map.class.equals(parameterTypes[2]) || DataObject.class
								.equals(parameterTypes[2]))) {
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	/**
	 * 根据method注册
	 * 
	 * 
	 * @author zchar
	 * @date 创建时间：2017-7-28 上午11:12:11
	 * @param method
	 */
	private void registerHandlerMethod(Method method) {
		if (logger.isDebugEnabled()) {
			logger.debug("Found action method [" + method + "]");
		}
		this.handlerMethodMap.put(method.getName(), method);
	}

	/**
	 * 获取request中的数据
	 * 
	 * @author zchar
	 * @date 创建时间：2017-7-28 上午11:12:21
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private DataObject getParamsFromRequest(HttpServletRequest request)
			throws Exception {
		DataObject para = new DataObject();
		Enumeration<String> names = request.getParameterNames();

		while (names.hasMoreElements()) {
			String paraName = names.nextElement();
			String value = request.getParameter(paraName);// gb2312
			para.put(paraName, value);
		}
		if (!para.containsKey(InteractiveDict.INTERACTIVE_SYSTEM_JSON)) {
			return new DataObject();
		}
		String jsonSysStr = request
				.getParameter(InteractiveDict.INTERACTIVE_SYSTEM_JSON);
		DataObject paraSys = DataObject.parseFromJSON(jsonSysStr);

		String data_type = paraSys.getString(
				InteractiveDict.INTERACTIVE_DATA_TYPE,
				InteractiveDict.INTERACTIVE_DATA_TYPE_JSON);
		DataObject param = new DataObject();
		if (InteractiveDict.INTERACTIVE_DATA_TYPE_JSON.equals(data_type)) {
			param = this.getJsonPara(request);
		} else if (InteractiveDict.INTERACTIVE_DATA_TYPE_XML.equals(data_type)) {
			param = this.getXmlPara(request);
		}
		param.putAll(paraSys);
		return param;
	}

	/**
	 * 从xml中获取数据
	 * 
	 * 
	 * @author zchar
	 * @date 创建时间：2017-7-28 上午11:13:02
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private DataObject getXmlPara(HttpServletRequest request) throws Exception {
		DataObject para = new DataObject();
		try {
			String xmlStr = request
					.getParameter(InteractiveDict.INTERACTIVE_DATA);
			para = DataObject.parseFromXML(xmlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return para;
	}

	/**
	 * 获取数据，不在前台报错，在后台报错
	 * 
	 * @author zchar
	 * @date 创建时间：2017-7-28 上午11:13:12
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private DataObject getJsonPara(HttpServletRequest request) throws Exception {
		DataObject para = new DataObject();
		try {
			String jsonStr = request
					.getParameter(InteractiveDict.INTERACTIVE_DATA);
			para = DataObject.parseFromJSON(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return para;
	}
}
