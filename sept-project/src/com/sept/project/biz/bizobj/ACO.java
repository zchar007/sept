package com.sept.project.biz.bizobj;

import com.sept.datastructure.DataObject;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.exception.BizException;
import com.sept.exception.SeptException;
import com.sept.project.biz.BizQueryEntry;

/**
 * 
 * @description 数据检查类，可用于各类数据检擦
 *
 * @author zchar.2018年12月1日上午12:02:52
 */
public abstract class ACO extends BizQueryEntry {

	/** 执行入口 **/
	protected abstract DataObject entry(DataObject para) throws AppException;

	/**
	 * 外部入口
	 * 
	 * @throws BizException
	 **/
	public DataObject doEntry(DataObject para) throws SeptException {
		DataObject pdo = null;
		try {
			pdo = this.doMethod("entry", para);
		} catch (Exception e) {
			if (!(e.getCause() instanceof BizException)) {
				LogHandler.fatal("执行ACO方法异常", e);
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

	protected ACO newACO(Class<? extends ACO> classz) {
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
