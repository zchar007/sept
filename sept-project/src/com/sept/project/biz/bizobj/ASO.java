package com.sept.project.biz.bizobj;

import com.sept.datastructure.DataObject;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.exception.BizException;
import com.sept.project.biz.BizEntry;
import com.sept.project.biz.exception.ApoException;
import com.sept.project.biz.exception.AsoException;

public abstract class ASO extends BizEntry {
	/** 执行入口 **/
	protected abstract DataObject entry(DataObject para) throws AppException;

	/**
	 * 外部入口
	 * 
	 * @throws ApoException,
	 *             AsoException
	 * 
	 * @throws BizException
	 **/
	public DataObject doEntry(DataObject para) throws ApoException, AsoException {
		DataObject pdo = null;
		try {
			pdo = this.doMethod("entry", para);
		} catch (Exception e) {
			/** ASO中不允许出现biz异常，即使出现也会被专程ApoException **/
			LogHandler.fatal("执行Apo方法异常", e);
			if (e instanceof ApoException) {
				throw (ApoException) e;
			}
			throw new AsoException(e);
		}
		return pdo;
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
}
