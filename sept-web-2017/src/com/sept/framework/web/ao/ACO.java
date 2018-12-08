package com.sept.framework.web.ao;

import com.sept.exception.AsoException;
import com.sept.support.model.data.DataObject;

/**
 * 一些检测以及通用的计算
 * 
 * @author zchar
 *
 */
public abstract class ACO extends AbstractSupport {

	protected abstract DataObject entry(DataObject para) throws Exception;

	public DataObject doEntry(DataObject para) throws AsoException {
		DataObject pdo = null;
		try {
			pdo = this.doMethod("entry", para);
		} catch (Exception e) {
			throw new AsoException(e);
		}
		return pdo;
	}

	protected void asoException(String message) throws AsoException {
		throw new AsoException(message);
	}

	protected void asoException(Exception message) throws AsoException {
		throw new AsoException(message);
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
