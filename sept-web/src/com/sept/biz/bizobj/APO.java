package com.sept.biz.bizobj;

import com.sept.biz.BizEntry;
import com.sept.biz.exception.ApoException;
import com.sept.database.db.Sql;
import com.sept.datastructure.DataObject;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.exception.BizException;

public abstract class APO extends BizEntry {
	public APO() {
		/** 只允许执行查询的sql **/
		this.sql = new Sql(true, true, false, false);
	}

	/** 执行入口 **/
	protected abstract DataObject entry(DataObject para) throws AppException;

	/**
	 * 外部入口
	 * 
	 * @throws BizException
	 **/
	public DataObject doEntry(DataObject para) throws ApoException {
		DataObject pdo = null;
		try {
			pdo = this.doMethod("entry", para);
		} catch (Exception e) {
			/** APO中不允许出现biz异常，即使出现也会被专程ApoException **/
			LogHandler.fatal("执行Apo方法异常", e);
			throw new ApoException(e);
		}
		return pdo;
	}
}
