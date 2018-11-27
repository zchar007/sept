package com.sept.debug.log4j;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogHandler {
	private static final Logger logger = LogManager.getLogger(LogHandler.class);

	/**
	 * info
	 * 
	 * @param message
	 */
	public static final void info(Object message) {
		logger.info(message);
	}

	public static final void info(Object message, Throwable err) {
		logger.info(message, err);
	}

	public static final void info(Object message, String classz) {
		logger.info("[" + classz + "]" + message);
	}

	public static final void info(Object message, Throwable err, String classz) {
		logger.info("[" + classz + "]" + message, err);
	}

	/**
	 * warn
	 * 
	 * @param message
	 */
	public static final void warn(Object message) {
		logger.warn(message);
	}

	public static final void warn(Object message, Throwable err) {
		logger.warn(message, err);
	}

	public static final void warn(Object message, String classz) {
		logger.warn("[" + classz + "]" + message);
	}

	public static final void warn(Object message, Throwable err, String classz) {
		logger.warn("[" + classz + "]" + message, err);
	}

	/**
	 * debug
	 * 
	 * @param message
	 */
	public static final void debug(Object message) {
		logger.debug(message);
	}

	public static final void debug(Object message, Throwable err) {
		logger.debug(message, err);
	}

	public static final void debug(Object message, String classz) {
		logger.debug("[" + classz + "]" + message);
	}

	public static final void debug(Object message, Throwable err, String classz) {
		logger.debug("[" + classz + "]" + message, err);
	}

	/**
	 * error
	 * 
	 * @param message
	 */
	public static final void error(Object message) {
		logger.error(message);
	}

	public static final void error(Object message, Throwable err) {
		logger.error(message, err);
	}

	public static final void error(Object message, String classz) {
		logger.error("[" + classz + "]" + message);
	}

	public static final void error(Object message, Throwable err, String classz) {
		logger.error("[" + classz + "]" + message, err);
	}

	/**
	 * fatal
	 * 
	 * @param message
	 */
	public static final void fatal(Object message) {
		logger.fatal(message);
	}

	public static final void fatal(Object message, Throwable err) {
		logger.fatal(message, err);
	}

	public static final void fatal(Object message, String classz) {
		logger.fatal("[" + classz + "]" + message);
	}

	public static final void fatal(Object message, Throwable err, String classz) {
		logger.fatal("[" + classz + "]" + message, err);
	}

	public static Logger getLogger() {
		return logger;
	}
}
