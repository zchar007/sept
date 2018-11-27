package com.sept.debug.log4j;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

class Demo {
	// 日志记录器
	private static Logger LOGGER = LogManager.getLogger(Demo.class);

	public static void main(String[] args) {
		// 自动快速地使用缺省Log4j环境
		//BasicConfigurator.configure();
		// 打印日志信息
//		LOGGER.debug("hello log4j !");
//		LOGGER.info("hello log4j !");
//		LOGGER.debug("hello log4j !");
//		LogHandler.info("hello log4j !");
//		LogHandler.warn("hello log4j !");
//		LogHandler.error("hello log4j !");
		LogHandler.debug("hello log4j !", Demo.class+"main");
//		LogHandler.fatal("hello log4j !");
	}
}
