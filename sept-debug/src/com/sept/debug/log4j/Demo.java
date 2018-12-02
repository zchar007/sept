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
		LOGGER.debug("hello log4j !");
//		LOGGER.info("hello log4j !");
//		LOGGER.debug("hello log4j !");
//		LogHandler.info("hello log4j !");
//		LogHandler.warn("hello log4j !");
//		LogHandler.error("hello log4j !");
		LogHandler.info("hello log4j !");
//		LogHandler.fatal("hello log4j !");
	    Demo demo = new Demo();
        demo.go();
	}
    
    public void go(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for(StackTraceElement s : stackTrace){
            System.out.println("类名：" + s.getClassName() + "  ,  java文件名：" + s.getFileName() + ",  当前方法名字：" + s.getMethodName() + ""
                    + " , 当前代码是第几行：" + s.getLineNumber() + ", " );
        }
    }
}
