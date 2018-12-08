package com.sept.framework.web.ao;

import com.sept.exception.AsoException;
import com.sept.exception.SeptException;
import com.sept.framework.web.ao.log.ASOLog;
import com.sept.support.model.data.DataObject;
import com.sept.support.thread.GlobalToolkit;
import com.sept.support.util.DateUtil;

/**
 * 只负责参数的校验以及记录日志 只能调用APO
 * 
 * @author zchar
 * 
 */
public abstract class ASO extends AbstractSupport {
	protected abstract DataObject entry(DataObject para) throws Exception;

	private ASOLog log;

	public DataObject doEntry(DataObject para) throws AsoException {
		DataObject pdo = null;
		try {
			log = new ASOLog();
			log.setStartTime(DateUtil.getCurrentDate().getTime());
			log.setClassName(this.getClass().getName());
			pdo = this.doMethod("entry", para);
		} catch (Exception e) {
			throw new AsoException(e);
		} finally {
			if (null == log) {
				log = new ASOLog();
			}
			this.addlog();
		}

		return pdo;
	}

	protected void asoException(String message) throws AsoException {
		throw new AsoException(message);
	}

	protected void asoException(Exception message) throws AsoException {
		throw new AsoException(message);
	}

	protected APO newAPO(Class<? extends APO> classz) {
		try {
			return classz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 这里用来记日志,先方茹茹当前线程中，由此次请求结束时自动调用
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-3
	 * @since V1.0
	 */
	private void addlog() {
		try {
			if (null == log) {
				log = new ASOLog();
			}
			//System.out.println("ASO记日志--"+GlobalToolkit.getCurrentThread().getStartTime());
			GlobalToolkit.getCurrentThread().addLogClass(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 记录附加信息 对于附加信息，可以自由规范化以便日后查询
	 * 
	 * @param attachMessage
	 * @throws SeptException
	 */
	protected void log(String attachMessage) throws SeptException {
		if (null == log) {
			log = new ASOLog();
		}
		log.setAttachMessage(attachMessage);
	}
}
