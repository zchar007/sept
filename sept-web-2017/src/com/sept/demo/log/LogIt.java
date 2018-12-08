package com.sept.demo.log;

import com.sept.exception.AppException;
import com.sept.support.thread.LogThread;

class LogIt extends LogThread {

	public LogIt() throws AppException {
		super();
	}

	@Override
	public void doLog() {
		System.out.println("暂时未记请求日志"+this.toString());
	}

}