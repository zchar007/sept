package com.sept.project.biz;

import com.sept.datastructure.DataObject;
import com.sept.exception.SeptException;

public interface BusinessAble {
	
	DataObject doMethod(Class<? extends BusinessAble> classz, String methodName, DataObject para) throws SeptException;

	DataObject doMethod(String method, DataObject para) throws SeptException;

}
