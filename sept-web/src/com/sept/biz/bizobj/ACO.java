package com.sept.biz.bizobj;

import com.sept.biz.BizEntry;
import com.sept.database.db.Sql;
import com.sept.datastructure.DataObject;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.exception.BizException;
import com.sept.exception.SeptException;

/**
 * 
 * @description 数据检查类，可用于各类数据检擦
 *
 * @author zchar.2018年12月1日上午12:02:52
 */
public abstract class ACO extends BizEntry {
	public ACO() {
		/** 只允许执行查询的sql **/
		this.sql = new Sql();
	}

	/** 执行入口 **/
	protected abstract DataObject entry(DataObject para) throws AppException;

	/** 外部入口 
	 * @throws BizException **/
	public DataObject doEntry(DataObject para) throws SeptException {
		DataObject pdo = null;
		try {
			pdo = this.doMethod("entry", para);
		} catch (Exception e) {
			if (!(e.getCause() instanceof BizException)) {
				LogHandler.fatal("执行Aco方法异常", e);
			}
			// 不是的话直接抛出
			if (e.getCause() instanceof BizException) {
				throw (BizException) (e.getCause());
			}
			if (e.getCause() instanceof AppException) {
				throw (AppException) (e.getCause());
			}
			throw new AppException(e);
		}
		return pdo;
	}
}
