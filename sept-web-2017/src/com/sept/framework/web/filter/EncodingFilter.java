package com.sept.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sept.exception.AppException;
import com.sept.framework.web.util.ActionUtil;

public class EncodingFilter implements Filter {
	protected String encoding;
	protected FilterConfig filterConfig;

	public EncodingFilter() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) {
		try {
			String currentEncoding = request.getCharacterEncoding();
			if (currentEncoding == null) {
				currentEncoding = this.encoding;
				if (currentEncoding == null) {
					currentEncoding = "UTF-8";
				}
				request.setCharacterEncoding(currentEncoding);
			}
			chain.doFilter(request, response);
		} catch (IOException e) {
		} catch (ServletException e) {
			Object exceptionDataObject = ((HttpServletRequest) request)
					.getSession().getAttribute("exception_context");

			String exceptionData = e.getMessage();
			if (exceptionDataObject != null) {
				exceptionData = exceptionDataObject.toString();
			}
			HttpServletResponse res = (HttpServletResponse) response;
			e.printStackTrace();
			ActionUtil.writeExceptionToResponse(res, new AppException(
					exceptionData));

		}
	}
}
