package com.sept.project.biz.bizobj;

import com.sept.datastructure.DataObject;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.BizException;
import com.sept.project.biz.BizEntry;
import com.sept.project.biz.exception.DboException;

/**
 * TODO 数据库操作的类，最好不要让业务系统Object调用
 * 
 * @description
 *
 * @author zchar.2018年12月1日上午12:08:13
 */
public abstract class DBO extends BizEntry {

	/** 执行入口 **/
	protected abstract DataObject entry(DataObject para) throws DboException;

	/**
	 * 外部入口
	 * 
	 * @throws BizException
	 **/
	public DataObject doEntry(DataObject para) throws DboException {
		DataObject pdo = null;
		try {
			pdo = this.doMethod("entry", para);
		} catch (Exception e) {
			/** DBO只能抛出DboException **/
			LogHandler.fatal("执行Apo方法异常", e);
			throw new DboException(e);
		}
		return pdo;
	}
}
