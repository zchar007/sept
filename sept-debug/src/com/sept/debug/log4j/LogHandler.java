package com.sept.debug.log4j;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sept.util.stack.StackTraceUtil;

public class LogHandler {
	private static Logger logger = LogManager.getLogger(LogHandler.class);

	/**
	 * info
	 * 
	 * @param message
	 */
	public static final void info(Object message) {
		logger.info(StackTraceUtil.getPrintInfo(StackTraceUtil.getStackTrace(-2)) + " - " + message);
	}

	public static final void info(Object message, Throwable err) {
		logger.info(StackTraceUtil.getPrintInfo(StackTraceUtil.getStackTrace(-2)) + " - " + message, err);
	}

	/**
	 * warn
	 * 
	 * @param message
	 */
	public static final void warn(Object message) {
		logger.warn(StackTraceUtil.getPrintInfo(StackTraceUtil.getStackTrace(-2)) + " - " + message);
	}

	public static final void warn(Object message, Throwable err) {
		logger.warn(StackTraceUtil.getPrintInfo(StackTraceUtil.getStackTrace(-2)) + " - " + message, err);
	}

	/**
	 * debug
	 * 
	 * @param message
	 */
	public static final void debug(Object message) {
		logger.debug(StackTraceUtil.getPrintInfo(StackTraceUtil.getStackTrace(-2)) + " - " + message);
	}

	public static final void debug(Object message, Throwable err) {
		logger.debug(StackTraceUtil.getPrintInfo(StackTraceUtil.getStackTrace(-2)) + " - " + message, err);
	}

	/**
	 * error
	 * 
	 * @param message
	 */
	public static final void error(Object message) {
		logger.error(StackTraceUtil.getPrintInfo(StackTraceUtil.getStackTrace(-2)) + " - " + message);
	}

	public static final void error(Object message, Throwable err) {
		logger.error(StackTraceUtil.getPrintInfo(StackTraceUtil.getStackTrace(-2)) + " - " + message, err);
	}

	/**
	 * fatal
	 * 
	 * @param message
	 */
	public static final void fatal(Object message) {
		logger.fatal(StackTraceUtil.getPrintInfo(StackTraceUtil.getStackTrace(-2)) + " - " + message);
	}

	public static final void fatal(Object message, Throwable err) {
		logger.fatal(StackTraceUtil.getPrintInfo(StackTraceUtil.getStackTrace(-2)) + " - " + message, err);
	}

	public static final Logger getLogger() {
		return logger;
	}

}
